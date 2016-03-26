package widget;

import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

import music.MusicSequenceBuilder;
import music.MusicSequencePlayer;
import scene.Connector;
import scene.GraphicsWrapper;
import scene.MusicVertex;

public class RadialSceneMusic extends AbstractRadial {
	
	private MusicVertex mv;
	private boolean action = false;
	private ArrayList<MusicVertex> lMv;
	private SoundBoard sb;

	public RadialSceneMusic() {
		super(43, 100, 5, new float[] {0, 0, 0.5f});
	}
	
	public void show(MusicVertex mv, float x, float y, ArrayList<MusicVertex> lMv, SoundBoard sb) {
		super.show(x, y);
		
		this.mv = mv;
		this.lMv = lMv;
		this.sb = sb;
	}
	
	@Override
	protected void drawOptions(GraphicsWrapper gw, int level, float x, float y) {
		gw.localWorldTrans(x, y, 0.3f, 0.3f);
			switch (level) {
				case 0:
					drawDelete(gw);
					break;
				case 1:
					drawMove(gw);
					break;
				case 2:
					drawStart(gw);
					break;
				case 3:
					drawMenu(gw);
					break;
				case 4:
					drawConnector(gw);
					break;
			}
		gw.popMatrix();
	}
	
	private void drawStart(GraphicsWrapper gw) {
		int width = 100;
		int triHeight = 30;
		int triWidth = 30;
		gw.drawLine(width / 2, 0, -width / 2, 0);
		// triangles
		gw.drawTriangle(
				width / 2 + triWidth, 0,
				width / 2, triHeight / 2,
				width / 2, -triHeight / 2
		);
	}
	
	private void drawDelete(GraphicsWrapper gw) {
		int width = 100;
		int height = 100;
		gw.drawLine(-width / 2, -height / 2, width / 2, height / 2);
		gw.drawLine(width / 2, -height / 2, -width / 2, height / 2);
	}
	
	private void drawMove(GraphicsWrapper gw) {
		int width = 100;
		int height = 100;
		int triHeight = 30;
		int triWidth = 30;
		gw.drawLine(0, -height / 2, 0, height / 2);
		gw.drawLine(width / 2, 0, -width / 2, 0);
		// triangles
		gw.drawTriangle( 
				-triWidth / 2, -height / 2,
				triWidth / 2, -height / 2,
				0, -height / 2 - triHeight
		);
		gw.drawTriangle( 
				triWidth / 2, height / 2,
				-triWidth / 2, height / 2,
				0, height / 2 + triHeight
		);
		gw.drawTriangle( 
				-width / 2 - triWidth, 0,
				-width / 2, -triHeight / 2,
				-width / 2, triHeight / 2
		);
		gw.drawTriangle(
				width / 2 + triWidth, 0,
				width / 2, triHeight / 2,
				width / 2, -triHeight / 2
		);
	}
	
	private void drawConnector(GraphicsWrapper gw) {
		int width = 80;
		int height = 80;
		gw.drawEllipse(0, height / 2, width / 2, height / 2, 270, 450, false);
		gw.drawEllipse(0, -height / 2, width / 2, height / 2, 90, 270, false);
	}
	
	private void drawMenu(GraphicsWrapper gw) {
		int width = 100;
		int height = 100;
		int wGap = 20;
		int hGap = 20;
		gw.drawRect(-width / 2, -height / 2, width, height);
		gw.drawLine(-width / 2 + wGap, -height / 2, -width / 2 + wGap, height / 2);
		gw.drawLine(-width / 2, -height / 2 + hGap, width / 2, -height / 2 + hGap);
	}
	
	public void draw(GraphicsWrapper gw) {
		drawRadial(gw);
	}
	
	protected void actionOnMove(float x, float y) {
		switch (selected) {
			case 1: // bouger
				mv.setPosition(x, y);
				break;
			case 3: // afficher le panneau de son
				sb.show(x, y, mv);
				break;
			case 4: // connecter à d'autres noeuds
				mv.extendConnector(x, y);
				break;
		}
	}
	
	public void close() {
		super.hide();
		
		mv = null;
		action = false;
		lMv = null;
	}

	@Override
	protected void actionOnSelect(int selected, float x, float y) {
		switch (selected) {
			case 0: // supprimer
				// enlever les connecteurs
				for (MusicVertex s : lMv) {
					s.deleteConnIfLinked(mv);
				}
				// enlever le noeud
				lMv.remove(mv);
				break;
			case 2: // noeud de départ
				for (MusicVertex mv : lMv) { // si un autre noeud est le noeud de départ, modifier pour qu'il ne le soit plus
					mv.setStart(false);
				}
				mv.setStart(true);
				System.out.println("Set noeud de départ");
				MusicSequenceBuilder seqBuilder = new MusicSequenceBuilder();
				
				try {
					MusicSequencePlayer msp = new MusicSequencePlayer(seqBuilder.buildMusicSequence(mv, Sequence.PPQ, 960));
					msp.play();
					System.out.println("Jouer******************************************");
					mv.getMusicSample().printMusicNotes();
					
				} catch (InvalidMidiDataException e) {
					e.printStackTrace();
					System.out.println("Paramètres de séquence invalide.");
				}
				
				break;
		}
		
		action = true;
	}
	
	private void setConnectorsDirection(MusicVertex musicVertex) {
		for (Connector c : musicVertex.getConnectors()) {
			c.getTarget(musicVertex);
		}
	}
	
	public boolean isAction() {
		return action;
	}
	
}
