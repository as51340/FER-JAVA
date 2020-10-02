package hr.fer.zemris.java.raytracer;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.GraphicalObject;

/**
 * Class{@linkplain RayCaster} is used to simulate ray casting. First, a scene is needed, where all objects and lights will be placed and then 
 * {@linkplain IRayTracerProducer} obtains results and sends them to {@linkplain IRayTracerResultObserver}. 
 * @author Andi Škrgat
 * @version 1.0
 */
public class RayCaster {

	/**
	 * Main method from where program starts.
	 * @param args, user should not provide any arguments
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(),
				new Point3D(10,0,0),
				new Point3D(0,0,0),
				new Point3D(0,0,10),
				20, 20);
	}
	
	/**
	 * Method that creates concrete instance of IRayTracerProducer which is class that should manage ray casting. Results are sent to {@linkplain IRayTracerObserver}
	 * where results are processed
	 * @returns conrete implementation of IRayTracerProducer
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical, int width,
					int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {
				
				System.out.println("Započinjem izračune...");
				short[] red = new short[width*height];
				short[] green = new short[width*height];
				short[] blue = new short[width*height];
				Point3D og = getOg(eye, view); 
				Point3D vuv = getZAxis(og, viewUp); //
				Point3D j = getJVector(vuv, og); //normalized
				Point3D yAxis = j.negate();
				Point3D xAxis = getXAxis(og, j);
				Point3D zAxis = xAxis.vectorProduct(yAxis);
				Point3D screenCorner = getScreenCorner(xAxis, j, view, horizontal, vertical);
				Scene scene = RayTracerViewer.createPredefinedScene();
				short[] rgb = new short[3];
				int offset = 0;
				for(int y = 0; y < height; y++) {
					for(int x = 0; x < width; x++) {
						if(cancel.get() == true) {
							break;
						}
						Point3D screenPoint = getScreenPoint(screenCorner, x, width, horizontal, xAxis, y, height, vertical, j);
				Ray ray = Ray.fromPoints(eye, screenPoint);
				tracer(scene, ray, rgb, eye);
				red[offset] = rgb[0] > 255 ? 255 : rgb[0];
				green[offset] = rgb[1] > 255 ? 255 : rgb[1];
				blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
				offset++;
				}
				}
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");	
			}
			
			/**
			 * Private method used for obtaining eye-view vector
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
			 * @return Point3D representing unit vector of yAxis in counter direction
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
					if(sourcePoint.sub(newRisPoint).norm() + 0.001 < sourcePoint.sub(risPoint).norm()) {
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
			 * Private method for getting diffuse component of some color
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
			 * Private method for getting reflected component of some color
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
			 * Private method for cheching if there is object between some object and light source. If there are more, returns one with the minimum
			 * distance to the light source.
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
		};
	}
}
