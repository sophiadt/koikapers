package com.mycompany.app;

import static org.junit.Assert.*;
import org.junit.Test;

public class RacoonTest {

    Game game = new Game();
    Raccoon raccoon = new Raccoon(game,game.keyHandle);

    @Test
    public void AssertRaccoonLocation() {
        raccoon.setLocation(20, 10);
        assertTrue(raccoon.x == 20*game.cellSize);
        assertTrue(raccoon.y == 10*game.cellSize);
    }

    @Test
    public void AssertRaccoonDirection() {
        game.gameBoard.loadMap("test.txt");
        raccoon.setLocation(10, 10);
        int x = raccoon.x;
        int y = raccoon.y;
        raccoon.direction = "up";
        raccoon.update(100,100);
        raccoon.direction = "down";
        raccoon.update(100,100);

        raccoon.direction = "right";
        raccoon.update(100,100);

        raccoon.direction = "left";
        for(int i = 0; i < 20; i++){
            raccoon.update(100,100);
        }
        assertTrue(raccoon.x != x && raccoon.y != y);
    }
    @Test
    public void AssertRacconSearchPath(){
        game.gameBoard.loadMap("test.txt");
        int x =20;
        int y =10;
        raccoon.setLocation(x,y);
        raccoon.searchPath(x,y+2);
        assertTrue(raccoon.direction == "down");
        raccoon.setLocation(x, y);
        raccoon.searchPath(x,y-2);
        assertEquals(raccoon.direction , "up");
        raccoon.setLocation(x, y);
        raccoon.searchPath(x+2,y);
        assertEquals(raccoon.direction , "right");
        raccoon.setLocation(x, y);
        raccoon.searchPath(x-2,y);
        assertEquals(raccoon.direction , "left");
        
    }

}
