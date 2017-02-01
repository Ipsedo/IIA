package jeux.dominos;

import iia.jeux.alg.AlgoJeu;
import iia.jeux.alg.AlphaBeta;
import iia.jeux.alg.Minimax;
import iia.jeux.alg.NegAlphaBeta;
import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

import java.util.ArrayList;
import java.util.Scanner;

public class PartieDominosRealPlayer {
	public static void main(String[] args) {

        Joueur jBlanc = new Joueur("Blanc");
        Joueur jNoir = new Joueur("Player");

        Joueur[] lesJoueurs = new Joueur[2];
        
        lesJoueurs[0] = jBlanc;
        lesJoueurs[1] = jNoir;
        
        
        AlgoJeu algoIA = new AlphaBeta(HeuristiquesDominos.hblanc, jBlanc, jNoir); // Il faut remplir la méthode !!!

        System.out.println("TD IIA n.3 - Algorithmes pour les Jeux");
        System.out.println("Etat Initial du plateau de jeu:");

        boolean jeufini = false;
        CoupJeu meilleurCoup = null;
        int jnum;

        PlateauJeu plateauCourant = new PlateauDominos();
        PlateauDominos.setJoueurs(jBlanc, jNoir);
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
            if (lesCoupsPossibles.size() > 0) {
                // On écrit le plateau

            	if(jnum == 0){
                // Lancement de l'algo de recherche du meilleur coup
            		System.out.println("Recherche du meilleur coup avec l'algo " + algoIA);
            		meilleurCoup = algoIA.meilleurCoup(plateauCourant);
            		System.out.println("Coup joué : " + meilleurCoup + " par le joueur " + jBlanc);

            		plateauCourant.joue(jBlanc, meilleurCoup);
            		// Le coup est effectivement joué
            	}else{
            		Scanner reader = new Scanner(System.in);
            		System.out.println("Entrez une ligne [0;6] ");
            		int l = reader.nextInt();
            		System.out.println("Entrez une colonne [0;6]");
            		int c = reader.nextInt();
            		while(!plateauCourant.coupValide(lesJoueurs[jnum], new CoupDominos(l,c))){
            			System.out.println("Entrez une ligne et une colonne [0;6] !");
            			l = reader.nextInt();
            			c = reader.nextInt();
            		}
            		plateauCourant.joue(lesJoueurs[jnum], new CoupDominos(l,c));
            	}
                jnum = 1 - jnum;

            } else {
                System.out.println("Le joueur " + lesJoueurs[jnum] + " ne peut plus jouer et abandone !");
                System.out.println("Le joueur " + lesJoueurs[1 - jnum] + " a gagné cette partie !");
                jeufini = true;

            }
        }
    }
}
