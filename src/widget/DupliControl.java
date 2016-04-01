package widget;

import scene.GraphicsWrapper;
import app.Application;

public class DupliControl extends AbstractControl {
	
	private float[] colorIcon = new float[]{0.5f, 0.5f, 0.5f};

	public DupliControl(float x, float y, float radius, float[] color) {
		super(x, y, radius, color);
	}
	
	public void duplicate(Application app, SoundBoard sb) {
		app.add(sb);
	}
	
	public void draw(GraphicsWrapper gw) {
		super.draw(gw);
		
		gw.setColor(colorIcon[0], colorIcon[1], colorIcon[2]);
			gw.setLineWidth(4);
			int gap = 20;
			gw.drawLine(position.x(), position.y() - radius + gap, position.x(), position.y() + radius - gap);
			gw.drawLine(position.x() - radius + gap, position.y(), position.x() + radius - gap, position.y());
			gw.setLineWidth(1);
		gw.setColor(1, 1, 1);
	}
}
