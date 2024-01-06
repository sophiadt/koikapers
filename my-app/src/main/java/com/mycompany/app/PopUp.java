package com.mycompany.app;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import static java.awt.Font.TRUETYPE_FONT;

/**
 * PopUp class for the game. Handles the pop up messages that need to be shown/drawn on screen.
 */
public class PopUp {
    Game gamepanel;
    Font titleFont, systemFont, FinkHeavy, Seurat;
    public static int titleOption = 0;

    /**
     * Constructor for the PopUp class.
     * @param gamepanel The game panel.
     */
    public PopUp(Game gamepanel) {
        this.gamepanel = gamepanel;

        try {
            InputStream is = getClass().getResourceAsStream("/fonts/FinkHeavy.ttf");
            assert is != null;
            FinkHeavy = Font.createFont(TRUETYPE_FONT, is);

            is = getClass().getResourceAsStream("/fonts/FOT-Seurat Pro B.otf");
            assert is != null;
            Seurat = Font.createFont(TRUETYPE_FONT, is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        titleFont = FinkHeavy.deriveFont(Font.BOLD, gamepanel.cellSize);
        systemFont = Seurat.deriveFont(Font.PLAIN, gamepanel.cellSize);
    }

    /**
     * Draws the title messages on screen.
     * @param g2d The graphics object.
     */
    public void displayTitleMessage(Graphics2D g2d, String message, int x, int y, Color color) {
        g2d.setFont(titleFont);
        g2d.setColor(color);
        g2d.drawString(message, x, y);
    }

    /**
     * Draws the pop up messages on screen.
     * @param g2d The graphics object.
     * @param message The message to be displayed.
     * @param x The x coordinate of the message.
     * @param y The y coordinate of the message.
     * @param color The color of the message.
     * @param specTitleOption The title option.
     */
    public void displaySubtitleMessage(Graphics2D g2d, String message, int x, int y, Color color, int specTitleOption) {
        // make the subtitle 0.8 times the size of the menuFont
        int fontSize = (int) (gamepanel.cellSize * 0.8);
        g2d.setFont(titleFont.deriveFont(Font.PLAIN, fontSize));
        g2d.setColor(color);
        g2d.drawString(message, x, y);

        if(titleOption == specTitleOption) {
            g2d.drawString("->", x - 40, y);
        }
    }

    /**
     * Draws messages in a specific style on screen.
     * @param g2d The graphics object.
     * @param message The message to be displayed.
     * @param x The x coordinate of the message.
     * @param y The y coordinate of the message.
     * @param color The color of the message.
     */
    // displaySubtitleMessage with no specTitleOption for instruction
    public void displaySubtitleMessage(Graphics2D g2d, String message, int x, int y, Color color) {
        // make the subtitle 0.8 times the size of the menuFont
        int fontSize = (int) (gamepanel.cellSize * 0.8);
        g2d.setFont(titleFont.deriveFont(Font.PLAIN, fontSize));
        g2d.setColor(color);
        g2d.drawString(message, x, y);
    }

    /**
     *  Draws the system text on screen.
     * @param g2d The graphics object.
     * @param message The message to be displayed.
     * @param x The x coordinate of the message.
     * @param y The y coordinate of the message.
     * @param color The color of the message.
     */
    public void displaySystemText(Graphics2D g2d, String message, int x, int y, Color color) {
        int fontSize = (int) (gamepanel.cellSize * 0.8);
        g2d.setFont(systemFont.deriveFont(Font.PLAIN, fontSize));
        g2d.setColor(color);
        g2d.drawString(message, x, y);
    }

    /** Below methods are for displaying rules*/
    /**
     * Draws images and rules on screen.
     * @param g2d The graphics object.
     * @param message The message to be displayed.
     * @param x The x coordinate of the message.
     * @param y The y coordinate of the message.
     * @param color The color of the message.
     * @param image The image to be displayed.
     */
    public void displaySubtitleRules(Graphics2D g2d, String message, int x, int y, Color color, BufferedImage image) {
        g2d.drawImage(image, x - 70, y-60, 75, 75, null);
        g2d.drawString(message, x, y);
    }

    /**
     * Draws the Key caps images on screen.
     * @param g2d The graphics object.
     * @param x The x coordinate of the message.
     * @param y The y coordinate of the message.    
     * @param image The image to be displayed.
     */
    public void displayKeyCaps(Graphics2D g2d, int x, int y, BufferedImage image) {
        g2d.drawImage(image, x, y, 64, 64, null);
    }
    
}
