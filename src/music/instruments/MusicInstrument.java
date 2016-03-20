package music.instruments;

import scene.GraphicsWrapper;

import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import music.MusicSequencer;

public abstract class MusicInstrument {
	
	// les paramètres qui définissent l'instrument
	private int bank = 0;
	private int program = 0;
	
	private int velocity; // entre 0 et 127, la rapidité avec laquelle la touche a été enfoncée
	private int duration; // durée des notes jouées
	
	private ArrayList<Sequence> samples; // les patrons de musique
	
	public Sequence getSample(int index) {
		return samples.get(index);
	}
	
	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}
	
	public int getVelocity() {
		return velocity;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public int getDuration() {
		return duration;
	}
	
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
	
	// Pour jouer un patron de musique dans le séquenceur
	public abstract void playSample(int sample, int channel, Sequencer sequencer);
	
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
    	 final int[  ] offsets = {  // add these amounts to the base value
		        // A   B  C  D  E  F  G
		          -4, -2, 0, 1, 3, 5, 7  
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

	public abstract void drawIcon(GraphicsWrapper gw, float transX, float transY, float scaleX, float scaleY);
}
