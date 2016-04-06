package music;

import java.io.Serializable;
import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import app.Application;
import music.instruments.AbstractInstrument;

/**
 * Un échantillon de musique créé par l'utilisateur et qui peut être joué par un Instrument (enfant de AbstractInstrument)
 * @author vince
 *
 */
public class MusicSample implements Serializable {
	
	private static final long serialVersionUID = -7222124622093166901L;
	
	private ArrayList<MusicNote> notes; // les notes de musique de l'échantillon
	private transient Track track; // la track dans laquelle l'échantillon sera créé (une track par échantillon)
	private int channel = 0; // le channel de l'échantillon
	
	public MusicSample() {
		notes = new ArrayList<MusicNote>();
	}
	
	public MusicSample(int channel) {
		notes = new ArrayList<MusicNote>();
		this.channel = channel;
	}
	
	public void setTrack(Track track) {
		this.track = track;
	}
	
	public Track getTrack() {
		return track;
	}

	public long getDuration() {
		return notes.get(notes.size() - 1).getStartTick() + notes.get(notes.size() - 1).getNoteLength();
	}
	
	public ArrayList<MusicNote> getMusicNotes() {
		return notes;
	}
	
	public void setChannel(int channel, Application app) {
		this.channel = channel;
		//System.out.println("setChannel channel : " + channel + ", channelCounter : " + app.getChannelCounter()); //vgr
	}
	
	public void printMusicNotes() {
		String notes = "Notes : ";
		for (MusicNote note : getMusicNotes()) {
			if (note.getVelocity() > 0)
				notes += note.getKey() + ", ";
		}
		if (!getMusicNotes().isEmpty()) {
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
		System.out.println("Début buildTrack===========================================");
		System.out.println("channel : " + channel);
		track = sequence.createTrack(); // créer la track
		//ticks = 0; // les ticks interne de la track commence 0
		
		// modifier l'instrument qui va jouer la track
		ShortMessage instrumentMessage = new ShortMessage();
		instrumentMessage.setMessage(ShortMessage.PROGRAM_CHANGE, channel, instrument.getProgram(), instrument.getBank());
		System.out.println("Instrument to " + instrument.getName());
		track.add(new MidiEvent(instrumentMessage, startTick));
		startTick++; // incrémenter le tick de 1 après avoir changer l'instrument
		
		for (MusicNote note : notes) { // créer un MidiEvent pour chaque note
			try {
				ShortMessage on = new ShortMessage();
				ShortMessage off = new ShortMessage();
				
				on.setMessage(ShortMessage.NOTE_ON, channel, note.getKey(), note.getVelocity());
				off.setMessage(ShortMessage.NOTE_OFF, channel, note.getKey(), note.getVelocity());
				
				track.add(new MidiEvent(on, (long)(startTick + note.getStartTick()))); // message pour jouer la note
				track.add(new MidiEvent(off, (long)(startTick + note.getStartTick() + note.getNoteLength()))); // message pour arrêter la note
				
				//System.out.println("NOTE key = " + note.getKey() + ", note startTick = " + note.getStartTick() + ", noteLength = " + note.getNoteLength() + ", volume : " + note.getVelocity());
			} catch (InvalidMidiDataException e) {
				e.printStackTrace();
			}
			
		}
		System.out.println("===========================================Fin buildTrack");
	}
	
}