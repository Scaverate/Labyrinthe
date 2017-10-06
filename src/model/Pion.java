package model;

public class Pion extends AbstractPiece {
	
	public Pion(Couleur couleur, Coord coord) {
		super(couleur, coord.x, coord.y, "Pion");
	}
	
	public boolean getFirstMoveDone(){
		return this.firstMoveDone;
	}
	
	public void setFirstMoveDone(boolean done){
		this.firstMoveDone = done;
	}

	@Override
	public boolean isMoveOK(int xFinal, int yFinal, boolean isCatchOK, boolean isCastlingPossible) {
		boolean canMove = false;
		int delta = yFinal - this.getY();
		int moveAllowed = 1;
		if(xFinal == this.getX()) {
			if(!this.getFirstMoveDone()){
				moveAllowed = 2;
			}
			if(Math.abs(delta) <= moveAllowed) {
				if(this.getCouleur() == Couleur.BLANC) {
					if(delta < 0){
						canMove = true;
					}
				}
				else {
					if(delta > 0) {
						canMove = true;
					}
				}
			}
		}
		return canMove;
	}
	
	@Override
	public boolean move(int xFinal, int yFinal) {
		boolean canMove = super.move(xFinal,yFinal);
		if(canMove){
			if(!this.getFirstMoveDone()){
				this.setFirstMoveDone(true);
			}
		}
		return canMove;
	}
	
	private boolean firstMoveDone = false;
}
