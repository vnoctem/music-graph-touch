package widget;

import app.Application;
import app.Cursor;
import scene.GraphicsWrapper;
import scene.MusicVertex;

public class SBMaster extends SoundBoard {
	// contrôle pour jouer
	private PlayControl playControl;
	
	// contrôle pour dupliquer
	private DupliControl dupliControl;
	private SBSlave duplicate;
	
	public SBMaster(float x, float y, MusicVertex mv) {
		super(x, y, mv);
		
		// contrôles
		recordControl = new RecordControl(controlStart, (height - pianoHeight) / 2, 35, new float[] {0.5f, 0.5f, 0.5f, 0.5f});
		playControl = new PlayControl(controlStart * 2 + gapBetweenControls, (height - pianoHeight) / 2, 35, new float[] {0.5f, 0.5f, 0.5f, 0.5f});
		dupliControl = new DupliControl(controlStart * 3 + gapBetweenControls * 2, (height - pianoHeight) / 2, 35, new float[] {0.5f, 0.5f, 0.5f, 0.5f});
		moveControl = new MoveControl(controlStart * 4 + gapBetweenControls * 3, (height - pianoHeight) / 2, 35, new float[] {0.5f, 0.5f, 0.5f, 0.5f});
		closeControl = new CloseControl(controlStart * 5 + gapBetweenControls * 4, (height - pianoHeight) / 2, 35, new float[] {0.5f, 0.5f, 0.5f, 0.5f});
		volControl = new VolControl(controlStart * 7 + gapBetweenControls * 6 - gapBetweenSection, (height - pianoHeight) / 2, 35, new float[] {0.5f, 0.5f, 0.5f, 0.5f}, "Vol.");
		octControl = new OctControl(controlStart * 8 + gapBetweenControls * 7 - gapBetweenSection, (height - pianoHeight) / 2, 35, new float[] {0.5f, 0.5f, 0.5f, 0.5f}, "Oct.", new int[] {2, 3, 4, 5, 6});
	}
	
	public boolean subOnRelease(float x, float y, long duration, Cursor cDown) {
		//plus besoin de bouger le nouveau panneau
		if (duplicate != null) {
			duplicate = null;
			return true;
		}
		
		return false;
	}
	
	public boolean subOnMove(float x, float y) {
		if (duplicate != null) {
			duplicate.setPosition(x, y);
			return true;
		}
		
		return false;
	}
	
	public boolean subOnClick(Application app, Cursor c, long timeBetweenClick, float x, float y) {
		if (dupliControl != null && dupliControl.isInside(x, y, position)) {
			System.out.println("duplicate========================================");
			duplicate = new SBSlave(x, y, mv, recordControl);
			dupliControl.duplicate(app, duplicate);
			return true;
		}
		
		if (playControl != null && playControl.isInside(x, y, position)) {
			if (this.mv.getMusicSample() != null) {
				if (playControl.isPlaying()) {
					System.out.println("Stop playing!");
					musicSamplePlayer.stopPlaying(); // arrêter de jouer
				} else {
					System.out.println("Start playing!");
					recordControl.getMusicSample().printMusicNotes();
					musicSamplePlayer.playMusicSample(mv.getMusicSample(), instrument); // jouer l'échantillon					
				}
			} else {
				System.out.println("Aucun échantillon enregistré.");
			}
			playControl.play(); // start or stop playing sample
			//System.out.println("playControl playing : " + playControl.isPlaying());
			return true;
		}
		
		return false;
	}

	@Override
	public void subDraw(GraphicsWrapper gw) {
		// contrôles
		playControl.draw(gw);
		dupliControl.draw(gw);
	}
}
