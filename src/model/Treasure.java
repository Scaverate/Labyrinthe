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
	
	@Override
	public boolean equals(Object object){
		if (this == object) {
			return true;
		}
	    if (object == null) {
	    		return false;
	    }
	    if (getClass() != object.getClass()) {
	    		return false;
	    }
	    if(this.getTreasureX() == ((Treasure) object).getTreasureX() && this.getTreasureY() == ((Treasure) object).getTreasureY() && this.getTreasureId() == ((Treasure) object).getTreasureId() && this.getTreasureType().equals(((Treasure) object).getTreasureType())) {
	    		return true;
	    }
	    return false;
	}
}
