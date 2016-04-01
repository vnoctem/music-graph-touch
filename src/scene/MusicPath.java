package scene;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedHashMap;

import javax.swing.JMenuBar;
import javax.swing.JPanel;

import app.Application;
import app.Cursor;
import app.CursorController;


public class MusicPath implements Runnable, ActionListener {

	public MultitouchFramework multitouchFramework = null;
	public GraphicsWrapper gw = null;
	
	// contenir l'application complète
	private Application app;
	// track les curseurs
	private LinkedHashMap<Integer, CursorController> cursors;
	private long startTime = 0;
	// pause entre les touches
	private long pause = 0;

	Thread thread = null;
	boolean threadSuspended;

	int mouse_x, mouse_y;
	
	public MusicPath( MultitouchFramework mf, GraphicsWrapper gw ) {
		multitouchFramework = mf;
		this.gw = gw;
		multitouchFramework.setPreferredWindowSize(Constant.INITIAL_WINDOW_WIDTH,Constant.INITIAL_WINDOW_HEIGHT);

		gw.setFontHeight( Constant.TEXT_HEIGHT );
		
		app = new Application();
		cursors = new LinkedHashMap<Integer, CursorController>();

		gw.frame( new AlignedRectangle2D( new Point2D(-100,-100), new Point2D(100,100) ), true );
		
		// charge la scène si celle-ci a été créée
		try {
			// chemin
			File fichier =  new File("scene.ser") ;
			if (fichier.exists()) {
				ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(fichier)) ;
				// désérialization de l'objet
				app = (Application) ois.readObject();
				ois.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		
		app.draw(gw);
	}

	// écouteur pour tous les événements de multitouch
	public synchronized void processMultitouchInputEvent( int id, float x, float y, int type ) {
		switch (type) {
			case MultitouchFramework.TOUCH_EVENT_DOWN:
				// vérifier si c'est la première touche
				// et un temps a été sauvegardé (touch up)
				if (cursors.isEmpty() && startTime != 0) {
					pause = System.nanoTime() - startTime;
				}
				
				// créer un nouveau tracker pour les curseurs
				CursorController cc = new CursorController();
				cc.addCursor(new Cursor(type, x, y));
				cursors.put(id, cc);
				
				app.touchDown(id, pause / 1000000, cursors);
				
				// réinitialiser pour pas avoir des effets inattendus
				if (pause != 0) {
					startTime = 0;
					pause = 0;
				}
				
				// forcer le rafraîchissement pour faire apparaître le menu
				multitouchFramework.requestRedraw();
			break;
			case MultitouchFramework.TOUCH_EVENT_UP:
				// garder le curseur dans le propre contrôleur
				cursors.get(id).addCursor(new Cursor(type, x, y));
				
				app.touchUp(id, cursors.get(id));
				
				// une fois traité, enlève le tracker
				cursors.remove(id);
				
				// vérifier si c'est la dernière touche
				if (cursors.isEmpty()) {
					startTime = System.nanoTime();
				}
			break;
			case MultitouchFramework.TOUCH_EVENT_MOVE:
				// garder le curseur dans le propre contrôleur
				cursors.get(id).addCursor(new Cursor(type, x, y));
				
				app.touchMove(id, cursors.get(id));
				
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


