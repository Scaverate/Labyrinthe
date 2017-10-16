package vue;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import tools.MazeImageProvider;
import model.AbstractPiece;
import model.Coord;
import model.Couleur;
import model.PieceIHM;
import model.observable.MazeGame;
import controler.MazeGameControlers;
import controler.controlerLocal.MazeGameControler;

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
		mazeBoard.setLayout( new GridLayout(7, 7) );
		mazeBoard.setPreferredSize( boardSize );
		mazeBoard.setBounds(0, 0, boardSize.width, boardSize.height);
		mazeBoard.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		for (int i = 0; i < 49; i++) {
			 JPanel square = new JPanel( new BorderLayout() );
			 square.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
			 mazeBoard.add( square );
		  }		  
	  }
	
	public void mousePressed(MouseEvent e){
		mazePiece = null;
		Component c =  mazeBoard.findComponentAt(e.getX(), e.getY());
		boolean isOkDest = false;
		int xDest, yDest;
		 
		if (c instanceof JPanel){
			return;
		}
			 
		Point parentLocation = c.getParent().getLocation();
		xAdjustment = parentLocation.x - e.getX();
		yAdjustment = parentLocation.y - e.getY();
		mazePiece = (JLabel) c;
		xOrigine = e.getX()/(layeredPane.getHeight()/7);
		yOrigine = e.getY()/(layeredPane.getHeight()/7);
		  
		mazePiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
		mazePiece.setSize(mazePiece.getWidth(), mazePiece.getHeight());
		layeredPane.add(mazePiece, JLayeredPane.DRAG_LAYER);
		for(Component component : mazeBoard.getComponents()){
			 xDest = component.getX()/(layeredPane.getHeight()/7);
			 yDest = component.getY()/(layeredPane.getHeight()/7);
			 isOkDest = this.mazeGameControler.isMoveOk(xOrigine,yOrigine,xDest,yDest); //moche ne doit pas êter public
			 if(isOkDest){
				 component.setBackground(Color.GREEN);
			 }
		}
	}
	
	//Move the maze piece around
	  
	 public void mouseDragged(MouseEvent me) {
		if (mazePiece == null) return;
		mazePiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
	 }
	 
	 //Drop the maze piece back onto the maze board
	 
	 public void mouseReleased(MouseEvent e) {
		 //layeredPane.getHeight() donne la hauteur en pixels de la fenêtre 
		 //getX() donne X en pixels
		 //on divise getX() par layeredPane.getHeight()/7 pour savoir dans quelle case on a déplacé la pièce
		 int destinationX = e.getX()/(layeredPane.getHeight()/7);
		 int destinationY = e.getY()/(layeredPane.getHeight()/7);
		  
		 if(mazePiece == null) return;

		 mazePiece.setVisible(false);
		 //Component c =  mazeBoard.findComponentAt(e.getX(), e.getY());
		 
		 boolean isMoveOK = mazeGameControler.move(
			 new Coord(xOrigine, yOrigine),
			 new Coord(destinationX, destinationY)
		 );
		 
		 if(isMoveOK){
		 	System.out.println("déplacement OK");
		 }
		 
	 }
	  
	 public void mouseClicked(MouseEvent e) {}
	 public void mouseMoved(MouseEvent e) {}
	 public void mouseEntered(MouseEvent e) {}
	 public void mouseExited(MouseEvent e) {}

	 @Override
	 public void update(Observable o, Object arg) {
		
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
	 }
}