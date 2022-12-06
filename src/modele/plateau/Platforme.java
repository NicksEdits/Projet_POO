package modele.plateau;

public class Platforme extends EntiteStatique {

    private int type;
    @Override
    public boolean jouable(){return false;}
    public Platforme(Jeu _jeu , int type) { super(_jeu); 
        this.type = type;
    }

    public int getType(){
        return type;
    }

}
