package modele.deplacements;
import modele.plateau.Colonne;
import modele.plateau.Entite;
import modele.plateau.EntiteDynamique;

import java.util.LinkedList;

/**
 * A la reception d'une commande, toutes les cases (EntitesDynamiques) des colonnes se déplacent dans la direction définie
 * (vérifier "collisions" avec le héros)
 */
public class ColonneDeplacement extends RealisateurDeDeplacement {

    private Direction directionCourante = Direction.droite;
    private LinkedList<Colonne> colonneEntière;

    public ColonneDeplacement() {
        this.directionCourante = Direction.haut;
        this.colonneEntière = new LinkedList<>();
    }

    public void addColonne(Colonne c){
        this.colonneEntière.add(c);
    }

    protected boolean realiserDeplacement() {

        boolean ret = false;

        if(this.directionCourante == Direction.haut){

            //Pour monter, on verifier que la première colonne peut monter, si oui on effectue le déplacement
            // Si non on inverse la direction

            Entite eCible = this.colonneEntière.getFirst().regarderDansLaDirection(directionCourante);

            if(eCible != null && eCible.peutMourir()){

                for(int i = 0; i < colonneEntière.size(); i++){

                    if(eCible instanceof EntiteDynamique){
                        ((EntiteDynamique) eCible).avancerDirectionChoisie(directionCourante);
                    }
                    this.colonneEntière.get(i).avancerDirectionChoisie(directionCourante);
                }
                ret = true;
            }
            else if(eCible != null && !eCible.peutMourir() ){
                this.directionCourante = Direction.bas;
            }else{
                for(int i = 0; i < colonneEntière.size(); i++){
                    this.colonneEntière.get(i).avancerDirectionChoisie(directionCourante);
                }
                ret = true;

            }

        }else if(this.directionCourante == Direction.bas){

            Entite eCible = this.colonneEntière.getLast().regarderDansLaDirection(directionCourante);

            if(eCible != null && !eCible.peutMourir()){
                this.directionCourante = Direction.haut;
            }else{
                for(int i = colonneEntière.size()-1; i >= 0; i--){
                    colonneEntière.get(i).avancerDirectionChoisie(directionCourante);
                    ret = true;
                }
            }
        }

        return true; }
}
