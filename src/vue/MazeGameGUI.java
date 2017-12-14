package vue;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import tools.MazeImageProvider;
import model.Coord;
import model.PieceIHMs;
import model.Treasure;
import model.TreasureIHMs;
import net.miginfocom.swing.MigLayout;
import model.CouloirIHM;
import model.TreasureIHM;
import controler.MazeGameControlers;

public class MazeGameGUI extends JFrame implements MouseListener,
		MouseMotionListener, Observer {

	/**
	 * default serial version uid
	 */
	private static final long serialVersionUID = 1L;

	private JLayeredPane layeredPane;
	private JLayeredPane mazeContainer;
	private JPanel generalBoard;
	private JPanel mazeBoard;
	private JLabel pawn = null;
	private int xAdjustment;
	private int yAdjustment;
	private int xOrigine;
	private int yOrigine;
	private MazeGameControlers mazeGameControler;
	private Component previouslyHoveredComponent;
	List<TreasureIHMs> treasureIHMs;

	private final Integer COULOIR_LAYER = 0;
	private final Integer TREASURE_LAYER = 1;
	private final Integer PAWN_LAYER = 2;

	public MazeGameGUI(String name, MazeGameControlers mazeGameControler,
			Dimension dim) {

		Dimension windowSize = new Dimension(950,1000);
		Icon imageIcon;
		Icon disabledIcon;
		List<CouloirIHM> couloirIHMs;
		List<PieceIHMs> pieceIHMs;
		JLabel couloir;
		JLabel treasure;
		JLabel tresorCard;
		JLabel tresorCardTwo;
		CouloirIHM extraCard;
		JLayeredPane extraCardPane;
		JLabel extraCardImage;

		// on initialise le controleur
		this.mazeGameControler = mazeGameControler;
		couloirIHMs = mazeGameControler.getCouloirsIHMs();
		pieceIHMs = mazeGameControler.getPiecesIHMs();
		treasureIHMs = mazeGameControler.getTreasuresIHMs();

		// on crée un conteneur general qui acceuillera le tableau de jeu +
		// l'element draggé
		mazeContainer = new JLayeredPane();
		mazeContainer.setPreferredSize(windowSize);
		mazeContainer.setBounds(0, 0, windowSize.width, windowSize.height);
		getContentPane().add(mazeContainer); // on l'ajoute à la fenetre
												// principale

		// On crée une grille de 2 par 2 (4 cases)
		// Le plateau sera dans la première case, les éléments de jeu dans les
		// autres
		generalBoard = new JPanel(new MigLayout());

		// On crée une grille de 7 par 7 (49 cases)
		mazeBoard = new JPanel(new GridLayout(7, 7));

		// On définit la taille de la grille générale
		generalBoard.setPreferredSize(windowSize);
		generalBoard.setBounds(0, 0, windowSize.width, windowSize.height);

		// On crée une image pour la pile des cartes des trésors
		imageIcon = new ImageIcon(
				MazeImageProvider.getImageCardTresorsFile("DosJeu"));
		// On crée la zone pour la pile de cartes
		tresorCard = new JLabel(imageIcon);

		// On crée une image pour la pile des cartes des trésors
		imageIcon = new ImageIcon(
				MazeImageProvider.getImageCardTresorsFile("TresorTrois"));
		// On crée la zone pour la pile de cartes
		tresorCardTwo = new JLabel(imageIcon);

		// On crée la carte supplémentaire, récupérant la deuxième pièce de la
		// liste
		// On garde le côté aléatoire comme la liste est aléatoire
		// Il faut la deuxième car la première est un angle de départ
		extraCard = couloirIHMs.get(1);
		// on crée un panneau contenant différents plans
		extraCardPane = new JLayeredPane();
		extraCardPane.setPreferredSize(new Dimension(100, 100));

		// on crée une image de couloir pour la pièce supplémentaire
		imageIcon = new ImageIcon(MazeImageProvider.getImageFile("Couloir",
				extraCard.isNorthOpened(), extraCard.isSouthOpened(),
				extraCard.isEastOpened(), extraCard.isWestOpened(), false));
		disabledIcon = new ImageIcon(MazeImageProvider.getImageFile("Couloir",
				extraCard.isNorthOpened(), extraCard.isSouthOpened(),
				extraCard.isEastOpened(), extraCard.isWestOpened(), true));
		extraCardImage = new JLabel(imageIcon);
		extraCardImage.setDisabledIcon(disabledIcon);

		// on paramètre la taille et la position de la pièce supplémentaire
		extraCardImage.setPreferredSize(new Dimension(100, 100));
		extraCardImage.setBounds(0, 0, 100, 100);

		// on ajoute le couloir en arrière-plan
		extraCardPane.add(extraCardImage, COULOIR_LAYER);

		// position et taille du plateau de jeu -> on récupère les dimensions passées en paramètres
		mazeBoard.setPreferredSize(dim);
		mazeBoard.setBounds(0, 0, dim.width, dim.height);

		// création des couloirs à partir du modèle
		for (CouloirIHM couloirIHM : couloirIHMs) {

			// on crée un panneau contenant différents plans
			this.layeredPane = new JLayeredPane();
			this.layeredPane.setPreferredSize(new Dimension(100, 100));

			// on crée une image de couloir
			imageIcon = new ImageIcon(
					MazeImageProvider.getImageFile("Couloir",
							couloirIHM.isNorthOpened(),
							couloirIHM.isSouthOpened(),
							couloirIHM.isEastOpened(),
							couloirIHM.isWestOpened(), false));
			disabledIcon = new ImageIcon(MazeImageProvider.getImageFile(
				"Couloir",
				couloirIHM.isNorthOpened(),
				couloirIHM.isSouthOpened(),
				couloirIHM.isEastOpened(),
				couloirIHM.isWestOpened(),
				true
			));
			couloir = new JLabel(imageIcon);
			couloir.setDisabledIcon(disabledIcon);

			// si on veut entourer les couloirs fixes
			/*
			 * if(couloirIHM.isFixed()) {
			 * this.couloir.setBorder(BorderFactory.createLineBorder
			 * (Color.blue)); }
			 */
			
			// pour chaque case on ajoute paramètre dimension et position du couloir
			couloir.setPreferredSize(new Dimension(100, 100));
			couloir.setBounds(0, 0, 100, 100);

			// on ajoute le couloir en arrière-plan
			this.layeredPane.add(couloir, COULOIR_LAYER);

			// on ajoute les différents plans au plateau
			this.mazeBoard.add(this.layeredPane);
		}

		// création des pions à partir du modèle
		for (PieceIHMs pieceIHM : pieceIHMs) {

			// si on est sur la position d'un pion
			// on crée un pion à chaque coin du jeu
			this.pawn = new JLabel(new ImageIcon(
					MazeImageProvider.getImageFile("Pion",
							pieceIHM.getCouleur())));

			this.pawn.setPreferredSize(new Dimension(100, 100));
			this.pawn.setBounds(0, 0, 100, 100);
			this.pawn.setOpaque(false);

			// TODO moche ajouter tests
			((JLayeredPane) this.mazeBoard.getComponent(pieceIHM.getX() + 7
					* pieceIHM.getY())).add(this.pawn, PAWN_LAYER);
		}
		
		for(TreasureIHMs treasureIHM : treasureIHMs){
			treasure = new JLabel (new ImageIcon(MazeImageProvider.getImageFile(treasureIHM.getTreasureName())));

			treasure.setPreferredSize(new Dimension(100, 100));
			treasure.setBounds(0, 0, 100, 100);
			treasure.setOpaque(false);
			//TODO moche ajouter tests
			((JLayeredPane)this.mazeBoard.getComponent(treasureIHM.getTreasureX() + 7*treasureIHM.getTreasureY())).add(treasure, TREASURE_LAYER);
		}
		
		generalBoard.add(mazeBoard, "pos 0 0");
		generalBoard.add(tresorCard, "pos 0.9al 0.9al");
		generalBoard.add(tresorCard, "pos 0.8al 0.9al");
		generalBoard.add(tresorCardTwo, "pos 0.5al 0.9al");
		generalBoard.add(extraCardPane, "pos 0.9al 0.3al"); // AbsoluteLayout :
															// on positionne à
															// 90% en x et 30%
															// en y
		mazeContainer.add(generalBoard);
		// TODO n'écouter que les pions éventuellement
		mazeBoard.addMouseListener(this);
		mazeBoard.addMouseMotionListener(this);

		// histoire d'utiliser le nom
		System.out.println(name);

	}

	public void mousePressed(MouseEvent e) {
		JLayeredPane parent;
		Component componentPressed =  this.mazeBoard.findComponentAt(e.getX(), e.getY());
		boolean isOkDest;
		int xDest, yDest;
		List<Coord> reacheableCoords;

		this.pawn = null;

		if (componentPressed instanceof JPanel || componentPressed == null) {
			return;
		}

		if (componentPressed != null) {
			JLayeredPane destinationPane = (JLayeredPane) componentPressed
					.getParent();

			// on ne prend que la couche la plus haute
			if (destinationPane.getLayer(componentPressed) == COULOIR_LAYER
					|| destinationPane.getLayer(componentPressed) == TREASURE_LAYER) {
				return;
			}

			Point parentLocation = componentPressed.getParent().getLocation();
			xAdjustment = parentLocation.x - e.getX();
			yAdjustment = parentLocation.y - e.getY();
			this.pawn = (JLabel) componentPressed;
			parent = (JLayeredPane) componentPressed.getParent();
			xOrigine = e.getX() / (this.mazeBoard.getHeight() / 7);
			yOrigine = e.getY() / (this.mazeBoard.getHeight() / 7);
			this.pawn.setLocation(e.getX() + xAdjustment, e.getY()
					+ yAdjustment);
			this.pawn.setSize(this.pawn.getWidth(), this.pawn.getHeight());

			if (parent != null) {
				this.mazeContainer.add(this.pawn, JLayeredPane.DRAG_LAYER);

				// TODO a reprendre pour génération chemin possible
				// on grise les cases où on ne peut pas se déplacer
				reacheableCoords = this.mazeGameControler.findPath(new Coord(xOrigine, yOrigine));
				for (Component component : this.mazeBoard.getComponents()) {
					xDest = component.getX() / (this.mazeBoard.getHeight()/7);
					yDest = component.getY() / (this.mazeBoard.getHeight()/7);
					isOkDest = false;
					for(Coord coord : reacheableCoords) {
						if(coord.x == xDest && coord.y == yDest) {
							isOkDest = true;
						}
					}
					if(!isOkDest) {
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
		 Component hoveredComponent;
		 JLayeredPane layeredPane;
		 JLabel corridorImage;

		if (this.pawn == null) {
			return;
		}
		this.pawn.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);

		hoveredComponent = mazeBoard.getComponentAt(me.getX(), me.getY());

		 // si on est en dehors du plateau
		 if(hoveredComponent == null) {
			 return;
		 }

		 // affichage d'effets au survol d'une case
		 if(previouslyHoveredComponent == null || !previouslyHoveredComponent.equals(hoveredComponent)){
			 if(previouslyHoveredComponent != null) {
				 layeredPane = (JLayeredPane) previouslyHoveredComponent;
				 corridorImage = (JLabel) layeredPane.getComponentsInLayer(COULOIR_LAYER)[0];
				 corridorImage.setBorder(null);
			 }

			 previouslyHoveredComponent = hoveredComponent;

			layeredPane = (JLayeredPane) previouslyHoveredComponent;
			 // FIXME moche
			 corridorImage = (JLabel) layeredPane.getComponentsInLayer(COULOIR_LAYER)[0];
			 corridorImage.setBorder(BorderFactory.createLineBorder(Color.yellow));

		 }
	 }

	 public void mouseReleased(MouseEvent e) {
		 //mazeBoard.getHeight() donne la hauteur en pixels de la fenêtre
		 //on divise getX() par layeredPane.getHeight()/7 pour savoir dans quelle case on a déplacé la pièce
		 int destinationX = e.getX()/(mazeBoard.getHeight()/7);
		 int destinationY = e.getY()/(mazeBoard.getHeight()/7);

		 JLayeredPane layeredPane;
		 JLayeredPane parentComponentHere;
		 JLabel corridorImage;

		 if (pawn == null) {
			 return;
		 }

		// on retire l'effet visuel du hover
		 layeredPane = (JLayeredPane) previouslyHoveredComponent;
		 corridorImage = (JLabel) layeredPane.getComponentsInLayer(COULOIR_LAYER)[0];
		 corridorImage.setBorder(null);

		 boolean isMoveOK = mazeGameControler.move(
			 new Coord(xOrigine, yOrigine),
			 new Coord(destinationX, destinationY)
		 );

		if (isMoveOK) {
			System.out.println("déplacement OK");
			Component componentHere = this.mazeBoard.findComponentAt(e.getX(),
					e.getY());
			parentComponentHere = (JLayeredPane) componentHere.getParent();
			if (parentComponentHere.getComponentsInLayer(TREASURE_LAYER).length > 0) {
				Treasure treasureToCatch = this.mazeGameControler
						.currentTreasureToCatch();
				System.out.println(treasureToCatch);
				if (destinationX == treasureToCatch.getTreasureX()
						&& destinationY == treasureToCatch.getTreasureY()) {
					this.mazeGameControler.treasureCatchedPlateau(treasureToCatch);
					System.out.println("Le score du joueur actuel est : " + mazeGameControler.getCurrentScorePlayer());
					this.mazeGameControler.setCurrentTreasureToCatch(null);

				}
			}
		}
	 }

	 public void mouseClicked(MouseEvent e) {}
	 public void mouseMoved(MouseEvent e) {}
	 public void mouseEntered(MouseEvent e) {}
	 public void mouseExited(MouseEvent e) {}

	@Override
	public void update(Observable o, Object arg) {
		if(((LinkedList<PieceIHMs>)arg).getFirst() instanceof PieceIHMs) {
			List<PieceIHMs> piecesIHM = (List<PieceIHMs>) arg;
			for (PieceIHMs pieceIHM : piecesIHM) {
				//On récupère la piece sur le board (son layerded pane)
				this.layeredPane = (JLayeredPane) this.mazeBoard.getComponent(7
						* pieceIHM.getY() + pieceIHM.getX());

				//On enlève le pion
				if (this.layeredPane.getComponentsInLayer(PAWN_LAYER).length != 0) {
					for (int i = 0; i < this.layeredPane
							.getComponentsInLayer(PAWN_LAYER).length; i++) {
						this.layeredPane.remove(this.layeredPane
								.getComponentsInLayer(PAWN_LAYER)[i]);
					}
				}
				// on enlève le pion du drag layer
				if (this.mazeContainer
						.getComponentsInLayer(JLayeredPane.DRAG_LAYER).length != 0) {
					for (int i = 0; i < this.mazeContainer
							.getComponentsInLayer(JLayeredPane.DRAG_LAYER).length; i++) {
						this.mazeContainer.remove(this.mazeContainer
								.getComponentsInLayer(JLayeredPane.DRAG_LAYER)[i]);
					}
				}

				// on recrée le pion
				this.pawn = new JLabel(new ImageIcon(
						MazeImageProvider.getImageFile("Pion",
								pieceIHM.getCouleur())));
				this.pawn.setPreferredSize(new Dimension(100, 100));
				this.pawn.setBounds(0, 0, 100, 100);
				this.pawn.setOpaque(false);

				// on rajoute le pion
				this.layeredPane.add(this.pawn, PAWN_LAYER);
			}
		}

		if(((LinkedList<TreasureIHMs>)arg).getFirst() instanceof TreasureIHMs) {
			List<TreasureIHMs> updatedList = (List<TreasureIHMs>) arg;
			JLabel treasure;

			//Suppression des trésors
			for (TreasureIHMs treasureIHM : treasureIHMs) {
				// on récupère le trésor
				this.layeredPane = (JLayeredPane) this.mazeBoard.getComponent(7
						* treasureIHM.getTreasureY() + treasureIHM.getTreasureX());

				//On le supprime
				if (this.layeredPane.getComponentsInLayer(TREASURE_LAYER).length != 0) {
					for (int i = 0; i < this.layeredPane
							.getComponentsInLayer(TREASURE_LAYER).length; i++) {
						this.layeredPane.remove(this.layeredPane
								.getComponentsInLayer(TREASURE_LAYER)[i]);
					}
				}
			}

			//Re-creaction des tresors
			for (TreasureIHMs treasureIHM : updatedList) {
				// on récupère le trésor
				this.layeredPane = (JLayeredPane) this.mazeBoard.getComponent(7
						* treasureIHM.getTreasureY() + treasureIHM.getTreasureX());

				//On le supprime
				if (this.layeredPane.getComponentsInLayer(TREASURE_LAYER).length != 0) {
					for (int i = 0; i < this.layeredPane
							.getComponentsInLayer(TREASURE_LAYER).length; i++) {
						this.layeredPane.remove(this.layeredPane
								.getComponentsInLayer(TREASURE_LAYER)[i]);
					}
				}

				//On recrée le trésor
				treasure = new JLabel(new ImageIcon(
						MazeImageProvider.getImageFile(treasureIHM
								.getTreasureName())));
				treasure.setPreferredSize(new Dimension(100, 100));
				treasure.setBounds(0, 0, 100, 100);
				treasure.setOpaque(false);
				// TODO moche ajouter tests
				((JLayeredPane) this.mazeBoard.getComponent(treasureIHM
						.getTreasureX() + 7 * treasureIHM.getTreasureY())).add(
						treasure, TREASURE_LAYER);
			}

		}

		//On réautorise toutes les cases
		for (Component component : this.mazeBoard.getComponents()) {
			if (((JLayeredPane) component).getComponentsInLayer(COULOIR_LAYER).length > 0) {
				((JLayeredPane) component).getComponentsInLayer(COULOIR_LAYER)[0]
						.setEnabled(true);
			}
		}

		this.repaint();
		this.revalidate();
	}
}
