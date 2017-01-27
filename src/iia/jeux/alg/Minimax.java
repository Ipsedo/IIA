/**
 * 
 */

package iia.jeux.alg;

import java.util.ArrayList;

import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class Minimax implements AlgoJeu {

    /** La profondeur de recherche par défaut
     */
    private final static int PROFMAXDEFAUT = 4;

   
    // -------------------------------------------
    // Attributs
    // -------------------------------------------
 
    /**  La profondeur de recherche utilisée pour l'algorithme
     */
    private int profMax = PROFMAXDEFAUT;

     /**  L'heuristique utilisée par l'algorithme
      */
   private Heuristique h;

    /** Le joueur Min
     *  (l'adversaire) */
    private Joueur joueurMin;

    /** Le joueur Max
     * (celui dont l'algorithme de recherche adopte le point de vue) */
    private Joueur joueurMax;

    /**  Le nombre de noeuds développé par l'algorithme
     * (intéressant pour se faire une idée du nombre de noeuds développés) */
       private int nbnoeuds;

    /** Le nombre de feuilles évaluées par l'algorithme
     */
    private int nbfeuilles;


  // -------------------------------------------
  // Constructeurs
  // -------------------------------------------
    public Minimax(Heuristique h, Joueur joueurMax, Joueur joueurMin) {
        this(h,joueurMax,joueurMin,PROFMAXDEFAUT);
    }

    public Minimax(Heuristique h, Joueur joueurMax, Joueur joueurMin, int profMaxi) {
        this.h = h;
        this.joueurMin = joueurMin;
        this.joueurMax = joueurMax;
        profMax = profMaxi;
//		System.out.println("Initialisation d'un MiniMax de profondeur " + profMax);
    }

   // -------------------------------------------
  // Méthodes de l'interface AlgoJeu
  // -------------------------------------------
   public CoupJeu meilleurCoup(PlateauJeu p) {
	   this.nbfeuilles = 0;
	   this.nbnoeuds = 0;
	   ArrayList<CoupJeu> coupsPossibles = p.coupsPossibles(this.joueurMax);
	   
	   PlateauJeu tmpP = p.copy();
	   CoupJeu meilleurCoup = coupsPossibles.get(0);
	   coupsPossibles.remove(0);
	   tmpP.joue(this.joueurMax, meilleurCoup);
	   int max = this.minMax(this.profMax - 1, tmpP);
	   
	   for(CoupJeu c : coupsPossibles){
		   tmpP = p.copy();
		   tmpP.joue(this.joueurMax, c);
		   int newVal = this.minMax(this.profMax - 1, tmpP);
		   if(newVal > max){
			   meilleurCoup = c;
			   max = newVal;
		   }
	   }
	   
	   System.out.println("nbFeuilles : "+this.nbfeuilles+", nbNoeuds : "+this.nbnoeuds);
	   
        /* A vous de compléter le corps de ce fichier */
	   
        return meilleurCoup;

    }
  // -------------------------------------------
  // Méthodes publiques
  // -------------------------------------------
    public String toString() {
        return "MiniMax(ProfMax="+profMax+")";
    }



  // -------------------------------------------
  // Méthodes internes
  // -------------------------------------------

    //A vous de jouer pour implanter Minimax
    
    private int maxMin(int pronf, PlateauJeu p){
    	if(pronf == 0 || p.finDePartie()){
    		this.nbfeuilles++;
    		return this.h.eval(p, this.joueurMax);
    	}else{
    		int max = Integer.MIN_VALUE;
    		for(CoupJeu c : p.coupsPossibles(this.joueurMax)){
    			this.nbnoeuds++;
    			PlateauJeu tmp = p.copy();
    			tmp.joue(this.joueurMax, c);
    			max = Math.max(max, this.minMax(pronf - 1, tmp));
    		}
    		return max;
    	}
    }
    
    private int minMax(int pronf, PlateauJeu p){
    	if(pronf == 0 || p.finDePartie()){
    		this.nbfeuilles++;
    		return this.h.eval(p, this.joueurMin);
    	}else{
    		int min = Integer.MAX_VALUE;
    		for(CoupJeu c : p.coupsPossibles(this.joueurMin)){
    			this.nbnoeuds++;
    			PlateauJeu tmp = p.copy();
    			tmp.joue(this.joueurMin, c);
    			min = Math.min(min, this.maxMin(pronf - 1, tmp));
    		}
    		return min;
    	}
    }

}
