package tools;

import model.Coord;

public enum MazeCouloirsPos {
	COULOIRFIXE1("CouloirFixe", new Coord(0, 0), false, true, true, false),
	COULOIRFIXE2("CouloirFixe", new Coord(2, 0), false, true, true, true),
	COULOIRFIXE3("CouloirFixe", new Coord(4, 0), false, true, true, true),
	COULOIRFIXE4("CouloirFixe", new Coord(6, 0), false, false, true, true),
	COULOIRFIXE5("CouloirFixe", new Coord(0, 2), true, true, true, false),
	COULOIRFIXE6("CouloirFixe", new Coord(2, 2), true, true, true, false),
	COULOIRFIXE7("CouloirFixe", new Coord(4, 2), false, true, true, true),
	COULOIRFIXE8("CouloirFixe", new Coord(6, 2), true, false, true, true),
	COULOIRFIXE9("CouloirFixe", new Coord(0, 4), true, true, true, false),
	COULOIRFIXE10("CouloirFixe", new Coord(2, 4), true, true, false, true),
	COULOIRFIXE11("CouloirFixe", new Coord(4, 4), true, false, true, true),
	COULOIRFIXE12("CouloirFixe", new Coord(6, 4), true, false, true, true),
	COULOIRFIXE13("CouloirFixe", new Coord(0, 6), true, true, false, false),
	COULOIRFIXE14("CouloirFixe", new Coord(2, 6), true, true, false, true),
	COULOIRFIXE15("CouloirFixe", new Coord(4, 6), true, true, false, true),
	COULOIRFIXE16("CouloirFixe", new Coord(6, 6), true, false, false, true);

	public String nom;
	public Coord coord;
	public boolean isNorthOpened;
	public boolean isSouthOpened;
	public boolean isEastOpened;
	public boolean isWestOpened;

	MazeCouloirsPos( String nom, Coord coord, boolean isNorthOpened, boolean isEastOpened, boolean isSouthOpened, boolean isWestOpened) {
		this.nom = nom;
		this.coord = coord;
		this.isNorthOpened = isNorthOpened;
		this.isSouthOpened = isSouthOpened;
		this.isEastOpened = isEastOpened;
		this.isWestOpened = isWestOpened;
	}
}

