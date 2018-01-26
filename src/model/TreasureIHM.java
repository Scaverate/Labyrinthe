package model;

import java.io.Serializable;

public class TreasureIHM implements TreasureIHMs, Serializable {
	public TreasureIHM(Treasures treasure) {
		this.treasure = treasure;
	}

	@Override
	public String toString() {
		return "TreasureIHM [id=" + getTreasureId() + ", name=" + getTreasureType() + ", x="
				+ getTreasureX() + ", y=" + getTreasureY() + "]";
	}

	@Override
	public int getTreasureX() {
		return treasure.getTreasureX();
	}

	@Override
	public int getTreasureY() {
		return treasure.getTreasureY();
	}

	@Override
	public String getTreasureType() {
		return treasure.getTreasureType();
	}

	@Override
	public int getTreasureId() {
		return treasure.getTreasureId();
	}

	private Treasures treasure;
}