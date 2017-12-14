package model;

/**
 * Classe qui permet de retourner des informations sur les pièces
 * en vue d'une utilisation par une IHM
 * 
 * DP Adapter
 * 
 */
public  class TreasureIHM  implements TreasureIHMs {
	 
	Treasures treasure;
	
	public TreasureIHM(Treasures treasure) {
		this.treasure = treasure;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
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
}