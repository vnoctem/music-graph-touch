package music;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import music.instruments.AbstractInstrument;


public class MusicSamplePlayer {
	
	private Sequencer sequencer;
	private Sequence sequence;

	public MusicSamplePlayer() {
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.setTempoInBPM(240);
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void playMusicSample(MusicSample musicSample, AbstractInstrument instrument) {
		try {
			sequencer.open();
			System.out.println("playMusicSample sequencer.open()");
			sequence = new Sequence(Sequence.PPQ, 250);
			musicSample.buildTrack(sequence, instrument, 0);
			sequencer.setSequence(sequence);
		} catch (InvalidMidiDataException e1) {
			e1.printStackTrace();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
		
		sequencer.start();
		System.out.println("playMusicSample sequencer.start()");
		try {
			Thread.sleep(Math.round((sequencer.getMicrosecondLength() / 1000))); // attendre que la séquence joue avant de fermer le séquenceur
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		sequencer.close();
		System.out.println("playMusicSample sequencer.close()");
	}
	
	public void stopPlaying() {
		if (sequencer.isOpen()) {
			sequencer.stop();
			System.out.println("stopPlaying sequencer.stop()");
			sequencer.close();
		}
		System.out.println("stopPlaying sequencer.close()");
	}
	
}