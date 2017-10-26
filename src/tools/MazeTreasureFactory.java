package tools;

import java.util.LinkedList;
import java.util.List;

import model.Coord;
import model.Treasure;
import model.Treasures;

/**
 * Classe qui fabrique une liste de tresors 
 * de la couleur passee en parametre
 *
 */
public class MazeTreasureFactory {
	
	/**
	 * private pour ne pas instancier d'objets
	 */
	private MazeTreasureFactory() {}

	/**
	 * @param pieceCouleur
	 * @return liste de pieces
	 */
	public static List<Treasures> newTreasure(){

		List<Treasures> treasures = null;
		treasures = new LinkedList<Treasures>();
		for (int i = 0; i < MazeTreasurePos.values().length; i++) {
			for (int j = 0; j < (MazePiecePos.values()[i].coords).length; j++) {
				//String className = "model." + MazeTreasurePos.values()[i].nom;	// attention au chemin
				Coord pieceCoord = MazeTreasurePos.values()[i].coords[j];
				Treasure theTreasure = new Treasure(pieceCoord.x,pieceCoord.y,MazeTreasurePos.values()[i].nom,false);
				treasures.add(theTreasure);
			}
		}
		return treasures;
	}
}
