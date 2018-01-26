package launcher.localLauncher;

import java.awt.Dimension;
import javax.swing.JFrame;
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
