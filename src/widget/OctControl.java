package widget;

import scene.Point2D;

public class OctControl extends AbstractCircleControl {
	
	private int level = 8;

	public OctControl(float x, float y, float radius, float[] color, String label) {
		super(x, y, radius, color, label);
	}

	@Override
	public void adjust(float x, float y, Point2D panelPos) {
		super.adjust(x, y, panelPos);
		
		System.out.println("angle" + value);
		// utilise plutôt les valeurs discrète
		float range = 360 / level;
		float fixedLevel = value / range;
		super.value = range * ((int) fixedLevel + 1);
	}
}
