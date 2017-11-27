package tools;

public enum MazeTresorsImage {
	TRESORUN("Dos", "dos_carte.png"),
	TRESORDEUX("DosJeu", "dos_carte_jeu.png"),
	TRESORTROIS("TresorTrois", "tresor_trois.png");
	
	public String name;
	public String imageFile;   

	MazeTresorsImage(String name, String imageFile) { 
		this.name = name;
		this.imageFile = imageFile;
	}
}
