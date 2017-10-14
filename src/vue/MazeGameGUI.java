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
	JLabel couloir;
	JLabel pawn;
	int xAdjustment;
	int yAdjustment;
	int xOrigine;
	int yOrigine;
	MazeGameControlers mazeGameControler;
	Couleur currentColor = null;
	final int BLEU_POS = 1;
	final int JAUNE_POS = 7;
	final int ROUGE_POS = 43;
	final int VERT_POS = 49;
	
	public MazeGameGUI(String nom, MazeGameControlers mazeGameControler, Dimension dim) {
		// récupération des dimensions de la fenetre
		Dimension boardSize = dim;

		// on initialise le controleur
		this.mazeGameControler = mazeGameControler;
		 
		// On crée une grille de 7 par 7 (49 cases)
		mazeBoard = new JPanel(new GridLayout(7,7));
		getContentPane().add(mazeBoard); // on l'ajoute à la fenetre principale

		// position et taille du plateau de jeu -> on récupère les dimensions passées en paramètres
		mazeBoard.setPreferredSize(boardSize);
		mazeBoard.setBounds(0, 0, boardSize.width, boardSize.height);

		// pour chaque case - à améliorer avec des variables si jamais on veut changer la dimension du jeu
		for(int i = 1; i <= 49; i++) {

			// on crée un panneau contenant différents plans
			layeredPane = new JLayeredPane();
			layeredPane.setPreferredSize(new Dimension(100, 100));
			layeredPane.addMouseListener(this);
			layeredPane.addMouseMotionListener(this);

			// on crée une image de couloir
			couloir = new JLabel(
				new ImageIcon(MazeImageProvider.getRandomImageFile(
					"Couloir"
				))
			);

			// en dur pour l'instant - gestion des pions
			// on crée un pion à chaque coin du jeu
			if(i == BLEU_POS || i == JAUNE_POS || i == ROUGE_POS || i == VERT_POS) {
				switch(i){
					case BLEU_POS : {
						currentColor = Couleur.BLEU;
						break;
					}
					case JAUNE_POS : {
						currentColor = Couleur.JAUNE;
						break;
					}
					case ROUGE_POS : {
						currentColor = Couleur.ROUGE;
						break;
					}
					case VERT_POS : {
						currentColor = Couleur.VERT;
						break;
					}
					default : { break; }
				}
				pawn = new JLabel(
					new ImageIcon(MazeImageProvider.getImageFile(
						"Pion",
						currentColor
					))
				);

				// on ajoute paramètre dimension et position du couloir
				//pawn.setBorder(BorderFactory.createLineBorder(Color.red)); // debug
				pawn.setPreferredSize(new Dimension(100, 100));
				pawn.setBounds(0, 0, 100, 100);
				pawn.setOpaque(false);

				layeredPane.add(pawn, new Integer(1), 0);
			}

			// pour chaque case on ajoute paramètre dimension et position du couloir
			//couloir.setBorder(BorderFactory.createLineBorder(Color.yellow)); //debug
			couloir.setPreferredSize(new Dimension(100, 100));
			couloir.setBounds(0, 0, 100, 100);

			// on ajoute le couloir en arrière-plan
			layeredPane.add(couloir, new Integer(0), 0);

			// on ajoute les différents plans au plateau
			mazeBoard.add(layeredPane);
		}

		// histoire d'utiliser le nom
		System.out.println(nom);
	  }
	
	public void mousePressed(MouseEvent e){
		/*
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
			 isOkDest = this.mazeGameControler.isMoveOk(xOrigine,yOrigine,xDest,yDest); //moche ne doit pas être public
			 if(isOkDest){
				 component.setBackground(Color.GREEN);
			 }
		}
		*/
	}

	 public void mouseDragged(MouseEvent me) {
		 /*
		if (mazePiece == null) return;
		mazePiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
		*/
	 }

	 public void mouseReleased(MouseEvent e) {
		 /*
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
		 */
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