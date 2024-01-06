package com.mycompany.app;

import java.util.ArrayList;
/**
 * PathFinder class for the game. Implements the A* algorithm to find the shortest path from the start node to the goal node.
 */
public class PathFinder {
    Game gamepanel;
    Node[][] nodes;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode,goalNode,currentNode;
    boolean pathFound = false;
    int step = 0;
    /**
     * Constructor for the PathFinder class.
     * @param gamepanel The game panel.
     */
    public PathFinder(Game gamepanel){
        this.gamepanel = gamepanel;
        createNodes();
    }
    /**
     * Creates the nodes for the game.
     */
    // intialize the nodes
    public void createNodes(){
        nodes = new Node[gamepanel.boardCol][gamepanel.boardRow];
        int x = 0;
        
        while(x<gamepanel.boardCol){
            int y = 0;
            while(y<gamepanel.boardRow){
                nodes[x][y] = new Node(x,y);
                y++;
            }
            x++;
        }
    }
    /**
     * Resets the nodes.
     */
    // reset the nodes
    public void resetNodes(){
        int x = 0;
        while(x<gamepanel.boardCol){
            int y = 0;
            while(y<gamepanel.boardRow){
                nodes[x][y].solid = false;
                nodes[x][y].open = false;
                nodes[x][y].closed = false;
                y++;
            }
            x++;
        }
        // clear the open list and the path list
        openList.clear();
        pathList.clear();
        pathFound = false;
        step = 0;
    }
    /**
     * Sets the nodes.
     * @param startX The starting x coordinate.
     * @param startY The starting y coordinate.
     * @param goalX The goal x coordinate.
     * @param goalY The goal y coordinate.
     */
    // set the start node, goal node and the solid nodes
    public void setNodes(int startX,int startY,int goalX,int goalY){
        resetNodes();

        startNode = nodes[startX][startY];
        currentNode = startNode;
        goalNode = nodes[goalX][goalY];
        openList.add(startNode);

        int x = 0;
        
        while(x<gamepanel.boardCol){
            int y = 0;
            while(y<gamepanel.boardRow){
                int cellNum = gamepanel.gameBoard.board[x][y];
                if((gamepanel.gameBoard.cell[cellNum].collision && gamepanel.gameBoard.cell[cellNum].value ==0) ){
                    nodes[x][y].solid = true;
                }
                // get the cost to move to the node
                getCost(nodes[x][y]);
                y++;
            }
            x++;
        }

    }
    /**
     * Gets the cost of moving to the node.
     * @param node The node.
     */
    public void getCost(Node node){
        // G cost
        // G cost is the cost of moving from the starting node to the current node

        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        // H cost
        // H cost is the cost of moving from the current node to the goal node
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        // F cost
        // F cost is the sum of the G cost and the H cost
        node.fCost = node.gCost + node.hCost;
    }
    /**
     * Searches to see if a path is possible, if possible track that path and store it.
     * @return Whether a path is possible.
     */
    // search to see if a path is possible, if possible track that path and store it
    public boolean search(){
        while (!pathFound && step < 1000) {
            int col = currentNode.col;
            int row = currentNode.row;
            currentNode.closed = true;
            openList.remove(currentNode);

            if(row -1 >= 0){
                openNode(nodes[col][row-1]);
            }
            if(row +1 < gamepanel.boardRow){
                openNode(nodes[col][row+1]);
            }
            if(col -1 >= 0){
                openNode(nodes[col-1][row]);
            }
            if(col +1 < gamepanel.boardCol){
                openNode(nodes[col+1][row]);
            }

            int bestFcost = 1000000;
            int bestIndex = 0;

            for(int i =0; i< openList.size(); i++){
                if(openList.get(i).fCost < bestFcost){
                    bestFcost = openList.get(i).fCost;
                    bestIndex = i;
                }
                else if (openList.get(i).fCost == bestFcost){
                    if(openList.get(i).gCost < openList.get(bestIndex).gCost){
                        bestIndex = i;
                    }
                }
            }
            if(openList.size()== 0){
                break;
            }
            currentNode = openList.get(bestIndex);
            if(currentNode == goalNode){
                pathFound = true;
                trackPath();
            }
            step++;
            
        }
        return pathFound;
    }
    /**
     * Opens the node.
     * @param node The node.
     */
    public void openNode(Node node){
        if(node.open == false && node.solid == false && node.closed == false){
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }
    /**
     * Tracks the path.
     */
    // reverse the path list to get the path from the start node to the goal node
    public void trackPath(){
        Node tempNode = goalNode;

        while(tempNode != startNode){
            pathList.add(0,tempNode);
            tempNode = tempNode.parent;
        }
    }

}
