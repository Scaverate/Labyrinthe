package model;

import java.io.Serializable;

public class CouloirIHM implements CouloirIHMs, Serializable {

    public CouloirIHM(Couloirs couloir) {
        this.couloir = couloir;
    }

    public boolean isFixed() { return !(this.couloir instanceof CouloirAmovible); }

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
