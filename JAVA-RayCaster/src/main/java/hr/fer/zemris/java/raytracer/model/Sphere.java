package hr.fer.zemris.java.raytracer.model;

import hr.fer.zemris.java.raytracer.RayIntersectionSphere;
import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;

/**
 * Concrete implementation of GraphicalObject with most important method for finding closest intersection.
 * @author Andi Škrgat
 * @version 1.0
 */
public class Sphere extends GraphicalObject {
	
    /**
     * Sphere's center 
     */
    private Point3D center;
    
    /**
     * Sphere's radius .
     */
    private double radius;
    
    /**
     * Specific coefficient for diffuse component-red color
     */
    private double kdr;
    
    /**
     * Specific coefficient for diffuse component-green color
     */
    private double kdg;
    
    /**
     * Specific coefficient for diffuse component-blue color
     */
    private double kdb;
    
    /**
     * Specific coefficient for reflective component-red color
     */
    private double krr;
    
    /**
     * Specific coefficient for reflective component-green color
     */
    private double krg;
    
    /**
     * Specific coefficient for reflective component-blue color
     */
    private double krb;
    
    
    /**
     * Shinines factor
     */
    private double krn;

    /***
     * Default constructor that intializes sphere's properties
     * @param center Sphere's center 
     * @param radius Sphere's radius 
     * @param kdr Specific coefficient for diffuse component-red color
     * @param kdg Specific coefficient for diffuse component-green color
     * @param kdb Specific coefficient for diffuse component-blue color
     * @param krr Specific coefficient for reflective component-red color
     * @param krg Specific coefficient for reflective component-green color
     * @param krb Specific coefficient for reflective component-blue color
     * @param krn Shinines factor
     */
    public Sphere(Point3D center, double radius, double kdr, double kdg,
            double kdb, double krr, double krg, double krb, double krn) {
        this.center = center;
        this.radius = radius;
        this.kdr = kdr;
        this.kdg = kdg;
        this.kdb = kdb;
        this.krr = krr;
        this.krg = krg;
        this.krb = krb;
        this.krn = krn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RayIntersection findClosestRayIntersection(Ray ray) {
       double a = 1;
       Point3D dir = ray.direction;
       Point3D start = ray.start;
       Point3D dif = start.sub(center);
       double b = 2 * dir.scalarProduct(dif);
       double c = dif.scalarProduct(dif) - radius * radius;
       double dis = b*b - 4 * a *c;
       if(dis < 0) {
    	   return null;
       }
       double t1 = (-b - Math.sqrt(dis)) / 2;
       double t2 = (-b + Math.sqrt(dis)) / 2;
       Point3D pt1 = start.add(dir.scalarMultiply(t1));
       Point3D pt2 = start.add(dir.scalarMultiply(t2));
       double distance1 = pt1.sub(start).norm();
       double distance2 = pt2.sub(start).norm();
       if(t1 >= 0) {
    	   return new RayIntersectionSphere(pt1,distance1, true, this);
       }
       if(t2 >= 0) {
    	   return new RayIntersectionSphere(pt2,distance2, true, this);
       }
       return null;
       
       
    }

	/**
	 * @return the center
	 */
	public Point3D getCenter() {
		return center;
	}

	/**
	 * @param center the center to set
	 */
	public void setCenter(Point3D center) {
		this.center = center;
	}

	/**
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}

	/**
	 * @return the kdr
	 */
	public double getKdr() {
		return kdr;
	}

	/**
	 * @param kdr the kdr to set
	 */
	public void setKdr(double kdr) {
		this.kdr = kdr;
	}

	/**
	 * @return the kdg
	 */
	public double getKdg() {
		return kdg;
	}

	/**
	 * @param kdg the kdg to set
	 */
	public void setKdg(double kdg) {
		this.kdg = kdg;
	}

	/**
	 * @return the kdb
	 */
	public double getKdb() {
		return kdb;
	}

	/**
	 * @param kdb the kdb to set
	 */
	public void setKdb(double kdb) {
		this.kdb = kdb;
	}

	/**
	 * @return the krr
	 */
	public double getKrr() {
		return krr;
	}

	/**
	 * @param krr the krr to set
	 */
	public void setKrr(double krr) {
		this.krr = krr;
	}

	/**
	 * @return the krg
	 */
	public double getKrg() {
		return krg;
	}

	/**
	 * @param krg the krg to set
	 */
	public void setKrg(double krg) {
		this.krg = krg;
	}

	/**
	 * @return the krb
	 */
	public double getKrb() {
		return krb;
	}

	/**
	 * @param krb the krb to set
	 */
	public void setKrb(double krb) {
		this.krb = krb;
	}

	/**
	 * @return the krn
	 */
	public double getKrn() {
		return krn;
	}

	/**
	 * @param krn the krn to set
	 */
	public void setKrn(double krn) {
		this.krn = krn;
	}
}

    