package project2;

import processing.core.PApplet;
import static processing.core.PApplet.*;
import static project2.PhysicsSimulator.ss;

public class Button {
	private PApplet parent;
	private float x1, y1, x2, y2;
	private String name;

	public Button(PApplet parent, float x1, float y1, float x2, float y2, String name) {
		this.parent = parent;

		this.x2 = x2;
		this.y2 = y2;
		this.x1 = x1;
		this.y1 = y1;

		this.name = name;
	}

	public void draw() {
		parent.textSize(30);
		parent.rectMode(CORNERS);
		parent.fill(0xFF);
		parent.rect(x1 * ss, y1 * ss, x2 * ss, y2 * ss);

		parent.fill(0x00);
		parent.textAlign(CENTER, CENTER);
		parent.text(name, x1 * ss, y1 * ss, x2 * ss, y2 * ss);

	}

	public boolean wasClicked(int mouseX, int mouseY) {
		return mouseX >= x1 * ss && mouseX <= x2 * ss && mouseY >= y1 * ss && mouseY <= y2 * ss;
	}
}
