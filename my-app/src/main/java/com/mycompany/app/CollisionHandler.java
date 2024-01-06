package com.mycompany.app;

import java.awt.Rectangle;

/**
 * CollisionHandler class for the game.
 */
public class CollisionHandler {

    Game gamepanel;
    Entity entity;
    boolean bonusCollect = false;

    /**
     * Constructor for the CollisionHandler class.
     * @param gamepanel the game panel
     */
    public CollisionHandler(Game gamepanel) {
        this.gamepanel = gamepanel;
    }

    /**
     * Checks if the entity is colliding with a cell that has collision
     * and if it is, checks if the cell is a reward, penalty, or hide cell.
     * @param entity the entity to check collision for
     */
    public void checkCollison(Entity entity) {
        this.entity = entity;
        // get the coordinates of the entity to create 4 points, 2 on x axis and 2 on y axis
        // we basically create a rectangle that is the hitbox of the entity relative to the entity's current position 
        int entityLeftX = entity.x + entity.hitbox.x;
        int entityRightX = entity.x + entity.hitbox.x + entity.hitbox.width;
        int entityTopY = entity.y + entity.hitbox.y;
        int entityBottomY = entity.y + entity.hitbox.y+ entity.hitbox.height;
        // then we use the coordinates to get the cell's coordinates that surround the entity hitbox
        int entityLeftCol = entityLeftX / gamepanel.cellSize;
        int entityRightCol = entityRightX / gamepanel.cellSize;
        int entityTopRow = entityTopY / gamepanel.cellSize;
        int entityBottomRow = entityBottomY / gamepanel.cellSize;
        int cellNum1, cellNum2;

        switch (entity.direction) {
            case "up":
                // we get the cell number of the cell we would go to if the entity was to move in the direction it is moving
                // we do that by moving the entity's hitbox up by the speed of the entity and dividing by the cell size as 
                // the entity's position is in pixels and the board is in cells
                entityTopRow = (entityTopY - entity.speed)/gamepanel.cellSize;
                if(checkGameWin(entityLeftCol, entityRightCol, entityTopRow, entityBottomRow)) {
                    return;
                }
                // we get the type of cell that the entity would be moving into
                cellNum1 = gamepanel.gameBoard.board[entityLeftCol][entityTopRow];
                cellNum2 = gamepanel.gameBoard.board[entityRightCol][entityTopRow];
                // we check if that type is cell has collision
                if (gamepanel.gameBoard.cell[cellNum1].collision || gamepanel.gameBoard.cell[cellNum2].collision) {
                    // if it does, we set the entity's colliding variable to true
                    entity.colliding = true;
                    // if the entity coliding is a player then check if the cell is a reward, penalty cell or hide cell
                    checkPlayerInteraction(cellNum1, cellNum2, entityLeftCol, entityTopRow, entityRightCol, entityTopRow);
                }
                break;

            case "down":
                // we repeat the same process for the other directions
                entityBottomRow = (entityBottomY + entity.speed)/gamepanel.cellSize;
                if(checkGameWin(entityLeftCol, entityRightCol, entityTopRow, entityBottomRow)) {
                    return;
                }
                cellNum1 = gamepanel.gameBoard.board[entityLeftCol][entityBottomRow];
                cellNum2 = gamepanel.gameBoard.board[entityRightCol][entityBottomRow];
                if (gamepanel.gameBoard.cell[cellNum1].collision || gamepanel.gameBoard.cell[cellNum2].collision) {
                    entity.colliding = true;
                    checkPlayerInteraction(cellNum1, cellNum2, entityLeftCol, entityBottomRow, entityRightCol, entityBottomRow);
                }
                break;

            case "right":
                entityRightCol = (entityRightX + entity.speed)/gamepanel.cellSize;
                if(checkGameWin(entityLeftCol, entityRightCol, entityTopRow, entityBottomRow)) {
                    return;
                }
                cellNum1 = gamepanel.gameBoard.board[entityRightCol][entityTopRow];
                cellNum2 = gamepanel.gameBoard.board[entityRightCol][entityBottomRow];
                if (gamepanel.gameBoard.cell[cellNum1].collision || gamepanel.gameBoard.cell[cellNum2].collision) {
                    entity.colliding = true;
                    checkPlayerInteraction(cellNum1, cellNum2, entityRightCol, entityTopRow, entityRightCol, entityBottomRow);
                }
                break;

            case "left":
                entityLeftCol = (entityLeftX - entity.speed)/gamepanel.cellSize;
                if(checkGameWin(entityLeftCol, entityRightCol, entityTopRow, entityBottomRow)) {
                    return;
                }
                cellNum1 = gamepanel.gameBoard.board[entityLeftCol][entityTopRow];
                cellNum2 = gamepanel.gameBoard.board[entityLeftCol][entityBottomRow];
                if (gamepanel.gameBoard.cell[cellNum1].collision || gamepanel.gameBoard.cell[cellNum2].collision) {
                    entity.colliding = true;

                    checkPlayerInteraction(cellNum1, cellNum2, entityLeftCol, entityTopRow, entityRightCol, entityBottomRow);
                }
                break;
        }
    }

    /**
     * Checks if the cell is a reward, penalty, or hide cell and performs the appropriate action.
     * @param cellNum the cell number
     * @param x the x coordinate of the cell
     * @param y the y coordinate of the cell
     */
    public void checkInteraction(int cellNum, int x, int y){
        // if the cell is a reward cell, collect the reward and remove the object from the board
       if(cellNum == 3 || cellNum == 5) {
            gamepanel.p1.collectReward(gamepanel.gameBoard.cell[cellNum]);
            gamepanel.gameBoard.removeObject(x, y);
       }
         // if the cell is a penalty cell, collect the penalty and remove the object from the board
       else if(cellNum == 4) {
            gamepanel.p1.collectPenalty(gamepanel.gameBoard.cell[cellNum]);
            gamepanel.gameBoard.removeObject(x, y);
       }
       // if the cell is a hide cell, hide the player
       else if (cellNum == 6) {
            gamepanel.p1.hide(x,y);
       }
    }

    /**
     * Checks if the entity is colliding with another entity.
     * @param e1 the first entity
     * @param e2 the second entity
     * @return true if the entities are colliding, false otherwise
     */
    public boolean checkGameOver(Entity e1, Entity e2){
        // create entity hitboxes and check if they intersect
        Rectangle e1Hitbox = new Rectangle(e1.hitbox.x + e1.x, e1.hitbox.y + e1.y, e1.hitbox.width, e1.hitbox.height);
        Rectangle e2Hitbox = new Rectangle(e2.hitbox.x + e2.x, e2.hitbox.y + e2.y, e2.hitbox.width, e2.hitbox.height);
        return e1Hitbox.intersects(e2Hitbox);
    }

    /**
     * Checks if the player is colliding with the end cell.
     * @param left the left cell number
     * @param right the right cell number
     * @param top the top cell number
     * @param bottom the bottom cell number
     * @return true if the player is colliding with the end cell, false otherwise
     */
    public boolean checkGameWin(int left, int right, int top, int bottom) {
        // if the next cell is the last cell/end cell, the game is won
        if (right == gamepanel.boardCol || bottom == gamepanel.boardRow || right == 0 || bottom == 0) {
            gamepanel.keyHandle.gameState = 5;
            return true;
        }
        return false;
    }
    /**
     * Checks if the player is colliding with a cell that has collision
     * and if it is, checks if the cell is a reward, penalty, or lilypad.
     * @param cellNum1 the cell number of the first cell
     * @param cellNum2 the cell number of the second cell
     * @param c1x the x coordinate of the first cell
     * @param c1y the y coordinate of the first cell
     * @param c2x the x coordinate of the second cell
     * @param c2y the y coordinate of the second cell
     */
    public void checkPlayerInteraction(int cellNum1,int cellNum2,int c1x,int c1y,int c2x,int c2y){
        if (entity.player == true) {
            if(cellNum1 == 3 || cellNum1 == 4 || cellNum1 == 5 || cellNum1 == 6) {
                checkInteraction(cellNum1, c1x, c1y);
                if(cellNum1 != 6){
                    entity.colliding = false;
                }
                } else if (cellNum2 == 3 || cellNum2 == 4 || cellNum2 == 5 || cellNum2 == 6) {
                    checkInteraction(cellNum2, c2x, c2y);
                    if(cellNum2 != 6){
                        entity.colliding = false;
                    }
                }
                if((cellNum1 == 6 && cellNum2 == 6)|| (cellNum1 == 6 && cellNum2 == 0) || (cellNum1 == 0 && cellNum2 == 6)){
                    entity.colliding = false;
                }
            } else {
                // if the entity is not a player, check if the cell is a reward, penalty cell and let the entity move through it
                if ((cellNum1 == 3 || cellNum1 == 4 || cellNum1 == 5 || cellNum2 == 3 || cellNum2 == 4 || cellNum1 == 5) 
                    && (cellNum1 != 1 && cellNum2 != 1 && cellNum1 != 2 && cellNum2 != 2))  {
                    entity.colliding = false;
                }
            }
    }
}

