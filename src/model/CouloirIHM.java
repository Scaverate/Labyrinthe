package model;

/**
 * Created by Martin on 10/10/2017.
 */
public class CouloirIHM implements CouloirIHMs {

    public CouloirIHM(Couloirs couloir) {
        this.couloir = couloir;
    }

    public boolean isFixed() {
        boolean isFixed = false;
        if(this.couloir instanceof CouloirAmovible) {
            isFixed = false;
        }
        else {
            isFixed = true;
        }

        return isFixed;
    }

    public int getX() { return this.couloir.getX(); }
    public int getY() {
        return this.couloir.getY();
    }
    public boolean isNorthOpened() {return this.couloir.isNorthOpened(); }
    public boolean isSouthOpened() {return this.couloir.isSouthOpened(); }
    public boolean isEastOpened() {return this.couloir.isEastOpened(); }
    public boolean isWestOpened() {return this.couloir.isWestOpened(); }

    private Couloirs couloir;
}
