/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import modele.Parametres;
/**
 *
 * @author Sylvain
 */
public class Case implements Parametres {
	private int x, y, valeur;
	public Grille grille;
	public Pane p;
	public Label l = new Label();

	/**
	 * 
	 * @return la gille (Grille) actuelle
	 */
	public Grille getGrille() {
		return grille;
	}

	/**
	 * Constructeur de la classe Case
	 * @param abs abscisse
	 * @param ord ordonnee
	 * @param v valeur
	 */
	public Case(int abs, int ord, int v) {
		this.x = abs;
		this.y = ord;
		this.valeur = v;
	}

	/**
	 * 
	 * @param g grille a fixer
	 */
	public void setGrille(Grille g) {
		this.grille = g;
		creerTuiles(x,y,valeur);
	}

	/**
	 * Methode qui gere la creation des tuiles
	 * @param x : abscisse
	 * @param y : ordonnee 
	 * @param v : valeur de la case
	 */
	private void creerTuiles(int x, int y, int v) {
		// TODO Auto-generated method stub
		p = new Pane();

		p.getStyleClass().add("pane");
		l.getStyleClass().add("tuile");

		controleur.ControleurInterface.pane.getChildren().add(p);
		l.setText(Integer.toString(v));
		p.getChildren().add(l);

		x = (x * 100) + 300;
		y = (y * 100) + 100;
		p.setTranslateX(x);
		p.setTranslateY(y);

		p.setVisible(true);
		l.setVisible(true);
		//System.out.println(x);
		//System.out.println(y);
	}

	/**
	 * 
	 * @param c : la case a modifier
	 */
	public void modifTuiles(Case c){
		c.setP(p);
		int v = c.getValeur();
		l.setText(Integer.toString(v));
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

	/**
	 * 
	 * @return l'abscisse actuelle de la case
	 */
	public int getX() {
		return x;
	}

	/**
	 * 
	 * @param x (abscisse) a fixer
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * 
	 * @return l'ordonnee actuelle de la case
	 */
	public int getY() {
		return y;
	}

	/**
	 * 
	 * @param y (ordonnee) a fixer
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * 
	 * @param valeur a fixer
	 */
	public void setValeur(int valeur) {
		this.valeur = valeur;
	}

	/**
	 * 
	 * @return la valeur actuelle de la case
	 */
	public int getValeur() {
		return this.valeur;
	}

	@Override
	public boolean equals(Object obj) { // la méthode equals est utilisée lors de l'ajout d'une case à un ensemble pour vérifier qu'il n'y a pas de doublons (teste parmi tous les candidats qui ont le même hashcode)
		if (obj instanceof Case) {
			Case c = (Case) obj;
			return (this.x == c.x && this.y == c.y);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() { // détermine le hashcode
		return this.x * 7 + this.y * 13;
	}

	public boolean valeurEgale(Case c) {
		if (c != null) {
			return this.valeur == c.valeur;
		} else {
			return false;
		}
	}

	/**
	 * Methode qui retourne une (des) case(s) voisine(s) ou null en fonction de la direction choisie par le joueur
	 * @param direction : la direction choisie par le joueur
	 * @return une case ou <code>null</code>
	 */
	public Case getVoisinDirect(int direction) {
		if (direction == HAUT) {
			for (int i = this.y - 1; i >= 0; i--) {
				for (Case c : grille.getGrille()) {
					if (c.getX() == this.x && c.getY() == i) {
						return c;
					}
				}
			}
		} else if (direction == BAS) {
			for (int i = this.y + 1; i < TAILLE; i++) {
				for (Case c : grille.getGrille()) {
					if (c.getX() == this.x && c.getY() == i) {
						return c;
					}
				}
			}
		} else if (direction == GAUCHE) {
			for (int i = this.x - 1; i >= 0; i--) {
				for (Case c : grille.getGrille()) {
					if (c.getX() == i && c.getY() == this.y) {
						return c;
					}
				}
			}
		} else if (direction == DROITE) {
			for (int i = this.x + 1; i < TAILLE; i++) {
				for (Case c : grille.getGrille()) {
					if (c.getX() == i && c.getY() == this.y) {
						return c;
					}
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "Case(" + this.x + "," + this.y + "," + this.valeur + ")";
	}

}
