package project2;

import processing.core.PApplet;
import java.text.DecimalFormat;
import static processing.core.PApplet.*;
import static processing.core.PConstants.CENTER;

public class PhysicsSimulator {
	public static final float ss = 1f; 
	// screenScale, use to scale down the screen as necessary

	public static final DecimalFormat dfVelocity = new DecimalFormat("0.000");
	public static final DecimalFormat dfTime = new DecimalFormat("0.00");
	public static final DecimalFormat dfAccel = new DecimalFormat("0.00");
	private PApplet parent;

	private Button plusM, minusM, plusH, minusH, plusA, minusA;
	private Button start;

	private FallingObject obj;
	private boolean objectIsFalling;
	private double timeElapsed;
	private double eqTime;

	public PhysicsSimulator(PApplet parent) {
		this.parent = parent;

		minusM = new Button(parent, 1220, 400, 1380, 450, "-");
		plusM = new Button(parent, 1420, 400, 1580, 450, "+");
		minusH = new Button(parent, 1220, 500, 1380, 550, "-");
		plusH = new Button(parent, 1420, 500, 1580, 550, "+");
		minusA = new Button(parent, 1220, 600, 1380, 650, "-");
		plusA = new Button(parent, 1420, 600, 1580, 650, "+");
		start = new Button(parent, 1220, 700, 1580, 850, "Start/Stop");

		obj = new FallingObject(parent);
		obj.obj = ObjectData.SPHERE;
		obj.h = 50;
		obj.m = 20;
		obj.a = 6;
		
		objectIsFalling = false;
	}

	public void draw() {
		drawBackground();
		drawInfo();
		drawButtons();

		obj.draw();
		if (objectIsFalling) {
			double oldV = obj.v;
			objectIsFalling = obj.update();
			timeElapsed += 1.0 / 30;
			
			if (Math.round(oldV * 1000) != Math.round(obj.v * 1000)) {
				eqTime = timeElapsed;
			}
		}
	}

	private void drawBackground() {
		parent.rectMode(CORNER);
	
		parent.background(0x7ecec9);
		
		parent.fill(0xFFa3c2c0);
		parent.rect(1200 * ss, 0, 400 * ss, 900 * ss);
		
		parent.fill(0xFF669996);
		parent.rect(0, 800 * ss, 1200 * ss, 100 * ss);
	}

	private void drawInfo() {
		parent.rectMode(CORNERS);
		
		parent.fill(0);
		parent.textSize(30 * ss);
		parent.textLeading(35 * ss);
		parent.textAlign(LEFT);
		parent.text(
				"Press 1-7 to alternate between objects\n\n"
				+ "Click the buttons below to change the variables\n\n"
				+ "See live information on the left",
				1220 * ss, 20 * ss, 1580 * ss, 880 * ss);

		parent.textAlign(CENTER);
		parent.text("Mass: " + obj.m + "kg", 1220 * ss, 360 * ss, 1580 * ss, 400 * ss);
		parent.text("Height: " + obj.h + "m", 1220 * ss, 460 * ss, 1580 * ss, 500 * ss);
		parent.text("Area: " + obj.a + "m²", 1220 * ss, 560 * ss, 1580 * ss, 600 * ss);

		parent.textAlign(LEFT);
		parent.textSize(60);
		parent.text(obj.obj.toString(), 30 * ss, 870 * ss);

		parent.textLeading(70 * ss);
		parent.textAlign(CENTER, CENTER);
		parent.text("Time.\n" + dfTime.format(timeElapsed) + "s", 1040 * ss, 85 * ss);
		
		parent.textLeading(70 * ss);
		parent.textAlign(CENTER, CENTER);
		parent.text("EQ Time\n" + dfTime.format(eqTime) + "s", 1040 * ss, 285 * ss);
		
		parent.textLeading(70 * ss);
		parent.textAlign(CENTER, CENTER);
		parent.text("Velocity\n" + dfVelocity.format(obj.v) + "m/s", 1040 * ss, 485 * ss);
		
		parent.textLeading(70 * ss);
		parent.textAlign(CENTER, CENTER);
		parent.text("Accel.\n" + dfAccel.format(obj.getAcceleration()) + "m/s²", 1040 * ss, 685 * ss);
	}

	private void drawButtons() {
		plusM.draw();
		minusM.draw();
		plusH.draw();
		minusH.draw();
		plusA.draw();
		minusA.draw();
		start.draw();
	}

	public void mouseClicked(int mouseX, int mouseY) {
		if (start.wasClicked(mouseX, mouseY)) {
			obj.reset();
			objectIsFalling = !objectIsFalling;
			timeElapsed = 0;
		}

		if (objectIsFalling)
			return;

		if (minusM.wasClicked(mouseX, mouseY)) {
			obj.m = Math.max(5, obj.m - 5);
			obj.reset();
		} 
		else if (plusM.wasClicked(mouseX, mouseY)) {
			obj.m = Math.min(50, obj.m + 5);
			obj.reset();
		} 
		else if (minusH.wasClicked(mouseX, mouseY)) {
			if (obj.h == Double.POSITIVE_INFINITY)
				obj.h = 200;
			else
				obj.h = Math.max(10, obj.h - 10);
			obj.reset();
		} 
		else if (plusH.wasClicked(mouseX, mouseY)) {
			if (obj.h == 200)
				obj.h = Double.POSITIVE_INFINITY;
			else
				obj.h = Math.min(200, obj.h + 10);
			
			obj.reset();
		}
		else if (minusA.wasClicked(mouseX, mouseY)) {
			obj.a = Math.max(2, obj.a - 2);
			obj.reset();
		}
		else if (plusA.wasClicked(mouseX, mouseY)) {
			obj.a = Math.min(20, obj.a + 2);
			obj.reset();
		}
	}

	public void keyPressed(char key) {
		if (!objectIsFalling && key >= '1' && key <= '7') {
			obj.obj = ObjectData.values()[key - '1'];
		}
	}
}
