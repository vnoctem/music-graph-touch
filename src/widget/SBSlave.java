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
