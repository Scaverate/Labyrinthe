package tools;

import model.Coord;
import model.Couleur;

public enum MazeTreasurePos {
	COFFRE("Tresor1", new Coord[] {new Coord(5,5)}),
	BOUTEILLERHUM("Tresor2", new Coord[] {new Coord(1,0)}),
	CROCODILE("Tresor3", new Coord[] {new Coord(5,0)}), 
	BATEAUX("Tresor4", new Coord[] {new Coord(0,5)}); 
	public String nom;
	public Coord[] coords = new Coord[8];

	MazeTreasurePos( String nom, Coord[] coords) {
		this.nom = nom;
		this.coords = coords;
	}
}

