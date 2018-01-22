package model;

public interface BoardGames {

	public boolean move (Coord initCoord, Coord finalCoord);

	public boolean isEnd();

	public String getMessage();

	public Couleur getColorCurrentPlayer();

	public Couleur getPieceColor(int x, int y);
 
}
