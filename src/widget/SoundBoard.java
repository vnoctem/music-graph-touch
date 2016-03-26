package widget;

import java.util.ArrayList;

import scene.GraphicsWrapper;
import scene.Point2D;

public class SoundBoard {
	private int heightPerCol = 90;
	private int width = 420;
	private int volWidth = 40;
	private Point2D position;
	private ArrayList<AbstractSound> sounds;
	private int col = 4;
	
	public SoundBoard(float x, float y) {
		position = new Point2D(x - width / 2, y - (heightPerCol * col) / 2);
		
		int sWidth = (width - volWidth) / 4;
		int sHeight = (heightPerCol * col) / 2;
		
		// initialiser les clés
		sounds = new ArrayList<AbstractSound>();
		sounds.add(new RecordControl(sWidth, sHeight, (as) -> {
			((RecordControl) as).play();
		}));
		sounds.add(new Sound("C", sWidth, sHeight, (as) -> {
			System.out.println("c");
		}));
		sounds.add(new Sound("D", sWidth, sHeight, (as) -> {
			System.out.println("d");
		}));
		sounds.add(new Sound("E", sWidth, sHeight, (as) -> {
			System.out.println("e");
		}));
		sounds.add(new Sound("F", sWidth, sHeight, (as) -> {
			System.out.println("f");
		}));
		sounds.add(new Sound("G", sWidth, sHeight, (as) -> {
			System.out.println("g");
		}));
		sounds.add(new Sound("A", sWidth, sHeight, (as) -> {
			System.out.println("a");
		}));
		sounds.add(new Sound("B", sWidth, sHeight, (as) -> {
			System.out.println("b");
		}));
		
		// positionner les clés
		int colCount = 0;
		int rowCount = 0;
		for (int i = 0; i < sounds.size(); i++) {
			if (colCount == col) {
				colCount = 0;
				rowCount++;
			}
			
			sounds.get(i).setPosition(sWidth * colCount, sHeight * rowCount);
			colCount++;
		}
	}
	
	public void setPosition(float x, float y) {
		position = new Point2D(x - width / 2, y - (heightPerCol * col) / 2);
	}
	
	public void onClick(float x, float y) {
		for (AbstractSound s : sounds) {
			if (s.isInside(x, y, position)) {
				s.setSelect(true);
				s.performAction();
			} else {
				s.setSelect(false);
			}
		}
	}

	public void draw(GraphicsWrapper gw) {
		gw.localWorldTrans(position.x(), position.y());
			gw.setColor(0.2f, 0.2f, 0.2f);
			// le panneau
			gw.drawRect(0, 0, width, (heightPerCol * col), true);
			// volume
			gw.setColor(1, 0, 0);
			gw.drawRect(width - volWidth, - 1, volWidth, (heightPerCol * col) + 3, true);
			// chacun des boutons
			for (AbstractSound s : sounds)
				s.draw(gw);
			gw.setColor(1, 1, 1);
		gw.popMatrix();
	}
}
