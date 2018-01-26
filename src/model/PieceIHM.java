package model;

import java.io.Serializable;

public class PieceIHM implements PieceIHMs, Serializable {
	public PieceIHM(Pieces piece) {
		this.piece = piece;
	}

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

	private Pieces piece;
}