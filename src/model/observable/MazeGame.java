package model.observable;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import model.*;


/**
 * Cette classe est fortement couplée à un Labyrinthe qu'elle crée
 * Elle le rend Observable et en simplifie l'interface
 * (DP Proxy, Facade, Observer)
 *
 */
public class MazeGame extends Observable implements BoardGames{

	private Plateau plateau;
	private int nbPlayer;
	/**
	 * Cree une instance de la classe Echiquier
	 * et notifie ses observateurs
	 */
	public MazeGame(int nbPlayer) {
		super();
		this.plateau = new Plateau(nbPlayer);
		this.notifyObservers(plateau.getPiecesIHM());
		this.notifyObservers(plateau.getTreasuresIHMs());
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
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

	/**
	 * Permet de deplacer une piece connaissant ses coordonnees initiales vers ses
	 * coordonnees finales si le deplacement est "legal". 
	 * Si deplacement OK, permet l'alternance des joueurs.
	 * @param xInit
	 * @param yInit
	 * @param xFinal
	 * @param yFinal
	 * @return OK si deplacement OK
	 * si OK, permet l'alternance des joueurs
	 */
	public boolean move (int xInit, int yInit, int xFinal, int yFinal){
		boolean ret = false;
		ret = plateau.isMoveOk(xInit, yInit, xFinal, yFinal);
		if (ret){
			ret = plateau.move(xInit, yInit, xFinal, yFinal);
		}
		this.notifyObservers(plateau.getPiecesIHM());
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
	
	public void setMessage(String message) {
		this.plateau.setMessage(message);
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
	public List<PieceIHMs> getPiecesIHMs() { return this.plateau.getPiecesIHMs(); }
	public List<Coord> findPath(Coord coord) { return this.plateau.findPath(coord); }

	/* (non-Javadoc)
	 * @see java.util.Observable#notifyObservers(java.lang.Object)
	 */
	@Override
	public void	notifyObservers(Object arg) {
		super.setChanged();
		super.notifyObservers(arg); 
	}

	/* (non-Javadoc)
	 * @see java.util.Observable#addObserver(java.util.Observer)
	 */
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
		boolean test = this.plateau.treasureCatched(treasureCatched);
		System.out.println("tresor a ete recupere : " + test);
		System.out.println(treasureCatched);
		this.notifyObservers(plateau.getTreasuresIHMs()); 
		return test;
	}
	
	public int getCurrentScorePlayer(){
		return this.plateau.getCurrentScorePlayer();
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
	
	public void switchJoueur() {
		this.plateau.switchJoueur();
	}
}
