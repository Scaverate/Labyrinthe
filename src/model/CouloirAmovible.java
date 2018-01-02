package model;

/**
 * Created by Martin on 10/10/2017.
 */
public class CouloirAmovible extends AbstractCouloir {
    public CouloirAmovible(Coord coord, boolean northOpened, boolean southOpened, boolean eastOpened, boolean westOpened){
        super(northOpened, southOpened, eastOpened, westOpened, coord.x, coord.y);
    }

    /**
     * Rotation d'un couloir vers la gauche
     */
    public void rotateLeft() {
        boolean north = isNorthOpened();
        boolean east = isEastOpened();
        boolean south = isSouthOpened();
        boolean west = isWestOpened();

        // le nord devient l'est
        this.setNorthOpened(east);
        // le sud devient l'ouest
        this.setSouthOpened(west);
        // l' est devient le sud
        this.setEastOpened(south);
        // l' ouest devient le nord
        this.setWestOpened(north);
    }

    /**
     * Rotation d'un couloir vers la droite
     */
    public void rotateRight() {
        boolean north = isNorthOpened();
        boolean east = isEastOpened();
        boolean south = isSouthOpened();
        boolean west = isWestOpened();

        // le nord devient l'ouest
        this.setNorthOpened(west);
        // le sud devient l'est
        this.setSouthOpened(east);
        // l' est devient le nord
        this.setEastOpened(north);
        // l' ouest devient le sud
        this.setWestOpened(south);
    }
}
