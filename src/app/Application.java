package app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

import music.MusicSequenceBuilder;
import music.MusicSequencePlayer;
import scene.GraphicsWrapper;
import scene.Point2D;
import scene.MusicVertex;
import widget.RadialMenu;
import widget.RadialMusicVertex;
import widget.SoundBoard;

public class Application implements Serializable {

	private static final long serialVersionUID = -2475392251654453887L;

	// un seul menu pour l'instant
	// si plusieurs menus possible (multiutilisateurs), utilise Arraylist
	private transient RadialMenu menu;
	
	// menu pour les composants graphiques
	private RadialMusicVertex menuMV;
	
	// panneau pour jouer des sons
	private SoundBoard sb;
	
	// liste des composant graphique et musical
	private ArrayList<MusicVertex> lMv = new ArrayList<MusicVertex>(); 
	
	private MusicVertex selectedMV = null;
	
	private MusicVertex startMV = null; // noeud de départ
	private transient MusicSequencePlayer msp;
	
	public void draw(GraphicsWrapper gw) {
		// tous les composants graphiques et musicals
		for (MusicVertex mv : lMv) {
			mv.draw(gw);
		}
		
		// panneau de son
		if (sb != null)
			sb.draw(gw);
		
		// menu des composants
		if (menuMV != null)
			menuMV.draw(gw);
		
		// menu
		// doit toujours être la dernière à dessiner pour qu'il soit toujours par dessus de tout
		if (menu != null)
			menu.draw(gw);
	}
	
	public void resetSB() {
		sb = null;
	}
	
	public void touchDown(int id, long pauseBetweenClick, LinkedHashMap<Integer, CursorController> cursors) {
		CursorController cCursor = cursors.get(id);
		Point2D pos = cCursor.last().getPos();
		
		// détecte s'il y a une clé cliquée
		// permet pas de faire d'autres choses avant de fermer le panneau
		if (sb != null) {
			sb.onClick(this, cCursor.last(), pauseBetweenClick);
		} else {
			if (cursors.size() == 1) {
				// si clic sur un des composants graphiques, fait l'action
				for (MusicVertex mv : lMv) {
					if (mv.isInside(pos.x(), pos.y())) {
						menuMV = new RadialMusicVertex(mv, mv.getPosition().x(), mv.getPosition().y(), lMv);
						selectedMV = mv;
						selectedMV.select();
						break;
					}
				}
				
				// sinon, afficher le menu
				if (selectedMV == null) {
					menu = new RadialMenu(pos.x(), pos.y());
				}
			} else { // mode de Play
				// tout effacer
				if (selectedMV != null) {
					menuMV = null;
					selectedMV.deselect();
					selectedMV.doneConnect(null);
					selectedMV = null;
				}
				menu = null;
			}
		}
	}
	
	// lorsqu'il y a plus de 3 clics
	public void specialAction() {
		// jouer seulement on n'est pas en mode de piano
		if (sb == null) {
			System.out.println("PLAY THE MUSIC**********************************************************");
			startMV = getStartMusicVertex();
			if (startMV != null) {
				MusicSequenceBuilder seqBuilder = new MusicSequenceBuilder(lMv);
				
				try {
					msp = new MusicSequencePlayer(seqBuilder.buildMusicSequence(startMV, Sequence.PPQ, 250));
					msp.play();
					System.out.println("AFTER PLAY();*********************************************");
					startMV.getMusicSample().printMusicNotes();
				} catch (InvalidMidiDataException e) {
					e.printStackTrace();
					System.out.println("Paramètres de séquence invalide.");
				}
				
			} else {
				System.out.println("Aucun noeud de départ spécifié.");
			}
		
			// sauvegarde la scène
			try {
				// le chemin
				File fichier =  new File("scene.ser") ;
				// sérialise
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichier));
				oos.writeObject(this);
				oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
				sb = menuMV.getSB();
				
				// si déposé sur un autre noeud, donc relie-les
				for (MusicVertex mv : lMv) {
					if (mv.isInside(pos.x(), pos.y()) && mv != selectedMV) {
						menuMV = null;
						selectedMV.deselect();
						selectedMV.doneConnect(mv);
						selectedMV = null;
						break;
					}
				}
				
				if (selectedMV != null) {
					menuMV = null;
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
		
		if (menuMV != null)
			menuMV.onMove(pos.x(), pos.y());
	}
}
