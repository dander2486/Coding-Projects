package Assign_1;

/** This class represents a node containing a character in a linked structure.
 *
 * @author Daniel Sokic
 * 
 * @version 1.0 (Sept 2018)
 */

class LetterNode {
    
    char item; // the item in the node
    LetterNode  next; // the next node in the structure
    
    /** This constructor creates a new node for the linked structure representing
     *  the stack.
     *
     * @param i the item in the node
     * @param n the next node in the structure.
     */
    
    public LetterNode ( char i, LetterNode n ){
        
        item = i;
        next = n;
    }; //constructor
    
} // LetterNode 
