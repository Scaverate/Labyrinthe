package controler.controlerLocal;

import model.Coord;
import model.observable.MazeGame;
import controler.AbstractMazeGameControler;

/**
 *         Ce controleur local précise comment empêcher un joueur à qui ce n'est pas le tour 
 *         de jouer, de déplacer une image de pièce sur le damier
 *
 */
public class MazeGameControler extends AbstractMazeGameControler {
	
	public MazeGameControler(MazeGame mazeGame) {
		super(mazeGame);
	}

	/* (non-Javadoc)
	 * @see controler.AbstractMazeGameControler#isPlayerOK(model.Coord)
	 */
	@Override
	public boolean isPlayerOK(Coord initCoord) {
		return this.mazeGame.isPlayerOk(initCoord);
	}
	
	/* (non-Javadoc)
	 * @see controler.AbstractMazeGameControler#endMove(model.Coord, model.Coord, java.lang.String)
	 * 
	 * Pas d'action supplémentaire dans un contrôleur local en fin de move
	 */
	@Override
	protected void endMove(Coord initCoord, Coord finalCoord,
			String promotionType) {
		
	}

	
}
