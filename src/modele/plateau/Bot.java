/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import java.util.Random;

/**
 * Ennemis (Smicks)
 */
public class Bot extends EntiteDynamique {
    public Random r = new Random();

    public Bot(Jeu _jeu) {
        super(_jeu);
    }
    
    public  int getType(){return 0;}
    @Override
    public boolean jouable(){return false;}
    public boolean peutEtreEcrase() { return true; }
    public boolean peutServirDeSupport() { return true; }
    public boolean peutPermettreDeMonterDescendre() { return false; }
    public boolean objetPeutEtreRamassable(){ return false; }
    public boolean peutMourir(){ return false;}
    public  boolean peutTuer(){ return true;}

}
