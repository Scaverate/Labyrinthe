package tools;

public enum MazeTresorsImage {
	TRESORUN("Dos", "dos_carte.png"),
	TRESORDEUX("DosJeu", "dos_carte_jeu.png"),
	TRESORTROIS("TresorTrois", "tresor_trois.png");
	
	public String nom;
	public String imageFile;   

	MazeTresorsImage(String nom, String imageFile) { 
		this.nom = nom;
		this.imageFile = imageFile;
	}
}
