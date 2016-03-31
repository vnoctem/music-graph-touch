package widget;

import scene.GraphicsWrapper;
import scene.Point2D;
import scene.Vector2D;

public abstract class AbstractCircleControl extends AbstractControl {
	
	private float[] colorIcon = new float[]{0f, 0.5f, 0f};
	private String label;
	private float value = 180;

	public AbstractCircleControl(float x, float y, float radius, float[] color, String label) {
		super(x, y, radius, color);
		this.label = label;
	}
	
	public void adjust(float x, float y, Point2D panelPos) {
		Vector2D v = Point2D.diff(new Point2D(x, y), new Point2D(position.x() + panelPos.x(), position.y() + panelPos.y()));
		value = (float) Math.toDegrees(v.angle());
	}
	
	public double getValue() {
		return value / 360;
	}
	
	// appelé après avoir ajusté la valeur
	public abstract void done();
	
	public void draw(GraphicsWrapper gw) {
		super.draw(gw);
		
		gw.setColor(1, 1, 1);
		gw.drawString(position.x() - radius, position.y() - radius, label);
		
		gw.setColor(colorIcon[0], colorIcon[1], colorIcon[2]);
		gw.drawPartOfCircle(position.x(), position.y(), radius, 0, (int)value, true);
		gw.setColor(1, 1, 1);
	}

}
