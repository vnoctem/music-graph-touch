package widget;

public class VolControl extends AbstractCircleControl {

	public VolControl(float x, float y, float radius, float[] color, String label) {
		super(x, y, radius, color, label);
	}

	@Override
	public double getValue() {
		return  (int) (value / 360 * 100);
	}
}
