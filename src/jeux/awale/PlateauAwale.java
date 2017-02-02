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
		ArrayList<CoupJeu> listeCoups = new ArrayList<CoupJeu>();
		if(j.equals(PlateauAwale.joueurBlanc)){
			if(this.famine(joueurNoir)){
				for(int i = 0; i < TROUS; i++){
					if(this.coupValide(j, new CoupAwale(i)) && this.plateau[i] + i >= TROUS){
						listeCoups.add(new CoupAwale(i));
					}
				}
			}
			if(listeCoups.isEmpty()){
				for(int i = 0; i < TROUS; i++){
					if(this.coupValide(j, new CoupAwale(i))){
						listeCoups.add(new CoupAwale(i));
					}
				}
			}		
		}else{
			if(this.famine(joueurBlanc)){
				for(int i = TROUS; i < TROUS * RANGEES; i++){
					if(this.coupValide(j, new CoupAwale(i)) && this.plateau[i] + i >= TROUS * RANGEES){
						listeCoups.add(new CoupAwale(i));
					}
				}
			}
			if(listeCoups.isEmpty()){
				for(int i = TROUS; i < TROUS * RANGEES; i++){
					if(this.coupValide(j, new CoupAwale(i))){
						listeCoups.add(new CoupAwale(i));
					}
				}
			}
		}		
		return listeCoups;
	}

	public void joue(Joueur j, CoupJeu c) {
		CoupAwale cA = (CoupAwale) c;
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
		if(j.equals(PlateauAwale.joueurBlanc)){
			if(!this.capturePasOk(PlateauAwale.joueurBlanc, currInd)){
				while(currInd < TROUS * RANGEES && currInd >= TROUS && (this.plateau[currInd] == 2 || this.plateau[currInd] == 3)){
					this.gainsJoueurBlanc += this.plateau[currInd];
					this.plateau[currInd] = 0;
					currInd--;
				}
			}
			if(this.famine(joueurNoir)){
				for(int i=0; i < TROUS * RANGEES; i++){
					this.gainsJoueurNoir += this.plateau[i];
					this.plateau[i] = 0;
				}
			}
		}else{
			if(!this.capturePasOk(PlateauAwale.joueurNoir, currInd)){
				while(currInd >= 0 && currInd < TROUS && (this.plateau[currInd] == 2 || this.plateau[currInd] == 3)){
					this.gainsJoueurNoir += this.plateau[currInd];
					this.plateau[currInd] = 0;
					currInd--;
				}
			}
			if(this.famine(joueurBlanc)){
				for(int i=0; i < TROUS * RANGEES; i++){
					this.gainsJoueurBlanc += this.plateau[i];
					this.plateau[i] = 0;
				}
			}
		}
	}
	
	private boolean famine(Joueur j){
		boolean famine = true;
		if(j.equals(PlateauAwale.joueurBlanc)){
			for(int i=0; i < TROUS; i++){
				famine &= this.plateau[i] == 0;
			}
		}else{
			for(int i = TROUS; i < TROUS * RANGEES; i++){
				famine &= this.plateau[i] == 0;
			}
		}
		return famine;
	}
	
	private boolean capturePasOk(Joueur j, int currInd){
		boolean res = true;
		if(j.equals(joueurBlanc)){
			for(int i = currInd; i >= TROUS; i--){
				res &= (this.plateau[i] == 2 || this.plateau[i] == 3);
			}
			return res && currInd == TROUS * RANGEES - 1;
		}else{
			for(int i = currInd; i >= 0; i--){
				res &= (this.plateau[i] == 2 || this.plateau[i] == 3);
			}
			return res && currInd == TROUS - 1;
		}
	}

	public boolean finDePartie() {
		int compteur = 0;
		for(int i = 0; i < this.plateau.length; i++){
			compteur += this.plateau[i];
		}
		return this.gainsJoueurNoir >= 25 || this.gainsJoueurBlanc >= 25 || compteur <= 6;
	}

	public PlateauJeu copy() {
		return new PlateauAwale(this.plateau);
	}

	public boolean coupValide(Joueur j, CoupJeu c) {
		CoupAwale cA = (CoupAwale) c;
		if(j.equals(PlateauAwale.joueurBlanc)){
			return cA.getIndiceTrou() >= 0 && cA.getIndiceTrou() < TROUS && this.plateau[cA.getIndiceTrou()] > 0;
		}else{
			return cA.getIndiceTrou() < TROUS * RANGEES && cA.getIndiceTrou() >= TROUS && this.plateau[cA.getIndiceTrou()] > 0;
		}
	}
	
	public int getNbTrousPrenableBlanc(){
		int nbRes = 0;
		for(int i = 0; i < TROUS; i++){
			nbRes += (this.plateau[i] == 1 || this.plateau[i] == 2) ? 1 : 0;
		}
		return nbRes;
	}
	
	public int getNbTrousPrenableNoir(){
		int nbRes = 0;
		for(int i = TROUS; i < TROUS * RANGEES; i++){
			nbRes += (this.plateau[i] == 1 || this.plateau[i] == 2) ? 1 : 0;
		}
		return nbRes;
	}
	
	public int getNbBarrageBlanc(){
		int res = 0;
		for(int i=0; i < TROUS; i++){
			res += this.plateau[i] == 0 ? 1 : 0;
		}
		return res;
	}
	
	public int getNbBarrageNoir(){
		int res = 0;
		for(int i=TROUS; i < TROUS * RANGEES; i++){
			res += this.plateau[i] == 0 ? 1 : 0;
		}
		return res;
	}
	
	public int getPointBlanc(){
		return this.gainsJoueurBlanc;
	}
	
	public int getPointNoir(){
		return this.gainsJoueurNoir;
	}
	
	public String toString(){
		String res = "-------------------\n|";
		for(int i = TROUS * RANGEES - 1; i >= TROUS; i--){
			res += (this.plateau[i] > 9 ? this.plateau[i] : "0"+this.plateau[i])+"|";
		}
		res += "\n-------------------";
		res += "\n|";
		for(int i=0; i < TROUS; i++){
			res += (this.plateau[i] > 9 ? this.plateau[i] : "0"+this.plateau[i])+"|";
		}
		res += "\n-------------------\nBlanc : "+this.gainsJoueurBlanc+", Noir : "+this.gainsJoueurNoir+"\n";
		return res;
	}
}
