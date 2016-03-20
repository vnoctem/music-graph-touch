package widget;

import java.util.ArrayList;

import music.instruments.Guitar;
import music.instruments.Piano;
import scene.GraphicsWrapper;
import scene.Point2D;

public class RadialMenu {
	private Point2D position;
	private float innerRadius;
	private float outerRadius;
	private boolean onShown = false;
	private int nb; // nombre d'éléments dans le menu
	private final int DISTANCE = 100;
	
	public RadialMenu(float radius, int nb) {
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
			gw.localWorldTrans(position.x(), position.y());
				// pour stocker les points
				ArrayList<Point2D> outerPoints = new ArrayList<Point2D>();
				ArrayList<Point2D> innerPoints = new ArrayList<Point2D>();
				
				int level = 1; // où rendu dans les angles (quel instrument)
				int condition;
				Point2D firstOuter = null;
				Point2D firstInner = null;
				
				for (int angle = 0; angle < 360; angle++) {
					if (360 % nb != 0 && level == nb)
						condition = 360;
					else
						condition = (int) ((360 / nb) * level);
					
					// si l'angle est complet, dessine la partie
					if (angle + 1 == condition) {
						// ajouter à la fin pour compléter le cercle
						if (level == nb) {
							outerPoints.add(firstOuter);
							innerPoints.add(firstInner);
						}
						
						// dessiner
						gw.setColor(0.2f,0.2f,0.2f);		
						gw.drawPartOfCircle(outerPoints, innerPoints);
						// contour
						gw.setColor(1,1,1);
						gw.drawPartOfCircleLine(outerPoints, innerPoints);
						
						int midAngle = (condition + (int) ((360 / nb) * (level - 1))) / 2;
						Point2D central = new Point2D(
								(float) Math.cos(Math.toRadians(midAngle)) * (innerRadius + DISTANCE / 2),
								(float) Math.sin(Math.toRadians(midAngle)) * (innerRadius + DISTANCE / 2)
						);
						// gw.drawRect(central.x() - 5, central.y() - 5, 10, 10);
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
						
						// inutile d'exécuter le reste
						if (level == nb) {
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
							(float) (cos * outerRadius),
							(float) (sin * outerRadius)
					));
					innerPoints.add(new Point2D(
							(float) (cos * innerRadius),
							(float) (sin * innerRadius)
					));
					
					// garder la position initiale pour les ajouter à la fin pour compléter le cercle
					if (angle == 0) {
						firstOuter = outerPoints.get(0);
						firstInner = innerPoints.get(0);
					}
				}
			gw.popMatrix();
		}
	}
}
