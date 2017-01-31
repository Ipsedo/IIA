package jeux.awale;

import iia.jeux.alg.Heuristique;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class HeuristiquesAwale {

	
	public static  Heuristique hblanc = new Heuristique(){
		
		public int eval(PlateauJeu p, Joueur j){
			PlateauAwale tmpP = (PlateauAwale) p;
			return tmpP.getPointBlanc() + tmpP.getNbTrousPrenableNoir() - tmpP.getNbTrousPrenableBlanc();
		}
	};

	public static  Heuristique hnoir = new Heuristique(){
	
		public int eval(PlateauJeu p, Joueur j){
			PlateauAwale tmpP = (PlateauAwale) p;
			return tmpP.getPointNoir() + tmpP.getNbTrousPrenableBlanc() - tmpP.getNbTrousPrenableNoir();
		}
	};
}
