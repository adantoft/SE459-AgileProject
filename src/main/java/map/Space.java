package map;

public class Space {
	public final Point bottomLeft;
	public final Point topRight;
	
	public Space(Point bottomLeft, Point topRight) {
		this.bottomLeft = bottomLeft;
		this.topRight = topRight;
	}

	public Point getBottomLeft() {
		return this.bottomLeft;
	}

	public Point getTopRight() {
		return this.topRight;
	}
}