package model.observable;

import java.util.Observable;
import java.util.Observer;

import model.BoardGames;
import model.Coord;
import model.Couleur;
import model.Plateau;


/**
 * Cette classe est fortement couplée à un Labyrinthe qu'elle crée
 * Elle le rend Observable et en simplifie l'interface
 * (DP Proxy, Facade, Observer)
 *
 */
public class MazeGame extends Observable implements BoardGames{

	private Plateau plateau;

	/**
	 * Cree une instance de la classe Echiquier
	 * et notifie ses observateurs
	 */
	public MazeGame() {
		super();
		this.plateau = new Plateau();
		this.notifyObservers(plateau.getPiecesIHM());
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
			plateau.switchJoueur();
		}
		this.notifyObservers(plateau.getPiecesIHM());
		return ret;	
	}
	
	public boolean isMoveOk (int xInit, int yInit, int xFinal, int yFinal){
		return plateau.isMoveOk(xInit, yInit, xFinal, yFinal);
	}

	public boolean isEnd(){
		return plateau.isEnd();		
	}

	public String getMessage() {
		return plateau.getMessage();
	}
	
	public void setMessage(String message) {
		plateau.setMessage(message);
	}


	public Couleur getColorCurrentPlayer(){		
		return plateau.getColorCurrentPlayer();		
	}

	public Couleur getPieceColor(int x, int y){
		return plateau.getPieceColor(x, y);
	}

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
	}
}
