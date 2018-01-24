package vue;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.Socket;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controler.controlerOnline.MazeGameControlerOnlineClient;
import controler.controlerOnline.MazeGameControlerOnlineServer;
import model.*;
import tools.MazeImageProvider;
import net.miginfocom.swing.MigLayout;
import model.observable.MazeGame;
import controler.MazeGameControlers;
import controler.controlerLocal.MazeGameControler;
import launcher.localLauncher.LauncherGUI;

public class MazeGameGUI extends JFrame implements MouseListener, MouseMotionListener, Observer {

	private static final long serialVersionUID = 1L;
	private MazeGameControlers mazeGameControler;
	private JLayeredPane layeredPane;
	private JLabel tresorToCatch;
	private JLabel pushDown2, pushDown4, pushDown6, pushUp2, pushUp4, pushUp6, pushRight2, pushRight4, pushRight6, pushLeft2, pushLeft4, pushLeft6;
	private JLabel bgGame;
	private Icon bg;
	private Icon pd2, pd4, pd6, pu2, pu4, pu6, pl2, pl4, pl6, pr2, pr4, pr6;
	private JLayeredPane mazeContainer;
	private JPanel generalBoard;
	private JPanel scores;
	private JPanel activePlayer;
	private Icon imageTreasureToCatch;
	private Box rulesBox;
	private Box generalRulesBox;
	private JPanel mazeBoard;
	private JLabel pawn = null;
	private JLabel scoreMario;
	private JLabel scoreYoshi;
	private JLabel scoreLuigi;
	private JLabel scoreToad;
	private JLabel player,playerText;
	private ImagePanel contentPane;
	private int xAdjustment;
	private JButton rotateRightButton;
	private JLayeredPane extraCardPane;
	private JTextArea rules;
	private int yAdjustment;
	private int xOrigine;
	private int yOrigine;
	private int nbPlayer = 2;
	private String theme = "green";
	private boolean playOnline = false;
	private boolean isServerPlayer = false;
	private boolean isClientPlayer = false;
	final JDialog waitDialog = new JDialog();
	private Component previouslyHoveredComponent;
	private List<TreasureIHMs> treasureIHMs;
	private List<CouloirIHM> couloirIHMs;
	private Dimension dim;
	private final Integer COULOIR_LAYER = 0;
	private final Integer TREASURE_LAYER = 1;
	private final Integer PAWN_LAYER = 2;
	private JDialog rulesFrame;
	private JPanel backgroundPane;
	private boolean mazeAltered = false;
	private JLabel test;
	private LinkedList<JLabel> arrowsList;

	
	public MazeGameGUI(Dimension dim) {
		Box bPlayers,bOK,bMenu,bRules,bTheme,bimgTheme;
		JButton reglesButton, okButton;
		ButtonGroup grpButton, grpButton2, grpButton3;
		JRadioButton nb2Button, nb3Button, nb4Button, theme1, theme2, theme3, imgTheme1, imgTheme2, imgTheme3;
		final Font myFont = new Font("Calibri", Font.ITALIC | Font.BOLD, 18);

		this.dim = dim; //Dimension du plateau de jeu 
		Dimension windowSize = new Dimension(950,700); //dimension de la fenetre
		JRadioButton onlineButton;
		
		final JOptionPane optionPane = new JOptionPane(
			"Attente autre joueur",
			JOptionPane.INFORMATION_MESSAGE,
			JOptionPane.DEFAULT_OPTION,
			null,
			new Object[]{},
			null
		);
	   	waitDialog.setTitle("Message");
	   	waitDialog.setModal(true);
	   	waitDialog.setContentPane(optionPane);
	   	waitDialog.pack();
		
		//JRadioButton onlineButton;
		JRadioButton severPlayerButton;
		JRadioButton clientPlayerButton;
		
		// on cree un conteneur general qui acceuillera le tableau de jeu + l'element dragge
		mazeContainer = new JLayeredPane();
		mazeContainer.setPreferredSize(windowSize);
		mazeContainer.setBounds(0, 0, windowSize.width, windowSize.height);
		
		// on cree le groupe button du choix du nb de joueurs
		grpButton = new ButtonGroup();
		nb2Button = new JRadioButton("2 JOUEURS");
		nb3Button = new JRadioButton("3 JOUEURS");
		nb4Button = new JRadioButton("4 JOUEURS");
		//onlineButton = new JRadioButton("PLAY ONLINE");
		severPlayerButton = new JRadioButton("PLAY SERVER");
		clientPlayerButton = new JRadioButton("PLAY CLIENT");
		
		nb2Button.setFont(myFont);
		nb3Button.setFont(myFont);
		nb4Button.setFont(myFont);
		
		nb2Button.setForeground(Color.WHITE);
		nb3Button.setForeground(Color.WHITE);
		nb4Button.setForeground(Color.WHITE);
		
		nb2Button.setBackground(Color.BLACK);
		nb3Button.setBackground(Color.BLACK);
		nb4Button.setBackground(Color.BLACK);
		Border lineRadio3 = new LineBorder(Color.WHITE);
		Border marginRadio3 = new EmptyBorder(8, 35, 8, 35);
		Border compoundRadio3 = new CompoundBorder(lineRadio3, marginRadio3);
		nb4Button.setBorder(compoundRadio3);

		severPlayerButton.setForeground(Color.WHITE);
		severPlayerButton.setBackground(Color.BLACK);
		clientPlayerButton.setForeground(Color.WHITE);
		clientPlayerButton.setBackground(Color.BLACK);
		Border lineOnlineButton = new LineBorder(Color.WHITE);
		Border marginOnlineButton = new EmptyBorder(8, 35, 8, 35);
		Border compoundOnlineButton = new CompoundBorder(lineOnlineButton, marginOnlineButton);
		severPlayerButton.setBorder(compoundOnlineButton);
		clientPlayerButton.setBorder(compoundOnlineButton);
		
		Border lineRadio = new LineBorder(Color.WHITE);
		Border marginRadio = new EmptyBorder(8, 35, 8, 35);
		Border compoundRadio = new CompoundBorder(lineRadio, marginRadio);
		
		nb2Button.setBorder(compoundRadio);
		nb3Button.setBorder(compoundRadio);
		nb4Button.setBorder(compoundRadio);
			
		// debug background color btn on Mac 
		nb2Button.setOpaque(true); 
		nb3Button.setOpaque(true); 
		nb4Button.setOpaque(true);
		nb3Button.setOpaque(true);
		severPlayerButton.setOpaque(true);
		clientPlayerButton.setOpaque(true);
		
		// ajout des boutons radio dans le groupe bg
		grpButton.add(nb2Button);
		grpButton.add(nb3Button);
		grpButton.add(nb4Button);
		grpButton.add(severPlayerButton);
		grpButton.add(clientPlayerButton);
		
		//init à deux joueurs
		nb2Button.setSelected(true);
		
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
		// on cree le container du menu
	    bPlayers = Box.createHorizontalBox();
		bPlayers.setOpaque(false); 
		bPlayers.add(nb2Button);
		bPlayers.add(nb3Button);
		bPlayers.add(nb4Button);
		bPlayers.setBorder(BorderFactory.createMatteBorder(
                1, 1, 1, 1, Color.WHITE));

		severPlayerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				nbPlayer = 2;
				isServerPlayer = true;
				playOnline = true;
			}
		});
		clientPlayerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				nbPlayer = 2;
				isClientPlayer = true;
				playOnline = true;
			}
		});
		
		grpButton2 = new ButtonGroup();
		theme1 = new JRadioButton(/*"LA PRAIRIE MEUH MEUH"*/);
		theme2 = new JRadioButton(/*"LE CHÂTEAU DE BOWSER"*/);
		theme3 = new JRadioButton(/*"LA MONTAGNE GLA GLA"*/);
		
		theme1.setFont(myFont);
		theme2.setFont(myFont);
		theme3.setFont(myFont);
		
		theme1.setForeground(Color.WHITE);
		theme2.setForeground(Color.WHITE);
		theme3.setForeground(Color.WHITE);
		
		theme1.setBackground(Color.BLACK);
		theme2.setBackground(Color.BLACK);
		theme3.setBackground(Color.BLACK);
		
		theme1.setBorder(compoundRadio);
		theme2.setBorder(compoundRadio);
		theme3.setBorder(compoundRadio);
		
		theme1.setOpaque(true); 
		theme2.setOpaque(true); 
		theme3.setOpaque(true);
		
		grpButton3 = new ButtonGroup();
		imgTheme1 = new JRadioButton();
		imgTheme2 = new JRadioButton();
		imgTheme3 = new JRadioButton();
		
		imgTheme1.setIcon(new ImageIcon(getClass().getResource("../images/theme/green/ico.jpg")));
		imgTheme2.setIcon(new ImageIcon(getClass().getResource("../images/theme/lava/ico.jpg")));
		imgTheme3.setIcon(new ImageIcon(getClass().getResource("../images/theme/snow/ico.jpg")));

		imgTheme1.setOpaque(false);
		imgTheme2.setOpaque(false);
		imgTheme3.setOpaque(false);
		
		imgTheme1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theme = "green";
				theme1.setSelected(true);				
			}
		});
		imgTheme2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theme = "lava";
				theme2.setSelected(true);
			}
		});
		imgTheme3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theme = "snow";
				theme3.setSelected(true);
			}
		});
		
		grpButton3.add(imgTheme1);
		grpButton3.add(imgTheme2);
		grpButton3.add(imgTheme3);
		
		grpButton2.add(theme1);
		grpButton2.add(theme2);
		grpButton2.add(theme3);
		
		theme1.setSelected(true);
		
		theme1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theme = "green";
				imgTheme1.setSelected(true);
			}
		});
		theme2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theme = "lava";
				imgTheme2.setSelected(true);
			}
		});
		theme3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theme = "snow";
				imgTheme3.setSelected(true);
			}
		});
		
		// on cree le container du choix des themes
		bimgTheme = Box.createHorizontalBox();
		bimgTheme.add(imgTheme1);
	    bimgTheme.add(imgTheme2);
	    bimgTheme.add(imgTheme3);

	    bTheme = Box.createHorizontalBox();
	    bTheme.setOpaque(false); 
	    bTheme.add(theme1);
	    bTheme.add(theme2);
	    bTheme.add(theme3);
	    bTheme.setBorder(BorderFactory.createMatteBorder(
                1, 1, 1, 1, Color.WHITE));

		bOK = Box.createHorizontalBox();
		bOK.setOpaque(false); // background gris desactive
		// Lancer le jeu
		
		okButton = new JButton("JOUER");
		okButton.setForeground(Color.WHITE);
		okButton.setBackground(Color.BLACK);
		okButton.setOpaque(true);
		okButton.setBorder(compoundRadio);
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
		
		bOK.add(okButton);

		
		//on cree le bouton qui va afficher les regles
		reglesButton  = new JButton("REGLES DU JEU");
		reglesButton.setFont(myFont);
		reglesButton.setForeground(Color.WHITE);
		reglesButton.setBackground(Color.BLACK);
		reglesButton.setOpaque(true);
		reglesButton.setBorder(compoundRadio);
		
		reglesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				final String rulesText = "Quel que soit le nombre de joueurs, Mario commence en premier.\n" + 
						"Un tour de jeu se déroule de la manière suivante :\n\n" + 
						"1. Si le joueur n'a pas de trésor attribué, il en reçoit un qu'il doit aller récupérer.\n\n" + 
						"2. Pour accéder à ce trésor, le joueur doit se frayer un chemin à travers les couloirs.\n\n" + 
						"3. Pour cela, à chaque tour avant de bouger son pion, le joueur doit modifier le labyrinthe. Cette étape est obligatoire. Pour cela il fait pivoter la pièce supplémentaire pour la placer dans le sens qu'il souhaite, puis il clique sur la flêche correspondant à la ligne ou colonne où il veut insérer sa pièce, et suivant le sens dans lequel il souhaite modifier le labyrinthe.\n\n" + 
						"4. Une fois le labyrinthe modifié comme il le souhaite, le joueur peut déplacer son pion. Attention, une fois le pion relâché sur une case accessible au joueur, son tour se termine et c'est au joueur suivant de jouer.\n\n" + 
						"5. Une fois qu'un joueur a ramassé une majorité de trésors (c'est-à-dire au moins 24/(nombre de joueurs) trésors), il doit retourner sur sa position initiale pour gagner la partie.\n" + 
						"";
				
				//creation de la fenetre des regles
				rulesFrame = new JDialog();
				rulesFrame.setSize(new Dimension(dim));
				rulesFrame.setLocationRelativeTo(getParent());
				rulesFrame.setTitle("Règles du jeu");
				
				//creation du panneau qui va etre ajoute dans la fenetre des regles
				File f = new File("");
				String path = "/src/images/theme/green/";
				String background = f.getAbsolutePath() + path + "bgGame.jpg";
				
				final ImageIcon backgroundImage = new ImageIcon(background);
				backgroundPane = new JPanel(new BorderLayout()) {
					@Override
					protected void paintComponent(Graphics g) {
						super.paintComponent(g);
						g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
					}
				};
				JButton QuitReglesButton;
				QuitReglesButton = new JButton("QUITTER");
				QuitReglesButton.setFont(myFont);
				QuitReglesButton.setForeground(Color.WHITE);
				QuitReglesButton.setBackground(Color.BLACK);
				QuitReglesButton.setOpaque(true);
				QuitReglesButton.setBorder(compoundRadio);
				QuitReglesButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						rulesFrame.dispose();
					}
				});
				rulesBox = Box.createHorizontalBox();
				rulesBox.setBorder(new EmptyBorder(0, 275, 50, 0));
				rulesBox.add(QuitReglesButton);
				generalRulesBox = Box.createVerticalBox();
				generalRulesBox.add(rulesBox);
				//backgroundPane.add(generalRulesBox);
				//ajout des composants
				
				rules = new JTextArea();
				Border rulesBorder = new EmptyBorder(8, 35, 8, 35);
				rules.setBorder(rulesBorder);
				rules.setEditable(false);
				rules.setFont(myFont);
				rules.setText(rulesText);
				rules.setLineWrap(true);
				rules.setWrapStyleWord(true);
				rules.setOpaque(false);
				backgroundPane.add(rules, BorderLayout.NORTH);
				backgroundPane.add(rulesBox, BorderLayout.SOUTH);
				rulesFrame.add(backgroundPane);

				rulesFrame.setModal(true);
				rulesFrame.setVisible(true);
			}			
		});
		
		//box contenant le bouton des regles
		bRules = Box.createHorizontalBox();
		bRules.setOpaque(false);
		bRules.add(reglesButton);
		
		Box bSpace = Box.createHorizontalBox();
		bSpace.setOpaque(false);
		bSpace.setBorder(new EmptyBorder(10,0,0,0));
		
		bMenu = Box.createVerticalBox();
		bMenu.setOpaque(false); // background gris desactive
		
		bMenu.add(bPlayers);
		bMenu.add(bSpace);
		bMenu.add(bimgTheme);
		bMenu.add(bTheme);
		bMenu.add(bOK);
		bMenu.add(bRules);
				
		bMenu.setBorder(new EmptyBorder(100, 0, 0, 0));
		bOK.setBorder(new EmptyBorder(10,0,0,0));
		bRules.setBorder(new EmptyBorder(200,0,0,0));
		
		File g = new File("");
		String path = "/src/images/";
		String ret = g.getAbsolutePath() + path + "bg.jpg";
		contentPane = new ImagePanel(new ImageIcon(ret).getImage());
	    contentPane.add(bMenu);
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
		String greyArrowDown;
		ImageIcon gad;
		String greyArrowUp;
		ImageIcon gau;
		String greyArrowLeft;
		ImageIcon gal;
		String greyArrowRight;
		ImageIcon gar;
		final String IP = "127.0.0.1";
		final int PORT = 1234;

	   	setContentPane(mazeContainer);
	   	
		repaint();
		pack();

		mazeGame = new MazeGame(nbPlayer);

		// mode en reseau
		if(this.playOnline) {
			/*
			// on détecte si un serveur existe deja
			try (Socket s = new Socket(IP, PORT)) {
				// la socket a réussi à se connecter, on va donc jouer le rôle du client (un serveur est deja la)
				this.mazeGameControler = new MazeGameControlerOnlineClient(mazeGame, IP, PORT);
				System.out.println("Mode client");
			} catch (IOException ex) {
				// pas de serveur détecté (connexion de test échouée), on joue donc le rôle de serveur
				this.mazeGameControler = new MazeGameControlerOnlineServer(mazeGame, IP, PORT);
				System.out.println("Mode serveur");
			}
			*/
			
			if(this.isClientPlayer) {
				// la socket a réussi à se connecter, on va donc jouer le rôle du client (un serveur est deja la)
				this.mazeGameControler = new MazeGameControlerOnlineClient(mazeGame, IP, PORT);
				System.out.println("Mode client");
				this.setTitle(this.getTitle() + " - mode client");
			}
			else if (this.isServerPlayer){
				// pas de serveur détecté (connexion de test échouée), on joue donc le rôle de serveur
				this.mazeGameControler = new MazeGameControlerOnlineServer(mazeGame, IP, PORT);
				System.out.println("Mode serveur");
				this.setTitle(this.getTitle() + " - mode serveur");
			}
		}
		// mode local
		else {
			this.mazeGameControler = new MazeGameControler(mazeGame);
		}

		mazeGame.addObserver(this);
		// on initialise le controleur
		couloirIHMs = this.mazeGameControler.getCouloirsIHMs();
		pieceIHMs = this.mazeGameControler.getPiecesIHMs();
		treasureIHMs = this.mazeGameControler.getTreasuresIHMs();

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
		if (theme == "lava") {
			playerText.setForeground(Color.WHITE); 
		}
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
		imageIcon = new ImageIcon(MazeImageProvider.getImageCardTresorsFile("DosJeu", theme));
		//On cree la zone pour la pile de cartes
		tresorCard = new JLabel(imageIcon);
		
		//On cree la carte supplementaire, recuperant la deuxieme piece de la liste
		//On garde le côte aleatoire comme la liste est aleatoire
		//Il faut la deuxieme car la premiere est un angle de depart
		extraCard = this.mazeGameControler.getExtraCorridorIHM();

		//Bouton de rotation droit
		rotateRightButton = new JButton("\u21BB");
		rotateRightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mazeGameControler.rotateExtraCorridor();
			}
		});
		// on cree un panneau contenant differents plans
		extraCardPane = new JLayeredPane();
		extraCardPane.setPreferredSize(new Dimension(100, 100));

		// on cree une image de couloir pour la piece supplementaire
		imageIcon = new ImageIcon(MazeImageProvider.getImageFile(
			"Couloir",
			theme,
			extraCard.isNorthOpened(),
			extraCard.isSouthOpened(),
			extraCard.isEastOpened(),
			extraCard.isWestOpened(),
			false
		));
		disabledIcon = new ImageIcon(MazeImageProvider.getImageFile(
			"Couloir",
			theme,
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
				theme,
				couloirIHM.isNorthOpened(),
				couloirIHM.isSouthOpened(),
				couloirIHM.isEastOpened(),
				couloirIHM.isWestOpened(),
				false
			));
			disabledIcon = new ImageIcon(MazeImageProvider.getImageFile(
				"Couloir",
				theme,
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
		path = "/src/images/theme/" + theme + "/";
		ret = g.getAbsolutePath() + path + "bgGame.jpg";
		path = "/src/images/theme/" + theme + "/arrow/";
		
		//On définit les chemins des images grisées des flèches
		greyArrowDown = g.getAbsolutePath() + path + "greytone/pushDown.png";
		greyArrowUp = g.getAbsolutePath() + path + "greytone/pushUp.png";
		greyArrowLeft = g.getAbsolutePath() + path + "greytone/pushLeft.png";
		greyArrowRight = g.getAbsolutePath() + path + "greytone/pushRight.png";
		
		bg = new ImageIcon(ret);
		bgGame = new JLabel(); 
		bgGame.setIcon(bg);
		
		//On définit une liste dans laquelle on va stocker toutes les flèches présentes autour du plateau
		arrowsList = new LinkedList<JLabel>();
		
		//On crée toutes les flèches avec leurs images et on les ajoute à la liste
		pushDown2 = new JLabel();
		ret = g.getAbsolutePath() + path + "pushDown.png";
		pd2 = new ImageIcon(ret);
		pushDown2.setIcon(pd2);
		pushDown2.setDisabledIcon(new ImageIcon(greyArrowDown));
		arrowsList.add(pushDown2);
		
		pushDown4 = new JLabel();
		pd4 = new ImageIcon(ret);
		pushDown4.setIcon(pd4);
		pushDown4.setDisabledIcon(new ImageIcon(greyArrowDown));
		arrowsList.add(pushDown4);
		
		pushDown6 = new JLabel();
		pd6 = new ImageIcon(ret);
		pushDown6.setIcon(pd6);
		pushDown6.setDisabledIcon(new ImageIcon(greyArrowDown));
		arrowsList.add(pushDown6);
		
		pushUp2 = new JLabel();
		ret = g.getAbsolutePath() + path + "pushUp.png";
		pu2 = new ImageIcon(ret);
		pushUp2.setIcon(pu2);
		pushUp2.setDisabledIcon(new ImageIcon(greyArrowUp));
		arrowsList.add(pushUp2);
		
		pushUp4 = new JLabel();
		pu4 = new ImageIcon(ret);
		pushUp4.setIcon(pu4);
		pushUp4.setDisabledIcon(new ImageIcon(greyArrowUp));
		arrowsList.add(pushUp4);
		
		pushUp6 = new JLabel();
		pu6 = new ImageIcon(ret);
		pushUp6.setIcon(pu6);
		pushUp6.setDisabledIcon(new ImageIcon(greyArrowUp));
		arrowsList.add(pushUp6);
		
		pushRight2 = new JLabel();
		ret = g.getAbsolutePath() + path + "pushRight.png";
		pr2 = new ImageIcon(ret);
		pushRight2.setIcon(pr2);
		pushRight2.setDisabledIcon(new ImageIcon(greyArrowRight));
		arrowsList.add(pushRight2);
		
		pushRight4 = new JLabel();
		pr4 = new ImageIcon(ret);
		pushRight4.setIcon(pr4);
		pushRight4.setDisabledIcon(new ImageIcon(greyArrowRight));
		arrowsList.add(pushRight4);
		
		pushRight6 = new JLabel();
		pr6 = new ImageIcon(ret);
		pushRight6.setIcon(pr6);
		pushRight6.setDisabledIcon(new ImageIcon(greyArrowRight));
		arrowsList.add(pushRight6);
		
		pushLeft2 = new JLabel();
		ret = g.getAbsolutePath() + path + "pushLeft.png";
		pl2 = new ImageIcon(ret);
		pushLeft2.setIcon(pl2);
		pushLeft2.setDisabledIcon(new ImageIcon(greyArrowLeft));
		arrowsList.add(pushLeft2);
		
		pushLeft4 = new JLabel();
		pl4 = new ImageIcon(ret);
		pushLeft4.setIcon(pl4);
		pushLeft4.setDisabledIcon(new ImageIcon(greyArrowLeft));
		arrowsList.add(pushLeft4);
		
		pushLeft6 = new JLabel();
		pl6 = new ImageIcon(ret);
		pushLeft6.setIcon(pl6);
		pushLeft6.setDisabledIcon(new ImageIcon(greyArrowLeft));
		arrowsList.add(pushLeft6);

		pushDown2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mazeGameControler.alterMaze(1, "down");
				rotateRightButton.setEnabled(false);
				mazeAltered = true;
				for(JLabel arrow : arrowsList) {
					arrow.setEnabled(false);
				}
			}
		});

		pushDown4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mazeGameControler.alterMaze(3, "down");
				rotateRightButton.setEnabled(false);
				mazeAltered = true;
				for(JLabel arrow : arrowsList) {
					arrow.setEnabled(false);
				}
			}
		});

		pushDown6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mazeGameControler.alterMaze(5, "down");
				rotateRightButton.setEnabled(false);
				mazeAltered = true;
				for(JLabel arrow : arrowsList) {
					arrow.setEnabled(false);
				}
			}
		});

		pushUp2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mazeGameControler.alterMaze(1, "up");
				rotateRightButton.setEnabled(false);
				mazeAltered = true;
				for(JLabel arrow : arrowsList) {
					arrow.setEnabled(false);
				}
			}
		});

		pushUp4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mazeGameControler.alterMaze(3, "up");
				rotateRightButton.setEnabled(false);
				mazeAltered = true;
				for(JLabel arrow : arrowsList) {
					arrow.setEnabled(false);
				}
			}
		});

		pushUp6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mazeGameControler.alterMaze(5, "up");
				rotateRightButton.setEnabled(false);
				mazeAltered = true;
				for(JLabel arrow : arrowsList) {
					arrow.setEnabled(false);
				}
			}
		});

		pushRight2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mazeGameControler.alterMaze(1, "right");
				rotateRightButton.setEnabled(false);
				mazeAltered = true;
				for(JLabel arrow : arrowsList) {
					arrow.setEnabled(false);
				}
			}
		});

		pushRight4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mazeGameControler.alterMaze(3, "right");
				rotateRightButton.setEnabled(false);
				mazeAltered = true;
				for(JLabel arrow : arrowsList) {
					arrow.setEnabled(false);
				}
			}
		});

		pushRight6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mazeGameControler.alterMaze(5, "right");
				rotateRightButton.setEnabled(false);
				mazeAltered = true;
				for(JLabel arrow : arrowsList) {
					arrow.setEnabled(false);
				}
			}
		});

		pushLeft2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mazeGameControler.alterMaze(1, "left");
				rotateRightButton.setEnabled(false);
				mazeAltered = true;
				for(JLabel arrow : arrowsList) {
					arrow.setEnabled(false);
				}
			}
		});

		pushLeft4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mazeGameControler.alterMaze(3, "left");
				rotateRightButton.setEnabled(false);
				mazeAltered = true;
				for(JLabel arrow : arrowsList) {
					arrow.setEnabled(false);
				}
			}
		});
		
		pushLeft6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mazeGameControler.alterMaze(5, "left");
				rotateRightButton.setEnabled(false);
				mazeAltered = true;
				for(JLabel arrow : arrowsList) {
					arrow.setEnabled(false);
				}
			}
		});

		//on positionne les fleches en coordonnees pixel comme la fenetre ne change pas de taille
		//fleches vers le bas
		generalBoard.add(pushDown2, "pos 105 0");
		generalBoard.add(pushDown4, "pos 305 0");
		generalBoard.add(pushDown6, "pos 505 0");
		
		//fleches vers le haut
		generalBoard.add(pushUp2, "pos 95 680");
		generalBoard.add(pushUp4, "pos 295 680");
		generalBoard.add(pushUp6, "pos 495 680");
		
		//fleches vers la gauche
		generalBoard.add(pushLeft2, "pos 680 105");
		generalBoard.add(pushLeft4, "pos 680 305");
		generalBoard.add(pushLeft6, "pos 680 505");
		
		//fleches vers la droite
		generalBoard.add(pushRight2, "pos 0 95");
		generalBoard.add(pushRight4, "pos 0 295");
		generalBoard.add(pushRight6, "pos 0 495");
		
		//ajout des elements sur la fenetre de jeu
		generalBoard.add(tresorToCatch,"pos 0.892al 0.458al");
		generalBoard.add(mazeBoard, "pos 0 0");
		generalBoard.add(tresorCard, "pos 0.93al 0.45al");
		generalBoard.add(extraCardPane, "pos 0.92al 0.03al"); //AbsoluteLayout : on positionne en pourcentage de la fenetre
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
				"Quitter", "Retourner au Menu"};

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
				if(this.mazeGameControler.getCurrentScorePlayer() == this.mazeGameControler.getScoreMax()){
					Treasure treasurePrincess = mazeGameControler.currentTreasureToCatch();
					JLabel princessIHM = new JLabel (new ImageIcon(MazeImageProvider.getImageFile(treasurePrincess.getTreasureId())));
					princessIHM.setPreferredSize(new Dimension(100, 100));
					princessIHM.setBounds(0, 0, 100, 100);
					princessIHM.setOpaque(false);
					((JLayeredPane)this.mazeBoard.getComponent(treasurePrincess.getTreasureX() + 7*treasurePrincess.getTreasureY())).add(princessIHM, TREASURE_LAYER);
					if(destinationX == CoordInitialeX && destinationY == CoordInitialeY){
						this.mazeGameControler.treasureCatchedPlateau(treasurePrincess);
						int n = JOptionPane.showOptionDialog(frame,
								"Félicitation, le vainqueur de cette bataille acharnée est " + nameJeuCourant + ". Allez retrouver votre dulcinée !",
								"VICTOIRE",
								JOptionPane.OK_CANCEL_OPTION,
								JOptionPane.PLAIN_MESSAGE,
								null,
								options,
								options[1]);
						if(n==0){
							System.exit(0);
						}else if(n == 1){
							super.dispose();
							LauncherGUI.main(null);
						}
					}
				}
				this.mazeGameControler.switchPlayer();

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
					if(treasureToCatch.getTreasureId() == 25){
						imageTreasureToCatch = new ImageIcon(MazeImageProvider.getImageFile(29));
					}else if(treasureToCatch.getTreasureId() == 26){
						imageTreasureToCatch = new ImageIcon(MazeImageProvider.getImageFile(30));
					}else if(treasureToCatch.getTreasureId() == 27){
						imageTreasureToCatch = new ImageIcon(MazeImageProvider.getImageFile(31));
					}else if(treasureToCatch.getTreasureId() == 28){
						imageTreasureToCatch = new ImageIcon(MazeImageProvider.getImageFile(32));
					}else{
						imageTreasureToCatch = new ImageIcon(MazeImageProvider.getImageFile(treasureToCatch.getTreasureId()));
					}	
				}
				//On cree la zone pour la pile de cartes
				tresorToCatch.setIcon(imageTreasureToCatch);
				mazeAltered = false;
				// réautoriser les boutons pour le prochain joueur
				rotateRightButton.setEnabled(true);

				for(JLabel arrow : arrowsList) {
					arrow.setEnabled(true);
				}
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
		
		if(
			(
				this.playOnline
				&& this.isServerPlayer
				&& this.mazeGameControler.getColorCurrentPlayer() == Couleur.BLEU
			)
			||
			(
				this.playOnline
				&& this.isClientPlayer
				&& this.mazeGameControler.getColorCurrentPlayer() == Couleur.ROUGE
			)
		) {
			waitDialog.setVisible(true); ;
			return;
		}
		else {
			waitDialog.setVisible(false);
		}
		
		//Lors d'un changement de score, on met à jour l'affichage du tableau
		scoreMario.setText("Mario : " + mazeGameControler.getRedPlayerScore());
		scoreLuigi.setText("Luigi : " + mazeGameControler.getBluePlayerScore());
		if (nbPlayer == 3 || nbPlayer == 4) {
			scoreYoshi.setText("Yoshi : " + mazeGameControler.getYellowPlayerScore());
		}
		if (nbPlayer == 4) {
			scoreToad.setText("Toad : " + mazeGameControler.getGreenPlayerScore());
		}File g = new File("");
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
		Treasure treasureToCatch = this.mazeGameControler
				.currentTreasureToCatch();

		if(treasureToCatch != null){
			imageTreasureToCatch = new ImageIcon(MazeImageProvider.getImageFile(treasureToCatch.getTreasureId()));
		}else{
			imageTreasureToCatch = new ImageIcon(MazeImageProvider.getImageFile(-1));
		}
		//On cree la zone pour la pile de cartes
		tresorToCatch.setIcon(imageTreasureToCatch);

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
					theme,
					extraCard.isNorthOpened(),
					extraCard.isSouthOpened(),
					extraCard.isEastOpened(),
					extraCard.isWestOpened(),
					false
			));
			disabledIcon = new ImageIcon(MazeImageProvider.getImageFile(
					"Couloir",
					theme,
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
						theme,
						couloirIHM.isNorthOpened(),
						couloirIHM.isSouthOpened(),
						couloirIHM.isEastOpened(),
						couloirIHM.isWestOpened(),
						false
				));
				disabledIcon = new ImageIcon(MazeImageProvider.getImageFile(
						"Couloir",
						theme,
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
		boolean upDown = (selectedValueDirection.equals(CMD_HAUT) || selectedValueDirection.equals(CMD_BAS));
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
		if(selectedValueNumber == null) {
			return false;
		}

		// modification du labyrinthe
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
