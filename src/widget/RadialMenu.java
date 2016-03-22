package widget;

import music.instruments.Clarinet;
import music.instruments.Contrabass;
import music.instruments.FrenchHorn;
import music.instruments.Guitar;
import music.instruments.Piano;
import music.instruments.Violin;
import scene.GraphicsWrapper;
import scene.Point2D;
import scene.SceneMusic;

public class RadialMenu extends AbstractRadial {
	private boolean onShownInstru = false;
	private SceneMusic sm;
	private float radiusInstru = 40;
	
	public RadialMenu() {
		super(30, 100, 6);
	}
	
	public void show(float x, float y) {
		super.show(x, y);
		
		// réinitialiser pour ne pas modifier l'ancien
		sm = null;
	}
	
	public boolean isShown() {
		return super.shown || onShownInstru;
	}
	
	public SceneMusic close() {
		super.hide();
		onShownInstru = false;
		
		return sm;
	}
	
	protected void drawOptions(GraphicsWrapper gw, int level, float x, float y) {
		// Créer l'instrument
		switch (level) {
			case 1:
				new Piano().drawIcon(gw, x, y, 0.4f, 0.4f);
				break;
			case 2:
				new Guitar().drawIcon(gw, x, y, 0.4f, 0.4f);
				break;
			case 3:
				new Violin().drawIcon(gw, x, y, 0.4f, 0.4f);
				break;
			case 4:
				new Clarinet().drawIcon(gw, x, y, 0.4f, 0.4f);
				break;
			case 5:
				new Contrabass().drawIcon(gw, x, y, 0.4f, 0.4f);
				break;
			case 6:
				new FrenchHorn().drawIcon(gw, x, y, 0.4f, 0.4f);
				break;
		}
	}
	
	protected void actionOnSelect(int selected) {
		switch (selected) {
			case 0:
				sm = new SceneMusic(new Piano(), radiusInstru);
				break;
			case 1:
				sm = new SceneMusic(new Guitar(), radiusInstru);
				break;
			case 2:
				sm = new SceneMusic(new Violin(), radiusInstru);
				break;
			case 3:
				sm = new SceneMusic(new Clarinet(), radiusInstru);
				break;
			case 4:
				sm = new SceneMusic(new Contrabass(), radiusInstru);
				break;
			case 5:
				sm = new SceneMusic(new FrenchHorn(), radiusInstru);
				break;
		}
		
		super.shown = false;
		onShownInstru = true;
	}
	
	public void onMove(float x, float y) {
		super.onMove(x, y);
		
		// faire suivre le composant graphique à l'utilisateur
		if (onShownInstru)
			sm.setPosition(new Point2D(x, y));
	}

	public void draw(GraphicsWrapper gw) {
		drawRadial(gw);
		
		if (onShownInstru) {
			sm.draw(gw);
		}
	}
}
