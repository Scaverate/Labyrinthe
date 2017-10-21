package tools;

import java.util.LinkedList;
import java.util.List;

import model.Coord;
import model.CouloirFixe;
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
        List<Couloirs> couloirsFixes = null;
        String className;
        Coord couloirCoord;
        boolean isNorthOpened;
        boolean isSouthOpened;
        boolean isEastOpened;
        boolean isWestOpened;

        couloirs = new LinkedList<Couloirs>();
        couloirsFixes = new LinkedList<Couloirs>();

        // génération des couloirs fixes
        for (int i = 0; i < MazeCouloirsPos.values().length; i++) {
            className = "model." + MazeCouloirsPos.values()[i].nom; // attention au chemin
            couloirCoord = MazeCouloirsPos.values()[i].coord;
            isNorthOpened = MazeCouloirsPos.values()[i].isNorthOpened;
            isSouthOpened = MazeCouloirsPos.values()[i].isSouthOpened;
            isEastOpened = MazeCouloirsPos.values()[i].isEastOpened;
            isWestOpened = MazeCouloirsPos.values()[i].isWestOpened;

            couloirsFixes.add(
                new CouloirFixe(
                    couloirCoord,
                    isNorthOpened,
                    isEastOpened,
                    isSouthOpened,
                    isWestOpened
                )
            );
        }

        // génération des couloirs amovibles (aléatoires)

        // fusion des deux listes
        couloirs.addAll(couloirsFixes);
        return couloirs;
    }
}
