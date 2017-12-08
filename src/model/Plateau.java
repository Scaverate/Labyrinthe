package model;

import java.util.LinkedList;
import java.util.List;

import tools.MazeCouloirsFactory;
import tools.MazeTreasureFactory;

public class Plateau implements BoardGames {
	public Plateau(int nbPlayer) {
		switch (nbPlayer) {
			case 2 :
				jeuRouge = new Jeu(Couleur.ROUGE);
				jeuBleu = new Jeu(Couleur.BLEU);
				break;
			case 3 :
				jeuBleu = new Jeu(Couleur.BLEU);
				jeuRouge = new Jeu(Couleur.ROUGE);
				jeuJaune = new Jeu(Couleur.JAUNE);
				break;
			case 4 :
				jeuBleu = new Jeu(Couleur.BLEU);
				jeuRouge = new Jeu(Couleur.ROUGE);
				jeuVert = new Jeu(Couleur.VERT);
				jeuJaune = new Jeu(Couleur.JAUNE);
				break;
			default:
				System.out.println("La creation des jeux a echoue");
		}
		this.jeuCourant = jeuRouge;
		this.message = new String("");
		this.couloirs = MazeCouloirsFactory.newCouloirs();
		this.treasures = MazeTreasureFactory.newTreasure();
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
	}

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

	private boolean isPieceAnyColor(int coord1, int coord2){
		boolean ret = false;

		/*
		if (jeuBlanc.isPieceHere(coord1, coord2) || jeuNoir.isPieceHere(coord1, coord2)) {
			ret = true;
		}
		*/

		return ret;
	}

	private boolean pieceOnTraject(int xInit, int yInit, int xFinal, int yFinal) {
		boolean pieceOnTraject = false;

		/*
		// Mouvement rectiligne le long de l'axe Y
		if (xInit == xFinal) {

			// si vers Y croissants
			if (yFinal > yInit) {
				for (int i = yInit + 1; i <= yFinal; i++) {
					pieceOnTraject = isPieceAnyColor(xInit,i);
					if(pieceOnTraject){ break; }
				}
			}
			// si vers Y decroissants
			if (yFinal < yInit) {
				for (int i = yInit - 1; i >= yFinal; i--) {
					pieceOnTraject = isPieceAnyColor(xInit,i);
					if(pieceOnTraject){ break; }
				}
			}
		}
		else {
			// Mouvement rectiligne le long de l'axe X
			if (yInit == yFinal) {
				// si vers X croissants
				if (xFinal > xInit) {
					for (int i = xInit + 1; i <= xFinal; i++) {
						pieceOnTraject = isPieceAnyColor(yInit,i);
						if(pieceOnTraject){ break; }
					}
				}
				// si vers X decroissants
				if (xFinal < xInit) {
					for (int i = xInit - 1; i >= xFinal; i--) {
						pieceOnTraject = isPieceAnyColor(yInit,i);
						if(pieceOnTraject){ break; }
					}
				}
			}
			else {
				// Mouvement en diagonale
				if (Math.abs(yInit - yFinal) == Math.abs(xInit - xFinal)) {
					int ecart = Math.abs(yInit - yFinal);

					// X croissant, Y croissant
					if ((xFinal - xInit > 0) && (yFinal - yInit > 0)) {
						for (int i = 1; i <= ecart; i++) {
							pieceOnTraject = isPieceAnyColor(xInit + i,yInit + i);
							if(pieceOnTraject){ break; }
						}
					}
					// X croissant, Y decroissant
					if ((xFinal - xInit > 0) && (yFinal - yInit < 0)) {
						for (int i = 1; i <= ecart; i++) {
							pieceOnTraject = isPieceAnyColor(xInit + i,yInit - i);
							if(pieceOnTraject){ break; }
						}
					}
					// X decroissant, Y decroissant
					if ((xFinal - xInit < 0) && (yFinal - yInit < 0)) {
						for (int i = 1; i <= ecart; i++) {
							pieceOnTraject = isPieceAnyColor(xInit - i,yInit - i);
							if(pieceOnTraject){ break; }
						}
					}

					// X decroissant, Y croissant
					if ((xFinal - xInit < 0) && (yFinal - yInit > 0)) {
						for (int i = 1; i <= ecart; i++) {
							pieceOnTraject = isPieceAnyColor(xInit - i,yInit + i);
							if(pieceOnTraject){ break; }
						}
					}
				}
				else {
					// Dans tous les autres cas de mouvement
					// la piece au coordonnées initiale est un cavalier
					pieceOnTraject = isPieceAnyColor(xFinal,yFinal);
				}
			}
		}
		*/
		return pieceOnTraject;
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

	public void setMessage(String message){
		this.message = message;
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
	
	public List<TreasureIHM> getTreasuresIHMs() {
		List<TreasureIHM> treasureIHMs = new LinkedList<TreasureIHM>();

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
		String string = new String("");
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

	private Jeu jeuBleu;
	private Jeu jeuRouge;
	private Jeu jeuJaune;
	private Jeu jeuVert;
	private Jeu jeuCourant;
	private String message;
	private List<Couloirs> couloirs;
	private List<Treasures> treasures;
	//vue/mazegameGUI
	//boucle for couloirs ihm qui permet de poser les images

	// tests
	public static void main(String[] args){
		System.out.println("tests plateau");
		Plateau plateau = new Plateau(2);

		List<Couloirs> couloirs;
		couloirs = plateau.findPath(new CouloirFixe(new Coord(0, 0), false, true, true, false), plateau.couloirs, new LinkedList<Couloirs>());
		System.out.println(couloirs);
	}
}
