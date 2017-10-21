package model;

/**
 * Created by Martin on 10/10/2017.
 */
public class CouloirFixe extends AbstractCouloir {
    public CouloirFixe(Coord coord, boolean northOpened, boolean southOpened, boolean eastOpened, boolean westOpened) {
        super(northOpened, southOpened, eastOpened, westOpened, coord.x, coord.y);
    }
}
