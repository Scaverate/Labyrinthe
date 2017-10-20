package model;

import model.Pieces;

/**
 * Classe qui permet de retourner des informations sur les pièces
 * en vue d'une utilisation par une IHM
 * 
 * DP Adapter
 * 
 */
public  class TreasureIHM  implements TreasureIHMs {
	 
	Treasure treasure;
	
	public TreasureIHM(Treasure treasure) {
		this.treasure = treasure;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TreasureIHM [name=" + getTreasureName() + ", x="
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
	public String getTreasureName() {
		return treasure.getTreasureName();
	}
}