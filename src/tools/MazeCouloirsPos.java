package tools;

import model.Coord;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

/*
 * Liste des pièces présentes sur le plateau
 */
public enum MazeCouloirsPos {
	
	FIXED1(new Coord(0, 0), false, true, true, false),
	MOVEABLE1(new Coord(1, 0)),
	FIXED2(new Coord(2, 0), false, true, true, true),
	MOVEABLE2(new Coord(3, 0)),
	FIXED3(new Coord(4, 0), false, true, true, true),
	MOVEABLE3(new Coord(5, 0)),
	FIXED4(new Coord(6, 0), false, false, true, true),
	MOVEABLE4(new Coord(0, 1)),
	MOVEABLE5(new Coord(1, 1)),
	MOVEABLE6(new Coord(2, 1)),
	MOVEABLE7(new Coord(3, 1)),
	MOVEABLE8(new Coord(4, 1)),
	MOVEABLE9(new Coord(5, 1)),
	MOVEABLE10(new Coord(6, 1)),
	FIXED5(new Coord(0, 2), true, true, true, false),
	MOVEABLE11(new Coord(1, 2)),
	FIXED6(new Coord(2, 2), true, true, true, false),
	MOVEABLE12(new Coord(3, 2)),
	FIXED7(new Coord(4, 2), false, true, true, true),
	MOVEABLE13(new Coord(5, 2)),
	FIXED8(new Coord(6, 2), true, false, true, true),
	MOVEABLE14(new Coord(0, 3)),
	MOVEABLE15(new Coord(1, 3)),
	MOVEABLE16(new Coord(2, 3)),
	MOVEABLE17(new Coord(3, 3)),
	MOVEABLE18(new Coord(4, 3)),
	MOVEABLE19(new Coord(5, 3)),
	MOVEABLE20(new Coord(6, 3)),
	FIXED9(new Coord(0, 4), true, true, true, false),
	MOVEABLE21(new Coord(1, 4)),
	FIXED10(new Coord(2, 4), true, true, false, true),
	MOVEABLE22(new Coord(3, 4)),
	FIXED11(new Coord(4, 4), true, false, true, true),
	MOVEABLE23(new Coord(5, 4)),
	FIXED12(new Coord(6, 4), true, false, true, true),
	MOVEABLE24(new Coord(0, 5)),
	MOVEABLE25(new Coord(1, 5)),
	MOVEABLE26(new Coord(2, 5)),
	MOVEABLE27(new Coord(3, 5)),
	MOVEABLE28(new Coord(4, 5)),
	MOVEABLE29(new Coord(5, 5)),
	MOVEABLE30(new Coord(6, 5)),
	FIXED13(new Coord(0, 6), true, true, false, false),
	MOVEABLE31(new Coord(1, 6)),
	FIXED14(new Coord(2, 6), true, true, false, true),
	MOVEABLE32(new Coord(3, 6)),
	FIXED15(new Coord(4, 6), true, true, false, true),
	MOVEABLE33(new Coord(5, 6)),
	FIXED16(new Coord(6, 6), true, false, false, true)
	;

	public Coord coord;
	public boolean isNorthOpened;
	public boolean isSouthOpened;
	public boolean isEastOpened;
	public boolean isWestOpened;
	public boolean isFixed;

	MazeCouloirsPos(Coord coord, boolean isNorthOpened, boolean isEastOpened, boolean isSouthOpened, boolean isWestOpened) {
		this.coord = coord;
		this.isNorthOpened = isNorthOpened;
		this.isSouthOpened = isSouthOpened;
		this.isEastOpened = isEastOpened;
		this.isWestOpened = isWestOpened;
		this.isFixed = true;
	}

	MazeCouloirsPos(Coord coord) {
		this.coord = coord;
		this.isFixed = false;
	}
}

