package tools;

import java.util.LinkedList;
import java.util.List;

import model.Coord;
import model.Couleur;
import model.Pieces;
import model.Treasure;

/**
 * Classe qui fabrique une liste de pieces 
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
	public static List<Treasure> newTreasure(String nom){

		List<Treasure> treasures = null;
		treasures = new LinkedList<Treasure>();

		if (nom != ""){
			for (int i = 0; i < MazeTreasurePos.values().length; i++) {
				String test = MazeTreasurePos.values()[i].nom;
				if (nom.equals(MazeTreasurePos.values()[i].nom)) {
					for (int j = 0; j < (MazePiecePos.values()[i].coords).length; j++) {
						String className = "model." + MazeTreasurePos.values()[i].nom;	// attention au chemin
						Coord pieceCoord = MazeTreasurePos.values()[i].coords[j];
						treasures.add((Treasure) Introspection.newInstance (className,
								new Object[] {nom, pieceCoord}));
					}
				}
			}
		}
		return treasures;
	}
}
