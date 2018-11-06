package Spartan;

import BasicIO.*;
import static BasicIO.Formats.*;
import java.io.*;

/**This class is the main class that allows the user to login and place an order
  * with the Spartan store.
  * 
  * @author Daniel Sokic
  * 
  * @version  1.0  (Mar.2018)                          */

public class CustomerOrder implements Serializable {
  
  private static final long  serialVersionUID = 99990004L;
  
  private StoreDatabase storeData;
  private BasicForm loginScreen;
  private BasicForm orderForm;
  private BinaryDataFile StoreFile;
  private StoreCatalogue catalogue;
  private CustomerCart cart;
  private StoreCustomer currentCustomer;
  private boolean printOrder;
  private ReportPrinter Report;
  
  private BinaryOutputFile newShippingList;
  private BinaryDataFile oldShippingList;
  private Node<StoreCustomer> ShippingList;
  
  /** This constructor first takes a binary shipping file to set as the current Shipping List
    * for the store. Then initializes the Login Screen and Data from the ASCII Store File. Afterwards,
    * the Login is built and the customer is asked to login, if they present the proper information, then
    * the order form is presented to them. The order form will have the available catalogue of items on the
    * left and the customers cart as empty. Calling upon the OrderForm method, which allows the user to add or
    * remove items from their cart until they press done, if the customer has at least one item in the cart,
    * the order is added to the shipping list and the customer is presented the order receipt with their items,
    * information, and order number. The newly changed shipping list overwrites the old binary shipping File,
    * allowing for the shipping clerk to access it as changes are made.  */
  
  private CustomerOrder() { 
    
    oldShippingList = new BinaryDataFile();
    try{
      ShippingList = (Node<StoreCustomer>)oldShippingList.readObject();
    }
    catch(NullPointerException e ){
      ShippingList = null;
    }
    
    loginScreen = new BasicForm("Login", "Quit");
    StoreFile = new BinaryDataFile();
    storeData = (StoreDatabase)StoreFile.readObject();
    
    buildLogin();
   
    if( Login() == true ){
      orderForm = new BasicForm("Add", "Remove", "Done");
      buildOrderForm();
      
      catalogue = storeData.getCatalogue();
      fillCatalogue();
      
      cart = currentCustomer.getCart();
      orderForm.writeString("total", "$" + cart.calcCost());
      
      printOrder = false;
      OrderForm();
      
      if(printOrder == true){
        currentCustomer.setCart(cart);
        addToShippingList();
        Report = new ReportPrinter();
        buildReport();
        newShippingList = new BinaryOutputFile();
        newShippingList.writeObject(ShippingList);
        Report.close();
      }
      
    } 
  }
  
  //This method builds the BasicForm for the loginScreen
  
  private void buildLogin(){
    
    loginScreen.addTextField("user","UserID:", 15);
    loginScreen.addTextField("pass","Password:", 12);
    loginScreen.addTextField("error",22);
    
    loginScreen.setEditable("error",false);
    
  }
  
  //This method builds the orderForm containing the cart and catalogue
  
  private void buildOrderForm(){
    
    orderForm.addTextField("itemnum","Item #", 7, 10, 400);
    orderForm.addTextField("quant","Quantity", 3, 110, 400);
    orderForm.addTextField("total","Order Total", getCurrencyInstance(), 10, 340, 397);
    orderForm.addTextArea("cata", "Catalogue", 20, 40, 10, 10);
    orderForm.addTextArea("cart", "Cart", 20, 40, 340, 10);
    
    orderForm.setEditable("total", false);
  }
  
  //Thie method builds the order confirmation that's given once an order is placed
  
  private void buildReport(){
    
    Report.setTitle("Order Confirmation", "Order #:" + cart.getOrderNum(), 
                    "Customer #:" + currentCustomer.getID(), 
                    "Name: " + currentCustomer.getUserName(),
                    "Address: " + currentCustomer.getAddress());
    
    Report.addField("quant", "Quantity", 8);
    Report.addField("itemnum", "Item #", 6);
    Report.addField("desc", "Description", 25);
    Report.addField("amount", "Amount", 7);
    Report.addField("total", "Total", getCurrencyInstance(), 7);
    
    Node<CartItem> buyList = cart.getList();
    
    while( buyList != null ){
      int itemQuant = buyList.item.getQuant();
      double itemPrice = buyList.item.getPrice();
      String itemNum = buyList.item.getItemNum();
      String itemDesc = buyList.item.getDesc();
      Report.writeString("quant", Integer.toString(itemQuant));
      Report.writeString("itemnum", itemNum);
      Report.writeString("desc", itemDesc);
      Report.writeString("amount", "$" + itemPrice);
      double Total = itemQuant * itemPrice;
      Report.writeString("total", "$" + Total);
      buyList = buyList.next;
    }
    
    Report.writeString("desc", "Total");
    Report.writeDouble("total", cart.calcCost());
    
  }
  
  //This method fills the Catalogue text field in the OrderForm with items from the catalogue
  
  private void fillCatalogue(){
    
    Node<StoreItem> itemList = catalogue.getList();
    
    while( itemList != null ){
      String itemNum = itemList.item.getItemNum();
      double itemPrice = itemList.item.getPrice();
      String itemDesc = itemList.item.getDesc();
      orderForm.writeString("cata", itemNum + "\t$" + itemPrice + " " + itemDesc);
      orderForm.newLine("cata");
      itemList = itemList.next;
    }
    
  }
  
  //This method updates the Cart text field with the items that are currently in the cart
  
  private void updateCart(){
    
    Node<CartItem> buyList = cart.getList();
    
    while( buyList != null ){
      int itemQuant = buyList.item.getQuant();
      double itemPrice = buyList.item.getPrice();
      String itemNum = buyList.item.getItemNum();
      String itemDesc = buyList.item.getDesc();
      orderForm.writeString("cart", itemQuant + "\t$" + itemPrice + " " + itemNum
                              + " " + itemDesc);
      orderForm.newLine("cart");
      buyList = buyList.next;
    }
    
    orderForm.clear("total");
    orderForm.writeDouble("total", cart.calcCost());
  }
  
  /** This method checks the submitted userID and password for a match in the database,
    * if no match is found an error message appears. If the user quits a false is returned,
    * signifying they've quit. Only once both userID and password match does a true return
    * and allow the user to proceed to the OrderForm.
    * 
    * @return boolean the user has given a valid userID and password */
  
  private boolean Login(){
    for( ; ; ){  
      if(loginScreen.accept() == 1){
        loginScreen.close();
        return false;
      }
      else{
        if(storeData.checkPassword(loginScreen.readString("user"), loginScreen.readString("pass"))){
          currentCustomer = storeData.getCustomer(loginScreen.readString("user"));
          loginScreen.close();
          return true;
        }
        else{
          loginScreen.writeString("error", "wrong ID or password");
          loginScreen.clear("user");
          loginScreen.clear("pass");
        }
      }
    }
  }
  
  /** This method checks for the user's input on whether to add to their cart or remove from their
    * cart with the values submitted into the ItemNum and Quantity fields. Depending on these
    * values, the item associated with the number will be added or removed by the indicated quantity.
    * The third "Done" option is for when the user decides to receive their order confirmation and
    * add it to the shipping list. However, if the Cart is empty, then no order is placed. Also,
    * an item removed by a quantity bringing it to 0 or below is removed from the Cart. */
  
  private void OrderForm(){
    
    String itemNum;
    int quantity;
    boolean doneCheck = false;
    
    while(doneCheck == false){
        switch (orderForm.accept()){
          
          case 0:
            itemNum = orderForm.readString("itemnum");
            quantity = orderForm.readInt("quant");
            if(quantity > 0){
              cart.addItem(quantity, itemNum);
              orderForm.clear("cart");
              updateCart();
            }
            orderForm.clear("itemnum");
            orderForm.clear("quant");
            break;
            
          case 1:
            itemNum = orderForm.readString("itemnum");
            quantity = orderForm.readInt("quant");
            cart.removeItem(quantity, itemNum);
            orderForm.clear("cart");
            updateCart();
            orderForm.clear("itemnum");
            orderForm.clear("quant");
            break;
          
          case 2:
            if(orderForm.readDouble("total") == 0.00){
              doneCheck = true;
              orderForm.close();
            }
            else{
              printOrder = true;
              doneCheck = true;
              orderForm.close();
            }
            break;
        }
        
      }
    
  }
  
  /**This method adds the current customer, which includes their information and cart, to the shipping list
    * which'll be sent to the shipping clerk.*/
  
  private void addToShippingList(){
    
    Node<StoreCustomer> p = ShippingList;
    Node<StoreCustomer> q = null;
    
    if(ShippingList == null){
      ShippingList = new Node<StoreCustomer>(currentCustomer,null);
    }
    else{
      while(p != null){
        q = p;
        p = p.next;
      }
      currentCustomer.getCart().setOrderNum(q.item.getCart().getOrderNum() + 1);
      q.next = new Node<StoreCustomer>(currentCustomer,null);
    }
  }
    
  
  
  public static void main(String[] args) { new CustomerOrder(); }; 
  
}
