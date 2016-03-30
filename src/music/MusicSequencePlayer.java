package music;

import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import scene.MusicVertex;

public class MusicSequencePlayer {
	
	private Sequencer sequencer; // permet de démarrer et arrêter la lecture d'une séquence
	private Sequence musicSequence; // la séquence principale dans laquelle tous les MusicSample (tracks) vont être joués
	
	public MusicSequencePlayer(Sequence sequence) {
		try {
			sequencer = MidiSystem.getSequencer(); // initialiser le séquenceur
			open(); // ouvrir le séquenceur
			sequencer.setSequence(sequence);
			sequencer.setTempoInBPM(120);
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}
	
	public Sequencer getSequencer() {
		return sequencer;
	}
	
	public Sequence getMusicSequence() {
		return musicSequence;
	}
	
	public void open() {
		try {
			sequencer.open();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		sequencer.start();
	}
	
	public void stop() {
		sequencer.stop();
	}
	
	public void close() {
		sequencer.close();
	}
	
	public void playAndStop(ArrayList<MusicVertex> sceneMusicList, MusicVertex startSM) {
		
		
		
		try {
			System.out.println(musicSequence.getTickLength());
			
			sequencer.setSequence(musicSequence); // assigner la séquence au séquenceur
			sequencer.start();
			//sequencer.setTickPosition(0);
			//Thread.sleep((sequencer.getMicrosecondLength() / 1000) + 1000);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace(); }
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} 
	}

}