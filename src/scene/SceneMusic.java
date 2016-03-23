package scene;

import java.util.ArrayList;

import music.instruments.AbstractInstrument;

public class SceneMusic {
	private AbstractInstrument mi;
	private Point2D position;
	private float radius;
	private float[] color = {0.7f,0.7f,0f};
	private float[] colorStart = {0.7f,0,0.7f};
	private Point2D posConnector = null; // connecteur utilisé pour esquisser
	private ArrayList<Connector> conn; // le composant connecté
	private boolean start = false;
	
	public SceneMusic(AbstractInstrument mi, float radius) {
		this.radius = radius;
		this.mi = mi;
		
		conn = new ArrayList<Connector>();
	}
	
	public float getRadius() {
		return radius;
	}
	
	public void deleteConnIfLinked(SceneMusic sm) {
		Connector toDelete = null;
		
		for (Connector c : conn) {
			if (c.getTarget() == sm) {
				toDelete = c;
				break;
			}
		}

		if (toDelete != null)
			conn.remove(toDelete);
	}
	
	public void setPosition(float x, float y) {
		this.position = new Point2D(x, y);
	}
	
	public boolean isInside(float x, float y) {
		return position.distance(new Point2D(x, y)) <= radius;
	}
	
	public void setStart() {
		this.start = !start;
	}
	
	public boolean isStart() {
		return start;
	}
	
	public void deselect() {
		color[0] = 0.7f;
		color[1] = 0.7f;
		color[2] = 0f;
	}
	
	public void select() {
		color[0] = 0f;
		color[1] = 0f;
		color[2] = 0.8f;
	}
	
	public void doneConnect(SceneMusic sm) {
		posConnector = null;
		
		if (sm != null)
			conn.add(new Connector(this, sm));
	}
	
	public void extendConnector(float x, float y) {
		posConnector = new Point2D(x, y);
	}
	
	public Point2D getPosition() {
		return position;
	}
	
	public void draw(GraphicsWrapper gw) {
		if (start)
			gw.setColor(colorStart[0],colorStart[1],colorStart[2]);
		else
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
				gw.setColor(color[0],color[1],color[2]);
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
		if (conn != null) {
			for (Connector c : conn)
				c.draw(gw);
		}
	}
}
