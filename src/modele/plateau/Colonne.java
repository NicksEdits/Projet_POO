package modele.plateau;

public class Colonne extends EntiteDynamique {
    private int type;
    private Colonne suivant;

    public Colonne(Jeu _jeu,int type) { super(_jeu); 
        this.type = type;}
        public int getType(){
            return type;
        }

    public boolean peutEtreEcrase() { return false; }
    public boolean peutServirDeSupport() { return true; }
    public boolean peutPermettreDeMonterDescendre() { return false; }
    public boolean objetPeutEtreRamassable () { return false; }

    @Override
    public boolean peutMourir() {return false;}

    public  boolean peutTuer(){ return false;} //Dans les fait elle peut tuer, mais le joueur ne meure pas aui cobtact horizontals avec les colonnes





}
