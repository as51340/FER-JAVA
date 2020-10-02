package hr.fer.zemris.math;

/**
 * This class is used to model immutable 3D vector. All operations returns new object with some changed 
 * property from original class. 
 * @author Andi Škrgat
 * @version 1.0
 */
public class Vector3 {
	
	/**
	 * X, Y and Z coordinate of vector
	 */
	private double x, y, z;
	
	/**
	 * Constructor that initializes properties of vector
	 * @param x coordinate
 	 * @param y coordinate
	 * @param z coordinate
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * @returns length of vector
	 */
	public double norm() {
		double nrm = Math.sqrt(x*x + y*y + z*z);
		return nrm;
	}
	
	/**
	 * Sums 2 vectors
	 * @param other Vector3 second operand in operation od addition
	 * @returns new Vector after performing operation of addition
	 */
	public Vector3 add(Vector3 other) {
		return new Vector3(this.x + other.getX(), this.y + other.getY(), this.z + other.getZ());
	}
	
	/**
	 * Method subtracts 2 vectors
	 * @param other Vector3 second operand in subtraction
	 * @returns new Vector after subtracting
	 */
	public Vector3 sub(Vector3 other) {
		return new Vector3(this.x - other.getX(), this.y - other.getY(), this.z - other.getZ());
	}
	
	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @return the z
	 */
	public double getZ() {
		return z;
	}
	
	/**
	 * @returns new array filled with coordinates of vector
	 */
	public double[] toArray() {
		double[] arr = new double[3];
		arr[0] = x;
		arr[1] = y;
		arr[2] = z;
		return arr;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z +")";
	}
	
	/**
	 * @returns new normalized vector
	 */
	public Vector3 normalized() {
		double modul = norm();
		return new Vector3(x / modul, y / modul, z / modul);
	}
	
	/**
	 * @param other second vector used in operation
	 * @returns dot product of 2 vectors
	 */
	public double dot(Vector3 other) {
		double d = this.x * other.getX() + this.y*other.getY() + this.z*other.getZ();
		return d;
	}
	
	/**
	 * @param other second vector used in operation
	 * @returns cross product of 2 vectors
	 */
	public Vector3 cross(Vector3 other) {
		return new Vector3(this.y * other.getZ() - this.z*other.getY(), other.getX()*this.z - this.x*other.getZ(),
				this.x*other.getY() - other.getX()*this.y);
	}
	
	/**
	 * @param s scaler of vector
	 * @returns new scaled vector
	 */
	public Vector3 scale(double s) {
		return new Vector3(this.x * s, this.y * s, this.z * s);
	}
	
	/**
	 * @param other second vector used in getting angle between these 2 vectors
	 * @returns cosinus of angle betwwen 2 vectors
	 */
	public double cosAngle(Vector3 other) {
		double cosAngle = dot(other) / (norm() * other.norm());
		return cosAngle;
	}
	
	

}
