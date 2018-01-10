package model;

public class Treasure extends AbstractTreasure {
	
	public Treasure(int x, int y, int id, String type, boolean isCatched) {
		super(x, y, id, type, isCatched);
	}
	public Treasure(Treasure treasure) {
		super(
				treasure.getTreasureX(),
				treasure.getTreasureY(),
				treasure.getTreasureId(),
				new String(treasure.getTreasureType()),
				treasure.isCatched()
		);
	}
}
