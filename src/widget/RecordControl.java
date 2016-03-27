package widget;

import scene.GraphicsWrapper;

public class RecordControl extends AbstractControl {
	private boolean recorded = false;
	private float[] colorIcon = new float[]{1, 0, 0};
	private float radiusIcon = 30;
	
	public RecordControl(float x, float y, float radius, float[] color) {
		super(x, y, radius, color);
	}
	
	public void record() {
		recorded = !recorded;
	}

	public void draw(GraphicsWrapper gw) {
		super.draw(gw);
		
		gw.setColor(colorIcon[0], colorIcon[1], colorIcon[2]);
		if (!recorded)
			gw.drawCircle(position.x() - radiusIcon / 2, position.y() - radiusIcon / 2, radiusIcon / 2, true);
		else
			gw.drawRect(position.x() - radiusIcon / 2, position.y() - radiusIcon / 2, radiusIcon, radiusIcon, true);
		gw.setColor(1, 1, 1);
	}

}
