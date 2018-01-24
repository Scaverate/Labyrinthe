package model;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import tools.MazeCouloirsFactory;
import tools.MazeTreasureFactory;

public class Plateau implements BoardGames {
	public Plateau(int nbPlayer) {
		List<Treasures> tresorFactoryOutput;
		this.treasureToDraw = new LinkedList<>(); // Liste correspondant à la pioche du jeu
		this.treasures = new LinkedList<>();
		this.gameList = new LinkedList<>();
		this.nbPlayer = nbPlayer;
		
		tresorFactoryOutput = MazeTreasureFactory.newTreasure();
		for(Treasures treasure : tresorFactoryOutput){
			if(treasure.getTreasureId() < 25){
				treasureToDraw.add(treasure);
				this.treasures.add(treasure);
			}
		}
		
		scoreMax = 24/nbPlayer; //Score max que chaque joueur doit atteindre

		// Création des jeux Rouge et Bleu qui sont toujours en jeu
		this.jeuRouge = new Jeu(Couleur.ROUGE);
		this.jeuBleu = new Jeu(Couleur.BLEU);
		
		this.gameList.add(this.jeuRouge);
		this.gameList.add(this.jeuBleu);
		
		if (nbPlayer == 3 || nbPlayer == 4) {
			this.jeuJaune = new Jeu(Couleur.JAUNE);
			this.gameList.add(this.jeuJaune);
		
			if (nbPlayer == 4) {
				this.jeuVert = new Jeu(Couleur.VERT);
				this.gameList.add(this.jeuVert);
			}
		}
		
		for (Jeu game : this.gameList) {
			game.drawCard(this.treasureToDraw);
			game.setCoordInit(new Coord(game.getPiecesIHM().get(0).getX(),game.getPiecesIHM().get(0).getY()));
		}
		
		this.jeuCourant = this.jeuRouge;
		this.message = "";
		MazeCouloirsFactory.resetCorridors();
		this.couloirs = MazeCouloirsFactory.newCouloirs();
		this.extraCorridor = MazeCouloirsFactory.getExtraCorridor();
		this.extraTreasure = null;
		
	}

	/*
	 * Changement de joueur en respectant l'ordre des aiguilles d'une montre:
	 * Rouge - Jaune - Bleu - Vert - Rouge - Jaune - ...
	*/
	public void switchPlayer() {
		if (this.jeuCourant == this.jeuRouge) {
			if (this.nbPlayer == 2) {
				this.jeuCourant = this.jeuBleu;
			} else {
				this.jeuCourant = this.jeuJaune;
			}
		} else if (this.jeuCourant == this.jeuBleu) {
			if (this.nbPlayer == 2 || this.nbPlayer == 3) {
				this.jeuCourant = this.jeuRouge;
			} else {
				this.jeuCourant = this.jeuVert;
			}
		} else if (this.jeuCourant == this.jeuJaune) {
			this.jeuCourant = this.jeuBleu;
		} else if (this.jeuCourant == this.jeuVert) {
			this.jeuCourant = this.jeuRouge;
		}

		this.isMazeAltered = false; //On remet le déplacement du Labyrinthe

		if(this.jeuCourant.getTreasureToCatch() == null && this.jeuCourant.getScorePlayer() < this.scoreMax){
			this.jeuCourant.drawCard(this.treasureToDraw); //Piocher une carte si le joueur n'a pas atteint le score final
		}
	}

	/*
	 * Modifie le Labyrinthe
	 *
	 * @param int    position  - indique la ligne (ou colonne) à déplacer
	 * @param String direction - indique la direction (up, down, left, right)
	 */
	public boolean alterMaze(int position, String direction) {

		List<Couloirs> couloirPushed;
		List<Couloirs> couloirsToRemove = new LinkedList<>();
		List<Treasures> treasuresPushed;
		List<Treasures> treasuresToRemove = new LinkedList<>();

		if(this.isMazeAltered) {
			System.err.println("ERREUR : Tu as déjà déplacé le labyrinthe ce tour-ci");
			return false;
		}

		// Les positions (lignes & colonnes) amovibles sont les colonnes 1,3,5
		if(position != 1 && position != 3 && position != 5) {
			System.err.println("ERREUR : Mauvaise position");
			return false;
		}

		if (!direction.equals("down") && !direction.equals("up") && !direction.equals("right") && !direction.equals("left")) {
			System.err.println("ERREUR : Mauvaise direction");
			return false;
		}

		// On ajoutes dans une liste les couloirs qui seront supprimés suivant le type de command
		for(Couloirs couloir : this.couloirs) {
			if(direction.equals("down") || direction.equals("up")){
				if(couloir.getX() == position) {
					couloirsToRemove.add(couloir);
				}
			}
			else {
				if(couloir.getY() == position) {
					couloirsToRemove.add(couloir);
				}
			}
		}

		// De même pour les trésors
		for(Treasures treasure : this.treasures) {
			if(direction.equals("down") || direction.equals("up")){
				if(treasure.getTreasureX() == position) {
					treasuresToRemove.add(treasure);
				}
			}
			else {
				if(treasure.getTreasureY() == position) {
					treasuresToRemove.add(treasure);
				}
			}
		}

		// On supprime le trésor sur la pièce supplémentaire de la liste des trésors présents
		if(this.extraTreasure != null) {
			this.treasures.remove(this.extraTreasure);
		}

		this.pushPlayers(position, direction);
		treasuresPushed = this.pushTreasures(position,direction);
		couloirPushed = this.pushCorridors(position,direction);

		this.couloirs.removeAll(couloirsToRemove); // On supprime tous les anciens couloirs
		this.couloirs.addAll(couloirPushed); //On ajoute les couloirs avec leurs nouvelles valeurs
		this.treasures.removeAll(treasuresToRemove); // On supprime les anciens trésors
		this.treasures.addAll(treasuresPushed); // On ajoute les trésors avec leurs nouvelles valeurs

		this.isMazeAltered = true;

		return true;
	}
	
	private void updateFromDirection(int position, String direction) {
		switch(direction) {
			case "down":
				this.insertX = position;
				this.insertY = 0;
				this.unchangedAxe = "x";
				this.updateX = 0;
				this.updateY = 1;
				break;
			case "up":
				this.insertX = position;
				this.insertY = 6;
				this.unchangedAxe = "x";
				this.updateX = 0;
				this.updateY = -1;
				break;
			case "right":
				this.insertX = 0;
				this.insertY = position;
				this.unchangedAxe = "y";
				this.updateX = 1;
				this.updateY = 0;
				break;
			case "left":
				this.insertX = 6;
				this.insertY = position;
				this.unchangedAxe = "y";
				this.updateX = -1;
				this.updateY = 0;
				break;
			default:
				System.out.println("Impossible de récupérer x et y");
		}
	}
	
	private List<Treasures> pushTreasures(int position, String direction) {
		List<Treasures> treasuresToAdd = new LinkedList<>();
		List<Treasures> drawableTreasureToAdd = new LinkedList<>();
		List<Treasures> drawableTreasureToRemove = new LinkedList<>();
		Treasure movedTreasure;
		
		if (!direction.equals("down") && !direction.equals("up") && !direction.equals("right") && !direction.equals("left")) {
			return null;
		}
		
		this.updateFromDirection(position, direction);
		
		//On regarde si un trésor est présent sur la pièce supplémentaire
		if (this.extraTreasure != null) {
			Treasure oldExtra = new Treasure(
				this.insertX, 
				this.insertY, 
				this.extraTreasure.getTreasureId(),
				this.extraTreasure.getTreasureType(),
				this.extraTreasure.isCatched()
			);

			System.out.println("Trésor qui vient d'être inséré : " + oldExtra + "\n");

			treasuresToAdd.add(oldExtra);
			
			//Gestion des draws
			for(Treasures treasure : this.treasureToDraw) {
				//TODO voir avec Martin
				if (treasure.getTreasureX() == -1 && treasure.getTreasureY() == -1) {
					drawableTreasureToRemove.add(treasure);
					drawableTreasureToAdd.add(oldExtra);
					System.out.println("Si, l'objet n'est affecté à personne :");
					System.out.println("drawableTreasureToRemove : " + drawableTreasureToRemove);
					System.out.println("drawableTreasureToAdd : " + drawableTreasureToAdd + "\n");
				}
			}

			// Changement de trésor à attraper
			for (Jeu game : this.gameList) { //TODO : Potentiellement un bug ici
				Treasure gameTreasureToCatch = game.getTreasureToCatch();
				
				if(gameTreasureToCatch != null) {
					if (this.extraTreasure.getTreasureX() == gameTreasureToCatch.getTreasureX() &&
							this.extraTreasure.getTreasureY() == gameTreasureToCatch.getTreasureY()) {
						System.out.println("extraTreasure = gameTreasureToCatch");
						System.out.println("extraTreasure : " + extraTreasure);
						System.out.println("gameTreasureToCatch : " + gameTreasureToCatch + "\n");

						game.setTreasureToCatch(oldExtra);
	 				}
				}
				
 			}
			
			this.extraTreasure = null;
		}
		
		for (Treasures treasure : this.treasures) {
			if ((this.unchangedAxe.equals("x") && treasure.getTreasureX() == position) || (this.unchangedAxe.equals("y") && treasure.getTreasureY() == position)) {
				// Suivant le type de push, on vérifie si le couloir est au bord du plateau
				if ((direction.equals("up") && treasure.getTreasureY() == 0) ||
						(direction.equals("down") && treasure.getTreasureY() == 6) ||
						(direction.equals("left") && treasure.getTreasureX() == 0) ||
						(direction.equals("right") && treasure.getTreasureX() == 6)) {
					this.extraTreasure = new Treasure(
							-1,
							-1,
							treasure.getTreasureId(),
							treasure.getTreasureType(),
							treasure.isCatched()
					);

					movedTreasure = this.extraTreasure;

				} else {
					movedTreasure = new Treasure(
						treasure.getTreasureX() + this.updateX,
						treasure.getTreasureY() + this.updateY,
						treasure.getTreasureId(),
						treasure.getTreasureType(),
						treasure.isCatched()
					);
					
					treasuresToAdd.add(movedTreasure);
				}

				for (Jeu game : this.gameList) {
					Treasure gameTreasureToCatch = game.getTreasureToCatch();

					if(gameTreasureToCatch != null) {
						if(treasure.getTreasureX() == gameTreasureToCatch.getTreasureX() &&
								treasure.getTreasureY() == gameTreasureToCatch.getTreasureY()) {
							game.setTreasureToCatch(movedTreasure);
						}
					}
				}
			}
		}
		
		for (Treasures treasure : this.treasureToDraw) {
			if ((this.unchangedAxe.equals("x") && treasure.getTreasureX() == position) || (this.unchangedAxe.equals("y") && treasure.getTreasureY() == position)) {
				// Suivant le type de push, on vérifie si le couloir est au bord du plateau
				if ((direction.equals("up") && treasure.getTreasureY() == 0) ||
						(direction.equals("down") && treasure.getTreasureY() == 6) ||
						(direction.equals("left") && treasure.getTreasureX() == 0) ||
						(direction.equals("right") && treasure.getTreasureX() == 6)) {
					movedTreasure = new Treasure(
							-1,
							-1,
							treasure.getTreasureId(),
							treasure.getTreasureType(),
							treasure.isCatched()
					);
				} else {
					movedTreasure = new Treasure(
							treasure.getTreasureX() + this.updateX,
							treasure.getTreasureY() + this.updateY,
							treasure.getTreasureId(),
							treasure.getTreasureType(),
							treasure.isCatched()
					);
				}

				drawableTreasureToAdd.add(movedTreasure);
				drawableTreasureToRemove.add(treasure);
			}
		}
		
		this.treasureToDraw.removeAll(drawableTreasureToRemove);
		this.treasureToDraw.addAll(drawableTreasureToAdd);
		
		return treasuresToAdd;
	}

	private void pushPlayers(int position, String direction) {
		List<Jeu> playerToMoveOut = new LinkedList<>();
		List<Jeu> playerMoved = new LinkedList<>();

		if (!direction.equals("down") && !direction.equals("up") && !direction.equals("right") && !direction.equals("left")) {
			return;
		}

		this.updateFromDirection(position, direction);

		for(Couloirs couloir : this.couloirs) {
			if ((this.unchangedAxe.equals("x") && couloir.getX() == position) || (this.unchangedAxe.equals("y") && couloir.getY() == position)) {
				// Suivant le type de push, on vérifie si le couloir est au bord du plateau
				if ((direction.equals("up") && couloir.getY() == 0) ||
						(direction.equals("down") && couloir.getY() == 6) ||
						(direction.equals("left") && couloir.getX() == 0) ||
						(direction.equals("right") && couloir.getX() == 6)) {
					for (Jeu game : this.gameList) {
						if (game.isPieceHere(couloir.getX(), couloir.getY())) {
							playerToMoveOut.add(game);
						}
					}
				}
			}
		}

		for(Couloirs couloir : this.couloirs) {
			if ((this.unchangedAxe.equals("x") && couloir.getX() == position) || (this.unchangedAxe.equals("y") && couloir.getY() == position)) {
				if ((direction.equals("up") && couloir.getY() > 0) ||
						(direction.equals("down") && couloir.getY() < 6) ||
						(direction.equals("left") && couloir.getX() > 0) ||
						(direction.equals("right") && couloir.getX() < 6)) {
					// on déplace le joueur d'une case
					for (Jeu game : this.gameList) {
						if (game.isPieceHere(couloir.getX(), couloir.getY()) && !playerToMoveOut.contains(game) && !playerMoved.contains(game)) {
							game.move(couloir.getX(), couloir.getY(),couloir.getX() + this.updateX, couloir.getY() + this.updateY);
							playerMoved.add(game);
						}
 					}
				}
			}
		}

		for(Jeu jeu : playerToMoveOut) {
			jeu.move(jeu.getCoord().x, jeu.getCoord().y, this.insertX, this.insertY);
		}
	}

	private List<Couloirs> pushCorridors(int position, String direction) {
		List<Couloirs> corridorsToAdd = new LinkedList<>();

		if (!direction.equals("down") && !direction.equals("up") && !direction.equals("right") && !direction.equals("left")) {
			return null;
		}

		this.updateFromDirection(position, direction);

		// On ajoute la pièce supplémentaire qu'on veut insérer dans le plateau
		Couloirs oldExtraCorridor = new CouloirAmovible(
				new Coord(this.insertX, this.insertY),
				this.extraCorridor.isNorthOpened(),
				this.extraCorridor.isSouthOpened(),
				this.extraCorridor.isEastOpened(),
				this.extraCorridor.isWestOpened()
		);

		corridorsToAdd.add(oldExtraCorridor);

		for(Couloirs couloir : this.couloirs) {
			if ((this.unchangedAxe.equals("x") && couloir.getX() == position) || (this.unchangedAxe.equals("y") && couloir.getY() == position)) {
				// Suivant le type de push, on vérifie si le couloir est au bord du plateau
				if ((direction.equals("up") && couloir.getY() == 0) ||
						(direction.equals("down") && couloir.getY() == 6) ||
						(direction.equals("left") && couloir.getX() == 0) ||
						(direction.equals("right") && couloir.getX() == 6)) {
					this.extraCorridor = (CouloirAmovible) couloir;
				} else {
					CouloirAmovible newCorridor = new CouloirAmovible(
							new Coord(couloir.getX() + this.updateX, couloir.getY() + this.updateY),
							couloir.isNorthOpened(),
							couloir.isSouthOpened(),
							couloir.isEastOpened(),
							couloir.isWestOpened()
					);

					corridorsToAdd.add(newCorridor);
				}
			}
		}
		return corridorsToAdd;
	}

	public void rotateExtraCorridor() { this.extraCorridor.rotate(); }

	public boolean isMoveOk(int xInit, int yInit, int xFinal, int yFinal){
		boolean canMove = false;

		List<Coord> moveOkCoords;

		moveOkCoords = this.findPath(new Coord(xInit, yInit));

		for(Coord coord : moveOkCoords) {
			if(coord.x == xFinal && coord.y == yFinal) {
				canMove = true;
			}
		}
		
		return canMove;
	}

	@Override
	public boolean move(Coord initCoord, Coord finalCoord) {
		boolean hasMoved = false;
		if(this.jeuCourant != null){
			hasMoved = jeuCourant.move(initCoord.x, initCoord.y, finalCoord.x, finalCoord.y);
		}
		return hasMoved;
	}

	@Override
	public boolean isEnd() { return false; }

	@Override
	public String getMessage() { return this.message; }

	@Override
	public Couleur getColorCurrentPlayer() {
		Couleur couleur = null;
		if(this.jeuCourant == this.jeuBleu){
			couleur = Couleur.BLEU;
		}
		else if(this.jeuCourant == this.jeuRouge){
			couleur = Couleur.ROUGE;
		}
		else if(this.jeuCourant == this.jeuJaune) {
			couleur = Couleur.JAUNE;
		}
		else if(this.jeuCourant == this.jeuVert) {
			couleur = Couleur.VERT;
		}
		return couleur;
	}

	@Override
	public Couleur getPieceColor(int x, int y) { return this.jeuCourant.getPieceColor(x,y); }

	public List<PieceIHMs> getPiecesIHM(){
		List<PieceIHMs> finalList = new LinkedList<>();

		for (Jeu game : gameList) {
			if (game.getPiecesIHM() != null) {
				finalList.addAll(game.getPiecesIHM());
			}
 		}

		return finalList;
	}

	public List<CouloirIHM> getCouloirsIHMs() {
		List<CouloirIHM> couloirIHMs = new LinkedList<CouloirIHM>();

		for(Couloirs couloir : this.couloirs) {
			couloirIHMs.add(new CouloirIHM(couloir));
		}

		return couloirIHMs;
	}

	public CouloirIHM getExtraCorridorIHM() {
		CouloirIHM extraCorridorIHM = new CouloirIHM(this.extraCorridor);
		return extraCorridorIHM;
	}

	public TreasureIHM getExtraTreasureIHM() {
		TreasureIHM extraTreasureIHM;
		if(this.extraTreasure != null) {
			extraTreasureIHM = new TreasureIHM(this.extraTreasure);
		}
		else {
			extraTreasureIHM = null;
		}
		return extraTreasureIHM;
	}

	public List<TreasureIHMs> getTreasuresIHMs() {
		List<TreasureIHMs> treasureIHMs = new LinkedList<TreasureIHMs>();

		for(Treasures treasure : this.treasures) {
			treasureIHMs.add(new TreasureIHM(treasure));
		}

		return treasureIHMs;
	}

	public List<Coord> findPath(Coord coord){
		Couloirs couloirFound = null;
		List<Couloirs> couloirsFound;
		List<Coord> coordsFound = new LinkedList<>();


		for(Couloirs couloir : this.couloirs){
			if(couloir.getX() == coord.x && couloir.getY() == coord.y) {
				couloirFound = couloir;
			}
		}

		couloirsFound = findPath(couloirFound, this.couloirs, new LinkedList<Couloirs>());

		for(Couloirs couloir : couloirsFound) {
			coordsFound.add(new Coord(couloir.getX(), couloir.getY()));
		}

		return coordsFound;
	}

	/**
	 * Recherche les chemins possibles à partir d'un couloir
	 *
	 * @param  c - Couloirs
	 * @param  corridors - List<Couloirs>
	 *
	 * @return List<Couloirs> reachableCorridors
	 */
	private List<Couloirs> findPath(Couloirs c, List<Couloirs> corridors, List<Couloirs> reachableCorridors) {
		reachableCorridors.add(c);

		if (c.isNorthOpened()) {
			Couloirs neighbor = null;
			for (Couloirs corridor : corridors) {
				if (corridor.getX() == c.getX() && corridor.getY() == c.getY()-1) {
					neighbor = corridor;
				}
			}

			if (neighbor != null && neighbor.isSouthOpened() && !reachableCorridors.contains(neighbor)) {
				findPath(neighbor, corridors, reachableCorridors);
			}
		}

		if (c.isEastOpened()) {
			Couloirs neighbor = null;
			for (Couloirs corridor : corridors) {
				if (corridor.getX() == c.getX()+1 && corridor.getY() == c.getY()) {
					neighbor = corridor;
				}
			}

			if (neighbor != null && neighbor.isWestOpened() && !reachableCorridors.contains(neighbor)) {
				findPath(neighbor, corridors, reachableCorridors);
			}
		}

		if (c.isSouthOpened()) {
			Couloirs neighbor = null;
			for (Couloirs corridor : corridors) {
				if (corridor.getX() == c.getX() && corridor.getY() == c.getY()+1) {
					neighbor = corridor;
				}
			}

			if (neighbor != null && neighbor.isNorthOpened() && !reachableCorridors.contains(neighbor)) {
				findPath(neighbor, corridors, reachableCorridors);
			}
		}

		if (c.isWestOpened()) {
			Couloirs neighbor = null;
			for (Couloirs corridor : corridors) {
				if (corridor.getX() == c.getX()-1 && corridor.getY() == c.getY()) {
					neighbor = corridor;
				}
			}

			if (neighbor != null && neighbor.isEastOpened() && !reachableCorridors.contains(neighbor)) {
				findPath(neighbor, corridors, reachableCorridors);
			}
		}

		return reachableCorridors;
	}

	public Treasure currentTreasureToCatch(){
		Treasure treasureToCatch = null;
		treasureToCatch = this.jeuCourant.getTreasureToCatch();
		return treasureToCatch;
	}

	public void setCurrentTreasureToCatch(Treasure treasureToCatch){
		this.jeuCourant.setTreasureToCatch(treasureToCatch);
	}

	public boolean treasureCatched(Treasure treasuresCatched){
		if(this.jeuCourant.addTreasureCatched(treasuresCatched)){
			Treasure treasureToDelete = null;
			for(Treasures treasure : this.treasures){
				if(treasuresCatched.equals(treasure)) {
					treasureToDelete = (Treasure) treasure;
				}
			}
			if(treasureToDelete == null) {
				System.out.println("TRESOR PAS SUPPRIMER");
			}
			this.treasures.remove(treasureToDelete);
			this.jeuCourant.setTreasureToCatch(null);
			if(this.jeuCourant.getScorePlayer() == getScoreMax()){
				Treasure princess = this.jeuCourant.getPrincessToCatch();
				this.jeuCourant.setTreasureToCatch(princess);
				this.treasures.add(jeuCourant.getTreasureToCatch());
			}
			return true;
		}else{
			return false;
		}
	}

	public int getCurrentScorePlayer() { return this.jeuCourant.getScorePlayer(); }

	public Coord getCurrentCoordInitiale() { return this.jeuCourant.getCoordInitiale(); }

	public int getScoreMax( ){ return this.scoreMax; }

	public String getNamePlayer() { return this.jeuCourant.getNamePlayer(); }
	
	public int getBluePlayerScore() { return this.jeuBleu.getScorePlayer(); }
	
	public int getRedPlayerScore() { return this.jeuRouge.getScorePlayer(); }
	
	public int getYellowPlayerScore() { return this.jeuJaune.getScorePlayer(); }
	
	public int getGreenPlayerScore() { return this.jeuVert.getScorePlayer(); }
	
	private List<Treasures> getMoveableTreasures(List<Treasures> listTreasures) {
		List<Treasures> moveableTreasures = new LinkedList<>();

		for (Treasures treasure: listTreasures) {
			if (treasure.getTreasureType().equals("TreasureMoveable")) {
				moveableTreasures.add(treasure);
			}
		}

		return moveableTreasures;
	}
	
	private int scoreMax = 0;
	private boolean isMazeAltered = false;
	private int nbPlayer;
	private Jeu jeuBleu, jeuRouge, jeuJaune, jeuVert, jeuCourant;
	private List<Jeu> gameList;
	private String message;
	private List<Couloirs> couloirs;
	private CouloirAmovible extraCorridor;
	private Treasure extraTreasure;
	private List<Treasures> treasures;
	private List<Treasures> treasureToDraw;
	private int insertX = 0;
	private int insertY = 0;
	private String unchangedAxe = "";
	private int updateX = 0;
	private int updateY = 0;


	// tests
	public static void main(String[] args){
		System.out.println("Class Plateau.java\n");
		Plateau plateau = new Plateau(2);
		Comparator compareCouloirs = new Comparator<Couloirs>() {
			@Override
			public int compare(Couloirs couloir1, Couloirs couloir2) {
				int compare;
				if(couloir1.getX() == couloir2.getX()) {
					if(couloir1.getY() == couloir2.getY()) {
						compare = 0;
					}
					else if (couloir1.getY() > couloir2.getY()){
						compare = 1;
					}
					else {
						compare = -1;
					}
				}
				else if(couloir1.getX() > couloir2.getX()){
					compare = 1;
				}
				else {
					compare = -1;
				}
				return compare;
			}
		};
		Comparator compareTreasures = new Comparator<Treasures>() {
			@Override
			public int compare(Treasures treasure1, Treasures treasure2) {
				int compare;
				if(treasure1.getTreasureX() == treasure2.getTreasureX()) {
					if(treasure1.getTreasureY() == treasure2.getTreasureY()) {
						compare = 0;
					}
					else if (treasure1.getTreasureY() > treasure2.getTreasureY()){
						compare = 1;
					}
					else {
						compare = -1;
					}
				}
				else if(treasure1.getTreasureX() > treasure2.getTreasureX()){
					compare = 1;
				}
				else {
					compare = -1;
				}
				return compare;
			}
		};

		List<Treasures> moveableTreasures = plateau.getMoveableTreasures(plateau.treasureToDraw);

		Collections.sort(moveableTreasures, compareTreasures);
		System.out.println(moveableTreasures);

		plateau.alterMaze(1, "up");
		moveableTreasures = plateau.getMoveableTreasures(plateau.treasureToDraw);
		Collections.sort(moveableTreasures, compareTreasures);
		System.out.println(moveableTreasures);

		plateau.alterMaze(1, "up");
		moveableTreasures = plateau.getMoveableTreasures(plateau.treasureToDraw);
		Collections.sort(moveableTreasures, compareTreasures);
		System.out.println(moveableTreasures);
	}

}
