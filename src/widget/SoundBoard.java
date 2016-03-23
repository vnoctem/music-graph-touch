package widget;

import java.util.ArrayList;

import scene.GraphicsWrapper;

public class SoundBoard {
	private boolean shown = false;
	private int height = 180;
	private int width = 400;
	private float x;
	private float y;
	private ArrayList<Sound> sounds;
	
	public SoundBoard() {
		int sWidth = width / 4;
		int sHeight = height / 2;
		sounds = new ArrayList<Sound>();
		sounds.add(new Sound(".", 0, 0, sWidth, sHeight));
		sounds.add(new Sound("C", sWidth, 0, sWidth, sHeight));
		sounds.add(new Sound("D", sWidth * 2, 0, sWidth, sHeight));
		sounds.add(new Sound("E", sWidth * 3, 0, sWidth, sHeight));
		sounds.add(new Sound("F", 0, sHeight, sWidth, sHeight));
		sounds.add(new Sound("G", sWidth, sHeight, sWidth, sHeight));
		sounds.add(new Sound("A", sWidth * 2, sHeight, sWidth, sHeight));
		sounds.add(new Sound("B", sWidth * 3, sHeight, sWidth, sHeight));
	}
	
	public void show(float x, float y, boolean isLeft) {
		shown = true;
		
		this.x = x + (isLeft ? -width - 5 : 5);
		this.y = y - height / 2;
	}
	
	public void hide() {
		shown = false;
	}
	
	public boolean isShown() {
		return shown;
	}

	public void draw(GraphicsWrapper gw) {
		if (shown) {
			gw.localWorldTrans(x, y);
				gw.setColor(0.4f, 0.4f, 0.4f);
				// le panneau
				gw.drawRect(0, 0, width, height, true);
				gw.setColor(0.5f, 0.5f, 0.5f);
				// chacun des boutons
				for (Sound s : sounds)
					s.draw(gw);
				gw.setColor(1, 1, 1);
			gw.popMatrix();
		}
	}
}
