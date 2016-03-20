package music.instruments;

import scene.GraphicsWrapper;

public class Piano extends MusicInstrument {
	
	
	public Piano() {
		this.setVelocity(80);
		this.setDuration(200);
		this.setBank(0);
		this.setProgram(0);
	}

	@Override
	public void drawIcon(GraphicsWrapper gw, float transX, float transY, float scaleX, float scaleY) {
		gw.setLineWidth(3);
		gw.localWorldTrans(transX, transY, scaleX, scaleY);
			// icônes "Piano"
			gw.setColor(1, 1, 1);
			// corps
			gw.drawLine(0, 0, 100, 0);
			gw.drawLine(0, 0, 0, 100);
			gw.drawLine(100, 0, 100, 100);
			gw.drawLine(0, 100, 100, 100);
			// clés
			gw.drawLine(100 / 3, 0, 100 / 3, 100);
			gw.drawLine((100 / 3) * 2, 0, (100 / 3) * 2, 100);
			gw.drawRect(100 / 3 - 10, 0, 20, 60, true);
			gw.drawRect((100 / 3) * 2 - 10, 0, 20, 60, true);
		gw.popMatrix();
		gw.setLineWidth(1);
	}
}
