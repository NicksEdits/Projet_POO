package modele.plateau;

public class Platforme extends EntiteStatique {

    private int type;
    public Platforme(Jeu _jeu , int type) { super(_jeu); 
        this.type = type;
    }

    public int getType(){
        return type;
    }

}
