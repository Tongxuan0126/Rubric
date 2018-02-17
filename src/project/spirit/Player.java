package project.spirit;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import project.natural.Natural;
import sedgewick.StdDraw;

public class Player extends Spirit {

	private String name;
	private Projectile projectile;
	private double centerX;
	private Color color;

	public Player(String name, Color color, double centerX, double bottom) {
		super(centerX - 17, bottom, 34, 100);
		this.name = name;
		this.centerX = centerX;
		this.color = color;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void draw(double a) {
		StdDraw.setPenColor(color);
		StdDraw.picture(centerX, bottom + 50, "player.png", 150, 100);
		StdDraw.setFont(new Font("Broadway", Font.PLAIN, 30));
		//StdDraw.text(centerX, bottom + 130, this.getName());
		StdDraw.text(a, 600, this.getName());
	}

	public Spirit throwProjectile(int orientation, double angle, double velocity, List<Spirit> allSpirits,
			List<Natural> naturals) {
		projectile = new Projectile(angle, velocity, centerX, bottom + 150, 10, allSpirits, naturals);
		return projectile.doThrow(orientation);
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}

}
