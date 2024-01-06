package com.mycompany.app;

import java.awt.image.BufferedImage;

/**
 * Cell class for the game.
 */
public class Cell {
    /** Stores cells image */
    public BufferedImage image;
    /** Stores whether the cell has collision enabled */
    public boolean collision = false;
    /** Stores cells value*/
    public int value = 0;

}
