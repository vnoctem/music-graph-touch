package music.instruments;

import scene.GraphicsWrapper;

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
