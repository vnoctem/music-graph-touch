package widget;

import java.util.ArrayList;
import java.util.LinkedList;

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

public abstract class SoundBoard {
	// entire panel
	protected int width = 800;
	protected int height = 300;
	// piano
	protected int pianoHeight = 200;
	protected int heightSharp = 120;
	protected int widthSharp = 80;
	protected int nbKeys = 8;
	protected int nbSharpKeys = 5;
	protected int controlStart = 70;
	protected int gapBetweenControls = 15;
	protected int gapBetweenSection = -50;
	
	protected Point2D position;
	protected ArrayList<Sound> sounds;
	
	// contrôle pour enregistrer
	protected RecordControl recordControl;
	
	// contrôle pour déplacer
	protected MoveControl moveControl;
	
	// contrôle pour fermer
	protected CloseControl closeControl;
	
	// contrôle pour changer le volume
	protected VolControl volControl;
	
	// contrôle pour changer l'octave
	protected OctControl octControl;
	
	protected MusicVertex mv;
	protected MusicNotePlayer musicNotePlayer;
	protected AbstractInstrument instrument;
	protected MusicSamplePlayer musicSamplePlayer;
	// identifier le no du panneau dans application
	protected int index;
	
	// sound board
	protected SoundBoard(float x, float y, MusicVertex mv) {
		this.mv = mv;
		
		// créer le clabier
		createUI(x, y);
		
		// contrôles
		moveControl = new MoveControl(controlStart * 2 + gapBetweenControls * 2, (height - pianoHeight) / 2, 35, new float[] {0.5f, 0.5f, 0.5f, 0.5f});
		closeControl = new CloseControl(controlStart * 3 + gapBetweenControls * 3, (height - pianoHeight) / 2, 35, new float[] {0.5f, 0.5f, 0.5f, 0.5f});
		volControl = new VolControl(controlStart * 7 + gapBetweenControls * 6 - gapBetweenSection, (height - pianoHeight) / 2, 35, new float[] {0.5f, 0.5f, 0.5f, 0.5f}, "Vol.");
		octControl = new OctControl(controlStart * 8 + gapBetweenControls * 7 - gapBetweenSection, (height - pianoHeight) / 2, 35, new float[] {0.5f, 0.5f, 0.5f, 0.5f}, "Oct.", new int[] {2, 3, 4, 5, 6});
	}
	
	private void createUI(float x, float y) {
		position = new Point2D(x - width / 2, y - height / 2);
		
		int keyWidth = width / nbKeys;
		
		musicSamplePlayer = new MusicSamplePlayer();
		instrument = mv.getInstrument();
		
		// initialiser les actions pour chaque note
		// les actions sont associées en ordre avec ceux d'ajouter dans le panneau
		// donc même order que l'insertion des notes
		LinkedList<ISoundAction> actions = new LinkedList<ISoundAction>();
		actions.add((as, c) -> {
			System.out.println("C#");
			MusicNote musicNote = new MusicNote(
					61 + octControl.getOctaveFactor(), 
					(int) Math.round(((volControl.getValue()) / 127) * 100), 
					(long) Math.round((System.nanoTime() / 1000000) - recordControl.getStartTime())
			);
			musicNotePlayer.playMusicNote(musicNote, instrument); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("D#");
			MusicNote musicNote = new MusicNote(
					63 + octControl.getOctaveFactor(), 
					(int) Math.round(((volControl.getValue()) / 127) * 100), 
					(long) Math.round((System.nanoTime() / 1000000) - recordControl.getStartTime())
			);
			musicNotePlayer.playMusicNote(musicNote, instrument); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("F#");
			MusicNote musicNote = new MusicNote(
					66 + octControl.getOctaveFactor(), 
					(int) Math.round(((volControl.getValue()) / 127) * 100), 
					(long) Math.round((System.nanoTime() / 1000000) - recordControl.getStartTime())
			);
			musicNotePlayer.playMusicNote(musicNote, instrument); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("G#");
			MusicNote musicNote = new MusicNote(
					68 + octControl.getOctaveFactor(),
					(int) Math.round(((volControl.getValue()) / 127) * 100), 
					(long) Math.round((System.nanoTime() / 1000000) - recordControl.getStartTime())
			);
			musicNotePlayer.playMusicNote(musicNote, instrument); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("A#");
			MusicNote musicNote = new MusicNote(
					70 + octControl.getOctaveFactor(), 
					(int) Math.round(((volControl.getValue()) / 127) * 100), 
					(long) Math.round((System.nanoTime() / 1000000) - recordControl.getStartTime())
			);
			musicNotePlayer.playMusicNote(musicNote, instrument); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("C");
			MusicNote musicNote = new MusicNote(
					60 + octControl.getOctaveFactor(), 
					(int) Math.round(((volControl.getValue() ) / 127) * 100), 
					(long) Math.round((System.nanoTime() / 1000000) - recordControl.getStartTime())
			);
			musicNotePlayer.playMusicNote(musicNote, instrument); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("D");
			MusicNote musicNote = new MusicNote(
					62 + octControl.getOctaveFactor(), 
					(int) Math.round(((volControl.getValue() ) / 127) * 100), 
					(long) Math.round((System.nanoTime() / 1000000) - recordControl.getStartTime())
			);
			musicNotePlayer.playMusicNote(musicNote, instrument); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("E");
			MusicNote musicNote = new MusicNote(
					64 + octControl.getOctaveFactor(), 
					(int) Math.round(((volControl.getValue() ) / 127) * 100), 
					(long) Math.round((System.nanoTime() / 1000000) - recordControl.getStartTime())
			);
			musicNotePlayer.playMusicNote(musicNote, instrument); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("F");
			MusicNote musicNote = new MusicNote(
					65 + octControl.getOctaveFactor(), 
					(int) Math.round(((volControl.getValue() ) / 127) * 100), 
					(long) Math.round((System.nanoTime() / 1000000) - recordControl.getStartTime())
			);
			musicNotePlayer.playMusicNote(musicNote, instrument); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("G");
			MusicNote musicNote = new MusicNote(
					67 + octControl.getOctaveFactor(), 
					(int) Math.round(((volControl.getValue() ) / 127) * 100), 
					(long) Math.round((System.nanoTime() / 1000000) - recordControl.getStartTime())
			);
			musicNotePlayer.playMusicNote(musicNote, instrument); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("A");
			MusicNote musicNote = new MusicNote(
					69 + octControl.getOctaveFactor(), 
					(int) Math.round(((volControl.getValue() ) / 127) * 100), 
					(long) Math.round((System.nanoTime() / 1000000) - recordControl.getStartTime())
			);
			musicNotePlayer.playMusicNote(musicNote, instrument); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("B");
			MusicNote musicNote = new MusicNote(
					71 + octControl.getOctaveFactor(), 
					(int) Math.round(((volControl.getValue() ) / 127) * 100), 
					(long) Math.round((System.nanoTime() / 1000000) - recordControl.getStartTime())
			);
			musicNotePlayer.playMusicNote(musicNote, instrument); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		actions.add((as, c) -> {
			System.out.println("C2");
			MusicNote musicNote = new MusicNote(
					72 + octControl.getOctaveFactor(), 
					(int) Math.round(((volControl.getValue() ) / 127) * 100),
					(long) Math.round((System.nanoTime() / 1000000) - recordControl.getStartTime())
			);
			musicNotePlayer.playMusicNote(musicNote, instrument); // jouer la note
			c.setData(new MusicData(musicNote, as));
		});
		
		// initialiser les clés
		// commencer avec les clés de dièse
		// ils vont être détectés en premier afin de simplifier les dessins
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
	
	public boolean onClick(Application app, Cursor c) {
		float x = c.getPos().x();
		float y = c.getPos().y();
		
		for (Sound s : sounds) {
			if (s.isInside(x, y, position)) {
				s.setSelected(true);
				s.performAction(c);
				return true;
			}
		}
		
		if (recordControl != null && recordControl.isInside(x, y, position)) {
			//System.out.println("click record");
			if (recordControl.isRecording()) {
				System.out.println("Stop recording!");
				if (!recordControl.getMusicSample().getMusicNotes().isEmpty()) { // si échantillon n'est pas vide
					recordControl.getMusicSample().setChannel(mv.getChannel(), app); // assigner le bon channel au musicSample
					mv.setMusicSample(recordControl.getMusicSample()); // assigner le musicSample au noeud
				} else { // si échantillon est vide
					System.out.println("échantillon vide");
				}
			} else {
				System.out.println("Start recording!");
				recordControl.setMusicSample(new MusicSample()); // créer un nouvel échantillon
			}
			recordControl.record(); // start or stop recording sample
			return true;
		}
		
		if (subOnClick(app, c, x, y)) {
			return true;
		}
		
		if (closeControl.isInside(x, y, position)) {
			closeControl.close(app, this);
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
				recordControl.getMusicSample().addMusicNote(musicNote); // ajouter la note à l'échantillon
				System.out.println("==========================Note ajoutée=============================================");
				System.out.println("note key : " + musicNote.getKey() + ", length : " + musicNote.getNoteLength() + ", startTick : " + musicNote.getStartTick());
			}
			
			return true;
		}
		
		if (subOnRelease(x, y, duration, cDown)) {
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
		
		if (subOnMove(x, y))
			return true;
		
		return false;
	}
	
	public abstract boolean subOnClick(Application app, Cursor c, float x, float y);
	public abstract boolean subOnRelease(float x, float y, long duration, Cursor cDown);
	public abstract boolean subOnMove(float x, float y);
	public abstract void subDraw(GraphicsWrapper gw);

	public void draw(GraphicsWrapper gw) {
		gw.localWorldTrans(position.x(), position.y());
			gw.setLineWidth(3);
			gw.setColor(0.2f, 0.2f, 0.2f, 0.5f);
			// le panneau
			gw.drawRect(0, 0, width, height, true);
			// chacun des clés
			// commencer avec les dièses pour qu'ils soient par dessus
			for (int i = sounds.size() - 1; i >= 0; i--)
				sounds.get(i).draw(gw);
			
			subDraw(gw);
			
			recordControl.draw(gw);
			moveControl.draw(gw);
			closeControl.draw(gw);
			volControl.draw(gw);
			octControl.draw(gw);
			
			gw.setLineWidth(1);
			gw.setColor(1, 1, 1);
		gw.popMatrix();
	}
}
