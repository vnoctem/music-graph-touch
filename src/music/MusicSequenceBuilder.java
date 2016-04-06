package music;

import java.util.ArrayList;
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
	
	private long ticks; // temps en ticks (millisecondes) pour la composition de la séquence
	private ArrayList<MusicVertex> lMv;
	private ArrayList<MusicVertex> lTimedMV;
	
	public MusicSequenceBuilder(ArrayList<MusicVertex> lMv) {
		ticks = 0; // on débute à 0
		this.lMv = lMv;
	}
	
	public ArrayList<MusicVertex> getLTimedMV() {
		return lTimedMV;
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
		Sequence sequence = new Sequence(tempoTimingType, timingResolution); // music sequence
		Queue<MusicVertex> queue = new LinkedList<MusicVertex>();
		lTimedMV = new ArrayList<MusicVertex>();
		
		resetVisitedMusicVertex(); // remettre les noeuds à non visité
		
		// ajouter l'échantillon de musique au temps donné
		addMusicSample(mvRoot.getMusicSample(), sequence, mvRoot.getInstrument(), ticks);
		lTimedMV.add(mvRoot);
		mvRoot.setTimePosition(ticks);
		
		queue.add(mvRoot);
		mvRoot.setVisited(true);
		
		while (!queue.isEmpty()) {
			MusicVertex mv = queue.remove(); // extraire le noeud inséré en premier
			ticks = mv.getTimePosition(); // incrémenter les ticks avec le temps de départ du noeud courant
			
			for (Connector c : mv.getConnectors()) {
				MusicVertex mvChild = c.getTarget(mv); // récupérer la cible du connecteur
				
				if (!mvChild.isVisited()) {
					mvChild.setPreviousMV(mv); // assigner le noeud précédent
					if (mvChild.getMusicSample() != null) { // si échantillon existe
						if (!mvChild.getMusicSample().getMusicNotes().isEmpty()) { // si échantillon pas vide
							addMusicSample(mvChild.getMusicSample(), sequence, mvChild.getInstrument(), ticks + (Math.round(c.getLength()) * 5));
							lTimedMV.add(mvChild);
							mvChild.setTimePosition(ticks + Math.round(c.getLength()) * 5);
							queue.add(mvChild);
							mvChild.setVisited(true);
						} else { // échantillon vide, continuer quand même au prochain
							lTimedMV.add(mvChild);
							mvChild.setTimePosition(ticks + Math.round(c.getLength()) * 5);
							queue.add(mvChild);
							mvChild.setVisited(true);
						}
					}
				}
			}
		}
		return sequence;
	}
	
	public void sortTimedMVByTimePosition() {
		for (int i = 0; i < lTimedMV.size() - 1; i++) {
			for (int j = 0; j < lTimedMV.size() - i - 1; j++) {
				if (lTimedMV.get(j).getTimePosition() > lTimedMV.get(j + 1).getTimePosition()) {
					MusicVertex tempMV = lTimedMV.get(j);
					lTimedMV.set(j, lTimedMV.get(j + 1));
					lTimedMV.set(j + 1, tempMV);
				}
			}
		}
	}
	
	private void addMusicSample(MusicSample musicSample, Sequence sequence, AbstractInstrument instrument, long startTick) {
		try {
			musicSample.buildTrack(sequence, instrument, startTick);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
			System.out.println("Instrument invalide.");
		}
	}
	
	private void resetVisitedMusicVertex() {
		for (MusicVertex mv : lMv) {
			mv.setVisited(false);
			System.out.println("resetVisitedMusicVertex()");
		}
	}

}
