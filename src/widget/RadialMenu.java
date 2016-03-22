package widget;

import java.util.ArrayList;

import music.instruments.Clarinet;
import music.instruments.Guitar;
import music.instruments.Piano;
import music.instruments.Violin;
import scene.GraphicsWrapper;
import scene.Point2D;
import scene.SceneMusic;
import scene.Vector2D;

public class RadialMenu {
	private Point2D position;
	private float innerRadius;
	private float outerRadius;
	private boolean onShownMenu = false;
	private boolean onShownInstru = false;
	private int nb; // nombre d'éléments dans le menu
	private final int DISTANCE = 100;
	private int selected = -1;
	private SceneMusic sm;
	private float radiusInstru;
	
	public RadialMenu(float radiusMenu, int nb, float radiusInstru) {
		this.innerRadius = radiusMenu;
		outerRadius = innerRadius + DISTANCE;
		this.nb = nb;
		this.radiusInstru = radiusInstru;
	}
	
	public void show(float x, float y) {
		// réinitialiser pour ne pas modifier l'ancien
		sm = null;
		position = new Point2D(x, y);
		onShownMenu = true;
	}
	
	public boolean isShown() {
		return onShownMenu || onShownInstru;
	}
	
	public SceneMusic close() {
		onShownMenu = false;
		onShownInstru = false;
		
		return sm;
	}
	
	private int findSection(Point2D user, float distance) {
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

	private void selectInstrument(float x, float y) {
		Point2D user = new Point2D(x, y);
		float distance = position.distance(user);
		
		// trouver la section
		selected = findSection(user, distance);
		
		// si rendu au bord du menu
		if (selected != -1 && distance >= outerRadius - 20) {
			switch (selected) {
				case 0:
					sm = new SceneMusic(new Piano(), radiusInstru);
					break;
				case 1:
					sm = new SceneMusic(new Guitar(), radiusInstru);
					break;
				case 2:
					sm = new SceneMusic(new Violin(), radiusInstru);
					break;
				case 3:
					sm = new SceneMusic(new Clarinet(), radiusInstru);
					break;
			}
			
			onShownMenu = false;
			onShownInstru = true;
		}
	}
	
	public void onMove(float x, float y) {
		// laisser sélectionner l'instrument
		if (onShownMenu)
			selectInstrument(x, y);
		
		// faire suivre le composant graphique à l'utilisateur
		if (onShownInstru)
			sm.setPosition(new Point2D(x, y));
	}

	public void draw(GraphicsWrapper gw) {
		if (onShownMenu) {
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
							gw.setColor(0.7f,0.7f,0f);
						else
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

						// Créer l'instrument
						switch (level) {
							case 1:
								new Piano().drawIcon(gw, central.x(), central.y(), 0.4f, 0.4f);
								break;
							case 2:
								new Guitar().drawIcon(gw, central.x(), central.y(), 0.4f, 0.4f);
								break;
							case 3:
								new Violin().drawIcon(gw, central.x(), central.y(), 0.4f, 0.4f);
								break;
							case 4:
								new Clarinet().drawIcon(gw, central.x(), central.y(), 0.4f, 0.4f);
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
		
		if (onShownInstru) {
			sm.draw(gw);
		}
	}
}
