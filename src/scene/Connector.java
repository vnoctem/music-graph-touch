package scene;

public class Connector {
	private SceneMusic source;
	private SceneMusic target;
	private float[] color = {0.7f,0.7f,0f};
	private float length = -1;
	
	public Connector(SceneMusic source, SceneMusic target) {
		this.source = source;
		this.target = target;
	}
	
	public SceneMusic getSource() {
		return source;
	}
	
	public SceneMusic getTarget() {
		return target;
	}
	
	public float getLength() {
		return length;
	}
	
	public void draw(GraphicsWrapper gw) {
		// vecteur utilisé pour que le connnecteur commence au bord
		// même longueur pour connected et position, car supposé d'avoir les mêmes longueurs
		Point2D position = source.getPosition();
		Point2D connected = target.getPosition();
		float radius = source.getRadius();
		Vector2D v = Point2D.diff(position, connected);
		float angle = v.angle();
		float x1 = (float) (position.x() - Math.cos(angle) * radius);
		float y1 = (float) (position.y() - Math.sin(angle) * radius);
		float x2 = (float) (connected.x() + Math.cos(angle) * radius);
		float y2 = (float) (connected.y() + Math.sin(angle) * radius);
		// toujours recalculé au cas où les noeuds ont changé de place
		length = Point2D.diff(new Point2D(x1, y1), new Point2D(x2, y2)).length();
		gw.setColor(color[0],color[1],color[2]);
		gw.drawLine(x1,	y1,	x2,	y2);
		gw.setLineWidth(1);
		gw.setColor(1f,1f,1f);
	}
}
