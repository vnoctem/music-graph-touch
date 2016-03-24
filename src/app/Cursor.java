package app;

import scene.Point2D;

public class Cursor {
	private int type;
	private Point2D pos;
	
	public Cursor(int type, float x, float y) {
		this.type = type;
		pos = new Point2D(x, y);
	}

	public int getType() {
		return type;
	}

	public Point2D getPos() {
		return pos;
	}
}
