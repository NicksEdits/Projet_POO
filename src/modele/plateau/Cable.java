package modele.plateau;

public class Cable extends EntiteStatique {
    public Cable(Jeu _jeu) { super(_jeu); }
    
    public int getType(){return 0;}    
    @Override
    public boolean peutPermettreDeMonterDescendre() { return true; }
    @Override
    public boolean peutServirDeSupport() { return false; }
}
