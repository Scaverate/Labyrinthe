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
	    if(this.getTreasureX() == ((AbstractTreasure) object).getTreasureX() && this.getTreasureY() == ((AbstractTreasure) object).getTreasureY() && this.getTreasureId() == ((AbstractTreasure) object).getTreasureId() && this.getTreasureType().equals(((AbstractTreasure) object).getTreasureType())) {
	    		return true;
	    }
	    return false;
	}
}
