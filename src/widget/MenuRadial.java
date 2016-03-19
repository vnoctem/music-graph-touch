package widget;

import scene.GraphicsWrapper;
import scene.Point2D;

public class MenuRadial {
	Point2D position;
	float radius;
	
	public MenuRadial(float radius) {
		this.radius = radius;
	}
	
	public void setPosition(float x, float y) {
		position = new Point2D(x, y);
	}
	
	public void draw(GraphicsWrapper gw) {
		gw.setColor(0.1f,0.1f,0.1f);
		gw.drawCircle(position.x() - radius, position.y() - radius, radius, true);
	}
}
