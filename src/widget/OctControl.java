package widget;

public class OctControl extends AbstractCircleControl {

	public OctControl(float x, float y, float radius, float[] color, String label) {
		super(x, y, radius, color, label);
	}
	
	@Override
	public void done() {
		System.out.println("Oct : " + getValue());
	}

}
