package model.observable;

import java.io.Serializable;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import model.*;

public class MazeGame extends Observable implements BoardGames, Serializable{

	private Plateau plateau;

	public MazeGame(int nbPlayer) {
		super();
		this.plateau = new Plateau(nbPlayer);
		this.notifyObservers(plateau.getPiecesIHM());
		this.notifyObservers(plateau.getTreasuresIHMs());
	}

	@Override
	public String toString() {
		String st = "";
		st += "\n" + plateau.getMessage() + "\n";
		st = plateau.toString();
		return  st;
	}

	public boolean isPlayerOk(Coord initCoord) {
		boolean ret = false;
		Couleur current = this.plateau.getColorCurrentPlayer();
		Couleur pieceColor = this.plateau.getPieceColor(initCoord.x, initCoord.y);
		
		if(current == pieceColor){
			ret = true;
		}
		return ret;
	}

	public boolean move (Coord initCoord, Coord finalCoord){
		boolean ret = false;
		if( initCoord != null && finalCoord != null){
			ret = plateau.isMoveOk(initCoord.x, initCoord.y, finalCoord.x, finalCoord.y);
			if (ret){
				ret = plateau.move(initCoord, finalCoord);
			}
		}
		this.notifyObservers(plateau.getPiecesIHM());
		this.notifyObservers(plateau.getCouloirsIHMs());
		return ret;
	}
	
	public boolean isMoveOk (int xInit, int yInit, int xFinal, int yFinal){
		return plateau.isMoveOk(xInit, yInit, xFinal, yFinal);
	}

	public boolean isEnd(){
		return this.plateau.isEnd();
	}

	public String getMessage() {
		return this.plateau.getMessage();
	}

	public Couleur getColorCurrentPlayer(){		
		return this.plateau.getColorCurrentPlayer();
	}

	public Couleur getPieceColor(int x, int y){
		return this.plateau.getPieceColor(x, y);
	}
	
	public List<TreasureIHMs> getTreasureIHMs () { return this.plateau.getTreasuresIHMs(); }
	public List<CouloirIHM> getCouloirIHMs () { return this.plateau.getCouloirsIHMs(); }
	public CouloirIHM getExtraCorridorIHM() { return this.plateau.getExtraCorridorIHM(); }
	public TreasureIHM getExtraTreasureIHM() { return this.plateau.getExtraTreasureIHM(); }
	public List<PieceIHMs> getPiecesIHM() { return this.plateau.getPiecesIHM(); }
	public List<Coord> findPath(Coord coord) { return this.plateau.findPath(coord); }
	public void rotateExtraCorridor() {
		this.plateau.rotateExtraCorridor();
		this.notifyObservers(plateau.getExtraCorridorIHM());
	}

	@Override
	public void	notifyObservers(Object arg) {
		super.setChanged();
		super.notifyObservers(arg); 
	}

	@Override
	public void addObserver(Observer o){
		super.addObserver(o);
		this.notifyObservers(plateau.getPiecesIHM()); 
		this.notifyObservers(plateau.getTreasuresIHMs()); 
	}
	
	public Treasure currentTreasureToCatch(){
		Treasure treasureToCatch = null;
		treasureToCatch = this.plateau.currentTreasureToCatch();
		return treasureToCatch;
	}
	
	public void setCurrentTreasureToCatch(Treasure treasureToCatch){
		this.plateau.setCurrentTreasureToCatch(treasureToCatch);
	}
	
	public boolean treasureCatchedPlateau(Treasure treasureCatched){
		boolean treasureHasBeenCatched = this.plateau.treasureCatched(treasureCatched);
		this.notifyObservers(plateau.getTreasuresIHMs()); 
		return treasureHasBeenCatched;
	}
	
	public int getCurrentScorePlayer(){
		return this.plateau.getCurrentScorePlayer();
	}

	public int getScoreMax(){
		return this.plateau.getScoreMax();
	}

	public Coord getCurrentCoordInitiale() {
		return this.plateau.getCurrentCoordInitiale();
	}

	public String getCurrentNamePlayer(){
		return this.plateau.getNamePlayer();
	}

	public int getBluePlayerScore() {
		return this.plateau.getBluePlayerScore();
	}
	
	public int getRedPlayerScore() {
		return this.plateau.getRedPlayerScore();
	}
	
	public int getYellowPlayerScore() {
		return this.plateau.getYellowPlayerScore();
	}
	
	public int getGreenPlayerScore() {
		return this.plateau.getGreenPlayerScore();
	}
	
	public void switchPlayer() { this.plateau.switchPlayer(); }

	public boolean alterMaze(int position, String direction){
		boolean commandComplete;
		commandComplete = this.plateau.alterMaze(position, direction);
		this.notifyObservers(plateau.getCouloirsIHMs());
		this.notifyObservers(plateau.getPiecesIHM());
		this.notifyObservers(plateau.getTreasuresIHMs());
		this.notifyObservers(plateau.getExtraCorridorIHM());
		return commandComplete;
	}
}
