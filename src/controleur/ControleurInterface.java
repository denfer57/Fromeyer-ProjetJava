package controleur;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import vue.Case;
import vue.Grille;
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
	@FXML
	private Button save;
	public static Pane pane;
	public Grille g;
	public boolean b;
	public int direction = 0;

	public int objectifx = 0, objectify = 0;

	/**
	 * 
	 * @return la grille (GridPane) actuelle
	 */
	public GridPane getGrille() {
		return this.grille;
	}

	/**
	 * 
	 * @param grille a fixer
	 */
	public void setGrille(GridPane grille) {
		this.grille = grille;
	}

	/**
	 * 
	 * @return le fond (Pane) actuel
	 */
	public Pane getFond() {
		return this.fond;
	}

	/**
	 * 
	 * @param fond a fixer
	 */
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


		save.setOnAction((event) -> {
			ObjectOutputStream oos = null;
			try {
				FileOutputStream fos=new FileOutputStream(new File("save.ser")) ;
				oos=new ObjectOutputStream(fos);
				oos.writeObject(g);
				oos.flush();
			} catch (final java.io.IOException e) { 
				e.printStackTrace();
			} finally { 
				try {
					if (oos != null) {
						oos.flush(); 
						oos.close();
					}
				} catch (final IOException ex) {
					ex.printStackTrace();
				}
			}
		});
	}

	/**
	 * Methode qui gere les animations des cases (tuiles)
	 * @param c une Case
	 * @return animation une animation (TranslateTransition)
	 */
	private TranslateTransition getAnimationDeplacement(Case c) {
		//pane est l�objet Pane repr�sentant la tuile (�a peut �tre n�importe quel objet h�ritant de Node)
		Pane pane = c.getP();

		TranslateTransition animation = new TranslateTransition(Duration.millis(200), pane);

		//Coordonn�es du point de d�part (position actuelle du pane)
		int x = (int) Math.round(pane.getTranslateX());
		int y = (int) Math.round(pane.getTranslateY());

		animation.setFromX(x);
		animation.setFromY(y);

		//System.out.println(x);
		//Coordonn�es d�arriv�e (position actuelle du mod�le)
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

	/**
	 * methode qui pour toutes les tuiles appelle la methode <code>getAnimationDeplacement(c)</code>
	 */
	private void animerToutesTuiles() {
		//On stocke dans animationsParalleles les animations de chaque tuile
		ParallelTransition animationsParalleles = new ParallelTransition();
		for (Case c : g.getGrille()){ // pour chaque case
			//System.out.println(g.getGrille());
			animationsParalleles.getChildren().add(getAnimationDeplacement(c));
		}

		//On les lance toutes en m�me temps
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
