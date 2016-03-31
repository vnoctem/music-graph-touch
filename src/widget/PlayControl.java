package widget;

import scene.GraphicsWrapper;

public class PlayControl extends AbstractControl {
	
	private float[] colorIcon = new float[]{0.5f, 0.5f, 0.5f};
	
	public PlayControl(float x, float y, float radius, float[] color) {
		super(x, y, radius, color);
	}
	
	public void move(float x, float y, SoundBoard sb) {
		sb.setPositionWithoutChanges(x - position.x(), y - position.y());
	}
	
	public void play() {
		System.out.println("play");
	}
	
	public void draw(GraphicsWrapper gw) {
		super.draw(gw);
		
		gw.setColor(colorIcon[0], colorIcon[1], colorIcon[2]);
			int triHeight = 40;
			int triWidth = 40;
			int gap = 5;
			gw.drawTriangle( 
					position.x() - triWidth / 2 + gap, position.y() - triHeight / 2,
					position.x() - triWidth / 2 + gap, position.y() + triHeight / 2,
					position.x() + triWidth / 2 + gap, position.y()
			);
		gw.setColor(1, 1, 1);
	}

}
