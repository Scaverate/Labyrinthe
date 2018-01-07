package model;


/**
 * 
 * Cette interface défini le comportement attendu 
 * des jeux de plateaux
 *
 */
public interface BoardGames {	

	public boolean move (Coord initCoord, Coord finalCoord);

	/**
	 * @return true si c'est la fin du jeu
	 */
	public boolean isEnd();

	/**
	 * @return un message sur l'état du jeu
	 */
	public String getMessage();

	/**
	 * @return la couleur du jouer courant
	 */
	public Couleur getColorCurrentPlayer();
	
	/**
	 * @param x
	 * @param y
	 * @return la couleur de la pièce sélectionnée
	 */
	public Couleur getPieceColor(int x, int y);
 
}
