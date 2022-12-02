package modele.deplacements;

import modele.plateau.Bot;
import modele.plateau.Entite;
import modele.plateau.EntiteDynamique;

public class IA extends RealisateurDeDeplacement {

    private Direction directionCourante = Direction.droite;

    protected boolean realiserDeplacement() {


        boolean ret = false;

        if (directionCourante != null) {
            for (EntiteDynamique e : lstEntitesDynamiques) {


                switch (directionCourante) {
                    case gauche:
                        Entite eGauche = e.regarderDansLaDirection(Direction.gauche);

                        if (eGauche == null || eGauche.objetPeutEtreRamassable()) {
                            if (e.avancerDirectionChoisie(directionCourante))
                                ret = true;

                        } else if (eGauche.peutPermettreDeMonterDescendre()) {
                            if (e.avancerDirectionChoisie(directionCourante)) {
                                ret = true;
                                if (e instanceof Bot) {
                                    if (((Bot) e).r.nextBoolean()) {
                                        directionCourante = Direction.haut;         //Aléatoirement le Smick va monter sur la corde
                                    } else if (((Bot) e).r.nextBoolean()) {           //Si il ne monte pas la corde, il va aléatoirement à droite oui a gauche
                                        directionCourante = Direction.droite;
                                    } else {
                                        directionCourante = Direction.gauche;
                                    }
                                }
                            }

                        } else {
                            directionCourante = Direction.droite;
                        }
                        break;

                    case droite:
                        Entite eDroite = e.regarderDansLaDirection(Direction.droite);

                        if (eDroite == null || eDroite.objetPeutEtreRamassable()) {
                            if (e.avancerDirectionChoisie(directionCourante))
                                ret = true;
                        } else if (eDroite.peutPermettreDeMonterDescendre()) {
                            if (e.avancerDirectionChoisie(directionCourante)) {
                                ret = true;
                                if (e instanceof Bot) {
                                    if (((Bot) e).r.nextBoolean()) {
                                        directionCourante = Direction.haut;         //Aléatoirement le Smick va monter sur la corde
                                    } else if (((Bot) e).r.nextBoolean()) {           //Si il ne monte pas la corde, il va aléatoirement à droite oui a gauche
                                        directionCourante = Direction.gauche;
                                    } else {
                                        directionCourante = Direction.droite;
                                    }
                                }
                            }

                        } else {
                            directionCourante = Direction.gauche;
                        }
                        break;

                    case haut:
                        Entite eHaut = e.regarderDansLaDirection(Direction.haut);
                        if (eHaut.peutPermettreDeMonterDescendre()) {
                            if (e.avancerDirectionChoisie(directionCourante)) {
                                ret = true;
                            }
                        } else {
                            if (e instanceof Bot) {
                                if (((Bot) e).r.nextBoolean()) {           //S'il atteint le haut de la corde, il va aléatoirement à droite ou a gauche
                                    directionCourante = Direction.gauche;
                                } else {
                                    directionCourante = Direction.droite;
                                }
                            }
                        }
                        break;
                }
            }
        }

        return ret;
    } // TODO
}
