package tools;

import model.Coord;
import model.Couleur;

public enum MazePiecePos {
	PIONBLEU("Pion", Couleur.BLEU, new Coord[] {new Coord(0,6)}),
	PIONROUGE("Pion", Couleur.ROUGE, new Coord[] {new Coord(0,0)}),
	PIONJAUNE("Pion", Couleur.JAUNE, new Coord[] {new Coord(6,0)}), 
	PIONVERT("Pion", Couleur.VERT, new Coord[] {new Coord(6,6)}); 
	public String nom;
	public Couleur couleur;
	public Coord[] coords = new Coord[8];  

	MazePiecePos( String nom, Couleur couleur, Coord[] coords) {
		this.nom = nom;
		this.couleur = couleur;
		this.coords = coords;
	}
}

