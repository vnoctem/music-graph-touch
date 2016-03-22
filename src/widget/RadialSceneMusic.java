package widget;

import scene.GraphicsWrapper;

public class RadialSceneMusic extends AbstractRadial {

	public RadialSceneMusic() {
		super(43, 100, 6, new float[] {0, 0, 0.5f});
	}

	@Override
	protected void drawOptions(GraphicsWrapper gw, int level, float x, float y) {
		gw.localWorldTrans(x, y, 0.3f, 0.3f);
			switch (level) {
				case 0:
					drawDelete(gw);
					break;
				case 1:
					drawMove(gw);
					break;
				case 2:
					drawStart(gw);
					break;
				case 3:
					drawMenu(gw);
					break;
				case 4:
					drawConnector(gw);
					break;
				case 5:
					drawMenu(gw);
					break;
			}
		gw.popMatrix();
	}
	
	private void drawStart(GraphicsWrapper gw) {
		int width = 100;
		int triHeight = 30;
		int triWidth = 30;
		gw.drawLine(width / 2, 0, -width / 2, 0);
		// triangles
		gw.drawTriangle(
				width / 2 + triWidth, 0,
				width / 2, triHeight / 2,
				width / 2, -triHeight / 2
		);
	}
	
	private void drawDelete(GraphicsWrapper gw) {
		int width = 100;
		int height = 100;
		gw.drawLine(-width / 2, -height / 2, width / 2, height / 2);
		gw.drawLine(width / 2, -height / 2, -width / 2, height / 2);
	}
	
	private void drawMove(GraphicsWrapper gw) {
		int width = 100;
		int height = 100;
		int triHeight = 30;
		int triWidth = 30;
		gw.drawLine(0, -height / 2, 0, height / 2);
		gw.drawLine(width / 2, 0, -width / 2, 0);
		// triangles
		gw.drawTriangle( 
				-triWidth / 2, -height / 2,
				triWidth / 2, -height / 2,
				0, -height / 2 - triHeight
		);
		gw.drawTriangle( 
				triWidth / 2, height / 2,
				-triWidth / 2, height / 2,
				0, height / 2 + triHeight
		);
		gw.drawTriangle( 
				-width / 2 - triWidth, 0,
				-width / 2, -triHeight / 2,
				-width / 2, triHeight / 2
		);
		gw.drawTriangle(
				width / 2 + triWidth, 0,
				width / 2, triHeight / 2,
				width / 2, -triHeight / 2
		);
	}
	
	private void drawConnector(GraphicsWrapper gw) {
		int width = 80;
		int height = 80;
		gw.drawEllipse(0, height / 2, width / 2, height / 2, 270, 450, false);
		gw.drawEllipse(0, -height / 2, width / 2, height / 2, 90, 270, false);
	}
	
	private void drawMenu(GraphicsWrapper gw) {
		int width = 100;
		int height = 100;
		int wGap = 20;
		int hGap = 20;
		gw.drawRect(-width / 2, -height / 2, width, height);
		gw.drawLine(-width / 2 + wGap, -height / 2, -width / 2 + wGap, height / 2);
		gw.drawLine(-width / 2, -height / 2 + hGap, width / 2, -height / 2 + hGap);
	}

	@Override
	protected void actionOnSelect(int selected) {
		
	}
	
	public void draw(GraphicsWrapper gw) {
		drawRadial(gw);
	}
	
}
