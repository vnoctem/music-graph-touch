package music.instruments;

import scene.GraphicsWrapper;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;


public class Piano extends MusicInstrument {

	public Piano() {
		this.setVelocity(80);
		this.setDuration(200);
		this.setBank(0);
		this.setProgram(0);
	}
	
	// Pour jouer un patron de musique dans le séquenceur
	public void playSample(int sample, int channel, Sequencer sequencer, Sequence sequence) 
			throws InvalidMidiDataException {
		// créer un File à partir du filePathName
		String filePathName = "samples/piano/sample" + sample + ".txt";
		
		super.playSample(filePathName, channel, sequencer, sequence);
	}
	
	// Pour ajouter une note à la 'track' (source : http://archive.oreilly.com/pub/a/onjava/excerpt/jenut3_ch17/index1.html)
	@Override
    public void addNote(Track track, int startTick, int tickLength, int key, int velocity, int channel)
        throws InvalidMidiDataException
    {
		// changer l'instrument pour un piano
		ShortMessage instrument = new ShortMessage();
        instrument.setMessage(ShortMessage.PROGRAM_CHANGE, channel, getProgram(), getBank());
        track.add(new MidiEvent(instrument, startTick));
        
        super.addNote(track, startTick, tickLength, key, velocity, channel);
    }
	
	@Override
	public void drawIcon(GraphicsWrapper gw, float transX, float transY, float scaleX, float scaleY) {
		gw.setLineWidth(3);
		gw.localWorldTrans(transX, transY, scaleX, scaleY);
			// icônes "Piano"
			gw.setColor(1, 1, 1);
			int width = 100;
			int height = 100;
			// corps
			gw.drawLine(-width / 2, -height / 2, width / 2, -height / 2);
			gw.drawLine(-width / 2, -height / 2, -width / 2, height / 2);
			gw.drawLine(width / 2, -height / 2, width / 2, height / 2);
			gw.drawLine(-width / 2, height / 2, width / 2, height / 2);
			// clés
			int keyWidth = 20;
			int keyHeight = 60;
			gw.drawLine(width / 3 - width / 2, -height / 2, width / 3 - width / 2, height / 2);
			gw.drawLine(width / 3 * 2 - width / 2, -height / 2, width / 3 * 2 - width / 2, height / 2);
			gw.drawRect(width / 3 - width / 2 - keyWidth / 2, -height / 2, keyWidth, keyHeight, true);
			gw.drawRect(width / 3 * 2 - width / 2 - keyWidth / 2, -height / 2, keyWidth, keyHeight, true);
		gw.popMatrix();
		gw.setLineWidth(1);
	}
}
