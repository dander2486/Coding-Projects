package Assign3;

/** This class creates an Item in a PriorityQueue represented by a
 * Linked Structure. It contains the priority of the Item and a pointer
 * to the next item in the queue. The pointer is initialized as null,
 * until another class changes the pointer.
 *
 * ****** THIS PROGRAM USES IntelliJ *******
 *
 * @author Daniel Sokic (6164545)
 * @version 1.0 (November 13, 2018)
 */

public class LinkedQueueItem {

    public int priority;
    public LinkedQueueItem nextItem;

    /** This constructor creates the Item for the Priority Queue
     *
     * @param p the item's priority
     */
    public LinkedQueueItem(int p) {

        priority = p;
        nextItem = null;

    }

}


