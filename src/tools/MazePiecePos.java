package tools;

import model.Coord;
import model.Couleur;

public enum MazePiecePos {
	PIONBLEU("Pion", Couleur.BLEU, new Coord[] {new Coord(6,6)}),
	PIONROUGE("Pion", Couleur.ROUGE, new Coord[] {new Coord(0,0)}),
	PIONJAUNE("Pion", Couleur.JAUNE, new Coord[] {new Coord(6,0)}),
	PIONVERT("Pion", Couleur.VERT, new Coord[] {new Coord(0,6)});
	public String name;
	public Couleur couleur;
	public Coord[] coords = new Coord[8];

	MazePiecePos( String name, Couleur couleur, Coord[] coords) {
		this.name = name;
		this.couleur = couleur;
		this.coords = coords;
	}
}

