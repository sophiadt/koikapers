package com.mycompany.app;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {
    Game game = new Game();
    Player player = new Player(game,game.keyHandle);
    @Test
    public void AssertPlayerLocation(){
        player.setPlayerLocation(20, 10);
        assertTrue(player.x == 20*game.cellSize);
        assertTrue(player.y == 10*game.cellSize);
    }
    @Test
    public void AssertPlayerDirection(){
        player.keyHandler.up = true;
        player.update();
        assertTrue(player.direction == "up");
        player.keyHandler.up = false;
        player.keyHandler.down = true;
        player.update();
        assertTrue(player.direction == "down");
        player.keyHandler.down = false;
        player.keyHandler.right = true;
        player.update();
        assertTrue(player.direction == "right");
        player.keyHandler.right = false;
        player.keyHandler.left = true;
        player.update();
        assertTrue(player.direction == "left");
        player.keyHandler.left = false;
    }
    @Test
    public void AssertRewardCollection(){
        player.collectReward(game.gameBoard.cell[3]);
        assertTrue(player.points == 1);
    }
    @Test
    public void AssertPenaltyCollection(){
        player.collectPenalty(game.gameBoard.cell[4]);
        assertTrue(player.points == -1);
    }
    @Test
    public void AssertReset(){
        player.timeElapsed = 0;
        assertTrue(player.timeElapsed == 0);
    }
    @Test
    public void AssertHide(){
        player.hide(10,10);
        assertTrue(player.hideState == true && player.hideX == 10*game.cellSize && player.hideY == 10*game.cellSize);
    }
    @Test
    public void AssertSpriteChange(){
        player.spriteCounter = 12;
        player.update();
        assertTrue(player.spriteNum == 2);
        player.spriteCounter = 12;
        player.update();
        assertTrue(player.spriteNum == 3);
        player.spriteCounter = 12;
        player.update();
        assertTrue(player.spriteNum == 4);
        player.spriteCounter = 12;
        player.update();
        assertTrue(player.spriteNum == 1);
        player.spriteCounter = 12;
        player.update();
        assertTrue(player.spriteNum == 2);
    }

}
