package tools;

/*
 * Liste des types de pièces
 */
public enum MazeCouloirsType {
	//Couloirs en forme de I
	I1(1, false, true, false, true),
	I2(1, true, false, true, false),
	//Couloirs en forme de L
	L1(2, false, false, true, true),
	L2(2, true, false, false, true),
	L3(2, false, true, true, false),
	L4(2, true, true, false, false),
	//Couloirs en forme de T
	T1(3, false, true, true, true),
	T2(3, true, false, true, true),
	T3(3, true, true, false, true),
	T4(3, true, true, true, false),
	;

	public int typeId;
	public boolean isNorthOpened;
	public boolean isSouthOpened;
	public boolean isEastOpened;
	public boolean isWestOpened;

	MazeCouloirsType(int typeId, boolean isNorthOpened, boolean isEastOpened, boolean isSouthOpened, boolean isWestOpened) {
		this.typeId = typeId;
		this.isNorthOpened = isNorthOpened;
		this.isSouthOpened = isSouthOpened;
		this.isEastOpened = isEastOpened;
		this.isWestOpened = isWestOpened;
	}
}

