package tools;

public enum MazeTreasureImage {
	//Creation des images
	COFFRE("TreasureRemovable", "treasure.png"),
	BOUTEILLERHUM("TreasureRemovable", "treasure.png"),
	CROCODILE("TreasureFixed", "treasure.png"),
	BATEAUX("TreasureFixed", "treasure.png");
	
	public String name;
	public String imageFile;   

	MazeTreasureImage(String name, String imageFile) { 
		this.name = name;
		this.imageFile = imageFile;
	}
}

