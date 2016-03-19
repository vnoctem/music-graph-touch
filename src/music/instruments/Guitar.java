package music.instruments;

import scene.GraphicsWrapper;

public class Guitar extends MusicInstrument {
	
	
	public Guitar() {
		this.setVelocity(80);
		this.setDuration(500);
		this.setBank(0);
		this.setProgram(25);
	}

	@Override
	public void drawIcon(GraphicsWrapper gw, float transX, float transY, float scaleX, float scaleY) {
		gw.localWorldTrans(transX, transY, scaleX, scaleY);
			// icônes "Guitare"
			gw.setColor(1, 1, 1);
			// bâton
			gw.setLineWidth(6);
			gw.drawLine(0, 0, 0, 100);
			gw.setLineWidth(2);
			gw.drawLine(-10, 10, 10, 10);
			gw.drawLine(-10, 20, 10, 20);
			// corps
			gw.drawEllipse(0, 100 - 20, 23, 18);
			gw.drawEllipse(0, 100 + 18 - 10, 30, 22);
			gw.setColor(0, 0, 0);
			gw.fillCircle(-(8 / 2) - (6 / 2), 100 - 20 - 8, 8);
			gw.setColor(0, 0, 0);
			gw.setLineWidth(4);
			gw.drawLine(-30 / 2, 100 + 18 - 10 + 5, 30 / 2, 100 + 18 - 10 + 5);
			gw.setLineWidth(2);
			gw.setColor(1, 1, 1);
		gw.popMatrix();
	}
}
