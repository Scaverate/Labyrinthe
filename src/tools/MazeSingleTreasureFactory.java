package tools;



import model.Coord;
import model.Treasure;

/**
 * Classe qui fabrique 1 trésor
 * du nom et aux coordonnées
 * passées en paramètre
 *
 */
public class MazeSingleTreasureFactory {

	/**
	 * private pour ne pas instancier d'objets
	 */
	private MazeSingleTreasureFactory() {}

	/**
	 * @param pieceCouleur
	 * @return liste de pi�ces de jeu d'�chec
	 */
	public static Treasure newTreasure(int x, int y, String nom) {

		Treasure treasure = null;
		
		String className = "model." + nom;	// attention au chemin
		Coord treasureCoord = new Coord(x, y);
		
		treasure = (Treasure) Introspection.newInstance (className,
				new Object[] {treasureCoord});
		return treasure;
	}
}
