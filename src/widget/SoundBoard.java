package widget;

import java.util.ArrayList;
import java.util.LinkedList;

import app.Application;
import app.Cursor;
import music.MusicNote;
import music.MusicNotePlayer;
import music.MusicSample;
import music.instruments.AbstractInstrument;

import scene.GraphicsWrapper;
import scene.Point2D;
import scene.MusicData;
import scene.MusicVertex;

public class SoundBoard {
	// entire panel
	private int width = 800;
	private int height = 400;
	// piano
	private int pianoHeight = 250;
	private int heightSharp = 180;
	private int widthSharp = 80;
	private int nbKeys = 8;
	private int nbSharpKeys = 5;
	private int controlStart = 90;
	private int gapBetweenControls = 20;
	
	private Point2D position;
	private ArrayList<Sound> sounds;
	
	// contrôle pour enregistrer
	private RecordControl recordControl;
	
	// contrôle pour jouer
	private PlayControl playControl;
	
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
	
	public SoundBoard(float x, float y, MusicVertex mv) {
		position = new Point2D(x - width / 2, y - height / 2);
		
		int keyWidth = width / nbKeys;
		
		this.mv = mv;
		musicNotePlayer = new MusicNotePlayer();
		//musicSample = new MusicSample();
		instrument = mv.getInstrument();
		
		// initialiser les actions pour chaque note
		// les actions sont associées en ordre avec ceux d'ajouter dans le panneau
		// donc même order que l'insertion des notes
		LinkedList<ISoundAction> actions = new LinkedList<ISoundAction>();
		actions.add((as, c) -> {
			System.out.println("c#");
			MusicNote musicNote = new MusicNote(61 + getOctaveFactor()); // hauteur de la note = 61, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote, instrument, (int) Math.round(((volControl.getValue() *100) / 127) * 100)); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("d#");
			MusicNote musicNote = new MusicNote(63 + getOctaveFactor()); // hauteur de la note = 63, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote, instrument, (int) Math.round(((volControl.getValue() *100) / 127) * 100)); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("f#");
			MusicNote musicNote = new MusicNote(66 + getOctaveFactor()); // hauteur de la note = 66, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote, instrument, (int) Math.round(((volControl.getValue() *100) / 127) * 100)); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("g#");
			MusicNote musicNote = new MusicNote(68 + getOctaveFactor()); // hauteur de la note = 68, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote, instrument, (int) Math.round(((volControl.getValue() *100) / 127) * 100)); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("a#");
			MusicNote musicNote = new MusicNote(70 + getOctaveFactor()); // hauteur de la note = 70, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote, instrument, (int) Math.round(((volControl.getValue() *100) / 127) * 100)); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("C");
			MusicNote musicNote = new MusicNote(60 + getOctaveFactor()); // hauteur de la note = 60, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote, instrument, (int) Math.round(((volControl.getValue() *100) / 127) * 100)); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("D");
			MusicNote musicNote = new MusicNote(62 + getOctaveFactor()); // hauteur de la note = 62, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote, instrument, (int) Math.round(((volControl.getValue() *100) / 127) * 100)); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("E");
			MusicNote musicNote = new MusicNote(64 + getOctaveFactor()); // hauteur de la note = 64, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote, instrument, (int) Math.round(((volControl.getValue() *100) / 127) * 100)); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("F");
			MusicNote musicNote = new MusicNote(65 + getOctaveFactor()); // hauteur de la note = 65, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote, instrument, (int) Math.round(((volControl.getValue() *100) / 127) * 100)); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("G");
			MusicNote musicNote = new MusicNote(67 + getOctaveFactor()); // hauteur de la note = 67, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote, instrument, (int) Math.round(((volControl.getValue() *100) / 127) * 100)); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("A");
			MusicNote musicNote = new MusicNote(69 + getOctaveFactor()); // hauteur de la note = 69, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote, instrument, (int) Math.round(((volControl.getValue() *100) / 127) * 100)); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("B");
			MusicNote musicNote = new MusicNote(71 + getOctaveFactor()); // hauteur de la note = 71, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote, instrument, (int) Math.round(((volControl.getValue() *100) / 127) * 100)); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("C2");
			
			MusicNote musicNote = new MusicNote(72 + getOctaveFactor()); // hauteur de la note = 72, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote, instrument, (int) Math.round(((volControl.getValue() *100) / 127) * 100)); // jouer la note
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
		
		// contrôles
		int gapBetweenSection = 50;
		recordControl = new RecordControl(controlStart, (height - pianoHeight) / 2, 50, new float[] {0.5f, 0.5f, 0.5f, 0.5f});
		playControl = new PlayControl(controlStart * 2 + gapBetweenControls, (height - pianoHeight) / 2, 50, new float[] {0.5f, 0.5f, 0.5f, 0.5f});
		moveControl = new MoveControl(controlStart * 3 + gapBetweenControls * 2, (height - pianoHeight) / 2, 50, new float[] {0.5f, 0.5f, 0.5f, 0.5f});
		closeControl = new CloseControl(controlStart * 4 + gapBetweenControls * 3, (height - pianoHeight) / 2, 50, new float[] {0.5f, 0.5f, 0.5f, 0.5f});
		volControl = new VolControl(controlStart * 6 + gapBetweenControls * 5 - gapBetweenSection, (height - pianoHeight) / 2, 50, new float[] {0.5f, 0.5f, 0.5f, 0.5f}, "Vol.");
		octControl = new OctControl(controlStart * 7 + gapBetweenControls * 6 - gapBetweenSection, (height - pianoHeight) / 2, 50, new float[] {0.5f, 0.5f, 0.5f, 0.5f}, "Oct.");
	}
	
	private int getOctaveFactor() {
		if (octControl.getValue() < 0.2) {
			return -22;
		} else if (octControl.getValue() >= 0.2 && octControl.getValue() < 0.4) {
			return -11;
		} else if (octControl.getValue() >= 0.4 && octControl.getValue() < 0.6) {
			return 0;
		} else if (octControl.getValue() >= 0.6 && octControl.getValue() < 0.8) {
			return 11;
		} else if (octControl.getValue() >= 0.8 && octControl.getValue() <= 1.0) {
			return 22;
		}
		return 0;
	}
	
	public void setPosition(float x, float y) {
		position = new Point2D(x - width / 2, y - height / 2);
	}
	
	public void setPositionWithoutChanges(float x, float y) {
		position = new Point2D(x, y);
	}
	
	public void onClick(Application app, Cursor c, long timeBetweenClick) {
		float x = c.getPos().x();
		float y = c.getPos().y();
		
		for (Sound s : sounds) {
			if (s.isInside(x, y, position)) {
				s.setSelected(true);
				s.performAction(c);
				
				return;
			}
		}
		
		if (recordControl.isInside(x, y, position)) {
			if (recordControl.isRecording()) {
				System.out.println("Stop recording!");
				mv.setMusicSample(musicSample); // assigner le musicSample lorqu'on arrête d'enregistrer
			} else {
				System.out.println("Start recording!");
				musicSample = new MusicSample((int) Math.round(((volControl.getValue() *100) / 127) * 100)); // faire un nouvel échantillon
			}
			recordControl.record(); // start or stop recording
		}
		
		if (playControl.isInside(x, y, position)) {
			playControl.play();
		}
		
		if (closeControl.isInside(x, y, position)) {
			closeControl.close(app);
		}
		
		if (volControl.isInside(x, y, position))
			volControl.adjust(x, y, position);
		
		if (octControl.isInside(x, y, position))
			octControl.adjust(x, y, position);
	}
	
	public void onRelease(float x, float y, long duration, Cursor cDown) {
		MusicData data = (MusicData)cDown.getData();
		data.getSound().setSelected(false);
		
		MusicNote musicNote = data.getMusicNote(); // récupérer la note qui joue
		musicNotePlayer.stopMusicNote(musicNote); // arrêter de jouer la note lorsqu'on enlève notre doigt
		
		if (recordControl.isRecording()) { // si recording
			musicNote.setNoteLength(Math.round(duration / 1000000)); // assigner la durée de la note
			musicSample.addMusicNote(musicNote); // ajouter la note à l'échantillon
			System.out.println("onRelease : note key=" + musicNote.getKey() + ", length=" + musicNote.getNoteLength());
		}
	}
	
	public void onMove(float x, float y) {
		if (moveControl.isInside(x, y, position))
			moveControl.move(x, y, this);
		
		if (volControl.isInside(x, y, position))
			volControl.adjust(x, y, position);
		
		if (octControl.isInside(x, y, position))
			octControl.adjust(x, y, position);
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
			playControl.draw(gw);
			moveControl.draw(gw);
			closeControl.draw(gw);
			volControl.draw(gw);
			octControl.draw(gw);
			
			gw.setColor(1, 1, 1);
		gw.popMatrix();
	}
}
