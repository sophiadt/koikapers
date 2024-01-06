package com.mycompany.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


/**
 * Game class that handles methods that involve activating the game and states.
 * <pre>
 * Variables:
 * {@code intialCellSize - sets each cell to 16x16 pixels}
 * {@code scale - scales each cell 4 times}
 * {@code cellSize - sets each cell to 64x64 pixels}
 * {@code hitboxSize - sets the hit box to 48x48 pixels}
 * {@code boardCol - sets the board to 30 cells wide which is 30*64 = 1920 pixels}
 * {@code boardRow - sets the board to 16 cells high which is 16*64 = 1024 pixels}
 * {@code windowWidth - sets the window width to 1920 pixels}
 * {@code windowHeight - sets the window height to 1024 pixels}
 * {@code p1 - creates a new player}
 * {@code raccoon - creates a new raccoon}
 * {@code collisionHandler - creates a new collision handler}
 * {@code pathFinder - creates a new path finder}
 * {@code thread - creates a new thread}
 * {@code fps - sets the fps to 60}
 * {@code level - sets the level to the title screen}
 * {@code gameBoard - creates a new board}
 * {@code keyHandle - creates a new key handler}
 * {@code UI - creates a new pop up}
 * {@code mapLevel - sets the level number to 0}
 * </pre>
 */
public class Game extends JPanel implements Runnable {

    final int intialCellSize = 16;
    final int scale = 4;
    public final int cellSize = intialCellSize * scale; 
    public final int hitboxSize = intialCellSize * 3;
    public final int boardCol = 30;
    public final int boardRow = 16;
    public final int windowWidth = boardCol * cellSize;
    public final int windowHeight = boardRow * cellSize;
    public final Player p1;
    public final Raccoon raccoon;
    public CollisionHandler collisionHandler = new CollisionHandler(this);
    public PathFinder pathFinder = new PathFinder(this);
    
    Thread thread;
    final int fps = 60;
    public String level = "/title.txt";
    public Board gameBoard = new Board(this, level);
    KeyHandler keyHandle = new KeyHandler(gameBoard, this);
    public PopUp UI = new PopUp(this);
    public int mapLevel = 1;

    /**
     *  Constructor for the game.
     */
    public Game() {
        this.setPreferredSize(new Dimension(this.windowWidth, this.windowHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(this.keyHandle);
        this.setFocusable(true);
        this.p1 = new Player(this, this.keyHandle);
        this.raccoon = new Raccoon(this, this.keyHandle);
        keyHandle.gameState = 2;
    }

    /**
     * Starts the game.
     * Bind to button, if button is pressed, start game.
     * For now hard coded on start.
     */
    public void startGame() {
        thread = new Thread(this); // start the thread
        thread.start();
        // six game states, 0 = playing, 1 = paused, 2 = Title screen, 3 = Game Over, 4 = Rules, 5 = Game Won, 6 = Game Finished
    }

    /**
     * Stops the game.
     */
    @Override
    public void run() {
        // delta/acumulator used for FPS
        double interval = 1000000000 / fps;
        double delta = 0;
        long currentTime;
        long lastTime = System.nanoTime();
        while (thread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / interval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    /**
     * Updates the game every frame (60 seconds).
     * gameState == 0 - playing screen
     * gameState = 3 - game over screen
     * If the player collides with the raccoon, it changes the game state to game over.
     */
    public void update() {
        if (keyHandle.gameState == 0) {
            p1.update();
            this.raccoon.update(p1.x, p1.y);
            if(collisionHandler.checkGameOver(p1, this.raccoon)){
                keyHandle.gameState = 3;
            }
        }
    }

    /**
     * Paints the game every frame (60 seconds) for each game state.
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (keyHandle.gameState == 2) {
            Color titleColour = new Color(61,119,161);
            g2d.setColor(titleColour);
            gameBoard.draw(g2d);

            //Options for Starting Game, Viewing Rules, and Quiting Game
            UI.displaySubtitleMessage(g2d, "Start Game", cellSize * ((boardCol / 2))-100,cellSize * ((boardRow / 2)) - 30, Color.BLACK, 0);
            UI.displaySubtitleMessage(g2d, "Rules", cellSize * ((boardCol / 2))-100,cellSize * ((boardRow / 2)) + 15, Color.BLACK, 1);
            UI.displaySubtitleMessage(g2d, "Quit", cellSize * ((boardCol / 2))-100,cellSize * ((boardRow / 2)) + 60, Color.BLACK, 2);
            UI.displaySubtitleMessage(g2d, "Switch Options", cellSize * ((boardCol/2) - 4) - 64*2 + 10,cellSize * 16 - 15, Color.BLACK);
            UI.displaySubtitleMessage(g2d, "Select", cellSize * ((boardCol/2) - 4) + 64*6 + 10,cellSize * 16 - 15, Color.BLACK);

            //Add photo displaying controls
            try {
                UI.displayKeyCaps(g2d,cellSize * ((boardCol/2) - 4) - 64*4,cellSize * 14, ImageIO.read(getClass().getResourceAsStream("/ui/w.png")));
                UI.displayKeyCaps(g2d,cellSize * ((boardCol/2) - 4) - 64*5,cellSize * 15, ImageIO.read(getClass().getResourceAsStream("/ui/a.png")));
                UI.displayKeyCaps(g2d,cellSize * ((boardCol/2) - 4) - 64*4,cellSize * 15, ImageIO.read(getClass().getResourceAsStream("/ui/s.png")));
                UI.displayKeyCaps(g2d,cellSize * ((boardCol/2) - 4) - 64*3,cellSize * 15, ImageIO.read(getClass().getResourceAsStream("/ui/d.png")));
                UI.displayKeyCaps(g2d,cellSize * ((boardCol/2) - 4) + 64*5,cellSize * 15, ImageIO.read(getClass().getResourceAsStream("/ui/enter.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }        
            //Added background for Rules page
        } else if (keyHandle.gameState == 4) {
            gameBoard.draw(g2d);

            UI.displayTitleMessage(g2d, "Rules", cellSize * ((boardCol / 2))-50, cellSize * ((boardRow / 2)) - 375, Color.BLACK);
            UI.displaySubtitleMessage(g2d, "Reach the end of the maze while avoiding raccoons!", cellSize * ((boardCol / 2))-550, cellSize * ((boardRow / 2)) - 310, Color.BLACK);
            UI.displaySubtitleMessage(g2d, "The game is over if the user reaches a negative score", cellSize * ((boardCol / 2))-550, cellSize * ((boardRow / 2)) - 260, Color.BLACK);
            UI.displaySubtitleMessage(g2d, "Hide under lily pads to prevent the raccoon from following", cellSize * ((boardCol / 2))-550, cellSize * ((boardRow / 2)) - 210, Color.BLACK);
            UI.displaySubtitleMessage(g2d, "The bonus black fish will disappear after a set duration of time", cellSize * ((boardCol / 2))-550, cellSize * ((boardRow / 2)) - 160, Color.BLACK);

            try {
                UI.displaySubtitleRules(g2d, "Lily Pad: Used for hiding", cellSize * ((boardCol / 2))-200, cellSize * ((boardRow / 2)), Color.BLACK, ImageIO.read(getClass().getResourceAsStream("/obstacles/lilypad.png")));
                UI.displaySubtitleRules(g2d, "Reward: +1 Point", cellSize * ((boardCol / 2))-200, cellSize * ((boardRow / 2))-75, Color.BLACK, ImageIO.read(getClass().getResourceAsStream("/orangekoi/up.png")));
                UI.displaySubtitleRules(g2d, "Moving Enemy: Game Over", cellSize * ((boardCol / 2))-200, cellSize * ((boardRow / 2))+75, Color.BLACK, ImageIO.read(getClass().getResourceAsStream("/enemies/raccoon/up/1.png")));
                UI.displaySubtitleRules(g2d, "Stationary Enemy: -1 Point", cellSize * ((boardCol / 2))-200, cellSize * ((boardRow / 2))+150, Color.BLACK, ImageIO.read(getClass().getResourceAsStream("/obstacles/caution.png")));
                UI.displaySubtitleRules(g2d, "Obstacle: Chair", cellSize * ((boardCol / 2))-200, cellSize * ((boardRow / 2))+225, Color.BLACK, ImageIO.read(getClass().getResourceAsStream("/obstacles/chair.png")));
                UI.displaySubtitleRules(g2d, "Bonus: +10 Points", cellSize * ((boardCol / 2))-200, cellSize * ((boardRow / 2))+300, Color.BLACK, ImageIO.read(getClass().getResourceAsStream("/blackkoi/up.png")));

                UI.displaySubtitleMessage(g2d, "Press esc to return to title page", cellSize * ((boardCol / 2))-300, cellSize * ((boardRow / 2))+400, Color.BLACK);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.gameBoard.draw(g2d);
            this.p1.draw(g2d);
            this.raccoon.draw(g2d);
            // draw UI for game over
            if(keyHandle.gameState == 3) {
                gameBoard.loadMap("/gameover.txt");
                gameBoard.draw(g2d);
                UI.displaySubtitleMessage(g2d, "Title Screen", cellSize * ((boardCol / 2))+520,cellSize * ((boardRow / 2)) - 150, Color.BLACK, 0);
                UI.displaySubtitleMessage(g2d, "Exit", cellSize * ((boardCol / 2))+520,cellSize * ((boardRow / 2)) - 110, Color.BLACK, 1);
                UI.displaySystemText(g2d, "Points: " + p1.points, cellSize * ((boardCol / 2))+520,cellSize * ((boardRow / 2)) +100, Color.BLACK);
                UI.displaySystemText(g2d, "Time: " + Math.round(p1.timeElapsed * 10.0)/10.0, cellSize * ((boardCol / 2))+520,cellSize * ((boardRow / 2)) +160, Color.BLACK);
    
            }
            // draw UI for game won
            else if(keyHandle.gameState == 5) {
                gameBoard.loadMap("/gamewon.txt");
                gameBoard.draw(g2d);
                UI.displaySubtitleMessage(g2d, "Next Level", cellSize * ((boardCol / 2))+520,cellSize * ((boardRow / 2)) - 190, Color.BLACK, 0);
                UI.displaySubtitleMessage(g2d, "Title Screen", cellSize * ((boardCol / 2))+520,cellSize * ((boardRow / 2)) - 150, Color.BLACK, 1);
                UI.displaySubtitleMessage(g2d, "Exit", cellSize * ((boardCol / 2))+520,cellSize * ((boardRow / 2)) - 110, Color.BLACK, 2);
                UI.displaySystemText(g2d, "Points: " + p1.points, cellSize * ((boardCol / 2))+520,cellSize * ((boardRow / 2)) +100, Color.BLACK);
                UI.displaySystemText(g2d, "Time: " + Math.round(p1.timeElapsed * 10.0)/10.0, cellSize * ((boardCol / 2))+520,cellSize * ((boardRow / 2)) +160, Color.BLACK);
    
            }
            // draw UI for game won
            else if(keyHandle.gameState == 6) {
                gameBoard.loadMap("/gamewon.txt");
                gameBoard.draw(g2d);
                UI.displaySubtitleMessage(g2d, "Thanks for playing", cellSize * ((boardCol / 2))+520,cellSize * ((boardRow / 2)) - 190, Color.BLACK);
                UI.displaySubtitleMessage(g2d, "Title Screen", cellSize * ((boardCol / 2))+520,cellSize * ((boardRow / 2)) - 150, Color.BLACK, 0);
                UI.displaySubtitleMessage(g2d, "Exit", cellSize * ((boardCol / 2))+520,cellSize * ((boardRow / 2)) - 110, Color.BLACK, 1);
                UI.displaySystemText(g2d, "Points: " + p1.points, cellSize * ((boardCol / 2))+520,cellSize * ((boardRow / 2)) +100, Color.BLACK);
                UI.displaySystemText(g2d, "Time: " + Math.round(p1.timeElapsed * 10.0)/10.0, cellSize * ((boardCol / 2))+520,cellSize * ((boardRow / 2)) +160, Color.BLACK);
    
            }
            // draw UI for paused
            if(keyHandle.gameState == 1){
                // needs better way to center text
                Color color = new Color(0,0,0,0.5f);
                g2d.setColor(color);
                g2d.fillRect(0, 0, windowWidth, windowHeight);
                UI.displaySystemText(g2d, "|| Paused", cellSize * ((boardCol/2) - 4),cellSize * ((boardRow / 2) - 2), Color.BLACK);
                UI.displaySystemText(g2d, "Resume", cellSize * ((boardCol/2) - 4) + 80,cellSize * ((boardRow / 2)) - 15, Color.BLACK);
                UI.displaySystemText(g2d, "Back to Main menu", cellSize * ((boardCol/2) - 4) + 80,cellSize * ((boardRow / 2)) +50  , Color.BLACK);

                //Add photo displaying key caps
                try {
                    UI.displayKeyCaps(g2d,cellSize * ((boardCol/2) - 4),cellSize * 7, ImageIO.read(getClass().getResourceAsStream("/ui/r.png")));
                    UI.displayKeyCaps(g2d,cellSize * ((boardCol/2) - 4),cellSize * 8, ImageIO.read(getClass().getResourceAsStream("/ui/q.png")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        g2d.dispose();
    }

    /**
     * Restarts the game.
     */
    public void restart() {
        this.p1.points = 0;
        this.p1.timeElapsed = 0;
        this.level = "/title.txt";
        this.gameBoard.loadMap(level);
        this.mapLevel = 1;
        keyHandle.gameState = 2;
    }

    /**
     * Starts the next level.
     */
    public void nextLevel(){
        if(keyHandle.gameState == 5){
            this.mapLevel++;
        }
        if(mapLevel == 4){
            keyHandle.gameState = 6;
            return;
        }
        this.level = "/map" + mapLevel + ".txt";
        this.gameBoard.loadMap(level);
        this.p1.timeElapsed = 0;
        this.p1.points = 0;
        keyHandle.gameState = 0;
    }
}