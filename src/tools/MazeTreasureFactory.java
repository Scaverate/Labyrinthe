package tools;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import model.Coord;
import model.Treasure;
import model.Treasures;

/**
 * Classe qui fabrique une liste de tresors de la couleur passee en parametre
 *
 */
public class MazeTreasureFactory {

	/**
	 * private pour ne pas instancier d'objets
	 */
	private MazeTreasureFactory() {
	}

	/**
	 * @param pieceCouleur
	 * @return liste de pieces
	 */
	public static List<Treasures> newTreasure() {
		List<Treasures> treasures = null;
		treasures = new LinkedList<Treasures>();
		List<Coord> listCoord = null;
		listCoord = new LinkedList<Coord>();
		int randomNumber = 0;
		
		//Récupération dans la liste les couloirs amovibles
		for(int j = 0; j < 48; j++){
			if(!(MazeCouloirsPos.values()[j].isFixed)){
				listCoord.add(MazeCouloirsPos.values()[j].coord);
			}
		}
		
		//Si objets fixe, on recupere les coordonnees ecrites dans MazeTreasurePos
		//Sinon, on recupere une piece amovible aleatoirement parmis la liste
		for(int i = 0; i < MazeTreasurePos.values().length; i++){
			if(MazeTreasurePos.values()[i].name == "TreasureFixed"){
				//Creation d'un tresor a une position fixe
				Treasure theTreasure = new Treasure(MazeTreasurePos.values()[i].coord.x,
						MazeTreasurePos.values()[i].coord.y, MazeTreasurePos.values()[i].name, false);
				treasures.add(theTreasure);
			}else{
				Random rand = new Random();
				//Recuperation nombre aleatoire entre 0 et nombre de coordonnees valides restantes
				randomNumber = rand.nextInt(listCoord.size() - 0 ) + 0;
				//Creation d'un tresor a une position aleatoire
				Treasure theTreasure = new Treasure(listCoord.get(randomNumber).x,listCoord.get(randomNumber).y,
						 			   MazeTreasurePos.values()[i].name, false);
				treasures.add(theTreasure);
				listCoord.remove(randomNumber);
			}
		}
		return treasures;
	}
}
