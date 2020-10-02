package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Sphere;

/**
 * Concrete implementation of {@linkplain RayIntersection}. It inherits RayIntersection but also provides methods for working with sphere considering
 * this is the implementation of intersection between line and sphere
 * @author Andi Škrgat
 * @version 1.0
 */
public class RayIntersectionSphere extends RayIntersection{
	
	// flag: is intersection outer?
	private boolean outer;
	// point of intersection
	private Point3D point;
	// distance between start of ray and intersection
	private double distance;
	
	/**
	 * Reference to the sphere
	 */
	private Sphere sphere;
	

	/**
	 * Constructor for intersection.
	 * 
	 * @param point point of intersection between ray and object
	 * @param distance distance between start of ray and intersection
	 * @param outer specifies if intersection is outer
	 */
	public RayIntersectionSphere(Point3D point, double distance, boolean outer, Sphere sphere) {
		super(point, distance,outer);
		this.point = point;
		this.distance = distance;
		this.outer = outer;
		this.sphere = sphere;
	}
		
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Point3D getNormal() {
		return point.sub(sphere.getCenter()).modifyNormalize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getKdr() {
		return sphere.getKdr();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getKdg() {
		return sphere.getKdg();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getKdb() {
		return sphere.getKdb();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getKrr() {
		return sphere.getKrr();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getKrg() {
		return sphere.getKrg();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getKrb() {
		return sphere.getKrb();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getKrn() {
		return sphere.getKrn();
	}
	

}
