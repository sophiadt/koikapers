package com.mycompany.app;

import javax.swing.JFrame;

/**
 * Main class for the game.
 */
public class Main {
    
    /** 
     * Main method for the game. Creates a new game object.
     */
    public static void main(String[] args) {
        JFrame window = new JFrame("Koi Kapers vs. Raccoon Raiders");
        Game gamePanel = new Game();
        window.add(gamePanel);
        gamePanel.startGame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
    }

}
