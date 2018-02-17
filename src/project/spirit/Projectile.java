package project.spirit;

import java.util.List;

import project.natural.Natural;
import sedgewick.StdDraw;

public class Projectile extends Spirit {

	private double xSpeed = 0;
	private double ySpeed = 0;

	private double x;
	private double y;
	private double r;

	private List<Spirit> allSpirits;
	private List<Natural> naturals;

	public Projectile(double angle, double velocity, double x, double y, double r, List<Spirit> allSpirits,
			List<Natural> naturals) {
		super(x - r, y - r, r * 2, r * 2);
		this.x = x;
		this.y = y;
		this.r = r;
		this.allSpirits = allSpirits;
		this.naturals = naturals;

		xSpeed = velocity * Math.cos(angle * Math.PI / 180) / 10;
		ySpeed = velocity * Math.sin(angle * Math.PI / 180) / 10;
	}

	@Override
	public String getName() {
		return "projectile";
	}

	@Override
	public void draw() {
	}

	public Spirit doThrow(int orientation) {
		double currentX = x;
		double currentY = y;

		while (true) {
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.filledCircle(currentX, currentY, r+1);

			for (Natural natural : naturals) {
				if (natural.getOrientation() == 0) {
					xSpeed += natural.getNaturalSpeedInfluence() * orientation / 100;
				} else {
					ySpeed += natural.getNaturalSpeedInfluence() / 100;
				}
			}

			currentX += xSpeed * orientation;
			currentY += ySpeed;
			x = currentX;
			y = currentY;

			StdDraw.setPenColor(StdDraw.MAGENTA);
			StdDraw.filledCircle(currentX, currentY, r);
			StdDraw.show(10);
			for (Spirit spirit : allSpirits) {
				if (isHit(spirit)) {
					StdDraw.setPenColor(StdDraw.WHITE);
					StdDraw.filledCircle(currentX, currentY, r+1);
					StdDraw.show(10);
					return spirit;
				}
			}
		}
	}

	@Override
	public boolean isHit(Spirit spirit) {
		double rx = x - (spirit.left + spirit.width / 2);
		double ry = y - (spirit.bottom + spirit.height / 2);
		
		boolean isHit = ComputeCollision(spirit.width, spirit.height, r, rx, ry);
		if (isHit) System.out.println(spirit.getName());
		return isHit;
	}

	private boolean ComputeCollision(double w, double h, double r, double rx, double ry) {
		double dx = Math.min(rx, w * 0.5);
		double dx1 = Math.max(dx, -w * 0.5);
		double dy = Math.min(ry, h * 0.5);
		double dy1 = Math.max(dy, -h * 0.5);
		return (dx1 - rx) * (dx1 - rx) + (dy1 - ry) * (dy1 - ry) <= r * r;
	}

}
