package model;

/**
 * Created by Martin on 10/10/2017.
 */
public class CouloirIHM implements CouloirIHMs {

    public CouloirIHM(Couloirs couloir) {
        this.couloir = couloir;
    }

    public int getX() {
        return this.couloir.getX();
    }
    public int getY() {
        return this.couloir.getY();
    }

    private Couloirs couloir;
}
