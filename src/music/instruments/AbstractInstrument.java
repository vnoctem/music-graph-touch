package music.instruments;

import java.io.Serializable;

import scene.GraphicsWrapper;

public abstract class AbstractInstrument implements Serializable {
	
	private static final long serialVersionUID = 7589662898690292035L;
	
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
	
	public abstract String getName();
	
	public abstract void drawIcon(GraphicsWrapper gw, float transX, float transY, float scaleX, float scaleY);
}
