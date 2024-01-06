package com.mycompany.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;


/**
 * Board class for the game. Loads the map from a text file and draws the board.
 * Uses the Cell class to store the images for the cells. And uses them to draw the board.
 * Also reads the text file to find the location of player, raccoon, and other objects.
 */
public class Board {
    
    int board[][];
    Game gamePanel;
    Cell[] cell;
    int blackKoiX, blackKoiY;
    int fishCount = 0;


    /**
     * Constructor for the board
     * @param gamePanel the game panel
     * @param mapString the name of the text file
     */
    public Board(Game gamePanel, String mapString) {
        this.gamePanel = gamePanel;
        board = new int[gamePanel.boardCol][gamePanel.boardRow];
        cell = new Cell[20];
        loadMap(mapString);
    }

    /**
     * Loads the map from a text file
     * @param mapString the name of the text file
     */
    public void loadMap(String mapString) {
        fishCount = 0;
        try {
            InputStream in = getClass().getResourceAsStream(mapString);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            int x = 0, y = 0;

            while (x < gamePanel.boardCol && y < gamePanel.boardRow) {
                String line = br.readLine();
                String[] tokens = line.split(" ");
                for (int i = 0; i < tokens.length; i++) {
                    board[x][y] = Integer.parseInt(tokens[i]);
                    // count the number of fish
                    if(board[x][y] == 3){
                        fishCount++;
                    }
                    // set the player and racoon location
                    if(board[x][y] == 9){
                        gamePanel.p1.setPlayerLocation(x,y);
                    }
                    if(board[x][y] == 8){
                        gamePanel.raccoon.setLocation(x,y);
                    }
                    x++;
                }
                x = 0;
                y++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        getCellImage();
    }

    /**
     * Loads the images for the cells from the resources folder
     * 0 = water
     * 1 = ice block
     * 2 = chair
     * 3 = orange koi
     * 4 = caution sign
     * 5 = black koi
     * 6 = lilypad
     * 7 = ending block
     * 8 = raccoon starting bloack
     * 9 = player starting block
     */
    public void getCellImage() {
        try {
            cell[0] = new Cell();
            cell[0].image = ImageIO.read(getClass().getResourceAsStream("/water/1.png"));

            cell[1] = new Cell();
            cell[1].image = ImageIO.read(getClass().getResourceAsStream("/ice/ice.png"));
            // collision = true means that the player cannot move through this cell
            cell[1].collision = true;

            cell[2] = new Cell();
            cell[2].image = ImageIO.read(getClass().getResourceAsStream("/obstacles/chair.png"));
            cell[2].collision = true;

            cell[3] = new Cell();
            cell[3].image = ImageIO.read(getClass().getResourceAsStream("/orangekoi/up.png"));
            cell[3].collision = true;
            cell[3].value = 1;

            cell[4] = new Cell();
            cell[4].image = ImageIO.read(getClass().getResourceAsStream("/obstacles/caution.png"));
            cell[4].collision = true;
            cell[4].value = -1;

            cell[5] = new Cell();
            cell[5].image = ImageIO.read(getClass().getResourceAsStream("/blackkoi/up.png"));
            cell[5].collision = true;
            cell[5].value = 10;

            cell[6] = new Cell();
            cell[6].image = ImageIO.read(getClass().getResourceAsStream("/obstacles/lilypad.png"));
            cell[6].collision = true;

            cell[7] = new Cell();
            cell[7].image = ImageIO.read(getClass().getResourceAsStream("/ice/cracked.png"));
            cell[7].collision = true;
            // cell for the player
            cell[8] = new Cell();
            cell[8].image = cell[0].image;
            // cell for the raccoon
            cell[9] = new Cell();
            cell[9].image = cell[0].image;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the game board
     * If the cell contains a fish or racoon, draw the movable background first then draw the fish or racoon on top.
     * If the cell contains an obstacle, draw the solid background first then draw the obstacle on top.
     * Stores the location of the black koi.
     * @param g2d the graphics object
     */
    public void draw(Graphics2D g2d) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
        while (col < gamePanel.boardCol && row < gamePanel.boardRow) {
            // draw moveable background 
            if(board[col][row] == 3 || board[col][row] == 4 || board[col][row] == 5 || board[col][row] == 6) { // makes it so that the fish and racoon are drawn on top of the cell
                g2d.drawImage(cell[0].image, x, y, gamePanel.cellSize, gamePanel.cellSize, null);
            }

            if (board[col][row] == 5) {
                blackKoiX = col;
                blackKoiY = row;
            }
            // draw the solid background for obstacle
            if(board[col][row] == 2){
                g2d.drawImage(cell[1].image, x, y, gamePanel.cellSize, gamePanel.cellSize, null);
            }

            g2d.drawImage(cell[board[col][row]].image, x, y, gamePanel.cellSize, gamePanel.cellSize, null);
            col++;
            x += gamePanel.cellSize;
            if (col == gamePanel.boardCol) {
                col = 0;
                row++;
                x = 0;
                y += gamePanel.cellSize;
            }
        }
    }

    /**
     * Removes the fish from the board and updates the fish count
     * @param x the x coordinate of the fish
     * @param y the y coordinate of the fish
     */
    public void removeObject(int x, int y) {
        if(board[x][y] == 3){
            fishCount--;
            if(fishCount <= 0){
                openEnd();
            }
        }
        board[x][y] = 0;
    }

    /**
     * Opens the end cells when all fish are collected
     */
    public void openEnd(){
        cell[7].collision = false;
        cell[7].image = cell[0].image;
    }

}
