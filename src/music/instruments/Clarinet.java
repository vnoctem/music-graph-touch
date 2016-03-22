package music.instruments;

import scene.GraphicsWrapper;

public class Clarinet extends AbstractInstrument {
	
	public Clarinet() {
		this.setBank(0);
		this.setProgram(71);
	}

	@Override
	public void drawIcon(GraphicsWrapper gw, float transX, float transY, float scaleX, float scaleY) {
		gw.setLineWidth(3);
		gw.localWorldTrans(transX, transY, scaleX, scaleY);
			// icônes "Clarinet"
			gw.setColor(1, 1, 1);
			// bâton
			int height = 100;
			int width = 20;
			int lineWidth = 12;
			gw.setLineWidth(lineWidth);
			gw.drawTrapeze(
					-width / 2, -height / 2,
					width / 2, -height / 2,
					width / 2, height / 2,
					-width / 2, height / 2
			);
			// boutons
			gw.setColor(0, 0, 0);
			int smallCircle = 5;
			int start = -17;
			int jump = 15;
			int buttonGap = -5;
			gw.fillCircle(-width / 2 - smallCircle - buttonGap, -smallCircle + start, smallCircle);
			gw.fillCircle(-width / 2 - smallCircle - buttonGap, -smallCircle + start + jump * 1, smallCircle);
			gw.fillCircle(-width / 2 - smallCircle - buttonGap, -smallCircle + start + jump * 2, smallCircle);
			gw.fillCircle(-width / 2 - smallCircle - buttonGap, -smallCircle + start + jump * 3, smallCircle);
			// head
			int headHeight = 20;
			int space = 5;
			int headGap = 5;
			gw.setColor(1, 1, 1);
			gw.drawTrapeze(
					-width / 2 + space, -height / 2 - headHeight - headGap,
					width / 2 - space, -height / 2 - headHeight - headGap,
					width / 2, -height / 2 - headGap,
					-width / 2, -height / 2 - headGap
			);
			// bottom
			int bottomWidth = 20;
			int bottomHeight = 50;
			int bottomGap = 5;
			gw.drawTrapeze(
					-width / 2, height / 2 + bottomGap,
					width / 2, height / 2 + bottomGap,
					width / 2 + bottomWidth / 2, height / 2 + bottomHeight / 2 + bottomGap,
					-width / 2 - bottomWidth / 2, height / 2 + bottomHeight / 2 + bottomGap
			);
			gw.setColor(1, 1, 1);
		gw.popMatrix();
		gw.setLineWidth(1);
	}

}
