package widget;

import scene.Point2D;

public class OctControl extends AbstractCircleControl {
	
	private static final int LEVEL = 5;
	private int[] fixedValue = null;

	public OctControl(float x, float y, float radius, float[] color, String label, int[] fixedValue) {
		super(x, y, radius, (360 / LEVEL) * ((int) LEVEL / 2), color, label);
		this.fixedValue = fixedValue;
	}
	
	@Override
	public double getValue() {
		if (fixedValue != null) {
			return fixedValue[(int) (value / 360 * LEVEL) - 1];
		} else {
			return value / 360 * LEVEL;
		}
	}

	@Override
	public void adjust(float x, float y, Point2D panelPos) {
		super.adjust(x, y, panelPos);
		
		System.out.println("angle : " + value);
		// utilise plutôt les valeurs discrètes
		float range = 360 / LEVEL;
		float fixedLevel = value / range;
		super.value = range * ((int) fixedLevel + 1);
	}
	
	public int getOctaveFactor() {
		 switch((int)Math.round(getValue())) {
		 	case 2: return -22;
		 	case 3: return -11;
		 	case 4: return 0;
		 	case 5: return 11;
		 	case 6: return 22;
		 }
		 return 0;
	}
	
}
