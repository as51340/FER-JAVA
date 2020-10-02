package hr.fer.zemris.java.raytracer;

import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;

/**
 * Implementation of RecursiveAction whose job is to color specific pixel. Each job is divided into 2 smaller subtasks and parent job waits them
 * to finish.
 * @author Andi Škrgat
 * @version 1.0
 */
public class CasterJob extends RecursiveAction {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constant that is experimentally calculated and for which I have fastest program running time.
	 */
	private static final int MIN_CONST = 35;
	
	/**
	 * Y-min coordinate of screen
	 */
	private int yMin;
	
	/** 
	 * Y-max coordinate of screen
	 */
	private int yMax;
	
	/**
	 * Width of image
	 */
	private int width;
	
	/**
	 * Height of image
	 */
	private int height;
	
	/**
	 * Simulates position of human
	 */
	private Point3D eye;
	
	/**
	 * Focus point of human
	 */
	private Point3D view;
	
	/**
	 * Given vector so we can determine x and y axis 
	 */
	private Point3D viewUp;
	
	/**
	 * Given parameter for obtaining screen corner and screen points, for x-axis
	 */
	private double horizontal;
	
	/**
	 * Given parameter for obtaining screen corner and screen points, for y-axis
	 */
	private double vertical;
	
	/**
	 * Parameter that is sent to the observer
	 */
	private long requestNo;
	
	/**
	 * Observer handles the GUI
	 */
	private IRayTracerResultObserver observer;
	
	/**
	 * Atomic variable so all threads could use that variable and see if job has been meanwhile canceled
	 */
	private AtomicBoolean cancel;
	
	/**
	 * Saves red pixels for screen
	 */
	private short[] red;
	
	/**
	 * Saves green pixels for screen
	 */
	private short[] green;
	
	/**
	 * Saves blue pixels for screen
	 */
	private short[] blue;
	
	/**
	 * Scene where all objects and light sources are placed
	 */
	private Scene scene;
	
	

	/**
	 * @param yMin yMin coordinate of screen
	 * @param yMay yMax coordinate of screen
	 * @param width Width of image
	 * @param height Height of image
	 * @param eye Simulates position of human
	 * @param view Focus point of human
	 * @param viewUp Given vector so we can determine x and y axis 
	 * @param horizontal Given parameter for obtaining screen corner and screen points, for x-axis
	 * @param vertical Given parameter for obtaining screen corner and screen points, for y-axis
	 * @param requestNo Parameter that is sent to the observer
	 * @param observer Observer handles the GUI
	 * @param cancel Atomic variable so all threads could use that variable and see if job has been meanwhile canceled
	 * @param red Saves red pixels for screen
	 * @param green Saves green pixels for screen
	 * @param blue Saves blue pixels for screen
	 */
	public CasterJob(int yMin, int yMax, Point3D eye, Point3D view,
			Point3D viewUp, double horizontal, double vertical, int width, int height,long requestNo,
			IRayTracerResultObserver observer, AtomicBoolean cancel, short[] red, short[] green, short[] blue, Scene scene) {
		super();
		this.yMin = yMin;
		this.yMax = yMax;
		this.width = width;
		this.height = height;
		this.eye = eye;
		this.view = view;
		this.viewUp = viewUp;
		this.horizontal = horizontal;
		this.vertical = vertical;
		this.requestNo = requestNo;
		this.observer = observer;
		this.cancel = cancel;
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.scene = scene;
	}

	@Override
	protected void compute() {
		int length = yMax - yMin;
		if(length <= MIN_CONST) {
			computeDirectly();
		} else {
			int split = length / 2;
			CasterJob job1 = new CasterJob(yMin, yMin + split, eye, view, viewUp, horizontal, vertical, width, height, requestNo, observer, cancel,
					red,green,blue,scene);
			CasterJob job2 = new CasterJob(yMin + split, yMax, eye, view, viewUp, horizontal, vertical, width, height, requestNo, observer, cancel,
					red, green, blue,scene);
			invokeAll(job1, job2);
		}
	}
	
	/**
	 * When length becomes 1(we have one pixel to color) then this method is called to determine color of pixel
	 */
	private void computeDirectly() {
		Point3D og = getOg(eye, view); 
		Point3D vuv = getZAxis(og, viewUp); //
		Point3D j = getJVector(vuv, og); //normalized
		Point3D yAxis = j.negate();
		Point3D xAxis = getXAxis(og, j);
		Point3D zAxis = xAxis.vectorProduct(yAxis);
		Point3D screenCorner = getScreenCorner(xAxis, j, view, horizontal, vertical);
		short[] rgb = new short[3];
		for(int y = yMin; y < yMax; y++) {
			for(int x = 0; x < width; x++) {
				if(cancel.get() == true) {
					return;
				}
				Point3D screenPoint = getScreenPoint(screenCorner, x, width, horizontal, xAxis, y, height, vertical, j);
				Ray ray = Ray.fromPoints(eye, screenPoint);
				tracer(scene, ray, rgb, eye);
				red[y*width + x] = rgb[0] > 255 ? 255 : rgb[0];
				green[y*width + x] = rgb[1] > 255 ? 255 : rgb[1];
				blue[y*width + x] = rgb[2] > 255 ? 255 : rgb[2];
			}
		}
	}
	

	/**
	 * Private method used for getting eye-view vector
	 * @param eye point where observer stands
	 * @param view point in which observer looks
	 * @returns normalized eye-view vector
	 */
	private Point3D getOg(Point3D eye, Point3D view) {
		Point3D sub = view.sub(eye); //returns new == OK
		return sub.modifyNormalize();
	}
	
	/**
	 * Private method used for getting z-axis from eye-view vector and view up vector. Method checks if eye-view vector and viewUp vector
	 * are colinear and in that case throws IllegalArgumentException
	 * @param vuv normalized view up vector
	 * @param og eye-view vector
	 * @return  Point3D representing unit vector of yAxis in counter direction
	 * @throws IllegalArgumentException if eye-view and viewUp vector are colinear
	 */
	private Point3D getZAxis(Point3D og, Point3D viewUp) {
		if(Math.abs(viewUp.x - og.x) < 1e-7 && Math.abs(viewUp.y - og.y) < 1e-7 && Math.abs(viewUp.z - og.z) < 1e-7) {
			throw new IllegalArgumentException("View up and eye vector should not be the same.");
		}
		return viewUp.normalize(); //returns new == okej
	}
	
	/**
	 * Private method used for getting y-axis from eye-view vector and normalized view up vector
	 * @param vuv normalized view up vector
	 * @param og eye-view vector
	 * @return  Point3D representing unit vector of yAxis in counter direction
	 */
	private Point3D getJVector(Point3D vuv, Point3D og) {
		double scalar = og.scalarProduct(vuv);
		Point3D mod = og.scalarMultiply(scalar); //returns new == OK
		return vuv.sub(mod).modifyNormalize(); //returns new == OK
	}
	
	/**
	 * Private method used for getting x-axis from eye-view vector and j vector. We will obtain i unit vector by performing vector product
	 * on given arguments.
	 * @param og eye-view vector
	 * @param j unit vector in our plane = -yAxis
	 * @returns Point3D representing unit vector of xAxis
	 */
	private Point3D getXAxis(Point3D og, Point3D j) {
		return og.vectorProduct(j).modifyNormalize(); //returns new == Ok
	}
	
	/**
	 * Private method used for obtaining screen corner
	 * @param i unit vector= xAxis
	 * @param j unit vector = yAxis
	 * @param view point of interest for observer
	 * @param horizontal parameter for setting how much will screen corner be distant, observing in xAxis, from point view
	 * @param vertical parameter for setting how much will screen corner be distant, observing in yAxis, from point view
	 * @returns screen corner
	 */
	private Point3D getScreenCorner(Point3D i, Point3D j,  Point3D view, double horizontal, double vertical) {
		Point3D scalI = i.scalarMultiply(horizontal / 2);
		Point3D scalJ = j.scalarMultiply(vertical / 2);
		Point3D v = view.sub(scalI).add(scalJ);
		return v;
	}
	
	/**
	 * Private method used for setting point to plane knowing position of corner and directions of i and j unit vectors
	 * @param corner screen corner
	 * @param x x-pixel
	 * @param width width of screen
	 * @param horizontal parameter for setting how much will screen corner be distant, observing in xAxis, from point view
	 * @param i unit vector in x-direction
	 * @param y y-pixel
	 * @param height height of screen
	 * @param vertical parameter for setting how much will screen corner be distant, observing in yAxis, from point view
	 * @param j unit vector in counter y-direction
	 * @returns position of pixel(x,y) mapped to out plane
	 */
	private Point3D getScreenPoint(Point3D corner, int x, int width, double horizontal, Point3D i, int y, int height, 
			double vertical, Point3D j) {
		double izraz1 = ((double) (x * horizontal) / (double) (width-1));
		double izraz2 = ((double) (y * vertical) / (double) (height-1));
		Point3D scallI = i.scalarMultiply(izraz1);
		Point3D scallJ = j.scalarMultiply(izraz2);
		Point3D v = corner.add(scallI).sub(scallJ); //returns new == OK
		return v;
	}
	
	/**
	 * Method first finds closest intersection between ray and any object and then determines color of pixel
	 * @param scene scene where objects and light are placed
	 * @param ray ray from eye to screen pixel
	 * @param rgb array for storing data about color of pixel
	 * @param eye simulates position where human stands
	 */
	private void tracer(Scene scene, Ray ray, short[] rgb, Point3D eye) {
		RayIntersection risMin = null;
		risMin = findMinimum(scene.getObjects(), ray);
		if(risMin == null) {
			rgb[0] = 0;
			rgb[1] = 0;
			rgb[2] = 0;
		} else {
			determineColorFor(risMin, rgb, scene,eye);
		}
	}
	
	/**
	 * Method that decides how will color specific pixel
	 * @param ris intersection point
	 * @param rgb data about rgb components
	 * @param scene where objects and lights are stored
	 * @param eye represents where human stands
	 */
	private void determineColorFor(RayIntersection ris, short[] rgb, Scene scene, Point3D eye) {
		rgb[0] = 15;
		rgb[1] =15;
		rgb[2] = 15;
		for(LightSource source :scene.getLights()) {
			Ray newRay = Ray.fromPoints(source.getPoint(), ris.getPoint());
			RayIntersection newRisMin = findMinimum(scene.getObjects(), newRay);
			if(newRisMin == null) {
				continue;
			}
			Point3D sourcePoint = source.getPoint();
			Point3D risPoint = ris.getPoint();
			Point3D newRisPoint = newRisMin.getPoint();
			if(sourcePoint.sub(newRisPoint).norm() + 0.002 < sourcePoint.sub(risPoint).norm()) {
				continue;
			}
			Point3D l = sourcePoint.sub(risPoint).normalize();
			Point3D n = ris.getNormal();
			Point3D v = eye.sub(risPoint).normalize();
			double tmp = l.scalarProduct(n);
			Point3D tmp2 = n.scalarMultiply(tmp*2);
			Point3D r = tmp2.sub(l).modifyNormalize();
			rgb[0] += getD(source.getR(), ris.getKdr(), l, n);
			rgb[0] += getR(source.getR(), ris.getKdr(), ris.getKrn(), v, r);
			rgb[1] += getD(source.getG(), ris.getKdg(), l, n);
			rgb[1] += getR(source.getG(), ris.getKdg(), ris.getKrn(), v,r);
			rgb[2] += getD(source.getB(), ris.getKdb(), l, n);
			rgb[2] += getR(source.getB(), ris.getKdb(), ris.getKrn(), v,r);
		}	
	}
	
	/**
	 * Private method for getting difuse component of some color
	 * @param intensity intensity of light
	 * @param koef specific koef characteristic for some surface
	 * @param l vector from intersection point to source of light
	 * @param n normal vector from intersection point
	 * @return value of component
	 */
	private double getD(int intensity, double koef, Point3D l, Point3D n) {
		return intensity * koef * Math.max(0, l.scalarProduct(n));
	}
	
	/**
	 *  Private method for getting reflected component of some color
	 * @param intensity intensity of light
	 * @param koef specific koef characteristic for some surface
	 * @param n shinines factor
	 * @param v vector from intersection point to eye
	 * @param r reflected component of light
	 * @returns value of component
	 */
	private double getR(int intensity, double koef, double n, Point3D v, Point3D r) {
		return intensity * koef * Math.pow(Math.max(0, v.scalarProduct(r)),n);
	}
	
	/**
	 * Private method for cheching if there is some object between some object and light source. If there are more, returns minimum one.
	 * @param list list of graphical objects
	 * @param ray from light source to first object
	 * @returns RayIntersection-object between first object and source of light that is between source of light and first object
	 */
	private RayIntersection findMinimum(List<GraphicalObject> list, Ray ray) {
		RayIntersection risMin = null, ris = null;
		for(GraphicalObject obj: list) {
			ris = obj.findClosestRayIntersection(ray);
			if(ris == null) {
				continue;
			}
			else {
				if(risMin == null) {
					risMin = ris;
				} else {
					if(ris.getDistance() < risMin.getDistance()) {
						risMin = ris;
					}
				}
			}
		}
		return risMin;
	}
	
}