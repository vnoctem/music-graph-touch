package music;

import javax.sound.midi.MidiUnavailableException;

import music.instruments.MusicInstrument;
import music.instruments.Piano;

// temporaire, pour tester les instruments
public class MusicTest {

	public static void main(String[] args) {
		MusicSynthesizer ms;
		int channel;
		
		try {
			ms = new MusicSynthesizer();
			ms.open();
			
			MusicInstrument piano = new Piano();
			ms.setInstrument(piano, 0);
			
			ms.playNote(60, piano.getVelocity(), 0);
			Thread.sleep(piano.getDuration());
			ms.stopNote(60, 0);
			
			
			
			ms.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		

	}

}
