package application;

import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vue.Grille;
import modele.Parametres;

public class Main extends Application implements Parametres {

	//public static Parent root = new Parent() ;
	@Override
	    public void start(Stage stage) throws Exception {
	        Parent root = FXMLLoader.load(getClass().getResource("FXMLInterface.fxml"));

	        Scene scene = new Scene(root);
	      //Scene scene = new Scene(root,400,400);
	        boolean add = scene.getStylesheets().add("css/styles.css");

	        stage.setScene(scene);
	        stage.show();
	    }

	    /**
	     * @param args the command line arguments
	     */
	    public static void main(String[] args) {
	        launch(args);
	    }
}
