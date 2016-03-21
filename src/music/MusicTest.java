package music;


import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;

import music.instruments.Guitar;
import music.instruments.Piano;

// temporaire, pour tester les instruments
public class MusicTest {
    
	public static void main(String[] args) {
		Sequencer sequencer; // pour jouer une séquence
		Sequence seq; // structure de données contenant de l'information musicale (tracks)

		try {
			// initialiser le séquenceur et l'ouvrir
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			
			// initialiser la séquence, 16 ticks par quart de note
			seq = new Sequence(Sequence.PPQ, 16);
			
			// créer un piano et jouer un échantillon
			Piano piano = new Piano();
			piano.playSample(2, 0, sequencer, seq);
			
			// créer une guitare et joueur un échantillon
			Guitar guitar = new Guitar();
			//guitar.playSample(1, 0, sequencer, seq);
			
			
			Track[] tracks = seq.getTracks();
			System.out.println("size : " + tracks.length);
			System.out.println(sequencer.getMicrosecondLength());
			// fermer le séquenceur à la fin de la séquence
			Thread.sleep((sequencer.getMicrosecondLength() / 1000) + 1000);
			sequencer.close();
			
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
