package music;

import java.io.Serializable;
import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import music.instruments.AbstractInstrument;

/**
 * Un échantillon de musique créé par l'utilisateur et qui peut être joué par un Instrument (enfant de AbstractInstrument)
 * @author vince
 *
 */
public class MusicSample implements Serializable {
	
	private static final long serialVersionUID = -7222124622093166901L;
	
	private long ticks; // temps en ticks pour la composition
	private ArrayList<MusicNote> notes; // les notes de musique de l'échantillon
	private transient Track track; // la track dans laquelle l'échantillon sera créé (une track par échantillon)
	
	public MusicSample() {
		notes = new ArrayList<MusicNote>();
		System.out.println("MusicSample()");
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
	
	public long getTicksLength() {
		return ticks;
	}
	
	public ArrayList<MusicNote> getMusicNotes() {
		return notes;
	}
	
	public void printMusicNotes() {
		String notes = "Notes : ";
		for (MusicNote note : getMusicNotes()) {
			notes += note.getKey() + ", ";
		}
		if (notes.length() > 0) {
			notes = notes.substring(0, notes.length() - 2);
		}
		System.out.println(notes);
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
	 * @throws InvalidMidiDataException 
	 */
	public void buildTrack(Sequence sequence, AbstractInstrument instrument, long startTick) 
			throws InvalidMidiDataException {
		track = sequence.createTrack(); // créer la track
		ticks = 0;
		
		// modifier l'instrument qui va jouer la track
		ShortMessage instrumentMessage = new ShortMessage();
		instrumentMessage.setMessage(ShortMessage.PROGRAM_CHANGE, 0, instrument.getProgram(), instrument.getBank());
		track.add(new MidiEvent(instrumentMessage, startTick));
		startTick++; // incrémenter le tick de 1
		
		for (MusicNote note : notes) {
			try {
				ShortMessage on = new ShortMessage();
				ShortMessage off = new ShortMessage();
				System.out.println("build track note key = " + note.getKey() + ", startTick = " + startTick + ", ticks = " + 
						ticks + ", noteLength = " + note.getNoteLength()); // tempo
				
				on.setMessage(ShortMessage.NOTE_ON, 0, note.getKey(), note.getVelocity());
				off.setMessage(ShortMessage.NOTE_OFF, 0, note.getKey(), note.getVelocity());
				
				track.add(new MidiEvent(on, startTick + ticks)); // message pour jouer la note
				track.add(new MidiEvent(off, startTick + ticks + note.getNoteLength())); // message pour arrêter la note
				ticks += note.getNoteLength(); // incrémenter les ticks avec la durée de la note jouée
				
				System.out.println("sequence.getTickLength() = " + sequence.getTickLength());
			} catch (InvalidMidiDataException e) {
				e.printStackTrace();
			}
		}
	}
	
}