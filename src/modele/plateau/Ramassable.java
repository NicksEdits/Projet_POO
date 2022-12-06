package modele.plateau;

public class Ramassable extends EntiteStatique {
    private int type;
    //Bombe de type 1
    //Radit de type 2
    public Ramassable(Jeu _jeu , int type) { super(_jeu); 
        this.type = type;
    }

    public int getType(){
        return type;
    }
    @Override
    public boolean objetPeutEtreRamassable() { return true; }
    @Override
    public boolean peutServirDeSupport() { return false; }
    @Override
    public boolean peutPermettreDeMonterDescendre() { return false; }
    public boolean estUnRadit(){ return type == 2;}
}
