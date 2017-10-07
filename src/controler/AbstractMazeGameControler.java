package controler;

import model.Coord;
import model.Couleur;
import model.observable.MazeGame;


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

	/* (non-Javadoc)
	 * @see controler.AbstractMazeGameControler#isPlayerOK(model.Coord)
	 * 
	 * cette méthode vérifie que la couleur de la pièce que l'utilisateur
	 * tente de déplacer est bien celle du jeu courant
	 * la vue se servira de cette information pour empêcher tout déplacement sur le damier
	 */
	public abstract boolean isPlayerOK(Coord initCoord);

	// Déplacement métier
	protected boolean moveModel(Coord initCoord, Coord finalCoord) {	
		return mazeGame.move(initCoord.x, initCoord.y, finalCoord.x, finalCoord.y);	
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

	
	protected Couleur getColorCurrentPlayer(){		
		return this.mazeGame.getColorCurrentPlayer();		
	}	

	protected Couleur getPieceColor(Coord initCoord){		
		return this.mazeGame.getPieceColor(initCoord.x, initCoord.y);		
	}	
	
}
