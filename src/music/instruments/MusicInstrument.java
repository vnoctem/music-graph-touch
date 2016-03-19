package music.instruments;

public abstract class MusicInstrument {
	
	private int velocity; // entre 0 et 127, la rapidité avec laquelle la touche a été enfoncée
	private int duration; // durée des notes jouées
	
	// les paramètres qui définissent l'instrument
	private int bank = 0;
	private int program = 0;
	
	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}
	
	public int getVelocity() {
		return velocity;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public int getDuration() {
		return duration;
	}
	
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

}
