package modele.deplacements;
import java.util.LinkedList;


/**
 * A la reception d'une commande, toutes les cases (EntitesDynamiques) des colonnes se déplacent dans la direction définie
 * (vérifier "collisions" avec le héros)
 */
public class Colonne extends RealisateurDeDeplacement {
    protected boolean realiserDeplacement() { return true; } 
}
