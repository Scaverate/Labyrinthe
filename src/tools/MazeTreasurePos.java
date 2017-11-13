package tools;

import model.Coord;

public enum MazeTreasurePos {
	//Création 24 objets
	COFFRE("Tresor1", new Coord[] {new Coord(0,0)}),
	BOUTEILLERHUM("Tresor2", new Coord[] {new Coord(0,0)}),
	CROCODILE("Tresor3", new Coord[] {new Coord(0,0)}), 
	BATEAUX("Tresor4", new Coord[] {new Coord(0,0)}),
	COURONNE("Tresor3", new Coord[] {new Coord(0,0)}),
	CORNE("Tresor3", new Coord[] {new Coord(0,0)}),
	MAGE("Tresor3", new Coord[] {new Coord(0,0)}),
	PAPYRUS("Tresor3", new Coord[] {new Coord(0,0)}),
	EPEE("Tresor3", new Coord[] {new Coord(0,0)}),
	DIAMAND("Tresor3", new Coord[] {new Coord(0,0)}),
	TABLEAU("Tresor3", new Coord[] {new Coord(0,0)}),
	SABLIER("Tresor3", new Coord[] {new Coord(0,0)});
	public String nom;
	public Coord[] coords = new Coord[8];
	
	MazeTreasurePos( String nom, Coord[] coords) {
		this.nom = nom;
		this.coords = coords;
	}
}