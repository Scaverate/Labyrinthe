package tools;

public enum MazePieceImage {
	PIONBLEU("PionBLEU", "pion_bleu.png"),
	PIONROUGE("PionROUGE", "pion_rouge.png"),
	PIONJAUNE("PionJAUNE", "pion_jaune.png"),
	PIONVERT("PionVERT", "pion_vert.png");
	
	public String name;
	public String imageFile;   

	MazePieceImage(String name, String imageFile) { 
		this.name = name;
		this.imageFile = imageFile;
	}
}

