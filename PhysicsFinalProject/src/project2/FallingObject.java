package project2;

import processing.core.PApplet;

public class FallingObject {
	public static int updatesPerSecond = 60 * 30;
	public static final double G = 9.8;
	public static final double AIR_DENSITY = 1.293; // air density, in kg/m^3
	
	public ObjectData obj;
	public double p; // position
	public double v; // velocity
	public double m; // mass
	public double a; // area
	private PApplet parent;
	
	public FallingObject(PApplet parent) {
		this.parent = parent;
	}
	
	public void draw() {
		
	}
	
	public void reset() {
		p = 0;
		v = 0;
	}
	
	public double getAcceleration() {
		return G - (AIR_DENSITY * (v * v) * obj.dc * a) / (2 * m);
	}
	
	public void update() {
		for (int i = 0; i < updatesPerSecond / parent.frameRate; i++) {
			a += getAcceleration();
			v += a / updatesPerSecond;
			p += v / updatesPerSecond;
		}
	}
}
