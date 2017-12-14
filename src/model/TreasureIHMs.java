package model;

/**
 * Cette interface permet de réduire l'interface de Pieces  
 * en ne donnant que des getters
 */
public interface TreasureIHMs {
	
	/**
	 * @return indice de la colonne où est positionnée la piece
	 */
	public int getTreasureX();
	
	/**
	 * @return indice de la ligne où est positionnée la piece
	 */
	public int getTreasureY();

	/**
	 * @return le nom de la pièce
	 *
	 */
	public String getTreasureType();
	
	/**
	 * 
	 * @return l'ID du trésor
	 */
	public int getTreasureId();
	
}