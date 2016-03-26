package widget;

import scene.GraphicsWrapper;

public class RecordControl extends AbstractSound {
	private float radius = 20; 
	private boolean isRecording = false;
	private SoundBoard sb;
	
	public RecordControl(float width, float height, SoundBoard sb, ISoundAction sAction) {
		super(width, height, sAction);
		this.sb = sb;
	}
	
	public void record() {
		isRecording = !isRecording;
		
		if (!isRecording)
			sb.hide();
	}
	
	public boolean isRecording() {
		return isRecording;
	}

	@Override
	public void draw(GraphicsWrapper gw) {
		super.draw(gw);
		
		gw.setColor(1, 0, 0);
		
		if (!isRecording)
			gw.drawCircle(position.x() + width / 2 - radius / 2, position.y() + height / 2 - radius / 2, radius / 2, true);
		else
			gw.drawRect(position.x() - radius / 2 + width / 2, position.y() - radius / 2 + height / 2, radius, radius, true);
	}

}
