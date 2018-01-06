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
		this.extraTreasure = null;

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
		//TODO ajouter le décalage des objets et personnages

		boolean commandComplete;
		List<Couloirs> couloirPushed = new LinkedList<>();
		List<Couloirs> couloirsToRemove = new LinkedList<>();
		List<Treasures> treasuresPushed = new LinkedList<>();
		List<Treasures> treasuresToRemove = new LinkedList<>();

		// si la position n'est pas bonne on arrête tout
		if(position != 1 && position != 3 && position != 5) {
			return false;
		}

		// on retire les anciens couloirs de la colonne ciblée
		for(Couloirs couloir : this.couloirs) {
			if(command.equals("pushDown") || command.equals("pushUp")){
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

		// on retire les anciens tresors de la colonne ciblée
		for(Treasures treasure : this.treasures) {
			if(command.equals("pushDown") || command.equals("pushUp")){
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
		// plus on retire l'extra tresor si besoin
		if(this.extraTreasure != null) {
			this.treasures.remove(this.extraTreasure);
		}

		// en fonction de la commande passe on traite
		switch(command) {
			case "pushDown" : {
				treasuresPushed = this.pushTreasuresDown(position);
				couloirPushed = this.pushCouloirsDown(position);
				commandComplete = true;
				break;
			}
			case "pushUp" : {
				treasuresPushed = this.pushTreasuresUp(position);
				couloirPushed = this.pushCouloirsUp(position);
				commandComplete = true;
				break;
			}
			case "pushLeft" : {
				treasuresPushed = this.pushTreasuresLeft(position);
				couloirPushed = this.pushCouloirsLeft(position);
				commandComplete = true;
				break;
			}
			case "pushRight" : {
				treasuresPushed = this.pushTreasuresRight(position);
				couloirPushed = this.pushCouloirsRight(position);
				commandComplete = true;
				break;
			}
			default : {
				commandComplete = false;
				break;
			}
		}

		// on supprime toute la ligne
		this.couloirs.removeAll(couloirsToRemove);
		// pour la rajouter avec les nouvelles valeurs
		this.couloirs.addAll(couloirPushed);

		//System.out.println(treasuresToRemove.size());
		//System.out.println(treasuresPushed.size());
		// on supprime les anciens trésors
		this.treasures.removeAll(treasuresToRemove);
		// pour la rajouter avec les nouvelles valeurs
		this.treasures.addAll(treasuresPushed);

		return commandComplete;
	}

	private List<Treasures> pushTreasuresDown(int position) {
		List<Treasures> treasuresToAdd = new LinkedList<>();

		// si on a un tresor sur la piece supp
		if(this.extraTreasure != null) {
			Treasure oldExtra = new Treasure(
					position,
					0,
					this.extraTreasure.getTreasureId(),
					this.extraTreasure.getTreasureType(),
					this.extraTreasure.isCatched()
			);
			treasuresToAdd.add(oldExtra);
			this.extraTreasure = null;
		}

		for(Treasures treasure : this.treasures) {
			if(treasure.getTreasureX() == position) {
				// si ejection, on met le tresor sur le couloir qui devient piece supp
				if(treasure.getTreasureY() == 6) {
					this.extraTreasure = (Treasure) treasure;
				}
				else {
					treasuresToAdd.add(
						new Treasure(
							treasure.getTreasureX(),
							treasure.getTreasureY() + 1,
							treasure.getTreasureId(),
							treasure.getTreasureType(),
							treasure.isCatched()
						)
					);
				}
			}
		}
		return treasuresToAdd;
	}
	private List<Couloirs> pushCouloirsDown(int position) {
		List<Couloirs> couloirsToAdd = new LinkedList<>();
		Couloirs oldExtra = new CouloirAmovible(
				new Coord(position, 0),
				this.extraCorridor.isNorthOpened(),
				this.extraCorridor.isSouthOpened(),
				this.extraCorridor.isEastOpened(),
				this.extraCorridor.isWestOpened()
		);
		couloirsToAdd.add(oldExtra);
		for(Couloirs couloir : this.couloirs) {
			// on ne traite que les couloirs sur l'axe sélectionné
			if(couloir.getX() == position) {
				// si dernier couloir on l'éjecte (pour le mettre en pièce supplémentaire)
				if(couloir.getY() == 6) {
					this.extraCorridor = (CouloirAmovible) couloir;
				}
				// sinon on met à jour et on ajoute à la liste de nouveaux couloirs
				else {
					couloirsToAdd.add(
						new CouloirAmovible(
							new Coord(couloir.getX(), couloir.getY() + 1),
							couloir.isNorthOpened(),
							couloir.isSouthOpened(),
							couloir.isEastOpened(),
							couloir.isWestOpened()
						)
					);
				}
			}
		}
		return couloirsToAdd;
	}

	private List<Treasures> pushTreasuresUp(int position) {
		List<Treasures> treasuresToAdd = new LinkedList<>();

		// si on a un tresor sur la piece supp
		if(this.extraTreasure != null) {
			Treasure oldExtra = new Treasure(
					position,
					6,
					this.extraTreasure.getTreasureId(),
					this.extraTreasure.getTreasureType(),
					this.extraTreasure.isCatched()
			);
			treasuresToAdd.add(oldExtra);
			this.extraTreasure = null;
		}

		for(Treasures treasure : this.treasures) {
			if(treasure.getTreasureX() == position) {
				// si ejection, on met le tresor sur le couloir qui devient piece supp
				if(treasure.getTreasureY() == 0) {
					this.extraTreasure = (Treasure) treasure;
				}
				else {
					treasuresToAdd.add(
							new Treasure(
									treasure.getTreasureX(),
									treasure.getTreasureY() - 1,
									treasure.getTreasureId(),
									treasure.getTreasureType(),
									treasure.isCatched()
							)
					);
				}
			}
		}
		return treasuresToAdd;
	}
	private List<Couloirs> pushCouloirsUp(int position) {
		List<Couloirs> couloirsToAdd = new LinkedList<>();
		Couloirs oldExtra = new CouloirAmovible(
				new Coord(position, 6),
				this.extraCorridor.isNorthOpened(),
				this.extraCorridor.isSouthOpened(),
				this.extraCorridor.isEastOpened(),
				this.extraCorridor.isWestOpened()
		);
		couloirsToAdd.add(oldExtra);
		for(Couloirs couloir : this.couloirs) {
			// on ne traite que les couloirs sur l'axe sélectionné
			if(couloir.getX() == position) {
				// si dernier couloir on l'éjecte (pour le mettre en pièce supplémentaire)
				if(couloir.getY() == 0) {
					this.extraCorridor = (CouloirAmovible) couloir;
				}
				// sinon on met à jour et on ajoute à la liste de nouveaux couloirs
				else {
					couloirsToAdd.add(
							new CouloirAmovible(
									new Coord(couloir.getX(), couloir.getY() - 1),
									couloir.isNorthOpened(),
									couloir.isSouthOpened(),
									couloir.isEastOpened(),
									couloir.isWestOpened()
							)
					);
				}
			}
		}
		return couloirsToAdd;
	}

	private List<Treasures> pushTreasuresLeft(int position) {
		List<Treasures> treasuresToAdd = new LinkedList<>();

		// si on a un tresor sur la piece supp
		if(this.extraTreasure != null) {
			Treasure oldExtra = new Treasure(
					6,
					position,
					this.extraTreasure.getTreasureId(),
					this.extraTreasure.getTreasureType(),
					this.extraTreasure.isCatched()
			);
			treasuresToAdd.add(oldExtra);
			this.extraTreasure = null;
		}

		for(Treasures treasure : this.treasures) {
			if(treasure.getTreasureY() == position) {
				// si ejection, on met le tresor sur le couloir qui devient piece supp
				if(treasure.getTreasureX() == 0) {
					this.extraTreasure = (Treasure) treasure;
				}
				else {
					treasuresToAdd.add(
							new Treasure(
									treasure.getTreasureX() - 1,
									treasure.getTreasureY(),
									treasure.getTreasureId(),
									treasure.getTreasureType(),
									treasure.isCatched()
							)
					);
				}
			}
		}
		return treasuresToAdd;
	}
	private List<Couloirs> pushCouloirsLeft(int position) {
		List<Couloirs> couloirsToAdd = new LinkedList<>();
		Couloirs oldExtra = new CouloirAmovible(
				new Coord(6, position),
				this.extraCorridor.isNorthOpened(),
				this.extraCorridor.isSouthOpened(),
				this.extraCorridor.isEastOpened(),
				this.extraCorridor.isWestOpened()
		);
		couloirsToAdd.add(oldExtra);
		for(Couloirs couloir : this.couloirs) {
			// on ne traite que les couloirs sur l'axe sélectionné
			if(couloir.getY() == position) {
				// si dernier couloir on l'éjecte (pour le mettre en pièce supplémentaire)
				if(couloir.getX() == 0) {
					this.extraCorridor = (CouloirAmovible) couloir;
				}
				// sinon on met à jour et on ajoute à la liste de nouveaux couloirs
				else {
					couloirsToAdd.add(
						new CouloirAmovible(
							new Coord(couloir.getX() - 1, couloir.getY()),
							couloir.isNorthOpened(),
							couloir.isSouthOpened(),
							couloir.isEastOpened(),
							couloir.isWestOpened()
						)
					);
				}
			}
		}
		return couloirsToAdd;
	}

	private List<Treasures> pushTreasuresRight(int position) {
		List<Treasures> treasuresToAdd = new LinkedList<>();

		// si on a un tresor sur la piece supp
		if(this.extraTreasure != null) {
			Treasure oldExtra = new Treasure(
					0,
					position,
					this.extraTreasure.getTreasureId(),
					this.extraTreasure.getTreasureType(),
					this.extraTreasure.isCatched()
			);
			treasuresToAdd.add(oldExtra);
			this.extraTreasure = null;
		}

		for(Treasures treasure : this.treasures) {
			if(treasure.getTreasureY() == position) {
				// si ejection, on met le tresor sur le couloir qui devient piece supp
				if(treasure.getTreasureX() == 6) {
					this.extraTreasure = (Treasure) treasure;
				}
				else {
					treasuresToAdd.add(
							new Treasure(
									treasure.getTreasureX() + 1,
									treasure.getTreasureY(),
									treasure.getTreasureId(),
									treasure.getTreasureType(),
									treasure.isCatched()
							)
					);
				}
			}
		}
		return treasuresToAdd;
	}
	private List<Couloirs> pushCouloirsRight(int position) {
		List<Couloirs> couloirsToAdd = new LinkedList<>();
		Couloirs oldExtra = new CouloirAmovible(
				new Coord(0, position),
				this.extraCorridor.isNorthOpened(),
				this.extraCorridor.isSouthOpened(),
				this.extraCorridor.isEastOpened(),
				this.extraCorridor.isWestOpened()
		);
		couloirsToAdd.add(oldExtra);
		for(Couloirs couloir : this.couloirs) {
			// on ne traite que les couloirs sur l'axe sélectionné
			if(couloir.getY() == position) {
				// si dernier couloir on l'éjecte (pour le mettre en pièce supplémentaire)
				if(couloir.getX() == 6) {
					this.extraCorridor = (CouloirAmovible) couloir;
				}
				// sinon on met à jour et on ajoute à la liste de nouveaux couloirs
				else {
					couloirsToAdd.add(
						new CouloirAmovible(
							new Coord(couloir.getX() + 1, couloir.getY()),
							couloir.isNorthOpened(),
							couloir.isSouthOpened(),
							couloir.isEastOpened(),
							couloir.isWestOpened()
						)
					);
				}
			}
		}
		return couloirsToAdd;
	}

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
	public boolean move(Coord initCoord, Coord finalCoord) {
		boolean hasMoved = false;
		if(this.jeuCourant != null){
			hasMoved = jeuCourant.move(initCoord.x, initCoord.y, finalCoord.x, finalCoord.y);
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

	public boolean treasureCatched(Treasure treasureCatched){
		if(this.jeuCourant.addTreasureCatched(treasureCatched)){
			this.treasures.remove(treasureCatched);
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

	private int scoreMax = 0;
	private Jeu jeuBleu;
	private Jeu jeuRouge;
	private Jeu jeuJaune;
	private Jeu jeuVert;
	private Jeu jeuCourant;
	private String message;
	private List<Couloirs> couloirs;
	private CouloirAmovible extraCorridor;
	private Treasure extraTreasure;
	private List<Treasures> treasures;
	private List<Treasures> treasureToDraw;
	//vue/mazegameGUI
	//boucle for couloirs ihm qui permet de poser les images

	// tests
	public static void main(String[] args){
		System.out.println("tests plateau");
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


		Collections.sort(plateau.treasures, compareTreasures);
		System.out.println(plateau.treasures);
		System.out.println(plateau.extraTreasure);
		plateau.alterMaze("pushDown", 1);
		Collections.sort(plateau.treasures, compareTreasures);
		System.out.println(plateau.treasures);
		System.out.println(plateau.extraTreasure);

		System.out.println("\n" + plateau.treasures);
		System.out.println(plateau.extraTreasure);
		plateau.alterMaze("pushDown", 1);
		Collections.sort(plateau.treasures, compareTreasures);
		System.out.println(plateau.treasures);
		System.out.println(plateau.extraTreasure);

		System.out.println("\n" + plateau.treasures);
		System.out.println(plateau.extraTreasure);
		plateau.alterMaze("pushDown", 1);
		Collections.sort(plateau.treasures, compareTreasures);
		System.out.println(plateau.treasures);
		System.out.println(plateau.extraTreasure);

	}
}
