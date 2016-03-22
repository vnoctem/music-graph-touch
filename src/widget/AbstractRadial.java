package widget;

import java.util.ArrayList;

import scene.GraphicsWrapper;
import scene.Point2D;
import scene.Vector2D;

public abstract class AbstractRadial {
	protected float innerRadius;
	protected float outerRadius;
	protected int nb; // nombre d'éléments dans le menu
	protected Point2D position;
	protected boolean shown;
	protected int selected = -1;
	protected float[] color;
	private int tolerance = 30;
	
	protected AbstractRadial(float innerRadius, float outerRadius, int nb, float[] color) {
		this.innerRadius = innerRadius;
		this.outerRadius = outerRadius;
		this.nb = nb;
		this.color = color;
	}
	
	public void show(float x, float y) {
		position = new Point2D(x, y);
		shown = true;
	}
	
	public boolean isShown() {
		return shown;
	}
	
	public void hide() {
		position = null;
		shown = false;
	}
	
	protected abstract void drawOptions(GraphicsWrapper gw, int level, float x, float y);
	
	protected abstract void actionOnSelect(int selected);
	
	public void onMove(float x, float y) {
		// laisser sélectionner l'instrument
		if (shown)
			select(x, y);
	}
	
	protected int findSection(Point2D user, float distance) {
		// si dans le menu
		if (distance > innerRadius && distance < outerRadius) {
			// vecteur entre la position du menu et de l'utilisateur
			Vector2D v = Point2D.diff(user, position);
			int range = 360 / nb;
			// trouve le niveau avec l'angle
			int level = (int) Math.toDegrees(v.angle()) / range;
			
			return level;
		}
		
		// rien trouvé
		return -1;
	}
	
	protected void select(float x, float y) {
		Point2D user = new Point2D(x, y);
		float distance = position.distance(user);
		
		// trouver la section
		selected = findSection(user, distance);
		
		// si rendu au bord du menu
		if (selected != -1 && distance >= outerRadius - tolerance) {
			// fait l'action selon l'option choisi
			actionOnSelect(selected);
			
			shown = false;
			selected = -1;
		}
	}
	
	protected void drawRadial(GraphicsWrapper gw) {
		if (shown) {
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
						if (selected + 1 == level)
							gw.setColor(color[0], color[1], color[2]);
						else
							gw.setColor(0.2f,0.2f,0.2f);
						gw.drawPartOfCircle(outerPoints, innerPoints, true);
						// contour
						gw.setColor(1,1,1);
						gw.drawPartOfCircle(outerPoints, innerPoints, false);
						
						int midAngle = (condition + (int) ((360 / nb) * (level - 1))) / 2;
						Point2D central = new Point2D(
								(float) Math.cos(Math.toRadians(midAngle)) * (innerRadius + (outerRadius - innerRadius) / 2),
								(float) Math.sin(Math.toRadians(midAngle)) * (innerRadius + (outerRadius - innerRadius) / 2)
						);
		
						// dessiner chacun des options
						drawOptions(gw, level - 1, central.x(), central.y());
						
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
