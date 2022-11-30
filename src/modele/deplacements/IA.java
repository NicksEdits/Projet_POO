package modele.deplacements;

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

                        if (eGauche == null) {
                            if (e.avancerDirectionChoisie(directionCourante))
                                ret = true;
                            break;
                        } else {
                            directionCourante = Direction.droite;
                        }

                    case droite:
                        Entite eDroite = e.regarderDansLaDirection(Direction.droite);

                        if (eDroite == null) {
                            if (e.avancerDirectionChoisie(directionCourante))
                                ret = true;
                            break;
                        } else {
                            directionCourante = Direction.gauche;
                        }
                }
            }
        }

        return ret;
    } // TODO
}
