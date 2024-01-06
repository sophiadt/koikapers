package com.mycompany.app;

import static org.junit.Assert.*;

// import java.awt.event.KeyEvent;

// import javax.swing.JFrame;

import org.junit.Test;

public class CollisionHandlerTest {
    
    Game game = new Game();
    @Test
    public void testCollisionAllDirection(){
        // player can move 16 pixel in one cell in y direction and 20 pixel in x direction
        // player can move 5 pixels in one update
        game.gameBoard.loadMap("test.txt");
        int x = game.p1.x;
        int y = game.p1.y;
        game.p1.keyHandler.up = true;
        game.p1.update();
        game.p1.update();
        game.p1.update();
        x = game.p1.x;
        y = game.p1.y;
        game.p1.update();
        game.p1.update();
        game.p1.update();
        assertEquals(game.p1.y, y);
        game.p1.keyHandler.up = false;
        game.p1.keyHandler.down = true;
        game.p1.update();
        game.p1.update();
        game.p1.update();
        x = game.p1.x;
        y = game.p1.y;
        game.p1.update();
        game.p1.update();
        game.p1.update();
        assertEquals(game.p1.y, y);
        game.p1.keyHandler.down = false;
        game.p1.keyHandler.right = true;
        game.p1.update();
        game.p1.update();
        game.p1.update();
        x = game.p1.x;
        y = game.p1.y;
        game.p1.update();
        game.p1.update();
        game.p1.update();
        assertEquals(game.p1.x, x);
        game.p1.keyHandler.right = false;
        game.p1.keyHandler.left = true;
        game.p1.update();
        game.p1.update();
        game.p1.update();
        x = game.p1.x;
        y = game.p1.y;
        game.p1.update();
        game.p1.update();
        game.p1.update();
        assertEquals(game.p1.x, x);
    }

}
