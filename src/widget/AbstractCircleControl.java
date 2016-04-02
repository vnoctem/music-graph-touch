package widget;

import scene.GraphicsWrapper;
import scene.Point2D;
import scene.Vector2D;

public abstract class AbstractCircleControl extends AbstractControl {
	
	private float[] colorIcon = new float[]{0f, 0.5f, 0f};
	private String label;
	protected float value;

	public AbstractCircleControl(float x, float y, float radius, float value, float[] color, String label) {
		super(x, y, radius, color);
		this.value = value;
		this.label = label;
	}
	
	public void adjust(float x, float y, Point2D panelPos) {
		Vector2D v = Point2D.diff(new Point2D(x, y), new Point2D(position.x() + panelPos.x(), position.y() + panelPos.y()));
		value = (float) Math.toDegrees(v.angle());
	}
	
	public double getValue() {
		return value / 360;
	}
	
	public void draw(GraphicsWrapper gw) {
		super.draw(gw);
		
		gw.setColor(1, 1, 1);
		int gapHoriz = 20;
		int gapVerti = 15;
		int gapText = 8;
		gw.drawString(position.x() - radius - gapHoriz, position.y() - radius, label);
		gw.drawString(position.x() - radius - gapHoriz + gapText, position.y() - radius + gapVerti, String.valueOf((int)getValue()));
		
		gw.setColor(colorIcon[0], colorIcon[1], colorIcon[2]);
		gw.drawPartOfCircle(position.x(), position.y(), radius, 0, (int)value, true);
		gw.setColor(1, 1, 1);
	}
	
}
