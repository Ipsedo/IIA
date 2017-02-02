package jeux.awale;

import iia.jeux.alg.Heuristique;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class HeuristiquesAwale {

	
	public static  Heuristique hblanc = new Heuristique(){
		
		public int eval(PlateauJeu p, Joueur j){
			PlateauAwale tmpP = (PlateauAwale) p;
			return tmpP.getPointBlanc() - tmpP.getPointNoir() + tmpP.getNbTrousPrenableNoir() - tmpP.getNbTrousPrenableBlanc() + tmpP.getNbBarrageBlanc() - tmpP.getNbBarrageNoir();
		}
	};

	public static  Heuristique hnoir = new Heuristique(){
	
		public int eval(PlateauJeu p, Joueur j){
			PlateauAwale tmpP = (PlateauAwale) p;
			return tmpP.getPointNoir() - tmpP.getPointBlanc() + tmpP.getNbTrousPrenableBlanc() - tmpP.getNbTrousPrenableNoir() + tmpP.getNbBarrageNoir() - tmpP.getNbBarrageBlanc();
		}
	};
}
