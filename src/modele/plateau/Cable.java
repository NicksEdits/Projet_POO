package modele.plateau;

public class Cable extends EntiteStatique {
    public Cable(Jeu _jeu) { super(_jeu); }

    @Override
    public boolean peutPermettreDeMonterDescendre() { return true; }
    @Override
    public boolean peutServirDeSupport() { return true; }
}
