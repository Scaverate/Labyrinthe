package model;

import java.util.LinkedList;
import java.util.List;

public class Plateau implements BoardGames {
	
	public Plateau(){
		/*
		jeuBlanc = new Jeu(Couleur.BLANC);
		jeuNoir = new Jeu(Couleur.NOIR);
		*/
		jeuCourant = null;
		message = new String("");
	}
	
	public void switchJoueur(){
		/*
		this.jeuCourant = (this.jeuCourant == this.jeuBlanc ? this.jeuNoir : this.jeuBlanc);
		*/
	}
	
	public boolean isMoveOk(int xInit, int yInit, int xFinal, int yFinal){
		boolean canMove = false;
		
		/*
		boolean pieceHere = false;
		if(this.jeuCourant != null){
			pieceHere = this.jeuCourant.isPieceHere(xInit,yInit);
			if(pieceHere){
				if(this.jeuCourant.isMoveOk(xInit,yInit,xFinal,yFinal,true,true)){
					if(!this.jeuCourant.isPieceHere(xFinal,yFinal)){
						if(!this.pieceOnTraject(xInit,yInit,xFinal,yFinal)){ // pas au bon endroit
							canMove = true;
						}
					}
				}
			}
		}
		*/
		
		return canMove;
	}
	
	private boolean isPieceAnyColor(int coord1, int coord2){
		boolean ret = false;
		
		/*
		if (jeuBlanc.isPieceHere(coord1, coord2) || jeuNoir.isPieceHere(coord1, coord2)) {
			ret = true;
		}
		*/
		
		return ret;
	}
	
	private boolean pieceOnTraject(int xInit, int yInit, int xFinal, int yFinal) {
		boolean pieceOnTraject = false;

		/*
		// Mouvement rectiligne le long de l'axe Y
		if (xInit == xFinal) {

			// si vers Y croissants
			if (yFinal > yInit) {
				for (int i = yInit + 1; i <= yFinal; i++) {
					pieceOnTraject = isPieceAnyColor(xInit,i);
					if(pieceOnTraject){ break; }
				}
			}
			// si vers Y decroissants
			if (yFinal < yInit) {				
				for (int i = yInit - 1; i >= yFinal; i--) {
					pieceOnTraject = isPieceAnyColor(xInit,i);
					if(pieceOnTraject){ break; }
				}
			}
		} 
		else {
			// Mouvement rectiligne le long de l'axe X
			if (yInit == yFinal) { 
				// si vers X croissants
				if (xFinal > xInit) {
					for (int i = xInit + 1; i <= xFinal; i++) {
						pieceOnTraject = isPieceAnyColor(yInit,i);
						if(pieceOnTraject){ break; }
					}
				}
				// si vers X decroissants
				if (xFinal < xInit) {
					for (int i = xInit - 1; i >= xFinal; i--) {
						pieceOnTraject = isPieceAnyColor(yInit,i);
						if(pieceOnTraject){ break; }
					}
				}
			} 
			else {
				// Mouvement en diagonale 
				if (Math.abs(yInit - yFinal) == Math.abs(xInit - xFinal)) {
					int ecart = Math.abs(yInit - yFinal);

					// X croissant, Y croissant
					if ((xFinal - xInit > 0) && (yFinal - yInit > 0)) {
						for (int i = 1; i <= ecart; i++) {
							pieceOnTraject = isPieceAnyColor(xInit + i,yInit + i);
							if(pieceOnTraject){ break; }			
						}
					}
					// X croissant, Y decroissant
					if ((xFinal - xInit > 0) && (yFinal - yInit < 0)) {
						for (int i = 1; i <= ecart; i++) {
							pieceOnTraject = isPieceAnyColor(xInit + i,yInit - i);
							if(pieceOnTraject){ break; }					
						}
					}
					// X decroissant, Y decroissant
					if ((xFinal - xInit < 0) && (yFinal - yInit < 0)) {
						for (int i = 1; i <= ecart; i++) {
							pieceOnTraject = isPieceAnyColor(xInit - i,yInit - i);
							if(pieceOnTraject){ break; }							
						}
					}					

					// X decroissant, Y croissant
					if ((xFinal - xInit < 0) && (yFinal - yInit > 0)) {
						for (int i = 1; i <= ecart; i++) {
							pieceOnTraject = isPieceAnyColor(xInit - i,yInit + i);
							if(pieceOnTraject){ break; }						
						}
					}
				} 
				else {
					// Dans tous les autres cas de mouvement
					// la piece au coordonnées initiale est un cavalier
					pieceOnTraject = isPieceAnyColor(xFinal,yFinal);
				}
			}
		}
		*/
		return pieceOnTraject;
	}

	@Override
	public boolean move(int xInit, int yInit, int xFinal, int yFinal) {
		boolean hasMoved = false;
		
		/*
		if(this.jeuCourant != null){
			hasMoved = jeuCourant.move(xInit, yInit, xFinal, yFinal);
			if(hasMoved){
			}
		}
		*/
		
		return hasMoved;
	}

	@Override
	public boolean isEnd() {
		return false;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String message){
		this.message = message;
	}

	@Override
	public Couleur getColorCurrentPlayer() {
		Couleur couleur = null;
		
		/*
		if(this.jeuCourant == this.jeuBlanc){
			couleur = Couleur.BLANC;
		}
		else{
			couleur = Couleur.NOIR;
		}
		*/
		
		return couleur;
	}

	@Override
	public Couleur getPieceColor(int x, int y) {
		return this.jeuCourant.getPieceColor(x,y);
	}
	

	public List<PieceIHMs> getPiecesIHM(){
		/*
		List<PieceIHMs> list1 = new LinkedList<PieceIHMs>();
		List<PieceIHMs>	list2 = new LinkedList<PieceIHMs>();
		*/
		List<PieceIHMs> listFinale = new LinkedList<PieceIHMs>();
		
		/*
		if(this.jeuBlanc != null){
			list1 = this.jeuBlanc.getPiecesIHM();
		}
		if(this.jeuNoir != null){
			list2 = this.jeuNoir.getPiecesIHM();
		}
		if(list1 != null && listFinale != null){
			listFinale.addAll(list1);
		}
		if(list2 != null && listFinale != null){
			listFinale.addAll(list2);
		}
		*/
		
		return listFinale;
	}
	
	public Couleur getJeuCourant(){
		Couleur couleur = null;
		
		/*
		if(this.jeuCourant != null){
			if(this.jeuCourant == jeuBlanc){
				couleur = Couleur.BLANC;
			}
			else{
				couleur = Couleur.NOIR;
			}
		}
		*/
		
		return couleur;
	}
	
	public String toString(){
		String string = new String("");
		
		/*
		if(jeuBlanc != null){
			string += "Jeu blanc : " + jeuBlanc.toString();
		}
		if(jeuNoir != null){
			string += "\nJeu noir : " + jeuNoir.toString();
		}
		if(jeuCourant != null){
			string += "\nJeu courant : " + this.getJeuCourant();
		}
		*/
		
		return string;
	}
	
	/*
	private Jeu jeuBlanc;
	private Jeu jeuNoir;
	*/
	
	private Jeu jeuCourant;
	private String message;

}
