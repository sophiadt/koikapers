package com.mycompany.app;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;

import org.junit.Test;

public class KeyHandlerTest {
    Game game = new Game();
    KeyEvent enter =  new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
    KeyEvent w =  new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W');
    KeyEvent s =  new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S');
    KeyEvent a =  new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'A');
    KeyEvent d =  new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_D, 'D');
    KeyEvent esc =  new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ESCAPE, KeyEvent.CHAR_UNDEFINED);
    KeyEvent r =  new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_R, 'R');
    KeyEvent q =  new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_Q, 'Q');
    
    @Test
    public void testPlayerInput(){
        game.keyHandle.gameState =0;
        game.keyHandle.keyPressed(w);
        assertTrue(game.p1.keyHandler.up);
        game.keyHandle.keyPressed(s);
        assertTrue(game.p1.keyHandler.down);
        game.keyHandle.keyPressed(a);
        assertTrue(game.p1.keyHandler.left);
        game.keyHandle.keyPressed(d);
        assertTrue(game.p1.keyHandler.right);
        game.keyHandle.keyPressed(esc);
        assertTrue(game.keyHandle.gameState == 1);
        game.keyHandle.gameState = 1;
    }
    @Test
    public void testKeyRealese(){
        game.keyHandle.keyReleased(w);
        assertFalse(game.p1.keyHandler.up);
        game.keyHandle.keyReleased(s);
        assertFalse(game.p1.keyHandler.down);
        game.keyHandle.keyReleased(a);
        assertFalse(game.p1.keyHandler.left);
        game.keyHandle.keyReleased(d);
        assertFalse(game.p1.keyHandler.right);
    }
}
