package model;

/**
 * Created by Martin on 10/10/2017.
 */
public interface Couloirs {
    public int getX();
    public int getY();
    public boolean isNorthOpened();
    public boolean isSouthOpened();
    public boolean isEastOpened();
    public boolean isWestOpened();
}
