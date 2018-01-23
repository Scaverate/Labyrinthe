package launcher.localLauncher;

import java.awt.Dimension;
import java.util.Observer;

import javax.swing.JFrame;

import controler.MazeGameControlers;
import controler.controlerLocal.MazeGameControler;
import model.observable.MazeGame;
import vue.MazeGameGUI;

public class LauncherGUI {

	public static void main(String[] args) {
		JFrame frame;	
		Dimension dim;
		
		dim = new Dimension(700, 700);
		frame = new MazeGameGUI(dim);		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Super Mario Maze");
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

	}
}
