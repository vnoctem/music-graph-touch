package music;

import javax.sound.midi.*;

import music.instruments.MusicInstrument;

public class MusicSynthesizer {
	
	private Synthesizer synth;
	private MidiChannel[] md;
	
	public MusicSynthesizer() throws MidiUnavailableException {
		synth = MidiSystem.getSynthesizer();
		md = synth.getChannels(); // pour avoir les MIDI channels (0 à 15)
		
	}
	
	public void open() throws MidiUnavailableException {
		synth.open();
	}
	
	public void close() throws MidiUnavailableException {
		synth.close();
	}
	
	public MidiChannel[] getMidiChannels() {
		return md;
	}
	
	// Pour assigner un instrument à un MIDI channel (0 à 15)
	public void setInstrument(MusicInstrument instrument, int channel) {
		md[channel].programChange(instrument.getBank(), instrument.getProgram());
	}
	
	
	public void playNote(int noteNumber, int velocity, int channel, int duration){
		md[channel].noteOn(noteNumber, velocity);
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//md[channel].noteOff(noteNumber);
	}
	
	public void stopNote(int noteNumber, int channel) {
		md[channel].noteOff(noteNumber);
	}
	

}
