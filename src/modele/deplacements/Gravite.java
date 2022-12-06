package modele.deplacements;

import modele.plateau.Entite;
import modele.plateau.EntiteDynamique;

public class Gravite extends RealisateurDeDeplacement {
    @Override
    public boolean realiserDeplacement() {
        boolean ret = false;

        for (EntiteDynamique e : lstEntitesDynamiques) {
            Entite eBas = e.regarderDansLaDirection(Direction.bas);
            //Permet de voir ce qui ce trouve sous le joueur

            if (eBas == null || (eBas != null && ((!eBas.peutServirDeSupport() && !eBas.peutPermettreDeMonterDescendre()) || eBas.objetPeutEtreRamassable()))) {

                if (e.avancerDirectionChoisie(Direction.bas))
                    ret = true;
            }
        }

        return ret;
    }
}
