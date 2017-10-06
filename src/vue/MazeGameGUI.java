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
	JPanel chessBoard;
	JLabel chessPiece;
	int xAdjustment;
	int yAdjustment;
	int xOrigine;
	int yOrigine;
	MazeGameControlers chessGameControler;
	Couleur currentColor = null;
	
	public MazeGameGUI(String nom, MazeGameControlers chessGameControler, Dimension dim){
			
		Dimension boardSize = dim;
		this.chessGameControler = chessGameControler;
		 
		//  Use a Layered Pane for this this application
		layeredPane = new JLayeredPane();
		getContentPane().add(layeredPane);
		layeredPane.setPreferredSize(boardSize);
		layeredPane.addMouseListener(this);
		layeredPane.addMouseMotionListener(this);
		 
		//Add a chess board to the Layered Pane 
		  
		chessBoard = new JPanel();
		layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
		chessBoard.setLayout( new GridLayout(7, 7) );
		chessBoard.setPreferredSize( boardSize );
		chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);
		 
		for (int i = 0; i < 49; i++) {
			 JPanel square = new JPanel( new BorderLayout() );
			 chessBoard.add( square );
			 square.setBackground(Color.white);
		  }
		System.out.println("Fin de génération de l'échiquier");
		  
	  }
	
	public void mousePressed(MouseEvent e){
		chessPiece = null;
		Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
		boolean isOkDest = false;
		int xDest, yDest;
		 
		if (c instanceof JPanel){
			return;
		}
			 
		Point parentLocation = c.getParent().getLocation();
		xAdjustment = parentLocation.x - e.getX();
		yAdjustment = parentLocation.y - e.getY();
		chessPiece = (JLabel) c;
		xOrigine = e.getX()/(layeredPane.getHeight()/7);
		yOrigine = e.getY()/(layeredPane.getHeight()/7);
		  
		chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
		chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
		layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
		for(Component component : chessBoard.getComponents()){
			 xDest = component.getX()/(layeredPane.getHeight()/7);
			 yDest = component.getY()/(layeredPane.getHeight()/7);
			 isOkDest = this.chessGameControler.isMoveOk(xOrigine,yOrigine,xDest,yDest); //moche ne doit pas êter public
			 if(isOkDest){
				 component.setBackground(Color.GREEN);
			 }
		}
	}
	
	//Move the chess piece around
	  
	 public void mouseDragged(MouseEvent me) {
		if (chessPiece == null) return;
		chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
	 }
	 
	 //Drop the chess piece back onto the chess board
	 
	 public void mouseReleased(MouseEvent e) {
		 //layeredPane.getHeight() donne la hauteur en pixels de la fenêtre 
		 //getX() donne X en pixels
		 //on divise getX() par layeredPane.getHeight()/7 pour savoir dans quelle case on a déplacé la pièce
		 int destinationX = e.getX()/(layeredPane.getHeight()/7);
		 int destinationY = e.getY()/(layeredPane.getHeight()/7);
		  
		 if(chessPiece == null) return;

		 chessPiece.setVisible(false);
		 //Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
		 
		 boolean isMoveOK = chessGameControler.move(
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
		// debug
		 //System.out.println("update");
		List<PieceIHM> piecesIHM = (List<PieceIHM>) arg;
		for(int i = 0; i < piecesIHM.size(); i++){
			  JPanel panel = (JPanel) chessBoard.getComponent(
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
			  JPanel panel = (JPanel) chessBoard.getComponent(
					  7 * piecesIHM.get(i).getY() + piecesIHM.get(i).getX()
			  );
			  panel.add(pieceLabel);
		}
		// reset board colors
		for(int i = 0; i < chessBoard.getComponents().length; i++){
			chessBoard.getComponent(i).setBackground(Color.white);
		}
		this.repaint();
		this.revalidate();
	 }
}