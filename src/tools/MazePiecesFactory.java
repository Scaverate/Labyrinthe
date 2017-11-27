package tools;

import java.util.LinkedList;
import java.util.List;

import model.Coord;
import model.Couleur;
import model.Pieces;

/**
 * Classe qui fabrique une liste de pieces 
 * de la couleur passee en parametre
 *
 */
public class MazePiecesFactory {
	
	/**
	 * private pour ne pas instancier d'objets
	 */
	private MazePiecesFactory() {}

	/**
	 * @param pieceCouleur
	 * @return liste de pieces
	 */
	public static List<Pieces> newPieces(Couleur pieceCouleur) {
		List<Pieces> pieces = new LinkedList<>();

		if (pieceCouleur != null) {
			for (int i = 0; i < MazePiecePos.values().length; i++) {
				if (pieceCouleur.equals(MazePiecePos.values()[i].couleur)) {
					for (int j = 0; j < (MazePiecePos.values()[i].coords).length; j++) {
						String className = "model." + MazePiecePos.values()[i].name;	// attention au chemin
						Coord pieceCoord = MazePiecePos.values()[i].coords[j];
						pieces.add((Pieces) Introspection.newInstance (className,
								new Object[] {pieceCouleur, pieceCoord}));
					}
				}
			}
		}
		return pieces;
	}
}
