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
import scene.MultitouchFramework;
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
	private transient RadialMusicVertex menuMV;
	
	// panneau pour jouer des sons
	private ArrayList<SoundBoard> lSb = new ArrayList<SoundBoard>();
	
	// liste des composant graphique et musical
	private ArrayList<MusicVertex> lMv = new ArrayList<MusicVertex>(); 
	private transient MusicVertex selectedMV = null;
	private MusicVertex startMV = null; // noeud de départ
	private transient int selectedSB;
	private transient boolean playingWholeScene = false;
	private transient Thread updateThread = null;
	
	private transient MusicSequenceBuilder seqBuilder;
	private transient MusicSequencePlayer msp;
	private int channelCounter = 0;
	
	private transient MultitouchFramework mf;
	
	private transient int index = 0;
	
	public Application(MultitouchFramework mf) {
		this.mf = mf;
	}
	
	public void setMultitouchFramework(MultitouchFramework mf) {
		this.mf = mf;
	}
	
	public MultitouchFramework getMF() {
		return mf;
	}
	
	public void draw(GraphicsWrapper gw, MultitouchFramework mf) {
		// tous les composants graphiques et musicaux
		for (MusicVertex mv : lMv) {
			mv.draw(gw);
		}
		
		// panneau de son
		if (!lSb.isEmpty()) {
			for (SoundBoard sb : lSb) {
				sb.draw(gw);
			}
		}
		
		// menu des composants
		if (menuMV != null)
			menuMV.draw(gw);
		
		// menu
		// doit toujours être la dernière à dessiner pour qu'il soit toujours par dessus de tout
		if (menu != null)
			menu.draw(gw);
		
		// si en train de jouer 
		// réappeler lui-même pour faire l'animation
		if (playingWholeScene && updateThread == null && seqBuilder != null) {
			update(gw, mf);
		}
	}
	
	private void update(GraphicsWrapper gw, MultitouchFramework mf) {
		updateThread = new Thread() {
			public void run() {
				seqBuilder.sortTimedMVByTimePosition();
				// continuer jusqu'à tant que c'est fini (thread tué)
				while (true) {
					try {
						if (index > 0 && index < seqBuilder.getLTimedMV().size()) {
							System.out.println(seqBuilder.getLTimedMV().get(index).getInstrument().getName() + " - timePosition actuel : " + seqBuilder.getLTimedMV().get(index).getTimePosition());
							System.out.println(seqBuilder.getLTimedMV().get(index).getInstrument().getName() + " - timePosition précédent : " + seqBuilder.getLTimedMV().get(index - 1).getTimePosition());
							System.out.println("différence temps : " + (seqBuilder.getLTimedMV().get(index).getTimePosition() - seqBuilder.getLTimedMV().get(index - 1).getTimePosition()));
							Thread.sleep(seqBuilder.getLTimedMV().get(index).getTimePosition() - seqBuilder.getLTimedMV().get(index - 1).getTimePosition());
						}
						
						// arrêter l'animation
						if (index == seqBuilder.getLTimedMV().size()) {
							// si la séquence a fini
							if (!msp.getSequencer().isRunning()) {
								playingWholeScene = false;
								updateThread = null;
								for (MusicVertex mv : seqBuilder.getLTimedMV()) {
									mv.setPlaying(false);
									mv.setProgress(0);
									// redessiner
									mf.requestRedraw();
								}
								Thread.currentThread().interrupt();
								index = 0;
								return;
							}
						} else {
							// update l'interface
							seqBuilder.getLTimedMV().get(index).setPlaying(true);
							new Thread() {
								private long startTime = System.nanoTime();
								private int noMV = index;
								
								public void run() {
									while (true) {
										try {
											double milli = (double) (System.nanoTime() - startTime) / 1000000l;
											
											// arrête l'animation si fini
											if (milli > seqBuilder.getLTimedMV().get(noMV).getMusicSample().getDuration()) {
												Thread.currentThread().interrupt();
												return;
											} else {
												// pour pas trop faire travailler le CPU
												Thread.sleep(50);
											}
											
											// update
											// pour gérer les confits entre les threads
											if (playingWholeScene) {
												seqBuilder.getLTimedMV().get(noMV).setProgress(milli / seqBuilder.getLTimedMV().get(noMV).getMusicSample().getDuration());
												mf.requestRedraw();
											} else { // si changé l'état, on veut probablement vouloir s'arrêter
												Thread.currentThread().interrupt();
												return;
											}
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}
							}.start(); 
							index++;
						}
						
						// redessiner
						mf.requestRedraw();
					} catch (InterruptedException e) {
						// si interrompu, réinitialise tout
						playingWholeScene = false;
						updateThread = null;
						Thread.currentThread().interrupt();
						index = 0;
						return;
					}
				}
			}
		};
		updateThread.start();
	}
	
	public void add(SoundBoard sb) {
		lSb.add(sb);
		sb.setIndex(lSb.size() - 1);
	}
	
	public void resetSB(SoundBoard toClose) {
		lSb.remove(toClose);
	}
	
	public void touchDown(int id, LinkedHashMap<Integer, CursorController> cursors) {
		CursorController cCursor = cursors.get(id);
		Point2D pos = cCursor.last().getPos();
		
		// détecte s'il y a une clé cliquée
		// permet pas de faire d'autres choses avant de fermer le panneau
		if (!lSb.isEmpty()) {
			for (int i = lSb.size() - 1; i >= 0; i--) {
				SoundBoard sb = lSb.get(i);
				if (sb.onClick(this, cCursor.last())) {
					selectedSB = i;
					break;
				}
			}
		} else {
			if (cursors.size() >= 3) {
				// jouer la séquence
				if (playingWholeScene) {
					stopMusic();
					if (updateThread != null)
						updateThread.interrupt();
					for (MusicVertex mv : seqBuilder.getLTimedMV()) {
						mv.setPlaying(false);
						mv.setProgress(0);
					}
				} else {
					playMusic();
				}
			}
			else {
				if (cursors.size() == 1) {
					// si clic sur un des composants graphiques, fait l'action
					for (MusicVertex mv : lMv) {
						if (mv.isInside(pos.x(), pos.y())) {
							menuMV = new RadialMusicVertex(mv, mv.getPosition().x(), mv.getPosition().y(), lMv, this);
							selectedMV = mv;
							selectedMV.setSelected(true);
							break;
						}
					}
					
					// sinon, afficher le menu
					if (selectedMV == null) {
						menu = new RadialMenu(pos.x(), pos.y(), this);
					}
				} else { // mode de Play
					// tout effacer
					if (selectedMV != null) {
						menuMV = null;
						selectedMV.setSelected(false);
						selectedMV.doneConnect(null);
						selectedMV = null;
					}
					menu = null;
				}
			}
		}
	}
	
	// lorsqu'il y a plus de 3 clics si en train de jouer
	public void stopMusic() {
		playingWholeScene = false;
		System.out.println("STOP THE MUSIC**********************************************************");
		msp.getSequencer().stop();
	}
	
	// lorsqu'il y a plus de 3 clics
	public void playMusic() {
		// jouer seulement on n'est pas en mode de piano
		if (lSb.isEmpty()) {
			System.out.println("PLAY THE MUSIC**********************************************************");
			startMV = getStartMusicVertex();
			if (startMV != null) {
				playingWholeScene = true;
				seqBuilder = new MusicSequenceBuilder(lMv);
				
				try {
					msp = new MusicSequencePlayer(seqBuilder.buildMusicSequence(startMV, Sequence.PPQ, 250));
					seqBuilder.sortTimedMVByTimePosition();
					for (MusicVertex mv : seqBuilder.getLTimedMV()) {
						System.out.println("obj MusicVertex instrument : " + mv.getInstrument().getName() + " , timePosition : " + mv.getTimePosition());
					}
					
					msp.play();
					System.out.println("AFTER PLAY*********************************************");
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
				e.printStackTrace();
			} 
		}
	}
	
	public int getChannelCounter() {
		return channelCounter;
	}
	
	public void setChannelCounter(int channelCounter) {
		this.channelCounter = channelCounter;
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
		if (!lSb.isEmpty()) {
			for (int i = lSb.size() - 1; i >= 0; i--) {
				SoundBoard sb = lSb.get(i);
				if (selectedSB == i && sb.onRelease(pos.x(), pos.y(), cursors.getDuration(), cursors.first()))
					break;
			}
		} else {
			if (selectedMV != null) {
				// garder le sound board
				if (menuMV.getSB() != null) {
					lSb.add(menuMV.getSB());
					menuMV.getSB().setIndex(lSb.size() - 1);
				}
				
				// si déposé sur un autre noeud, donc relie-les
				for (MusicVertex mv : lMv) {
					if (mv.isInside(pos.x(), pos.y()) && mv != selectedMV && lMv.contains(selectedMV)) {
						menuMV = null;
						selectedMV.setSelected(false);
						selectedMV.doneConnect(mv);
						selectedMV = null;
						break;
					}
				}
				
				if (selectedMV != null) {
					menuMV = null;
					selectedMV.setSelected(false);
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
		if (!lSb.isEmpty()) {
			for (int i = lSb.size() - 1; i >= 0; i--) {
				SoundBoard sb = lSb.get(i);
				if (selectedSB == i && sb.onMove(pos.x(), pos.y())) {
					break;
				}
			}
		}
		
		if (menu != null)
			menu.onMove(pos.x(), pos.y());
		
		if (menuMV != null)
			menuMV.onMove(pos.x(), pos.y());
	}
}
