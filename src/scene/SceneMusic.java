package scene;

import music.instruments.MusicInstrument;

public class SceneMusic {
	private MusicInstrument mi;
	private Point2D position;
	private float radius;
	
	public SceneMusic(MusicInstrument mi, float radius) {
		this.radius = radius;
		this.mi = mi;
	}
	
	public void setPosition(Point2D position) {
		this.position = position;
	}
	
	public void draw(GraphicsWrapper gw) {
		gw.setColor(0.7f,0.7f,0f);
		gw.setLineWidth(3);
		gw.drawCircle(position.x() - radius, position.y() - radius, radius);
		mi.drawIcon(gw, position.x(), position.y(), 0.5f, 0.5f);
		gw.setLineWidth(1);
		gw.setColor(1f,1f,1f);
	}
}
