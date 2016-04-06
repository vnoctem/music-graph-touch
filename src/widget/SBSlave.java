package widget;

import music.MusicNotePlayer;
import app.Application;
import app.Cursor;
import scene.GraphicsWrapper;
import scene.MusicVertex;

public class SBSlave extends SoundBoard {
	
	public SBSlave(float x, float y, MusicVertex mv, RecordControl recordControl, MusicNotePlayer musicNotePlayer) {
		super(x, y, mv);
		
		super.musicNotePlayer = musicNotePlayer;
		super.recordControl = recordControl;
		
		moveControl = new MoveControl(controlStart * 4 + gapBetweenControls * 3, (height - pianoHeight) / 2, 35, new float[] {0.5f, 0.5f, 0.5f, 0.5f});
		closeControl = new CloseControl(controlStart * 5 + gapBetweenControls * 4, (height - pianoHeight) / 2, 35, new float[] {0.5f, 0.5f, 0.5f, 0.5f});
	}

	@Override
	public boolean subOnClick(Application app, Cursor c, float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean subOnRelease(float x, float y, long duration, Cursor cDown) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean subOnMove(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void subDraw(GraphicsWrapper gw) {
		// TODO Auto-generated method stub
		
	}
}
