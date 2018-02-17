package project.spirit;

import sedgewick.StdDraw;

public class Building extends Spirit {

	public Building(double left, double bottom, double width, double height) {
		super(left, bottom, width, height);
	}

	@Override
	public void draw() {
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.filledRectangle(left + width / 2, bottom + height / 2, width / 2, height / 2);
		StdDraw.setPenColor(StdDraw.WHITE);
		int num = 4 + (int) Math.random() * 2;
		for (int i = 0; i < num; i++) {
			double x = Math.random() * (this.width - 10) + left;
			double y = Math.random() * this.height / num + this.height * i / num;
			StdDraw.filledRectangle(x, y, 8, 4);
		}
	}

	@Override
	public String getName() {
		return "Building";
	}

}
