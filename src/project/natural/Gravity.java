package project.natural;

public class Gravity implements Natural {
	
	private double g;
	
	public Gravity(double g) {
		this.g = -g;
	}

	@Override
	public double getNaturalSpeedInfluence() {
		return g;
	}

	@Override
	public void draw() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public int getOrientation() {
		return 1;
	}

}
