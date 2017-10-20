package model;

public abstract class AbstractTreasure implements Treasure {
	private int x;
	private int y;
	private String nom;
	
	public AbstractTreasure(int x, int y, String nom) {
		this.x = x;
		this.y = y;
		this.nom = new String(nom);
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
	
	@Override
	public String toString(){
		return "Piece de type '" + this.getTreasureName() + "' en position (" + this.getTreasureX() +";"+ this.getTreasureY()+ ")";
	}
}
