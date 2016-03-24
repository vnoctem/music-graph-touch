package music;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

import music.instruments.AbstractInstrument;
import scene.Connector;
import scene.SceneMusic;

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
	public void buildMusicSequence(SceneMusic startSM, float tempoTimingType, int timingResolution) 
			throws InvalidMidiDataException {
		Sequence musicSequence = new Sequence(tempoTimingType, timingResolution); // music sequence
		
		addMusicSample(startSM.getMusicSample(), musicSequence, startSM.getInstrument(), ticks); // ajouter l'échantillon de musique au temps donné
		
		for (Connector c : startSM.getConnectors()) {
			SceneMusic nextSM = c.getTarget();
			if (nextSM == null) {
				return;
			}
			// ajouter l'échantillon suivant après un certain temps (calculé avec la longueur du connecteur * 100 millisecondes)
			addMusicSample(nextSM.getMusicSample(), musicSequence, nextSM.getInstrument(), ticks + Math.round(c.getLength()) * 100);
			
		}
		//ticks += 
		ticks += startSM.getMusicSample().getDuration(); // incrémenter le temps avec le temps de l'échantillon (pas vrm)
		
		//musicPlayer, musicSample, channel);
		
		
		
	}
	
	private void addMusicSample(MusicSample musicSample, Sequence sequence, AbstractInstrument instrument, int startTick) {
		
	}

}
