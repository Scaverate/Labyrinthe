package tools;

import model.Coord;
import model.Couleur;

public enum MazeCouloirsPos {
	COULOIRFIXE1("Couloir", Couleur.BLEU, new Coord[] {new Coord(6,6)}),
	COULOIRFIXE2("Couloir", Couleur.ROUGE, new Coord[] {new Coord(0,0)}),
	COULOIRFIXE3("Couloir", Couleur.JAUNE, new Coord[] {new Coord(6,0)}), 
	COULOIRFIXE4("Couloir", Couleur.VERT, new Coord[] {new Coord(0,6)}),
	COULOIRFIXE5("Couloir", Couleur.BLEU, new Coord[] {new Coord(6,6)}),
	COULOIRFIXE6("Couloir", Couleur.ROUGE, new Coord[] {new Coord(0,0)}),
	COULOIRFIXE7("Couloir", Couleur.JAUNE, new Coord[] {new Coord(6,0)}), 
	COULOIRFIXE8("Couloir", Couleur.VERT, new Coord[] {new Coord(0,6)}), 
	COULOIRFIXE9("Couloir", Couleur.BLEU, new Coord[] {new Coord(6,6)}),
	COULOIRFIXE10("Couloir", Couleur.ROUGE, new Coord[] {new Coord(0,0)}),
	COULOIRFIXE11("Couloir", Couleur.JAUNE, new Coord[] {new Coord(6,0)}), 
	COULOIRFIXE12("Couloir", Couleur.VERT, new Coord[] {new Coord(0,6)}),
	COULOIRFIXE13("Couloir", Couleur.BLEU, new Coord[] {new Coord(6,6)}),
	COULOIRFIXE14("Couloir", Couleur.ROUGE, new Coord[] {new Coord(0,0)}),
	COULOIRFIXE15("Couloir", Couleur.JAUNE, new Coord[] {new Coord(6,0)}), 
	COULOIRFIXE16("Couloir", Couleur.VERT, new Coord[] {new Coord(0,6)});
	public String nom;
	public Couleur couleur;
	public Coord[] coords = new Coord[8];

	MazeCouloirsPos( String nom, Couleur couleur, Coord[] coords) {
		this.nom = nom;
		this.couleur = couleur;
		this.coords = coords;
	}
}

