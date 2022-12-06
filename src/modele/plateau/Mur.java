package modele.plateau;

public class Mur extends EntiteStatique {
    public Mur(Jeu _jeu) { super(_jeu); }
    public  int getType(){return 0;}
    public boolean jouable(){return false;}

}
