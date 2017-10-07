package vue;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import model.Coord;
import model.Couleur;
import model.PieceIHM;
import controler.controlerLocal.MazeGameControler;



/**
 * Vue console d'un jeu
 * Cette classe est un observateur et le damier est mis à jour à chaque changement dans la classe métier
 */

/*
public class MazeGameCmdLine implements Observer{

	MazeGameControler mazeGameControler;

	public MazeGameCmdLine(MazeGameControler mazeGameControler) {
		this.MazeGameControler = mazeGameControler;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		
		System.out.println(mazeGameControler.getMessage() + "\n");	

		List<PieceIHM> piecesIHM = (List<PieceIHM>) arg1;


		String[][] damier = new String[8][8];
		
		// création d'un tableau 2D avec les noms des pièces
		for(PieceIHM pieceIHM : piecesIHM) {

			Couleur color = pieceIHM.getCouleur();
			String stColor = (Couleur.BLANC == color ? "B_" : "N_" );
			String type = (pieceIHM.getNamePiece()).substring(0, 2);
			
			
				damier[pieceIHM.getY()][pieceIHM.getX()] = stColor + type;
					
		}
		
		// Affichage du tableau formatté
		String st = "    0     1     2     3     4     5    6     7 \n";
		for ( int i = 0; i < 8; i++) {
			st += i + " ";
			for ( int j = 0; j < 8; j++) {				 
				String nomPiece = damier[i][j];				
				if (nomPiece != null) {						
					st += nomPiece + "  ";	
				} 
				else {
					st += "____  ";
				}
			}
			st +="\n";
		}
		
		System.out.println(st);		
	}

	public void go() {

		System.out.print("\n Déplacement de 3,6 vers 3,4 = ");
		mazeGameControler.move(new Coord(3,6), new Coord(3, 4));	// true
		
		// dans ce cas, update non appelé et pas d'affichage 
		// controleur empêche le move car pas le bon joueur
		System.out.print("\n Déplacement de 3,4 vers 3,6 = ");		
		mazeGameControler.move(new Coord(3,4), new Coord(3, 6));	// false 

		System.out.print("\n Déplacement de 4,1 vers 4,3 = ");
		mazeGameControler.move(new Coord(4, 1), new Coord(4, 3));	// true

		System.out.print("\n Déplacement de 3,4 vers 3,4 = ");
		mazeGameControler.move(new Coord(3, 4), new Coord(3, 4));	// false

		System.out.print("\n Déplacement de 3,4 vers 4,3 = ");
		mazeGameControler.move(new Coord(3, 4), new Coord(4, 3));	// false
		
		System.out.print("\n Déplacement de 3,4 vers 3,3 = ");
		System.out.print(mazeGameControler.move(new Coord(3, 4), new Coord(3, 3))); // true	
		
		System.out.print("\n Déplacement de 1,7 vers 2,5 = ");
		System.out.print(mazeGameControler.move(new Coord(1, 7), new Coord(2, 5))); // false	
		
		System.out.print("\n Déplacement de 1,0 vers 2,2 = ");
		System.out.print(mazeGameControler.move(new Coord(1, 0), new Coord(2, 2))); // true

	}
}
	*/
