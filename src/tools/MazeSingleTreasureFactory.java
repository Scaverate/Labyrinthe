package tools;



import model.Coord;
import model.Treasures;

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
	public static Treasures newTreasure(int x, int y, String name) {

		Treasures treasure = null;
		
		String className = "model." + name;	// attention au chemin
		Coord treasureCoord = new Coord(x, y);
		
		treasure = (Treasures) Introspection.newInstance (className,
				new Object[] {treasureCoord});
		return treasure;
	}
}
