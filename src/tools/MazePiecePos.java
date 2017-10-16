package tools;

import model.Coord;
import model.Couleur;

public enum MazePiecePos {
	PIONBLEU("Pion", Couleur.BLEU, 4, new Coord[] {new Coord(6,6)}),
	PIONROUGE("Pion", Couleur.ROUGE, 1, new Coord[] {new Coord(0,0)}),
	PIONJAUNE("Pion", Couleur.JAUNE, 2, new Coord[] {new Coord(6,0)}), 
	PIONVERT("Pion", Couleur.VERT, 3, new Coord[] {new Coord(0,6)}); 
	public String nom;
	public Couleur couleur;
	public Coord[] coords = new Coord[8];
	private int numPlayer;  

	MazePiecePos( String nom, Couleur couleur, int numPlayer, Coord[] coords) {
		this.nom = nom;
		this.numPlayer = numPlayer;
		this.couleur = couleur;
		this.coords = coords;
	}
}

