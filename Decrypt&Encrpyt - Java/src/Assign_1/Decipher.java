package Assign_1;

import BasicIO.*;
import java.util.ArrayList;

/** This class receives a .txt file containing the length of the ciphered text,
 * the length of the known word, the new cipher value, the ciphered text, and
 * the known word in this order.Having this information, outputs the
 * decoded text, then outputs the text ciphered with the new cipher value. 
 * 
 * @author Daniel Sokic
 * @version 1.0 (September 17, 2018)
 */
public class Decipher {
    
    //instance variables
    
    private final ASCIIDataFile textfile; //The textfile being read from
    private ArrayList<Character> plaintext; //Contains the characters of all known plaintext
    private ArrayList<Character> ciphertext;//Contains the ciphered characters
    private int cipherlength;//The length of the ciphered text
    private int knownlength;//The length of the known text from 'textfile'
    private int newcipher;//The new cipher used once the full plaintext is known
    private ArrayList<Integer> permutation;//The permutation used to decipher ciphertext
    private final ArrayList<Character> check;//The array list used to check against
                                             //the known array list
    private ArrayList<Character> nextSection;//Used to represent the next section
                                             //of ciphertext of size 'knownlength'
    private final LetterNode plainNode;//The header node of the plaintext structure
    private LetterNode EndNode;//The tail node of the plaintext structure
    
    /**
     * This constructor calls the 'readFile' method to initialize all variables
     * attainable from the ASCIIDataFile. Then, using these variables, forms the
     * first part of the ciphertext and initialize the base permutation array
     * list. After calling the 'permute' method to attain the proper permutation,
     * the constructor applies the permutation to the rest of ciphertext in
     * increments of 'knownlength'. As the permutations occur, the characters are
     * added to the 'plaintext' array list. Then, appended to a string and printed
     * out, the characters are placed in a circularly linked structure. Wherein
     * they are ciphered into a new string using the 'newcipher' variable.
     * This string is printed out and the program is closed.
     */
    public Decipher (){
        
        //local variables
        textfile = new ASCIIDataFile();
        readFile();
        check = new ArrayList<>(knownlength);
        permutation = new ArrayList<>(knownlength);
        ArrayList<Character> currenttext = new ArrayList<>(knownlength);
        
        //statements
        
        for(int i=0; i<knownlength; i++){
           currenttext.add(ciphertext.get(i));
           permutation.add(0);
        }
        permutation = permute(knownlength, 0, 1, currenttext, plaintext, check, permutation);
        
        for(int q=knownlength; q<ciphertext.size(); q+=knownlength){
            currenttext.clear();
            for(int i=q; i<q+knownlength; i++){
                currenttext.add(ciphertext.get(i));
            }
        
            nextSection = applyPermute(permutation,currenttext);
        
            for(int i=0; i<nextSection.size(); i++){
                plaintext.add(nextSection.get(i));
            }
        }
        
        String fullText = "";
        for(int i=0; i<plaintext.size(); i++){
            fullText = fullText + plaintext.get(i).toString();
        }
        System.out.println(fullText);
        
       
        plainNode = AssembleWord(fullText,0,fullText.length());
        EndNode.next = plainNode;
        
        String newciphertext = Encode(newcipher);
        
        System.out.println(newciphertext);
        
        System.exit(0);
        
    }
    /**
     * This method reads from an ASCII file. The three reads will set each integer
     * as the length of the ciphertext, the length of the known text, and the new
     * encryption key respectively. The next two string read are taken as the cipher
     * text and known text respectively. Their characters are appended to array 
     * lists for later use.
     */
    private void readFile(){
        
        cipherlength = textfile.readInt();
        knownlength = textfile.readInt();
        newcipher = textfile.readInt();
        String cipheredtext = textfile.readString();
        ciphertext = new ArrayList<>(cipherlength);
        for(int i=0; i<cipherlength; i++){
            ciphertext.add(cipheredtext.charAt(i));
        }
        String knowntext = textfile.readString();
        plaintext = new ArrayList<> (knownlength);
        for(int i=0; i<knownlength; i++){
            plaintext.add(knowntext.charAt(i));
        }
        

    }
    
    /**
     * This method takes in a string, its length, and where to start to form
     * a link structure of characters retrieved from the string. The global
     * variable "EndNode" is used to hold the final character and to reference
     * the header node after the method completes. The method recursively forms
     * the structure by calling on itself with the starting position being
     * incremented by each call. Eventually reaching the final character and
     * allowing the method to return the header Node.
     * 
     * @param word The string used to form the structure
     * @param start The starting index of the string
     * @param length The length of the string
     * @return The header node of the structure of characters
     */
    
    private LetterNode AssembleWord( String word, int start, int length ){
        
        if(start == length-1){
            return EndNode = new LetterNode(word.charAt(start), null);
        }
        else{
            return new LetterNode(word.charAt(start), AssembleWord(word, start+1, length));
        }
        
    }
    
    /**
     * This method returns the string of the global plaintext ciphered with the
     * given cipher. By setting local variables p and q to the head and tail of
     * the plain text nodes, respectively, the method traverses to the specified
     * cipher character, adds it to the local newText variable, and removes it from
     * the plaintext structure. Once there's only one character left, the loop 
     * breaks and its appended to the end of the string. The string is then returned 
     * and the method completes.
     * 
     * @param cipher The amount traversed before the character is appended and
     *               removed from the plain text structure.
     * @return A string of the plain text ciphered with the given parameter
     */
    
    private String Encode( int cipher ){
        String newText = "";
        LetterNode p = plainNode;
        LetterNode q = EndNode;
        while(p.next != q.next){
            int i = 0;
            while( i != cipher-1 ){
                q = q.next;
                p = p.next;
                i += 1;
            }
            newText += Character.toString(p.item);
            q.next = p.next;
            p = q.next;
        }
        newText += Character.toString(p.item);
        return newText;
    }
    
    /**
     * This method returns a character array list which is the result of applying
     * an array list of permutation to a given character array list.
     * 
     * @param permutation the array list indicating how characters should be
     *                    arranged
     * @param text the array list of characters which will have the permutations
     *             applied to
     * @return a character array list of the permutation applied to the given text 
     */
    
    private ArrayList<Character> applyPermute( ArrayList<Integer> permutation, ArrayList<Character> text ){
        
        ArrayList<Character> newText = new ArrayList<>(text.size());
        
        for(int i=0; i<permutation.size(); i++){ 
            newText.add(i, text.get(permutation.get(i)-1));
        }
        
        return newText;
    }
    
    /** 
     * -- Purpose and First Call--
     * This method's purpose is to take in a base permutation array list (of size
     * k and filled with 0) and populate it with the permutations necessary to retrieve
     * the arrangement of characters located in 'known' from 'vals'. This is done
     * recursively by first taking the length of 'vals'. Typically this is greater than
     * 1, so for most cases, the character in the indexed position is appended to the empty 'check'
     * arrayList. Along with setting its relative position in the permutation
     * arrayList to the current count.The count is where that character
     * should be located to achieve 'known'. This will vary and cycle through each
     * character in 'vals' until the correct assortment is found. Afterwards, a 
     * subset of 'vals' is created, which has every character but the one appended.
     * 
     * --Recursion--
     *  Then the method calls on itself, shortening the length and increasing the count
     * both by one, changing each relative position to the appropriate count. Until
     * the length reaches 1, wherein the final character is appended and the count 
     * applied. Then the 'check' list is compared to the 'known' list, if they match,
     * then the permutations are returned and quickly end the method by not checking any
     * further possibilities. However, if incorrect, then the recently appended 
     * character is removed and the permutation count is set back to 0. A null
     * value is returned, which indicates to the previous call that it needs to reset
     * its assigned values and increment the index. Although, if no correct permutations are
     * found and the length > 1, then a failsafe returns null to the previous call.
     * Cycling through each possible combination until the correct permutation is found.
     * 
     * --Algorithm Explanations--
     * (1)To ensure that overwriting doesn't occur when inputting the 'count' into
     * the permutation array list. The '0's are used as markers to indicate which one
     * should be replaced. For instance, if the permutation is of size 4, and already
     * has [0,4,2,0], the index of the used character will be used to determine which
     * 0 should be replaced. Given the characters [a,d], using 'd', the index would 
     * equal 1, which indicates the second 0 should be replaced with the current count.
     * Any value within the permutation that isn't 0 is skipped over and not tracked.
     * 
     * 
     * 
     * 
     * 
     * @param length the number of elements in 'vals'
     * @param index the current index
     * @param count the amount in which a ciphered character must move to match
     *              match the known list
     * @param vals the ciphered characters
     * @param known the properly arranged characters
     * @param check the list used to compare different permutation against the
     *              known word
     * @param permutes the permutation list
     * @return the permutations necessary to have 'vals' match 'known'.
     */
    
    
    private ArrayList<Integer> permute( int length, int index, int count, ArrayList<Character> vals, ArrayList<Character> known, ArrayList<Character> check, ArrayList<Integer> permutes){
        
        if(length == 1){
            
            check.add(vals.get(0));

            int i = 0;
                for(;;){
                    if(permutes.get(i) == 0){
                        permutes.set(i, count);
                        break;
                    }
                    else{
                        i += 1;
                    }
                    
                }
            
                
            if(check.equals(known)){
                return permutes;
            }
            else{
                permutes.set(i, 0);
                check.remove(check.size()-1);
                return null;
            }
            
            
        }
        else{
            for(;;){
                check.add(vals.get(index));
                int iterator = 0;
                int zerocount = 0;
                for(;;){ //Algorithm Explanation (1)
                    if(permutes.get(iterator) == 0){
                        if(zerocount == index){
                            permutes.set(iterator,count);
                            break;
                        }
                        else{
                            zerocount +=1;
                            iterator += 1;
                        }
                    }
                    else{
                        iterator += 1;
                    }
                }
                
                ArrayList<Character> v = (ArrayList<Character>) vals.clone();
                v.remove(index); //Removes only the specific index value, which allows
                                 //for gradual incrementation through all possible
                                 //permutations
                ArrayList<Integer> test = permute(length-1, 0, count+1, v, known, check, permutes);
                
                if(test != null ){ //Ensures that the correct permutation is sent
                    return test;   //back immediately after being found
                } 

                check.remove(check.size()-1);
                permutes.set(iterator,0);
                
                index += 1;
                
                if(index == length){ //Avoids an OutOfBounds Exception from occuring
                    return test;     //when the index goes above the last element index
                }
            }
        } 
    }
        
    
    
    
    public static void main(String[] args) { Decipher c = new Decipher();};
    
} //Decipher
