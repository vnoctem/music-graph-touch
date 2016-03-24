package music;

import java.util.ArrayList;

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
	
	private int ticks; // temps en ticks pour la composition (en millisecondes)
	private int velocity; // le volume de l'échantillon
	private ArrayList<MusicNote> notes; // les notes de musique de l'échantillon
	private Track track; // la track dans laquelle l'échantillon sera créé (une track par échantillon)
	
	public MusicSample() {
		notes = new ArrayList<MusicNote>();
	}
	
	public MusicSample(Sequence sequence, int velocity) {
		this.ticks = 1; // on débute à 1
		this.velocity = velocity;
		
		// créer une nouvelle track dans la séquence
		track = sequence.createTrack();
	}
	
	public void setTrack(Track track) {
		this.track = track;
	}
	
	public Track getTrack() {
		return track;
	}

	public void setStartTick(int startTick) {
		ticks = startTick;
	}
	
	public int getDuration() {
		return ticks;
	}
	
	public ArrayList<MusicNote> getMusicNotes() {
		return notes;
	}
	
	/**
	 * Ajouter une note de musique à l'échantillon
	 * @param note
	 * @param channel
	 */
	public void addMusicNote(MusicNote musicNote) {
		notes.add(musicNote);
	}
	
	/**
	 * Bâtir la track de l'échantillon au temps donné dans la séquence
	 */
	public void buildTrack(Sequence sequence, int startTick) {
		track = sequence.createTrack();
		ShortMessage on = new ShortMessage();
		ShortMessage off = new ShortMessage();
		for (MusicNote note : notes) {
			try {
				on.setMessage(ShortMessage.NOTE_ON, 0, note.getKey(), this.velocity);
				off.setMessage(ShortMessage.NOTE_OFF, 0, note.getKey(), this.velocity);
				
				track.add(new MidiEvent(on, startTick + ticks)); // message pour jouer la note
				track.add(new MidiEvent(off, startTick + ticks + note.getNoteLength())); // message pour arrêter la note
				ticks += note.getNoteLength(); // incrémenter les ticks avec la durée de la note jouée
			} catch (InvalidMidiDataException e) {
				e.printStackTrace();
			}
		}
	}
	
//	public void addMusicNote(MusicNote note, int channel) 
//			throws InvalidMidiDataException {
//		ShortMessage on = new ShortMessage();
//		on.setMessage(ShortMessage.NOTE_ON, channel, note.getKey(), this.velocity);
//		
//		ShortMessage off = new ShortMessage();
//		off.setMessage(ShortMessage.NOTE_OFF, channel, note.getKey(), this.velocity);
//		
//		track.add(new MidiEvent(on, ticks)); // jouer la note
//		track.add(new MidiEvent(off, ticks + note.getNoteLength())); // arrêter la note après une certaine durée (noteLength)
//		ticks += note.getNoteLength(); // incrémenter les ticks avec la durée de la note ajoutée
//	}
}
