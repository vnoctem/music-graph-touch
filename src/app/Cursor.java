package app;

import scene.Point2D;

public class Cursor {
	private int type;
	private Point2D pos;
	private long time;
	private Object data;
	
	public Cursor(int type, float x, float y) {
		this.type = type;
		pos = new Point2D(x, y);
		time = System.nanoTime();
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
	public Object getData() {
		return data;
	}
	
	public long getTime() {
		return time;
	}

	public int getType() {
		return type;
	}

	public Point2D getPos() {
		return pos;
	}
}
