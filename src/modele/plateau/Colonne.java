package modele.plateau;

public class Colonne extends EntiteDynamique {
    private int type;

    public Colonne(Jeu _jeu,int type) { super(_jeu); 
        this.type = type;}
        public int getType(){
            return type;
        }

    public boolean peutEtreEcrase() { return false; }
    public boolean peutServirDeSupport() { return true; }
    public boolean peutPermettreDeMonterDescendre() { return false; }
    public boolean objetPeutEtreRamassable () { return false; }
}
