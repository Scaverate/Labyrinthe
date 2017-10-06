package model;

public interface Pieces {
	public int getX();
	public int getY();
	public Couleur getCouleur();
	public String getName();
	public boolean isMoveOK(int xFinal, int yFinal, boolean isCatchOK, boolean isCastlingPossible);
	public boolean move(int xFinal, int yFinal);
}
