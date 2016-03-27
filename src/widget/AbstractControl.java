package widget;

import scene.GraphicsWrapper;
import scene.Point2D;

public abstract class AbstractControl {
	protected Point2D position;
	protected float radius;
	protected float[] color;
	
	protected AbstractControl(float x, float y, float radius, float[] color) {
		this.position = new Point2D(x, y);
		this.radius = radius;
		this.color = color;
	}
	
	public void draw(GraphicsWrapper gw) {
		gw.setColor(color[0], color[1], color[2], color[3]);
		gw.drawCircle(position.x() - radius, position.y() - radius, radius, true);
		gw.setColor(1, 1, 1);
	}
	
	public boolean isInside(float x, float y, Point2D panelPos) {
		return new Point2D(position.x() + panelPos.x(), position.y() + panelPos.y()).distance(new Point2D(x, y)) <= radius;
	}
}
