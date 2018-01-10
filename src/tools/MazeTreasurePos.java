package tools;

import model.Coord;

public enum MazeTreasurePos {
	//Création 24 objets
	//Les objets sur les couloirs fixes ont les bonnes coordonnees
	BULLET_BILL(1, "TreasureFixed",new Coord(0,2)),
	BLOC_QUESTION(2, "TreasureFixed", new Coord(0,4)),
	BOB_OMB(3, "TreasureFixed", new Coord(2,0)), 
	CHAMPIGNON(4, "TreasureFixed", new Coord(2,2)),
	HELICE(5, "TreasureFixed", new Coord(2,4)),
	BANANE(6, "TreasureFixed", new Coord(2,6)),
	BLOOBS(7, "TreasureFixed", new Coord(4,0)),
	CARAPACE_VERTE(8, "TreasureFixed", new Coord(4,2)),
	GOOMBA(9, "TreasureFixed", new Coord(4,4)),
	PIECE(10, "TreasureFixed", new Coord(4,6)),
	ECLAIR(11, "TreasureFixed", new Coord(6,2)),
	PLANTE_PIRANHA(12, "TreasureFixed", new Coord(6,4)),
	CHAMPIGNON_DORE(13, "TreasureMoveable", new Coord(0,0)),
	FLEUR_FEU(14, "TreasureMoveable", new Coord(0,0)),
	CARAPACE_EPINES(15, "TreasureMoveable", new Coord(0,0)),
	TRIPLE_CARAPACE_ROUGE(16, "TreasureMoveable", new Coord(0,0)),
	FLEU_BOOMERANG(17, "TreasureMoveable", new Coord(0,0)),
	ETOILE(18, "TreasureMoveable", new Coord(0,0)),
	FEUILLE_INVINCIBILITE(19, "TreasureMoveable", new Coord(0,0)),
	BOO(20, "TreasureMoveable", new Coord(0,0)),
	NUAGE_ZAP(21, "TreasureMoveable", new Coord(0,0)),
	FLEUR_GLACE(22, "TreasureMoveable", new Coord(0,0)),
	BLOC_POW(23, "TreasureMoveable", new Coord(0,0)),
	DOUBLE_CERISE(24, "TreasureMoveable", new Coord(0,0)),
	PEACH(25, "TreasureFixed", new Coord(0,0)),
	DAISY(26, "TreasureFixed", new Coord(6,6)),
	BIRDO(27, "TreasureFixed", new Coord(6,0)),
	TOADETTE(28, "TreasureFixed", new Coord(0,6));
	
	public int id;
	public String type;
	public Coord coord;
	
	MazeTreasurePos( int id, String type, Coord coords) {
		this.id = id;
		this.type = type;
		this.coord = coords;
	}
}