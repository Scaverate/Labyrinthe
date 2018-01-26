package tools;

import java.io.File;
import java.lang.reflect.Array;
import java.util.*;

import model.Couleur;

public class MazeImageProvider {
	
	private static Map<String, String> mapImage;
	private static Map<String, String> mapImageCouloirs;
	private static Map<Integer, String> mapImageTreasures;
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

		mapImageTreasures = new HashMap<Integer, String>();
		for (int i = 0; i < MazeTreasureImage.values().length; i++) {
			mapImageTreasures.put(MazeTreasureImage.values()[i].id, MazeTreasureImage.values()[i].imageFile);
		}
		
		mapImageTresorsCard = new HashMap<String, String>();
		for (int i = 0; i < MazeTresorsImage.values().length; i++) {
			mapImageTresorsCard.put(MazeTresorsImage.values()[i].name, MazeTresorsImage.values()[i].imageFile);
		}
	}

	private MazeImageProvider() {}

	public static String getImageFile(String pieceType, Couleur pieceCouleur, boolean isGreyTone){
		String ret, key, value, path;
		ret = null;
		key = pieceType + pieceCouleur.name();
		value = mapImage.get(key);
		File g = new File("");
		path = "/images/" + (isGreyTone ? "greytone/" : "");
		ret = path + value;
		return ret;		
	}
	
	public static String getImageCardTresorsFile(String pieceType, String theme) {
		String ret, key, value, path; 
		ret = null;
		key = pieceType;
		value = mapImageTresorsCard.get(key);
		File g = new File("");
		path = "/images/theme/" + theme + "/";
		ret = path + value;
		return ret;
	}

	public static String getImageFile(String pieceType, String theme, boolean northOpened, boolean southOpened, boolean eastOpened, boolean westOpened, boolean isGreyTone){
		String ret, key, value, path;
		ret = null;
		key = pieceType + "_" + (northOpened ? "1" : "0") + "_" + (eastOpened ? "1" : "0") + "_" + (southOpened ? "1" : "0") + "_" + (westOpened ? "1" : "0");
		value = mapImageCouloirs.get(key);
		File g = new File("");
		path = "/images/theme/" + theme + "/" + (isGreyTone ? "greytone/" : "");
		ret = path + value;
		return ret;
	}
	
	public static String getImageFile(int treasureID){
		String ret, value = "", path;
		if(mapImageTreasures.get(treasureID) != null){
			value = mapImageTreasures.get(treasureID);
		//Si image de princess pour carte
		}else if(treasureID > 28){
			value = "treasure_" + Integer.toString(treasureID) + ".png";
		}
		File g = new File("");
		path = "/images/treasures/";
		ret = path+value;
		return ret;
	}
}
