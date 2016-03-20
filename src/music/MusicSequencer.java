package music;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;
import javax.sound.midi.Sequence;

/**
 * Pour jouer une (ou des) séquence(s) de musique
 * @author Vincent
 *
 */
public class MusicSequencer implements Sequencer {
	
	private Sequencer seq;
	
	public MusicSequencer() throws MidiUnavailableException {
		seq = MidiSystem.getSequencer();
	}

	@Override
	public Info getDeviceInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getMaxReceivers() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxTransmitters() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Receiver getReceiver() throws MidiUnavailableException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Receiver> getReceivers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transmitter getTransmitter() throws MidiUnavailableException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transmitter> getTransmitters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSequence(InputStream stream) throws IOException, InvalidMidiDataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Sequence getSequence() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void startRecording() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopRecording() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isRecording() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void recordEnable(Track track, int channel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recordDisable(Track track) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getTempoInBPM() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTempoInBPM(float bpm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getTempoInMPQ() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTempoInMPQ(float mpq) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTempoFactor(float factor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getTempoFactor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getTickLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getTickPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTickPosition(long tick) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getMicrosecondLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getMicrosecondPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMicrosecondPosition(long microseconds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMasterSyncMode(SyncMode sync) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SyncMode getMasterSyncMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SyncMode[] getMasterSyncModes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSlaveSyncMode(SyncMode sync) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SyncMode getSlaveSyncMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SyncMode[] getSlaveSyncModes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTrackMute(int track, boolean mute) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getTrackMute(int track) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setTrackSolo(int track, boolean solo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getTrackSolo(int track) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addMetaEventListener(MetaEventListener listener) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeMetaEventListener(MetaEventListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[] addControllerEventListener(ControllerEventListener listener, int[] controllers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] removeControllerEventListener(ControllerEventListener listener, int[] controllers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLoopStartPoint(long tick) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getLoopStartPoint() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLoopEndPoint(long tick) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getLoopEndPoint() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLoopCount(int count) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLoopCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void open() throws MidiUnavailableException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSequence(Sequence sequence) throws InvalidMidiDataException {
		// TODO Auto-generated method stub
		
	}
	
	

}