package project.natural;

import java.awt.Color;
import java.awt.Font;

import project.Consts;
import sedgewick.StdDraw;

public class Wind implements Natural {

	private double maxSpeed;
	private double lastSpeed = maxSpeed + 1;
	private double currentSpeed = maxSpeed + 1;

	public Wind(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	@Override
	public void update() {
		lastSpeed = currentSpeed;
		currentSpeed = Math.random() * 2 * maxSpeed - maxSpeed;
	}

	@Override
	public double getNaturalSpeedInfluence() {
		return currentSpeed;
	}

	public void draw() {
		if (lastSpeed < maxSpeed) {
			drawRaw((int) lastSpeed, StdDraw.WHITE);
		}
		drawRaw((int) currentSpeed, StdDraw.BLUE);
	}

	public void drawRaw(int speed, Color color) {
		StdDraw.setPenColor(color);
		StdDraw.setFont(new Font("Broadway", Font.ITALIC, 40));
		StdDraw.text(Consts.GAME_WIDTH / 2 - 100, Consts.GAME_HEIGHT - 100, "Wind:" + (int) Math.abs(speed));
		StdDraw.line(Consts.GAME_WIDTH / 2, Consts.GAME_HEIGHT - 100, Consts.GAME_WIDTH / 2 + 100,
				Consts.GAME_HEIGHT - 100);
		if (speed > 0) {
			double[] x = { Consts.GAME_WIDTH / 2 + 80, Consts.GAME_WIDTH / 2 + 100, Consts.GAME_WIDTH / 2 + 80 };
			double[] y = { Consts.GAME_HEIGHT - 90, Consts.GAME_HEIGHT - 100, Consts.GAME_HEIGHT - 110 };
			StdDraw.filledPolygon(x, y);
		} else {
			double[] x = { Consts.GAME_WIDTH / 2 + 20, Consts.GAME_WIDTH / 2, Consts.GAME_WIDTH / 2 + 20 };
			double[] y = { Consts.GAME_HEIGHT - 90, Consts.GAME_HEIGHT - 100, Consts.GAME_HEIGHT - 110 };
			StdDraw.filledPolygon(x, y);
		}
	}

	@Override
	public int getOrientation() {
		return 0;
	}

}
