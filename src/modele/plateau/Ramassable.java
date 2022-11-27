package modele.plateau;

public class Ramassable extends EntiteStatique {
    private int type;
    public Ramassable(Jeu _jeu , int type) { super(_jeu); 
        this.type = type;
    }

    public int getType(){
        return type;
    }
}
