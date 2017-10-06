package model;

import model.Pieces;

/**
 * Classe qui permet de retourner des informations sur les pièces
 * en vue d'une utilisation par une IHM
 * 
 * DP Adapter
 * 
 */
public  class PieceIHM  implements PieceIHMs {
	 
	Pieces piece;
	
	public PieceIHM(Pieces piece) {
		this.piece = piece;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PieceIHM [name=" + getNamePiece() + ", couleur=" + getCouleur() + ", x="
				+ getX() + ", y=" + getY() + "]";
	}

	@Override
	public int getX() {
		return piece.getX();
	}

	@Override
	public int getY() {
		return piece.getY();
	}

	@Override
	public String getNamePiece() {
		return piece.getName();
	}

	@Override
	public Couleur getCouleur() {
		return piece.getCouleur();
	}
}