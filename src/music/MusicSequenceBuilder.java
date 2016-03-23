package music;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

import scene.SceneMusic;

/**
 * Pour bâtir la séquence de musique qui sera jouer par le MusicPlayer
 * @author Vincent
 *
 */
public class MusicSequenceBuilder {
	
	public MusicSequenceBuilder() {
		
	}
	
	// bâtir la séquence
	public void buildMusicSequence(SceneMusic startSM, float tempoTimingType, int timingResolution) 
			throws InvalidMidiDataException {
		Sequence ms = new Sequence(tempoTimingType, timingResolution); // music sequence
		
		
		
	}

}
