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
	private int gainsJoueurBlanc;

	/** Le joueur qui joue "dans la deuxième rangée" */
	private static Joueur joueurNoir;
	private int gainsJoueurNoir;
	
	private int[] plateau = new int[RANGEES * TROUS];
	
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
	
	public static void setJoueurs(Joueur j1, Joueur j2){
		joueurBlanc = j1;
		joueurNoir = j2;
	}

	public ArrayList<CoupJeu> coupsPossibles(Joueur j) {
		// TODO Auto-generated method stub
	
		ArrayList<CoupJeu> listeCoups = new ArrayList<CoupJeu>();
		
		if(j.equals(PlateauAwale.joueurBlanc)){
			boolean famine = true;
			for(int i=6; i<TROUS * RANGEES; i++){
				famine &= this.plateau[i] == 0;
			}
			if(famine){
				for(int i = 0; i < 6; i++){
					if(this.coupValide(j, new CoupAwale(i)) && this.plateau[i] + i > 5){
						listeCoups.add(new CoupAwale(i));
					}
				}
				if(listeCoups.size() == 0){
					for(int i=0; i<6; i++){
						if(this.coupValide(j, new CoupAwale(i))){
							listeCoups.add(new CoupAwale(i));
						}
					}
				}
			}else{
				for(int i=0; i<6; i++){
					if(this.coupValide(j, new CoupAwale(i))){
						listeCoups.add(new CoupAwale(i));
					}
				}
			}		
		}else{
			boolean famine = true;
			for(int i=0; i<6; i++){
				famine &= this.plateau[i] == 0;
			}
			if(famine){
				for(int i = 6; i < TROUS * RANGEES; i++){
					if(this.coupValide(j, new CoupAwale(i)) && this.plateau[i] + i > TROUS * RANGEES){
						listeCoups.add(new CoupAwale(i));
					}
				}
				if(listeCoups.size() == 0){
					for(int i = 6; i < TROUS * RANGEES; i++){
						if(this.coupValide(j, new CoupAwale(i))){
							listeCoups.add(new CoupAwale(i));
						}
					}
				}
			}else{
				for(int i = 6; i < TROUS * RANGEES; i++){
					if(this.coupValide(j, new CoupAwale(i))){
						listeCoups.add(new CoupAwale(i));
					}
				}
			}
		}		
		return listeCoups;
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
					if(nbGraine !=0 ){
						currInd++;
					}
				}
				if(currInd >= TROUS * RANGEES){
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
				if(!capturePasOK){
					this.gainsJoueurBlanc += this.plateau[currInd];
					this.plateau[currInd] = 0;
				}
				currInd--;
			}
			boolean famine = true;
			for(int i=6; i<TROUS * RANGEES; i++){
				famine &= this.plateau[i] == 0;
			}
			if(famine){
				for(int i=0; i<TROUS * RANGEES; i++){
					this.gainsJoueurNoir += this.plateau[i];
					this.plateau[i] = 0;
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
					if(nbGraine !=0 ){
						currInd++;
					}
				}
				if(currInd >= TROUS * RANGEES){
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
				if(!capturePasOK){
					this.gainsJoueurBlanc += this.plateau[currInd];
					this.plateau[currInd] = 0;
				}
				currInd--;
			}
			boolean famine = true;
			for(int i=0; i<6; i++){
				famine &= this.plateau[i] == 0;
			}
			if(famine){
				for(int i=0; i<TROUS * RANGEES; i++){
					this.gainsJoueurBlanc += this.plateau[i];
					this.plateau[i] = 0;
				}
			}
		}
	}

	public boolean finDePartie() {
		if(this.gainsJoueurBlanc >= 25 || this.gainsJoueurBlanc >= 25){
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
		if(j.equals(PlateauAwale.joueurBlanc)){
			return cA.getIndiceTrou() >= 0 && cA.getIndiceTrou() < 6 && this.plateau[cA.getIndiceTrou()] > 0;
		}else{
			return cA.getIndiceTrou() < TROUS * RANGEES && cA.getIndiceTrou() >= 6 && this.plateau[cA.getIndiceTrou()] > 0;
		}
	}
	
	public int getNbTrousPrenableBlanc(){
		int nbRes = 0;
		for(int i = 0; i < 6; i++){
			nbRes = (this.plateau[i] == 1 || this.plateau[i] == 2) ? nbRes + 1 : nbRes;
		}
		return nbRes;
	}
	
	public int getNbTrousPrenableNoir(){
		int nbRes = 0;
		for(int i = 6; i < TROUS * RANGEES; i++){
			nbRes = (this.plateau[i] == 1 || this.plateau[i] == 2) ? nbRes + 1 : nbRes;
		}
		return nbRes;
	}
	
	public int getPointBlanc(){
		return this.gainsJoueurBlanc;
	}
	
	public int getPointNoir(){
		return this.gainsJoueurNoir;
	}
	
	public String toString(){
		String res = "|";
		for(int i = TROUS * RANGEES - 1; i >= 6; i--){
			res += (this.plateau[i] > 9 ? this.plateau[i] : "0"+this.plateau[i])+"|";
		}
		res += "\n-------------------";
		res += "\n|";
		for(int i=0; i<6; i++){
			res += (this.plateau[i] > 9 ? this.plateau[i] : "0"+this.plateau[i])+"|";
		}
		res += "\nBlanc : "+this.gainsJoueurBlanc+", Noir : "+this.gainsJoueurNoir+"\n";
		return res;
	}
}
