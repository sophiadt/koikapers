package com.mycompany.app;

import static org.junit.Assert.assertEquals;

import org.junit.*;

public class GameTest {
    
    static Game newGame = new Game();

    //initialize startGame method before tests
    @BeforeClass 
    public static void setupClass(){
        newGame.startGame();
    }

    /*
     * Tests the case when initilialising the Game constructor,
     * the map should be set to title.txt.
     */
    @Test
    public void testStartingLevel() {

        assertEquals("/title.txt", newGame.level);

    }

    /*
     * Tests whether the Gamepanel has the correct screen resolution
     * Proper resolution should be set to 1920x1024
     */
    @Test
    public void correctResolution() {

        int correctWidth = 1920;
        int correctHeight = 1024;

        assertEquals(correctWidth, newGame.windowWidth);
        assertEquals(correctHeight, newGame.windowHeight);
    }

    /*
     * Tests the case that when the user reaches the next level
     * and it is the game finished state
     */
    @Test
    public void incrementLevelif4() {
        newGame.mapLevel = 4;

        newGame.nextLevel();

        assertEquals(6, newGame.keyHandle.gameState);

    }
}
