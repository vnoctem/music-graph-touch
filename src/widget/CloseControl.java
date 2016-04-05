package widget;

import app.Application;
import scene.GraphicsWrapper;

public class CloseControl extends AbstractControl {
	
	private float[] colorIcon = new float[]{0.5f, 0.5f, 0.5f};

	public CloseControl(float x, float y, float radius, float[] color) {
		super(x, y, radius, color);
	}
	
	public void close(Application app, SoundBoard toClose) {
		app.resetSB(toClose);
	}
	
	public void draw(GraphicsWrapper gw) {
		super.draw(gw);
		
		gw.setColor(colorIcon[0], colorIcon[1], colorIcon[2]);
			gw.setLineWidth(4);
			int length = 30;
			gw.drawLine(position.x() - length / 2, position.y() - length / 2, position.x() + length / 2, position.y() + length / 2);
			gw.drawLine(position.x() + length / 2, position.y() - length / 2, position.x() - length / 2, position.y() + length / 2);
			gw.setLineWidth(1);
		gw.setColor(1, 1, 1);
	}

}
