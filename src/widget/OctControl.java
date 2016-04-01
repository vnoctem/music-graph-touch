package widget;

import scene.Point2D;

public class OctControl extends AbstractCircleControl {
	
	private int level = 5;
	private int[] fixedValue = null;

	public OctControl(float x, float y, float radius, float[] color, String label, int[] fixedValue) {
		super(x, y, radius, color, label);
		this.fixedValue = fixedValue;
	}
	
	@Override
	public double getValue() {
		if (fixedValue != null) {
			return fixedValue[(int) (value / 360 * level) - 1];
		} else {
			return value / 360 * level;
		}
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
