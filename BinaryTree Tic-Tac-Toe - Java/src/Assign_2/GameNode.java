package Assign_2;

/** This class represents a node containing pointers to a child and sibling node,
 * a 2D array, how many wins or losses it will eventually result in, and a boolean
 * representing if it's been sorted.
 *
 * 
 * ****** THIS PROGRAM USES NETBEANS *******
 * 
 * 
 * @author Daniel Sokic (6164545)
 * @version 1.0 (October 17, 2018)
 */
public class GameNode {
    
    char[][] gameBoard; // the array in the node
    GameNode firstChild; // the child of the node in the structure
    GameNode nextSibling; // the sibling of the node in the structure
    int wins = 0; // the amount of wins this node will result in
    int losses = 0; // the amount of losses this node will result in
    boolean sorted = false; // indicates if this node has been sorted
    
    /** This constructor creates a new node for the linked structure representing
     *  the stack.
     *
     * @param i the item in the node
     * @param f the next child in the structure
     * @param n the next sibling in the structure.
     */
    
    public GameNode ( char[][] i, GameNode f, GameNode n ){
        
        gameBoard = i;
        firstChild = f;
        nextSibling = n;
    }; //constructor
    
} // GameNode 
