package widget;

import scene.GraphicsWrapper;

public class RecordControl extends AbstractSound {
	private float radius = 20; 
	private boolean play = false;
	
	public RecordControl(float width, float height, ISoundAction sAction) {
		super(width, height, sAction);
	}
	
	public void play() {
		play = !play;
	}

	@Override
	public void draw(GraphicsWrapper gw) {
		super.draw(gw);
		
		gw.setColor(1, 0, 0);
		
		if (!play)
			gw.drawCircle(position.x() + width / 2 - radius / 2, position.y() + height / 2 - radius / 2, radius / 2, true);
		else
			gw.drawRect(position.x() - radius / 2 + width / 2, position.y() - radius / 2 + height / 2, radius, radius, true);
	}

}
