package tools;

import model.Coord;

public enum MazeTreasurePos {
	//Création 24 objets
	COFFRE("TresorFixe",new Coord(0,2)),
	BOUTEILLERHUM("TresorFixe", new Coord(0,4)),
	CROCODILE("TresorFixe", new Coord(2,0)), 
	BATEAUX("TresorFixe", new Coord(2,2)),
	COURONNE("TresorFixe", new Coord(2,4)),
	CORNE("TresorFixe", new Coord(2,6)),
	MAGE("TresorFixe", new Coord(4,0)),
	PAPYRUS("TresorFixe", new Coord(4,2)),
	EPEE("TresorFixe", new Coord(4,4)),
	DIAMAND("TresorFixe", new Coord(4,6)),
	TABLEAU("TresorFixe", new Coord(6,2)),
	SABLIER("TresorFixe", new Coord(6,4)),
	POMME("TresorAmovible", new Coord(0,0)),
	POIRE("TresorAmovible", new Coord(0,0)),
	PECHE("TresorAmovible", new Coord(0,0)),
	ABRICOT("TresorAmovible", new Coord(0,0)),
	BANANE("TresorAmovible", new Coord(0,0)),
	CLEMENTINE("TresorAmovible", new Coord(0,0)),
	ORANGE("TresorAmovible", new Coord(0,0)),
	FRAISE("TresorAmovible", new Coord(0,0)),
	FRAMBOISE("TresorAmovible", new Coord(0,0)),
	CERISE("TresorAmovible", new Coord(0,0)),
	PAMPLEMOUSSE("TresorAmovible", new Coord(0,0)),
	RAISIN("TresorAmovible", new Coord(0,0)),
	TOMATE("TresorAmovible", new Coord(0,0));
	
	public String nom;
	public Coord coord;
	
	MazeTreasurePos( String nom, Coord coords) {
		this.nom = nom;
		this.coord = coords;
	}
}