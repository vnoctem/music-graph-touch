package music.instruments;

import scene.GraphicsWrapper;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public abstract class AbstractInstrument {
	
	// les paramètres qui définissent l'instrument
	private int bank;
	private int program;

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
	
	public abstract void drawIcon(GraphicsWrapper gw, float transX, float transY, float scaleX, float scaleY);
}
