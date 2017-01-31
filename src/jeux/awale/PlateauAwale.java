package jeux.awale;

import java.util.ArrayList;

import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class PlateauAwale implements PlateauJeu {
	
	private final int RANGEES = 2;
	private final int TROUS = 6;
	
	/** Le joueur qui joue "dans la première rangée" */
	private static Joueur joueurBlanc;

	/** Le joueur qui joue "dans la deuxième rangée" */
	private static Joueur joueurNoir;
	
	private int[] plateau = new int[RANGEES * TROUS];
	
	private int gainsJoueurBlanc;
	private int gainsJoueurNoir;
	
	public PlateauAwale(){
		for(int i=0; i < RANGEES * TROUS; i++){
			this.plateau[i] = 4;
		}
		this.gainsJoueurBlanc = 0;
		this.gainsJoueurNoir = 0;
	}
	
	private PlateauAwale(int[] depuis){
		for(int i=0; i < RANGEES * TROUS; i++){
			this.plateau[i] = depuis[i];
		}
		this.gainsJoueurBlanc = 0;
		this.gainsJoueurNoir = 0;
	}

	public ArrayList<CoupJeu> coupsPossibles(Joueur j) {
		ArrayList<CoupJeu> lesCoupsPossibles = new ArrayList<CoupJeu>();
		if(j.equals(joueurBlanc)){
			for(int i=0; i < this.plateau.length-6; i++){  //regarde la premiere partie du plateau
				if(this.plateau[i] > 0) lesCoupsPossibles.add(new CoupAwale(i)); // on peut jouer
			}
		}else{
			for(int i=6; i < this.plateau.length; i++){ //regarde la deuxieme partie du plateau
				if(this.plateau[i] > 0) lesCoupsPossibles.add(new CoupAwale(i)); // on peut jouer
			}
		}
		return lesCoupsPossibles;
	}

	public void joue(Joueur j, CoupJeu c) {
		// TODO Auto-generated method stub
		CoupAwale cA = (CoupAwale) c;
		if(j.equals(PlateauAwale.joueurBlanc)){
			int currInd = cA.getIndiceTrou();
			int nbGraine = this.plateau[currInd];
			this.plateau[currInd] = 0;
			while(nbGraine > 0){
				if(currInd == cA.getIndiceTrou()){
					currInd++;
				}else{
					this.plateau[currInd] += 1;
					nbGraine--;
					currInd++;
				}
				if(currInd > TROUS * RANGEES){
					currInd = 0;
				}
			}
			boolean capturePasOK = true;
			if(currInd == TROUS * RANGEES - 1){
				for(int i = currInd; i >= 6; i--){
					capturePasOK &= (this.plateau[i] == 2 || this.plateau[i] == 3);
				}
			}
			while(currInd < TROUS * RANGEES && currInd >= 6 && (this.plateau[currInd] == 2 || this.plateau[currInd] == 3)){
				this.nbPointBlanc += this.plateau[currInd];
				currInd--;
				if(!capturePasOK){
					this.plateau[currInd] = 0;
				}
			}
		}else{
			int currInd = cA.getIndiceTrou();
			int nbGraine = this.plateau[currInd];
			this.plateau[currInd] = 0;
			while(nbGraine > 0){
				if(currInd == cA.getIndiceTrou()){
					currInd++;
				}else{
					this.plateau[currInd] += 1;
					nbGraine--;
					currInd++;
				}
				if(currInd > TROUS * RANGEES){
					currInd = 0;
				}
			}
			boolean capturePasOK = true;
			if(currInd == 5){
				for(int i = currInd; i >= 0; i--){
					capturePasOK &= (this.plateau[i] == 2 || this.plateau[i] == 3);
				}
			}
			while(currInd >= 0 && currInd < 6 && (this.plateau[currInd] == 2 || this.plateau[currInd] == 3)){
				this.nbPointBlanc += this.plateau[currInd];
				currInd--;
				if(!capturePasOK){
					this.plateau[currInd] = 0;
				}
			}
		}
	}

	public boolean finDePartie() {
		if(this.gainsJoueurBlanc==25 || this.gainsJoueurNoir==25){
			return true;
		}
		int compteur=0;
		for(int i=0; i < this.plateau.length; i++){
			compteur += this.plateau[i];
		}
		if(compteur <= 6) return true;
		else return false;
	}

	public PlateauJeu copy() {
		// TODO Auto-generated method stub
		return new PlateauAwale(this.plateau);
	}

	public boolean coupValide(Joueur j, CoupJeu c) {
		CoupAwale cA = (CoupAwale) c;
		if(j.equals(this.joueurBlanc)){
			return cA.getIndiceTrou() < 6 && this.plateau[cA.getIndiceTrou()] > 0;
		}else{
			return cA.getIndiceTrou() >= 6 && this.plateau[cA.getIndiceTrou()] > 0;
		}
	}

}
