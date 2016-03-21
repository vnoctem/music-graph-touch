package music.instruments;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import scene.GraphicsWrapper;

public class Guitar extends MusicInstrument {
	
	public Guitar() {
		this.setVelocity(80);
		this.setDuration(500);
		this.setBank(0);
		this.setProgram(25);
	}
	
	// Pour jouer un patron de musique dans le séquenceur
	public void playSample(int sample, int channel, Sequencer sequencer, Sequence sequence) 
			throws InvalidMidiDataException {
		// créer un File à partir du filePathName
		String filePathName = "samples/guitar/sample" + sample + ".txt";
		
		super.playSample(filePathName, channel, sequencer, sequence);
	}
	
	// Pour ajouter une note à la 'track' (source : http://archive.oreilly.com/pub/a/onjava/excerpt/jenut3_ch17/index1.html)
	@Override
    public void addNote(Track track, int startTick, int tickLength, int key, int velocity, int channel)
        throws InvalidMidiDataException
    {
		// changer l'instrument pour une guitare
		ShortMessage instrument = new ShortMessage();
        instrument.setMessage(ShortMessage.PROGRAM_CHANGE, channel, getProgram(), getBank());
        track.add(new MidiEvent(instrument, startTick));
        
		super.addNote(track, startTick, tickLength, key, velocity, channel);
    }

	@Override
	public void drawIcon(GraphicsWrapper gw, float transX, float transY, float scaleX, float scaleY) {
		gw.setLineWidth(3);
		gw.localWorldTrans(transX, transY, scaleX, scaleY);
			// icônes "Guitare"
			gw.setColor(1, 1, 1);
			// bâton
			int height = 150;
			int lineWidth = 6;
			gw.setLineWidth(lineWidth);
			gw.drawLine(-lineWidth / 2, -height / 2, -lineWidth / 2, height / 2);
			gw.setLineWidth(2);
			int topWidth = 20;
			gw.drawLine(-topWidth / 2 - lineWidth / 2, height / 10 - height / 2, topWidth / 2 - lineWidth / 2, height / 10 - height / 2);
			gw.drawLine(-topWidth / 2 - lineWidth / 2, height / 5 - height / 2, topWidth / 2 - lineWidth / 2, height / 5 - height / 2);
			// corps
			int topCircleWidth = 26;
			int topCircleHeight = 18;
			gw.drawEllipse(-lineWidth / 2, height / 8, topCircleWidth, topCircleHeight);
			int bottomCircleWidth = 35;
			int bottomCircleHeight = 25;
			int gap = 10;
			gw.drawEllipse(-lineWidth / 2, height / 8 + topCircleHeight + bottomCircleHeight - gap, bottomCircleWidth, bottomCircleHeight);
			gw.setColor(0, 0, 0);
			int smallCircle = 10;
			gw.fillCircle(-lineWidth / 2 - smallCircle, height / 8 - smallCircle, smallCircle);
			gw.setLineWidth(4);
			int bottomWidth = bottomCircleWidth / 2;
			gw.drawLine(
					-lineWidth / 2 - bottomWidth, 
					height / 8 + topCircleHeight + bottomCircleHeight - gap + bottomCircleHeight / 2, 
					-lineWidth / 2 + bottomWidth, 
					height / 8 + topCircleHeight + bottomCircleHeight - gap + bottomCircleHeight / 2
			);
			gw.setLineWidth(2);
			gw.setColor(1, 1, 1);
		gw.popMatrix();
		gw.setLineWidth(1);
	}
}
