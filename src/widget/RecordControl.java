package widget;

import music.MusicSample;
import scene.GraphicsWrapper;

public class RecordControl extends AbstractControl {
	private boolean recording = false;
	private float[] colorIcon = new float[]{1, 0, 0};
	private float radiusIcon = 20;
	protected MusicSample musicSample;
	private long startTime = -1;
	
	public MusicSample getMusicSample() {
		return musicSample;
	}

	public void setMusicSample(MusicSample musicSample) {
		this.musicSample = musicSample;
	}

	public RecordControl(float x, float y, float radius, float[] color) {
		super(x, y, radius, color);
	}
	
	public void record() {
		recording = !recording;
		
		if (recording) {
			startTime = System.nanoTime();
		}
	}
	
	public long getStartTime() {
		return startTime / 1000000;
	}
	
	public boolean isRecording() {
		return recording;
	}

	public void draw(GraphicsWrapper gw) {
		super.draw(gw);
		
		gw.setColor(colorIcon[0], colorIcon[1], colorIcon[2]);
		if (!recording)
			gw.drawCircle(position.x() - radiusIcon / 2, position.y() - radiusIcon / 2, radiusIcon / 2, true);
		else
			gw.drawRect(position.x() - radiusIcon / 2, position.y() - radiusIcon / 2, radiusIcon, radiusIcon, true);
		gw.setColor(1, 1, 1);
	}

}
