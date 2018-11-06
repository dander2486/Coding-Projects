package Spartan;


/** This class represents the exception that occurs when an attempt is made to
  * create a new customer account with pre-existing credentials in the database.
  * 
  * @author Daniel Sokic
  * 
  * @version  1.0  (Mar.2018)                                                   */

public class AlreadyExistException extends RuntimeException { 

  private static final long  serialVersionUID = 99990001L;

}