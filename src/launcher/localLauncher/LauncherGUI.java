package launcher.localLauncher;

import java.awt.Dimension;
import java.util.Observer;

import javax.swing.JFrame;

import controler.MazeGameControlers;
import controler.controlerLocal.MazeGameControler;
import model.observable.MazeGame;
import vue.MazeGameGUI;



/**
 * Lance l'exécution d'un jeu en mode graphique.
 * La vue (GameGUI) observe le modèle (Game)
 * les échanges passent par le contrôleur (GameControlers)
 * 
 */
public class LauncherGUI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame;	
		Dimension dim;
		
		dim = new Dimension(700, 700);
		frame = new MazeGameGUI(dim);		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(600, 10);
		frame.setResizable(true);
		frame.pack();
		frame.setVisible(true);
	}
}
