package widget;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import app.Application;
import app.Cursor;
import music.MusicNote;
import music.MusicNotePlayer;
import music.MusicSample;
import music.MusicSamplePlayer;
import music.instruments.AbstractInstrument;

import scene.GraphicsWrapper;
import scene.Point2D;
import scene.MusicData;
import scene.MusicVertex;

public class SoundBoard extends Observable implements Observer {
	// entire panel
	private int width = 800;
	private int height = 300;
	// piano
	private int pianoHeight = 200;
	private int heightSharp = 120;
	private int widthSharp = 80;
	private int nbKeys = 8;
	private int nbSharpKeys = 5;
	private int controlStart = 70;
	private int gapBetweenControls = 15;
	private int gapBetweenSection = -50;
	
	private Point2D position;
	private ArrayList<Sound> sounds;
	
	// contrôle pour enregistrer
	private RecordControl recordControl;
	
	// contrôle pour jouer
	private PlayControl playControl;
	
	// contrôle pour dupliquer
	private DupliControl dupliControl;
	
	// contrôle pour déplacer
	private MoveControl moveControl;
	
	// contrôle pour fermer
	private CloseControl closeControl;
	
	// contrôle pour changer le volume
	private VolControl volControl;
	
	// contrôle pour changer l'octave
	private OctControl octControl;
	
	private MusicVertex mv;
	private MusicNotePlayer musicNotePlayer;
	private MusicSample musicSample;
	private AbstractInstrument instrument;
	private MusicSamplePlayer musicSamplePlayer;
	private int index;
	private SoundBoard duplicate;
	
	// sound board esclave
	public SoundBoard(float x, float y, MusicVertex mv, MusicSample musicSample, RecordControl recordControl) {
		this.mv = mv;
		this.musicSample = musicSample; // utilise le même
		this.recordControl = recordControl;
		
		createUI(x, y);
		
		// contrôles
		moveControl = new MoveControl(controlStart * 2 + gapBetweenControls * 2, (height - pianoHeight) / 2, 35, new float[] {0.5f, 0.5f, 0.5f, 0.5f});
		closeControl = new CloseControl(controlStart * 3 + gapBetweenControls * 3, (height - pianoHeight) / 2, 35, new float[] {0.5f, 0.5f, 0.5f, 0.5f});
		volControl = new VolControl(controlStart * 7 + gapBetweenControls * 6 - gapBetweenSection, (height - pianoHeight) / 2, 35, new float[] {0.5f, 0.5f, 0.5f, 0.5f}, "Vol.");
		octControl = new OctControl(controlStart * 8 + gapBetweenControls * 7 - gapBetweenSection, (height - pianoHeight) / 2, 35, new float[] {0.5f, 0.5f, 0.5f, 0.5f}, "Oct.", new int[] {2, 3, 4, 5, 6});
	}
	
	// sound board maître
	public SoundBoard(float x, float y, MusicVertex mv) {
		this.mv = mv;
		musicSample = new MusicSample(); // créer un nouvel échantillon
		
		createUI(x, y);
		
		// contrôles
		recordControl = new RecordControl(controlStart, (height - pianoHeight) / 2, 35, new float[] {0.5f, 0.5f, 0.5f, 0.5f});
		playControl = new PlayControl(controlStart * 2 + gapBetweenControls, (height - pianoHeight) / 2, 35, new float[] {0.5f, 0.5f, 0.5f, 0.5f});
		dupliControl = new DupliControl(controlStart * 3 + gapBetweenControls * 2, (height - pianoHeight) / 2, 35, new float[] {0.5f, 0.5f, 0.5f, 0.5f});
		moveControl = new MoveControl(controlStart * 4 + gapBetweenControls * 3, (height - pianoHeight) / 2, 35, new float[] {0.5f, 0.5f, 0.5f, 0.5f});
		closeControl = new CloseControl(controlStart * 5 + gapBetweenControls * 4, (height - pianoHeight) / 2, 35, new float[] {0.5f, 0.5f, 0.5f, 0.5f});
		volControl = new VolControl(controlStart * 7 + gapBetweenControls * 6 - gapBetweenSection, (height - pianoHeight) / 2, 35, new float[] {0.5f, 0.5f, 0.5f, 0.5f}, "Vol.");
		octControl = new OctControl(controlStart * 8 + gapBetweenControls * 7 - gapBetweenSection, (height - pianoHeight) / 2, 35, new float[] {0.5f, 0.5f, 0.5f, 0.5f}, "Oct.", new int[] {2, 3, 4, 5, 6});
	}
	
	private void createUI(float x, float y) {
		position = new Point2D(x - width / 2, y - height / 2);
		
		int keyWidth = width / nbKeys;
		
		musicNotePlayer = new MusicNotePlayer();
		instrument = mv.getInstrument();
		musicSamplePlayer = new MusicSamplePlayer();
		
		
		// initialiser les actions pour chaque note
		// les actions sont associées en ordre avec ceux d'ajouter dans le panneau
		// donc même order que l'insertion des notes
		LinkedList<ISoundAction> actions = new LinkedList<ISoundAction>();
		actions.add((as, c) -> {
			System.out.println("c#");
			MusicNote musicNote = new MusicNote(61 + octControl.getOctaveFactor(), (int) Math.round(((volControl.getValue()) / 127) * 100)); // hauteur de la note = 61, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote, instrument); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("d#");
			MusicNote musicNote = new MusicNote(63 + octControl.getOctaveFactor(), (int) Math.round(((volControl.getValue()) / 127) * 100)); // hauteur de la note = 63, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote, instrument); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("f#");
			MusicNote musicNote = new MusicNote(66 + octControl.getOctaveFactor(), (int) Math.round(((volControl.getValue()) / 127) * 100)); // hauteur de la note = 66, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote, instrument); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("g#");
			MusicNote musicNote = new MusicNote(68 + octControl.getOctaveFactor(), (int) Math.round(((volControl.getValue()) / 127) * 100)); // hauteur de la note = 68, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote, instrument); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("a#");
			MusicNote musicNote = new MusicNote(70 + octControl.getOctaveFactor(), (int) Math.round(((volControl.getValue()) / 127) * 100)); // hauteur de la note = 70, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote, instrument); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("C");
			MusicNote musicNote = new MusicNote(60 + octControl.getOctaveFactor(), (int) Math.round(((volControl.getValue() ) / 127) * 100)); // hauteur de la note = 60, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote, instrument); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("D");
			MusicNote musicNote = new MusicNote(62 + octControl.getOctaveFactor(), (int) Math.round(((volControl.getValue() ) / 127) * 100)); // hauteur de la note = 62, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote, instrument); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("E");
			MusicNote musicNote = new MusicNote(64 + octControl.getOctaveFactor(), (int) Math.round(((volControl.getValue() ) / 127) * 100)); // hauteur de la note = 64, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote, instrument); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("F");
			MusicNote musicNote = new MusicNote(65 + octControl.getOctaveFactor(), (int) Math.round(((volControl.getValue() ) / 127) * 100)); // hauteur de la note = 65, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote, instrument); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("G");
			MusicNote musicNote = new MusicNote(67 + octControl.getOctaveFactor(), (int) Math.round(((volControl.getValue() ) / 127) * 100)); // hauteur de la note = 67, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote, instrument); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("A");
			MusicNote musicNote = new MusicNote(69 + octControl.getOctaveFactor(), (int) Math.round(((volControl.getValue() ) / 127) * 100)); // hauteur de la note = 69, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote, instrument); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("B");
			MusicNote musicNote = new MusicNote(71 + octControl.getOctaveFactor(), (int) Math.round(((volControl.getValue() ) / 127) * 100)); // hauteur de la note = 71, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote, instrument); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("C2");
			MusicNote musicNote = new MusicNote(72 + octControl.getOctaveFactor(), (int) Math.round(((volControl.getValue() ) / 127) * 100)); // hauteur de la note = 72, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote, instrument); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		
		// initialiser les clés
		// commencer avec les clés de dièse
		// ils vont être détecté en premier afin de simplifier les dessins
		sounds = new ArrayList<Sound>();
		for (int i = 0; i < nbSharpKeys + 1; i++) {
			if (i == 2)
				continue;
			sounds.add(
					new Sound(widthSharp, heightSharp, 
							new float[] {0.1f, 0.1f, 0.1f, 1f},
							new float[] {0.2f, 0.2f, 0.2f, 1f},
							actions.poll()
					)
			);
			sounds.get(sounds.size() - 1).setPosition(keyWidth * i + (keyWidth - widthSharp / 2), height - pianoHeight);
		}
		// les autres clés
		for (int i = 0; i < nbKeys; i++) {
			sounds.add(
				new Sound(keyWidth, pianoHeight, 
						new float[] {0.5f, 0.5f, 0.5f, 0.5f},
						new float[] {0.7f, 0.7f, 0.7f, 0.5f},
						actions.pop()
				)
			);
			sounds.get(sounds.size() - 1).setPosition(keyWidth * i, height - pianoHeight);
		}
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public void setPosition(float x, float y) {
		position = new Point2D(x - width / 2, y - height / 2);
	}
	
	public void setPositionWithoutChanges(float x, float y) {
		position = new Point2D(x, y);
	}
	
	public boolean onClick(Application app, Cursor c, long timeBetweenClick) {
		float x = c.getPos().x();
		float y = c.getPos().y();
		
		for (Sound s : sounds) {
			if (s.isInside(x, y, position)) {
				s.setSelected(true);
				s.performAction(c);
				if (recordControl.isRecording()) {
					musicSample.addMusicNote(new MusicNote(60, timeBetweenClick, 0)); // add a note without volume to simulate a pause
				}
				return true;
			}
		}
		
		if (dupliControl != null && dupliControl.isInside(x, y, position)) {
			duplicate = new SoundBoard(x, y, mv, musicSample, recordControl);
			this.addObserver(duplicate);
			dupliControl.duplicate(app, duplicate);
			return true;
		}
		
		if (recordControl.isInside(x, y, position)) {
			if (recordControl.isRecording()) {
				System.out.println("Stop recording!");
				if (!musicSample.getMusicNotes().isEmpty()) { // si échantillon n'est pas vide
					mv.setMusicSample(musicSample); // assigner le musicSample au noeud
					musicSample.setChannel(mv.getChannel(), app); // assigner le bon channel au musicSample
				} else { // si échantillon est vide
					System.out.println("échantillon vide");
				}
			} else {
				System.out.println("Start recording!");
				musicSample = new MusicSample(); // créer un nouvel échantillon
				notifyObservers(musicSample);
			}
			recordControl.record(); // start or stop recording sample
			return true;
		}
		
		if (playControl != null && playControl.isInside(x, y, position)) {
			if (this.mv.getMusicSample() != null) {
				if (playControl.isPlaying()) {
					System.out.println("Stop playing!");
					playControl.play(); // start or stop playing sample
					app.mf.requestRedraw();
					musicSamplePlayer.stopPlaying(); // arrêter de jouer
				} else {
					System.out.println("Start playing!");
					playControl.play(); // start or stop playing sample
					app.mf.requestRedraw();
					musicSamplePlayer.playMusicSample(mv.getMusicSample(), instrument); // jouer l'échantillon
					
				}
			} else {
				System.out.println("Aucun échantillon enregistré.");
			}
			
			System.out.println("playControl playing : " + playControl.isPlaying());
			return true;
		}
		
		if (closeControl.isInside(x, y, position)) {
			closeControl.close(app, index);
			return true;
		}
		
		if (volControl.isInside(x, y, position)) {
			volControl.adjust(x, y, position);
			return true;
		}
		
		if (octControl.isInside(x, y, position)) {
			octControl.adjust(x, y, position);
			return true;
		}
		
		if (moveControl.isInside(x, y, position)) {
			return true;
		}
		
		// l'événement non consommé
		return false;
	}
	
	public boolean onRelease(float x, float y, long duration, Cursor cDown) {
		if (cDown.getData() != null) {
			MusicData data = (MusicData)cDown.getData();
			data.getSound().setSelected(false);
			
			MusicNote musicNote = data.getMusicNote(); // récupérer la note qui joue
			musicNotePlayer.stopMusicNote(musicNote); // arrêter de jouer la note lorsqu'on enlève notre doigt
			
			if (recordControl.isRecording()) { // si recording
				musicNote.setNoteLength(Math.round(duration / 1000000)); // assigner la durée de la note
				musicSample.addMusicNote(musicNote); // ajouter la note à l'échantillon
			}
			
			return true;
		}
		
		//plus besoin de bouger le nouveau panneau
		if (duplicate != null) {
			duplicate = null;
			return true;
		}
		
		return false;
	}
	
	public boolean onMove(float x, float y) {
		if (moveControl.isInside(x, y, position)) {
			moveControl.move(x, y, this);
			return true;
		}
		
		if (volControl.isInside(x, y, position)) {
			volControl.adjust(x, y, position);
			return true;
		}
		
		if (octControl.isInside(x, y, position)) {
			octControl.adjust(x, y, position);
			return true;
		}
		
		if (duplicate != null) {
			duplicate.setPosition(x, y);
			return true;
		}
		
		return false;
	}

	public void draw(GraphicsWrapper gw) {
		gw.localWorldTrans(position.x(), position.y());
			gw.setColor(0.2f, 0.2f, 0.2f, 0.5f);
			// le panneau
			gw.drawRect(0, 0, width, height, true);
			// chacun des clés
			// commencer avec les dièses pour qu'ils soient par dessus
			for (int i = sounds.size() - 1; i >= 0; i--)
				sounds.get(i).draw(gw);
			
			// contrôles
			recordControl.draw(gw);
			if (playControl != null)
				playControl.draw(gw);
			if (dupliControl != null)
				dupliControl.draw(gw);
			moveControl.draw(gw);
			closeControl.draw(gw);
			volControl.draw(gw);
			octControl.draw(gw);
			
			gw.setColor(1, 1, 1);
		gw.popMatrix();
	}

	@Override
	public void update(Observable o, Object arg) {
		musicSample = (MusicSample) arg;
		
	}
}
