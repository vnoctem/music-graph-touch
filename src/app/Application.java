package app;

import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

import music.MusicSequenceBuilder;
import music.MusicSequencePlayer;
import scene.GraphicsWrapper;
import scene.Point2D;
import scene.MusicVertex;
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
	private ArrayList<MusicVertex> lMv = new ArrayList<MusicVertex>(); 
	
	private MusicVertex selectedMV = null;
	
	private MusicVertex startMV = null; // noeud de départ
	
	public void draw(GraphicsWrapper gw) {
		// tous les composants graphiques et musicals
		for (MusicVertex mv : lMv) {
			mv.draw(gw);
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
	
	public void touchDown(int id, CursorController cursors, long pauseBetweenClick) {
		Point2D pos = cursors.last().getPos();
		
		// détecte s'il y a une clé cliquée
		// permet pas de faire d'autres choses avant de fermer le panneau
		if (sb != null) {
			sb.onClick(this, cursors.last(), pauseBetweenClick);
		} else {
			// si clic sur un des composants graphiques, fait l'action
			for (MusicVertex mv : lMv) {
				if (mv.isInside(pos.x(), pos.y())) {
					menuSM = new RadialSceneMusic(mv, mv.getPosition().x(), mv.getPosition().y(), lMv);
					selectedMV = mv;
					selectedMV.select();
					break;
				}
			}
			
			// sinon, afficher le menu
			if (selectedMV == null) {
				menu = new RadialMenu(pos.x(), pos.y());
			}
		}
	}
	
	// lorsqu'il y a plus de 3 clics
	public void specialAction() {
		System.out.println("3 clicks : play the music");
		startMV = getStartMusicVertex();
		if (startMV != null) {
			MusicSequenceBuilder seqBuilder = new MusicSequenceBuilder();
			
			try {
				MusicSequencePlayer msp = new MusicSequencePlayer(seqBuilder.buildMusicSequence(startMV, Sequence.PPQ, 960));
				msp.play();
				System.out.println("Jouer musique******************************************");
				startMV.getMusicSample().printMusicNotes();
				
			} catch (InvalidMidiDataException e) {
				e.printStackTrace();
				System.out.println("Paramètres de séquence invalide.");
			}
			
		} else {
			System.out.println("Aucun noeud de départ spécifié.");
		}
	}
	
	private MusicVertex getStartMusicVertex() {
		MusicVertex startMV = null;
		for (MusicVertex mv : lMv) {
			if (mv.isStart()) {
				startMV = mv;
				break;
			}
		}
		return startMV;
	}

	public void touchUp(int id, CursorController cursors) {
		Point2D pos = cursors.last().getPos();
		
		// passer aussi la durée entre touch down et touch up
		if (sb != null) {
			sb.onRelease(pos.x(), pos.y(), cursors.getDuration(), cursors.first());
		} else {
			if (selectedMV != null) {
				// garder le sound board
				sb = menuSM.getSB();
				
				// si déposé sur un autre noeud, donc relie-les
				for (MusicVertex mv : lMv) {
					if (mv.isInside(pos.x(), pos.y()) && mv != selectedMV) {
						menuSM = null;
						selectedMV.deselect();
						selectedMV.doneConnect(mv);
						selectedMV = null;
						break;
					}
				}
				
				if (selectedMV != null) {
					menuSM = null;
					selectedMV.deselect();
					selectedMV.doneConnect(null);
					selectedMV = null;
				}
			}
			
			if (menu != null) {
				MusicVertex mv = menu.getMV();
				
				if (mv != null)
					lMv.add(mv);
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
