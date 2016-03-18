package scene;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import java.util.Vector;

// Voici, ci-dessous, quelques valeurs pour le "MIDI note number".
// Veuillez noter que # veut dire diese (en anglais, "sharp")
// et b veut dire bemol (en anglais, "flat").
//
// MIDI_note_number  Nom anglais  Nom francais           Remarques
// ----------------  -----------  ------------           --------------------------
//  21               A0           la                     touche la plus basse sur un piano de 88 touches
//   .               .            .                      .
//   .               .            .                      .
//   .               .            .                      .
//  57               A3           la                     frequence de 220 Hertz
//  58               A3# = B3b    la diese = si bemol
//  59               B3           si
//  60               C4           do                     "middle C"; debut de l'octave 4
//  61               C4# = D4b    do diese = re bemol
//  62               D4           re
//  63               D4# = E4b    re diese = mi bemol
//  64               E4           mi
//  65               F4           fa
//  66               F4# = G4b    fa diese = sol bemol
//  67               G4           sol
//  68               G4# = A4b    sol diese = la bemol
//  69               A4           la                     "concert A"; frequence de 440 Hertz
//  70               A4# = B4b    la diese = si bemol
//  71               B4           si
//  72               C5           do                     "concert C" = do concert; debut de l'octave 5
//  73               C5# = D5b    do diese = re bemol
//   .               .            .                      .
//   .               .            .                      .
//   .               .            .                      .
// 108               C8           do                     touche la plus haute sur un piano de 88 touches

public class SynthesizerTest {

	public static void main( String[] args ) {

		int channel = 0; // 0 is a piano, 9 is percussion, other channels are for other instruments

		int volume = 80; // entre 0 et 127
		int duration = 200; // en millisecondes

		int[] notes = new int[ 13 ];

		boolean playMajorScale = true;
		boolean playMinorScale = true;
		boolean playChromaticScale = true;

		try {
			Synthesizer synth = MidiSystem.getSynthesizer();
			synth.open();
			MidiChannel[] channels = synth.getChannels();

			// --------------------------------------
			// Jouons quelques notes.
			// Les deux parametres de la methode noteOn() sont:
			// "MIDI note number" (hauteur de la note),
			// et "velocity" (c.-a-d. volume, ou intensite).
			// Les deux parametres varient entre 0 et 127.
			channels[channel].noteOn( 60, volume ); // do
			Thread.sleep( duration );
			channels[channel].noteOff( 60 );
			channels[channel].noteOn( 62, volume ); // re
			Thread.sleep( duration );
			channels[channel].noteOff( 62 );
			channels[channel].noteOn( 64, volume ); // mi
			Thread.sleep( duration );
			channels[channel].noteOff( 64 );

			Thread.sleep( 500 );

			// --------------------------------------
			if ( playMajorScale ) {
				// Jouons une gamme majeure de do.
				notes[0] = 60; // do
				notes[1] = 62; // re
				notes[2] = 64; // mi
				notes[3] = 65; // fa
				notes[4] = 67; // sol
				notes[5] = 69; // la
				notes[6] = 71; // si
				notes[7] = 72; // do

				for ( int i = 0; i <= 7; ++i ) {
					channels[channel].noteOn( notes[i], volume );
					Thread.sleep( duration );
					channels[channel].noteOff( notes[i] );
				}
				for ( int i = 7; i >= 0; --i ) {
					channels[channel].noteOn( notes[i], volume );
					Thread.sleep( duration );
					channels[channel].noteOff( notes[i] );
				}

				Thread.sleep( 500 );
			}

			// --------------------------------------
			if ( playMinorScale ) {
				// Jouons une gamme MINEURE de do.
				notes[0] = 60; // do
				notes[1] = 62; // re
				notes[2] = 63; // re#
				notes[3] = 65; // fa
				notes[4] = 67; // sol
				notes[5] = 68; // sol#
				notes[6] = 70; // la#
				notes[7] = 72; // do

				for ( int i = 0; i <= 7; ++i ) {
					channels[channel].noteOn( notes[i], volume );
					Thread.sleep( duration );
					channels[channel].noteOff( notes[i] );
				}
				for ( int i = 7; i >= 0; --i ) {
					channels[channel].noteOn( notes[i], volume );
					Thread.sleep( duration );
					channels[channel].noteOff( notes[i] );
				}

				Thread.sleep( 500 );
			}

			// --------------------------------------
			if ( playChromaticScale ) {
				// Jouons une gamme CHROMATIQUE de do.
				notes[ 0] = 60; // do
				notes[ 1] = 61; // do#
				notes[ 2] = 62; // re
				notes[ 3] = 63; // re#
				notes[ 4] = 64; // mi
				notes[ 5] = 65; // fa
				notes[ 6] = 66; // fa#
				notes[ 7] = 67; // sol
				notes[ 8] = 68; // sol#
				notes[ 9] = 69; // la
				notes[10] = 70; // la#
				notes[11] = 71; // si
				notes[12] = 72; // do

				for ( int i = 0; i <= 12; ++i ) {
					channels[channel].noteOn( notes[i], volume );
					Thread.sleep( duration );
					channels[channel].noteOff( notes[i] );
				}
				for ( int i = 12; i >= 0; --i ) {
					channels[channel].noteOn( notes[i], volume );
					Thread.sleep( duration );
					channels[channel].noteOff( notes[i] );
				}

				Thread.sleep( 500 );
			}

			// --------------------------------------
			// Jouons un accord de do majeur ("C major chord").
			channels[channel].noteOn( 60, volume ); // do
			channels[channel].noteOn( 64, volume ); // mi
			channels[channel].noteOn( 67, volume ); // sol
			Thread.sleep( 3000 );
			channels[channel].allNotesOff();
			Thread.sleep( 500 );

			// --------------------------------------
			// Essayons la percussion.
			// Pour trouver une liste d'instruments de percussion,
			// cherchez dans google "General Midi PERCUSSION Key Map",
			// et vous allez peut-etre tomber sur
			//    http://computermusicresource.com/GM.Percussion.KeyMap.html
			channels[channel].allNotesOff();
			channel = 9; // percussion
			for ( int i = 35; i <= 81; ++i ) {
				channels[channel].noteOn( i, volume );
				Thread.sleep( duration );
				channels[channel].noteOff( i );
			}
			Thread.sleep( 500 );



			synth.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}


