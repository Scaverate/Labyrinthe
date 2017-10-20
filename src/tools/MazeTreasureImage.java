package tools;

public enum MazeTreasureImage {
	COFFRE("Tresor1", "treasure.png"),
	BOUTEILLERHUM("Tresor2", "treasure.png"),
	CROCODILE("Tresor3", "treasure.png"),
	BATEAUX("Tresor4", "treasure.png");
	
	public String nom;
	public String imageFile;   

	MazeTreasureImage(String nom, String imageFile) { 
		this.nom = nom;
		this.imageFile = imageFile;
	}
}

