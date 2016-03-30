package music.instruments;

import scene.GraphicsWrapper;

public class Contrabass extends AbstractInstrument {

	public Contrabass() {
		this.setBank(0);
		this.setProgram(43);
	}
	
	@Override
	public void drawIcon(GraphicsWrapper gw, float transX, float transY, float scaleX, float scaleY) {
		gw.setLineWidth(3);
		gw.localWorldTrans(transX, transY, scaleX, scaleY);
			// icônes "Contrabass"
			gw.setColor(1, 1, 1);
			// bâton
			int height = 150;
			int lineWidth = 4;
			gw.setLineWidth(lineWidth);
			gw.drawLine(-lineWidth / 2, -height / 2, -lineWidth / 2, height / 2);
			gw.setLineWidth(2);
			int topWidth = 20;
			gw.drawLine(-topWidth / 2 - lineWidth / 2, height / 10 - height / 2, topWidth / 2 - lineWidth / 2, height / 10 - height / 2);
			gw.drawLine(-topWidth / 2 - lineWidth / 2, height / 5 - height / 2, topWidth / 2 - lineWidth / 2, height / 5 - height / 2);
			// corps
			int topCircleWidth = 30;
			int topCircleHeight = 25;
			int start = -6;
			gw.drawEllipse(-lineWidth / 2, start, topCircleWidth, topCircleHeight, true);
			int bottomCircleWidth = 45;
			int bottomCircleHeight = 35;
			int gap = 10;
			gw.drawEllipse(-lineWidth / 2, start + topCircleHeight + bottomCircleHeight - gap, bottomCircleWidth, bottomCircleHeight, true);
			int smallCircle = 8;
			int space = 20;
			int smallCircleStart = 10;
			gw.setColor(0.2f,0.2f,0.2f);
			gw.fillCircle(-lineWidth / 2 - smallCircle - space, smallCircleStart / 2, smallCircle);
			gw.fillCircle(-lineWidth / 2 - smallCircle + space, smallCircleStart / 2, smallCircle);
			gw.setColor(1, 1, 1);
			// wire
			gw.setLineWidth(1);
			gw.setColor(0,0,0);
			int wireHeight = 80;
			int wireSpace = 4;
			gw.drawLine(-lineWidth / 2 - wireSpace, start - topCircleHeight / 2, -lineWidth / 2 - wireSpace, start + wireHeight - topCircleHeight / 2);
			gw.drawLine(-lineWidth / 2, start - topCircleHeight / 2, -lineWidth / 2, start + wireHeight - topCircleHeight / 2);
			gw.drawLine(-lineWidth / 2 + wireSpace, start - topCircleHeight / 2, -lineWidth / 2 + wireSpace, start + wireHeight - topCircleHeight / 2);
			gw.setColor(1, 1, 1);
		gw.popMatrix();
		gw.setLineWidth(1);
	}

}
