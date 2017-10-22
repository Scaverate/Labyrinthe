package vue;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import tools.MazeImageProvider;
import model.Coord;
import model.Couleur;
import model.PieceIHM;
import controler.MazeGameControlers;

public class MazeGameGUI extends JFrame implements MouseListener, MouseMotionListener, Observer {

	/**
	 * default serial version uid
	 */
	private static final long serialVersionUID = 1L;

	JLayeredPane layeredPane;
	JPanel mazeBoard;
	JLabel mazePiece;
	int xAdjustment;
	int yAdjustment;
	int xOrigine;
	int yOrigine;
	MazeGameControlers mazeGameControler;
	Couleur currentColor = null;
	public MazeGameGUI(String nom, MazeGameControlers mazeGameControler, Dimension dim){
		Dimension boardSize = dim;

		// on initialise le controleur
		this.mazeGameControler = mazeGameControler;

		//  Use a Layered Pane for this this application
		layeredPane = new JLayeredPane();
		getContentPane().add(layeredPane);
		layeredPane.setPreferredSize(boardSize);
		layeredPane.addMouseListener(this);
		layeredPane.addMouseMotionListener(this);

		//Add a maze board to the Layered Pane
		mazeBoard = new JPanel();
		layeredPane.add(mazeBoard, JLayeredPane.DEFAULT_LAYER);
		mazeBoard.setLayout(new GridLayout(7, 7));
		mazeBoard.setPreferredSize( boardSize );
		mazeBoard.setBounds(0, 0, boardSize.width, boardSize.height);
		// generation du plateau de jeu
		for (int i = 0; i < 49; i++) {
		 	JPanel square = new JPanel(new BorderLayout());
			mazeBoard.add(square);
			//square.setBackground(Color.white);
			JLabel couloir = new JLabel(
				new ImageIcon(MazeImageProvider.getRandomImageFile(
					"Couloir"
				))
			);

			square.add(couloir);
		  }
	  }

	public void mousePressed(MouseEvent e){
		mazePiece = null;
		Component c =  mazeBoard.findComponentAt(e.getX(), e.getY());
		boolean isOkDest = false;
		int xDest, yDest;

		pawn = null;

		if (c instanceof JPanel || c == null) {
			return;
		}
		// on ne prend que la couche la plus haute
		if(this.layeredPane.getLayer(c) < PAWN_LAYER) {
			return;
		}

		Point parentLocation = c.getParent().getLocation();
		xAdjustment = parentLocation.x - e.getX();
		yAdjustment = parentLocation.y - e.getY();
		pawn = (JLabel) c;
		xOrigine = e.getX()/(this.layeredPane.getHeight()/7);
		yOrigine = e.getY()/(this.layeredPane.getHeight()/7);
		pawn.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
		pawn.setSize(pawn.getWidth(), pawn.getHeight());
		parent = (JLayeredPane) c.getParent();

		if(parent != null) {
			MazeContainer.add(pawn, JLayeredPane.DRAG_LAYER);
			for (Component component : this.mazeBoard.getComponents()) {
				xDest = component.getX() / (parent.getHeight() / 7);
				yDest = component.getY() / (parent.getHeight() / 7);
				isOkDest = this.mazeGameControler.isMoveOk(xOrigine, yOrigine, xDest, yDest); // moche ne doit pas être public
				if(isOkDest) {
					((JLayeredPane) component).getComponentsInLayer(COULOIR_LAYER)[0].setEnabled(false);
				}
			}
		}
	}

	 public void mouseDragged(MouseEvent me) {
		if (pawn == null) {
			return;
		}
		 pawn.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
	 }

	 public void mouseReleased(MouseEvent e) {
		 //mazeBoard.getHeight() donne la hauteur en pixels de la fenêtre
		 //on divise getX() par layeredPane.getHeight()/7 pour savoir dans quelle case on a déplacé la pièce
		 int destinationX = e.getX()/(mazeBoard.getHeight()/7);
		 int destinationY = e.getY()/(mazeBoard.getHeight()/7);

		 if (pawn == null) {
			 return;
		 }

		 // on cache le composant avant la mise à jour de la vue dans "update"
		 pawn.setVisible(false);

		 boolean isMoveOK = mazeGameControler.move(
			 new Coord(xOrigine, yOrigine),
			 new Coord(destinationX, destinationY)
		 );

		 if(isMoveOK) {
		 	System.out.println("déplacement OK");
		 }
	 }

	 public void mouseClicked(MouseEvent e) {}
	 public void mouseMoved(MouseEvent e) {}
	 public void mouseEntered(MouseEvent e) {}
	 public void mouseExited(MouseEvent e) {}

	 @Override
	 public void update(Observable o, Object arg) {
		/*
		List<PieceIHM> piecesIHM = (List<PieceIHM>) arg;
		for(int i = 0; i < piecesIHM.size(); i++){
			  JPanel panel = (JPanel) mazeBoard.getComponent(
					  7 * piecesIHM.get(i).getY() + piecesIHM.get(i).getX()
			  );
			  if(panel.getComponents().length != 0) {
				  panel.removeAll();
			  }
		}
		for(int i = 0; i < piecesIHM.size(); i++){
			  JLabel pieceLabel = new JLabel(
				  new ImageIcon(MazeImageProvider.getImageFile(
					  piecesIHM.get(i).getNamePiece(),
					  piecesIHM.get(i).getCouleur())
				  )
			  );
			  JPanel panel = (JPanel) mazeBoard.getComponent(
					  7 * piecesIHM.get(i).getY() + piecesIHM.get(i).getX()
			  );
			  
			  panel.add(pieceLabel);
		}
		// reset board colors
		for(int i = 0; i < mazeBoard.getComponents().length; i++){
			mazeBoard.getComponent(i).setBackground(Color.white);
		}
		
		
		this.repaint();
		this.revalidate();
		*/
	 }
}
