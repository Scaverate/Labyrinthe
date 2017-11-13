package tools;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
		int nombreAleatoirey = 0;
		int nombreAleatoirex = 0;
		int j =0;
		int flagCouloirFixeTrouve = 0;
		List<Treasures> treasures = null;
		treasures = new LinkedList<Treasures>();
		for (int i = 0; i < MazeTreasurePos.values().length; i++) {
			int test = MazeTreasurePos.values().length;
			//String className = "model." + MazeTreasurePos.values()[i].nom;	// attention au chemin
			flagCouloirFixeTrouve = 0;
			while(flagCouloirFixeTrouve == 0 && j < 49){
				if(MazeCouloirsPos.values()[j].isFixed){
					nombreAleatoirex = MazeCouloirsPos.values()[j].coord.x;
					nombreAleatoirey = MazeCouloirsPos.values()[j].coord.y;
					if((nombreAleatoirex != 0 || nombreAleatoirey != 0) && (nombreAleatoirex != 6 || nombreAleatoirey != 0)
							&& (nombreAleatoirex != 0 || nombreAleatoirey != 6) && (nombreAleatoirex != 6 || nombreAleatoirey != 6)){
						flagCouloirFixeTrouve = 1;
					}
					j++;
				}else{
					j++;
				}
			}
			/*Random randx = new Random(); 
			 = randx.nextInt(6 - 0 + 1) + 0;
			 = 2;
			Random randy = new Random(); 
			int test = nombreAleatoirey % 2;
			while(nombreAleatoirey % 2 == 0){
				randy = new Random();
				nombreAleatoirey = randy.nextInt(6 - 0 + 1) + 0;
			}*/
			Treasure theTreasure = new Treasure(nombreAleatoirex,nombreAleatoirey,MazeTreasurePos.values()[i].nom,false);
			treasures.add(theTreasure);
		}
		return treasures;
	}
}
