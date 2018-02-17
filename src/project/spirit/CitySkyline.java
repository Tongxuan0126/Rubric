package project.spirit;

import sedgewick.StdDraw;

public class CitySkyline extends Spirit {

	public CitySkyline(double left, double bottom, double width, double height) {
		super(left, bottom, width, height);
	}

	@Override
	public String getName() {
		return "Skyline";
	}

	@Override
	public void draw() {
		StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
		StdDraw.filledRectangle(left + width/2, bottom + height/2, width/2, height/2);
	}

}
