package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.IRayTracerAnimator;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Class that can, with the assistance of GUI, create a simple animation of user rotating around the scene and from time to time going up-down.
 * @author Andi Škrgat
 * @version 1.0
 */
public class RayCasterParallel2 {
	
	/**
	 * Here program starts with calling {@linkplain RayTraceryViewer} to show results of what {@linkplain IRayTracerProducer} created
	 * @param args
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(
		getIRayTracerProducer(), getIRayTracerAnimator(), 30, 30
		);
	}
	
	private static IRayTracerAnimator getIRayTracerAnimator() {
		return new IRayTracerAnimator() {
			long time;
		
			@Override
			public void update(long deltaTime) {
				time += deltaTime;
			}
		
			@Override
			public Point3D getViewUp() { // fixed in time
				return new Point3D(0,0,10);
			}
		
			@Override
			public Point3D getView() { // fixed in time
					return new Point3D(-2,0,-0.5);
			}
		
			@Override
			public long getTargetTimeFrameDuration() {
				return 120; // redraw scene each 150 milliseconds
			}
		
			@Override
			public Point3D getEye() { // changes in time
				double t = (double)time / 10000 * 2 * Math.PI;
				double t2 = (double)time / 5000 * 2 * Math.PI;
				double x = 50*Math.cos(t);
				double y = 50*Math.sin(t);
				double z = 30*Math.sin(t2);
				return new Point3D(x,y,z);
		}
		};
		}
	
	/**
	 * Producer breaks bigger tasks into smaller subtasks and sends results to the observer to represent them
	 * to the user through GUI.
	 * @returns instance of IRayTracerProducer. 
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical, int width,
					int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {
				
				System.out.println("Započinjem izračune...");
				ForkJoinPool pool = new ForkJoinPool(); 
				short[] red = new short[width*height];
				short[] green = new short[width*height];
				short[] blue = new short[width*height];
				Scene scene = RayTracerViewer.createPredefinedScene();
//				double t1 = (System.currentTimeMillis());
				CasterJob job = new CasterJob(0, height, eye, view, viewUp, horizontal, vertical, width, height, requestNo, observer, cancel, 
				red,green,blue,scene);
				pool.invoke(job);
				pool.shutdown();
//				double t2 = (System.currentTimeMillis());
//				System.out.println(t2 -t1);
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");	
			}
		};
	}

}
