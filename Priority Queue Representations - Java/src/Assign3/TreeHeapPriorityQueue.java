package Assign3;

import BasicIO.ASCIIOutputFile;

/** This class creates a PriorityQueue represented by a min heap binary tree.
 *
 * ****** THIS PROGRAM USES IntelliJ *******
 *
 * @author Daniel Sokic (6164545)
 * @version 1.0 (November 13, 2018)
 */

public class TreeHeapPriorityQueue {

    TreeHeapQueueItem root = null; //Root of the tree
    int TotalElements; //Total elements that are present in the tree
    double TreeDepth; //Current max depth of the tree
    double TreeThreshold; //Amount needed at the bottom of the tree to be considered full
    double BottomTreeCount; //Current amount of elements at the bottom of the tree

    /** This constructor takes the total number of elements to be added and integer array
     * of elements to construct the tree.
     *
     * @param numOfItems total elements to be added
     * @param NumArray array of all elements
     */
    public TreeHeapPriorityQueue( int numOfItems, int[] NumArray){

        TotalElements = numOfItems;
        buildTree(NumArray);

    }

    /** This method takes in an integer array and inserts each one into the tree of the class.
     * After all the insertions are complete, the data calculated is assigned to its appropriate
     * global variable.
     *
     * @param elements array of all elements
     */
    private void buildTree(int[] elements){

        int index = 0; //Start at the beginning of the element array
        int bottomCount = 0; //Null tree being created, so there are 0 elements at the bottom
        int threshold = 1; //To be able to move to the next depth, there needs to be a root (i.e. 1 element)
        double maxDepth = 0;//The current maximum depth that can be reached

        while (index < elements.length) { //For all elements in the array

            if(root == null){ //Empty tree check
                root = new TreeHeapQueueItem(elements[index]); //Null tree so insert the first item to the array
            }
            else {
                insert(elements[index], root, bottomCount, threshold); //Insert the next item to the bottom
            }

            index++; //increment index
            bottomCount++; //increment bottom count
            if(bottomCount == threshold){ //Is the bottom full?
                maxDepth++; //The next depth can be traversed
                threshold = threshold*2; //The next amount of elements to fill the bottom
                bottomCount = 0;//Reset the bottom count
            }

        }

        TreeDepth = maxDepth; //The local variable would represent the tree's depth at this point
        TreeThreshold = threshold; //The local variable would represent the tree's next depth threshold at this point
        BottomTreeCount = bottomCount; //The local variable would represent the tree's bottom count at this point


    }

    /** Given the new priority to add, the current item being viewed, the current amount of items at the bottom of the
     * tree, and the current threshold until the bottom is considered full, this method inserts the priority into the
     * tree at the leftmost available position. Once inserted, the value is trickled up to maintain the min heap
     * condition.
     *
     * @param newPriority
     * @param current
     */
    public void insert( int newPriority, TreeHeapQueueItem current, int currentBottom, int currentThreshold){

        if(current.leftChild == null){ //If left child is not initialized
            current.leftChild = new TreeHeapQueueItem(newPriority); //Initialize left child with new priority
            if(newPriority < current.priority){ //If the new priority is less than the current item's
                current.leftChild.priority = current.priority; //Set the left child's priority to the current item's priority
                current.priority = newPriority; //Set the current item's priority to the new priority
            }
        }
        else if(current.rightChild == null){ //If right child is not initialized
            current.rightChild = new TreeHeapQueueItem(newPriority); //Initialize right child with new priority
            if(newPriority < current.priority){ //If the new priority is less than the current item's
                current.rightChild.priority = current.priority; //Set the right child's priority to the current item's priority
                current.priority = newPriority; //Set the current item's priority to the new priority
            }
        }
        else {
            if (currentBottom < currentThreshold / 2) { //If the left side of the bottom of the tree is not full
                insert(newPriority, current.leftChild, currentBottom, (currentThreshold / 2)); //Move to the left child and divide the full check in half
                if (newPriority < current.priority) { //If the inserted item is less than the current item's priority
                    current.leftChild.priority = current.priority; //Set the left child's priority to the current item's priority
                    current.priority = newPriority; //Set the current item's priority to the new priority
                }
            }
            else { //Otherwise
                insert(newPriority, current.rightChild, (currentBottom - (currentThreshold / 2)), (currentThreshold / 2)); //Move to the right child, divide the full check in half, and remove the amount of items from the left side from the current bottom
                if (newPriority < current.priority) { //If the inserted item is less than the current item's priority
                    current.rightChild.priority = current.priority; //Set the right child's priority to the current item's priority
                    current.priority = newPriority; //Set the current item's priority to the new priority
                }
            }
        }
    }

    /** This method ensures the tree is not empty, and that the global tree variables are updated in case of previous
     * removals or insertions. Then stores the smallest priority from the root, finds the leftmost bottom priority to
     * remove from the bottom and insert as the root. Then, trickles down to mainatin the min heap condition. After
     * this is complete, the priority is returned.
     *
     * @return smallest priority in the tree
     */
    public int deleteMin(){

        if(root == null){ //If empty tree
            return -1; //Return error
        }

        if(BottomTreeCount == 0){ //If the bottom count was made zero from a previous insertion or deletion
            TreeThreshold = TreeThreshold/2; //Update threshold
            BottomTreeCount = TreeThreshold; //Update bottom count
            TreeDepth--; //Decrement tree depth
        }

        int minNum = root.priority; //Store smallest priority
        TreeHeapQueueItem ptr = root; //Point to the root
        int swapNum;
        double currentDepth = 0; //Initialize current depth
        double currentThreshold = TreeThreshold; //Store tree threshold locally
        double currentBottomcount = BottomTreeCount; //Store bottom count locally

        if(TreeDepth == 0){ //If last item was taken
            root = null; //Set root to null
            return minNum; //Return the minimum
        }
        else {
            for (;;) { //Until leftmost child found
                if (currentBottomcount <= currentThreshold / 2) { //If the bottom's left side is full or less
                    if (currentDepth + 1 == TreeDepth) { //If the next traversal is to the bottom of the tree
                        root.priority = ptr.leftChild.priority; //Set the child's priority to the root
                        ptr.leftChild = null; //Delete the child
                        break;
                    }
                    else {
                        ptr = ptr.leftChild; //Traverse to next child
                        currentDepth++; //Increment current depth
                        currentThreshold = currentThreshold / 2; //Divide the threshold by 2
                    }
                }
                else {
                    if (currentDepth + 1 == TreeDepth) { //If the next traversal is to the bottom of the tree
                        root.priority = ptr.rightChild.priority; //Set the child's priority to the root
                        ptr.rightChild = null; //Delete the child
                        break;
                    }
                    else {
                        ptr = ptr.rightChild; //Traverse to next child
                        currentDepth++; //Increment current depth
                        currentBottomcount = currentBottomcount - (currentThreshold / 2); //Remove the amount of left children from the bottom count
                        currentThreshold = currentThreshold / 2; //Divide the threshold by 2
                    }
                }
            }
        }

        BottomTreeCount--; //Decrement global bottom count to account for removal

        ptr = root; //Set pointer to root again
        swapNum = root.priority; //Store the current root's priority

        for(;;){ //Until completion
            if(ptr.rightChild == null && ptr.leftChild == null){ //If current item has no children
                return minNum;
            }
            else if(ptr.rightChild == null){ //If there is only no right child
                if(swapNum > ptr.leftChild.priority){ //If the trickled down priority is greater than the left child's priority
                    ptr.priority = ptr.leftChild.priority; //Set the current item's priority to the child's priority
                    ptr.leftChild.priority = swapNum; //Set the child's priority to the trickled down priority
                }
                return minNum;
            }
            else if(ptr.leftChild == null){ //If there is only no left child
                if(swapNum > ptr.rightChild.priority){ //If the trickled down priority is greater than the right child's priority
                    ptr.priority = ptr.rightChild.priority; //Set the current item's priority to the child's priority
                    ptr.rightChild.priority = swapNum; //Set the child's priority to the trickled down priority
                }
                return minNum;
            }
            else if((ptr.leftChild.priority <= ptr.rightChild.priority) && (swapNum >= ptr.leftChild.priority)){ //If the left child is equal to or less than the right child, and the trickled down priority is greater than or equal to the left child
                ptr.priority = ptr.leftChild.priority; //Set the current item's priority to the child's priority
                ptr.leftChild.priority = swapNum; //Set the child's priority to the trickled down priority
                ptr = ptr.leftChild; //Traverse to the child
            }
            else if((ptr.rightChild.priority < ptr.leftChild.priority) && (swapNum >= ptr.rightChild.priority)){ //If the right child is less than the left child, and the trickled down priority is greater than or equal to the right child
                ptr.priority = ptr.rightChild.priority; //Set the current item's priority to the child's priority
                ptr.rightChild.priority = swapNum; //Set the child's priority to the trickled down priority
                ptr = ptr.rightChild; //Traverse to the child
            }
            else{
                return minNum;
            }
        }
    }

    /** This method writes out the priorities in the tree while performing a PreOrder traversal
     *
     * @param currentItem the current tree item visiting
     * @param txtfile the text file to write out to
     */
    public void traverse(TreeHeapQueueItem currentItem, ASCIIOutputFile txtfile){

        if(currentItem == null){} //If null item, then do nothing
        else{
            txtfile.writeInt(currentItem.priority); //Write out the current priority
            if(currentItem.leftChild != null){ //If there is a left child
                traverse(currentItem.leftChild, txtfile); //Traverse to the left child
            }
            if(currentItem.rightChild != null){ //If there is a right child
                traverse(currentItem.rightChild, txtfile); //Traverse to the right child
            }
        }
    }
}
