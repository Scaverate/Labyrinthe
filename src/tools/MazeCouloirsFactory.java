package tools;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import model.Coord;
import model.CouloirFixe;
import model.CouloirAmovible;
import model.Couloirs;

/**
 * Classe qui fabrique une liste de pieces sur le plateau
 */
public class MazeCouloirsFactory {

    /**
     * private pour ne pas instancier d'objets
     */
    private MazeCouloirsFactory() {}
    
    private static int typeI=12;
    private static int typeL=16;
    private static int typeT=6;

    /**
     * @return liste de couloirs
     */
    public static List<Couloirs> newCouloirs() {
        List<Couloirs> couloirs = null;
        Coord couloirCoord;
        Couloirs couloirGenerated;
        boolean isNorthOpened;
        boolean isSouthOpened;
        boolean isEastOpened;
        boolean isWestOpened;
        int typeIdCorridor;
        MazeCouloirsType corridor;

        couloirs = new LinkedList<Couloirs>();

        //Génération des couloirs
        for (int i = 0; i < MazeCouloirsPos.values().length; i++) {
            couloirCoord = MazeCouloirsPos.values()[i].coord;

            if(MazeCouloirsPos.values()[i].isFixed) {
            	isNorthOpened = MazeCouloirsPos.values()[i].isNorthOpened;
                isSouthOpened = MazeCouloirsPos.values()[i].isSouthOpened;
                isEastOpened = MazeCouloirsPos.values()[i].isEastOpened;
                isWestOpened = MazeCouloirsPos.values()[i].isWestOpened;
                
                couloirGenerated = new CouloirFixe(
                    couloirCoord,
                    isNorthOpened,
                    isSouthOpened,
                    isEastOpened,
                    isWestOpened
                );
            }
            else {
            	typeIdCorridor = getTypeIdCorridor();
            	corridor = getRandomCorridorFromType(typeIdCorridor);
            	
            	isNorthOpened = corridor.isNorthOpened;
                isSouthOpened = corridor.isSouthOpened;
                isEastOpened = corridor.isEastOpened;
                isWestOpened = corridor.isWestOpened;
            	
                couloirGenerated = new CouloirAmovible(
                    couloirCoord,
                    isNorthOpened,
                    isSouthOpened,
                    isEastOpened,
                    isWestOpened
                );
            }
            couloirs.add(couloirGenerated);
        }

        // fusion des deux listes
        return couloirs;
    }
    
    private static int getTypeIdCorridor() {
    	List<Integer> listNbAleatoire = null;
    	int randomNumber;
    	
    	listNbAleatoire = new LinkedList<Integer>();
    	
    	if (typeI !=0) {
    		listNbAleatoire.add(1);
    	}
    	if (typeL !=0) {
    		listNbAleatoire.add(2);
    	}
    	if (typeT !=0) {
    		listNbAleatoire.add(3);
    	}
    	
    	Random rand = new Random();
    	
    	randomNumber = rand.nextInt(listNbAleatoire.size());
    	
    	switch (listNbAleatoire.get(randomNumber)) {
    	case 1: 
    		typeI--;
    		break;
    	case 2: 
    		typeL--;
    		break;
    	case 3: 
    		typeT--;
    		break;
		default:
			System.out.println("Erreur sur le type de la piece");
			break;
    	}
    	
    	return listNbAleatoire.get(randomNumber);
    }
    
    private static MazeCouloirsType getRandomCorridorFromType(int typeId) {
    	List<MazeCouloirsType> corridors= new LinkedList<MazeCouloirsType>();
    	
    	int randomCorridor;
    	
    	for (int i=0; i < MazeCouloirsType.values().length; i++) {
    		if(MazeCouloirsType.values()[i].typeId == typeId) {
    			corridors.add(MazeCouloirsType.values()[i]);
    		}
    	}
    	Random rand = new Random();
    	
    	randomCorridor = rand.nextInt(corridors.size());
    	
    	return corridors.get(randomCorridor);
    }
}
