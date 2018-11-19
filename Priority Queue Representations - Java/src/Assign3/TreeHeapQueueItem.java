package Assign3;

/** This class creates an Item in a PriorityQueue represented by a
 * Min Heap Binary Tree. It contains the priority of the Item and pointers
 * to its left and right child. The pointers are initialized as null,
 * until another class changes the pointer.
 *
 * ****** THIS PROGRAM USES IntelliJ *******
 *
 * @author Daniel Sokic (6164545)
 * @version 1.0 (November 13, 2018)
 */

public class TreeHeapQueueItem {

    int priority;
    TreeHeapQueueItem leftChild;
    TreeHeapQueueItem rightChild;

    /** This constructor creates the Item for the Priority Queue
     *
     * @param p the item's priority
     */
    public TreeHeapQueueItem(int p){

        priority = p;
        leftChild = null;
        rightChild = null;

    }

}
