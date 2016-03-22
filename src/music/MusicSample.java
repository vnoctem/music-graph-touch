package music;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 * Un échantillon de musique créé par l'utilisateur et qui peut être joué
 * @author vince
 *
 */
public class MusicSample {
	
	private int[] musicSheet; // la partition de musique de l'échantillon
	private final int BASE_KEY = 60; // 60 = C du milieu, la note de base
	
	private int currentNote; // la note courante dans la partition
	private int ticks; // temps en ticks pour la composition
	private int velocity; // le volume de l'échantillon
	
	private Track track; // la track dans laquelle l'échantillon sera créé (une track par échantillon)
	
	private final String[] notes = {
			"C", "D", "E", "F", "G", "A", "B"
	};
	
	public MusicSample(Sequence sequence, int velocity) {
		this.currentNote = 0;
		this.ticks = 0;
		this.velocity = velocity;
		
		// créer une nouvelle track dans la séquence
		track = sequence.createTrack();
	}
	
	public void addNote(MusicNote note) {
		musicSheet[currentNote] = note.getKey();
	}
	
	public void addMidiEvent(MusicNote note, int channel) 
			throws InvalidMidiDataException {
		ShortMessage on = new ShortMessage();
		on.setMessage(ShortMessage.NOTE_ON, channel, note.getKey(), velocity);
		
		ShortMessage off = new ShortMessage();
		off.setMessage(ShortMessage.NOTE_OFF, channel, note.getKey(), velocity);
		
		track.add(new MidiEvent(on, ticks));
		track.add(new MidiEvent(on, ticks + note.getNoteLength()));
	}
	
	

}
