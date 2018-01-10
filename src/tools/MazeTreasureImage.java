package tools;

import model.Coord;

public enum MazeTreasureImage {
	//Creation des images
	BULLET_BILL(1, "treasure_1.png"),
	BLOC_QUESTION(2, "treasure_2.png"),
	BOB_OMB(3, "treasure_3.png"), 
	CHAMPIGNON(4, "treasure_4.png"),
	HELICE(5, "treasure_5.png"),
	BANANE(6, "treasure_6.png"),
	BLOOBS(7, "treasure_7.png"),
	CARAPACE_VERTE(8, "treasure_8.png"),
	GOOMBA(9, "treasure_9.png"),
	PIECE(10, "treasure_10.png"),
	ECLAIR(11, "treasure_11.png"),
	PLANTE_PIRANHA(12, "treasure_12.png"),
	CHAMPIGNON_DORE(13, "treasure_13.png"),
	FLEUR_FEU(14, "treasure_14.png"),
	CARAPACE_EPINES(15, "treasure_15.png"),
	TRIPLE_CARAPACE_ROUGE(16, "treasure_16.png"),
	FLEU_BOOMERANG(17, "treasure_17.png"),
	ETOILE(18, "treasure_18.png"),
	FEUILLE_INVINCIBILITE(19, "treasure_19.png"),
	BOO(20, "treasure_20.png"),
	NUAGE_ZAP(21, "treasure_21.png"),
	FLEUR_GLACE(22, "treasure_22.png"),
	BLOC_POW(23, "treasure_23.png"),
	DOUBLE_CERISE(24, "treasure_24.png"),
	PEACH(25, "treasure_25.png"),
	DAISY(26, "treasure_26.png"),
	BIRDO(27, "treasure_27.png"),
	TOADETTE(28, "treasure_28.png");
	
	public int id;
	public String imageFile;   

	MazeTreasureImage(int id, String imageFile) { 
		this.id = id;
		this.imageFile = imageFile;
	}
}

