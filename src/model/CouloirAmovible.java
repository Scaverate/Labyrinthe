package model;

/**
 * Created by Martin on 10/10/2017.
 */
public class CouloirAmovible extends AbstractCouloir {
    CouloirAmovible(boolean northOpened, boolean southOpened, boolean eastOpened, boolean westOpened, int x, int y){
        super(northOpened, southOpened, eastOpened, westOpened, x, y);
    }

    /**
     * Rotation d'un couloir vers la gauche
     */
    public void tiltLeft() {
        // le nord devient l'est
        this.setNorthOpened(this.isEastOpened());
        // le sud devient l'ouest
        this.setSouthOpened(this.isWestOpened());
        // l' est devient le sud
        this.setEastOpened(this.isSouthOpened());
        // l' ouest devient le nord
        this.setWestOpened(this.isNorthOpened());
    }

    /**
     * Rotation d'un couloir vers la droite
     */
    public void tiltRight() {
        // le nord devient l'ouest
        this.setNorthOpened(this.isWestOpened());
        // le sud devient l'est
        this.setSouthOpened(this.isEastOpened());
        // l' est devient le nord
        this.setEastOpened(this.isNorthOpened());
        // l' ouest devient le sud
        this.setWestOpened(this.isSouthOpened());
    }
}
