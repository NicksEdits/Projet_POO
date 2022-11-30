/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import modele.deplacements.Controle4Directions;
import modele.deplacements.Direction;
import modele.deplacements.Gravite;
import modele.deplacements.Ordonnanceur;
import modele.deplacements.RealisateurDeDeplacement;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Actuellement, cette classe gère les postions
 * (ajouter conditions de victoire, chargement du plateau, etc.)
 */
public class Jeu {

    public static final int SIZE_X = 30; // Max 100 pour <du 1920
    public static final int SIZE_Y = 20; // Max 43 pour du 1080

    // compteur de déplacements horizontal et vertical (1 max par défaut, à chaque pas de temps)
    private HashMap<Entite, Integer> cmptDeplH = new HashMap<Entite, Integer>();
    private HashMap<Entite, Integer> cmptDeplV = new HashMap<Entite, Integer>();

    private Heros hector;
    private Bot smick;

    private HashMap<Entite, Point> map = new HashMap<Entite, Point>(); // permet de récupérer la position d'une entité à partir de sa référence
    private ArrayList<Entite>[][] grilleEntites = new ArrayList[SIZE_X][SIZE_Y]; // permet de récupérer une entité à partir de ses coordonnées

    private Ordonnanceur ordonnanceur = new Ordonnanceur(this);

    public Jeu() {

        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                grilleEntites[i][j] = new ArrayList<>();
            }
        }

        initialisationDesEntites();

    }

    public void resetCmptDepl() {
        cmptDeplH.clear();
        cmptDeplV.clear();
    }

    public void start(long _pause) {
        ordonnanceur.start(_pause);
    }

    public ArrayList<Entite>[][] getGrille() {
        return grilleEntites;
    }

    public Heros getHector() {
        return hector;
    }

    public Bot getSmick() {
        return smick;
    }

    private void initialisationDesEntites() {
        // hero
        hector = new Heros(this);
        addEntite(hector, 2, 1);
        // smick
        smick = new Bot(this);
        addEntite(smick, 13, 4);
        addEntite(smick, 20, 14);

        // gravité + directions pour Hector
        Gravite g = new Gravite();
        g.addEntiteDynamique(hector);
        ordonnanceur.add(g);

        // gravité + directions pour les smicks
        Gravite s = new Gravite();
        s.addEntiteDynamique(smick);
        ordonnanceur.add(s);

        Controle4Directions.getInstance().addEntiteDynamique(hector);
        ordonnanceur.add(Controle4Directions.getInstance());

        // bombes 
        addEntite(new Ramassable(this, 1), 7, 17);

        // radis

        addEntite(new Ramassable(this, 2), 2, 13);
        addEntite(new Ramassable(this, 2), 5, 7);
        addEntite(new Ramassable(this, 2), 10, 13);


        // murs extérieurs horizontaux
        for (int x = 0; x < SIZE_X; x++) {
            addEntite(new Mur(this), x, 0);
            addEntite(new Mur(this), x, SIZE_Y - 1); //changement de nombre en dur par SIZE_Y-1
        }

        // murs extérieurs verticaux
        for (int y = 1; y < SIZE_Y; y++) {
            addEntite(new Mur(this), 0, y);
            addEntite(new Mur(this), SIZE_X - 1, y);// changement de nombre en dur par SIZE_X-1
        }


        //Platformes
        for (int i = 1; i < 19; i++) {
            addEntite(new Platforme(this, 2), 26, i);
        }
        for (int y = 1; y < 15; y++) {
            addEntite(new Platforme(this, 1), y, 18);
        }
        for (int y = 14; y < 29; y++) {
            addEntite(new Platforme(this, 1), y, 15);
        }
        for (int i = 9; i < 17; i++) {
            addEntite(new Platforme(this, 1), i, 11);
        }
        for (int i = 18; i < 23; i++) {
            addEntite(new Platforme(this, 1), i, 11);
        }
        for (int i = 1; i < 14; i++) {
            addEntite(new Platforme(this, 1), i, 14);
        }
        for (int i = 6; i < 11; i++) {
            addEntite(new Platforme(this, 2), 22, i);
        }
        for (int i = 6; i < 11; i++) {
            addEntite(new Platforme(this, 2), 15, i);
        }
        for (int i = 20; i < 25; i++) {
            addEntite(new Platforme(this, 1), i, 5);
        }
        for (int i = 11; i < 17; i++) {
            addEntite(new Platforme(this, 1), i, 5);
        }
        for (int i = 1; i < 3; i++) {
            addEntite(new Platforme(this, 1), i, 8);
        }
        for (int i = 6; i < 9; i++) {
            addEntite(new Platforme(this, 1), i, 8);
        }
        for (int i = 1; i < 10; i++) {
            addEntite(new Platforme(this, 1), i, 3);
        }
        addEntite(new Platforme(this, 3), 3, 8);
        addEntite(new Platforme(this, 4), 5, 8);
        addEntite(new Platforme(this, 1), 4, 13);
        addEntite(new Platforme(this, 2), 9, 12);
        addEntite(new Platforme(this, 2), 9, 13);
        addEntite(new Platforme(this, 1), 24, 12);
        addEntite(new Platforme(this, 2), 24, 13);
        addEntite(new Platforme(this, 2), 24, 14);
        addEntite(new Platforme(this, 3), 17, 5);
        addEntite(new Platforme(this, 4), 19, 5);
        addEntite(new Platforme(this, 1), 18, 10);
        addEntite(new Platforme(this, 3), 16, 15);
        addEntite(new Platforme(this, 4), 18, 15);
        addEntite(new Platforme(this, 2), 14, 17);
        addEntite(new Platforme(this, 2), 14, 16);
        addEntite(new Platforme(this, 1), 17, 10);

        //Colonnes
        addEntite(new Colonne(this, 2), 18, 1);
        addEntite(new Colonne(this, 1), 18, 2);
        addEntite(new Colonne(this, 1), 18, 3);
        addEntite(new Colonne(this, 1), 18, 4);
        addEntite(new Colonne(this, 3), 18, 5);
        addEntite(new Colonne(this, 2), 4, 8);
        addEntite(new Colonne(this, 1), 4, 9);
        addEntite(new Colonne(this, 1), 4, 10);
        addEntite(new Colonne(this, 1), 4, 11);
        addEntite(new Colonne(this, 3), 4, 12);
        addEntite(new Colonne(this, 2), 17, 11);
        addEntite(new Colonne(this, 1), 17, 14);
        addEntite(new Colonne(this, 1), 17, 13);
        addEntite(new Colonne(this, 1), 17, 12);
        addEntite(new Colonne(this, 3), 17, 15);
        //Cables 
        for (int i = 6; i < 15; i++) {
            addEntite(new Cable(this), 23, i);

        }
        for (int i = 1; i < 15; i++) {
            addEntite(new Cable(this), 25, i);

        }
        for (int i = 9; i < 14; i++) {
            addEntite(new Cable(this), 8, i);

        }
        for (int i = 4; i < 11; i++) {
            addEntite(new Cable(this), 9, i);

        }
        for (int i = 12; i < 18; i++) {
            addEntite(new Cable(this), 13, i);

        }
        for (int i = 12; i < 15; i++) {
            addEntite(new Cable(this), 21, i);

        }
        for (int i = 12; i < 15; i++) {
            addEntite(new Cable(this), 16, i);

        }
    }

    /**
     * Ajoute un élément dans le jeu, est utilisé principalement pour l'initialisation du jeu
     * donc appelé au tout debut
     */

    private void addEntite(Entite e, int x, int y) {


        grilleEntites[x][y].add(e);  //Ici ajouté une liste d'entité
        map.put(e, new Point(x, y));
    }

    /**
     * Permet par exemple a une entité  de percevoir sont environnement proche et de définir sa stratégie de déplacement
     * renvoie simplement ce qu'il y à dans la case à regarder
     */
    public Entite regarderDansLaDirection(Entite e, Direction d) {
        Point positionEntite = map.get(e);
        return objetALaPosition(calculerPointCible(positionEntite, d));
    }

    /**
     * Si le déplacement de l'entité est autorisé (pas de mur ou autre entité), il est réalisé
     * Sinon, rien n'est fait.
     */
    public boolean deplacerEntite(Entite e, Direction d) {
        boolean retour = false;

        Point pCourant = map.get(e);

        Point pCible = calculerPointCible(pCourant, d);     // ex : regarde dans la case ou le joueur veux aller pour voir si c'est possible d'y aller

        if ((contenuDansGrille(pCible) && (objetALaPosition(pCible) == null) || objetALaPosition(pCible).peutPermettreDeMonterDescendre())) { // a adapter (collisions murs, etc.)
            //contenuDansGrille(pCible) vérifie que tu ne sors pas de la map
            //objetALaPosition(pCible)  == null  regarde qu'il n'y ai rien dans la case cible
            // compter le déplacement : 1 deplacement horizontal et vertical max par pas de temps par entité
            switch (d) {
                case bas:
                case haut:
                    if (cmptDeplV.get(e) == null) {
                        cmptDeplV.put(e, 1);

                        retour = true;
                    }
                    break;
                case gauche:
                case droite:
                    if (cmptDeplH.get(e) == null) {
                        cmptDeplH.put(e, 1);
                        retour = true;

                    }
                    break;
            }
        }

        if (retour) {
            deplacerEntite(pCourant, pCible, e);
        }

        return retour;
    }


    private Point calculerPointCible(Point pCourant, Direction d) {
        Point pCible = null;

        switch (d) {
            case haut:
                pCible = new Point(pCourant.x, pCourant.y - 1);
                break;
            case bas:
                pCible = new Point(pCourant.x, pCourant.y + 1);
                break;
            case gauche:
                pCible = new Point(pCourant.x - 1, pCourant.y);
                break;
            case droite:
                pCible = new Point(pCourant.x + 1, pCourant.y);
                break;

        }

        return pCible;
    }

    private void deplacerEntite(Point pCourant, Point pCible, Entite e) {

        grilleEntites[pCourant.x][pCourant.y].remove(e);
        grilleEntites[pCible.x][pCible.y].add(e);
        map.put(e, pCible);
    }

    /**
     * Indique si p est contenu dans la grille
     */
    private boolean contenuDansGrille(Point p) {
        return p.x >= 0 && p.x < SIZE_X && p.y >= 0 && p.y < SIZE_Y;
    }

    private Entite objetALaPosition(Point p) {
        Entite retour = null;

        if (contenuDansGrille(p)) {
            if (!grilleEntites[p.x][p.y].isEmpty()) {
                retour = grilleEntites[p.x][p.y].get(0);
            }
        }

        return retour;
    }

    public Ordonnanceur getOrdonnanceur() {
        return ordonnanceur;
    }
}
