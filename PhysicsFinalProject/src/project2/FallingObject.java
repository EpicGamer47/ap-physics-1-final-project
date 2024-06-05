package project2;

import processing.core.PApplet;

import static project2.PhysicsSimulator.ss;

public class FallingObject {
	public static int updatesPerSecond = 60 * 30;
	public static final double G = 9.8;
	public static final double AIR_DENSITY = 1.293; // air density, in kg/m^3
	
	public PApplet parent;
	public ObjectData obj;
	public double p; // position
	public double v; // velocity
	
	public int h; // height
	public double m; // mass
	public double a; // area
	
	public FallingObject(PApplet parent) {
		this.parent = parent;
	}
	
	public void draw() {
		final float xC = 200 * ss, sY = 100 * ss, bY = 800 * ss;
		final float r = 10 * ss * (float) Math.sqrt(a);
		parent.rectMode(PApplet.CORNERS);
		parent.ellipseMode(PApplet.CORNERS);
		parent.fill(obj.color);
		
		float yC = sY + (float) (p / h) * (bY - sY);
		float x1 = xC - r, x2 = xC + r;
		float y1 = yC - r, y2 = yC + r;
		
		switch (obj) {
		case ARROW:
			parent.line(xC, y1, xC, y2);
			break;
		case CONE:
			parent.triangle(xC, y2, x1, y1, x2, y1);
			break;
		case CUBE:
			parent.rect(x1, y1, x2, y2);
			break;
		case DIAMOND:
			parent.rect(xC, y1, x1, yC, xC, y2, x2, yC);
			break;
		case HALF_SPHERE:
			parent.arc(x1, y1, x2, y2, 0, PApplet.PI, PApplet.PIE);
		case SHEET:
			parent.line(x1, yC, x2, yC);
			break;
		case SPHERE:
			parent.ellipse(x1, y1, x2, y2);
			break;
		}
	}
	
	public void reset() {
		p = 0;
		v = 0;
	}
	
	public double getAcceleration() {
		return G - (AIR_DENSITY * (v * v) * obj.dc * a) / (2 * m);
	}
	
	public boolean update() {
		for (int i = 0; i < updatesPerSecond / parent.frameRate; i++) {
			v += getAcceleration() / updatesPerSecond;
			p += v / updatesPerSecond;
		}
		
		if (p > h) {
			p = h;
			return false;
		}
		
		return true;
	}
}
