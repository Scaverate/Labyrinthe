package tools;

public enum MazeTreasureImage {
	COFFRE("TresorAmovible", "treasure.png"),
	BOUTEILLERHUM("TresorAmovible", "treasure.png"),
	CROCODILE("TresorFixe", "treasure.png"),
	BATEAUX("TresorFixe", "treasure.png");
	
	public String nom;
	public String imageFile;   

	MazeTreasureImage(String nom, String imageFile) { 
		this.nom = nom;
		this.imageFile = imageFile;
	}
}

