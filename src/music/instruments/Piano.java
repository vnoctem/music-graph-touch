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
			int width = 100;
			int height = 100;
			// corps
			gw.drawLine(-width / 2, -height / 2, width / 2, -height / 2);
			gw.drawLine(-width / 2, -height / 2, -width / 2, height / 2);
			gw.drawLine(width / 2, -height / 2, width / 2, height / 2);
			gw.drawLine(-width / 2, height / 2, width / 2, height / 2);
			// clés
			int keyWidth = 20;
			int keyHeight = 60;
			gw.drawLine(width / 3 - width / 2, -height / 2, width / 3 - width / 2, height / 2);
			gw.drawLine(width / 3 * 2 - width / 2, -height / 2, width / 3 * 2 - width / 2, height / 2);
			gw.drawRect(width / 3 - width / 2 - keyWidth / 2, -height / 2, keyWidth, keyHeight, true);
			gw.drawRect(width / 3 * 2 - width / 2 - keyWidth / 2, -height / 2, keyWidth, keyHeight, true);
		gw.popMatrix();
		gw.setLineWidth(1);
	}
}
