package widget;

import java.util.ArrayList;

import music.instruments.Guitar;
import music.instruments.MusicInstrument;
import music.instruments.Piano;
import scene.GraphicsWrapper;
import scene.Point2D;
import scene.Point2DUtil;
import scene.Vector2D;

public class MenuRadial {
	private Point2D position;
	private float innerRadius;
	private float outerRadius;
	private boolean onShown = false;
	private int nb; // nombre d'éléments dans le menu
	private final int DISTANCE = 100;
	
	public MenuRadial(float radius, int nb) {
		this.innerRadius = radius;
		outerRadius = innerRadius + DISTANCE;
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
					// dessiner
					gw.setColor(0.1f,0.1f,0.1f);			
					gw.drawPartOfCircle(outerPoints, innerPoints);
					// contour
					gw.setColor(1,1,1);
					gw.drawPartOfCircleLine(outerPoints, innerPoints);
					
					// trouver le point central entre les parties
					ArrayList<Point2D> polygonPoints = new ArrayList<Point2D>();
					polygonPoints.add(outerPoints.get(0));
					polygonPoints.add(outerPoints.get(outerPoints.size() - 1));
					polygonPoints.add(innerPoints.get(0));
					polygonPoints.add(innerPoints.get(innerPoints.size() - 1));
					Point2D central = Point2DUtil.computeCentroidOfPoints(polygonPoints);
					Vector2D v = Point2D.diff(
							Point2D.average(innerPoints.get(0), innerPoints.get(innerPoints.size() - 1)),
							Point2D.average(outerPoints.get(0), outerPoints.get(outerPoints.size() - 1))
					);
					v.normalized();
					// il faut soustraire une certaine distance pour tenir compte que c'est un cercle
					gw.drawRect(central.x() - v.x() / 3, central.y() - v.y() / 3, 10, 10);
					// Créer l'instrument
					switch (level) {
						case 1:
							new Piano().drawIcon(gw, central.x(), central.y(), 0.5f, 0.5f);
							break;
						case 2:
							new Guitar().drawIcon(gw, central.x(), central.y(), 0.5f, 0.5f);
							break;
						case 3:
							break;
						case 4:
							break;
					}
					
					outerPoints.clear();
					innerPoints.clear();
					
					level++;
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
