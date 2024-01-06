package com.mycompany.app;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * Entity class for the game.
 */
public class Entity {

    /** Stores Entity's location */
    public int x, y;
    /** Stores Entity's speed */
    public int speed;
    /** Stores Entity's direction */
    public String direction;

    /** Stores Entity's hitbox */
    public Rectangle hitbox;
    /** Stores whether the Entity is colliding with a cell that has collision */
    public boolean colliding = false;
    
    /** Count for what frame is currently being displayed */
    public int spriteCounter = 0;
    /** Stores the sprite number to display */
    public int spriteNum = 1;

    /** Stores sprites for entity's up movement allowing for upwards animation */
    public ArrayList<BufferedImage> upImages = new ArrayList<BufferedImage>(9);
    /** Stores sprites for entity's down movement allowing for downwards animation */
    public ArrayList<BufferedImage> downImages = new ArrayList<BufferedImage>(9);
    /** Stores sprites for entity's left movement allowing for leftwards animation */
    public ArrayList<BufferedImage> leftImages = new ArrayList<BufferedImage>(9);
    /** Stores sprites for entity's right movement allowing for rightwards animation */
    public ArrayList<BufferedImage> rightImages = new ArrayList<BufferedImage>(9);

    /** Determines whether or not the entity is being controlled by the player (using their keyboard) */
    public boolean player = false;
    
    /**
     * Loads images for the entities in different directions (up, down, left, right).
     * The images are loaded for various animation frames in each direction and stored in an ArrayList.
     * If an IOException occurs during the image loading process, the exception is printed to the console.
     * 
     * @param entityType The type of entity to get the sprites for.
     */
    public void loadImages(String entityType) {
        try {
            switch (entityType) {
                case "Player":
                    for (int i = 1; i <= 4; i++) {
                        upImages.add(ImageIO.read(getClass().getResource("/player/up/" + i + ".png")));
                        downImages.add(ImageIO.read(getClass().getResource("/player/down/" + i + ".png")));
                        leftImages.add(ImageIO.read(getClass().getResource("/player/left/" + i + ".png")));
                        rightImages.add(ImageIO.read(getClass().getResource("/player/right/" + i + ".png")));
                    }
                    break;
                case "Raccoon":
                    for (int i = 1; i <= 9; i++) {
                        upImages.add(ImageIO.read(getClass().getResource("/enemies/raccoon/up/" + i + ".png")));
                        downImages.add(ImageIO.read(getClass().getResource("/enemies/raccoon/down/" + i + ".png")));
                        leftImages.add(ImageIO.read(getClass().getResource("/enemies/raccoon/left/" + i + ".png")));
                        rightImages.add(ImageIO.read(getClass().getResource("/enemies/raccoon/right/" + i + ".png")));
                    }
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called by entity subclasses to get the sprite to draw to the screen.
     * 
     * @return the image to draw to the screen
     */
    public BufferedImage draw() {
        BufferedImage image = null;
        switch (direction) {
            case "up":
                image = upImages.get(spriteNum - 1);
                break;
            case "down":
                image = downImages.get(spriteNum - 1);
                break;
            case "left":
                image = leftImages.get(spriteNum - 1);
                break;
            case "right":
                image = rightImages.get(spriteNum - 1);
                break;
        }

        return image;
    }

}
