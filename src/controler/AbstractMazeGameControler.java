package controler;

import java.util.List;

import model.*;
import model.observable.MazeGame;
import model.Treasure;


/**
 * 
 * le controleur illustre le DP Strategy vis-à-vis de la Vue
 * 
 * Méthodes communes des controleurs
 * dont le travail essentiel est de faire communiquer
 * la vue et le modèle pour gérer le déplacement des pièces
 * déplacement figé à ce niveau de la hiérarchie dans un Template Method 
 * les petites lignes étant implémentées dans les classes dérivées
 *
 */
public abstract class AbstractMazeGameControler implements MazeGameControlers {

	protected MazeGame mazeGame;	 

	public AbstractMazeGameControler(MazeGame mazeGame) {
		super();
		this.mazeGame = mazeGame;	 
	}

	/* (non-Javadoc)
	 * @see controler.MazeGameControlers#move(model.Coord, model.Coord)
	 * 
	 * Cette méthode illustre le DP "Template Method" 
	 * avec une partie commune implémentée dans cette classe
	 * et une partie variable implémentée dans les classes dérivées
	 */

	public boolean isMoveOk (int xInit, int yInit, int xFinal, int yFinal){
		return mazeGame.isMoveOk(xInit, yInit, xFinal, yFinal);
	}

	final public boolean move(Coord initCoord, Coord finalCoord) {
		boolean ret = false;
		String promotionType = null;
		
		ret = this.moveModel(initCoord, finalCoord);
		if (ret) {
			this.endMove(initCoord, finalCoord, promotionType);
		}
		return ret;
	}

	public abstract boolean isPlayerOK(Coord initCoord);

	// Déplacement métier
	protected boolean moveModel(Coord initCoord, Coord finalCoord) {
		return mazeGame.move(initCoord, finalCoord);
	}

	protected abstract void endMove(Coord initCoord, Coord finalCoord, String promotionType);

	public boolean isEnd() {
		return this.mazeGame.isEnd();		
	}

	public String getMessage() {
		String ret = "";
		if(this.mazeGame != null){
			ret = this.mazeGame.getMessage();	 
		}
		return ret;
	}

	public String toString() {
		return this.mazeGame.toString();
	}
	public Couleur getColorCurrentPlayer(){		
		return this.mazeGame.getColorCurrentPlayer();		
	}

	protected Couleur getPieceColor(Coord initCoord){		
		return this.mazeGame.getPieceColor(initCoord.x, initCoord.y);		
	}
	
	public Treasure currentTreasureToCatch(){
		Treasure treasureToCatch = null;
		treasureToCatch = this.mazeGame.currentTreasureToCatch();
		return treasureToCatch;
	}
	
	public void setCurrentTreasureToCatch(Treasure treasureToCatch){
		this.mazeGame.setCurrentTreasureToCatch(treasureToCatch);
	}
	
	public int getCurrentScorePlayer(){
		return this.mazeGame.getCurrentScorePlayer();
	}
	
	public int getBluePlayerScore() {
		return this.mazeGame.getBluePlayerScore();
	}
	
	public int getRedPlayerScore() {
		return this.mazeGame.getRedPlayerScore();
	}
	
	public int getYellowPlayerScore() {
		return this.mazeGame.getYellowPlayerScore();
	}
	
	public int getGreenPlayerScore() {
		return this.mazeGame.getGreenPlayerScore();
	}
	
	public void treasureCatchedPlateau(Treasure treasureCatched){
		this.mazeGame.treasureCatchedPlateau(treasureCatched);
	}

	public int getScoreMax(){
		return this.mazeGame.getScoreMax();
	}

	public Coord getCurrentCoordInitiale() {
		return this.mazeGame.getCurrentCoordInitiale();
	}

	public String getCurrentNamePlayer(){
		return this.mazeGame.getCurrentNamePlayer();
	}
	
	public void switchJoueur() {
		this.mazeGame.switchJoueur();
	}
	
	public List<TreasureIHMs> getTreasuresIHMs() { return this.mazeGame.getTreasureIHMs(); }
	public List<CouloirIHM> getCouloirsIHMs() { return this.mazeGame.getCouloirIHMs(); }
	public CouloirIHM getExtraCorridorIHM() { return this.mazeGame.getExtraCorridorIHM(); }
	public TreasureIHM getExtraTreasureIHM() { return this.mazeGame.getExtraTreasureIHM(); }
	public List<PieceIHMs> getPiecesIHM() { return this.mazeGame.getPiecesIHM(); }
	public List<Coord> findPath(Coord coord) { return this.mazeGame.findPath(coord); }
	public void rotateExtraCardLeft() { this.mazeGame.rotateExtraCardLeft(); }
	public void rotateExtraCardRight() { this.mazeGame.rotateExtraCardRight(); }
	public boolean alterMaze(String command, int position){ return this.mazeGame.alterMaze(command, position); }
}
