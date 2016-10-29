package map;

public class Point { 
	public final int x; 
	public final int y; 
	public Point(int x, int y) { 
		this.x = x; 
		this.y = y; 
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("X: " + this.x);
		sb.append(" Y: " + this.y);

		return sb.toString();
	}

} 