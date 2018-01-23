package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import tools.MazePiecesFactory;
import tools.MazeTreasureFactory;

public class Jeu implements Game {
	private Treasure treasureToCatch;
	private Coord coordInitiale = new Coord(0,0);
	private Couleur jeuCouleur;
	public Jeu(Couleur couleur){
		this.pieces = MazePiecesFactory.newPieces(couleur);
		this.jeuCouleur = couleur;
		this.listTreasureCatched.clear();
		this.treasureToCatch = null;
	}
	
	public Couleur getPieceColor(int x, int y){
		Couleur couleur = null;
		Pieces piece = this.findPiece(x, y);
		if(piece != null){
			couleur = piece.getCouleur();
		}
		return couleur;
	}
	
	public Treasure drawCard(List<Treasures> listTreasure){
		List<Treasure> curatedList = new LinkedList<>();
		Treasure treasureDraw;
		int randomNumber;
		Random rand = new Random();

		for(Treasures treasure : listTreasure) {
			if(treasure.getTreasureX() != -1 && treasure.getTreasureY() != -1) {
				curatedList.add((Treasure) treasure);
			}
		}
		if(curatedList.size() == 0) {
			listTreasure.clear();
			return null;
		}
		randomNumber = rand.nextInt(curatedList.size());
		treasureDraw = curatedList.get(randomNumber);
		this.setTreasureToCatch(treasureDraw);
		listTreasure.remove(treasureDraw);
		return treasureDraw;
	}
	
	private Pieces findPiece(int x, int y){
		Pieces foundPiece = null;
		for(Pieces piece : this.pieces) {
			if(piece.getX() == x && piece.getY() == y){
				foundPiece = piece;
				break;
			}
		}
		return foundPiece;
	}
	
	@Override
	public boolean isPieceHere(int x, int y) {
		boolean piecePresente = false;
		for(Pieces piece : this.pieces){
			if(piece.getX() == x && piece.getY() == y){
				piecePresente = true;
			}
		}
		return piecePresente;
	}

	@Override
	public boolean isMoveOk(int xInit, int yInit, int xFinal, int yFinal,
			boolean isCatchOk, boolean isCastlingPossible) {
		boolean canMove = false;
		Pieces piece = findPiece(xInit, yInit);
		if(piece != null) {
			canMove = piece.isMoveOK(xFinal, yFinal, true, true);
		}
		return canMove;
	}

	@Override
	public boolean move(int xInit, int yInit, int xFinal, int yFinal) {
		Pieces pieceToMove = findPiece(xInit, yInit);
		boolean done = false;
		if(pieceToMove != null) {
			pieceToMove.move(xFinal,yFinal);
			done = true;
		}
		return done;
	}

	public List<PieceIHMs> getPiecesIHM(){
		PieceIHMs newPieceIHM;
		List<PieceIHMs> list = new LinkedList<>();
		
		for(Pieces piece : this.pieces){
			// si la piece est toujours en jeu
			if(piece != null){
				if(piece.getX() != -1) {
					newPieceIHM = new PieceIHM(piece);
					list.add(newPieceIHM);
				}
			}
		}
		return list;
	}
	
	@Override
	public String toString(){
		String string = null;
		if(this.pieces != null){
			string = this.pieces.toString();
		}
		return string;
	}
	
	public Treasure getTreasureToCatch() {
		return treasureToCatch;
	}

	public void setTreasureToCatch(Treasure treasureToCatch) {
		this.treasureToCatch = treasureToCatch;
	}
	
	public boolean addTreasureCatched(Treasure treasureCatched){
		return this.listTreasureCatched.add(treasureCatched);
	}
	
	public List<Treasures> getListTreasureCatched(){
		return this.listTreasureCatched;
	}
	
	public int getScorePlayer(){
		return this.listTreasureCatched.size();
	}
	
	public Coord getCoordInitiale() {
		return this.coordInitiale;
	}

	public void setCoordInit(Coord coordInit) {
		this.coordInitiale = coordInit;
	}

	public String getNamePlayer(){
		Couleur colorPlayer = this.jeuCouleur;
		String name = "PasdeNom";
		if(colorPlayer == Couleur.BLEU){
			name = "Luigi";
		}else if(colorPlayer == Couleur.ROUGE){
			name = "Mario";
		}else if(colorPlayer == Couleur.JAUNE){
			name = "Yoshi";
		}else if(colorPlayer == Couleur.VERT){
			name = "Toad";
		}
		return name;
	}
	
	public Treasure getPrincessToCatch(){
		String name = getNamePlayer();
		int coordPrincessX = this.coordInitiale.x;
		int coordPrincessY = this.coordInitiale.y;
		int id = 0;
		if(name == "Mario"){
			id = 25;
		}else if(name == "Luigi"){
			id = 26;
		}else if(name == "Yoshi"){
			id = 27;
		}else if(name == "Toad"){
			id = 28;
		}else{
			return null;
		}
		Treasure princess = new Treasure(coordPrincessX, coordPrincessY, id, "TreasureFixed", false); 
		return princess;
	}

	// moche mais assumé
	public Coord getCoord() {
		return new Coord(
				this.pieces.get(0).getX(),
				this.pieces.get(0).getY()
		);
	}

	private List<Pieces> pieces;
	private List<Treasures> listTreasureCatched = new LinkedList<>();
}
