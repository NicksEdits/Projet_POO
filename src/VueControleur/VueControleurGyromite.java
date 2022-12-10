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
import java.util.ResourceBundle.Control;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;


import modele.deplacements.Controle4Directions;
import modele.deplacements.Direction;
import modele.plateau.*;

/**
 * Cette classe a deux fonctions :
 * (1) Vue : proposer une représentation graphique de l'application (cases graphiques, etc.)
 * (2) Controleur : écouter les évènements clavier et déclencher le traitement adapté sur le modèle (flèches direction Pacman, etc.))
 */
public class VueControleurGyromite extends JFrame implements Observer {
    private Jeu jeu; // référence sur une classe de modèle : permet d'accéder aux données du modèle pour le rafraichissement, permet de communiquer les actions clavier (ou souris)

    private int sizeX; // taille de la grille affichée
    private int sizeY;
    private int width;
    private int height;
    public JComponent gamePanel;
    private boolean gameOverOrWin = false;

    // icones affichées dans la grille
    private ImageIcon icoHero;
    private ImageIcon icoHero2;
    private ImageIcon icoBot;
    private ImageIcon icoBombe;
    private ImageIcon icoVide;
    private ImageIcon icoMur;
    private ImageIcon icoColonne;
    private ImageIcon icoColonne_haut; // extremité des colonnes
    private ImageIcon icoColonne_bas;
    private ImageIcon icoCable;
    private ImageIcon icoRadis;
    private ImageIcon icoBonus;


    private ImageIcon icoPlatforme; // different type de platformes
    private ImageIcon icoPlatforme_vertical;
    private ImageIcon icoPlatforme_avant_colonne;
    private ImageIcon icoPlatforme_apres_colonne;


    private JLabel[][] tabJLabel; // cases graphique (au moment du rafraichissement, chaque case va être associée à une icône, suivant ce qui est présent dans le modèle)

    private Timer timer;
    public int timeSecond = 2;   //Set ici le temps de jeu de base
    private JLabel score;
    private JLabel bombesEtRadits;

    private JLabel WinOrLose;

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
    
    private void ajouterEcouteurClavier(){
        addKeyListener(new KeyAdapter(){ // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un objet qui correspond au controleur dans MVC
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {  // on regarde quelle touche a été pressée
                        case KeyEvent.VK_LEFT:
                        Controle4Directions.getInstance().setDirectionCourante(Direction.gauche);
                        break;
                        case KeyEvent.VK_RIGHT:
                        Controle4Directions.getInstance().setDirectionCourante(Direction.droite);
                        break;
                        case KeyEvent.VK_DOWN:
                        Controle4Directions.getInstance().setDirectionCourante(Direction.bas);
                        break;
                        case KeyEvent.VK_UP:
                        Controle4Directions.getInstance().setDirectionCourante(Direction.haut);
                        System.out.println("up");
                        break;

                        case KeyEvent.VK_Z:
                        Controle4Directions.getInstance2().setDirectionCourante(Direction.haut);
                        break;

                        case KeyEvent.VK_Q:
                        Controle4Directions.getInstance2().setDirectionCourante(Direction.gauche);
                        break;

                        case KeyEvent.VK_S:
                        Controle4Directions.getInstance2().setDirectionCourante(Direction.bas);
                        break;

                        case KeyEvent.VK_D:
                        Controle4Directions.getInstance2().setDirectionCourante(Direction.droite);
                        System.out.println("right");
                        break;

                        case KeyEvent.VK_SPACE:
                        if (gameOverOrWin) {
                            reset();
                        }
                        break;

                        case KeyEvent.VK_R:
                        if(jeu.getRadit() > 0){
                            System.out.println("key r press when radit >= 1");
                            jeu.dropRadihector2();
                        }
                        break;
                        
                        case KeyEvent.VK_M:
                        if(jeu.getRadit() > 0){
                            System.out.println("key m press when radit >= 1");                            
                            jeu.dropRadit();
                        }
                        break;

                        case KeyEvent.VK_ESCAPE:
                        System.exit(0);
                        break;
                    }
                }
            });
    }

    public void reset() {
        this.jeu = new Jeu();
        //chargerLesIcones();       
        gamePanel.removeAll();
        placerLesComposantsGraphiques();
        jeu.getOrdonnanceur().deleteObservers();
        jeu.getOrdonnanceur().addObserver(this);
        this.setVisible(true);
        timeSecond = 300;

        jeu.start(300);
        gameOverOrWin = false;
    }

    private void chargerLesIcones() {
        icoHero = chargerIcone("Images/sprites.png", 0, 0,25, 30);
        icoHero2=chargerIcone("Images/sprites.png", 139, 0,25, 30);//chargerIcone("Images/Pacman.png");
        icoBot = chargerIcone("Images/smick_ca.png", 0, 0, 40, 40);//chargerIcone("Images/Pacman.png");
        icoVide = chargerIcone("Images/Mur.png");
        icoColonne = chargerIcone("Images/tileset.png", 17, 45, 15, 15);
        icoColonne_haut = chargerIcone("Images/tileset.png", 1, 49, 15, 15);
        icoColonne_bas = chargerIcone("Images/tileset.png", 33, 49, 15, 15);
        icoBombe = chargerIcone("Images/bomb_ca.png", 10, 10, 45, 45);
        icoMur = chargerIcone("Images/Mur.png");
        icoPlatforme = chargerIcone("Images/tileset.png", 0, 0, 15, 15);
        icoPlatforme_vertical = chargerIcone("Images/tileset.png", 0, 15, 15, 15);
        icoPlatforme_avant_colonne = chargerIcone("Images/tileset.png", 15, 15, 15, 15);
        icoPlatforme_apres_colonne = chargerIcone("Images/tileset.png", 34, 15, 15, 15);
        icoCable = chargerIcone("Images/tileset.png", 18, 0, 15, 15);
        icoRadis = chargerIcone("Images/smick_ca.png", 35, 130, 30, 30);
        icoBonus = chargerIcone("Images/Pacman.png");

    }

    public int getTime() {
        return timeSecond;
    }

    private void placerLesComposantsGraphiques() {
        setTitle("Gyromite");
        setSize(width, height); // changement de la taille de la fenetre 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // permet de terminer l'application à la fermeture de la fenêtre

        gamePanel = new JPanel(new BorderLayout()); // grilleJLabels va contenir les cases graphiques et les positionner sous la forme d'une grille


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
        JLabel timerCounter = new JLabel("Time left : " + Integer.toString(timeSecond));
        toolBar.add(timerCounter, BorderLayout.EAST);

        WinOrLose = new JLabel("");
        toolBar.add(WinOrLose, BorderLayout.NORTH);

        // Ajout du score
        score = new JLabel("Score : " + Integer.toString(jeu.getScore()));
        toolBar.add(score, BorderLayout.PAGE_END);

        bombesEtRadits = new JLabel("Bombes Left : " + Integer.toString(jeu.getBombe()) + "                  Radit : " + Integer.toString(jeu.getRadit()));
        toolBar.add(bombesEtRadits, BorderLayout.CENTER);
        gamePanel.add(toolBar, BorderLayout.NORTH);
        gamePanel.add(grilleJLabels, BorderLayout.CENTER);
        add(gamePanel);

        //Section qui gère le timer du jeu

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeSecond--;
                timerCounter.setText("Time left : " + Integer.toString(timeSecond));
                if (timeSecond == 0) {
                    timer.stop();
                    gameOver(true);
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

                for (Entite e : jeu.getGrille()[x][y]) {

                    if (e instanceof Heros) {
                        Heros hero = (Heros) e;
                        switch(hero.getType()){
                            case 1 :
                        // si la grille du modèle contient un Pacman, on associe l'icône Pacman du côté de la vue
                        tabJLabel[x][y].setIcon(icoHero);
                            break;
                            case 2:
                            tabJLabel[x][y].setIcon(icoHero2);
                            break;

                        }
                        // si transparence : images avec canal alpha + dessins manuels (voir ci-dessous + créer composant qui redéfinie paint(Graphics g)), se documenter
                        //BufferedImage bi = getImage("Images/smick.png", 0, 0, 20, 20);
                        //tabJLabel[x][y].getGraphics().drawImage(bi, 0, 0, null);

                    } else if (e instanceof Bot) {
                        tabJLabel[x][y].setIcon(icoBot);
                    } else if (e instanceof Mur) {
                        tabJLabel[x][y].setIcon(icoMur);
                    } else if (e instanceof Ramassable) {
                        Ramassable plat = (Ramassable) e;
                        switch (plat.getType()) {

                            case 1:
                                tabJLabel[x][y].setIcon(icoBombe); // Bombe
                                break;
                            case 2:
                                tabJLabel[x][y].setIcon(icoRadis); // Radis
                                break;

                            case 3:
                                tabJLabel[x][y].setIcon(icoBonus); // Bombe
                                break;
                        }
                    } else if (e instanceof Cable) {
                        tabJLabel[x][y].setIcon(icoCable);
                    } else if (e instanceof Colonne) {
                        Colonne col = (Colonne) e;
                        switch (col.getType()) {
                            case 1: // simple colonne sans extrémités
                                tabJLabel[x][y].setIcon(icoColonne);
                                break;
                            case 2: // extremité du haut de la colonne
                                tabJLabel[x][y].setIcon(icoColonne_haut);
                                break;
                            case 3: // extremité du bas de la colonne
                                tabJLabel[x][y].setIcon(icoColonne_bas);
                        }
                    } else if (e instanceof Platforme) {
                        Platforme plat = (Platforme) e;
                        switch (plat.getType()) {

                            case 1:
                                tabJLabel[x][y].setIcon(icoPlatforme); // platforme horizontal
                                break;
                            case 2:
                                tabJLabel[x][y].setIcon(icoPlatforme_vertical); // platforme verticale
                                break;
                            case 3:
                                tabJLabel[x][y].setIcon(icoPlatforme_avant_colonne); // platforme avant la colonne
                                break;
                            case 4:
                                tabJLabel[x][y].setIcon(icoPlatforme_apres_colonne); // platforme apres la colonne
                                break;
                        }
                    } else {
                        tabJLabel[x][y].setIcon(icoVide);
                    }
                }

                if (jeu.getGrille()[x][y].isEmpty()) {
                    tabJLabel[x][y].setIcon(icoVide);
                }

            }
            int b = jeu.getBombe();
            if (b == 0) {
                timer.stop();
                sucess();
            }

        }
        if (getTime() == 0) {
            timer.stop();
            gameOver(true);


        } else if (jeu.getkill()) {
            gameOver(false);
            timer.stop();

        }
        score.setText("Score : " + Integer.toString(jeu.getScore()));

        bombesEtRadits.setText("Bombes Left : " + Integer.toString(jeu.getBombe()) + "                  Radit : " + Integer.toString(jeu.getRadit()));


    }

    @Override
    public void update(Observable o, Object arg) {
        mettreAJourAffichage();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mettreAJourAffichage();
            }
        });


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


    public void gameOver(boolean WinorLose) {

        gameOverOrWin = true ;
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                tabJLabel[x][y].setIcon(icoVide);
            }
        }
        timer.stop();
        if (WinorLose) {
            WinOrLose.setText("Time exeded .. Game over ! Press Space to restart");

        } else {
            WinOrLose.setText("You're dead.. Game over ! Press Space to restart");}
        }
      
       /*  JLabel gameOverText = new JLabel("Game over ! press space to restart");
        this.add(gameOverText);*/


     
    public void sucess(){


        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                tabJLabel[x][y].setIcon(icoVide);
            }
        }
        gameOverOrWin = true;
        WinOrLose.setText("Victory you win ! Press Space to restart ");
       /*  JLabel sucessText = new JLabel("Victory ! presse space to restart");
        this.add(sucessText); */

    }

}
