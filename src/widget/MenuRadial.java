package widget;

import java.util.ArrayList;

import scene.GraphicsWrapper;
import scene.Point2D;

public class MenuRadial {
	private Point2D position;
	private float innerRadius;
	private float outerRadius;
	private boolean onShown = false;
	private int nb; // nombre d'éléments dans le menu
	
	public MenuRadial(float radius, int nb) {
		this.innerRadius = radius;
		outerRadius = innerRadius + 100;
		this.nb = nb;
	}
	
	public void setPosition(float x, float y) {
		position = new Point2D(x, y);
	}

	public void setOnShown(boolean onShown) {
		this.onShown = onShown;
	}

	public void draw(GraphicsWrapper gw) {
		if (onShown) {
			// pour stocker les points
			ArrayList<Point2D> outerPoints = new ArrayList<Point2D>();
			ArrayList<Point2D> innerPoints = new ArrayList<Point2D>();
			
			int level = 1; // où rendu dans les angles (quel instrument)
			for (int angle = 0; angle < 360; angle++) {
				// si l'angle est complet, dessine la partie
				if (angle + 1 == (int) ((360 / nb) * level)) {
					level++;
					
					// dessiner
					gw.setColor(0.1f,0.1f,0.1f);			
					gw.drawPartOfCircle(outerPoints, innerPoints);
					// contour
					gw.setColor(1,1,1);
					gw.drawPartOfCircleLine(outerPoints, innerPoints);
					
					outerPoints.clear();
					innerPoints.clear();
				}
				
				double rad = Math.toRadians(angle);
				double cos = Math.cos(rad);
				double sin = Math.sin(rad);
				
				// calculer les positions
				outerPoints.add(new Point2D(
						(float) (position.x() + cos * outerRadius),
						(float) (position.y() + sin * outerRadius)
				));
				innerPoints.add(new Point2D(
						(float) (position.x() + cos * innerRadius),
						(float) (position.y() + sin * innerRadius)
				));
			}
		}
	}
}
