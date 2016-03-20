package music;


import java.io.File;
import java.io.FileReader;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;

import music.instruments.Guitar;
import music.instruments.MusicInstrument;
import music.instruments.Piano;

// temporaire, pour tester les instruments
public class MusicTest {
    
	///////////////////////////////////////////////////////
	public static void main(String[] args) {
		Sequencer seq;
		
		

		try {
			// initialiser le séquenceur
			seq = MidiSystem.getSequencer();
			seq.open();
			
			MusicInstrument piano = new Piano();
			piano.playSample(1, 0, seq);
			
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
