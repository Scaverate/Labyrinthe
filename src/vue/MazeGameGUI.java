package vue;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import tools.MazeImageProvider;
import model.Coord;
import model.Couleur;
import model.PieceIHMs;
import model.CouloirIHM;
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

	private final Integer COULOIR_LAYER = new Integer(0);
	private final Integer PAWN_LAYER = new Integer(1);


	public MazeGameGUI(String nom, MazeGameControlers mazeGameControler, Dimension dim) {

		// récupération des dimensions de la fenetre
		Dimension boardSize = dim;
		Icon imageIcon;
		Icon disabledIcon;
		List<CouloirIHM> couloirIHMs;
		List<PieceIHMs> pieceIHMs;

		// on initialise le controleur
		this.mazeGameControler = mazeGameControler;
		couloirIHMs = mazeGameControler.getCouloirsIHMs();
		pieceIHMs = mazeGameControler.getPiecesIHMs();

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

		// création des couloirs à partir du modèle
		for(CouloirIHM couloirIHM : couloirIHMs) {

			// on crée un panneau contenant différents plans
			this.layeredPane = new JLayeredPane();
			this.layeredPane.setPreferredSize(new Dimension(100, 100));

			// on crée une image de couloir
			imageIcon = new ImageIcon(MazeImageProvider.getImageFile(
				"Couloir",
				couloirIHM.isNorthOpened(),
				couloirIHM.isSouthOpened(),
				couloirIHM.isEastOpened(),
				couloirIHM.isWestOpened(),
				false
			));
			disabledIcon = new ImageIcon(MazeImageProvider.getImageFile(
				"Couloir",
				couloirIHM.isNorthOpened(),
				couloirIHM.isSouthOpened(),
				couloirIHM.isEastOpened(),
				couloirIHM.isWestOpened(),
				true
			));
			this.couloir = new JLabel(imageIcon);
			this.couloir.setDisabledIcon(disabledIcon);

			// si on veut entourer les couloirs fixes
			/*
			if(couloirIHM.isFixed()) {
				this.couloir.setBorder(BorderFactory.createLineBorder(Color.blue));
			}
			*/

			// pour chaque case on ajoute paramètre dimension et position du couloir
			this.couloir.setPreferredSize(new Dimension(100, 100));
			this.couloir.setBounds(0, 0, 100, 100);

			// on ajoute le couloir en arrière-plan
			this.layeredPane.add(this.couloir, COULOIR_LAYER);

			// on ajoute les différents plans au plateau
			this.mazeBoard.add(this.layeredPane);
		}

		// création des pions à partir du modèle
		for(PieceIHMs pieceIHM : pieceIHMs) {

			// si on est sur la position d'un pion
			// on crée un pion à chaque coin du jeu
			this.pawn = new JLabel(
					new ImageIcon(MazeImageProvider.getImageFile(
							"Pion",
							pieceIHM.getCouleur()
					))
			);

			this.pawn.setPreferredSize(new Dimension(100, 100));
			this.pawn.setBounds(0, 0, 100, 100);
			this.pawn.setOpaque(false);

			//TODO moche ajouter tests
			((JLayeredPane)this.mazeBoard.getComponent(pieceIHM.getX() + 7*pieceIHM.getY())).add(this.pawn, PAWN_LAYER);
		}

		mazeContainer.add(mazeBoard, new Integer(0));
		
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

		if(componentPressed instanceof JPanel || componentPressed == null) {
			return;
		}

		if(componentPressed != null) {
			JLayeredPane destinationPane = (JLayeredPane) componentPressed.getParent();

			// on ne prend que la couche la plus haute
			if(destinationPane.getLayer(componentPressed) == COULOIR_LAYER) {
				return;
			}

			Point parentLocation = componentPressed.getParent().getLocation();
			xAdjustment = parentLocation.x - e.getX();
			yAdjustment = parentLocation.y - e.getY();
			this.pawn = (JLabel) componentPressed;
			parent = (JLayeredPane) componentPressed.getParent();
			xOrigine = e.getX()/(this.mazeBoard.getHeight()/7);
			yOrigine = e.getY()/(this.mazeBoard.getHeight()/7);
			this.pawn.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
			this.pawn.setSize(this.pawn.getWidth(), this.pawn.getHeight());

			if(parent != null) {
				this.mazeContainer.add(this.pawn, JLayeredPane.DRAG_LAYER);

				// TODO a reprendre pour génération chemin possible
				// on grise les cases où on ne peut pas se déplacer
				for (Component component : this.mazeBoard.getComponents()) {
					xDest = component.getX() / (parent.getHeight() / 7);
					yDest = component.getY() / (parent.getHeight() / 7);
					isOkDest = this.mazeGameControler.isMoveOk(xOrigine, yOrigine, xDest, yDest); // moche ne doit pas être public
					if(isOkDest) {
						if(((JLayeredPane) component).getComponentsInLayer(COULOIR_LAYER).length > 0) {
							//TODO moche ajouter un test
							((JLayeredPane) component).getComponentsInLayer(COULOIR_LAYER)[0].setEnabled(false);
						}
					}
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
		List<PieceIHMs> piecesIHM = (List<PieceIHMs>) arg;
	 	for(PieceIHMs pieceIHM : piecesIHM) {

			this.layeredPane = (JLayeredPane) this.mazeBoard.getComponent(
				7 * pieceIHM.getY() + pieceIHM.getX()
			);

			if(this.layeredPane.getComponentsInLayer(PAWN_LAYER).length != 0) {
				for(int i = 0; i < this.layeredPane.getComponentsInLayer(PAWN_LAYER).length; i++) {
					this.layeredPane.remove(this.layeredPane.getComponentsInLayer(PAWN_LAYER)[i]);
				}
			}

			if(this.mazeContainer.getComponentsInLayer(JLayeredPane.DRAG_LAYER).length != 0) {
				for(int i = 0; i < this.mazeContainer.getComponentsInLayer(JLayeredPane.DRAG_LAYER).length; i++) {
					this.mazeContainer.remove(this.mazeContainer.getComponentsInLayer(JLayeredPane.DRAG_LAYER)[i]);
				}
			}

			this.pawn = new JLabel(
				new ImageIcon(MazeImageProvider.getImageFile(
					"Pion",
					pieceIHM.getCouleur()
				))
			);

			this.pawn.setPreferredSize(new Dimension(100, 100));
			this.pawn.setBounds(0, 0, 100, 100);
			this.pawn.setOpaque(false);

			this.layeredPane.add(this.pawn, PAWN_LAYER);
		}

		 // on réautorise toutes les cases
		for (Component component : this.mazeBoard.getComponents()) {
			if(((JLayeredPane) component).getComponentsInLayer(COULOIR_LAYER).length > 0) {
				((JLayeredPane) component).getComponentsInLayer(COULOIR_LAYER)[0].setEnabled(true);
			}
		}

		this.repaint();
		this.revalidate();
	 }
}
