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
	
	private JLayeredPane layeredPane;
	private JLayeredPane mazeContainer;
	private JPanel mazeBoard;
	private JLabel couloir;
	private JLabel pawn = null;
	private int xAdjustment;
	private int yAdjustment;
	private int xOrigine;
	private int yOrigine;
	private MazeGameControlers mazeGameControler;
	private Couleur currentColor = null;
	private final int BLEU_POS = 1;
	private final int JAUNE_POS = 7;
	private final int ROUGE_POS = 43;
	private final int VERT_POS = 49;

	private final int COULOIR_LAYER = 0;
	private final int PAWN_LAYER = 1;

	public MazeGameGUI(String nom, MazeGameControlers mazeGameControler, Dimension dim) {
		// récupération des dimensions de la fenetre
		Dimension boardSize = dim;
		Icon disabledIcon = new ImageIcon(MazeImageProvider.getImageFile(
			"Couloir", 1, 1, 0, 1, true
		));

		// on initialise le controleur
		this.mazeGameControler = mazeGameControler;

		// on crée un conteneur general qui acceuillera le tableau de jeu + l'element draggé
		mazeContainer = new JLayeredPane();
		mazeContainer.setPreferredSize(boardSize);
		mazeContainer.setBounds(0, 0, boardSize.width, boardSize.height);
		getContentPane().add(mazeContainer); // on l'ajoute à la fenetre principale

		// On crée une grille de 7 par 7 (49 cases)
		mazeBoard = new JPanel(new GridLayout(7,7));

		// position et taille du plateau de jeu -> on récupère les dimensions passées en paramètres
		mazeBoard.setPreferredSize(boardSize);
		mazeBoard.setBounds(0, 0, boardSize.width, boardSize.height);

		// pour chaque case - à améliorer avec des variables si jamais on veut changer la dimension du jeu
		for(int i = 1; i <= 49; i++) {

			// on crée un panneau contenant différents plans
			layeredPane = new JLayeredPane();
			layeredPane.setPreferredSize(new Dimension(100, 100));

			// on crée une image de couloir
			couloir = new JLabel(
				new ImageIcon(MazeImageProvider.getRandomImageFile(
					"Couloir"
				))
			);
			couloir.setDisabledIcon(disabledIcon);

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
				this.pawn = new JLabel(
					new ImageIcon(MazeImageProvider.getImageFile(
						"Pion",
						currentColor
					))
				);

				// on ajoute paramètre dimension et position du couloir
				//pawn.setBorder(BorderFactory.createLineBorder(Color.red)); // debug
				this.pawn.setPreferredSize(new Dimension(100, 100));
				this.pawn.setBounds(0, 0, 100, 100);
				this.pawn.setOpaque(false);

				layeredPane.add(this.pawn, PAWN_LAYER, 0);
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

		mazeContainer.add(mazeBoard, 0);
		
		// TODO n'écouter que les pions éventuellement
		mazeBoard.addMouseListener(this);
		mazeBoard.addMouseMotionListener(this);

		// histoire d'utiliser le nom
		System.out.println(nom);

	}
	
	public void mousePressed(MouseEvent e) {
		JLayeredPane parent;
		Component componentPressed =  this.mazeBoard.findComponentAt(e.getX(), e.getY());
		boolean isOkDest = false;
		int xDest, yDest;

		this.pawn = null;

		if (componentPressed instanceof JPanel || componentPressed == null) {
			return;
		}
		// on ne prend que la couche la plus haute
		if(this.layeredPane.getLayer(componentPressed) < PAWN_LAYER) {
			return;
		}

		Point parentLocation = componentPressed.getParent().getLocation();
		xAdjustment = parentLocation.x - e.getX();
		yAdjustment = parentLocation.y - e.getY();
		this.pawn = (JLabel) componentPressed;
		xOrigine = e.getX()/(this.layeredPane.getHeight()/7);
		yOrigine = e.getY()/(this.layeredPane.getHeight()/7);
		this.pawn.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
		this.pawn.setSize(this.pawn.getWidth(), this.pawn.getHeight());
		parent = (JLayeredPane) componentPressed.getParent();

		if(parent != null) {
			this.mazeContainer.add(this.pawn, JLayeredPane.DRAG_LAYER);
			
			// TODO a reprendre pour génération chemin possible
			// on grise les cases où on ne peut pas se déplacer
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
		if (this.pawn == null) {
			return;
		}
		 this.pawn.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
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
		 //pawn.setVisible(false);

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
		
		*/

		if(pawn == null) { return; }

		Point pawnPosition = this.pawn.getLocation();
		int xPosition = pawnPosition.x;
		int yPosition = pawnPosition.y;
		Component destinationReached =  this.mazeBoard.findComponentAt(xPosition, yPosition);
		
		if(destinationReached != null) {
			JLayeredPane destinationPane = (JLayeredPane) destinationReached.getParent();
			destinationPane.add(this.pawn, PAWN_LAYER);
		}
		 	
	 	// on réautorise toutes les cases
		for (Component component : this.mazeBoard.getComponents()) {
			((JLayeredPane) component).getComponentsInLayer(COULOIR_LAYER)[0].setEnabled(true);
		}
		
		this.pawn.setVisible(true);
		this.repaint();
		this.revalidate();
	 }
}
