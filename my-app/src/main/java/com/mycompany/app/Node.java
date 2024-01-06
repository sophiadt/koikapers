package com.mycompany.app;

/**
 * Node class for the game.
 */
public class Node {
    Node parent;
    public int col;
    public int row;
    // store the cost of moving to this node
    int gCost;
    int hCost;
    int fCost;
    // store whether this node is solid, open or closed
    boolean solid;
    boolean open;
    boolean closed;

    public Node(int col,int row){
        this.col = col;
        this.row = row;
    }
}
