package music;

public class MusicNote {
	
	private int key;
	private int noteLength;
	
	
	
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