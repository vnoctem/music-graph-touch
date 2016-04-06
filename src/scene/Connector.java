package scene;

import java.io.Serializable;

public class Connector implements Serializable {
	
	private static final long serialVersionUID = -3010316384353874983L;
	
	private MusicVertex[] neighbours;
	private float[] color = {0.7f,0.7f,0f};
	private float length = -1;
	
	public Connector(MusicVertex source, MusicVertex target) {
		neighbours = new MusicVertex[2];
		neighbours[0] = source;
		neighbours[1] = target;
	}
	
	public MusicVertex getTarget(MusicVertex mv) {
		return mv == neighbours[0] ? neighbours[1] : neighbours[0];
	}
	
	public float getLength() {
		return length;
	}
	
	public void draw(GraphicsWrapper gw) {
		// vecteur utilisé pour que le connnecteur commence au bord
		// même longueur pour connected et position, car supposé d'avoir les mêmes longueurs
		Point2D position = neighbours[0].getPosition();
		Point2D connected = neighbours[1].getPosition();
		float radius = neighbours[0].getRadius();
		Vector2D v = Point2D.diff(position, connected);
		float angle = v.angle();
		float x1 = (float) (position.x() - Math.cos(angle) * radius);
		float y1 = (float) (position.y() - Math.sin(angle) * radius);
		float x2 = (float) (connected.x() + Math.cos(angle) * radius);
		float y2 = (float) (connected.y() + Math.sin(angle) * radius);
		// toujours recalculé au cas où les noeuds ont changé de place
		length = Point2D.diff(new Point2D(x1, y1), new Point2D(x2, y2)).length();
		//System.out.println("longueur : " + length);
		gw.setLineWidth(3);
		gw.setColor(color[0],color[1],color[2]);
		gw.drawLine(x1,	y1,	x2,	y2);
		gw.setLineWidth(1);
		gw.setColor(1f,1f,1f);
		Point2D middle = Point2D.average(position, connected);
		gw.drawString(middle.x() + 15, middle.y() + 15, String.valueOf(Math.round(length) * 5 / 1000.0));
	}
	
}