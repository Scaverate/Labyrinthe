package model;

/**
 * Created by Martin on 10/10/2017.
 */
public class AbstractCouloir implements Couloirs{

    public AbstractCouloir(boolean northOpened, boolean southOpened, boolean eastOpened, boolean westOpened, int x, int y){
        this.x = x;
        this.y = y;
        this.northOpened = northOpened;
        this.southOpened = southOpened;
        this.eastOpened = eastOpened;
        this.westOpened = westOpened;
    }

    // getters
    public boolean isNorthOpened(){ return this.northOpened; }
    public boolean isSouthOpened(){ return this.southOpened; }
    public boolean isEastOpened(){ return this.eastOpened; }
    public boolean isWestOpened(){ return this.westOpened; }
    public int getX(){ return this.x; }
    public int getY(){ return this.y; }

    // setters
    public void setNorthOpened(boolean opened){ this.northOpened = opened; }
    public void setSouthOpened(boolean opened){ this.southOpened = opened; }
    public void setEastOpened(boolean opened){ this.eastOpened = opened; }
    public void setWestOpened(boolean opened){ this.westOpened = opened; }

    @Override
    public String toString() {
        return "[" + this.x + "," + this.y + "]";	
    }

    private boolean northOpened = true;
    private boolean southOpened = true;
    private boolean eastOpened = true;
    private boolean westOpened = true;
    private int x = 0;
    private  int y = 0;
}
