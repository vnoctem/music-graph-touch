package app;

import java.util.ArrayList;

public class CursorController {

	private ArrayList<Cursor> cursors;
	
	public CursorController() {
		cursors = new ArrayList<Cursor>();
	}
	
	public void addCursor(Cursor c) {
		cursors.add(c);
	}
	
	public Cursor last() {
		return cursors.get(cursors.size() - 1);
	}
	
	public Cursor first() {
		return cursors.get(0);
	}
	
	public Integer getSize() {
		return cursors.size();
	}
	
	public long getDuration() {
		return last().getTime() - cursors.get(0).getTime();
	}
}
