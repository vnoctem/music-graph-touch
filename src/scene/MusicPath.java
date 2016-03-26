package scene;

import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.midi.Sequence;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import music.MusicSequencePlayer;
import widget.RadialMenu;
import widget.RadialSceneMusic;
import widget.SoundBoard;

public class MusicPath implements Runnable, ActionListener {

	public MultitouchFramework multitouchFramework = null;
	public GraphicsWrapper gw = null;
	
	// un seul menu pour l'instant
	// si plusieurs menus possible (multiutilisateurs), utilise Arraylist
	private RadialMenu menu;
	
	// menu pour les composants graphiques
	private RadialSceneMusic menuMV;
	
	// panneau pour jouer des sons
	private SoundBoard sb;
	
	// liste des composant graphique et musical
	private ArrayList<MusicVertex> lMV = new ArrayList<MusicVertex>(); 
	
	private MusicVertex selectedMV = null;

	Thread thread = null;
	boolean threadSuspended;

	int mouse_x, mouse_y;
	
	private Sequence sequence;

	public MusicPath( MultitouchFramework mf, GraphicsWrapper gw ) {
		multitouchFramework = mf;
		this.gw = gw;
		multitouchFramework.setPreferredWindowSize(Constant.INITIAL_WINDOW_WIDTH,Constant.INITIAL_WINDOW_HEIGHT);
		
		// initialiser le menu
		menu = new RadialMenu();
		
		// initialiser le menu des composants
		menuMV = new RadialSceneMusic();
		
		// initialiser le panneau de son
		sb = new SoundBoard();

		gw.setFontHeight( Constant.TEXT_HEIGHT );

		gw.frame( new AlignedRectangle2D( new Point2D(-100,-100), new Point2D(100,100) ), true );
		
		//MusicSequencePlayer mp = new MusicSequencePlayer();
		//mp.playAndStop(lMv, getStartSceneMusic());
		
	}
	
	// retourne le noeud de départ
	public MusicVertex getStartSceneMusic() {
		for (MusicVertex mv : lMV) {
			if (mv.isStart()) {
				return mv;
			}
		}
		return null;
	}

	// méthode pour dessiner dans la scène
	public synchronized void draw() {
		// mettre noir dans l'arrière plan
		gw.clear(0,0,0);
		gw.setColor(0,0,0);
		gw.setupForDrawing();

		gw.setCoordinateSystemToWorldSpaceUnits();
		gw.enableAlphaBlending();

		gw.setCoordinateSystemToPixels();
		
		// tous les composants graphiques et musicals
		for (MusicVertex mv : lMV) {
			mv.draw(gw);
		}
		
		// panneau de son
		sb.draw(gw);
		
		// menu des composants
		menuMV.draw(gw);
		
		// menu
		// doit toujours être la dernière à dessiner pour qu'il soit toujours par dessus de tout
		menu.draw(gw);
	}

	// écouteur pour tous les événements de multitouch
	public synchronized void processMultitouchInputEvent( int id, float x, float y, int type ) {
		switch (type) {
			case MultitouchFramework.TOUCH_EVENT_DOWN:
				// détecte s'il y a une clé cliquée
				// permet pas de faire d'autres choses avant de fermer le panneau
				if (sb.isShown()) {
					sb.onClick(x, y);
				} else {
					// si clic sur un des composants graphiques, fait l'action
					for (MusicVertex mv : lMV) {
						if (mv.isInside(x, y)) {
							menuMV.show(mv, mv.getPosition().x(), mv.getPosition().y(), lMV, sb);
							selectedMV = mv;
							selectedMV.select();
							break;
						}
					}
					
					// sinon, afficher le menu
					if (selectedMV == null) {
						menu.show(x, y);
					}
				}
				
				// forcer le rafraîchissement pour faire apparaître le menu
				multitouchFramework.requestRedraw();
			break;
			case MultitouchFramework.TOUCH_EVENT_UP:
				if (selectedMV != null) {
					// si déposé sur un autre noeud, donc relie-les
					for (MusicVertex mv : lMV) {
						if (mv.isInside(x, y) && mv != selectedMV) {
							menuMV.close();
							selectedMV.deselect();
							selectedMV.doneConnect(mv);
							selectedMV = null;
							break;
						}
					}
					
					if (selectedMV != null) {
						menuMV.close();
						selectedMV.deselect();
						selectedMV.doneConnect(null);
						selectedMV = null;
					}
				}
				
				if (menu.isShown()) {
					// cacher le menu
					MusicVertex mv = menu.close();
					
					if (mv != null)
						lMV.add(mv);
				}
			break;
			case MultitouchFramework.TOUCH_EVENT_MOVE:
				if (menu.isShown())
					menu.onMove(x, y);
				
				if (menuMV.isShown() || menuMV.isAction())
					menuMV.onMove(x, y);
				
				// pour changer l'apparence
				multitouchFramework.requestRedraw();
			break;
		}
	}
	
	// Called by the framework at startup time.
	public void startBackgroundWork() {
		if ( thread == null ) {
			thread = new Thread( this );
			threadSuspended = false;
			thread.start();
		}
		else {
			if ( threadSuspended ) {
				threadSuspended = false;
				synchronized( this ) {
					notify();
				}
			}
		}
	}
	public void stopBackgroundWork() {
		threadSuspended = true;
	}
	public void run() {
		try {
			int sleepIntervalInMilliseconds = 1000;
			while (true) {

				// Here's where the thread does some work
				synchronized( this ) {
					// System.out.println("some background work");
					// ...
				}
				// multitouchFramework.requestRedraw();

				// Now the thread checks to see if it should suspend itself
				if ( threadSuspended ) {
					synchronized( this ) {
						while ( threadSuspended ) {
							wait();
						}
					}
				}
				Thread.sleep( sleepIntervalInMilliseconds );  // interval given in milliseconds
			}
		}
		catch (InterruptedException e) { }
	}
	
	// écouteurs pour tous les événements d'ordi
	// pas sensé d'utiliser
	public synchronized void keyPressed( KeyEvent e ) {}
	public synchronized void keyReleased( KeyEvent e ) {}
	public synchronized void keyTyped( KeyEvent e ) {}
	public synchronized void mouseEntered( MouseEvent e ) {}
	public synchronized void mouseExited( MouseEvent e ) {}
	public synchronized void mouseClicked( MouseEvent e ) {}
	public synchronized void mousePressed(MouseEvent e) {}
	public synchronized void mouseReleased(MouseEvent e) {}
	public synchronized void mouseDragged(MouseEvent e) {}
	public synchronized void mouseMoved(MouseEvent e) {}
	// Méthodes forcés à redéfinir par le framework
	// pas sensé d'utiliser
	public void actionPerformed(ActionEvent e) {}
	// Called by the framework when creating widgets.
	// pas sensé d'utiliser
	public JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		return menuBar;
	}
	// Called by the framework when creating widgets.
	// pas sensé d'utiliser
	public JPanel createPanelOfWidgets() {
		JPanel panelOfWidgets = new JPanel();
		return panelOfWidgets;
	}
}


