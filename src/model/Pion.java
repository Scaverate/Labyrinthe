package model;

public class Pion extends AbstractPiece {
	
	public Pion(Couleur couleur, Coord coord) {
		super(couleur, coord.x, coord.y, "Pion");
	}

	@Override
	public boolean isMoveOK(int xFinal, int yFinal, boolean isCatchOK, boolean isCastlingPossible) {
		boolean canMove = true;
		return canMove;
	}
	
	@Override
	public boolean move(int xFinal, int yFinal) {
		boolean canMove = super.move(xFinal,yFinal);
		
		return canMove;
	}
}
