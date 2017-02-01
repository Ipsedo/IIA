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

        Joueur[] lesJoueurs = new Joueur[2];
        
        lesJoueurs[0] = jBlanc;
        lesJoueurs[1] = jNoir;
        
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
            System.out.println("C'est au joueur " + lesJoueurs[jnum] + " de jouer.");
            // Vérifie qu'il y a bien des coups possibles
            // Ce n'est pas tres efficace, mais c'est plus rapide... a écrire...
            ArrayList<CoupJeu> lesCoupsPossibles = plateauCourant.coupsPossibles(lesJoueurs[jnum]);
            System.out.println("Coups possibles pour" + lesJoueurs[jnum] + " : " + lesCoupsPossibles);
            if (!plateauCourant.finDePartie()) {
                // On écrit le plateau
            	if(jnum == 0){
            		// Lancement de l'algo de recherche du meilleur coup
            		System.out.println("Recherche du meilleur coup avec l'algo " + lesJoueurs[jnum]);
            		meilleurCoup = algoIA.meilleurCoup(plateauCourant);
            		System.out.println("Coup joué : " + meilleurCoup + " par le joueur " + lesJoueurs[jnum]);

            		plateauCourant.joue(lesJoueurs[jnum], meilleurCoup);
            		// Le coup est effectivement joué
            	}else{
            		Scanner reader = new Scanner(System.in);
                	System.out.println("Entrez une case [0;5] : ");
                	int n = reader.nextInt();
                	while(n < 0 || n > 5 || !plateauCourant.coupValide(lesJoueurs[jnum], new CoupAwale(n))){
                		System.out.println("Entrez une case valide !");
                		n = reader.nextInt();
                	}
                	System.out.println("Coup joué par "+lesJoueurs[jnum]+" : "+new CoupAwale(n));
                	plateauCourant.joue(jNoir, new CoupAwale(n));
            	}
                jnum = 1 - jnum;

            } else {
                System.out.println("Plateau Final");
                System.out.println(plateauCourant);
                jeufini = true;

            }
        }
	}
}
