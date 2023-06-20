package project;

import processing.core.PApplet;
import java.text.DecimalFormat;
import static processing.core.PApplet.*;
import static processing.core.PConstants.CENTER;

public class PhysicsSimulator {
	public static final float ss = 0.85f; 
	// screenScale, use to scale down the screen as neccessary

	public static final float g = 9.8f * 100;
	private static final float rampWidth = 1000;
	public static final DecimalFormat dfFriction = new DecimalFormat("0.00");
	public static final DecimalFormat dfVelocity = new DecimalFormat("0.0");
	private PApplet parent;

	private Button plusMu, minusMu, plusTheta, minusTheta; // buttons to change friction & angle
	private Button start;

	private float mu, theta;
	private float rampHeight;
	private float rampSlant;

	private Object object;
	private int[] colorTable;
	private int objectColor;
	private float objectX;
	private float objectV;
	private boolean objectIsFalling;

	public PhysicsSimulator(MainRunner parent) {
		this.parent = parent;

		minusMu = new Button(parent, 1220, 400, 1380, 450, "-");
		plusMu = new Button(parent, 1420, 400, 1580, 450, "+");
		minusTheta = new Button(parent, 1220, 500, 1380, 550, "-");
		plusTheta = new Button(parent, 1420, 500, 1580, 550, "+");
		start = new Button(parent, 1220, 600, 1580, 650, "Start/Stop");

		mu = 0.1f;
		theta = 30;
		updateRamp();

		object = Object.BOX;
		initColorTable(); // won't let me lazy init >:(
		objectColor = colorTable[0];

		objectIsFalling = false;
		objectX = 0;
		objectV = 0;
	}

	private void initColorTable() {
		colorTable = new int[5];
		colorTable[0] = parent.color(0x66, 0x00, 0x00);
		colorTable[1] = parent.color(0x17, 0x17, 0x82);
		colorTable[2] = parent.color(0x4c, 0x17, 0x82);
		colorTable[3] = parent.color(0x23, 0xc3, 0x23);
		colorTable[4] = parent.color(0x1f, 0xad, 0x66);
	}

	private void updateRamp() {
		float oldSlant = rampSlant;
		rampHeight = (float) (rampWidth * Math.tan(Math.toRadians(theta)));
		rampSlant = (float) (rampWidth / Math.cos(Math.toRadians(theta)));
		objectX *= rampSlant / oldSlant; // if object at end, adjust pos
	}

	public void draw() {
		drawBackground();
		drawInfo();
		drawButtons();

		drawRamp();
		drawObject();

		if (objectIsFalling) {
			updateBlockPos();
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
		parent.text("Friction: " + dfFriction.format(mu), 1220 * ss, 360 * ss, 1580 * ss, 400 * ss);
		parent.text("Angle: " + theta, 1220 * ss, 460 * ss, 1580 * ss, 500 * ss);

		parent.textAlign(LEFT);
		parent.textSize(60);
		parent.text(object.getName(), 30 * ss, 870 * ss);

		parent.textSize(40);
		parent.textAlign(RIGHT);
		parent.text("Ramp width: 10m", 1170 * ss, 870 * ss);

		parent.textLeading(70 * ss);
		parent.textAlign(CENTER, CENTER);
		parent.text("Velocity\n" + dfVelocity.format(objectV / 100) + "m/s", 1400 * ss, 810 * ss);
	}

	private void drawButtons() {
		plusMu.draw();
		minusMu.draw();
		plusTheta.draw();
		minusTheta.draw();
		start.draw();
	}

	private void drawRamp() {
		parent.fill(0xaa, 0xaa, 0xaa);
		parent.triangle(100 * ss, 800 * ss, 
				(100 + (float) rampWidth) * ss, 800 * ss, 
				100 * ss, (800 - (float) rampHeight) * ss);
	}

	private void drawObject() {
		final float size = 50 * ss, 
				cos = (float) Math.cos(Math.toRadians(theta)),
				sin = (float) Math.sin(Math.toRadians(theta));

		float z = (100 + rampWidth); // cleans up stuff
		float mX = z - rampWidth / cos + objectX, mY = 800;
		parent.fill(objectColor);

		if (object.isRolling()) {
			float x = mX, y = mY - size / 2; // get center

			float xN = (x - z) * cos - (y - mY) * sin + z, 
					yN = (y - mY) * cos + (x - z) * sin + mY; // rotate

			parent.ellipse(xN * ss, yN * ss, size * ss, size * ss);
		} 
		else {
			float[] x = new float[4], y = new float[4];
			for (int i = 0; i < 4; i++) { // make square
				x[i] = mX - size * (i / 2);
				y[i] = mY - size * (i % 2);
			}

			float[] xN = new float[4], yN = new float[4];
			for (int i = 0; i < 4; i++) { // rotate all points
				xN[i] = (x[i] - z) * cos - (y[i] - mY) * sin + z;
				yN[i] = (y[i] - mY) * cos + (x[i] - z) * sin + mY;
				xN[i] *= ss;
				yN[i] *= ss;
			}

			parent.quad(xN[0], yN[0], xN[1], yN[1], xN[3], yN[3], xN[2], yN[2]);
		}
	}

	public void mouseClicked(int mouseX, int mouseY) {
		if (start.wasClicked(mouseX, mouseY)) {
			objectX = 0;
			objectV = 0;
			objectIsFalling = !objectIsFalling;
		}

		if (objectIsFalling)
			return;

		if (minusMu.wasClicked(mouseX, mouseY)) {
			mu = Math.max(0, mu - 0.05f);
		} 
		else if (plusMu.wasClicked(mouseX, mouseY)) {
			mu = Math.min(1, mu + 0.05f);
		} 
		else if (minusTheta.wasClicked(mouseX, mouseY)) {
			theta = Math.max(5, theta - 5);
			updateRamp();
		} 
		else if (plusTheta.wasClicked(mouseX, mouseY)) {
			theta = Math.min(85, theta + 5);
			updateRamp();
		}
	}

	public void keyPressed(char key) {
		if (key >= '1' && key <= '5') {
			switch (key) {
			case '1': object = Object.BOX; break;
			case '2': object = Object.HOOP; break;
			case '3': object = Object.SHELL; break;
			case '4': object = Object.CYLINDER; break;
			case '5': object = Object.SPHERE; break;
			}
			objectColor = colorTable[key - '1'];
		}
	}

	private void updateBlockPos() {
		final float frameRate = 30;
		
		if (rampSlant > objectX) {
			objectV += object.getAcceleration(mu, theta) / frameRate;
			objectX += objectV / frameRate;
		} 
		else {
			objectX = rampSlant;
			objectIsFalling = false;
		}
	}
}
