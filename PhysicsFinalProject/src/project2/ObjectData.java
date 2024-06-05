package project2;

public enum ObjectData {
	ARROW(0, "Arrow", 0xFF000000),
	SHEET(1.05, "Sheet Metal", 0xFFababab),
	SPHERE(0.47, "Sphere", 0xFF631603), 
	HALF_SPHERE(0.42, "Semi-Sphere", 0x942105),
	CONE(0.50, "Cone", 0xFF0abd60), 
	CUBE(1.05, "Cube", 0xFF1d33a3),
	DIAMOND(0.80, "Angled Cube", 0xFF331da3);
	
	public int color;
	public double dc; // drag coefficient
	public String name;

	ObjectData(double dc, String name, int color) {
		this.dc = dc;
		this.name = name;
		this.color = color;
	}
}
