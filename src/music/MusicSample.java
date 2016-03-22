package music;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 * Un échantillon de musique créé par l'utilisateur et qui peut être joué par un Instrument (enfant de AbstractInstrument)
 * @author vince
 *
 */
public class MusicSample {
	
	private int[] musicSheet; // la partition de musique de l'échantillon
	private final int BASE_KEY = 60; // 60 = C du milieu, la note de base
	
	private int currentNote; // la note courante dans la partition
	private int ticks; // temps en ticks pour la composition (en millisecondes)
	private int velocity; // le volume de l'échantillon
	
	private Track track; // la track dans laquelle l'échantillon sera créé (une track par échantillon)
	
	public MusicSample(Sequence sequence, int velocity) {
		this.currentNote = 0;
		this.ticks = 1; // on débute à 1
		this.velocity = velocity;
		
		// créer une nouvelle track dans la séquence
		track = sequence.createTrack();
	}
	
	public Track getTrack() {
		return track;
	}

	/**
	 * Ajouter une note de musique dans la track du MusicSample
	 * @param note
	 * @param channel
	 * @throws InvalidMidiDataException
	 */
	public void addMusicNote(MusicNote note, int channel) 
			throws InvalidMidiDataException {
		ShortMessage on = new ShortMessage();
		on.setMessage(ShortMessage.NOTE_ON, channel, note.getKey(), this.velocity);
		
		ShortMessage off = new ShortMessage();
		off.setMessage(ShortMessage.NOTE_OFF, channel, note.getKey(), this.velocity);
		
		track.add(new MidiEvent(on, ticks)); // jouer la note
		track.add(new MidiEvent(off, ticks + note.getNoteLength())); // arrêter la note après une certaine durée (noteLength)
		ticks += note.getNoteLength(); // incrémenter les ticks avec la durée de la note ajoutée
	}
}
