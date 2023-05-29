package Program;

import javax.swing.JFrame;



public class Main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		GamePanel gamePanel = new GamePanel();
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Close the window properly close
		window.setResizable(false);
		window.setTitle("Generic title");
		window.setLocationRelativeTo(null);//Display the window on the center of the screen by not specifying the location
		window.setVisible(true);
		window.add(gamePanel);
		window.pack();
		
		gamePanel.startGameThread();
		

	}

}
