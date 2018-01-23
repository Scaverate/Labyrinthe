package model;

/**
 * Created by Martin on 10/10/2017.
 */
public class CouloirAmovible extends AbstractCouloir {
    public CouloirAmovible(Coord coord, boolean northOpened, boolean southOpened, boolean eastOpened, boolean westOpened){
        super(northOpened, southOpened, eastOpened, westOpened, coord.x, coord.y);
    }

    /**
     * Rotation d'un couloir dans le sens horaire
     */
    public void rotate() {
        boolean north = isNorthOpened();
        boolean east = isEastOpened();
        boolean south = isSouthOpened();
        boolean west = isWestOpened();

        this.setNorthOpened(west); // le nord devient l'ouest
        this.setSouthOpened(east); // le sud devient l'est
        this.setEastOpened(north); // l' est devient le nord
        this.setWestOpened(south); // l' ouest devient le sud
    }
}
