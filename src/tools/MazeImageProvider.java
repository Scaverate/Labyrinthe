package tools;

import java.io.File;
import java.lang.reflect.Array;
import java.util.*;

import model.Couleur;

/**
 * Cette classe s'appuie sur MazePieceImage
 * pour fournir les noms des images des pièces
 * qui sont utilisées dans l'IHM 
 *  
 */
public class MazeImageProvider {
	
	private static Map<String, String> mapImage;
	private static Map<String, String> mapImageCouloirs;
	private static Map<String, String> mapImageTreasures;
	private static Map<String, String> mapImageTresorsCard;

	static {	
		mapImage = new HashMap<String, String>();
		for (int i = 0; i < MazePieceImage.values().length; i++) {
			mapImage.put(MazePieceImage.values()[i].name, MazePieceImage.values()[i].imageFile);
		}

		mapImageCouloirs = new HashMap<String, String>();
		for (int i = 0; i < MazeCouloirImage.values().length; i++) {
			mapImageCouloirs.put(MazeCouloirImage.values()[i].name, MazeCouloirImage.values()[i].imageFile);
		}

		mapImageTreasures = new HashMap<String, String>();
		for (int i = 0; i < MazeTreasureImage.values().length; i++) {
			mapImageTreasures.put(MazeTreasureImage.values()[i].name, MazeTreasureImage.values()[i].imageFile);
		}
		
		mapImageTresorsCard = new HashMap<String, String>();
		for (int i = 0; i < MazeTresorsImage.values().length; i++) {
			mapImageTresorsCard.put(MazeTresorsImage.values()[i].name, MazeTresorsImage.values()[i].imageFile);
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
		String ret, key, value, path;
		ret = null;
		key = pieceType + pieceCouleur.name();
		value = mapImage.get(key);
		File g = new File("");
		path = "/src/images/";
		ret = g.getAbsolutePath()+ path + value;
		return ret;		
	}
	
	public static String getImageCardTresorsFile(String pieceType) {
		String ret, key, value, path; 
		ret = null;
		key = pieceType;
		//System.out.println(key);
		value = mapImageTresorsCard.get(key);
		//System.out.println(value);
		File g = new File("");
		path = "/src/images/tresors/";
		ret = g.getAbsolutePath() + path + value;
		System.out.println(ret);
		return ret;
	}

	/**
	 * @param pieceType
	 * @param northOpened
	 * @param southOpened
	 * @param eastOpened
	 * @param westOpened
	 * @return nom fichier contenant image de la piece
	 */
	public static String getImageFile(String pieceType, boolean northOpened, boolean southOpened, boolean eastOpened, boolean westOpened, boolean isGreyTone){
		String ret, key, value, path;
		ret = null;
		key = pieceType + "_" + (northOpened ? "1" : "0") + "_" + (eastOpened ? "1" : "0") + "_" + (southOpened ? "1" : "0") + "_" + (westOpened ? "1" : "0");
		value = mapImageCouloirs.get(key);
		File g = new File("");
		path = "/src/images/couloirs/" + (isGreyTone ? "greytone/" : "");
		ret = g.getAbsolutePath()+ path + value;
		return ret;
	}
	
	public static String getImageFile(String treasureType){
		String ret, value, path;
		value = mapImageTreasures.get(treasureType);
		File g = new File("");
		path = "/src/images/treasures/";
		ret = g.getAbsolutePath()+path+value;
		return ret;
	}

	/**
	 * @return nom fichier contenant image de la piece
	 */
	/*
	public static String getRandomImageFile(String pieceType){
		String ret, key, value, path;
		File g = new File("");
		Random randomGenerator = new Random();
		int randomInt, keySetSize, mapSize;
		Set<String> keySet;

		mapSize = mapImageCouloirs.size();
		String[] keySetArray = new String[mapSize];

		ret = null;
		keySet = mapImageCouloirs.keySet();
		keySet.toArray(keySetArray);
		keySetSize = keySetArray.length;

		if(pieceType.equals("Couloir")) {
			randomInt = randomGenerator.nextInt(keySetSize);
			key = keySetArray[randomInt];
			value = mapImageCouloirs.get(key);
			path = "/src/images/couloirs/";
		}
		else {
			//TODO reproduire la logique pour les piece de type pion
			path = "";
			value = "";
		}


		ret = g.getAbsolutePath()+ path + value;
		return ret;
	}
	*/
}
