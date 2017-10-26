package model;

public abstract class AbstractTreasure implements Treasures {
	private int x;
	private int y;
	private String nom;
	private boolean catchOk;
	
	public AbstractTreasure(int x, int y, String nom, boolean catchOk) {
		this.x = x;
		this.y = y;
		this.nom = new String(nom);
		this.catchOk = false;
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
		return nom;
	}
	
	public boolean isCatchOk() {
		return catchOk;
	}

	@Override
	public String toString(){
		return "Piece de type '" + this.getTreasureName() + "' en position (" + this.getTreasureX() +";"+ this.getTreasureY()+ ")";
	}
}
