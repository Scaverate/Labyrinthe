package tools;

import model.Coord;

public enum MazeTreasurePos {
	//Création 24 objets
	//Les objets sur les couloirs fixes ont les bonnes coordonnees
	COFFRE("TreasureFixed",new Coord(0,2)),
	BOUTEILLERHUM("TreasureFixed", new Coord(0,4)),
	CROCODILE("TreasureFixed", new Coord(2,0)), 
	BATEAUX("TreasureFixed", new Coord(2,2)),
	COURONNE("TreasureFixed", new Coord(2,4)),
	CORNE("TreasureFixed", new Coord(2,6)),
	MAGE("TreasureFixed", new Coord(4,0)),
	PAPYRUS("TreasureFixed", new Coord(4,2)),
	EPEE("TreasureFixed", new Coord(4,4)),
	DIAMAND("TreasureFixed", new Coord(4,6)),
	TABLEAU("TreasureFixed", new Coord(6,2)),
	SABLIER("TreasureFixed", new Coord(6,4)),
	POMME("TreasureMoveable", new Coord(0,0)),
	POIRE("TreasureMoveable", new Coord(0,0)),
	PECHE("TreasureMoveable", new Coord(0,0)),
	ABRICOT("TreasureMoveable", new Coord(0,0)),
	BANANE("TreasureMoveable", new Coord(0,0)),
	CLEMENTINE("TreasureMoveable", new Coord(0,0)),
	ORANGE("TreasureMoveable", new Coord(0,0)),
	FRAISE("TreasureMoveable", new Coord(0,0)),
	FRAMBOISE("TreasureMoveable", new Coord(0,0)),
	CERISE("TreasureMoveable", new Coord(0,0)),
	PAMPLEMOUSSE("TreasureMoveable", new Coord(0,0)),
	RAISIN("TreasureMoveable", new Coord(0,0)),
	TOMATE("TreasureMoveable", new Coord(0,0));
	
	public String name;
	public Coord coord;
	
	MazeTreasurePos( String name, Coord coords) {
		this.name = name;
		this.coord = coords;
	}
}