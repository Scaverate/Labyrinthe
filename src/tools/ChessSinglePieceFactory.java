package tools;



import model.Coord;
import model.Couleur;
import model.Pieces;

/**
 * Classe qui fabrique 1 pièce
 * de la couleur, du type et aux coordonnées
 * passées en paramètre
 *
 */
public class ChessSinglePieceFactory {

	/**
	 * private pour ne pas instancier d'objets
	 */
	private ChessSinglePieceFactory() {}

	/**
	 * @param pieceCouleur
	 * @return liste de pi�ces de jeu d'�chec
	 */
	public static Pieces newPiece(Couleur pieceCouleur, String type, int x, int y) {

		Pieces piece = null;
		
		String className = "model." + type;	// attention au chemin
		Coord pieceCoord = new Coord(x, y);
		
		piece = (Pieces) Introspection.newInstance (className,
				new Object[] {pieceCouleur, pieceCoord});
		return piece;
	}
}
