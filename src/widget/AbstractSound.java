package widget;

import scene.GraphicsWrapper;
import scene.Point2D;

public abstract class AbstractSound {
	protected Point2D position;
	protected float width;
	protected float height;
	protected ISoundAction sAction;
	protected boolean select = false;
	protected float[] colorSelect = new float[] {0.5f, 0.5f, 0.5f};
	protected float[] color = new float[] {0.7f, 0.7f, 0.7f};
	
	protected AbstractSound(float width, float height, ISoundAction sAction) {
		this.width = width;
		this.height = height;
		this.sAction = sAction;
	}
	
	protected void setPosition(float x, float y) {
		position = new Point2D(x, y);
	}
	
	public void performAction() {
		sAction.action(this);
	}
	
	public void setSelect(boolean select) {
		this.select = select;
	}
	
	protected void draw(GraphicsWrapper gw) {
		// grisé la case pour la sélection
		if (select) {
			gw.setColor(colorSelect[0], colorSelect[1], colorSelect[2]);
			gw.drawRect(position.x(), position.y(), width, height, true);
		}
		gw.setColor(color[0], color[1], color[2]);
		gw.drawRect(position.x(), position.y(), width, height);
	}
	
	protected boolean isInside(float x, float y, Point2D panelPos) {
		return x < panelPos.x() + width + position.x() && x > panelPos.x() + position.x() && y < (panelPos.y() + height + position.y()) && y > panelPos.y() + position.y();
	}
}
