package modele.plateau;

public class Ramassable extends EntiteStatique {
    private int type;
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




}
