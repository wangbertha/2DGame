package main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {

        // Create window and specify desired configurations
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Allows user to properly close window
        window.setResizable(false); // Does not allow user to resize window
        window.setTitle("2D Game");

        // Add GamePanel to the window
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        // Size window to fit preferred size and layout
        window.pack();

        // Set more window configurations
        window.setLocationRelativeTo(null); // Centers window on screen
        window.setVisible(true); // Makes window visible
    }
}
