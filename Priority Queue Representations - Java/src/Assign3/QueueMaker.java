package Assign3;
import BasicIO.*;
/** This main class reads in a file, opens an output file, and initializes 3 different implementations of Priority Queues.
 * Each implementation's traversal and min removal are then tested, their outputs written to the designated output file.
 * Afterwards, another file is read in with 10,000 random numbers. Each of the implementations are timed to insert then
 * remove 50, 100, 1,000, 5,000, and  10,000 of these numbers. The times are written to the output file.
 *
 * ****** THIS PROGRAM USES IntelliJ *******
 *
 * @author Daniel Sokic (6164545)
 * @version 1.0 (November 13, 2018)
 */

public class QueueMaker {

    int numOfItems; //Total number of elements
    int[] NumArray; //Array of all elements
    ASCIIDataFile txtfile; //File being read from
    ASCIIOutputFile outfile; //FIle being written to

    /** This constructor reads from a file to receive all needed information to then initialize 3
     * implementations of a Priority Queue.
     */
    public QueueMaker(){

        ReadFile(); //Choose initial test file

        outfile = new ASCIIOutputFile(); //Choose overall output file

        outfile.writeString("PART A\nWith " + numOfItems + " items: \n");

        LinkedPriorityQueue newQ = new LinkedPriorityQueue(NumArray); //Initialize Linked Queue

        ArrayHeapPriorityQueue newQ2 = new ArrayHeapPriorityQueue(numOfItems, NumArray); //Initialize Array Heap Queue

        TreeHeapPriorityQueue newQ3 = new TreeHeapPriorityQueue(numOfItems, NumArray); //Initialize Tree Heap Queue

        outfile.writeString("Linked Priority Queue Traversal: \n\t");

        newQ.traverse(outfile); //Traverse Linked Queue

        outfile.writeString("\n\tMin Heap Array Priority Queue PreOrder Traversal: \n\t");

        newQ2.traverse(1,outfile); //Traverse Array Heap Queue

        outfile.writeString("\n\tMin Heap Tree Priority Queue PreOrder Traversal: \n\t");

        newQ3.traverse(newQ3.root,outfile); //Traverse Tree Heap Queue

        outfile.writeString("\n\tLinked Priority Queue with " + numOfItems + " min removal(s): \n\t");

        for(int i=0; i<numOfItems; i++){
            outfile.writeInt(newQ.deleteMin()); //Remove all items from Linked Queue
        }

        outfile.writeString("\n\tMin Heap Array Priority Queue with " + numOfItems + " min removal(s): \n\t");

        for(int i=0; i<numOfItems; i++){
            outfile.writeInt(newQ2.deleteMin()); //Remove all items from Array Heap Queue
        }

        outfile.writeString("\n\tMin Heap Tree Priority Queue with " + numOfItems + " min removal(s): \n\t");

        for(int i=0; i<numOfItems; i++){
            outfile.writeInt(newQ3.deleteMin()); //Remove all items from Tree Heap Queue
        }

        ReadFile(); //Choose random number file containing 10,000 numbers

        outfile.writeString("\nPART B\nTime Taken to: ");

        outfile.writeString("\n\tInsert then remove 50 random numbers from: ");

        CheckTime(50); //Time when n=50

        outfile.writeString("\n\tInsert then remove 100 random numbers from: ");

        CheckTime(100); //Time when n=100

        outfile.writeString("\n\tInsert then remove 1,000 random numbers from: ");

        CheckTime(1000); //Time when n=1000

        outfile.writeString("\n\tInsert then remove 5,000 random numbers from: ");

        CheckTime(5000); //Time when n=5000

        outfile.writeString("\n\tInsert then remove 10,000 random numbers from: ");

        CheckTime(10000); //Time when n=10000

        outfile.close(); //Close output file

        System.exit(1);


    }

    /** This method reads a file, initializes its data, then closes the file it read from.
     *
     * *Note: file.readInt() was not used for each integer in case of unexpected test file formats
     *        (i.e. having spaces in between each number instead of \t)
     */
    private void ReadFile(){

        txtfile = new ASCIIDataFile(); //Initialize input file

        numOfItems = txtfile.readInt(); //First number read is the total amount of elements

        NumArray = new int[numOfItems]; //Initialize array to contain the following elements

        String currentInt = ""; //Will store the integer to be added to the array

        char currentChar; //The next primitive char that is read from the file

        int index = 0; //Current index of the element array

        while(!txtfile.isEOF()){ //While the end of file isn't reached

            currentChar = txtfile.readC(); //Read in the next primitive char
            if(currentChar >= '0'){ //If it is a digit
                currentInt+= Character.toString(currentChar); //Append it to the currentInt string
            }
            else{ //Otherwise
                if(!currentInt.equals("")){ //If a digit was appended to the string
                    NumArray[index] = Integer.parseInt(currentInt); //Convert the string to an int and insert into the array
                    index++; //Increment index
                    currentInt = ""; //Reset the string
                }
            }
        }

        txtfile.close(); //Close file after data is read
    }

    /** Writes out the time taken to insert and remove n items from the different implementations of
     * a Priority Queue
     *
     * @param currentNum the amount of items to insert then remove
     */
    private void CheckTime(int currentNum){

        int[] currentArray = new int[currentNum]; //Initialize array of n size

        for(int i=0; i<currentArray.length; i++){
            currentArray[i] = NumArray[i]; //Copy over n items from array of 10,000
        }

        outfile.writeString("\n\t\tThe Linked Priority Queue: ");

        long currentTime = System.nanoTime(); //Take current time

        LinkedPriorityQueue TimeQ1 = new LinkedPriorityQueue(currentArray); //Create Queue/Insert all items

        for(int i=0; i<currentNum; i++){
            TimeQ1.deleteMin();//Remove n items
        }

        currentTime = System.nanoTime() - currentTime; //Take current time and calculate difference from previous call

        outfile.writeString(""+ currentTime+ " nanoseconds"); //Write out result

        outfile.writeString("\n\t\tThe Min Heap Array Priority Queue: ");

        currentTime = System.nanoTime(); //Take current time

        ArrayHeapPriorityQueue TimeQ2 = new ArrayHeapPriorityQueue(currentNum, currentArray); //Create Queue/Insert all items

        for(int i=0; i<currentNum; i++){
            TimeQ2.deleteMin(); //Remove n items
        }

        currentTime = System.nanoTime() - currentTime; //Take current time and calculate difference from previous call

        outfile.writeString(""+ currentTime+ " nanoseconds"); //Write out result

        outfile.writeString("\n\t\tThe Min Heap Tree Priority Queue: ");

        currentTime = System.nanoTime(); //Take current time

        TreeHeapPriorityQueue TimeQ3 = new TreeHeapPriorityQueue(currentNum, currentArray); //Create Queue/Insert all items

        for(int i=0; i<currentNum; i++){
            TimeQ3.deleteMin(); //Remove n items
        }

        currentTime = System.nanoTime() - currentTime; //Take current time and calculate difference from previous call

        outfile.writeString(""+ currentTime+ " nanoseconds"); //Write out result


    }

    public static void main(String args[]){
        QueueMaker c = new QueueMaker();
    }
}
