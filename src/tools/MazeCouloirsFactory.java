package tools;

import java.util.LinkedList;
import java.util.List;

import model.Coord;
import model.CouloirFixe;
import model.CouloirAmovible;
import model.Couloirs;

/**
 * Classe qui fabrique une liste de pieces
 * de la couleur passee en parametre
 *
 */
public class MazeCouloirsFactory {

    /**
     * private pour ne pas instancier d'objets
     */
    private MazeCouloirsFactory() {}

    /**
     * @return liste de couloirs
     */
    public static List<Couloirs> newCouloirs() {
        List<Couloirs> couloirs = null;
        String className;
        Coord couloirCoord;
        Couloirs couloirGenerated;
        boolean isNorthOpened;
        boolean isSouthOpened;
        boolean isEastOpened;
        boolean isWestOpened;

        couloirs = new LinkedList<Couloirs>();

        // génération des couloirs
        for (int i = 0; i < MazeCouloirsPos.values().length; i++) {
            //className = "model." + MazeCouloirsPos.values()[i].name;
            couloirCoord = MazeCouloirsPos.values()[i].coord;
            isNorthOpened = MazeCouloirsPos.values()[i].isNorthOpened;
            isSouthOpened = MazeCouloirsPos.values()[i].isSouthOpened;
            isEastOpened = MazeCouloirsPos.values()[i].isEastOpened;
            isWestOpened = MazeCouloirsPos.values()[i].isWestOpened;

            if(MazeCouloirsPos.values()[i].isFixed) {
                couloirGenerated = new CouloirFixe(
                    couloirCoord,
                    isNorthOpened,
                    isSouthOpened,
                    isEastOpened,
                    isWestOpened
                );
            }
            else {
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
}
