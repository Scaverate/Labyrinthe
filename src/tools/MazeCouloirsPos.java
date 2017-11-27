package tools;

import model.Coord;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

/*
	* Liste en dur des types et positions des couloirs - volontairement illisible
 */
public enum MazeCouloirsPos {

	COULOIRFIXE1("CouloirFixe", new Coord(0, 0), false, true, true, false),
	COULOIRAMOVIBLE1("CouloirAmovible", new Coord(1, 0), getRandomBooleanList()),
	COULOIRFIXE2("CouloirFixe", new Coord(2, 0), false, true, true, true),
	COULOIRAMOVIBLE2("CouloirAmovible", new Coord(3, 0), getRandomBooleanList()),
	COULOIRFIXE3("CouloirFixe", new Coord(4, 0), false, true, true, true),
	COULOIRAMOVIBLE3("CouloirAmovible", new Coord(5, 0), getRandomBooleanList()),
	COULOIRFIXE4("CouloirFixe", new Coord(6, 0), false, false, true, true),
	COULOIRAMOVIBLE4("CouloirAmovible", new Coord(0, 1), getRandomBooleanList()),
	COULOIRAMOVIBLE5("CouloirAmovible", new Coord(1, 1), getRandomBooleanList()),
	COULOIRAMOVIBLE6("CouloirAmovible", new Coord(2, 1), getRandomBooleanList()),
	COULOIRAMOVIBLE7("CouloirAmovible", new Coord(3, 1), getRandomBooleanList()),
	COULOIRAMOVIBLE8("CouloirAmovible", new Coord(4, 1), getRandomBooleanList()),
	COULOIRAMOVIBLE9("CouloirAmovible", new Coord(5, 1), getRandomBooleanList()),
	COULOIRAMOVIBLE10("CouloirAmovible", new Coord(6, 1), getRandomBooleanList()),
	COULOIRFIXE5("CouloirFixe", new Coord(0, 2), true, true, true, false),
	COULOIRAMOVIBLE11("CouloirAmovible", new Coord(1, 2), getRandomBooleanList()),
	COULOIRFIXE6("CouloirFixe", new Coord(2, 2), true, true, true, false),
	COULOIRAMOVIBLE12("CouloirAmovible", new Coord(3, 2), getRandomBooleanList()),
	COULOIRFIXE7("CouloirFixe", new Coord(4, 2), false, true, true, true),
	COULOIRAMOVIBLE13("CouloirAmovible", new Coord(5, 2), getRandomBooleanList()),
	COULOIRFIXE8("CouloirFixe", new Coord(6, 2), true, false, true, true),
	COULOIRAMOVIBLE14("CouloirAmovible", new Coord(0, 3), getRandomBooleanList()),
	COULOIRAMOVIBLE15("CouloirAmovible", new Coord(1, 3), getRandomBooleanList()),
	COULOIRAMOVIBLE16("CouloirAmovible", new Coord(2, 3), getRandomBooleanList()),
	COULOIRAMOVIBLE17("CouloirAmovible", new Coord(3, 3), getRandomBooleanList()),
	COULOIRAMOVIBLE18("CouloirAmovible", new Coord(4, 3), getRandomBooleanList()),
	COULOIRAMOVIBLE19("CouloirAmovible", new Coord(5, 3), getRandomBooleanList()),
	COULOIRAMOVIBLE20("CouloirAmovible", new Coord(6, 3), getRandomBooleanList()),
	COULOIRFIXE9("CouloirFixe", new Coord(0, 4), true, true, true, false),
	COULOIRAMOVIBLE21("CouloirAmovible", new Coord(1, 4), getRandomBooleanList()),
	COULOIRFIXE10("CouloirFixe", new Coord(2, 4), true, true, false, true),
	COULOIRAMOVIBLE22("CouloirAmovible", new Coord(3, 4), getRandomBooleanList()),
	COULOIRFIXE11("CouloirFixe", new Coord(4, 4), true, false, true, true),
	COULOIRAMOVIBLE23("CouloirAmovible", new Coord(5, 4), getRandomBooleanList()),
	COULOIRFIXE12("CouloirFixe", new Coord(6, 4), true, false, true, true),
	COULOIRAMOVIBLE24("CouloirAmovible", new Coord(0, 5), getRandomBooleanList()),
	COULOIRAMOVIBLE25("CouloirAmovible", new Coord(1, 5), getRandomBooleanList()),
	COULOIRAMOVIBLE26("CouloirAmovible", new Coord(2, 5), getRandomBooleanList()),
	COULOIRAMOVIBLE27("CouloirAmovible", new Coord(3, 5), getRandomBooleanList()),
	COULOIRAMOVIBLE28("CouloirAmovible", new Coord(4, 5), getRandomBooleanList()),
	COULOIRAMOVIBLE29("CouloirAmovible", new Coord(5, 5), getRandomBooleanList()),
	COULOIRAMOVIBLE30("CouloirAmovible", new Coord(6, 5), getRandomBooleanList()),
	COULOIRFIXE13("CouloirFixe", new Coord(0, 6), true, true, false, false),
	COULOIRAMOVIBLE31("CouloirAmovible", new Coord(1, 6), getRandomBooleanList()),
	COULOIRFIXE14("CouloirFixe", new Coord(2, 6), true, true, false, true),
	COULOIRAMOVIBLE32("CouloirAmovible", new Coord(3, 6), getRandomBooleanList()),
	COULOIRFIXE15("CouloirFixe", new Coord(4, 6), true, true, false, true),
	COULOIRAMOVIBLE33("CouloirAmovible", new Coord(5, 6), getRandomBooleanList()),
	COULOIRFIXE16("CouloirFixe", new Coord(6, 6), true, false, false, true)
	;

	public String name;
	public Coord coord;
	public boolean isNorthOpened;
	public boolean isSouthOpened;
	public boolean isEastOpened;
	public boolean isWestOpened;
	public boolean isFixed;

	public static ArrayList<Boolean> getRandomBooleanList() {
		List<ArrayList> booleanSetList = new ArrayList<>();

		// differents types
		ArrayList<Boolean> type_0 = new ArrayList<>();
		ArrayList<Boolean> type_1 = new ArrayList<>();
		ArrayList<Boolean> type_2 = new ArrayList<>();
		ArrayList<Boolean> type_3 = new ArrayList<>();
		ArrayList<Boolean> type_4 = new ArrayList<>();
		ArrayList<Boolean> type_5 = new ArrayList<>();
		ArrayList<Boolean> type_6 = new ArrayList<>();
		ArrayList<Boolean> type_7 = new ArrayList<>();
		ArrayList<Boolean> type_8 = new ArrayList<>();
		ArrayList<Boolean> type_9 = new ArrayList<>();

		Random random = new Random();

		// ajouts des booleans correspondant aux differentes pieces existantes
		// type 0
		type_0.add(new Boolean(true));
		type_0.add(new Boolean(true));
		type_0.add(new Boolean(false));
		type_0.add(new Boolean(true));
		// type 1
		type_1.add(new Boolean(true));
		type_1.add(new Boolean(false));
		type_1.add(new Boolean(true));
		type_1.add(new Boolean(false));
		// type 2
		type_2.add(new Boolean(true));
		type_2.add(new Boolean(true));
		type_2.add(new Boolean(false));
		type_2.add(new Boolean(false));
		// type 3
		type_3.add(new Boolean(true));
		type_3.add(new Boolean(false));
		type_3.add(new Boolean(false));
		type_3.add(new Boolean(true));
		// type 4
		type_4.add(new Boolean(false));
		type_4.add(new Boolean(true));
		type_4.add(new Boolean(false));
		type_4.add(new Boolean(true));
		// type 5
		type_5.add(new Boolean(false));
		type_5.add(new Boolean(true));
		type_5.add(new Boolean(true));
		type_5.add(new Boolean(false));
		// type 6
		type_6.add(new Boolean(false));
		type_6.add(new Boolean(false));
		type_6.add(new Boolean(true));
		type_6.add(new Boolean(true));
		// type 7
		type_7.add(new Boolean(false));
		type_7.add(new Boolean(true));
		type_7.add(new Boolean(true));
		type_7.add(new Boolean(true));
		// type 8
		type_8.add(new Boolean(true));
		type_8.add(new Boolean(true));
		type_8.add(new Boolean(true));
		type_8.add(new Boolean(false));
		// type 9
		type_9.add(new Boolean(true));
		type_9.add(new Boolean(false));
		type_9.add(new Boolean(true));
		type_9.add(new Boolean(true));

		booleanSetList.add(type_0);
		booleanSetList.add(type_1);
		booleanSetList.add(type_2);
		booleanSetList.add(type_3);
		booleanSetList.add(type_4);
		booleanSetList.add(type_5);
		booleanSetList.add(type_6);
		booleanSetList.add(type_7);
		booleanSetList.add(type_8);
		booleanSetList.add(type_9);


		return booleanSetList.get(random.nextInt(booleanSetList.size()));
	}

	MazeCouloirsPos( String name, Coord coord, boolean isNorthOpened, boolean isEastOpened, boolean isSouthOpened, boolean isWestOpened) {
		this.name = name;
		this.coord = coord;
		this.isNorthOpened = isNorthOpened;
		this.isSouthOpened = isSouthOpened;
		this.isEastOpened = isEastOpened;
		this.isWestOpened = isWestOpened;
		this.isFixed = true;
	}

	MazeCouloirsPos(String name, Coord coord, ArrayList<Boolean> booleans) {
		this.name = name;
		this.coord = coord;
		this.isNorthOpened = booleans.get(0);
		this.isSouthOpened = booleans.get(2);
		this.isEastOpened = booleans.get(1);
		this.isWestOpened = booleans.get(3);
		this.isFixed = false;
	}
}

