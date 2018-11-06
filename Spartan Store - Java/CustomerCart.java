package Spartan;

import java.io.*;

/** This interface represents a cart that will store all of the items a customer
  * wishes to purchase in a linked list, along with calculate the total cost of the order.
  * Each cart will be assigned an order number upon creation.
  * 
  * @author Daniel Sokic
  * 
  * @version 1.0 (Mar. 2018)                                             */

public class CustomerCart implements Cart, Serializable{
  
  private static final long  serialVersionUID = 99990003L;
  
  private Node<CartItem> buyList;
  private double totalcost;
  private int ordernum;
  private StoreCatalogue catalogue;
  
  //This constructor takes parameters for the instance variables and sets their values
  
  public CustomerCart( int oNum , StoreCatalogue cata ){
   
   buyList = null;
   totalcost = 0;
   ordernum = oNum;
   catalogue = cata;
    
  }
  
  
  /**This method adds an item to the cart by a given amount, if the item is already present in
    * the cart it will increment the count by however much indicated. Will not add the amount
    * indicated if there is not the specified amount of item.
    * 
    * @param addAmount the amount of product the user has requested
    * @param newItemNum the item number being added
    * 
    * @exception OutofBoundsException The amount requested is not present in the store */
  
  public void addItem( int addAmount , String newItemNum ){
    
    StoreItem newItem = catalogue.getItem(newItemNum);
    
    Node<CartItem> p = buyList; 
    Node<CartItem> q = null;
    
    boolean sameItemCheck = false;
    
    if(buyList == null){
      buyList = new Node<CartItem>(new CartItem(newItem.getPrice(), newItem.getDesc(), newItemNum, addAmount), null);
      totalcost += newItem.getPrice() * addAmount;
    }
    else{
    
      while( p != null ){
        if(p.item.getItemNum().equals(newItemNum)){
          sameItemCheck = true;
          break;
        }
        else{
          q = p;
          p = p.next;
        }
      }
      
      if(sameItemCheck == true){
        p.item.addQuant(addAmount);
        totalcost += newItem.getPrice() * addAmount;
      }
      else{
        q.next = new Node<CartItem>(new CartItem(newItem.getPrice(), newItem.getDesc(), newItemNum, addAmount), null);
        totalcost += newItem.getPrice() * addAmount;
        
      }
    }
  }
    
  
  /**This method removes the specified item and amount from the cart. Will
    * return an error if no items are in the cart or if the item number is not found
    * 
    * @exception NoValueException no items are in the cart or if the item number is not found */
  
  public void removeItem( int removeAmount , String ItemNum ){
    
    if( buyList == null ){
      
      throw new NoValueException();
      
    }
    
    Node<CartItem> p = buyList; 
    Node<CartItem> q = null;
    
    boolean sameItemCheck = false;
    
    while( p != null ){
      if(p.item.getItemNum().equals(ItemNum)){
        sameItemCheck = true;
        break;
      }
      else{
        q = p;
        p = p.next;
      }
    }
    
    if(sameItemCheck == true){
      totalcost -= p.item.getPrice() * p.item.getQuant();
      p.item.removeQuant(removeAmount);;
      if(p.item.getQuant() <= 0){
        if(q == null){
          buyList = buyList.next;
        }
        else{
          q.next = p.next;
        }
        
      }
      else{
        totalcost += p.item.getPrice() * p.item.getQuant();
      }
    }
    else{
      throw new NoValueException();
    }
  }
  
  /** This method returns the item at a particular position. Will return
    * an error if the position is out of bounds of the length of the cart.
    * 
    * @param itemNum the item numbet of the item needed
    * 
    * @exception NoValueException the indicated position is out of bounds  */
  
  public Item getItem( String itemNum ){
    
    Node<CartItem> p = buyList;
    
    while( p != null ){
      if(p.item.getItemNum().equals(itemNum)){
        return p.item;
      }
      else{
        p = p.next;
      }
    }
    
    throw new NoValueException();
    
  }
    
  
  /** This method returns the order number of the cart
    * 
    * @return String the order number                                           */
  
  public int getOrderNum(){
    
    return ordernum;
    
  }
  
  //This method will calculate and return the total cost of the cart
  
  public double calcCost(){
    
    return totalcost;
    
  }
  
  //This method returns the amount of items in the cart
  
  public int getCartQuantity(){
    
    int length = 0;
    
    Node<CartItem> p = buyList;
    
    while( p != null ){
      
      length += 1;
      p = p.next;
      
    }
    
    return length;
    
  }
  
  //This method returns the buy list of the cart
  
  public Node<CartItem> getList(){
    
    return buyList;
    
  }
  
  //This method updates the order num of the cart
  
  public void setOrderNum( int newOrderNum ){
    
    ordernum = newOrderNum;
    
  }
      
  
  
} //Cart