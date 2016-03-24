package widget;

import scene.GraphicsWrapper;

public class Sound extends AbstractSound {
	private String label;
	
	public Sound(String label, float width, float height, ISoundAction sAction) {
		super(width, height, sAction);
		this.label = label;
	}
	
	public void draw(GraphicsWrapper gw) {
		super.draw(gw);
		
		gw.setColor(1, 1, 1);
		gw.drawString(position.x() + width / 2, position.y() + height / 2, label);
	}
}
