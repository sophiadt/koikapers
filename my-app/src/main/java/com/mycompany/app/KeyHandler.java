package com.mycompany.app;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 * KeyHandler class for the game. Handles all the key inputs for the game.
 */
public class KeyHandler implements KeyListener {
    public boolean up, down, left, right;
    public int gameState;
    private Board board;
    private Game panel;

    /**
     * Constructor for the KeyHandler
     * @param board
     * @param panel
     */
    public KeyHandler(Board board, Game panel) {
        this.board = board;
        this.panel = panel;
    }

    /**
     * Processes the key pressed
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int program = e.getKeyCode();

        switch (gameState) {
            case 0: // Playing
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE:
                        gameState = 1;
                        break;
                    case KeyEvent.VK_W:
                        up = true;
                        break;
                    case KeyEvent.VK_S:
                        down = true;
                        break;
                    case KeyEvent.VK_D:
                        right = true;
                        break;
                    case KeyEvent.VK_A:
                        left = true;
                        break;
                }
                break;

            case 1: // Paused
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_R:  //For now the R key is the "Resuming key"
                        if (gameState == 1) {
                            gameState = 0;
                        }
                        break;
                    case KeyEvent.VK_Q:
                        if (gameState == 1) {
                            board.loadMap("/title.txt");
                            gameState = 2;
                        }
                        break;
                }
                break;

            case 2: // Title Screen
                switch (program) {
                    case KeyEvent.VK_ENTER:
                        if(PopUp.titleOption == 0) {
                            this.panel.nextLevel();
                            gameState = 0;
                        }
                        if(PopUp.titleOption == 1) {
                            gameState = 4;
                        }
                        if(PopUp.titleOption == 2) {
                            System.exit(0);
                        }
                        break;
                    case KeyEvent.VK_W:
                        PopUp.titleOption--;
                        if (PopUp.titleOption < 0) {
                            PopUp.titleOption = 2;
                        }
                        break;
                    case KeyEvent.VK_S:
                        PopUp.titleOption++;
                        if (PopUp.titleOption > 2) {
                            PopUp.titleOption = 0;
                        }
                        break;
                }
                break;

            case 3: // Game Over
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        if(PopUp.titleOption == 0) {
                            panel.restart();
                        }
                        if(PopUp.titleOption == 1) {
                            System.exit(0);
                        }
                        break;
                    case KeyEvent.VK_W:
                        PopUp.titleOption--;
                        if (PopUp.titleOption < 0) {
                            PopUp.titleOption = 1;
                        }
                        break;
                    case KeyEvent.VK_S:
                        PopUp.titleOption++;
                        if (PopUp.titleOption > 1) {
                            PopUp.titleOption = 0;
                        }
                        break;
                }
                break;
                    
            case 4: // Rules
                board.loadMap("/rules.txt");
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE:
                        board.loadMap("/title.txt");
                        gameState = 2;
                        break;
                }
                break;

            case 5:
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        if(PopUp.titleOption == 0) {
                            panel.nextLevel();
                        }
                        if(PopUp.titleOption == 1) {
                            panel.restart();
                        }
                        if(PopUp.titleOption == 2) {
                            System.exit(0);
                        }
                        break;
                    case KeyEvent.VK_W:
                        PopUp.titleOption--;
                        if (PopUp.titleOption < 0) {
                            PopUp.titleOption = 2;
                        }
                        break;
                    case KeyEvent.VK_S:
                        PopUp.titleOption++;
                        if (PopUp.titleOption > 2) {
                            PopUp.titleOption = 0;
                        }
                        break;
                    }
                break;

            case 6:
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        if(PopUp.titleOption == 0) {
                            panel.restart();
                        }
                        if(PopUp.titleOption == 1) {
                            System.exit(0);
                        }
                        break;
                    case KeyEvent.VK_W:
                        PopUp.titleOption--;
                        if (PopUp.titleOption < 0) {
                            PopUp.titleOption = 1;
                        }
                        break;
                    case KeyEvent.VK_S:
                        PopUp.titleOption++;
                        if (PopUp.titleOption > 1) {
                            PopUp.titleOption = 0;
                        }
                        break;
                }
                break;
        }
    }

    /**
     * Processes the key released
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                up = false;
                break;
            case KeyEvent.VK_S:
                down = false;
                break;
            case KeyEvent.VK_D:
                right = false;
                break;
            case KeyEvent.VK_A:
                left = false;
                break;
        }
    }

    /**
     * Unused method from KeyListener interface
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
}
