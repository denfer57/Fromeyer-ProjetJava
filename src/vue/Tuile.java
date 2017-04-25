package vue;

import java.net.URL;
import java.util.ResourceBundle;

import controleur.ControleurInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Tuile {
    public static Pane p;
    public static int x, y, valeur;

    public Tuile(int abs, int ord, int v) {
        // TODO Auto-generated constructor stub
    	p = new Pane();
        Label c = new Label(Integer.toString(v));

        p.getStyleClass().add("pane");
        c.getStyleClass().add("tuile");

        controleur.ControleurInterface.pane.getChildren().add(p);

        p.getChildren().add(c);

        abs = (abs * 100) + 300;
        ord = (ord * 100) + 100;

       	p.setLayoutX(abs);
       	p.setLayoutY(ord);

       	p.setVisible(true);
       	c.setVisible(true);
       	x = abs;
       	y = ord;
       	valeur = v;

       	System.out.println(abs);
       	System.out.println(ord);
	}

    /**
     * 
     * @return le pane actuel
     */
	public Pane getP() {
		return p;
	}

	/**
	 * 
	 * @param le pane a fixer
	 */
	public void setP(Pane p) {
		this.p = p;
	}

}
