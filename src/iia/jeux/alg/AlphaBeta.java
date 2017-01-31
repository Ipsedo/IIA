package iia.jeux.alg;

import java.util.ArrayList;

import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class AlphaBeta implements AlgoJeu {

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
    
    public AlphaBeta(Heuristique h, Joueur joueurMax, Joueur joueurMin) {
        this(h,joueurMax,joueurMin,PROFMAXDEFAUT);
    }

    public AlphaBeta(Heuristique h, Joueur joueurMax, Joueur joueurMin, int profMaxi) {
        this.h = h;
        this.joueurMin = joueurMin;
        this.joueurMax = joueurMax;
        profMax = profMaxi;
    }
    
    public String toString() {
        return "AlphaBeta(ProfMax="+profMax+")";
    }
	
	public CoupJeu meilleurCoup(PlateauJeu p) {
		this.nbfeuilles = 0;
		   this.nbnoeuds = 0;
		   ArrayList<CoupJeu> coupsPossibles = p.coupsPossibles(this.joueurMax);
		   int alpha = Integer.MIN_VALUE;
		   int beta = Integer.MAX_VALUE;
		   PlateauJeu tmpP = p.copy();
		   CoupJeu meilleurCoup = coupsPossibles.get(0);
		   coupsPossibles.remove(0);
		   tmpP.joue(this.joueurMax, meilleurCoup);
		   alpha = this.minMax(this.profMax - 1, tmpP, alpha, beta);
		   
		   for(CoupJeu c : coupsPossibles){
			   tmpP = p.copy();
			   tmpP.joue(this.joueurMax, c);
			   int newVal = this.minMax(this.profMax - 1, tmpP, alpha, beta);
			   if(newVal > alpha){
				   meilleurCoup = c;
				   alpha = newVal;
			   }
		   }
		   
		   System.out.println("nbFeuilles : "+this.nbfeuilles+", nbNoeuds : "+this.nbnoeuds);
		   
	       return meilleurCoup;
	}
	
	private int maxMin(int pronf, PlateauJeu p, int alpha, int beta){
    	if(pronf == 0 || p.finDePartie()){
    		this.nbfeuilles++;
    		return this.h.eval(p, this.joueurMax);
    	}else{
    		for(CoupJeu c : p.coupsPossibles(this.joueurMax)){
    			this.nbnoeuds++;
    			PlateauJeu tmp = p.copy();
    			tmp.joue(this.joueurMax, c);
    			alpha = Math.max(alpha, this.minMax(pronf - 1, tmp, alpha, beta));
    			if(alpha >= beta){
    				return beta;
    			}
    		}
    	}
    	return alpha;
    }
    
    private int minMax(int pronf, PlateauJeu p, int alpha, int beta){
    	if(pronf == 0 || p.finDePartie()){
    		this.nbfeuilles++;
    		return this.h.eval(p, this.joueurMin);
    	}else{
    		for(CoupJeu c : p.coupsPossibles(this.joueurMin)){
    			this.nbnoeuds++;
    			PlateauJeu tmp = p.copy();
    			tmp.joue(this.joueurMin, c);
    			beta = Math.min(beta, this.maxMin(pronf - 1, tmp, alpha, beta));
    			if(alpha >= beta){
    				return alpha;
    			}
    		}
    	}
    	return beta;
    }

}
