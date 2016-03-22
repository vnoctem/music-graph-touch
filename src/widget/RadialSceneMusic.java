package widget;

import scene.GraphicsWrapper;

public class RadialSceneMusic extends AbstractRadial {

	public RadialSceneMusic() {
		super(43, 100, 6);
	}

	@Override
	protected void drawOptions(GraphicsWrapper gw, int level, float x, float y) {
		
	}

	@Override
	protected void actionOnSelect(int selected) {
		
	}
	
	public void draw(GraphicsWrapper gw) {
		drawRadial(gw);
	}
	
}
