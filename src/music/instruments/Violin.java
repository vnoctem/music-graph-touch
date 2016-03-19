package music.instruments;

import scene.GraphicsWrapper;

public class Violin extends MusicInstrument {

	public Violin() {
		this.setVelocity(80);
		this.setDuration(150);
		this.setBank(0);
		this.setProgram(40);
	}

	@Override
	public void drawIcon(GraphicsWrapper gw, float transX, float transY, float scaleX, float scaleY) {
		
	}
}
