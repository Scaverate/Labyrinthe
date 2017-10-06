package tools;

import model.Coord;
import model.Couleur;

public enum MazePiecePos {
	PIONBLEU("PionBLEU", Couleur.BLEU, new Coord[] {new Coord(0,6)}),
	PIONROUGE("PionROUGE", Couleur.ROUGE, new Coord[] {new Coord(0,0)}),
	PIONJAUNE("PionJAUNE", Couleur.JAUNE, new Coord[] {new Coord(6,0)}), 
	PIONVERT("PionVERT", Couleur.VERT, new Coord[] {new Coord(6,6)}); 
	public String nom;
	public Couleur couleur;
	public Coord[] coords = new Coord[8];  

	MazePiecePos( String nom, Couleur couleur, Coord[] coords) {
		this.nom = nom;
		this.couleur = couleur;
		this.coords = coords;
	}
}

