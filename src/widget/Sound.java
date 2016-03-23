package widget;

import scene.GraphicsWrapper;

public class Sound {
	private String label;
	private float x;
	private float y;
	private float width;
	private float height;
	
	public Sound(String label, float x, float y, float width, float height) {
		this.label = label;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void draw(GraphicsWrapper gw) {
		gw.drawRect(x, y, width, height);
		gw.setColor(1, 1, 1);
		gw.drawString(x + width / 2, y + height / 2, label);
		gw.setColor(0.5f, 0.5f, 0.5f);
	}
}
