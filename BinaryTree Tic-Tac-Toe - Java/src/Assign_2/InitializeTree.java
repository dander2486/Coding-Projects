package Assign_2;

import BasicIO.*;

/** This class creates a General Tree of all possibilities of tic tac toe, then
 * uses it to play an optimal game of tic tac toe against a human user.
 *
 * ****** THIS PROGRAM USES NETBEANS *******
 * 
 * 
 * @author Daniel Sokic (6164545)
 * @version 1.0 (October 17,2018)
 */
public class InitializeTree {

    char[][] blankBoard; //A 2D array of ' '
    GameNode currentNode; //The parent node of the tic tac toe tree
    BasicForm gameDisplay; //The basic form display of the tic tac toe game
    
    /** The constructor creates a blank board, then initializes all possibilities
     * of tic tac toe with currentNode as the parent. Afterwards, the tree is
     * traversed in preOrder to remove any child nodes that follow a win or loss
     * condition. These leaves are then used totaled of wins and losses in postOrder.
     * Once the tree is sorted, the game display is created and the tic tac toe
     * game begins.
     */
    public InitializeTree(){

        blankBoard = BuildBoard(3,3);
      
        currentNode = MakeTree(blankBoard, 'X', 'O', 0, 0, 0, 0);
        
        preOrderTraverse(currentNode);

        postOrderTraverse(currentNode);
        
        currentNode.firstChild = TreeSort(currentNode.firstChild);
        gameDisplay = new BasicForm();
        BuildGame();
        GameStart(currentNode.firstChild); 
        
        System.exit(0);
        
    }
    /** This method creates all possibilities of a tic-tac-toe board stored within
     * a general tree.
     * 
     * [A]Introduction, Child Cycle:
     *  With the given 2D array (param "board"), a copy is made [A1] to allow additions
     * to be made, without disturbing the original array. As the given array contains ' ',
     * the first location found empty within the copy is assigned the "nextchar" value[A2]. Then, 
     * a clone of the copy is recursively sent back to the method with the "newchar" and "prevchar" 
     * swapped [A3] so that each possibility may eventually be reached. The exchanges 
     * are continued until the array is filled, which then begins the sibling cycle. Also,
     * the x and y of the insert is given to the child,[A4] to ensure swapping with the proper
     * character when creating a sibling.//[A5]
     * 
     * [B]Sibling Cycle Explanation:
     *  The idea of a sibling is to have it be the same array as its predecessor, except
     * the last value is incremented forward one space. This is performed by taking
     * an x and y position, replacing the previous position with a ' ' and the current
     * position with the "prevchar" variable. The "prevchar" variable is used since
     * it equates to the removed character. In case of a gap, the sibx and siby
     * variables keep track of the previous sibling's swap position, so that
     * the next created sibling may increment the intended character forward.
     * If the previous sibling swapped at [2,2] then the method immediately returns the
     * Node without creating a sibling [B1]. Also, if the next position to swap is already
     * occupied, then another empty space is checked for until the end of the array [B2].
     * If no other empty space is found, the Node is returned without creating another sibling [B3].
     * Finally, if a node without a previous sibling attempts a swap at [0,0], it is immediately returned [B4].
     * 
     * [C]Sibling Cycle:
     *  Once a child is given, the method checks the sibx and siby values against
     * the current x and y position. If there was not a previous sibling, these will
     * be 0,0. If there was a previous sibling then the sibx and siby would be greater
     * than the x and y, as explained in [B], since the swap would leave an empty
     * character behind [sibx,siby][C1]. If there was a previous sibling, then the
     * next position in the array is properly found and assigned to new variables [C2].
     * As the child cycle adds a new character to the array, the x and y must be replaced
     * with a ' ' [C3]. A swap[B] occurs between [sibx,siby] and [newsibx,newsiby] [C4], followed
     * by creation of the next sibling with newsibx and newsiby[C5]. The node is returned
     * after a sibling is received[C5]. If there was no previous sibling, and x and y
     * are not at the beginning or end of the array, then the previous position
     * is found and assigned to variables[C6]. A swap occurs between [prevx,prevy]
     * and [x,y][C7], followed by the creation of a new sibling with values
     * x and y[C8].
     * 
     * 
     * @param board A 2D array of a tic-tac-toe board
     * @param nextchar The character used for creating a new board state for the child
     * @param prevchar The character used for creating a new board state for the sibling
     * @param sibx The x position of the swap performed by the previous sibling
     * @param siby The y position of the swap performed by the previous sibling
     * @param insx The x position of where the parent node inserted a character
     * @param insy The y position of where the parent node inserted a character
     * @return The top node of a general tree of tic-tac-toe boards
     */
    private GameNode MakeTree(char[][] board, char nextchar, char prevchar, int sibx, int siby, int insx, int insy){
        
        char[][] currentboard = new char[board.length][board.length];
        char[][] sentboard = new char[board.length][board.length];
        char[][] sentboard2 = new char[board.length][board.length];
        currentboard = CopyBoard(board); //[A1]
        GameNode currentGameNode = new GameNode(board, null, null);
        
        for(int y=0; y<currentboard[0].length; y++){
            
            for(int x=0; x<currentboard.length; x++){
                
                if(currentboard[x][y] == ' '){
                    
                    currentboard[x][y] = nextchar; //[A2]
                    sentboard = CopyBoard(currentboard); //[A3]
                    currentGameNode.firstChild = MakeTree(sentboard, prevchar, nextchar, 0, 0, x, y);//[A3][A4]
                    
                    if(sibx > x || siby > y){ //A.K.A Previous Sibling Check [B][C1]
                        
                        if(sibx == 2 && siby == 2){
                            return currentGameNode; //[B1]
                        }
                        
                        else{
                            int newsibx = sibx;
                            int newsiby = siby;
                            while(newsibx != 2 || newsiby != 2){ //[B2]
                            
                                if((newsibx+1) == 3){ //[C2]
                                    newsiby += 1;
                                    newsibx = 0;
                                }
                                else{
                                    newsibx += 1;
                                }
                            
                            
                                if(currentboard[newsibx][newsiby] == ' '){ //[B2]
                                    sentboard2 = CopyBoard(currentboard);
                                    sentboard2[x][y] = ' '; //[C3]
                                    sentboard2[sibx][siby] = ' ';//[C4]
                                    sentboard2[newsibx][newsiby] = prevchar;//[C4]
                                    currentGameNode.nextSibling = MakeTree(sentboard2,nextchar,prevchar,newsibx,newsiby,0,0);//[C5]
                                    return currentGameNode;//[C5]
                                }
                            }
                            return currentGameNode; //[B3]   
                        }
                    }
                    
                    else{
                        
                        if(x == 0 && y == 0){
                            return currentGameNode; //[B4]
                        }
                        
                        else{
                            int prevy = y;
                            int prevx = x;
                            
                            if(currentboard[insx][insy] == prevchar){ //[A5]
                                prevy = insy;
                                prevx = insx;
                            }
                            else{
                                for(;;){
                                    if(prevx == 0){
                                        prevy -= 1; //[C6]
                                        prevx = 2;
                                    }
                                    else{
                                        prevx -= 1; //[C6]
                                    }

                                    if(currentboard[prevx][prevy] == prevchar){
                                        break;
                                    }

                                }
                            }
                            
                            currentboard[prevx][prevy] = ' ';//[C7]
                            currentboard[x][y] = prevchar;//[C7]
                            currentGameNode.nextSibling = MakeTree(currentboard,nextchar,prevchar,x,y,0,0);//[C8]
                            return currentGameNode;
                            
                        }
                    }
                    
                }
            }
        }
        
        return currentGameNode;
        
    }
    
    /**This method performs a preOrder traversal of the given tree. As it traverses,
     * a win condition is checked for, if one is found, the children of the current
     * node are removed, if not, then the traversal continues until completion.
     * 
     * @param currentNode The node currently viewed 
     */
    private void preOrderTraverse(GameNode currentNode){
        if(currentNode == null){
        }
        else{
            if(CheckWin(currentNode.gameBoard) != 0){
                currentNode.firstChild = null;
            } 
            preOrderTraverse(currentNode.firstChild);
            preOrderTraverse(currentNode.nextSibling);
        }
    }
    
    /**This method performs a postOrder traversal of the given tree. As it traverses,
     * a win or loss condition is checked for, if a win is found, the node's win counter
     * increases by 1. Whereas if a loss was found, the node's loss counter is increased by
     * 1. To properly represent win and loss paths, the children and sibling wins and losses
     * are added to the parent's totals. The provided node will have all the number
     * of wins and losses possible.
     * 
     * **A Win = Win for 'O'  A Loss = Win for 'X'
     * 
     * @param currentNode The node currently viewed 
     */
    private void postOrderTraverse(GameNode currentNode){
        if(currentNode == null){
        }
        else{
            postOrderTraverse(currentNode.firstChild);
            postOrderTraverse(currentNode.nextSibling);
            switch(CheckWin(currentNode.gameBoard)){
                case 0:
                    break;
                case 1:
                    currentNode.losses += 1;
                    break;
                case 2:
                    currentNode.wins += 1;
                    break;
            }
            if(currentNode.firstChild != null){
                currentNode.wins += currentNode.firstChild.wins;
                currentNode.losses += currentNode.firstChild.losses;
            }
            if(currentNode.nextSibling != null){
                currentNode.wins += currentNode.nextSibling.wins;
                currentNode.losses += currentNode.nextSibling.losses;
            }
        }
    }
    /** This method returns a Node in sorted order relative to its siblings, each with
     * their children properly sorted. 
     * 
     * [A]Sibling/Horizontal Sort: 
     * First, a pointer variable references currentNode [A1], the pointer traverses 
     * through every sibling, comparing their w-l values to the max value, which 
     * initializes as the lowest possible integer value.[A2] If the w-l value is 
     * greater than the max, and if the node isn't already sorted, the w-l value 
     * is set to the max[A3]. Then, the current maximum Node is checked if it's already
     * referencing another node, if so, it's sorted value is set to false[A4]. Afterwards,
     * MaxNode references the current node, it's sorted value set to true, the next
     * node is checked in sequence [A5]. However, if the node has already been sorted,
     * or the w-l is less than the max value, the pointer moves to the next node [A6].
     * Once a MaxNode is confirmed by the traversal, it is either set to a separate
     * node [A7], or the sibling of whichever node has a null sibling [A8]. The maxNode
     * is reset to null[A9], and since the sorted value is true, the node will be skipped
     * over on the next traversal[A10]. This cycle is repeated until every node in the
     * sequence has been sorted, which is why returnCheck is set to false every time
     * an unsorted node is passed over[A11].
     * 
     * [B]Child Traversal:
     * Once the sequence has been sorted, the sorted sequence is traversed, checking
     * for sorted children in each node[B1]. If there is a child and it is unsorted,
     * then the child is recursively sent into the method and is assigned to the
     * current node's child pointer[B2]. This effectively covers the entire tree
     * of possibilities.
     * 
     * @param currentNode The first node of a list of siblings
     * @return Node in sorted order
     */
    private GameNode TreeSort(GameNode currentNode){
        
        boolean returnCheck = false;
        GameNode sortedTree = null;
        GameNode p;
        GameNode maxNode = null;
        int max;
        while(returnCheck == false){//[A11]
            returnCheck = true;
            p = currentNode; //[A2]
            max = Integer.MIN_VALUE; //[A2]
            while(p != null){
                if((p.wins - p.losses) >= max && p.sorted == false){//[A2][A10]
                    max = (p.wins - p.losses);//[A3]
                    if(maxNode != null){ //[A4]
                        maxNode.sorted = false;
                    }
                    maxNode = p;
                    maxNode.sorted = true;
                    p = p.nextSibling; //[A5]
                    returnCheck = false;//[A11]
                }
                else{
                    if(p.sorted == false){ //[A11]
                        returnCheck = false;
                    }
                    p = p.nextSibling; //[A6]
                }
            }
            if(maxNode != null){
                if(sortedTree == null){ //[A7]
                    sortedTree = maxNode;
                }
                else{ //[A8]
                    if(sortedTree.nextSibling == null){
                        sortedTree.nextSibling = new GameNode(maxNode.gameBoard, maxNode.firstChild, null);
                        sortedTree.wins = maxNode.wins;
                        sortedTree.losses = maxNode.losses;
                        sortedTree.sorted = maxNode.sorted;
                    }
                    else{
                        GameNode trav = sortedTree.nextSibling;
                        while(trav.nextSibling != null){
                            trav = trav.nextSibling;
                        }
                        trav.nextSibling = new GameNode(maxNode.gameBoard, maxNode.firstChild, null);
                        trav.nextSibling.wins = maxNode.wins;
                        trav.nextSibling.losses = maxNode.losses;
                        trav.nextSibling.sorted = maxNode.sorted;
                    }
                }
                maxNode = null; //[A9]
            }
        }
        
        GameNode childCheck = sortedTree;
        while(childCheck != null){
            if(childCheck.firstChild != null && childCheck.firstChild.sorted == false){ //[B1]
                childCheck.firstChild = TreeSort(childCheck.firstChild); //[B2]
            }
            childCheck = childCheck.nextSibling;
        }
        
        return sortedTree;
        
    }
    
    /** This method returns an int to define a win condition and which character won. The first
     * two if statements check for a diagonal win [A1][A2]. If neither are found, then
     * the columns [B] and rows [C] are checked for a win condition. A 1 indicates that
     * 'X' won, a 2 indicates 'O' won, and 0 indicates no one won.
     * 
     * @param gameBoard 2D array representing 
     * @return an integer representing the win state
     */
    
    private int CheckWin(char[][] gameBoard){
        
        if((gameBoard[0][0] == gameBoard[1][1]) && (gameBoard[1][1]== gameBoard[2][2])){ //[A1]
            if(gameBoard[0][0] == 'X'){
                    return 1;
                }
            else if(gameBoard[0][0] == 'O'){
                    return 2;
                }
        }
        
        if((gameBoard[2][0] == gameBoard[1][1]) && (gameBoard[1][1]== gameBoard[0][2])){ //[A2]
            if(gameBoard[2][0] == 'X'){
                    return 1;
                }
            else if(gameBoard[2][0] == 'O'){
                    return 2;
                }
        }
        
        for(int i=0;i<gameBoard.length;i++){
            if((gameBoard[i][0] == gameBoard[i][1]) && (gameBoard[i][1]== gameBoard[i][2])){ //[B]
                if(gameBoard[i][0] == 'X'){
                    return 1;
                }
                else if(gameBoard[i][0] == 'O'){
                    return 2;
                }
            }
            if((gameBoard[0][i] == gameBoard[1][i]) && (gameBoard[1][i]== gameBoard[2][i])){ //[C]
                if(gameBoard[0][i] == 'X'){
                    return 1;
                }
                else if(gameBoard[0][i] == 'O'){
                    return 2;
                }
            }
        }
        
        return 0;
        
    }
    
    
    
    /**This method returns a 2D array of specified height and width, filled with
     * blank space through iteration.
     * @param cols The width of the array
     * @param rows The height of the array
     * @return 2D Array of ' '
     */
    private char[][] BuildBoard(int cols, int rows){
        
        char[][] board = new char[cols][rows];
        
        for(int y=0; y<rows; y++){
            for(int x=0; x<cols; x++){
                board[x][y] = ' ';
            }
        }
        
        
        return board;
    }
    
    /**This method returns the contents of a given array within another
     * array.
     * 
     * @param boardA A 2D array
     * @return An array with the contents of boardA.
     */
        
    private char[][] CopyBoard(char[][] boardA){
        
        char[][] copiedBoard = new char[boardA.length][boardA.length];
        
        for(int y=0; y<boardA.length; y++){
            for(int x=0; x<boardA.length; x++){
                copiedBoard[x][y] = boardA[x][y]; 
            }
        }
        
        return copiedBoard;
        
    }
    
    /** This method compares the contents of two 2D arrays, returning
     * true if they contain the exact same contents.
     * 
     * @param boardA The first 2D array
     * @param boardB The second 2D array
     * @return boolean value representing whether the arrays match or not
     */
    private boolean CompareBoards(char[][] boardA, char[][] boardB){
        
        boolean match = true;
        
        for(int y=0; y<boardA.length; y++){
            for(int x=0; x<boardA.length; x++){
                if(boardA[x][y] != boardB[x][y]){
                    match = false;
                } 
            }
        }
        
        return match;
        
    }
    
    /** This method creates the form for the tic tac toe
     * game to take place on.
     */
    private void BuildGame(){
        
        int width = 210;
        int height = 210;
        int boxcount = 0;
        
        for(int y=0; y<height; y+=(height/3)){
            for(int x=0; x<width; x+=(width/3)){
                gameDisplay.addTextField(""+boxcount,1, x, y);
                boxcount+=1;
            }
        }
    }
    
    /** This method allows the user to play tic tac toe with the computer.
     * 
     * [A] Variable Explanations:
     * The variable ReadCheck is used to detect if the human user has input an 'X' value. 
     * If there is an 'X' value, then the variable is marked true[A1]. Any other values
     * or subsequent 'X's are ignored. While TurnNum is used to track what turn has passed
     * in order to distinguish a draw [A2]. The index variable is used within the game loop
     * to track the indices of the basicForm text areas[A3]. Currentboard is used to represent
     * the current state of the basic form table[A4]. While subNode is used as a pointer for
     * traversal, so the parentNode may be used for replay calls[A5].
     * 
     * [B] Game Loop:
     * Once all the variables are initialized, and the board is cleared from a potential
     * previous game[B1]. The method checks for an input of 'X' to represent the user's
     * move and will not progress until at least one is found [B2]. Once the player
     * has made their move, the text areas are assigned their respective locations in
     * the currentBoard[B3]. After every user turn, the method checks for a draw or
     * win condition and suggests to quit or play again if either is found [B4].
     * If not, the method then compares the current board to each sibling within subNode[B5],
     * once a matching sibling is found, the node traverses to its firstChild[B6].
     * As the firstChild has the highest w-l value of its siblings, its board is used
     * to represent the computer's move[B7]. A lose condition is checked and suggests
     * to play again or quit if found [B8]. If not, the node traverses to its firstChild
     * for future comparisons[B9] and the cycle repeats until a win,lose, or draw is found.
     * 
     * **Replay is accomplished through recursive call of the method with the parent
     * node being reused
     * 
     * @param currentNode The parent node of the tree of tic tac toe possibilities
     */
    private void GameStart(GameNode currentNode){
        
        int TurnNum = 0;
        boolean ReadCheck = false;
        int index;
        char[][] currentBoard = new char[currentNode.gameBoard.length][currentNode.gameBoard.length];//[A4]
        GameNode subNode = currentNode;//[A5]
        
        for(int i=0; i<9; i++){ //[B1]
            gameDisplay.setEditable(""+i, true);
            gameDisplay.clear(""+i);
        }
        
        for(;;){
            while(ReadCheck != true){ //[B2]
            gameDisplay.accept("NextTurn");
                for(int i=0; i<9; i++){
                    if(gameDisplay.isEditable(""+i)){
                        if(gameDisplay.readChar(""+i) == 'X'){
                            if(ReadCheck == false){
                                ReadCheck = true; //[A1]
                                gameDisplay.setEditable(""+i, false);
                            }
                            else{
                                gameDisplay.clear(""+i);
                            }
                        }
                    }
                }
            }
            ReadCheck = false;
            
            index = 0; //[A3]
            for(int y=0; y<currentBoard.length; y++){ //[B3]
                for (char[] currentBoard1 : currentBoard) {
                    if (gameDisplay.readChar(""+index) == 'X' || gameDisplay.readChar(""+index) == 'O') {
                        currentBoard1[y] = gameDisplay.readChar(""+index);
                    } else {
                        currentBoard1[y] = ' ';
                    }
                    index += 1;
                }
            }
            
            if(CheckWin(currentBoard) == 1){ //[B4]
                gameDisplay.clearAll();
                for(int i=0; i<9; i++){
                    gameDisplay.setEditable(""+i, false);
                }
                gameDisplay.writeChar("0", 'Y');
                gameDisplay.writeChar("1", 'O');
                gameDisplay.writeChar("2", 'U');
                gameDisplay.writeChar("3", 'W');
                gameDisplay.writeChar("4", 'I');
                gameDisplay.writeChar("5", 'N');
                if(gameDisplay.accept("Play Again?", "Quit") == 0){
                    GameStart(currentNode);//[A5]
                }
                return;
            }
            
            TurnNum += 1;
            
            if(TurnNum == 9){ //[A2][B4]
                gameDisplay.clearAll();
                for(int i=0; i<9; i++){
                    gameDisplay.setEditable(""+i, false);
                }
                gameDisplay.writeChar("0", 'I');
                gameDisplay.writeChar("1", 'T');
                gameDisplay.writeChar("2", 'S');
                gameDisplay.writeChar("4", 'A');
                gameDisplay.writeChar("6", 'T');
                gameDisplay.writeChar("7", 'I');
                gameDisplay.writeChar("8", 'E');
                if(gameDisplay.accept("Play Again?", "Quit") == 0){
                    GameStart(currentNode);//[A5]
                }
                return;
            }
            
            while(!(CompareBoards(currentBoard,subNode.gameBoard))){//[B5]
                subNode = subNode.nextSibling;
            }
            subNode = subNode.firstChild;//[B6]
            
            index = 0; //[A3][B7]
            for(int y=0; y<currentBoard.length; y++){
                for(int x=0; x<currentBoard.length; x++){
                    if(subNode.gameBoard[x][y] == 'O'){
                        gameDisplay.writeChar(""+index, subNode.gameBoard[x][y]);
                        currentBoard[x][y] = subNode.gameBoard[x][y];
                        gameDisplay.setEditable(""+index, false);
                    }
                    index += 1;
                }
            }
            
            if(CheckWin(currentBoard) == 2){
                gameDisplay.accept();
                gameDisplay.clearAll();
                for(int i=0; i<9; i++){
                    gameDisplay.setEditable(""+i, false);
                }
                gameDisplay.writeChar("1", 'I');
                gameDisplay.writeChar("3", 'W');
                gameDisplay.writeChar("4", 'I');
                gameDisplay.writeChar("5", 'N');
                if(gameDisplay.accept("Play Again?", "Quit") == 0){
                    GameStart(currentNode);//[A5]
                }
                return;
            }
            
            subNode = subNode.firstChild; //[B9]
            TurnNum += 1;

        }
        
    }
   
    
    public static void main(String[] args) { InitializeTree c = new InitializeTree();}
    
} //IntializeTree
