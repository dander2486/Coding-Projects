package Spartan;

import BasicIO.*;
import java.io.*;

/**This class is the main class that intializes all necessary objects to
  * run the Spartan website.
  * 
  * @author Daniel Sokic
  * 
  * @version  1.0  (Mar.2018)                          */

public class StoreSetup implements Serializable {
  
  private static final long  serialVersionUID = 99990013L;
  
  private StoreDatabase storeData;
  private ASCIIDataFile datafile;
  private BinaryOutputFile StoreFile;
  
  /** This constructor initializes an ASCIIDataFile which is then used to intializes a StoreDatabase object,
    * which contains two linked lists of the catalogue and the registered customers. Afterwards, this object
    * is written to a BinaryOutputFile for use by the CustomerOrder class. */
  private StoreSetup() { 
    
    datafile = new ASCIIDataFile();
    storeData = new StoreDatabase(datafile);
    StoreFile = new BinaryOutputFile();
    
    StoreFile.writeObject(storeData);
    
    StoreFile.close();
    
  }
  
  public static void main(String[] args) { new StoreSetup(); }; 
  
}
