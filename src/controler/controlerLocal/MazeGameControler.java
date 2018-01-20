package controler.controlerLocal;

import model.Coord;
import model.observable.MazeGame;
import controler.AbstractMazeGameControler;

public class MazeGameControler extends AbstractMazeGameControler {
	
	public MazeGameControler(MazeGame mazeGame) {
		super(mazeGame);
	}

	@Override
	public boolean isPlayerOK(Coord initCoord) {
		return this.mazeGame.isPlayerOk(initCoord);
	}

	@Override
	protected void endMove(Coord initCoord, Coord finalCoord, String promotionType) { }

}
