package controler.controlerOnline;

import controler.controlerLocal.MazeGameControler;
import model.Coord;
import model.observable.MazeGame;

public abstract class MazeGameControlerOnline extends MazeGameControler {

    public MazeGameControlerOnline(MazeGame mazeGame) {
        super(mazeGame);
    }

    @Override
    public boolean isPlayerOK(Coord initCoord) {
        return this.mazeGame.isPlayerOk(initCoord);
    }

    @Override
    protected void endMove(Coord initCoord, Coord finalCoord, String promotionType) { }
}
