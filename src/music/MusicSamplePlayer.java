package music;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import widget.PlayControl;
import app.Application;
import music.instruments.AbstractInstrument;


public class MusicSamplePlayer {
	
	private Sequencer sequencer;
	private Sequence sequence;
	private Thread thread;

	public MusicSamplePlayer() {
		
	}
	
	public void playMusicSample(MusicSample musicSample, AbstractInstrument instrument, Application app, PlayControl playControl) {
		thread = new Thread() {
			public void run() {
				try {
					sequencer = MidiSystem.getSequencer();
					sequencer.setTempoInBPM(240);
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
					// attendre que la séquence joue avant de fermer le séquenceur
					Thread.sleep((sequencer.getMicrosecondLength() / 1000));
					sequencer.close();
					System.out.println("playMusicSample sequencer.close()");
					
					//System.out.println("avant app.getMF().requestRedraw()");
					//app.getMF().requestRedraw();
					//System.out.println("après app.getMF().requestRedraw();");
					
					playControl.stop();
					app.getMF().requestRedraw();
				} catch (InterruptedException e) {
					System.out.println("thread interrupted");
				}
				
			}
		};
		thread.start();
	}
	
	public void stopPlaying(PlayControl playControl) {
		if (sequencer.isOpen()) {
			sequencer.stop();
			System.out.println("stopPlaying sequencer.stop()");
			sequencer.close();
			System.out.println("stopPlaying sequencer.close()");
			thread.interrupt();
		}
	}
	
}