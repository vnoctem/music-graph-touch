package music.instruments;

import scene.GraphicsWrapper;

public class FrenchHorn extends AbstractInstrument {

	public FrenchHorn() {
		this.setBank(0);
		this.setProgram(60);
	}

	@Override
	public void drawIcon(GraphicsWrapper gw, float transX, float transY, float scaleX, float scaleY) {
		gw.setLineWidth(3);
		gw.localWorldTrans(transX, transY, scaleX, scaleY);
			// icônes "FrenchHorn"
			gw.setColor(1, 1, 1);
			// body
			int bodyRadius = 40;
			gw.drawCenteredCircle(0, 0, bodyRadius, false);
			// top
			int angle = 180;
			float topX = (float) (Math.cos(Math.toRadians(angle)) * bodyRadius);
			float topY = (float) (Math.sin(Math.toRadians(angle)) * bodyRadius);
			int topHeight = 60;
			int topWidth = 20;
			gw.drawTrapeze(
					topX - topWidth / 2, topY - topHeight,
					topX + topWidth / 2, topY - topHeight,
					topX, topY,
					topX, topY
			);
			// bottom
			float bottomX = (float) (Math.cos(Math.toRadians(90)) * bodyRadius);
			float bottomY = (float) (Math.sin(Math.toRadians(90)) * bodyRadius);
			int bottomWidth = 50;
			gw.drawLine(bottomX, bottomY, bottomX + bottomWidth, bottomY);
			gw.setColor(1, 1, 1);
			// wire
			int wireWidth = 2;
			int space = 7;
			gw.setLineWidth(wireWidth);
			gw.drawLine(bottomX - space * 2, bottomY, bottomX - space * 2, bottomY - bodyRadius);
			gw.drawLine(bottomX - space, bottomY, bottomX - space, bottomY - bodyRadius);
			gw.drawLine(bottomX + space, bottomY, bottomX + space, bottomY - bodyRadius);
			gw.drawLine(bottomX + space * 2, bottomY, bottomX + space * 2, bottomY - bodyRadius);
			gw.drawPartOfCircleLine(space + space / 2, bottomY - bodyRadius, space / 2);
			gw.drawPartOfCircleLine(-space - space / 2, bottomY - bodyRadius, space / 2);
		gw.popMatrix();
		gw.setLineWidth(1);
	}
	
}
