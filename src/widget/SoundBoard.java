package widget;

import java.util.ArrayList;

import music.MusicNote;
import music.MusicNotePlayer;
import music.MusicSample;
import scene.GraphicsWrapper;
import scene.Point2D;
import scene.SceneMusic;

public class SoundBoard {
	private boolean shown = false;
	private int heightPerCol = 90;
	private int width = 420;
	private int volWidth = 40;
	private Point2D position;
	private ArrayList<AbstractSound> sounds;
	private int col = 4;
	
	public void show(float x, float y, SceneMusic sm) {
		int sWidth = (width - volWidth) / 4;
		int sHeight = (heightPerCol * col) / 2;
		
		MusicNotePlayer musicNotePlayer = new MusicNotePlayer(); // créer le musicNotePlayer pour jouer les notes lorsqu'elle sont touchées
		MusicSample musicSample = sm.getMusicSample(); // l'échantillon de musique qui sera créé par les notes jouées
		
		// initialiser les notes
		sounds = new ArrayList<AbstractSound>();
		sounds.add(new RecordControl(sWidth, sHeight, (as) -> {
			((RecordControl) as).play();
		}));
		sounds.add(new Sound("C", sWidth, sHeight, (as) -> {
			MusicNote musicNote = new MusicNote(60, 1000); // hauteur de la note = 60, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote); // jouer la note
			musicSample.addMusicNote(musicNote); // ajouter la note à l'échantillon
			System.out.println("C");
			// temporaire
			for (MusicNote note : musicSample.getMusicNotes()) {
				System.out.println(note.getKey());
			}
		}));
		sounds.add(new Sound("D", sWidth, sHeight, (as) -> {
			MusicNote musicNote = new MusicNote(62, 1000); // hauteur de la note = 62, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote); // jouer la note
			musicSample.addMusicNote(musicNote); // ajouter la note à l'échantillon
			System.out.println("D");
		}));
		sounds.add(new Sound("E", sWidth, sHeight, (as) -> {
			MusicNote musicNote = new MusicNote(64, 1000); // hauteur de la note = 64, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote); // jouer la note
			musicSample.addMusicNote(musicNote); // ajouter la note à l'échantillon
			System.out.println("E");
		}));
		sounds.add(new Sound("F", sWidth, sHeight, (as) -> {
			MusicNote musicNote = new MusicNote(65, 1000); // hauteur de la note = 65, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote); // jouer la note
			musicSample.addMusicNote(musicNote); // ajouter la note à l'échantillon
			System.out.println("F");
		}));
		sounds.add(new Sound("G", sWidth, sHeight, (as) -> {
			MusicNote musicNote = new MusicNote(67, 1000); // hauteur de la note = 67, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote); // jouer la note
			musicSample.addMusicNote(musicNote); // ajouter la note à l'échantillon
			System.out.println("G");
		}));
		sounds.add(new Sound("A", sWidth, sHeight, (as) -> {
			MusicNote musicNote = new MusicNote(69, 1000); // hauteur de la note = 69, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote); // jouer la note
			musicSample.addMusicNote(musicNote); // ajouter la note à l'échantillon
			System.out.println("A");
		}));
		sounds.add(new Sound("B", sWidth, sHeight, (as) -> {
			MusicNote musicNote = new MusicNote(71, 1000); // hauteur de la note = 71, durée de la note (millisecondes) = 1000
			musicNotePlayer.playMusicNote(musicNote); // jouer la note
			musicSample.addMusicNote(musicNote); // ajouter la note à l'échantillon
			System.out.println("B");
		}));
		
		// positionner les clés
		int colCount = 0;
		int rowCount = 0;
		for (int i = 0; i < sounds.size(); i++) {
			if (colCount == col) {
				colCount = 0;
				rowCount++;
			}
			
			sounds.get(i).setPosition(sWidth * colCount, sHeight * rowCount);
			colCount++;
		}
		
		shown = true;
		
		position = new Point2D(x - width / 2, y - (heightPerCol * col) / 2);
	}
	
	public void hide() {
		shown = false;
	}
	
	public boolean isShown() {
		return shown;
	}
	
	public void onClick(float x, float y) {
		for (AbstractSound s : sounds) {
			if (s.isInside(x, y, position)) {
				s.setSelect(true);
				s.performAction();
			} else {
				s.setSelect(false);
			}
		}
	}

	public void draw(GraphicsWrapper gw) {
		if (shown) {
			gw.localWorldTrans(position.x(), position.y());
				gw.setColor(0.2f, 0.2f, 0.2f);
				// le panneau
				gw.drawRect(0, 0, width, (heightPerCol * col), true);
				// volume
				gw.setColor(1, 0, 0);
				gw.drawRect(width - volWidth, - 1, volWidth, (heightPerCol * col) + 3, true);
				// chacun des boutons
				for (AbstractSound s : sounds)
					s.draw(gw);
				gw.setColor(1, 1, 1);
			gw.popMatrix();
		}
	}
}
