package Spartan;

import java.io.*;

/** This class represents a customer associated with the store who will interact with and hold
  * a cart. A customer will have an ID, username, password, and address. 
  * 
  * @author Daniel Sokic
  * 
  * @version 1.0 (Mar. 2018)                                             */

public class StoreCustomer implements Customer, Serializable {
  
  private static final long  serialVersionUID = 99990010L;
  
  private String Userid;
  private String userName;
  private String address;
  private String password;
  private CustomerCart Usercart;
  
  //This method takes the parameters given and assigns them values corresponding to the instance variables
  
  public StoreCustomer( String id, String user, String adres, String pass, CustomerCart c ){
    
    Userid = id;
    userName = user;
    address = adres;
    password = pass;
    Usercart = c;
    
  }
  
  /** This method returns the customer's ID.
    *
    * @return  String  the userID.                       */
  
  public String getID (){
    
    return Userid;
    
  }
  
  /** This method returns the customer's password.
    *
    * @return  String  the password.                       */
  
  public String getPassword(){
    
    return password;
    
  }
  
  /** This method returns the cart asssociated with the customer
    *
    * @return  Cart  the cart.                       */
  
  public CustomerCart getCart(){
    
    return Usercart;
    
  }
  
  // This method updates the cart associated with the customer
  
  /** This method returns the address asssociated with the customer
    *
    * @return  String  the address.                       */
  
  public String getAddress(){
    
    return address;
    
  }
  
  /** This method returns the name asssociated with the customer
    *
    * @return  String  the user's name.                       */
  
  public String getUserName(){
    
    return userName;
    
  }
  
  // This method updates the cart associated with the customer
  
  public void setCart( CustomerCart newcart ){
    
    Usercart = newcart;
    
  }
  
  
} //Customer