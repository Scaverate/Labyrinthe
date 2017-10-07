package tools;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import model.Couleur;

/**
 * Cette classe s'appuie sur MazePieceImage
 * pour fournir les noms des images des pièces
 * qui sont utilisées dans l'IHM 
 *  
 */
public class MazeImageProvider {
	
	private static Map<String, String> mapImage;

	static {	
		mapImage = new HashMap<String, String>();
		for (int i = 0; i < MazePieceImage.values().length; i++) {
			mapImage.put(MazePieceImage.values()[i].nom, MazePieceImage.values()[i].imageFile);
		}
	}

	/**
	 * private pour ne pas instancier d'objets
	 */
	private MazeImageProvider() {}
	
	/**
	 * @param pieceType
	 * @param pieceCouleur
	 * @return nom fichier contenant image de la piece
	 */
	public static String getImageFile(String pieceType, Couleur pieceCouleur){
		String ret, key, value;
		ret = null;
		key = pieceType + pieceCouleur.name();
		value = mapImage.get(key);
		File g=new File("");
		ret = g.getAbsolutePath()+"/src/images/" + value;
		return ret;		
	}
}
