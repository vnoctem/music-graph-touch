package widget;

import scene.GraphicsWrapper;

public class SoundBoard {
	private boolean shown = false;
	private int height = 200;
	private int width = 200;
	private float x;
	private float y;
	
	public void show(float x, float y) {
		shown = true;
		
		this.x = x;
		this.y = y;
	}
	
	public void hide() {
		shown = false;
	}
	
	public boolean isShown() {
		return shown;
	}

	public void draw(GraphicsWrapper gw) {
		if (shown) {
			gw.drawRect(x, y, width, height);
		}
	}
}
