package project2;

import processing.core.PApplet;
import java.text.DecimalFormat;
import static processing.core.PApplet.*;
import static processing.core.PConstants.CENTER;

public class PhysicsSimulator {
	public static final float ss = 1f; 
	// screenScale, use to scale down the screen as neccessary

	private static final float rampWidth = 1000;
//	public static final DecimalFormat dfFriction = new DecimalFormat("0.00");
	public static final DecimalFormat dfVelocity = new DecimalFormat("0.0");
	private PApplet parent;

	private Button plusM, minusM, plusH, minusH, plusA, minusA; // buttons to change friction & angle
	private Button start;

	private float fallHeight;
	private FallingObject obj;

	private boolean objectIsFalling;

	public PhysicsSimulator(PApplet parent) {
		this.parent = parent;

		minusM = new Button(parent, 1220, 400, 1380, 450, "-");
		plusM = new Button(parent, 1420, 400, 1580, 450, "+");
		minusH = new Button(parent, 1220, 500, 1380, 550, "-");
		plusH = new Button(parent, 1420, 500, 1580, 550, "+");
		minusA = new Button(parent, 1220, 600, 1380, 650, "-");
		plusA = new Button(parent, 1420, 600, 1580, 650, "+");
		start = new Button(parent, 1220, 700, 1580, 750, "Start/Stop");

		obj = new FallingObject(parent);
		obj.obj = ObjectData.DIAMOND;
		obj.h = 50;
		obj.m = 20;
		obj.a = 6;
		
		objectIsFalling = false;
	}

	public void draw() {
		drawBackground();
		drawInfo();
		drawButtons();

		drawRamp();
		obj.draw();

		if (objectIsFalling) {
			obj.update();
		}
	}

	private void drawBackground() {
		parent.rectMode(CORNER);
		parent.background(0x7ecec9);
		parent.fill(0xa3, 0xc2, 0xc0);
		parent.rect(1200 * ss, 0, 400 * ss, 900 * ss);
	}

	private void drawInfo() {
		parent.rectMode(CORNERS);
		parent.fill(0);
		parent.textSize(30 * ss);
		parent.textLeading(35 * ss);
		parent.textAlign(LEFT);
		parent.text(
				"Press 1-5 to alternate between objects\n\n"
				+ "Click the buttons to change the variables\n\n"
				+ "When the ball reaches the end, see below for the final speed.",
				1220 * ss, 20 * ss, 1580 * ss, 880 * ss);

		parent.textAlign(CENTER);
		parent.text("Mass: " + obj.m, 1220 * ss, 360 * ss, 1580 * ss, 400 * ss);
		parent.text("Height: " + obj.h, 1220 * ss, 460 * ss, 1580 * ss, 500 * ss);
		parent.text("Area: " + obj.a, 1220 * ss, 560 * ss, 1580 * ss, 600 * ss);

		parent.textAlign(LEFT);
		parent.textSize(60);
		parent.text(obj.obj.toString(), 30 * ss, 870 * ss);

		parent.textLeading(70 * ss);
		parent.textAlign(CENTER, CENTER);
		parent.text("Velocity\n" + dfVelocity.format(obj.v) + "m/s", 1400 * ss, 810 * ss);
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

	private void drawRamp() {
		//TODO
	}

	private void drawObject() {
		obj.draw();
	}

	public void mouseClicked(int mouseX, int mouseY) {
		if (start.wasClicked(mouseX, mouseY)) {
			obj.reset();
			objectIsFalling = !objectIsFalling;
		}

		if (objectIsFalling)
			return;

		if (minusM.wasClicked(mouseX, mouseY)) {
			obj.m = Math.max(5, obj.m - 5);
		} 
		else if (plusM.wasClicked(mouseX, mouseY)) {
			obj.m = Math.min(50, obj.m + 5);
		} 
		else if (minusH.wasClicked(mouseX, mouseY)) {
			obj.h = Math.max(10, obj.h - 10);
		} 
		else if (plusH.wasClicked(mouseX, mouseY)) {
			obj.h = Math.min(200, obj.h + 10);
		}
		else if (minusA.wasClicked(mouseX, mouseY)) {
			obj.a = Math.max(2, obj.a - 2);
		}
		else if (plusA.wasClicked(mouseX, mouseY)) {
			obj.a = Math.min(20, obj.a + 2);
		}
	}

	public void keyPressed(char key) {
		if (key >= '1' && key <= '6') {
			obj.obj = ObjectData.values()[key - '1'];
		}
	}
}
