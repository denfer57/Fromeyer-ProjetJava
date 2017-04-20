package controleur;

import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.animation.Animation;
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
import javafx.util.Duration;
import vue.Case;
import vue.Grille;
import vue.Tuile;
import modele.Parametres;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;

public class ControleurInterface implements Initializable {
    @FXML
    private Label score;
    //grille de l'interface FXML
    @FXML
	public GridPane grille;
    @FXML
    public Pane fond;
    public static Pane pane;
    public Grille g;
    public boolean b;
    public int direction = 0;

    public int objectifx = 0, objectify = 0;

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
        grille.getStyleClass().add("gridpane");
        g = new Grille();
        b = g.nouvelleCase();
        b = g.nouvelleCase();
    }

	private TranslateTransition getAnimationDeplacement(Case c) {
    	//pane est l’objet Pane représentant la tuile (ça peut être n’importe quel objet héritant de Node)
    	Pane pane = c.RecupereUneCase();

    	TranslateTransition animation = new TranslateTransition(Duration.millis(200), pane);

    	//Coordonnées du point de départ (position actuelle du pane)
    	int x = (int) Math.round(pane.getTranslateX());
    	int y = (int) Math.round(pane.getTranslateY());

    	animation.setFromX(x);
    	animation.setFromY(y);

    	//System.out.println(x);
    	//Coordonnées d’arrivée (position actuelle du modèle)
    	if(direction == Parametres.BAS || direction == Parametres.HAUT){
    		animation.setToX(x);
    		animation.setToY(objectify);
    	}
    	else if(direction == Parametres.GAUCHE || direction == Parametres.DROITE) {
    		animation.setToX(objectifx);
    		animation.setToY(y);
    	}

    	/*
    	 * 600 = right, 300 = left, 100 = high, 400 = bottom
    	 * if high or bottom then x else objx
    	 * if left or right then y else objy
    	 * example in the high on the right (600,100)
    	 * go to the left, 300
    	 * objectifx = 300
    	 *
    	*/

    	return animation;
    }

    private void animerToutesTuiles() {
    	//On stocke dans animationsParalleles les animations de chaque tuile
    	ParallelTransition animationsParalleles = new ParallelTransition();
    	for (Case c : g.getGrille()){ // pour chaque case
    		//System.out.println(g.getGrille());
    		animationsParalleles.getChildren().add(getAnimationDeplacement(c));
    	}

    	//On les lance toutes en même temps
    	animationsParalleles.play();
    }

    @FXML
    public void keyPressed(KeyEvent ke) {
    	String touche = ke.getText();
    	if (touche.compareTo("q") == 0){
    		direction = Parametres.GAUCHE;
    		//objectifx -= (int) 300;
    		objectifx = 300;
    		//objectify = ;
    		System.out.println("Deplacement vers la gauche");
    	}
    	if (touche.compareTo("d") == 0){
    		direction = Parametres.DROITE;
    		//objectifx += (int) 300;
    		objectifx = 600;
    		System.out.println("Deplacement vers la droite");
    	}
    	if (touche.compareTo("z") == 0){
    		direction = Parametres.HAUT;
    		//objectify -= (int) 300;
    		objectify = 100;
    		System.out.println("Deplacement vers le haut");
    	}
    	if (touche.compareTo("s") == 0){
    		direction = Parametres.BAS;
    		//objectify += (int) 300;
    		objectify = 400;
    		System.out.println("Deplacement vers le bas");
    	}

    	boolean b2 = g.lanceurDeplacerCases(direction);
        if (b2) {
        	animerToutesTuiles();
        	b = g.nouvelleCase();
        	score.setText(Integer.toString(Integer.parseInt(score.getText()) + 1));
        	System.out.println(g);
            if (!b) g.gameOver();
        }

        if (g.getValeurMax()>=Parametres.OBJECTIF) g.victory();
    }
}
