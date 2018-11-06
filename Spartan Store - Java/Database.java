package Spartan;

/** This interface represents the database containing and intializing most of the information important to
  * the store. It includes an object of the store catalogue and all customer information.
  * 
  * @author Daniel Sokic
  * 
  * @version  1.0  (Mar.2018)                                                   */

public interface Database{
  
  
  /**This method returns true if the specified username and password is correct. Will return false
    * if no pairing is found within the database.
    * 
    * @param username the username of the customer
    * 
    * @param password the password of the customer
    * 
    * @return boolean if the username and password is correct for the user  */
  
  public boolean checkPassword( String username, String password );
  
 /**This method adds a customer to the linked list of customers. Will
    * return an error if the customer's name is already in the database
    * 
    * @param newCustomer the customer to be added
    * 
    * @exception AlreadyExistException user's name was already in the database */
  
  public void addNewCustomer( StoreCustomer newCustomer );
  
  //This method returns the catalogue intialized by the class
  
  public StoreCatalogue getCatalogue();
  
  /**This method returns the specified Customer by ID
    * 
    * @param userID the ID of the customer
    * 
    * @return StoreCustomer the customr associated with the ID  */
  
  public StoreCustomer getCustomer( String userID );
  


} //LoginDatabase