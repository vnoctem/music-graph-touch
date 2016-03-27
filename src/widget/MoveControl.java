package widget;

import scene.GraphicsWrapper;

public class MoveControl extends AbstractControl {
	
	private float[] colorIcon = new float[]{0.5f, 0.5f, 0.5f};

	public MoveControl(float x, float y, float radius, float[] color) {
		super(x, y, radius, color);
	}
	
	public void move(float x, float y, SoundBoard sb) {
		sb.setPositionWithoutChanges(x - position.x(), y - position.y());
	}
	
	public void draw(GraphicsWrapper gw) {
		super.draw(gw);
		
		gw.setColor(colorIcon[0], colorIcon[1], colorIcon[2]);
			int gap = 20;
			int triHeight = 20;
			int triWidth = 20;
			gw.setLineWidth(4);
			gw.drawLine(position.x(), position.y() - radius + gap, position.x(), position.y() + radius - gap);
			gw.drawLine(position.x() - radius + gap, position.y(), position.x() + radius - gap, position.y());
			// triangles
			gw.drawTriangle( 
					position.x() - triWidth / 2, position.y() - radius + gap + triHeight / 2,
					position.x() + triWidth / 2, position.y() - radius + gap + triHeight / 2,
					position.x(), position.y() - radius + gap - 5
			);
			gw.drawTriangle( 
					position.x() + triWidth / 2, position.y() + radius - gap - triHeight / 2,
					position.x() - triWidth / 2, position.y() + radius - gap - triHeight / 2,
					position.x(), position.y() + radius - gap + 5
			);
			gw.drawTriangle( 
					position.x() - radius + gap, position.y(),
					position.x() - radius + gap + triWidth / 2, position.y() + triHeight / 2,
					position.x() - radius + gap + triWidth / 2, position.y() - triHeight / 2
			);
			gw.drawTriangle( 
					position.x() + radius - gap, position.y(),
					position.x() + radius - gap - triWidth / 2, position.y() - triHeight / 2,
					position.x() + radius - gap - triWidth / 2, position.y() + triHeight / 2
			);
			gw.setLineWidth(1);
		gw.setColor(1, 1, 1);
	}

}
