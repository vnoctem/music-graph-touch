package music.instruments;

import scene.GraphicsWrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import music.MusicSequencer;

public class Piano extends MusicInstrument {

	public Piano() {
		this.setVelocity(80);
		this.setDuration(200);
		this.setBank(0);
		this.setProgram(0);
	}
	
	@Override
	public void playSample(int sample, int channel, Sequencer sequencer) {
		String filePathName = "samples/piano/sample0" + sample + ".txt";
		File f = new File(filePathName);
		try {
			FileReader fr = new FileReader(f);
			
			// créer un char array de la taille du fichier
			char[] notes = new char[(int)f.length()];
			
			// lire le fichier et le mettre dans le char array
			fr.read(notes);
			// temporaire
			for (int i = 0; i < f.length(); i++) {
				System.out.println(notes[i]);
			}
			
			// Créer une séquence et une track
			Sequence sequence = new Sequence(Sequence.PPQ, 16);
			Track track = sequence.createTrack();
			createTrack(track, channel, notes);
			
			// Assigner la séquence au séquenceur et démarrer
			sequencer.setSequence(sequence);
			sequencer.start();
			
		
		} catch (FileNotFoundException e) {
			System.out.println("Le fichier " + filePathName + " est introuvable.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Erreur lors de la lecture de " + filePathName);
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
		
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
