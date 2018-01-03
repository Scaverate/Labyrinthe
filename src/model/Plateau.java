package model;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import tools.MazeCouloirsFactory;
import tools.MazeTreasureFactory;

public class Plateau implements BoardGames {
	public Plateau(int nbPlayer) {
		this.treasures = MazeTreasureFactory.newTreasure();
		//Liste correspondant à la pioche du jeu
		this.treasureToDraw = new LinkedList<Treasures>(this.treasures);
		//Score maximum que chaque joueur doit atteindre
		scoreMax = 24/nbPlayer;
		switch (nbPlayer) {
		case 2 :
			jeuRouge = new Jeu(Couleur.ROUGE);
			jeuRouge.setCoordInit(new Coord(0,0));
			jeuRouge.drawCard(this.treasureToDraw);
			jeuBleu = new Jeu(Couleur.BLEU);
			jeuBleu.drawCard(this.treasureToDraw);
			jeuRouge.setCoordInit(new Coord(jeuRouge.getPiecesIHM().get(0).getX(),jeuRouge.getPiecesIHM().get(0).getY()));
			jeuBleu.setCoordInit(new Coord(jeuBleu.getPiecesIHM().get(0).getX(),jeuBleu.getPiecesIHM().get(0).getY()));
			break;
		case 3 :
			jeuRouge = new Jeu(Couleur.ROUGE);
			jeuRouge.drawCard(this.treasureToDraw);
			jeuBleu = new Jeu(Couleur.BLEU);
			jeuBleu.drawCard(this.treasureToDraw);
			jeuJaune = new Jeu(Couleur.JAUNE);
			jeuJaune.drawCard(this.treasureToDraw);
			jeuRouge.setCoordInit(new Coord(jeuRouge.getPiecesIHM().get(0).getX(),jeuRouge.getPiecesIHM().get(0).getY()));
			jeuBleu.setCoordInit(new Coord(jeuBleu.getPiecesIHM().get(0).getX(),jeuBleu.getPiecesIHM().get(0).getY()));
			jeuJaune.setCoordInit(new Coord(jeuJaune.getPiecesIHM().get(0).getX(),jeuJaune.getPiecesIHM().get(0).getY()));
			break;
		case 4 :
			jeuRouge = new Jeu(Couleur.ROUGE);
			jeuRouge.drawCard(this.treasureToDraw);
			jeuBleu = new Jeu(Couleur.BLEU);
			jeuBleu.drawCard(this.treasureToDraw);
			jeuJaune = new Jeu(Couleur.JAUNE);
			jeuJaune.drawCard(this.treasureToDraw);
			jeuVert = new Jeu(Couleur.VERT);
			jeuVert.drawCard(this.treasureToDraw);
			jeuRouge.setCoordInit(new Coord(jeuRouge.getPiecesIHM().get(0).getX(),jeuRouge.getPiecesIHM().get(0).getY()));
			jeuBleu.setCoordInit(new Coord(jeuBleu.getPiecesIHM().get(0).getX(),jeuBleu.getPiecesIHM().get(0).getY()));
			jeuJaune.setCoordInit(new Coord(jeuJaune.getPiecesIHM().get(0).getX(),jeuJaune.getPiecesIHM().get(0).getY()));
			jeuVert.setCoordInit(new Coord(jeuVert.getPiecesIHM().get(0).getX(),jeuVert.getPiecesIHM().get(0).getY()));
			break;
		default:
				System.out.println("La creation des jeux a echoue");
		}
		this.jeuCourant = jeuRouge;
		this.message = "";
		this.couloirs = MazeCouloirsFactory.newCouloirs();
		this.extraCorridor = MazeCouloirsFactory.getExtraCorridor();

	}

	/*
	 * Changement de joueur en respectant l'ordre des aiguilles d'une montre:
	 * Rouge - Jaune - Bleu - Vert - Rouge - Jaune - ...
	*/
	public void switchJoueur(){
		if(this.jeuCourant == this.jeuRouge) {
			if(this.jeuJaune != null) {
				this.jeuCourant = this.jeuJaune;
			}
			else if(this.jeuBleu != null){
				this.jeuCourant = this.jeuBleu;
			}
			else if(this.jeuVert != null) {
				this.jeuCourant = this.jeuVert;
			}
		}
		else if(this.jeuCourant == this.jeuJaune) {
			if(this.jeuBleu != null) {
				this.jeuCourant = this.jeuBleu;
			}
			else if(this.jeuVert != null) {
				this.jeuCourant = this.jeuVert;
			}
			else if(this.jeuRouge != null) {
				this.jeuCourant = this.jeuRouge;
			}
		}
		else if(this.jeuCourant == this.jeuBleu) {
			if(this.jeuVert != null) {
				this.jeuCourant = this.jeuVert;
			}
			else if (this.jeuRouge != null) {
				this.jeuCourant = this.jeuRouge;
			}
			else if(this.jeuJaune != null) {
				this.jeuCourant = this.jeuJaune;
			}
		}
		else if(this.jeuCourant == this.jeuVert) {
			if(this.jeuRouge != null) {
				this.jeuCourant = this.jeuRouge;
			}
			else if(this.jeuJaune != null) {
				this.jeuCourant = this.jeuJaune;
			}
			else if(this.jeuBleu != null) {
				this.jeuCourant = this.jeuBleu;
			}
		}
		if(this.jeuCourant.getTreasureToCatch() == null && this.jeuCourant.getScorePlayer() < this.scoreMax){
			//Piocher une carte si le joueur n'a pas atteint le score final
			this.jeuCourant.drawCard(this.treasureToDraw);
		}


		// avant le changement de joueur, modifier le labyrinthe
		this.alterMaze("pushDown", 1);
	}

	/*
	 * Modifie le labyrinthe
	 * @param String commande - Code de la commande a effectuer
	 * 	* "pushDown" -> pousser vers le bas
	 * 	* "pushUp" -> pousser vers le haut
	 * 	* "pushLeft" -> pousser vers la gauche
	 * 	* "pushRight" -> pousser vers la droite
	 *  * Si pas de code correct, return false TODO thrower une erreur
	 * @param int position - indice de la colonne OU ligne a pousser
	 * 	* si commande "pushDown" -> indique une colonne
	 * 	* si commande "pushUp" -> indique une colonne
	 * 	* si commande "pushLeft" -> indique une ligne
	 * 	* si commande "pushRight" -> indique une ligne
	 */
	public boolean alterMaze(String command, int position) {
		boolean commandComplete;

		System.out.println("alterMaze");

		if(position != 1 && position != 3 && position != 5) {
			return false;
		}

		switch(command) {
			case "pushDown" : {
				this.pushDown(position);
				commandComplete = true;
				break;
			}
			case "pushUp" : {
				this.pushUp(position);
				commandComplete = true;
				break;
			}
			case "pushLeft" : {
				this.pushLeft(position);
				commandComplete = true;
				break;
			}
			case "pushRight" : {
				this.pushRight(position);
				commandComplete = true;
				break;
			}
			default : {
				commandComplete = false;
				break;
			}
		}

		return commandComplete;
	}

	private void pushDown(int position) {
		int oldX;
		int oldY;
		boolean oldNorthOpened;
		boolean oldSouthOpened;
		boolean oldEastOpened;
		boolean oldWestOpened;
		Couloirs oldExtra = new CouloirAmovible(
				new Coord(position, 0),
				this.extraCorridor.isNorthOpened(),
				this.extraCorridor.isSouthOpened(),
				this.extraCorridor.isEastOpened(),
				this.extraCorridor.isWestOpened()
		);
		List<Couloirs> couloirsToAdd = new LinkedList<>();
		List<Couloirs> couloirsToRemove = new LinkedList<>();

		for(Couloirs couloir : this.couloirs) {
			// on ne traite que les couloirs sur l'axe sélectionné
			if(couloir.getX() == position) {
				// si dernier couloir on l'éjecte (pour le mettre en pièce supplémentaire)
				if(couloir.getY() == 6) {
					this.extraCorridor = (CouloirAmovible) couloir;
				}
				// sinon on met à jour et on ajoute à la liste de nouveaux couloirs
				else {
					oldX = couloir.getX();
					oldY = couloir.getY();
					oldNorthOpened = couloir.isNorthOpened();
					oldSouthOpened = couloir.isSouthOpened();
					oldEastOpened = couloir.isEastOpened();
					oldWestOpened = couloir.isWestOpened();

					couloirsToAdd.add(new CouloirAmovible(new Coord(oldX, oldY + 1), oldNorthOpened, oldSouthOpened, oldEastOpened, oldWestOpened));
				}
				// dans tous les cas on retire l'ancien couloir
				couloirsToRemove.add(couloir);
			}
		}

		couloirsToAdd.add(oldExtra);

		// on supprime tout la ligne
		this.couloirs.removeAll(couloirsToRemove);
		// pour la rajouter avec les nouvelles valeurs
		this.couloirs.addAll(couloirsToAdd);
	}

	private void pushUp(int position) { }
	private void pushLeft(int position) { }
	private void pushRight(int position) { }

	public void rotateExtraCardLeft() { this.extraCorridor.rotateLeft(); }
	public void rotateExtraCardRight() { this.extraCorridor.rotateRight(); }

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
	public boolean move(int xInit, int yInit, int xFinal, int yFinal) {
		boolean hasMoved = false;
		if(this.jeuCourant != null){
			hasMoved = jeuCourant.move(xInit, yInit, xFinal, yFinal);
			if(hasMoved){
			}
		}
		return hasMoved;
	}

	@Override
	public boolean isEnd() {
		return false;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

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
	public Couleur getPieceColor(int x, int y) {
		return this.jeuCourant.getPieceColor(x,y);
	}


	public List<PieceIHMs> getPiecesIHM(){
		List<PieceIHMs> list1 = new LinkedList<PieceIHMs>();
		List<PieceIHMs>	list2 = new LinkedList<PieceIHMs>();
		List<PieceIHMs>	list3 = new LinkedList<PieceIHMs>();
		List<PieceIHMs>	list4 = new LinkedList<PieceIHMs>();
		List<PieceIHMs> listFinale = new LinkedList<PieceIHMs>();
		if(this.jeuRouge != null){
			list1 = this.jeuRouge.getPiecesIHM();
		}
		if(this.jeuBleu != null){
			list2 = this.jeuBleu.getPiecesIHM();
		}
		if(this.jeuJaune != null){
			list3 = this.jeuJaune.getPiecesIHM();
		}
		if(this.jeuVert != null){
			list4 = this.jeuVert.getPiecesIHM();
		}
		if(list1 != null && listFinale != null){
			listFinale.addAll(list1);
		}
		if(list2 != null && listFinale != null){
			listFinale.addAll(list2);
		}
		if(list3 != null && listFinale != null){
			listFinale.addAll(list3);
		}
		if(list4 != null && listFinale != null){
			listFinale.addAll(list4);
		}
		return listFinale;
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

	public List<TreasureIHMs> getTreasuresIHMs() {
		List<TreasureIHMs> treasureIHMs = new LinkedList<TreasureIHMs>();

		for(Treasures treasure : this.treasures) {
			treasureIHMs.add(new TreasureIHM(treasure));
		}

		return treasureIHMs;
	}

	public List<PieceIHMs> getPiecesIHMs() {
		List<PieceIHMs> pieceIHMs = new LinkedList<>();
		List<PieceIHMs> jeuBleuIHMs = new LinkedList<>();
		List<PieceIHMs> jeuRougeIHMs = new LinkedList<>();
		List<PieceIHMs> jeuJauneIHMs = new LinkedList<>();
		List<PieceIHMs> jeuVertIHMs = new LinkedList<>();

		if(this.jeuBleu != null) {
			jeuBleuIHMs = this.jeuBleu.getPiecesIHM();
		}
		if(this.jeuRouge != null) {
			jeuRougeIHMs = this.jeuRouge.getPiecesIHM();
		}
		if(this.jeuJaune != null) {
			jeuJauneIHMs = this.jeuJaune.getPiecesIHM();
		}
		if(this.jeuVert != null) {
			jeuVertIHMs = this.jeuVert.getPiecesIHM();
		}

		pieceIHMs.addAll(jeuBleuIHMs);
		pieceIHMs.addAll(jeuRougeIHMs);
		pieceIHMs.addAll(jeuJauneIHMs);
		pieceIHMs.addAll(jeuVertIHMs);

		return pieceIHMs;
	}

	public Couleur getJeuCourant(){
		Couleur couleur = null;
		if(this.jeuCourant != null){
			if(this.jeuCourant == jeuBleu){
				couleur = Couleur.BLEU;
			}
			else if(this.jeuCourant == jeuRouge){
				couleur = Couleur.ROUGE;
			}
			else if(this.jeuCourant == jeuJaune) {
				couleur = Couleur.JAUNE;
			}
			else if(this.jeuCourant == jeuVert) {
				couleur = Couleur.VERT;
			}
		}
		return couleur;
	}

	public String toString(){
		String string = "";
		if(jeuRouge != null){
			string += "Jeu blanc : " + jeuRouge.toString();
		}
		if(jeuBleu != null){
			string += "\nJeu noir : " + jeuBleu.toString();
		}
		if(jeuJaune != null){
			string += "\nJeu noir : " + jeuJaune.toString();
		}
		if(jeuVert != null){
			string += "\nJeu noir : " + jeuVert.toString();
		}
		if(jeuCourant != null){
			string += "\nJeu courant : " + this.getJeuCourant();
		}
		return string;
	}

	public List<Coord> findPath(Coord coord){
		Couloirs couloirFound = null;
		List<Couloirs> couloirsFound = new LinkedList<>();
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

	public boolean treasureCatched(Treasure treasureCatched){
		if(this.jeuCourant.addTreasureCatched(treasureCatched)){
			System.out.println("TRESOR VA ETRE SUPPRIMER");
			this.treasures.remove(treasureCatched);
			System.out.println(this.treasures.size());
			return true;

		}else{
			return false;
		}
	}

	public int getCurrentScorePlayer(){
		return this.jeuCourant.getScorePlayer();
	}

	public Coord getCurrentCoordInitiale() {
		return this.jeuCourant.getCoordInitiale();
	}

	public int getScoreMax(){
		return this.scoreMax;
	}

	public String getNamePlayer(){
		return this.jeuCourant.getNamePlayer();
	}
	
	public int getBluePlayerScore() {
		return this.jeuBleu.getScorePlayer();
	}
	
	public int getRedPlayerScore() {
		return this.jeuRouge.getScorePlayer();
	}
	
	public int getYellowPlayerScore() {
		return this.jeuJaune.getScorePlayer();
	}
	
	public int getGreenPlayerScore() {
		return this.jeuVert.getScorePlayer();
	}

	int scoreMax = 0;
	private Jeu jeuBleu;
	private Jeu jeuRouge;
	private Jeu jeuJaune;
	private Jeu jeuVert;
	private Jeu jeuCourant;
	private String message;
	private List<Couloirs> couloirs;
	private CouloirAmovible extraCorridor;
	private List<Treasures> treasures;
	List<Treasures> treasureToDraw;
	//vue/mazegameGUI
	//boucle for couloirs ihm qui permet de poser les images

	// tests
	public static void main(String[] args){
		System.out.println("tests plateau");
		Plateau plateau = new Plateau(2);
		List<Couloirs> couloirs;
		Comparator comp = new Comparator<Couloirs>() {
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


		Collections.sort(plateau.couloirs, comp);
		System.out.println(plateau.couloirs);
		plateau.alterMaze("pushDown", 1);
		Collections.sort(plateau.couloirs, comp);
		System.out.println(plateau.couloirs);

	}
}
