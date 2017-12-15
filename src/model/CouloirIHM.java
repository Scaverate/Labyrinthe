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

    public void rotateLeft() {
        if (this.couloir instanceof CouloirAmovible) {
            ((CouloirAmovible) this.couloir).rotateLeft();
            System.out.println(isNorthOpened());
            System.out.println(isEastOpened());
            System.out.println(isSouthOpened());
            System.out.println(isWestOpened());
        }
    }

    public void rotateRight() {
        if (this.couloir instanceof CouloirAmovible) {
            ((CouloirAmovible) this.couloir).rotateRight();
            System.out.println(isNorthOpened());
            System.out.println(isEastOpened());
            System.out.println(isSouthOpened());
            System.out.println(isWestOpened());
        }
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
