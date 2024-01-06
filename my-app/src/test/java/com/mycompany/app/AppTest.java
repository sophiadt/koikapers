package com.mycompany.app;

import static org.junit.Assert.*;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import org.junit.Test;

public class AppTest {
    // Run this test to ensure that the app builds correctly
    @Test
    public void testAppBuild()
    {
        Exception exception = null;
        try{
            Main.main(null);
        }
        catch(Exception e){
            exception = e;
        }
        assertTrue( exception == null );
    }
    Game game = new Game();
    public void openWindow(){
        JFrame window = new JFrame("Test");
        window.add(game);
        game.startGame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
    }
    KeyEvent enter =  new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
    KeyEvent w =  new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W');
    KeyEvent s =  new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S');
    KeyEvent a =  new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'A');
    KeyEvent d =  new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_D, 'D');
    KeyEvent esc =  new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ESCAPE, KeyEvent.CHAR_UNDEFINED);
    KeyEvent r =  new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_R, 'R');
    KeyEvent q =  new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_Q, 'Q');
    
    /**
     * Tests if all the basic game states are loading properly
     * @throws InterruptedException
     */
    @Test
    public void testGameStates() throws InterruptedException{
        Exception exception = null;
        openWindow();
        Thread.sleep(100);
        assertTrue(game.keyHandle.gameState == 2);
        game.keyHandle.keyPressed(enter);
        Thread.sleep(100);
        assertTrue(game.keyHandle.gameState == 0);
        game.keyHandle.keyPressed(esc);
        Thread.sleep(100);
        assertTrue(game.keyHandle.gameState == 1);
        game.keyHandle.keyPressed(r);
        Thread.sleep(100);
        assertTrue(game.keyHandle.gameState == 0);
        game.keyHandle.keyPressed(esc);
        game.keyHandle.keyPressed(q);
        Thread.sleep(100);
        assertTrue(game.keyHandle.gameState == 2);
        game.keyHandle.keyPressed(s);
        game.keyHandle.keyPressed(enter);
        Thread.sleep(100);
        assertTrue(game.keyHandle.gameState == 4);
        Thread.sleep(200);
    }
}
