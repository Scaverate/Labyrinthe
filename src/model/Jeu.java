package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import tools.MazePiecesFactory;
import tools.MazeTreasureFactory;

public class Jeu implements Game {
	private Treasure treasureToCatch;
	private Coord coordInitiale = new Coord(0,0);
	public Jeu(Couleur couleur){
		this.pieces = MazePiecesFactory.newPieces(couleur);
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
		Treasure treasureDraw;
		int randomNumber = 0;
		Random rand = new Random();
		randomNumber = rand.nextInt(listTreasure.size() - 0 ) + 0;
		treasureDraw = (Treasure) listTreasure.get(randomNumber);
		this.setTreasureToCatch(treasureDraw);
		listTreasure.remove(randomNumber);	
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

	@Override
	public boolean capture(int xCatch, int yCatch) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * @return une version réduite de la liste des pièces en cours
	 * ne donnant que des accès en lecture sur des PieceIHMs
	 * (type piece + couleur + cooredonnées)
	 */
	public List<PieceIHMs> getPiecesIHM(){
		PieceIHMs newPieceIHM = null;
		List<PieceIHMs> list = new LinkedList<>();
		
		for(Pieces piece : this.pieces){
			// si la piece est toujours en jeu
			if(piece != null){
				if(piece.getX() != -1) {
					newPieceIHM = new PieceIHM(piece);
					if(list != null) {
						list.add(newPieceIHM);
					}
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
		return coordInitiale;
	}

	public void setCoordInit(Coord coordInit) {
		this.coordInitiale = coordInit;
	}

	private List<Pieces> pieces;
	private List<Treasures> listTreasureCatched = new LinkedList<Treasures>();
}
