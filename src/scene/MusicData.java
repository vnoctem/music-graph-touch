package scene;

import widget.Sound;
import music.MusicNote;

public class MusicData {
	
	private MusicNote mn;
	private Sound s;
	
	public MusicData(MusicNote mn, Sound s) {
		this.mn = mn;
		this.s = s;
	}

	public MusicNote getMusicNote() {
		return mn;
	}

	public Sound getSound() {
		return s;
	}
}
