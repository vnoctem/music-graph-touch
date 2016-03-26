package music;


import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import music.instruments.Guitar;
import music.instruments.Piano;
import music.instruments.Violin;

// temporaire, pour tester les instruments
public class MusicTest {
    
	public static void main(String[] args) {
		//MusicSequencePlayer mp = new MusicSequencePlayer();

//		try {
//			// création d'un Instrument guitare
//			Guitar guitar = new Guitar();
//			
//			// création d'un MusicSample avec volume de 64
//			MusicSample musicSample = new MusicSample(mp.getMusicSequence(), 64);
//			// ajouter des notes au MusicSample
//			musicSample.addMusicNote(new MusicNote(60, 1000));
//			musicSample.addMusicNote(new MusicNote(65, 1000));
//			musicSample.addMusicNote(new MusicNote(62, 1000));
//			musicSample.addMusicNote(new MusicNote(67, 1000));
//			// faire la guitare jouer le MusicSample
//			//guitar.playMusicSample(mp, musicSample, 0);
//			
//			
//			// création d'un Instrument violon
//			Violin violin = new Violin();
//			
//			// création d'un MusicSample2 avec volume de 64
//			MusicSample musicSample2 = new MusicSample(mp.getMusicSequence(), 64);
//			// ajouter des notes au MusicSample
//			musicSample2.addMusicNote(new MusicNote(54, 1000));
//			musicSample2.addMusicNote(new MusicNote(53, 1000));
//			musicSample2.addMusicNote(new MusicNote(50, 1000));
//			musicSample2.addMusicNote(new MusicNote(43, 1000));
//			// faire la guitare jouer le MusicSample
//			//violin.playMusicSample(mp, musicSample2, 0);
//			
//			
//			Track track1 = mp.getMusicSequence().createTrack();
//			ShortMessage on1 = new ShortMessage();
//			on1.setMessage(ShortMessage.NOTE_ON, 0, 60, 64);
//			ShortMessage off1 = new ShortMessage();
//			off1.setMessage(ShortMessage.NOTE_OFF, 0, 60, 64);
//			
//			track1.add(new MidiEvent(on1, 0));
//			track1.add(new MidiEvent(off1, 3000));
//			
//			//mp.playAndStop();
//			//mp.close();
//			
//			
//			
//			//System.out.println((mp.getSequencer().getMicrosecondLength() / 1000) + 1000);
//			//Thread.sleep((mp.getSequencer().getMicrosecondLength() / 1000) + 1000);
//			//mp.close();
//			
//		} catch (InvalidMidiDataException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
			

			

	}

}
