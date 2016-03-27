package app;

import java.util.ArrayList;

import scene.GraphicsWrapper;
import scene.Point2D;
import scene.SceneMusic;
import widget.RadialMenu;
import widget.RadialSceneMusic;
import widget.SoundBoard;

public class Application {

	// un seul menu pour l'instant
	// si plusieurs menus possible (multiutilisateurs), utilise Arraylist
	private RadialMenu menu;
	
	// menu pour les composants graphiques
	private RadialSceneMusic menuSM;
	
	// panneau pour jouer des sons
	private SoundBoard sb;
	
	// liste des composant graphique et musical
	private ArrayList<SceneMusic> lSm = new ArrayList<SceneMusic>(); 
	
	private SceneMusic selectedSM = null;
	
	public void draw(GraphicsWrapper gw) {
		// tous les composants graphiques et musicals
		for (SceneMusic sm : lSm) {
			sm.draw(gw);
		}
		
		// panneau de son
		if (sb != null)
			sb.draw(gw);
		
		// menu des composants
		if (menuSM != null)
			menuSM.draw(gw);
		
		// menu
		// doit toujours être la dernière à dessiner pour qu'il soit toujours par dessus de tout
		if (menu != null)
			menu.draw(gw);
	}
	
	public void resetSB() {
		sb = null;
	}
	
	public void touchDown(int id, CursorController cursors) {
		Point2D pos = cursors.last().getPos();
		
		// détecte s'il y a une clé cliquée
		// permet pas de faire d'autres choses avant de fermer le panneau
		if (sb != null) {
			sb.onClick(pos.x(), pos.y(), this);
		} else {
			// si clic sur un des composants graphiques, fait l'action
			for (SceneMusic sm : lSm) {
				if (sm.isInside(pos.x(), pos.y())) {
					menuSM = new RadialSceneMusic(sm, sm.getPosition().x(), sm.getPosition().y(), lSm);
					selectedSM = sm;
					selectedSM.select();
					break;
				}
			}
			
			// sinon, afficher le menu
			if (selectedSM == null) {
				menu = new RadialMenu(pos.x(), pos.y());
			}
		}
	}
	
	// lorsqu'il y a plus de 3 clics
	public void specialAction() {
		System.out.println("play the music");
	}

	public void touchUp(int id, CursorController cursors) {
		Point2D pos = cursors.last().getPos();
		
		// passer aussi la durée entre touch down et touch up
		if (sb != null) {
			sb.onRelease(pos.x(), pos.y(), cursors.getDuration());
		} else {
			if (selectedSM != null) {
				// garder le sound board
				sb = menuSM.getSB();
				
				// si déposé sur un autre noeud, donc relie-les
				for (SceneMusic sm : lSm) {
					if (sm.isInside(pos.x(), pos.y()) && sm != selectedSM) {
						menuSM = null;
						selectedSM.deselect();
						selectedSM.doneConnect(sm);
						selectedSM = null;
						break;
					}
				}
				
				if (selectedSM != null) {
					menuSM = null;
					selectedSM.deselect();
					selectedSM.doneConnect(null);
					selectedSM = null;
				}
			}
			
			if (menu != null) {
				SceneMusic sm = menu.getSM();
				
				if (sm != null)
					lSm.add(sm);
				menu = null;
			}
		}
	}

	public void touchMove(int id, CursorController cursors) {
		Point2D pos = cursors.last().getPos();
		
		// pas besoin de les mettre dans un if
		// parce que dans touch down ils subissent déjà une restriction
		if (sb != null)
			sb.onMove(pos.x(), pos.y());
		
		if (menu != null)
			menu.onMove(pos.x(), pos.y());
		
		if (menuSM != null)
			menuSM.onMove(pos.x(), pos.y());
	}
}
