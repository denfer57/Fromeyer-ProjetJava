package controleur;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import vue.Case;
import vue.Grille;
import vue.Tuile;
import modele.Parametres;

public class ControleurInterface implements Initializable {
    @FXML
    private Label score;
    @FXML
	public GridPane grille;
    @FXML
    public Pane fond;
    public static Pane pane;
    public static GridPane g;

    public GridPane getGrille() {
		return this.grille;
	}

	public void setGrille(GridPane grille) {
		this.grille = grille;
	}

	public Pane getFond() {
		return this.fond;
	}

	public void setFond(Pane fond) {
		this.fond = fond;
	}

	@Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
		this.pane=this.fond;
		this.g=this.grille;
		System.out.println(pane);
        grille.getStyleClass().add("gridpane");
    }

    /*
     * Méthodes listeners pour gérer les événements (portent les mêmes noms que
     * dans Scene Builder
     */
   /* @FXML
    private void handleDragAction(MouseEvent event) {
        System.out.println("Glisser/déposer sur la grille avec la souris");
        double x = event.getX();//translation en abscisse
        double y = event.getY();//translation en ordonnée
        if (x > y) {
            for (int i = 0; i < grille.getChildren().size(); i++) { //pour chaque colonne
                //for (int j = 0; j < grille.getRowConstraints().size(); j++) { //pour chaque ligne
                System.out.println("ok1");
                grille.getChildren().remove(i);

                /*Node tuile = grille.getChildren().get(i);
                 if (tuile != null) {
                 int rowIndex = GridPane.getRowIndex(tuile);
                 int rowEnd = GridPane.getRowIndex(tuile);
                 }
                // }
            }
        } else if (x < y) {
            System.out.println("ok2");
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    Pane p = new Pane();
                    p.getStyleClass().add("pane");
                    grille.add(p, i, j);
                    p.setVisible(true);
                    grille.getStyleClass().add("gridpane");
                }
            }
        }
    }

    @FXML
    private void handleButtonAction(MouseEvent event) {
        System.out.println("Clic de souris sur le bouton menu");
    }
*/
    @FXML
    public void keyPressed(KeyEvent ke) {
    	int direction = 0;
    	String touche = ke.getText();
    	if (touche.compareTo("q") == 0){
    		direction = Parametres.GAUCHE;
    		System.out.println("Deplacement vers la gauche");
    	}
    	if (touche.compareTo("d") == 0){
    		direction = Parametres.DROITE;
    		System.out.println("Deplacement vers la droite");
    	}
    	if (touche.compareTo("z") == 0){
    		System.out.println("Deplacement vers le haut");
    		direction = Parametres.HAUT;
    	}
    	if (touche.compareTo("s") == 0){
    		System.out.println("Deplacement vers le bas");
    		direction = Parametres.BAS;
    	}
    	else System.out.println("Vous devez appuyez sur d pour Droite, g pour Gauche, h pour Haut ou b pour Bas");

    	boolean b2 = application.Main.g.lanceurDeplacerCases(direction);
        if (b2) {
        	application.Main.b = application.Main.g.nouvelleCase();
            if (!application.Main.b) application.Main.g.gameOver();
        }
        System.out.println(application.Main.g);
        if (application.Main.g.getValeurMax()>=Parametres.OBJECTIF) application.Main.g.victory();

        /*System.out.println("touche appuy�e");
        String touche = ke.getText();
        if (touche.compareTo("q") == 0) { // utilisateur appuie sur "q" pour envoyer la tuile vers la gauche
            if (objectifx > 300) { // possible uniquement si on est pas dans la colonne la plus à gauche
                objectifx -= (int) 300; // on définit la position que devra atteindre la tuile en abscisse (modèle). Le thread se chargera de mettre la vue à jour
                score.setText(Integer.toString(Integer.parseInt(score.getText()) + 1)); // mise à jour du compteur de mouvement
            }
        }
        else if (touche.compareTo("d") == 0) { // utilisateur appuie sur "d" pour envoyer la tuile vers la droite
            if (objectifx <= (int) 1000 - 2 * 400 / 4 - 300) { // possible uniquement si on est pas dans la colonne la plus à droite (taille de la fenêtre - 2*taille d'une case - taille entre la grille et le bord de la fenêtre)
                objectifx += (int) 300;
                score.setText(Integer.toString(Integer.parseInt(score.getText()) + 1));
            }
        }
        else if (touche.compareTo("z") == 0) { // utilisateur appuie sur "d" pour envoyer la tuile vers la droite
            if (objectify > 100) { // possible uniquement si on est pas dans la colonne la plus à droite (taille de la fenêtre - 2*taille d'une case - taille entre la grille et le bord de la fenêtre)
                    objectify -= (int) 300;
                    score.setText(Integer.toString(Integer.parseInt(score.getText()) + 1));
            }
        }
        else if (touche.compareTo("s") == 0) { // utilisateur appuie sur "d" pour envoyer la tuile vers la droite
        	if (objectify < 400) { // possible uniquement si on est pas dans la colonne la plus à droite (taille de la fenêtre - 2*taille d'une case - taille entre la grille et le bord de la fenêtre)
                    objectify += (int) 300;
                    score.setText(Integer.toString(Integer.parseInt(score.getText()) + 1));
        	}
        }
        System.out.println("objectifx=" + objectifx);
        Task task = new Task<Void>() { // on définit une tâche parallèle pour mettre à jour la vue
            @Override
            public Void call() throws Exception { // implémentation de la méthode protected abstract V call() dans la classe Task
                while (x != objectifx) { // si la tuile n'est pas à la place qu'on souhaite attendre en abscisse
                    if (x < objectifx) {
                        x += 1; // si on va vers la droite, on modifie la position de la tuile pixel par pixel vers la droite
                    } else {
                        x -= 1; // si on va vers la gauche, idem en décrémentant la valeur de x
                    }
                    // Platform.runLater est nécessaire en JavaFX car la GUI ne peut être modifiée que par le Thread courant, contrairement à Swing où on peut utiliser un autre Thread pour ça
                    Platform.runLater(new Runnable() { // classe anonyme
                        @Override
                        public void run() {
                            //javaFX operations should go here
                            p.relocate(x, y); // on déplace la tuile d'un pixel sur la vue, on attend 5ms et on recommence jusqu'à atteindre l'objectif
                            p.setVisible(true);
                        }
                    }
                    );
                    Thread.sleep(5);
                } // end while
                while (y != objectify) { // si la tuile n'est pas à la place qu'on souhaite attendre en abscisse
                    if (y < objectify) {
                        y += 1; // si on va vers la droite, on modifie la position de la tuile pixel par pixel vers la droite
                    } else {
                        y -= 1; // si on va vers la gauche, idem en décrémentant la valeur de x
                    }
                    // Platform.runLater est nécessaire en JavaFX car la GUI ne peut être modifiée que par le Thread courant, contrairement à Swing où on peut utiliser un autre Thread pour ça
                    Platform.runLater(new Runnable() { // classe anonyme
                        @Override
                        public void run() {
                            //javaFX operations should go here
                            p.relocate(x, y); // on déplace la tuile d'un pixel sur la vue, on attend 5ms et on recommence jusqu'à atteindre l'objectif
                            p.setVisible(true);
                        }
                    }
                    );
                    Thread.sleep(5);
                } // end while
                return null; // la méthode call doit obligatoirement retourner un objet. Ici on n'a rien de particulier à retourner. Du coup, on utilise le type Void (avec un V majuscule) : c'est un type spécial en Java auquel on ne peut assigner que la valeur null
            } // end call

        };
        Thread th = new Thread(task); // on crée un contrôleur de Thread
        th.setDaemon(true); // le Thread s'exécutera en arrière-plan (démon informatique)
        th.start(); // et on exécute le Thread pour mettre à jour la vue (déplacement continu de la tuile horizontalement)*/
    }
}
