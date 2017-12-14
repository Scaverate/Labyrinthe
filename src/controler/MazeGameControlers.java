package controler;

import model.*;

import java.util.List;

public interface MazeGameControlers {

	
	/**
	 * @param initCoord
	 * @param finalCoord
	 * @return true si le déplacement s'est bien passé
	 */
	public boolean move(Coord initCoord, Coord finalCoord);
	
	public boolean isMoveOk(int xInit, int yInit, int xFinal, int yFinal);

	/**
	 * @return message relatif aux déplacement, capture, etc.
	 */
	public String getMessage();
	
	/**
	 * @return true si fin de partie OK ()
	 */
	public boolean isEnd();

	/**
	 * @param initCoord
	 * @return une info dont la vue se servira 
	 * pour empêcher tout déplacement sur le damier
	 */
	public boolean isPlayerOK(Coord initCoord);
	
	public Treasure currentTreasureToCatch();
	public void setCurrentTreasureToCatch(Treasure treasureToCatch);
	public void treasureCatchedPlateau(Treasure treasureCatched);
	
	public int getCurrentScorePlayer();
	
	public List<TreasureIHMs> getTreasuresIHMs();
	public List<CouloirIHM> getCouloirsIHMs();
	public List<PieceIHMs> getPiecesIHMs();
	public List<Coord> findPath(Coord coord);

}
