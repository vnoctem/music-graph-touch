package music.instruments;

import scene.GraphicsWrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import music.MusicPlayer;
import music.MusicSample;


public abstract class AbstractInstrument {
	
	// les paramètres qui définissent l'instrument
	private int bank = 0;
	private int program = 0;

	public void setBank(int bank) {
		this.bank = bank;
	}
	
	public int getBank() {
		return bank;
	}
	
	public void setProgram(int program) {
		this.program = program;
	}
	
	public int getProgram() {
		return program;
	}
	
	
	// Pour jouer un échantillon de musique
	public void playMusicSample(MusicPlayer musicPlayer, MusicSample musicSample, int channel) 
			throws InvalidMidiDataException {
		// changer l'instrument pour une guitare
		ShortMessage instrument = new ShortMessage();
        instrument.setMessage(ShortMessage.PROGRAM_CHANGE, channel, getProgram(), getBank());
        musicSample.getTrack().add(new MidiEvent(instrument, 0)); // -1 pour changer l'instrument avant de jouer l'échantillon de musique
        
        //musicPlayer.getSequencer().setSequence(musicPlayer.getMusicSequence());
		//musicPlayer.playAndStop();
		musicPlayer.getMusicSequence().deleteTrack(musicSample.getTrack()); // supprimer la track une fois qu'elle est jouée
	}
	
	// Pour jouer un échantillon de musique dans le séquenceur
//	public void playSample(String filePathName, int channel, Sequencer sequencer, Sequence sequence) 
//			throws InvalidMidiDataException {
//		// créer un File à partir du filePathName
//		File f = new File(filePathName);
//		try {
//			FileReader fr = new FileReader(f); // FileReader pour lire le fichier
//			
//			// créer un char array de la taille du fichier
//			char[] notes = new char[(int)f.length()];
//			
//			fr.read(notes);// lire le fichier et le mettre dans le char array
//			fr.close(); // fermer le fichier
//			
//			// Créer une séquence et une track
//			Track track = sequence.createTrack();
//			createTrack(track, channel, notes);
//			
//			// Assigner la séquence au séquenceur et démarrer
//			sequencer.setSequence(sequence);
//			sequencer.start();
//		} catch (FileNotFoundException e) {
//			System.out.println("Le fichier " + filePathName + " est introuvable.");
//			e.printStackTrace();
//		} catch (IOException e) {
//			System.out.println("Erreur lors de la lecture de " + filePathName);
//			e.printStackTrace();
//		}
//	}
	
    /*
     * (source : http://archive.oreilly.com/pub/a/onjava/excerpt/jenut3_ch17/index1.html)
     * This method parses the specified char[  ] of notes into a Track.
     * The musical notation is the following:
     * A-G:   A named note; Add b for flat and # for sharp.
     * +:     Move up one octave. Persists.
     * -:     Move down one octave.  Persists.
     * /1:    Notes are whole notes.  Persists 'till changed
     * /2:    Half notes
     * /4:    Quarter notes
     * /n:    N can also be 8, 16, 32, 64.
     * >:     Louder.  Persists
     * <:     Softer.  Persists
     * .:     Rest. Length depends on current length setting
     * Space: Play the previous note or notes; notes not separated by spaces
     *        are played at the same time
     */
    public void createTrack(Track track, int channel, char[] notes)
        throws InvalidMidiDataException
    {
    	 final int[] offsets = {  // add these amounts to the base value
		       // A  B   C  D  E  F  G
		          9, 11, 0, 2, 4, 5, 7  
		    };

        int n = 0; // current character in notes[  ] array
        int t = 0; // time in ticks for the composition

        // These values persist and apply to all notes 'till changed
        int notelength = 16; // default to quarter notes
        int velocity = 64;   // default to middle volume
        int basekey = 60;    // 60 is middle C. Adjusted up and down by octave
        int numnotes = 0;    // How many notes in current chord?
        
        while(n < notes.length) {
            char c = notes[n++];

            if (c == '+') basekey += 12;        // increase octave
            else if (c == '-') basekey -= 12;   // decrease octave
            else if (c == '>') velocity += 16;  // increase volume;
            else if (c == '<') velocity -= 16;  // decrease volume;
            else if (c == '/') {
                char d = notes[n++];
                if (d == '2') notelength = 32;  // half note
                else if (d == '4') notelength = 16;  // quarter note
                else if (d == '8') notelength = 8;   // eighth note
                else if (d == '3' && notes[n++] == '2') notelength = 2;
                else if (d == '6' && notes[n++] == '4') notelength = 1;
                else if (d == '1') {
                    if (n < notes.length && notes[n] == '6') {
                        notelength = 4;    // 1/16th note
                    }
                    else {
                    	notelength = 64;  // whole note
                    }
                }
            }
            else if (c >= 'A' && c <= 'G') {
                int key = basekey + offsets[c - 'A'];
                if (n < notes.length) {
                    if (notes[n] == 'b') { // flat
                        key--; 
                        n++;
                    }
                    else if (notes[n] == '#') { // sharp
                        key++;
                        n++;
                    }
                }

                System.out.println("t : " + t + ", notelength : " + notelength + ", key : " + key + ", velocity : " + velocity + ", channel : " + channel);
                addNote(track, t, notelength, key, velocity, channel);
                numnotes++;
            }
            else if (c == ' ') {
                // Spaces separate groups of notes played at the same time.
                // But we ignore them unless they follow a note or notes.
                if (numnotes > 0) {
                    t += notelength;
                    numnotes = 0;
                }
            }
            else if (c == '.') { 
                // Rests are like spaces in that they force any previous
                // note to be output (since they are never part of chords)
                if (numnotes > 0) {
                    t += notelength;
                    numnotes = 0;
                }
                // Now add additional rest time
                t += notelength;
            }
        }
    }
    
 // Pour ajouter une note à la 'track' (source : http://archive.oreilly.com/pub/a/onjava/excerpt/jenut3_ch17/index1.html)
    public void addNote(Track track, int startTick, int tickLength, int key, int velocity, int channel)
        throws InvalidMidiDataException
    {
    	ShortMessage on = new ShortMessage();
        on.setMessage(ShortMessage.NOTE_ON,  channel, key, velocity);
        ShortMessage off = new ShortMessage();
        off.setMessage(ShortMessage.NOTE_OFF, channel, key, velocity);
        track.add(new MidiEvent(on, startTick));
        track.add(new MidiEvent(off, startTick + tickLength));
    }

	public abstract void drawIcon(GraphicsWrapper gw, float transX, float transY, float scaleX, float scaleY);
}
