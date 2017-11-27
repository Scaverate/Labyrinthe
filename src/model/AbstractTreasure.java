package model;

public abstract class AbstractTreasure implements Treasures {
	private int x;
	private int y;
	private String name;
	private boolean isCatched;
	
	public AbstractTreasure(int x, int y, String name, boolean isCatched) {
		this.x = x;
		this.y = y;
		this.name = new String(name);
		this.isCatched = false;
	}

	@Override
	public int getTreasureX() {
		return x;
	}

	@Override
	public int getTreasureY() {
		return y;
	}

	@Override
	public String getTreasureName() {
		return name;
	}
	
	public boolean isCatched() {
		return isCatched;
	}

	@Override
	public String toString(){
		return "Piece de type '" + this.getTreasureName() + "' en position (" + this.getTreasureX() +";"+ this.getTreasureY()+ ")";
	}
}
