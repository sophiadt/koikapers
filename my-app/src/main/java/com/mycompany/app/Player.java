package com.mycompany.app;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Player class for the game. Handles the player's movement and collision.
 */
public class Player extends Entity {
    
    Game panel;
    KeyHandler keyHandler;
    int points;
    double timeElapsed;
    int hideX, hideY;
    boolean hideState = false;
    Rectangle playerHitBox1, playerHitBox2;
    
    /**
     * Constructor for the Player class.
     * @param game
     * @param k
     */
    public Player(Game game, KeyHandler k) {
        this.panel = game;
        this.keyHandler = k;
        this.speed = 5;
        this.direction = "right";
        this.playerHitBox1 = new Rectangle(16, 8, this.panel.hitboxSize - 16, this.panel.hitboxSize); // 1x2 cell hitbox
        this.playerHitBox2 = new Rectangle(8, 16, this.panel.hitboxSize , this.panel.hitboxSize - 16); // 1x2 cell hitbox
        this.hitbox = playerHitBox2;
        this.player = true;
        this.points = 0;
        super.loadImages("Player");
    }

    /**
     * Sets the player's location.
     * @param x
     * @param y
     */
    public void setPlayerLocation(int x, int y) {
        this.x = x * panel.cellSize;
        this.y = y * panel.cellSize;
    }

    /**
     * Updates the player's location based on input from the user.
     */
    public void update() { // gets called 60 times per second (60 FPS)
        this.timeElapsed += (double) 1/this.panel.fps;

        if (this.keyHandler.up || this.keyHandler.down || this.keyHandler.right || this.keyHandler.left) {
            if (this.keyHandler.up) {
                this.direction = "up";
            } else if (keyHandler.down) {
                this.direction = "down";
            } else if (keyHandler.right) {
                this.direction = "right";
            } else if (keyHandler.left) {
                this.direction = "left";
            }
            if (this.direction == "up"||this.direction == "down") {
                this.hitbox = playerHitBox1;
            } else {
                this.hitbox = playerHitBox2;
            }
            // Checking collision
            colliding = false;
            panel.collisionHandler.checkCollison(this);
            // move if not colliding 
            if(!colliding){
                if (direction == "up") {
                    y -= speed;
                } else if (direction == "down") {
                    y += speed;
                } else if (direction == "right") {
                    x += speed;
                } else if (direction == "left") {
                    x -= speed;
                }
            }
            spriteCounter++;
            if(spriteCounter > 4) { // 4 FPS
                if(spriteNum == 1) {
                    spriteNum = 2;
                }
                else if(spriteNum == 2) {
                    spriteNum = 3;
                }
                else if(spriteNum == 3) {
                    spriteNum = 4;
                }
                else if(spriteNum == 4) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        spriteCounter++;
        if(spriteCounter > 12) { // 12 FPS
            switch (spriteNum) {
                case 1:
                case 2:
                case 3:
                spriteNum++;
                break;
                
                case 4:
                spriteNum = 1;
                break;
            }
            spriteCounter = 0;
        }

        if (timeElapsed >= 30 && panel.gameBoard.board[panel.gameBoard.blackKoiX][panel.gameBoard.blackKoiY] != 0) {
            panel.gameBoard.board[panel.gameBoard.blackKoiX][panel.gameBoard.blackKoiY] = 0;
        }

        if (points < 0) {
            keyHandler.gameState = 3;
        }
    }
    
    /**
     * Draws the player.
     * @param g2d
     */
    public void draw(Graphics2D g2d) {
        Color gameStatColours = new Color(61,119,161);

        panel.UI.displaySystemText(g2d, "Time: " + Math.round(timeElapsed), panel.cellSize*25 - 200,panel.cellSize*1-10, gameStatColours);
        panel.UI.displaySystemText(g2d, "Points: " + points, panel.cellSize*2,panel.cellSize*1-10, gameStatColours);
        panel.UI.displaySystemText(g2d, "Pause", panel.cellSize*3+10,panel.cellSize*16-10, gameStatColours);

        //Add photo displaying key caps
        try {
            panel.UI.displayKeyCaps(g2d,panel.cellSize*2,panel.cellSize*15, ImageIO.read(getClass().getResourceAsStream("/ui/esc.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // check if the player is still hiding under the lilypad
        if((panel.gameBoard.board[(this.x + this.hitbox.width/2)/panel.cellSize][(this.y + this.hitbox.height/2)/panel.cellSize]) != 6){
            hideState = false;
        }
        // draw background, then player and then transparent lilypad
        if(hideState) {
            g2d.drawImage(panel.gameBoard.cell[0].image, hideX, hideY, panel.cellSize, panel.cellSize, null);
            g2d.drawImage(super.draw(), x, y, panel.cellSize, panel.cellSize, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g2d.drawImage(panel.gameBoard.cell[6].image, hideX, hideY, panel.cellSize, panel.cellSize, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
        else{
            // draw player
            g2d.drawImage(super.draw(), x, y, panel.cellSize, panel.cellSize, null);
        }
    }

    /**
     * Adds the reward score to the players total.
     * 
     * @param reward
     */
    public void collectReward(Cell reward) {
        points += reward.value;
    }

    /**
     * Adds the reward score to the players total.
     * 
     * @param penalty
     */
    public void collectPenalty(Cell penalty) {
        points += penalty.value;
    }


    /**
     * Hides the player under the lilypad.
     */
    public void hide(int x , int y) {
        hideState = true;
        hideX = x * panel.cellSize;
        hideY = y * panel.cellSize;
    }

}
