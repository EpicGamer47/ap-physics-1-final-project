package project2;

import static project2.PhysicsSimulator.ss;

import processing.core.PApplet;

public class MainRunner extends PApplet {
	
	private PhysicsSimulator sr;
	
	public static void main(String[] args) {
		PApplet.main("project2.MainRunner");
	}
	
	@Override
	public void settings() {
		size((int)(1600 * ss), (int)(900 * ss));
	}

	@Override
	public void setup() {
		sr = new PhysicsSimulator(this);
		frameRate(30);
	}
	
	@Override
	public void draw() {
		sr.draw();
	}
	
	@Override
	public void mouseClicked() {
		sr.mouseClicked(mouseX, mouseY);
	}
	
	@Override
	public void keyPressed() {
		sr.keyPressed(key);
	}
}

