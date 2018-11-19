package Assign3;

import BasicIO.ASCIIOutputFile;

/** This class creates a PriorityQueue represented by a min heap array.
 *
 * ****** THIS PROGRAM USES IntelliJ *******
 *
 * @author Daniel Sokic (6164545)
 * @version 1.0 (November 13, 2018)
 */

public class ArrayHeapPriorityQueue {

    int[] QueueArray; //The min heap array
    int[] ElementArray; //Array of all elements
    int elementAmount; // The amount of elements in the array

    /** This constructor requires an array of all elements to be added, and the
     * max amount of items to store in the array. It initializes the global variable
     * that dictates current number of elements in the array, the unpopulated heap array,
     * and a global reference to the element array. The min heap is then constructed
     * from the provided elements.
     *
     * @param numOfItems
     * @param NumArray
     */
    public ArrayHeapPriorityQueue( int numOfItems, int[] NumArray){

        elementAmount = NumArray.length; //Initialize global num of elements

        QueueArray = new int[numOfItems+1]; //Initialize the min heap array

        ElementArray = NumArray; //Have global reference to the element array

        buildHeap();

    };

    /** This method constructs the min heap by iteratively calling to the insert method to
     *  insert each item from the element array into the min heap
     */
    private void buildHeap(){

        for( int i = 1; i < elementAmount+1; i++ ){
            Insert(i);
        }

    }

    /** This method inserts a priority located at the index-1 position in the element array, at the index location
     * in the min heap array. Will not allow insertions at
     *
     * @param index location of where the priority will be stored in the min heap
     */
    public void Insert( int index ){

        int currentPos = index; //Set the current position to the index
        int parentPos = (currentPos / 2); //Initialize the parent relative to the index
        int newNum = ElementArray[currentPos-1]; //Store the new priority from the element array

        if(QueueArray[index] == 0){ //If the current index is available (i.e. not already full)
            QueueArray[index] = newNum; //Store the priority at the position
            while (QueueArray[parentPos] > newNum) { //Continue if the parent is greater than the new priority
                QueueArray[currentPos] = QueueArray[parentPos]; //Set the current index to the parent's value
                QueueArray[parentPos] = newNum; //Set the parent's index to the new priority
                currentPos = parentPos; //Current index is now at the parent
                parentPos = (currentPos / 2); //Update the parent index
            }
        }

    }


    /** This method stores then removes the highest priority item from the queue. Followed by
     * rearranging the array to maintain its min heap structure. If the tree has no values,
     * then an error is returned.
     */
    public int deleteMin(){

        int minNum = QueueArray[1]; //Store the highest priority
        if(minNum == 0){ //If empty tree
            return -1; //Return error
        }
        QueueArray[1] = QueueArray[elementAmount]; //Set the root to the last item
        QueueArray[elementAmount] = 0; //Remove the last item
        elementAmount--; //Decrement the amount of elements due to removal

        int currentPos = 1; //Initialize current index at root
        int currentItem;
        int smallestItem;

        while((currentPos*2) < elementAmount){ //Loop while current index has a left child

            currentItem = QueueArray[currentPos]; //Store the item at the current index

            if(((currentPos*2)+1) > elementAmount){ //If there is no right child
                smallestItem = currentPos*2; //Smallest item is set to the left child by default
            }
            else{
                smallestItem = Math.min(QueueArray[currentPos*2], QueueArray[(currentPos*2)+1]); //Set smallest item to minimum between the children
            }

            if(currentItem < smallestItem) break; //Stop if the children are bigger than the current item
            else {
                if(smallestItem == QueueArray[(currentPos*2)+1]) { //If the right child was smaller
                    QueueArray[currentPos] = smallestItem;  //Set the current index to the smaller value
                    QueueArray[(currentPos * 2) + 1] = currentItem; //Set the rightChild to the previously stored value
                    currentPos = (currentPos * 2) + 1; //Update the index
                } else {
                    QueueArray[currentPos] = smallestItem; //Set the current index to the smaller value
                    QueueArray[currentPos * 2] = currentItem; //Set the leftChild to the previously stored value
                    currentPos = currentPos * 2; //Update the index
                }
            }
        }

        return minNum;
    }

    /** This method outputs all items in the min heap array Priority Queue in PreOrder traversal
     *
     * @param currentIndex The index to start the traversal from
     * @param txtfile The text file to write out the priorities to
     */
    public void traverse(int currentIndex, ASCIIOutputFile txtfile){

        if(QueueArray[currentIndex] == 0){}//In case of an empty list, or invalid starting position
        else {
            txtfile.writeInt(QueueArray[currentIndex]); //Write out value
            if ((currentIndex * 2) < QueueArray.length) { //If left child exists
                traverse((currentIndex * 2), txtfile); //Visit left child
            }
            if (((currentIndex * 2) + 1) < QueueArray.length) { //If right child exists
                traverse(((currentIndex * 2) + 1), txtfile); //Visit right child
            }
        }

    }

}
