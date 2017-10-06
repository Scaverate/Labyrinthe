package model;

public abstract class AbstractPiece implements Pieces {
	private Couleur couleur;
	private int x;
	private int y;
	private String nom;
	
	public AbstractPiece(Couleur couleur, int x, int y, String nom) {
		this.couleur = couleur;
		this.x = x;
		this.y = y;
		this.nom = new String(nom);
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public Couleur getCouleur() {
		return couleur;
	}

	@Override
	public String getName() {
		return nom;
	}

	@Override
	public abstract boolean isMoveOK(int xFinal, int yFinal, boolean isCatchOK,
			boolean isCastlingPossible);

	@Override
	/**
	 * @param int xFinal - the x position
	 * @param int yFinal - the y position
	 * @return boolean - true if the piece has moved
	 */
	public boolean move(int xFinal, int yFinal) {
		boolean moved = false;
		
		if(Coord.coordonnees_valides(xFinal, yFinal)){
			this.x = xFinal;
			this.y = yFinal;
			moved = true;
		}
		return moved;
	}
	
	@Override
	public String toString(){
		return "Piece de type '" + this.getName() + "' en position (" + this.getX() +";"+ this.getY()+ ")";
	}

}
