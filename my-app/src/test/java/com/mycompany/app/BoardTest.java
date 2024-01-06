package com.mycompany.app;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Unit tests for Board class.
 */
public class BoardTest 
{


    Game panel = new Game();
    Board gameBoard = new Board(panel, "test.txt");
    // unit test board class
    @Test
    public void AssertFishLoaded(){
        assertTrue(gameBoard.fishCount == 1);
    }
    @Test
    public void AssertObjectRemoval(){
        gameBoard.removeObject(10, 0);
        assertTrue(gameBoard.board[10][0] == 0);
    }
    @Test
    public void AssertFishRemoval(){
        gameBoard.removeObject(9, 9);
        assertTrue(gameBoard.fishCount == 0);
    }
    @Test
    public void AssertCellLoaded(){
        assertTrue(gameBoard.cell[3].collision == true );
        assertTrue(gameBoard.cell[3].value == 1);
    }
    @Test
    public void AssertOpenEnd(){
        gameBoard.loadMap("test.txt");
        gameBoard.removeObject(9, 9);
        assertTrue(gameBoard.cell[8].collision == false);
        assertTrue(gameBoard.cell[8].image == gameBoard.cell[0].image);
    }
    // @Test
    // public void AssertDrawMap(){
    //     gameBoard.loadMap("test.txt");
    //     panel.startGame();
    //     panel.repaint();
    //     panel.update();
    // }


}
