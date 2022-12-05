package modele.deplacements;

import modele.plateau.Bot;
import modele.plateau.Entite;
import modele.plateau.EntiteDynamique;
import java.awt.Point;
public class IA extends RealisateurDeDeplacement {

    private Direction directionCourante = Direction.droite;

    protected boolean realiserDeplacement() {


        boolean ret = false;

        if (directionCourante != null) {
            for (EntiteDynamique e : lstEntitesDynamiques) {

                Entite eCible = e.regarderDansLaDirection(directionCourante);


                if (eCible != null && (eCible.peutMourir() || eCible.peutTuer())) {
                    e.avancerDirectionChoisie(directionCourante);
                    ret = true;
                }
                

                switch (directionCourante) {
                    case gauche:
                    Entite eGauche = e.regarderDansLaDirection(Direction.gauche);
                   /*Point pCourant = e.getJeu().map.get(e);                                     // tentative d'amelioration de l'IA des smicks en ne les faisant pas sauter
                    Point pCible = e.getJeu().calculerPointCible(pCourant, directionCourante);   // lors de la fin d'une platforme ou d'une corde 
                    Point pCibleBas = e.getJeu().calculerPointCible(pCible, Direction.bas);
                    e.getJeu().objetALaPosition(pCibleBas);*/

                        if ((eGauche == null/*  && e.getJeu().objetALaPosition(pCibleBas) != null*/) || eGauche.objetPeutEtreRamassable()  ) {
                            if (e.avancerDirectionChoisie(directionCourante))
                                ret = true;

                        } else if (eGauche.peutPermettreDeMonterDescendre()) {
                            if (e.avancerDirectionChoisie(directionCourante)) {
                                ret = true;
                                if (e instanceof Bot) {
                                    if (((Bot) e).r.nextBoolean()) {
                                        directionCourante = Direction.haut;         //Aléatoirement le Smick va monter sur la corde
                                    } else if (((Bot) e).r.nextBoolean()/*&& e.getJeu().objetALaPosition(pCibleBas) != null*/) {           //Si il ne monte pas la corde, il va aléatoirement à droite oui a gauche
                                        directionCourante = Direction.droite;
                                    } else /*if (e.getJeu().objetALaPosition(pCibleBas) != null)*/{
                                        directionCourante = Direction.gauche;
                                    }/*else {directionCourante = Direction.bas;}*/
                                }
                            }

                        } else {
                            directionCourante = Direction.droite;
                        }
                        break;

                    case droite:
                    
                        Entite eDroite = e.regarderDansLaDirection(Direction.droite);
                        /*Point Courant = e.getJeu().map.get(e);
                        Point Cible = e.getJeu().calculerPointCible(Courant, directionCourante);
                        Point CibleBas = e.getJeu().calculerPointCible(Cible, Direction.bas);*/


                        if ((eDroite == null /*&& e.getJeu().objetALaPosition(CibleBas) != null*/) || eDroite.objetPeutEtreRamassable() ) {
                            if (e.avancerDirectionChoisie(directionCourante))
                                ret = true;
                        } else if (eDroite.peutPermettreDeMonterDescendre()) {
                            if (e.avancerDirectionChoisie(directionCourante)) {
                                ret = true;
                                if (e instanceof Bot) {
                                    if (((Bot) e).r.nextBoolean()) {
                                        directionCourante = Direction.haut;         //Aléatoirement le Smick va monter sur la corde
                                    } else if (((Bot) e).r.nextBoolean()/*&&  e.getJeu().objetALaPosition(CibleBas) != null*/) {           //Si il ne monte pas la corde, il va aléatoirement à droite oui a gauche
                                        directionCourante = Direction.gauche;
                                    } else /*if (  e.getJeu().objetALaPosition(CibleBas) != null)*/{
                                        directionCourante = Direction.droite;
                                    } /*else  {
                                        directionCourante = Direction.bas;
                                    }*/
                                }
                            }

                        } else {
                            directionCourante = Direction.gauche;
                        }
                        break;

                    case haut:
                        Entite eHaut = e.regarderDansLaDirection(Direction.haut);
                    /*  Point hautCourant = e.getJeu().map.get(e);
                        Point hautCible = e.getJeu().calculerPointCible(hautCourant, directionCourante);
                        Point hautCibleBas = e.getJeu().calculerPointCible(hautCible, Direction.bas);*/
                        if (eHaut.peutPermettreDeMonterDescendre()) {
                            if (e.avancerDirectionChoisie(directionCourante)) {
                                ret = true;
                            }
                        } else {
                            if (e instanceof Bot) {
                                if (((Bot) e).r.nextBoolean()/*&& e.getJeu().objetALaPosition(hautCibleBas) != null*/) {           //S'il atteint le haut de la corde, il va aléatoirement à droite ou a gauche
                                    directionCourante = Direction.gauche;
                                } else /*if (e.getJeu().objetALaPosition(hautCibleBas) != null)*/{
                                    directionCourante = Direction.droite;
                                }/*else {directionCourante = Direction.bas;}*/
                            }
                        }
                        break;
                }
            }
        }

        return ret;
    } // TODO
}
