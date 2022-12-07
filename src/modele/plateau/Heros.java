/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

/**
 * Héros du jeu
 */   

public class Heros extends EntiteDynamique {
    private int typejoueur;
    public Heros(Jeu _jeu, int typejoueur) {
        super(_jeu);
        this.typejoueur = typejoueur;
    }
    public  int getType(){
        return typejoueur;
    }


    public boolean peutEtreEcrase() { return true; }
    public boolean peutServirDeSupport() { return false; }
    public boolean peutPermettreDeMonterDescendre() { return false; }
    public boolean objetPeutEtreRamassable() { return false; }

    public boolean peutMourir(){ return true;}
    public  boolean peutTuer(){ return false;}
    public boolean jouable(){ return true;}
}
