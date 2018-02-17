package project.spirit;

public abstract class Spirit implements Hittable {

	protected double left;
	protected double bottom;	
	protected double height;
	protected double width;
	
	public Spirit(double left, double bottom, double width, double height){
		this.left = left;
		this.bottom = bottom;
		this.width = width;
		this.height = height;
	}
	
	public abstract String getName();
	public abstract void draw();
	
	public double getLeft() {
		return left;
	}
	
	public double getBottom() {
		return bottom;
	}
	
	public double getHeight(){
		return height;
	}
	
	public double getWidth(){
		return width;
	}

	@Override
	public boolean isHit(Spirit spirit) {
		return false;
	}

	public void draw(double a) {
		// TODO Auto-generated method stub
		
	}

}
