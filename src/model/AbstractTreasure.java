package model;

import java.io.Serializable;

public abstract class AbstractTreasure implements Treasures, Serializable {
	private int x;
	private int y;
	private String type;
	private int id;
	private boolean isCatched;
	
	public AbstractTreasure(int x, int y, int id, String type, boolean isCatched) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.type = new String(type);
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
	public String getTreasureType() {
		return type;
	}
	
	public int getTreasureId() {
		return id;
	}
	
	public boolean isCatched() {
		return isCatched;
	}

	@Override
	public String toString(){
		String description, type;

		type = this.getTreasureType().equals("TreasureFixed") ? "F" : "M";

		description = "[" + type + "," + this.getTreasureId() + ",(" + this.getTreasureX() + ";" + this.getTreasureY() + ")]";

		return description;
	}
}
