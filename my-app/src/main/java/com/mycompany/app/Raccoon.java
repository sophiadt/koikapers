package com.mycompany.app;

import java.awt.*;
import java.util.Random;
/**
 * Raccoon class for the game. Extends the Entity class.
 * Also contains the searchPath() method which implements the A* algorithm to find the shortest path from the start node to the goal node.
 * Also contains the update() method which moves the raccoon in the direction of the player.
 */
public class Raccoon extends Entity {

    Game gamePanel;
    KeyHandler keyHandler;
    int changeDirection = 0;

    /**
     * Constructor for the raccoon.
     * @param panel is the game panel
     * @param k is the key handler
     */
    public Raccoon(Game panel, KeyHandler k) {
        this.gamePanel = panel;
        this.keyHandler = k;
        // need a way to set the x and y, probably by loading it from the board
        this.speed = 4;
        this.hitbox = new Rectangle(8,8,this.gamePanel.hitboxSize, this.gamePanel.hitboxSize); // 1x2 cell hitbox
        super.loadImages("Raccoon");
    }
    /**
     * Sets the location of the raccoon.
     * @param x is the x coordinate of the raccoon
     * @param y is the y coordinate of the raccoon
     */
    public void setLocation(int x, int y){
        this.x = x * gamePanel.cellSize;
        this.y = y * gamePanel.cellSize;
    }

    /**
     * Updates the raccoon's location and direction.
     * @param playerX is the player's x coordinate
     * @param playerY is the player's y coordinate
     */
    public void update(int playerX, int playerY) {
        // player location in cells
        int goalCol = (playerX + gamePanel.p1.hitbox.x) / gamePanel.cellSize;
        int goalRow = (playerY + gamePanel.p1.hitbox.y) / gamePanel.cellSize;
        // get the path to the player location
        searchPath(goalCol, goalRow);

        colliding = false;
        gamePanel.collisionHandler.checkCollison(this);
        // if not colliding with anything move in the direction
        if (!colliding) {
            switch (direction) {
                case "up":
                this.y -= speed;
                break;

                case "down":
                this.y += speed;
                break;

                case "left":
                this.x -= speed;
                break;

                case "right":
                this.x += speed;
                break;
            }

            spriteCounter++;

            if(spriteCounter > 2) { // 2 FPS
                // if (spriteNum > 0) {
                //     spriteNum++;
                // } else {
                //     spriteNum = 1;
                // }

                switch (spriteNum) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    spriteNum++;
                    break;

                    case 9:
                    spriteNum = 1;
                    break;
                }
                spriteCounter = 0;
            }
        }
    }

    /**
     * Draws the raccoon from the 9 images loaded in loadRaccoonImages() based on the direction and spriteNum.
     * @param g2d is the graphics object
     */
    public void draw(Graphics2D g2d) {
        g2d.drawImage(super.draw(), this.x, this.y, gamePanel.cellSize, gamePanel.cellSize, null);
    }

    /**
     * Searches for a path to the player.
     * @param goalCol is the player's x coordinate
     * @param goalRow is the player's y coordinate
     */
    public void searchPath(int goalCol, int goalRow) {
        int startX = (this.x + this.hitbox.width) / gamePanel.cellSize;
        int startY = (this.y + this.hitbox.height) / gamePanel.cellSize;

        gamePanel.pathFinder.setNodes(startX, startY, goalCol, goalRow);
        // search for the path
        if(gamePanel.pathFinder.search()){
            int nextX = gamePanel.pathFinder.pathList.get(0).col * gamePanel.cellSize;
            int nextY = gamePanel.pathFinder.pathList.get(0).row * gamePanel.cellSize;

            int entityLeftX = this.x + this.hitbox.x;
            int entityRightX = this.x + this.hitbox.x + this.hitbox.width;
            int entityTopY = this.y + this.hitbox.y;
            int entityBottomY = this.y + this.hitbox.y + this.hitbox.height;
            // move in the direction of the next node
            if (entityTopY > nextY && entityLeftX >= nextX && entityRightX < nextX+gamePanel.cellSize) {
                this.direction = "up";
            } else if (entityTopY < nextY && entityLeftX >= nextX && entityRightX < nextX+gamePanel.cellSize) {
                this.direction = "down";
            } else if (entityTopY >= nextY && entityBottomY < nextY + gamePanel.cellSize) {
                if (entityLeftX > nextX){
                    this.direction = "left";
                } else if (entityLeftX < nextX){
                    this.direction = "right";
                }
            } else if (entityTopY > nextY && entityLeftX > nextX) {
                this.direction = "up";
                this.colliding = false;
                gamePanel.collisionHandler.checkCollison(this);
                if (this.colliding) {
                    this.direction = "left";
                }
            } else if (entityTopY > nextY && entityLeftX<nextX) {
                this.direction = "up";
                this.colliding = false;
                gamePanel.collisionHandler.checkCollison(this);
                if (this.colliding) {
                    this.direction = "right";
                }
            } else if (entityTopY < nextY && entityLeftX > nextX) {
                this.direction = "down";
                this.colliding = false;
                gamePanel.collisionHandler.checkCollison(this);
                if (this.colliding) {
                    this.direction = "left";
                }
            } else if (entityTopY < nextY && entityLeftX < nextX) {
                this.direction = "down";
                this.colliding = false;
                gamePanel.collisionHandler.checkCollison(this);
                if (this.colliding) {
                    this.direction = "right";
                }
            }
        } else {
            // if the path is not found, change direction every 60 frames
            if (this.changeDirection == 0) {
                Random rand = new Random();
                int low = 1;
                int high = 5;
                int result = rand.nextInt(high-low) + low;
                switch (result) {
                    case 1:
                        this.direction = "up";
                        break;
                    case 2:
                        this.direction = "down";
                        break;
                    case 3:
                        this.direction = "left";
                        break;
                    case 4:
                        this.direction = "right";
                        break;
                    default:
                        break;
                }
            }
            this.changeDirection++;
            if (this.changeDirection == 60) {
                this.changeDirection = 0;
            }
        }
    }

    
}
