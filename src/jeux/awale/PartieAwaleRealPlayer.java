package jeux.awale;

import iia.jeux.alg.AlgoJeu;
import iia.jeux.alg.AlphaBeta;
import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

import java.util.ArrayList;
import java.util.Scanner;

public class PartieAwaleRealPlayer {
	public static void main(String[] args) {

        Joueur jBlanc = new Joueur("Blanc");
        Joueur jNoir = new Joueur("Player");
        
        AlgoJeu algoIA = new AlphaBeta(HeuristiquesAwale.hblanc, jBlanc, jNoir); // Il faut remplir la méthode !!!

        System.out.println("TD IIA n.4 - Algorithmes pour les Jeux");
        System.out.println("Etat Initial du plateau de jeu:");

        boolean jeufini = false;
        CoupJeu meilleurCoup = null;
        int jnum;

        PlateauJeu plateauCourant = new PlateauAwale();
        PlateauAwale.setJoueurs(jNoir, jBlanc);
        // Pour savoir qui joue "noir" et qui joue "blanc"


        // A chaque itération de la boucle, on fait jouer un des deux joueurs
        // tour a tour
        jnum = 0; // On commence par le joueur Blanc (arbitraire)

        while (!jeufini) {
            System.out.println("" + plateauCourant);
            System.out.println("C'est au joueur " + (jnum == 0 ? jBlanc : jNoir) + " de jouer.");
            // Vérifie qu'il y a bien des coups possibles
            // Ce n'est pas tres efficace, mais c'est plus rapide... a écrire...
            ArrayList<CoupJeu> lesCoupsPossibles = plateauCourant.coupsPossibles((jnum == 0 ? jBlanc : jNoir));
            System.out.println("Coups possibles pour" + (jnum == 0 ? jBlanc : jNoir) + " : " + lesCoupsPossibles);
            if (lesCoupsPossibles.size() > 0 && !plateauCourant.finDePartie() && jnum ==0 ) {
                // On écrit le plateau

                // Lancement de l'algo de recherche du meilleur coup
                System.out.println("Recherche du meilleur coup avec l'algo " + jBlanc);
                meilleurCoup = algoIA.meilleurCoup(plateauCourant);
                System.out.println("Coup joué : " + meilleurCoup + " par le joueur " + jBlanc);

                plateauCourant.joue(jBlanc, meilleurCoup);
                // Le coup est effectivement joué
                jnum = 1 - jnum;

            } else if(lesCoupsPossibles.size() > 0 && !plateauCourant.finDePartie() && jnum == 1 ){
            	Scanner reader = new Scanner(System.in);
            	System.out.println("Entrez une case [0;5] : ");
            	int n = reader.nextInt();
            	while(n < 0 || n > 5 || !plateauCourant.coupValide(jNoir, new CoupAwale(n))){
            		System.out.println("Entrez une case valide !");
            		n = reader.nextInt();
            	}
            	System.out.println("Coup joué par "+jNoir+" : "+new CoupAwale(n));
            	plateauCourant.joue(jNoir, new CoupAwale(n));
            	jnum = 1 - jnum;
            } else {
                System.out.println("Plateau Final");
                System.out.println(plateauCourant);
                jeufini = true;

            }
        }
	}
}
