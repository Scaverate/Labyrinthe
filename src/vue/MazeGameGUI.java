package vue;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import model.*;
import tools.MazeImageProvider;
import net.miginfocom.swing.MigLayout;
import model.observable.MazeGame;
import controler.MazeGameControlers;
import controler.controlerLocal.MazeGameControler;

public class MazeGameGUI extends JFrame implements MouseListener, MouseMotionListener, Observer {

	private static final long serialVersionUID = 1L;
	private MazeGameControlers mazeGameControler;
	private JLayeredPane layeredPane;
	private JLabel tresorToCatch;
	private JLabel bgGame;
	private Icon bg;
	private JLayeredPane mazeContainer;
	private JPanel generalBoard;
	private JPanel menu;
	private JPanel scores;
	private JPanel activePlayer;
	private Icon imageTreasureToCatch;
	private Box b1,b2,b3;
	private JPanel mazeBoard;
	private JLabel pawn = null;
	private JLabel scoreMario;
	private JLabel scoreYoshi;
	private JLabel scoreLuigi;
	private JLabel scoreToad;
	private JLabel player,playerText;
	private ImagePanel contentPane;
	private int xAdjustment;
	private JButton rotateLeftButton, rotateRightButton;
	private JLayeredPane extraCardPane;
	private JButton okButton; 
	private JRadioButton nb2Button, nb3Button, nb4Button;
	private int yAdjustment;
	private int xOrigine;
	private ButtonGroup grpButton;
	private int yOrigine;
	private int nbPlayer = 2;
	private Component previouslyHoveredComponent;
	private List<TreasureIHMs> treasureIHMs;
	private List<CouloirIHM> couloirIHMs;
	private Dimension dim;
	private final Integer COULOIR_LAYER = 0;
	private final Integer TREASURE_LAYER = 1;
	private final Integer PAWN_LAYER = 2;
	private JFrame f1;
	private boolean mazeAltered = false;

	
	public MazeGameGUI(Dimension dim) {
		
		this.dim = dim;
		Dimension windowSize = new Dimension(950,700);
		
		// on cree un conteneur general qui acceuillera le tableau de jeu + l'element dragge
		mazeContainer = new JLayeredPane();
		mazeContainer.setPreferredSize(windowSize);
		mazeContainer.setBounds(0, 0, windowSize.width, windowSize.height);
		Font myFont = new Font("Calibri", Font.ITALIC | Font.BOLD, 18);
		// on cree le container du menu
	    b1 = Box.createHorizontalBox();
		b1.setOpaque(true); // background gris desactive
		grpButton = new ButtonGroup();
		nb2Button = new JRadioButton("2 JOUEURS");
		nb3Button = new JRadioButton("3 JOUEURS");
		nb4Button = new JRadioButton("4 JOUEURS");
		
		nb2Button.setFont(myFont);
		nb3Button.setFont(myFont);
		nb4Button.setFont(myFont);
		
		nb2Button.setForeground(Color.WHITE);
		nb2Button.setBackground(Color.BLACK);
		Border lineRadio1 = new LineBorder(Color.WHITE);
		Border marginRadio1 = new EmptyBorder(8, 35, 8, 35);
		Border compoundRadio1 = new CompoundBorder(lineRadio1, marginRadio1);
		nb2Button.setBorder(compoundRadio1);

		nb3Button.setForeground(Color.WHITE);
		nb3Button.setBackground(Color.BLACK);

		Border lineRadio2 = new LineBorder(Color.WHITE);
		Border marginRadio2 = new EmptyBorder(8, 35, 8, 35);
		Border compoundRadio2 = new CompoundBorder(lineRadio2, marginRadio2);
		nb3Button.setBorder(compoundRadio2);
		
		nb4Button.setForeground(Color.WHITE);
		nb4Button.setBackground(Color.BLACK);
		Border lineRadio3 = new LineBorder(Color.WHITE);
		Border marginRadio3 = new EmptyBorder(8, 35, 8, 35);
		Border compoundRadio3 = new CompoundBorder(lineRadio3, marginRadio3);
		nb4Button.setBorder(compoundRadio3);
		
		// debug background color btn on Mac 
		nb2Button.setOpaque(true); 
		nb3Button.setOpaque(true); 
		nb4Button.setOpaque(true);
		nb3Button.setOpaque(true); 
		
		// ajout des boutons radio dans le groupe bg
		grpButton.add(nb2Button);
		grpButton.add(nb3Button);
		grpButton.add(nb4Button);
		
		nb2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nbPlayer = 2;
			}
		});
		nb3Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nbPlayer = 3;
			}
		});
		nb4Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nbPlayer = 4;
			}
		});
		b1.add(nb2Button);
		b1.add(nb3Button);
		b1.add(nb4Button);
		b1.setBorder(BorderFactory.createMatteBorder(
                1, 1, 1, 1, Color.WHITE));
		nb2Button.setSelected(true);
		
		b2 = Box.createHorizontalBox();
		b2.setOpaque(false); // background gris desactive
		// Lancer le jeu
		
		okButton = new JButton("JOUER");
		okButton.setForeground(Color.WHITE);
		okButton.setBackground(Color.BLACK);
		okButton.setOpaque(true);
		Border line = new LineBorder(Color.WHITE);
		Border margin = new EmptyBorder(8, 35, 8, 35);
		Border compound = new CompoundBorder(line, margin);
		okButton.setBorder(compound);
		
		
		
		okButton.setFont(myFont);
		okButton.setIcon(new ImageIcon(getClass().getResource("../images/icon_play.png")));
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initMazeGame();
			}			
		});
		b2.add(okButton);
			
		b3 = Box.createVerticalBox();
		b3.setOpaque(false); // background gris desactive
		b3.add(b1);
		b3.add(b2);
				
		b3.setBorder(new EmptyBorder(100, 0, 0, 0));
		b2.setBorder(new EmptyBorder(10,0,0,0));
		
		File g = new File("");
		String path = "/src/images/";
		String ret = g.getAbsolutePath() + path + "bg.jpg";
		
		//ImagePanel panel = new ImagePanel(new ImageIcon(ret).getImage());	
	   	contentPane = new ImagePanel(new ImageIcon(ret).getImage());
	    contentPane.add(b3);
		contentPane.setPreferredSize(windowSize);
		setContentPane(contentPane);	
	}
	
	public void initMazeGame() {
		Dimension windowSize = new Dimension(950,1000);		
		Icon imageIcon;
		Icon disabledIcon;
		List<PieceIHMs> pieceIHMs;
		JLabel couloir;
		JLabel treasure;
		JLabel tresorCard;
		CouloirIHM extraCard;
		JLabel extraCardImage;
		final MazeGame mazeGame;
		final MazeGameControlers mazeGameControler;
		
		
	   	setContentPane(mazeContainer);
	   	
		repaint();
		pack();

		mazeGame = new MazeGame(nbPlayer);
		mazeGameControler = new MazeGameControler(mazeGame);
		this.mazeGameControler = mazeGameControler;
		mazeGame.addObserver((Observer) this);
		// on initialise le controleur
		couloirIHMs = mazeGameControler.getCouloirsIHMs();
		pieceIHMs = mazeGameControler.getPiecesIHMs();
		treasureIHMs = mazeGameControler.getTreasuresIHMs();

		//On cree une grille de 2 par 2 (4 cases)
		//Le plateau sera dans la premiere case, les elements de jeu dans les autres
		generalBoard = new JPanel(new MigLayout());

		// On cree une grille de 7 par 7 (49 cases)
		mazeBoard = new JPanel(new GridLayout(7,7));
		
		//On cree on grille de 2 par 2 pour les scores
		scores = new JPanel(new GridLayout(2,2));
		//On met une bordure pour le délimiter visuellement
		scores.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		//On donne une taille au tableau des scores
		scores.setPreferredSize(new Dimension(200,100));
		
		//On initialise le score de chaque joueur
		scoreMario = new JLabel();
		scoreMario.setText("Mario : 0"); //+ mazeGameControler.getRedPlayerScore());
		scoreYoshi = new JLabel();
		scoreYoshi.setText("Yoshi : 0"); //+ mazeGameControler.getYellowPlayerScore());
		scoreLuigi = new JLabel();
		scoreLuigi.setText("Luigi : 0"); //+ mazeGameControler.getBluePlayerScore());
		scoreToad = new JLabel();
		scoreToad.setText("Toad : 0"); //+ mazeGameControler.getGreenPlayerScore());
		
		//On ajoute les scores dans le tableau
		scores.add(scoreMario);
		scores.add(scoreLuigi);
		if(nbPlayer == 3 || nbPlayer == 4) {
			scores.add(scoreYoshi);
		}
		if(nbPlayer == 4) {
			scores.add(scoreToad);
		}
		
		//On crée le panel du joueur actif
		activePlayer = new JPanel(new BorderLayout(1,2));
		
		//On crée le label sur lequel le joueur actif sera récupéré
		player = new JLabel();
		playerText = new JLabel();
		playerText.setText("JOUEUR  :" + " ");
		//On initialise le joueur devant jouer à "Mario"
		File g = new File("");
		String path = "/src/images/";
		String ret = g.getAbsolutePath() + path + "pion_rouge.png";
		bg = new ImageIcon(ret); 
		player.setIcon(bg);
		
		
		//On ajoute le JLabel sur le JPanel
		activePlayer.add(playerText,BorderLayout.PAGE_START);
		activePlayer.add(player,BorderLayout.PAGE_END);
		activePlayer.setOpaque(false);		
		//On definit la taille de la grille generale
		generalBoard.setPreferredSize(windowSize);
		generalBoard.setBounds(0, 0, windowSize.width, windowSize.height);
		
		//On crée le JLabel du tresor à attraper
		tresorToCatch = new JLabel();
		
		
		//On cree une image pour la pile des cartes des tresors
		imageIcon = new ImageIcon(MazeImageProvider.getImageCardTresorsFile("DosJeu"));
		//On cree la zone pour la pile de cartes
		tresorCard = new JLabel(imageIcon);
		
		//On cree la carte supplementaire, recuperant la deuxieme piece de la liste
		//On garde le côte aleatoire comme la liste est aleatoire
		//Il faut la deuxieme car la premiere est un angle de depart
		extraCard = mazeGameControler.getExtraCorridorIHM();

		//Bouton de rotation gauche
		rotateLeftButton = new JButton("Insérer la pièce");
		rotateLeftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mazeAltered = alterMaze();
				if(mazeAltered) {
					rotateLeftButton.setEnabled(false);
					rotateRightButton.setEnabled(false);
				}
				else {
					rotateLeftButton.setEnabled(true);
					rotateRightButton.setEnabled(true);
				}
			}
		});
		//Bouton de rotation droit
		rotateRightButton = new JButton("\u21BB");
		rotateRightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mazeGameControler.rotateExtraCardRight();
			}
		});
		// on cree un panneau contenant differents plans
		extraCardPane = new JLayeredPane();
		extraCardPane.setPreferredSize(new Dimension(100, 100));

		// on cree une image de couloir pour la piece supplementaire
		imageIcon = new ImageIcon(MazeImageProvider.getImageFile(
			"Couloir",
			extraCard.isNorthOpened(),
			extraCard.isSouthOpened(),
			extraCard.isEastOpened(),
			extraCard.isWestOpened(),
			false
		));
		disabledIcon = new ImageIcon(MazeImageProvider.getImageFile(
			"Couloir",
			extraCard.isNorthOpened(),
			extraCard.isSouthOpened(),
			extraCard.isEastOpened(),
			extraCard.isWestOpened(),
			true
		));
		extraCardImage = new JLabel(imageIcon);
		extraCardImage.setDisabledIcon(disabledIcon);
		
		// on parametre la taille et la position de la piece supplementaire
		extraCardImage.setPreferredSize(new Dimension(100, 100));
		extraCardImage.setBounds(0, 0, 100, 100);

		// on ajoute le couloir en arriere-plan
		extraCardPane.add(extraCardImage, COULOIR_LAYER);

		// position et taille du plateau de jeu -> on recupere les dimensions passees en parametres
		mazeBoard.setPreferredSize(dim);
		mazeBoard.setBounds(0, 0, dim.width, dim.height);

		// creation des couloirs a partir du modele
		for(CouloirIHM couloirIHM : couloirIHMs) {

			// on cree un panneau contenant differents plans
			this.layeredPane = new JLayeredPane();
			this.layeredPane.setPreferredSize(new Dimension(100, 100));

			// on cree une image de couloir
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
			couloir = new JLabel(imageIcon);
			couloir.setDisabledIcon(disabledIcon);
			
			// pour chaque case on ajoute paramètre dimension et position du couloir
			couloir.setPreferredSize(new Dimension(100, 100));
			couloir.setBounds(0, 0, 100, 100);

			// on ajoute le couloir en arriere-plan
			this.layeredPane.add(couloir, COULOIR_LAYER);

			// on ajoute les differents plans au plateau
			this.mazeBoard.add(this.layeredPane);
		}
		
		// creation des pions a partir du modele
		for(PieceIHMs pieceIHM : pieceIHMs) {

			// si on est sur la position d'un pion
			// on cree un pion a chaque coin du jeu
			this.pawn = new JLabel(
					new ImageIcon(MazeImageProvider.getImageFile(
							"Pion",
							pieceIHM.getCouleur()
					))
			);

			this.pawn.setPreferredSize(new Dimension(100, 100));
			this.pawn.setBounds(0, 0, 100, 100);
			this.pawn.setOpaque(false);

			((JLayeredPane) this.mazeBoard.getComponent(pieceIHM.getX() + 7
					* pieceIHM.getY())).add(this.pawn, PAWN_LAYER);
		}
		
		for(TreasureIHMs treasureIHM : treasureIHMs){
			treasure = new JLabel (new ImageIcon(MazeImageProvider.getImageFile(treasureIHM.getTreasureId())));
			treasure.setPreferredSize(new Dimension(100, 100));
			treasure.setBounds(0, 0, 100, 100);
			treasure.setOpaque(false);
			((JLayeredPane)this.mazeBoard.getComponent(treasureIHM.getTreasureX() + 7*treasureIHM.getTreasureY())).add(treasure, TREASURE_LAYER);
		}
		Treasure treasureToCatch = this.mazeGameControler
				.currentTreasureToCatch();
		imageTreasureToCatch = new ImageIcon(MazeImageProvider.getImageFile(treasureToCatch.getTreasureId()));
		//On cree la zone pour la pile de cartes
		tresorToCatch.setIcon(imageTreasureToCatch);

		g = new File("");
		path = "/src/images/";
		 ret = g.getAbsolutePath() + path + "bgGame.jpg";
		
		bg = new ImageIcon(ret);
		bgGame = new JLabel(); 
		bgGame.setIcon(bg);
		
		generalBoard.add(tresorToCatch,"pos 0.892al 0.458al");
		generalBoard.add(mazeBoard, "pos 0 0");
		generalBoard.add(tresorCard, "pos 0.93al 0.45al");
		generalBoard.add(extraCardPane, "pos 0.92al 0.03al"); //AbsoluteLayout : on positionne en pourcentage de la fenetre
		generalBoard.add(rotateLeftButton, "pos 0.932al 0al");
		generalBoard.add(rotateRightButton, "pos 0.90al 0.135al");
		generalBoard.add(activePlayer, "pos 0.901al 0.25al");
		generalBoard.add(scores, "pos 0.98al 0.65al");
		generalBoard.add(bgGame,"pos 0 0");

		mazeContainer.add(generalBoard);
		mazeBoard.addMouseListener(this);
		mazeBoard.addMouseMotionListener(this);
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
						((JLayeredPane) component).getComponentsInLayer(COULOIR_LAYER)[0].setEnabled(false);
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
		 //mazeBoard.getHeight() donne la hauteur en pixels de la fenetre
		 //on divise getX() par layeredPane.getHeight()/7 pour savoir dans quelle case on a deplace la piece
		int destinationX = e.getX()/(mazeBoard.getHeight()/7);
		int destinationY = e.getY()/(mazeBoard.getHeight()/7);
		int CoordInitialeX = this.mazeGameControler.getCurrentCoordInitiale().x;
		int CoordInitialeY = this.mazeGameControler.getCurrentCoordInitiale().y;
		String nameJeuCourant = this.mazeGameControler.getCurrentNamePlayer();
		Object[] options = {
				"Quitter"};

		JLayeredPane layeredPane;
		JLayeredPane parentComponentHere;
		JLabel corridorImage;
		JInternalFrame frame = new JInternalFrame();

		if (pawn == null) {
			return;
		}

		// on retire l'effet visuel du hover
		 layeredPane = (JLayeredPane) previouslyHoveredComponent;
		if(layeredPane != null) {
			corridorImage = (JLabel) layeredPane.getComponentsInLayer(COULOIR_LAYER)[0];
			corridorImage.setBorder(null);
		}

		 Treasure treasureToCatch = this.mazeGameControler
					.currentTreasureToCatch();
		if(mazeAltered) {
			// deplacement du pion
			boolean isMoveOK = mazeGameControler.move(
					new Coord(xOrigine, yOrigine),
					new Coord(destinationX, destinationY)
			);

			if (isMoveOK) {
				Component componentHere = this.mazeBoard.findComponentAt(e.getX(),
						e.getY());
				parentComponentHere = (JLayeredPane) componentHere.getParent();
				if(this.mazeGameControler.getCurrentScorePlayer() < this.mazeGameControler.getScoreMax()) {
					if (parentComponentHere.getComponentsInLayer(TREASURE_LAYER).length > 0) {
						if (destinationX == treasureToCatch.getTreasureX()
								&& destinationY == treasureToCatch.getTreasureY()) {
							this.mazeGameControler.treasureCatchedPlateau(treasureToCatch);
							this.mazeGameControler.setCurrentTreasureToCatch(null);
							//Lors d'un changement de score, on met à jour l'affichage du tableau
							scoreMario.setText("Mario : " + mazeGameControler.getRedPlayerScore());
							scoreLuigi.setText("Luigi : " + mazeGameControler.getBluePlayerScore());
							if (nbPlayer == 3 || nbPlayer == 4) {
								scoreYoshi.setText("Yoshi : " + mazeGameControler.getYellowPlayerScore());
							}
							if (nbPlayer == 4) {
								scoreToad.setText("Toad : " + mazeGameControler.getGreenPlayerScore());
							}
						}
					}
				}
				else {
					if(destinationX == CoordInitialeX && destinationY == CoordInitialeY){
						int n = JOptionPane.showOptionDialog(frame,
								"Félicitation, le vainqueur de cette bataille acharnée est " + nameJeuCourant + ". Allez retrouver votre dulcinée !",
								"VICTOIRE",
								JOptionPane.OK_CANCEL_OPTION,
								JOptionPane.PLAIN_MESSAGE,
								null,
								options,
								options[0]);
						if(n==0){
							System.exit(0);
						}
					}
				}
				this.mazeGameControler.switchJoueur();

				File g = new File("");
				String path = "/src/images/";
				String ret = "";
				if(mazeGameControler.getColorCurrentPlayer() == Couleur.ROUGE) {
					ret = g.getAbsolutePath() + path + "pion_rouge.png";
				}
				else if(mazeGameControler.getColorCurrentPlayer() == Couleur.BLEU) {
					ret = g.getAbsolutePath() + path + "pion_bleu.png";
				}
				else if(mazeGameControler.getColorCurrentPlayer() == Couleur.JAUNE) {
					ret = g.getAbsolutePath() + path + "pion_jaune.png";
				}
				else if(mazeGameControler.getColorCurrentPlayer() == Couleur.VERT) {
					ret = g.getAbsolutePath() + path + "pion_vert.png";
				}
				bg = new ImageIcon(ret);
				player.setIcon(bg);
				treasureToCatch = this.mazeGameControler
						.currentTreasureToCatch();

				if(treasureToCatch != null){
					imageTreasureToCatch = new ImageIcon(MazeImageProvider.getImageFile(treasureToCatch.getTreasureId()));
				}else{
					imageTreasureToCatch = new ImageIcon(MazeImageProvider.getImageFile(-1));
				}
				//On cree la zone pour la pile de cartes
				tresorToCatch.setIcon(imageTreasureToCatch);
				mazeAltered = false;
				// réautoriser les boutons pour le prochain joueur
				rotateLeftButton.setEnabled(true);
				rotateRightButton.setEnabled(true);
			}
		}
		else {
			mazeGameControler.move(null, null);
		}
	 }

	public void mouseClicked(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	@Override
	public void update(Observable o, Object arg) {
		if(this.mazeBoard == null || arg == null) {
			return;
		}

		// attention l'ordre des if est important...
		// si mise à jour de la piece supplémentaire
		if(arg instanceof CouloirIHM) {
			CouloirIHM extraCard = (CouloirIHM) arg;
			JLabel extraCardImage;
			ImageIcon imageIcon;
			ImageIcon disabledIcon;

			if (extraCardPane.getComponentsInLayer(COULOIR_LAYER).length != 0) {
				for (int i = 0; i < extraCardPane
						.getComponentsInLayer(COULOIR_LAYER).length; i++) {
					extraCardPane.remove(extraCardPane
							.getComponentsInLayer(COULOIR_LAYER)[i]);
				}
			}
			// on cree une image de couloir pour la piece supplementaire
			imageIcon = new ImageIcon(MazeImageProvider.getImageFile(
					"Couloir",
					extraCard.isNorthOpened(),
					extraCard.isSouthOpened(),
					extraCard.isEastOpened(),
					extraCard.isWestOpened(),
					false
			));
			disabledIcon = new ImageIcon(MazeImageProvider.getImageFile(
					"Couloir",
					extraCard.isNorthOpened(),
					extraCard.isSouthOpened(),
					extraCard.isEastOpened(),
					extraCard.isWestOpened(),
					true
			));
			extraCardImage = new JLabel(imageIcon);
			extraCardImage.setDisabledIcon(disabledIcon);

			// on parametre la taille et la position de la piece supplementaire
			extraCardImage.setPreferredSize(new Dimension(100, 100));
			extraCardImage.setBounds(0, 0, 100, 100);

			// on ajoute le couloir en arriere-plan
			extraCardPane.add(extraCardImage, COULOIR_LAYER);
		}
		// si mise à jour du pion
		else if(((LinkedList<PieceIHMs>)arg).size() > 0 && ((LinkedList<PieceIHMs>)arg).getFirst() instanceof PieceIHMs) {
			List<PieceIHMs> piecesIHM = (List<PieceIHMs>) arg;
			// on enlève tous les pions (sur chaque couloir)
			for(CouloirIHMs couloirIHM : this.couloirIHMs){
				this.layeredPane = (JLayeredPane) this.mazeBoard.getComponent(7 * couloirIHM.getY() + couloirIHM.getX());
				//On enlève le pion
				if (this.layeredPane.getComponentsInLayer(PAWN_LAYER).length != 0) {
					for (int i = 0; i < this.layeredPane
							.getComponentsInLayer(PAWN_LAYER).length; i++) {
						this.layeredPane.remove(this.layeredPane
								.getComponentsInLayer(PAWN_LAYER)[i]);
					}
				}
			}
			// on les recrée
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
		// si mise à jour des tresors
 		else if(((LinkedList<TreasureIHMs>)arg).size() > 0 && ((LinkedList<TreasureIHMs>)arg).getFirst() instanceof TreasureIHMs) {
			List<TreasureIHMs> updatedList = (List<TreasureIHMs>) arg;
			JLabel treasure;
			// on enlève tous les trésors (sur chaque couloir)
			for(CouloirIHMs couloirIHM : this.couloirIHMs){
				this.layeredPane = (JLayeredPane) this.mazeBoard.getComponent(7 * couloirIHM.getY() + couloirIHM.getX());
				if (this.layeredPane.getComponentsInLayer(TREASURE_LAYER).length != 0) {
					for (int i = 0; i < this.layeredPane
							.getComponentsInLayer(TREASURE_LAYER).length; i++) {
						this.layeredPane.remove(this.layeredPane
								.getComponentsInLayer(TREASURE_LAYER)[i]);
					}
				}
			}
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
						MazeImageProvider.getImageFile(treasureIHM.getTreasureId())));
				treasure.setPreferredSize(new Dimension(100, 100));
				treasure.setBounds(0, 0, 100, 100);
				treasure.setOpaque(false);
				// TODO moche ajouter tests
				((JLayeredPane) this.mazeBoard.getComponent(treasureIHM
						.getTreasureX() + 7 * treasureIHM.getTreasureY())).add(
						treasure, TREASURE_LAYER);
			}
			this.treasureIHMs = updatedList;
		}
		// si mise à jour des couloirs
		else if(((LinkedList<CouloirIHM>)arg).size() > 0 && ((LinkedList<CouloirIHM>)arg).getFirst() instanceof CouloirIHM) {
			List<CouloirIHM> updatedList = (List<CouloirIHM>) arg;
			JLabel couloir;
			ImageIcon imageIcon;
			ImageIcon disabledIcon;

			//Suppression des trésors
			for (CouloirIHM couloirIHM : couloirIHMs) {
				//On enlève le couloir
				this.layeredPane = (JLayeredPane) this.mazeBoard.getComponent(7
						* couloirIHM.getY() + couloirIHM.getX());
				if (this.layeredPane.getComponentsInLayer(COULOIR_LAYER).length != 0) {
					for (int i = 0; i < this.layeredPane
							.getComponentsInLayer(COULOIR_LAYER).length; i++) {
						this.layeredPane.remove(this.layeredPane
								.getComponentsInLayer(COULOIR_LAYER)[i]);
					}
				}
			}

			for (CouloirIHM couloirIHM : updatedList) {
				//On enlève le couloir
				this.layeredPane = (JLayeredPane) this.mazeBoard.getComponent(7
						* couloirIHM.getY() + couloirIHM.getX());
				if (this.layeredPane.getComponentsInLayer(COULOIR_LAYER).length != 0) {
					for (int i = 0; i < this.layeredPane
							.getComponentsInLayer(COULOIR_LAYER).length; i++) {
						this.layeredPane.remove(this.layeredPane
								.getComponentsInLayer(COULOIR_LAYER)[i]);
					}
				}

				// on recrée le couloir
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
				couloir = new JLabel(imageIcon);
				couloir.setDisabledIcon(disabledIcon);
				couloir.setPreferredSize(new Dimension(100, 100));
				couloir.setBounds(0, 0, 100, 100);
				this.layeredPane.add(couloir, COULOIR_LAYER);
			}
		}

		// on reautorise toutes les cases
		for (Component component : this.mazeBoard.getComponents()) {
			if (((JLayeredPane) component).getComponentsInLayer(COULOIR_LAYER).length > 0) {
				((JLayeredPane) component).getComponentsInLayer(COULOIR_LAYER)[0]
						.setEnabled(true);
			}
		}

		// on régénère l'objet de la piece supp
		TreasureIHM extraTreasureIHM = this.mazeGameControler.getExtraTreasureIHM();
		JLabel extraTreasure;

		// suppression dans tous les cas
		if (extraCardPane.getComponentsInLayer(TREASURE_LAYER).length != 0) {
			for (int i = 0; i < extraCardPane.getComponentsInLayer(TREASURE_LAYER).length; i++) {
				extraCardPane.remove(extraCardPane.getComponentsInLayer(TREASURE_LAYER)[i]);
			}
		}
		// recréation si besoin
		if(extraTreasureIHM != null) {
			extraTreasure = new JLabel(new ImageIcon(
					MazeImageProvider.getImageFile(extraTreasureIHM.getTreasureId())));
			extraTreasure.setPreferredSize(new Dimension(100, 100));
			extraTreasure.setBounds(0, 0, 100, 100);
			extraTreasure.setOpaque(false);
			extraCardPane.add(
				extraTreasure, TREASURE_LAYER
			);
		}

		this.repaint();
		this.revalidate();
	}

	private boolean alterMaze() {
		final String CMD_HAUT =  "\u2191";
		final String CMD_BAS = "\u2193";
		final String CMD_GAUCHE = "\u2190";
		final String CMD_DROITE = "\u2192";
		final String CMD_1 = "2";
		final String CMD_3 = "4";
		final String CMD_5 = "6";
		String command;
		int selectedNumber;
		// choix modification du labyrinthe
		String[] possibleValuesDirection = {CMD_HAUT, CMD_BAS, CMD_GAUCHE, CMD_DROITE};
		Object selectedValueDirection = JOptionPane.showInputDialog(
				null,
				"Choisir une valeur pour la direction",
				"Direction",
				JOptionPane.INFORMATION_MESSAGE,
				null,
				possibleValuesDirection,
				possibleValuesDirection[0]
		);
		if(selectedValueDirection == null) {
			return false;
		}
		boolean upDown = (possibleValuesDirection.equals(CMD_HAUT) || possibleValuesDirection.equals(CMD_BAS));
		String columnOrLine = (upDown ? "colonne" : "ligne");
		String[] possibleValuesNumber = { CMD_1, CMD_3, CMD_5 };
		Object selectedValueNumber = JOptionPane.showInputDialog(
				null,
				"Choisir une valeur pour la " +  columnOrLine + " à pousser",
				"Choix " + columnOrLine,
				JOptionPane.INFORMATION_MESSAGE,
				null,
				possibleValuesNumber,
				possibleValuesNumber[0]
		);

		// si annulation d'un des deux prompt
		if(selectedValueDirection == null || selectedValueNumber == null) {
			return false;
		}

		// modification du labyrinthe
		selectedValueDirection = selectedValueDirection == null ? CMD_HAUT : selectedValueDirection;
		selectedValueNumber = selectedValueNumber == null ? CMD_1 : selectedValueNumber;
		switch((String) selectedValueDirection) {
			case CMD_HAUT : {
				command = "pushUp";
				break;
			}
			case CMD_BAS : {
				command = "pushDown";
				break;
			}
			case CMD_GAUCHE : {
				command = "pushLeft";
				break;
			}
			case CMD_DROITE : {
				command = "pushRight";
				break;
			}
			default: {
				command = "pushUp";
				break;
			}
		}
		switch((String) selectedValueNumber) {
			case CMD_1 : {
				selectedNumber = 1;
				break;
			}
			case CMD_3 : {
				selectedNumber = 3;
				break;
			}
			case CMD_5 : {
				selectedNumber = 5;
				break;
			}
			default: {
				selectedNumber = 1;
				break;
			}
		}
		this.mazeGameControler.alterMaze(command, selectedNumber);
		return true;
	}

}
