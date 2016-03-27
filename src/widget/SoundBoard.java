package widget;

import java.util.ArrayList;
import java.util.LinkedList;

import scene.GraphicsWrapper;
import scene.Point2D;

public class SoundBoard {
	// entire panel
	private int width = 800;
	private int height = 400;
	// piano
	private int pianoHeight = 250;
	private int heightSharp = 180;
	private int widthSharp = 80;
	private int nbKeys = 7;
	private int nbSharpKeys = 5;
	
	private Point2D position;
	private ArrayList<Sound> sounds;
	
	public SoundBoard(float x, float y) {
		position = new Point2D(x - width / 2, y - height / 2);
		
		int keyWidth = width / nbKeys;
		
		// initialiser les actions pour chaque clé
		// les actions sont associés en ordre avec ceux d'ajouter dans le panneau
		// donc même order que l'insertion des clés
		LinkedList<ISoundAction> actions = new LinkedList<ISoundAction>();
		actions.add((as) -> {
			System.out.println("c#");
		});
		actions.add((as) -> {
			System.out.println("d#");
		});
		actions.add((as) -> {
			System.out.println("f#");
		});
		actions.add((as) -> {
			System.out.println("g#");
		});
		actions.add((as) -> {
			System.out.println("a#");
		});
		actions.add((as) -> {
			System.out.println("c");
		});
		actions.add((as) -> {
			System.out.println("d");
		});
		actions.add((as) -> {
			System.out.println("e");
		});
		actions.add((as) -> {
			System.out.println("f");
		});
		actions.add((as) -> {
			System.out.println("g");
		});
		actions.add((as) -> {
			System.out.println("a");
		});
		actions.add((as) -> {
			System.out.println("b");
		});
		
		// initialiser les clés
		// commencer avec les clés de dièse
		// ils vont être détecté en premier afin de simplifier les dessins
		sounds = new ArrayList<Sound>();
		for (int i = 0; i < nbSharpKeys + 1; i++) {
			if (i == 2)
				continue;
			
			sounds.add(
					new Sound(widthSharp, heightSharp, 
							new float[] {0.1f, 0.1f, 0.1f, 1f},
							new float[] {0.2f, 0.2f, 0.2f, 1f},
							actions.poll()
					)
			);
			sounds.get(sounds.size() - 1).setPosition(keyWidth * i + (keyWidth - widthSharp / 2), height - pianoHeight);
		}
		// les autres clés
		for (int i = 0; i < nbKeys; i++) {
			sounds.add(
					new Sound(keyWidth, pianoHeight, 
							new float[] {0.5f, 0.5f, 0.5f, 0.5f},
							new float[] {0.7f, 0.7f, 0.7f, 0.5f},
							actions.pop()
					)
			);
			sounds.get(sounds.size() - 1).setPosition(keyWidth * i, height - pianoHeight);
		}
	}
	
	public void setPosition(float x, float y) {
		position = new Point2D(x - width / 2, y - height / 2);
	}
	
	public void onClick(float x, float y) {
		for (Sound s : sounds) {
			if (s.isInside(x, y, position)) {
				s.setSelected(true);
				s.performAction();
				
				return;
			}
		}
	}
	
	public void onRelease(float x, float y, long duration) {
		for (Sound s : sounds) {
			if (s.isInside(x, y, position)) {
				s.setSelected(false);
				// debug with seconds
				// System.out.println((double)duration / 1000000000.0);
				return;
			}
		}
	}

	public void draw(GraphicsWrapper gw) {
		gw.localWorldTrans(position.x(), position.y());
			gw.setColor(0.2f, 0.2f, 0.2f, 0.5f);
			// le panneau
			gw.drawRect(0, 0, width, height, true);
			// chacun des clés
			// commencer avec les dièses pour qu'ils soient par dessus
			for (int i = sounds.size() - 1; i >= 0; i--)
				sounds.get(i).draw(gw);
			gw.setColor(1, 1, 1);
		gw.popMatrix();
	}
}
