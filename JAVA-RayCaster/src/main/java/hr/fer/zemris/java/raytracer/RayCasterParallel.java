package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Ray caster that works parallel on all available processors. ForkJoinPool is used to break bigger tasks into smaller subtasks 
 * @author Andi Škrgat
 * @version 1.0
 */
public class RayCasterParallel {

	/**
	 * Main method from where program starts.
	 * @param args user should not provide any arguments
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(),
				new Point3D(10,0,0),
				new Point3D(0,0,0),
				new Point3D(0,0,10),
				20, 20);
	}
	
	/**
	 * 
	 * @returns instance of IRayTracerProducer. Producer breaks bigger tasks into smaller subtasks and sends results to the observer to represent them
	 * to the user through GUI.
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
				double t1 = (System.currentTimeMillis());
				CasterJob job = new CasterJob(0, height, eye, view, viewUp, horizontal, vertical, width, height, requestNo, observer, cancel, 
				red,green,blue,scene);
				pool.invoke(job);
				pool.shutdown();
				double t2 = (System.currentTimeMillis());
				System.out.println(t2 -t1);
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");	
			}
		};
	}
}
