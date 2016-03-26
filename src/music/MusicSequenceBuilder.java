package music;

import java.util.LinkedList;
import java.util.Queue;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

import music.instruments.AbstractInstrument;
import scene.Connector;
import scene.MusicVertex;

/**
 * Pour bâtir la séquence de musique qui sera jouer par le MusicPlayer
 * @author Vincent
 *
 */
public class MusicSequenceBuilder {
	
	private int ticks; // temps en ticks (millisecondes) pour la composition de la séquence
	
	public MusicSequenceBuilder() {
		ticks = 0; // on débute à 0
	}
	
	/**
	 * Bâtir la séquence de musique
	 * @param startSM : Le noeud de départ
	 * @param tempoTimingType
	 * @param timingResolution
	 * @throws InvalidMidiDataException
	 */
	public Sequence buildMusicSequence(MusicVertex mvRoot, float tempoTimingType, int timingResolution) 
			throws InvalidMidiDataException {
		Sequence musicSequence = new Sequence(tempoTimingType, timingResolution); // music sequence
		
		Queue<MusicVertex> queue = new LinkedList<MusicVertex>();
		
		// ajouter l'échantillon de musique au temps donné
		addMusicSample(mvRoot.getMusicSample(), musicSequence, mvRoot.getInstrument(), ticks);
		queue.add(mvRoot);
		mvRoot.setVisited(true);
		
		while (!queue.isEmpty()) {
			MusicVertex mv = queue.remove(); // extraire le noeud inséré en premier
			ticks += mv.getTimePosition(); // incrémenter les ticks avec le temps de départ du noeud courant
			
			for (Connector c : mv.getConnectors()) {
				MusicVertex mvChild = c.getTarget(mv); // récupérer la cible du connecteur
				
				if (!mvChild.isVisited()) {
					addMusicSample(mvChild.getMusicSample(), musicSequence, mvChild.getInstrument(), ticks + (Math.round(c.getLength()) * 5));
					mvChild.setTimePosition(ticks);
					queue.add(mvChild);
					mvChild.setVisited(true);
				}
			}
		}
		return musicSequence;
	}
	
	private void addMusicSample(MusicSample musicSample, Sequence sequence, AbstractInstrument instrument, int startTick) {
		try {
			musicSample.buildTrack(sequence, instrument, startTick);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
			System.out.println("Instrument invalide.");
		}
	}

}
