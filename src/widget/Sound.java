package widget;

import app.Cursor;
import scene.GraphicsWrapper;
import scene.Point2D;

public class Sound {
	protected Point2D position;
	protected float width;
	protected float height;
	protected ISoundAction sAction;
	protected boolean selected = false;
	protected float[] colorSelect;
	protected float[] color;
	
	protected Sound(float width, float height, float[] colorSelect, float[] color, ISoundAction sAction) {
		this.width = width;
		this.height = height;
		this.colorSelect = colorSelect;
		this.color = color;
		this.sAction = sAction;
	}
	
	protected void setPosition(float x, float y) {
		position = new Point2D(x, y);
	}
	
	public void performAction(Cursor c) {
		sAction.action(this, c);
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	protected void draw(GraphicsWrapper gw) {
		// grisé la case pour la sélection
		if (selected)
			gw.setColor(colorSelect[0], colorSelect[1], colorSelect[2], colorSelect[3]);
		else
			gw.setColor(color[0], color[1], color[2], color[3]);
		gw.drawRect(position.x(), position.y(), width, height, true);
		// ajouter bordure
		gw.setColor(0, 0, 0);
		gw.drawRect(position.x(), position.y(), width, height);
	}
	
	protected boolean isInside(float x, float y, Point2D panelPos) {
		return x < panelPos.x() + width + position.x() && x > panelPos.x() + position.x() && y < (panelPos.y() + height + position.y()) && y > panelPos.y() + position.y();
	}
}
