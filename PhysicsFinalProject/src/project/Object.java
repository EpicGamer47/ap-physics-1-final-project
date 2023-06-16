package project;

import static project.PhysicsSimulator.*;

public enum Object {
	BOX(-1, false, "Box"), // TODO if you somehow get an error from the -1 i swear to god
	HOOP(1, true, "Hoop"), 
	SHELL(2 / 3.0, true, "Hollow Sphere"),
	CYLINDER(1 / 2.0, true, "Cylinder"), 
	SPHERE(2 / 5.0, true, "Sphere");

	private double iC; // inertia coefficient
	private boolean isRolling;
	private String name;

	Object(double inertiaCoefficient, boolean isRolling, String name) {
		this.iC = inertiaCoefficient;
		this.isRolling = isRolling;
		this.name = name;
	}

	public boolean isRolling() {
		return isRolling;
	}

	public boolean isSlipping(double mu, double theta) {
		return isRolling && mu < critMu(theta);
	}

	public double getAcceleration(double mu, double theta) {
		if (isRolling && !isSlipping(mu, theta)) {
			return 2 * g * Math.sin(Math.toRadians(theta)) / (iC + 1);
		} 
		else if (isRolling) {
			double ratio = mu / critMu(theta);
			return (1 - ratio) * g * Math.sin(Math.toRadians(theta)) * (1 - mu)
					+ (ratio) * 2 * g * Math.sin(Math.toRadians(theta)) / (iC + 1);
		} 
		else {
			return g * Math.sin(Math.toRadians(theta)) * (1 - mu);
		}
	}

	private double critMu(double theta) {
		return iC / (Math.tan(Math.toRadians(theta)) * (iC + 1));
	}

	public String getName() {
		return name;
	}
}
