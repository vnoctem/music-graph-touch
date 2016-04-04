package widget;

import app.Application;
import music.instruments.Clarinet;
import music.instruments.Contrabass;
import music.instruments.FrenchHorn;
import music.instruments.Guitar;
import music.instruments.Piano;
import music.instruments.Violin;
import scene.GraphicsWrapper;
import scene.Point2D;
import scene.MusicVertex;

public class RadialMenu extends AbstractRadial {
	
	private MusicVertex mv;
	private float radiusInstru = 40;
	private Application app;
	
	public RadialMenu(float x, float y, Application app) {
		super(30, 100, 6, new float[] {0.7f, 0.7f, 0f});
		position = new Point2D(x, y);
		this.app = app;
	}
	
	public MusicVertex getMV() {
		return mv;
	}
	
	protected void drawOptions(GraphicsWrapper gw, int level, float x, float y) {
		// Créer l'instrument
		switch (level) {
			case 0:
				new Piano().drawIcon(gw, x, y, 0.4f, 0.4f);
				break;
			case 1:
				new Guitar().drawIcon(gw, x, y, 0.4f, 0.4f);
				break;
			case 2:
				new Violin().drawIcon(gw, x, y, 0.4f, 0.4f);
				break;
			case 3:
				new Clarinet().drawIcon(gw, x, y, 0.4f, 0.4f);
				break;
			case 4:
				new Contrabass().drawIcon(gw, x, y, 0.4f, 0.4f);
				break;
			case 5:
				new FrenchHorn().drawIcon(gw, x, y, 0.4f, 0.4f);
				break;
		}
	}
	
	protected void actionOnSelect(int selected, float x, float y) {
		if (app.getChannelCounter() <= 15) { // pour ne pas dépasser le nombre maximal de channels
			if (app.getChannelCounter()  == 9) { // skip le channel de percussion
				app.setChannelCounter(app.getChannelCounter() + 1);
				System.out.println("Skip channel 9");
			}
			switch (selected) {
				case 0:
					mv = new MusicVertex(new Piano(), radiusInstru, app.getChannelCounter());
					app.setChannelCounter(app.getChannelCounter() + 1);
					break;
				case 1:
					mv = new MusicVertex(new Guitar(), radiusInstru, app.getChannelCounter());
					app.setChannelCounter(app.getChannelCounter() + 1);
					break;
				case 2:
					mv = new MusicVertex(new Violin(), radiusInstru, app.getChannelCounter());
					app.setChannelCounter(app.getChannelCounter() + 1);
					break;
				case 3:
					mv = new MusicVertex(new Clarinet(), radiusInstru, app.getChannelCounter());
					app.setChannelCounter(app.getChannelCounter() + 1);
					break;
				case 4:
					mv = new MusicVertex(new Contrabass(), radiusInstru, app.getChannelCounter());
					app.setChannelCounter(app.getChannelCounter() + 1);
					break;
				case 5:
					mv = new MusicVertex(new FrenchHorn(), radiusInstru, app.getChannelCounter());
					app.setChannelCounter(app.getChannelCounter() + 1);
					break;
			}
		} else {
			System.out.println("Nombre maximal de noeuds atteint! (15 noeuds)");
		}
	}
	
	protected void actionOnMove(float x, float y) {
		// faire suivre le composant graphique à l'utilisateur
		if (mv != null)
			mv.setPosition(x, y);
	}

	public void draw(GraphicsWrapper gw) {
		drawRadial(gw);
		
		if (mv != null) {
			mv.draw(gw);
		}
	}
}
