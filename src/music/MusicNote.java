package music;

/**
 * Une note de musique qui peut être ajoutée à un MusicSample
 * @author Vincent
 *
 */
public class MusicNote {
	
	private int key; // la hauteur de la note, entre 21 et 108 pour un piano standard
	private int noteLength; // la durée en millisecondes de la note
	
	public MusicNote(int key) {
		this.key = key;
		noteLength = 1000;
	}
	
	public MusicNote(int key, int noteLength) {
		this.key = key;
		this.noteLength = noteLength;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public int getNoteLength() {
		return noteLength;
	}

	public void setNoteLength(int noteLength) {
		this.noteLength = noteLength;
	}
}