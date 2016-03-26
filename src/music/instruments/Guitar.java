package music.instruments;

import scene.GraphicsWrapper;

public class Guitar extends AbstractInstrument {
	
	public Guitar() {
		this.setBank(0);
		this.setProgram(25);
	}
	
	@Override
	public void drawIcon(GraphicsWrapper gw, float transX, float transY, float scaleX, float scaleY) {
		gw.setLineWidth(3);
		gw.localWorldTrans(transX, transY, scaleX, scaleY);
			// icônes "Guitare"
			gw.setColor(1, 1, 1);
			// bâton
			int height = 150;
			int lineWidth = 6;
			gw.setLineWidth(lineWidth);
			gw.drawLine(-lineWidth / 2, -height / 2, -lineWidth / 2, height / 2);
			gw.setLineWidth(2);
			int topWidth = 20;
			gw.drawLine(-topWidth / 2 - lineWidth / 2, height / 10 - height / 2, topWidth / 2 - lineWidth / 2, height / 10 - height / 2);
			gw.drawLine(-topWidth / 2 - lineWidth / 2, height / 5 - height / 2, topWidth / 2 - lineWidth / 2, height / 5 - height / 2);
			// corps
			int topCircleWidth = 26;
			int topCircleHeight = 18;
			gw.drawEllipse(-lineWidth / 2, height / 8, topCircleWidth, topCircleHeight, true);
			int bottomCircleWidth = 35;
			int bottomCircleHeight = 25;
			int gap = 10;
			gw.drawEllipse(-lineWidth / 2, height / 8 + topCircleHeight + bottomCircleHeight - gap, bottomCircleWidth, bottomCircleHeight, true);
			gw.setColor(0, 0, 0);
			int smallCircle = 10;
			gw.fillCircle(-lineWidth / 2 - smallCircle, height / 8 - smallCircle, smallCircle);
			int bottomWidth = bottomCircleWidth / 2;
			gw.drawLine(
					-lineWidth / 2 - bottomWidth, 
					height / 8 + topCircleHeight + bottomCircleHeight - gap + bottomCircleHeight / 2, 
					-lineWidth / 2 + bottomWidth, 
					height / 8 + topCircleHeight + bottomCircleHeight - gap + bottomCircleHeight / 2
			);
			gw.setColor(1, 1, 1);
		gw.popMatrix();
		gw.setLineWidth(1);
	}
	
}