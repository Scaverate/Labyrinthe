package tools;

public enum MazePieceImage {
	PIONBLEU("PionBLEU", "pion_bleu.png"),
	PIONROUGE("PionROUGE", "pion_rouge.png"),
	PIONJAUNE("PionJAUNE", "pion_jaune.png"),
	PIONVERT("PionVERT", "pion_vert.png");
	
	public String nom;
	public String imageFile;   

	MazePieceImage(String nom, String imageFile) { 
		this.nom = nom;
		this.imageFile = imageFile;
	}
}

