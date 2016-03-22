package scene;

import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuBar;
import javax.swing.JPanel;

import music.instruments.Clarinet;
import music.instruments.Guitar;
import widget.RadialMenu;

// This class stores the current position of a finger,
// as well as the history of previous positions of that finger
// during its drag.
//
// An instance of this class is created when a finger makes contact
// with the multitouch surface.  The instance stores all
// subsequent positions of the finger, and is destroyed
// when the finger is lifted off the multitouch surface.
class MyCursor {

	// Each finger in contact with the multitouch surface is given
	// a unique id by the framework (or computing platform).
	// There is no guarantee that these ids will be consecutive nor increasing.
	// For example, when two fingers are in contact with the multitouch surface,
	// their ids may be 0 and 1, respectively,
	// or their ids may be 14 and 9, respectively.
	public int id; // identifier

	// This stores the history of positions of the "cursor" (finger)
	// in pixel coordinates.
	// The first position is where the finger pressed down,
	// and the last position is the current position of the finger.
	private ArrayList< Point2D > positions = new ArrayList< Point2D >();

	private float totalDistanceAlongDrag = 0;
	private float distanceFromStartToEndOfDrag = 0;


	// These are used to store what the cursor is being used for.
	public static final int TYPE_NOTHING = 0; // in this case, the cursor is ignored
	public int type = TYPE_NOTHING;


	public MyCursor( int id, float x, float y ) {
		this.id = id;
		positions.add( new Point2D(x,y) );
	}

	public ArrayList< Point2D > getPositions() { return positions; }

	public void addPosition( Point2D p ) {
		if ( positions.size() >= 1 ) {
			totalDistanceAlongDrag += p.distance( positions.get(positions.size()-1) );
			distanceFromStartToEndOfDrag = p.distance( positions.get(0) );
		}
		positions.add( p );
	}

	public Point2D getFirstPosition() {
		if ( positions == null || positions.size() < 1 )
			return null;
		return positions.get( 0 );
	}
	public Point2D getCurrentPosition() {
		if ( positions == null || positions.size() < 1 )
			return null;
		return positions.get( positions.size()-1 );
	}
	public Point2D getPreviousPosition() {
		if ( positions == null || positions.size() == 0 )
			return null;
		if ( positions.size() == 1 )
			return positions.get( 0 );
		return positions.get( positions.size()-2 );
	}

	public boolean doesDragLookLikeLassoGesture() {
		return totalDistanceAlongDrag / (float)distanceFromStartToEndOfDrag > 2.5f;
	}

	public int getType() { return type; }
	public void setType( int type ) { this.type = type; }
}


// This stores a set of instances of MyCursor.
// Each cursor can be identified by its id,
// which is assigned by the framework or computing platform.
// Each cursor can also be identified by its index in this class's container.
// For example, if an instance of this class is storing 3 cursors,
// their ids may be 2, 18, 7,
// but their indices should be 0, 1, 2.
class CursorContainer {
	private ArrayList< MyCursor > cursors = new ArrayList< MyCursor >();

	public int getNumCursors() { return cursors.size(); }
	public MyCursor getCursorByIndex( int index ) { return cursors.get( index ); }

	public int findIndexOfCursorById( int id ) {
		for ( int i = 0; i < cursors.size(); ++i ) {
			if ( cursors.get(i).id == id )
				return i;
		}
		return -1;
	}
	public MyCursor getCursorById( int id ) {
		int index = findIndexOfCursorById( id );
		return ( index == -1 ) ? null : cursors.get( index );
	}

	// Returns the number of cursors that are of the given type.
	public int getNumCursorsOfGivenType( int type ) {
		int num = 0;
		for ( int i = 0; i < cursors.size(); ++i ) {
			if ( cursors.get(i).getType() == type )
				num ++;
		}
		return num;
	}

	// Returns the (i)th cursor of the given type,
	// or null if no such cursor exists.
	// Can be used for retrieving both cursors of type TYPE_CAMERA_PAN_ZOOM, for example,
	// by calling getCursorByType( MyCursor.TYPE_CAMERA_PAN_ZOOM, 0 )
	// and getCursorByType( MyCursor.TYPE_CAMERA_PAN_ZOOM, 1 ),
	// when there may be cursors of other type present at the same time.
	public MyCursor getCursorByType( int type, int i ) {
		for ( int ii = 0; ii < cursors.size(); ++ii ) {
			if ( cursors.get(ii).getType() == type ) {
				if ( i == 0 )
					return cursors.get(ii);
				else
					i --;
			}
		}
		return null;
	}

	// Returns index of updated cursor.
	// If a cursor with the given id does not already exist, a new cursor for it is created.
	public int updateCursorById(
		int id,
		float x, float y
	) {
		Point2D updatedPosition = new Point2D( x, y );
		int index = findIndexOfCursorById( id );
		if ( index == -1 ) {
			cursors.add( new MyCursor( id, x, y ) );
			index = cursors.size() - 1;
		}
		MyCursor c = cursors.get( index );
		if ( ! c.getCurrentPosition().equals( updatedPosition ) ) {
			c.addPosition( updatedPosition );
		}
		return index;
	}
	public void removeCursorByIndex( int index ) {
		cursors.remove( index );
	}
	public ArrayList< Point2D > getWorldPositionsOfCursors( GraphicsWrapper gw ) {
		ArrayList< Point2D > positions = new ArrayList< Point2D >();
		for ( MyCursor cursor : cursors ) {
			positions.add( gw.convertPixelsToWorldSpaceUnits( cursor.getCurrentPosition() ) );
		}
		return positions;
	}
}


//		// Find the cursor that corresponds to the event id, if such a cursor already exists.
//		// If no such cursor exists, the below index will be -1, and the reference to cursor will be null.
//		int cursorIndex = cursorContainer.findIndexOfCursorById( id );
//		MyCursor cursor = (cursorIndex==-1) ? null : cursorContainer.getCursorByIndex( cursorIndex );
//
//
//		if ( cursor == null ) {


public class MusicPath implements Runnable, ActionListener {

	public MultitouchFramework multitouchFramework = null;
	public GraphicsWrapper gw = null;
	
	// un seul menu pour l'instant
	// si plusieurs menus possible (multiutilisateurs), utilise Arraylist
	private RadialMenu menu;
	
	// liste des composant graphique et musical
	private ArrayList<SceneMusic> lSm = new ArrayList<SceneMusic>(); 
	
	private SceneMusic selectedSM = null;

	Thread thread = null;
	boolean threadSuspended;

	int mouse_x, mouse_y;

	public MusicPath( MultitouchFramework mf, GraphicsWrapper gw ) {
		multitouchFramework = mf;
		this.gw = gw;
		multitouchFramework.setPreferredWindowSize(Constant.INITIAL_WINDOW_WIDTH,Constant.INITIAL_WINDOW_HEIGHT);
		
		// initialiser le menu
		menu = new RadialMenu(30, 6, 40);

		gw.setFontHeight( Constant.TEXT_HEIGHT );

		gw.frame( new AlignedRectangle2D( new Point2D(-100,-100), new Point2D(100,100) ), true );
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
		for (SceneMusic sm : lSm) {
			sm.draw(gw);
		}
		
		// menu
		// doit toujours être la dernière à dessiner pour qu'il soit toujours par dessus de tout
		menu.draw(gw);
	}

	// écouteur pour tous les événements de multitouch
	public synchronized void processMultitouchInputEvent( int id, float x, float y, int type ) {
		switch (type) {
			case MultitouchFramework.TOUCH_EVENT_DOWN:
				// si clic sur un des composants graphiques, fait l'action
				for (SceneMusic sm : lSm) {
					if (sm.isInside(x, y)) {
						sm.connect(x, y);
						
						selectedSM = sm;
						break;
					}
				}
				
				// sinon, afficher le menu
				if (selectedSM == null) {
					menu.show(x, y);
				}
				
				// forcer le rafraîchissement pour faire apparaître le menu
				multitouchFramework.requestRedraw();
			break;
			case MultitouchFramework.TOUCH_EVENT_UP:
				if (selectedSM != null) {
					// si déposer le connecteur sur un composant, il faut les relier
					for (SceneMusic sm : lSm) {
						if (sm.isInside(x, y) && sm != selectedSM) {
							selectedSM.doneConnect(sm);
							
							selectedSM = null;
							break;
						}
					}
					
					if (selectedSM != null) {
						selectedSM.doneConnect();
						selectedSM = null;
					}
				}
				
				if (menu.isShown()) {
					// cacher le menu
					SceneMusic sm = menu.close();
					
					if (sm != null)
						lSm.add(sm);
				}
			break;
			case MultitouchFramework.TOUCH_EVENT_MOVE:
				if (selectedSM != null) {
					selectedSM.extendConnector(x, y);
				}
				
				if (menu.isShown())
					menu.onMove(x, y);
				
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


