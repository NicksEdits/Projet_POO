package VueControleur;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

import modele.deplacements.Controle4Directions;
import modele.deplacements.Direction;
import modele.plateau.*;


/** Cette classe a deux fonctions :
 *  (1) Vue : proposer une représentation graphique de l'application (cases graphiques, etc.)
 *  (2) Controleur : écouter les évènements clavier et déclencher le traitement adapté sur le modèle (flèches direction Pacman, etc.))
 *
 */
public class VueControleurGyromite extends JFrame implements Observer {
    private Jeu jeu; // référence sur une classe de modèle : permet d'accéder aux données du modèle pour le rafraichissement, permet de communiquer les actions clavier (ou souris)

    private int sizeX; // taille de la grille affichée
    private int sizeY;
    private int width; 
    private int height; 

    // icones affichées dans la grille
    private ImageIcon icoHero;
    private ImageIcon icoBot;
    private ImageIcon icoVide;
    private ImageIcon icoMur;
    private ImageIcon icoColonne;
    private ImageIcon icoColonne_haut; // extremité des colonnes
    private ImageIcon icoColonne_bas;
    private ImageIcon icoCable;


    private ImageIcon icoPlatforme; // different type de platformes
    private ImageIcon icoPlatforme_vertical;
    private ImageIcon icoPlatforme_avant_colonne;
    private ImageIcon icoPlatforme_apres_colonne;


    private JLabel[][] tabJLabel; // cases graphique (au moment du rafraichissement, chaque case va être associée à une icône, suivant ce qui est présent dans le modèle)

    private Timer timer;
    private int timeSecond = 120;   //Set ici le temps de jeu de base

    public VueControleurGyromite(Jeu _jeu) {
        sizeX = jeu.SIZE_X;
        sizeY = _jeu.SIZE_Y;
        jeu = _jeu;
        width = sizeX * 18;
        height = sizeY * 21;
        chargerLesIcones();
        placerLesComposantsGraphiques();
        ajouterEcouteurClavier();
    }

    private void ajouterEcouteurClavier() {
        addKeyListener(new KeyAdapter() { // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un objet qui correspond au controleur dans MVC
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {  // on regarde quelle touche a été pressée
                    case KeyEvent.VK_LEFT : Controle4Directions.getInstance().setDirectionCourante(Direction.gauche); break;
                    case KeyEvent.VK_RIGHT : Controle4Directions.getInstance().setDirectionCourante(Direction.droite); break;
                    case KeyEvent.VK_DOWN : Controle4Directions.getInstance().setDirectionCourante(Direction.bas); break;
                    case KeyEvent.VK_UP : Controle4Directions.getInstance().setDirectionCourante(Direction.haut); break;
                }
            }
        });
    }


    private void chargerLesIcones() {
        icoHero = chargerIcone("Images/player_ca.png", 0, 0, 35, 40);//chargerIcone("Images/Pacman.png");
        //icoBot = chargerIcone("Images/smick.png", 0, 0, 20, 20);//chargerIcone("Images/Pacman.png");
        icoVide = chargerIcone("Images/Mur.png");
        icoColonne = chargerIcone("Images/tileset.png", 17,45,15,15);
        icoColonne_haut = chargerIcone("Images/tileset.png", 1,49,15,15);
        icoColonne_bas = chargerIcone("Images/tileset.png", 33,49,15,15);

        icoMur = chargerIcone("Images/Mur.png");
        icoPlatforme = chargerIcone("Images/tileset.png", 0, 0, 15, 15);
        icoPlatforme_vertical = chargerIcone("Images/tileset.png", 0, 15, 15, 15);
        icoPlatforme_avant_colonne = chargerIcone("Images/tileset.png", 15, 15, 15, 15);
        icoPlatforme_apres_colonne = chargerIcone("Images/tileset.png", 34, 15, 15, 15);
        icoCable = chargerIcone("Images/tileset.png",18,0,15,15 );


    }

    private void placerLesComposantsGraphiques() {
        setTitle("Gyromite");
        setSize(width, height); // changement de la taille de la fenetre 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // permet de terminer l'application à la fermeture de la fenêtre

        JComponent gamePanel = new JPanel(new BorderLayout()); // grilleJLabels va contenir les cases graphiques et les positionner sous la forme d'une grille


        JComponent grilleJLabels = new JPanel(new GridLayout(sizeY, sizeX)); // grilleJLabels va contenir les cases graphiques et les positionner sous la forme d'une grille

        tabJLabel = new JLabel[sizeX][sizeY];

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                JLabel jlab = new JLabel();

                tabJLabel[x][y] = jlab; // on conserve les cases graphiques dans tabJLabel pour avoir un accès pratique à celles-ci (voir mettreAJourAffichage() )
                grilleJLabels.add(jlab);
            }
        }


        //Ajout du timer sur la pannel
        JComponent toolBar = new JPanel(new BorderLayout());
        JLabel timerCounter = new JLabel(Integer.toString(timeSecond));
        toolBar.add(timerCounter, BorderLayout.CENTER);


        gamePanel.add(toolBar, BorderLayout.NORTH);
        gamePanel.add(grilleJLabels, BorderLayout.CENTER);
        add(gamePanel);

        //Section qui gère le timer du jeu

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeSecond--;
                timerCounter.setText(Integer.toString(timeSecond));
                if(timeSecond == 0){

                }
            }
        });
        timer.start();
    }


    /**
     * Il y a une grille du côté du modèle ( jeu.getGrille() ) et une grille du côté de la vue (tabJLabel)
     */
    private void mettreAJourAffichage() {

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                if (jeu.getGrille()[x][y] instanceof Heros) { // si la grille du modèle contient un Pacman, on associe l'icône Pacman du côté de la vue

                    tabJLabel[x][y].setIcon(icoHero);

                    // si transparence : images avec canal alpha + dessins manuels (voir ci-dessous + créer composant qui redéfinie paint(Graphics g)), se documenter
                    //BufferedImage bi = getImage("Images/smick.png", 0, 0, 20, 20);
                    //tabJLabel[x][y].getGraphics().drawImage(bi, 0, 0, null);

                } else if (jeu.getGrille()[x][y] instanceof Bot) {
                    tabJLabel[x][y].setIcon(icoBot);
                }
                
                else if (jeu.getGrille()[x][y] instanceof Mur) {
                    tabJLabel[x][y].setIcon(icoMur);
                } 
                else if (jeu.getGrille()[x][y] instanceof Cable) {
                    tabJLabel[x][y].setIcon(icoCable); 
                }
                else if (jeu.getGrille()[x][y] instanceof Colonne) {
                    Colonne col = (Colonne)jeu.getGrille()[x][y] ;
                   switch(col.getType()){
                    case 1 : // simple colonne sans extrémités
                    tabJLabel[x][y].setIcon(icoColonne); 
                    break;
                    case 2 : // extremité du haut de la colonne
                    tabJLabel[x][y].setIcon(icoColonne_haut); 
                    break;
                    case 3 : // extremité du bas de la colonne
                    tabJLabel[x][y].setIcon(icoColonne_bas);
                   }
                }
                else if (jeu.getGrille()[x][y] instanceof Platforme) {
                    Platforme plat = (Platforme)jeu.getGrille()[x][y] ;
                    switch(plat.getType()){

                    case 1 : 
                    tabJLabel[x][y].setIcon(icoPlatforme); // platforme horizontal
                    break;
                    case 2 : 
                    tabJLabel[x][y].setIcon(icoPlatforme_vertical); // platforme verticale
                    break;
                    case 3 : 
                    tabJLabel[x][y].setIcon(icoPlatforme_avant_colonne); // platforme avant la colonne
                    break;
                    case 4 : 
                    tabJLabel[x][y].setIcon(icoPlatforme_apres_colonne); // platforme apres la colonne
                    break;
                    }
                }else {
                    tabJLabel[x][y].setIcon(icoVide);
                }
            }
        }



    }

    @Override
    public void update(Observable o, Object arg) {
        mettreAJourAffichage();
        /*
        SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        mettreAJourAffichage();
                    }
                }); 
        */

    }


    // chargement de l'image entière comme icone
    private ImageIcon chargerIcone(String urlIcone) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(urlIcone));
        } catch (IOException ex) {
            Logger.getLogger(VueControleurGyromite.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }


        return new ImageIcon(image);
    }

    // chargement d'une sous partie de l'image
    private ImageIcon chargerIcone(String urlIcone, int x, int y, int w, int h) {
        // charger une sous partie de l'image à partir de ses coordonnées dans urlIcone
        BufferedImage bi = getSubImage(urlIcone, x, y, w, h);
        // adapter la taille de l'image a la taille du composant (ici : 20x20)
        return new ImageIcon(bi.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH));
    }

    private BufferedImage getSubImage(String urlIcone, int x, int y, int w, int h) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(urlIcone));
        } catch (IOException ex) {
            Logger.getLogger(VueControleurGyromite.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        BufferedImage bi = image.getSubimage(x, y, w, h);
        return bi;
    }

}
