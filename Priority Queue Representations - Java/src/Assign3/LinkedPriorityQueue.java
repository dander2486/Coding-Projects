package Assign3;

import BasicIO.ASCIIOutputFile;

/** This class creates a PriorityQueue represented by a Linked Structure.
 *
 * ****** THIS PROGRAM USES IntelliJ *******
 *
 * @author Daniel Sokic (6164545)
 * @version 1.0 (November 13, 2018)
 */

public class LinkedPriorityQueue {

    LinkedQueueItem firstItem = null; //Initial first item

    /** This constructor creates the queue by taking an array of integers
     * and traverses through each one. Each integer is inserted into the
     * appropriate position through each call.
     *
     * @param NumArray array of all item priorities
     */
    public LinkedPriorityQueue(int[] NumArray){

        for(int i=0; i<NumArray.length; i++){
            Insert( new LinkedQueueItem(NumArray[i]));
        }

    };

    /** This method inserts an item into it's appropriate position,
     * relative to the first item in the queue.
     *
     * @param newItem the newest item to add
     */
    public void Insert( LinkedQueueItem newItem ) {

        if(firstItem == null){ //If a first item doesn't exist, then assign the new item as the first
            firstItem = newItem;
        }
        else{
            LinkedQueueItem ptr = firstItem; //Pointer is set to the front
            if(newItem.priority < firstItem.priority){ //If the first item is lower priority than the new item
                firstItem = newItem; //Set the first item as the new item
                firstItem.nextItem = ptr; //Make the previous "firstItem" second in the queue
            }
            else {
                while (ptr.nextItem != null) { //Traverse until the last item is reached
                    if(newItem.priority < ptr.nextItem.priority){ //Stop if an item with less priority than the new item is found
                        LinkedQueueItem temp = ptr.nextItem; //Store the lower priority item
                        newItem.nextItem = temp;//Make the lower priority item follow the new Item
                        break; //Stop traversal
                    }
                    ptr = ptr.nextItem; //Traverse to next item
                }
                ptr.nextItem = newItem; //Set the new Item to follow the pointer
            }
        }

    }

    /** This method deletes then returns the highest priority item from the queue. */
    public int deleteMin(){
        if(firstItem == null) return -1; //If the queue is empty, return error
        int item = firstItem.priority;
        firstItem = firstItem.nextItem; //Removal by iterating the first item to the following item
        return item;
    }

    /** This method displays all items within the queue from highest to lowest priority*/
    public void traverse(ASCIIOutputFile txtfile){

        LinkedQueueItem ptr = firstItem; //Set pointer to first item
        while(ptr != null) { //Traverse until all items have been traversed
           txtfile.writeInt(ptr.priority); //Print out the priority
           ptr = ptr.nextItem; //Traverse to next item
        }

    }

}
