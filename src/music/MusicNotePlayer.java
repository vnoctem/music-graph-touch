package music;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class MusicNotePlayer {
	
	private Synthesizer synth;
	private MidiChannel[] midiChannels;
	
	public MusicNotePlayer() {
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
			midiChannels = synth.getChannels();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void playMusicNote(MusicNote musicNote) {
		midiChannels[0].noteOn(musicNote.getKey(), musicNote.getNoteLength());
	}

}
