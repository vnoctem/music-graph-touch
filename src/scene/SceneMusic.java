package scene;

import music.instruments.MusicInstrument;

public class SceneMusic {
	private MusicInstrument mi;
	private Point2D position;
	private float radius;
	private float[] color = {0.7f,0.7f,0f};
	private Point2D posConnector = null; // connecteur utilisé pour esquisser
	private SceneMusic connected; // le composant connecté
	
	public SceneMusic(MusicInstrument mi, float radius) {
		this.radius = radius;
		this.mi = mi;
	}
	
	public void setPosition(Point2D position) {
		this.position = position;
	}
	
	public boolean isInside(float x, float y) {
		return position.distance(new Point2D(x, y)) <= radius;
	}
	
	public void doneConnect(SceneMusic sm) {
		posConnector = null;
		connected = sm;
		
		color[0] = 0.7f;
		color[1] = 0.7f;
		color[2] = 0f;
	}
	
	public void doneConnect() {
		posConnector = null;
		
		color[0] = 0.7f;
		color[1] = 0.7f;
		color[2] = 0f;
	}
	
	public void extendConnector(float x, float y) {
		posConnector = new Point2D(x, y);
	}
	
	public void connect(float x, float y) {
		color[0] = 0f;
		color[1] = 0f;
		color[2] = 0.8f;
	}
	
	public Point2D getPosition() {
		return position;
	}
	
	public void draw(GraphicsWrapper gw) {
		gw.setColor(color[0],color[1],color[2]);
		gw.setLineWidth(3);
		gw.drawCircle(position.x() - radius, position.y() - radius, radius);
		mi.drawIcon(gw, position.x(), position.y(), 0.4f, 0.4f);
		gw.setLineWidth(3);
		gw.setColor(1f,1f,1f);
		
		// dessiner le connecteur
		if (posConnector != null) {
			if (!isInside(posConnector.x(), posConnector.y())) {
				// vecteur utilisé pour que le connnecteur commence au bord
				// même longueur pour connected et position, car supposé d'avoir les mêmes longueurs
				Vector2D v = Point2D.diff(position, posConnector);
				float angle = v.angle();
				gw.setColor(0.7f,0.7f,0f);
				gw.drawLine(
						(float) (position.x() - Math.cos(angle) * radius), 
						(float) (position.y() - Math.sin(angle) * radius), 
						(float) (posConnector.x()), 
						(float) (posConnector.y())
				);
				gw.setLineWidth(1);
				gw.setColor(1f,1f,1f);
			}
		}
		
		// s'il y a un composant connecté
		if (connected != null) {
			// vecteur utilisé pour que le connnecteur commence au bord
			// même longueur pour connected et position, car supposé d'avoir les mêmes longueurs
			Vector2D v = Point2D.diff(position, connected.getPosition());
			float angle = v.angle();
			gw.setColor(0.7f,0.7f,0f);
			gw.drawLine(
					(float) (position.x() - Math.cos(angle) * radius), 
					(float) (position.y() - Math.sin(angle) * radius), 
					(float) (connected.getPosition().x() + Math.cos(angle) * radius), 
					(float) (connected.getPosition().y() + Math.sin(angle) * radius)
			);
			gw.setLineWidth(1);
			gw.setColor(1f,1f,1f);
		}
	}
}
