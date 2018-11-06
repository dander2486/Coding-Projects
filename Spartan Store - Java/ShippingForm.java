package Spartan;
   
import BasicIO.*;
import java.io.*;

/**This class is the main class that is used by the shipping clerk to
  * ship orders.
  * 
  * @author Daniel Sokic
  * 
  * @version  1.0  (Mar.2018)                          */

public class ShippingForm implements Serializable {
  
  private static final long  serialVersionUID = 99990008L;
  
  private BasicForm shipForm;
  private BinaryDataFile currentOrderFile;
  private BinaryOutputFile newOrderFile;
  private Node<StoreCustomer> OrderList;
  private ReportPrinter shippingReport;
  
  /** This constructor first opens the binary Shipping File and sets the 
    * OrderList to the list within the file. If there is nothing within the file,
    * then the list is automatically set to null. Once the form's been built, and if there
    * are any orders on the list, the shipping clerk is presented the shipping form with the most
    * recent order issued. If the clerk chooses to ship it, a shipping notice is created and printed.
    * Afterwards, the next order in the list is presented on the form to the clerk, if one exists. Once
    * there are no more orders, or the clerk decides to quit the form, the current OrderList overwrites
    * the binary Shipping File and the form is closed. */
  private ShippingForm() {
    
    currentOrderFile = new BinaryDataFile();
    try{
      OrderList = (Node<StoreCustomer>)currentOrderFile.readObject();
    }
    catch(NullPointerException e){
      OrderList = null;
    }
    
    shipForm = new BasicForm("Ship","Quit");
    buildShipForm();
    
    while(OrderList != null){
      shippingReport = new ReportPrinter();
      shipForm.clear("cart");
      updateCart();
      if(shipForm.accept() == 0){
        shipCurrentCart();
        shippingReport.close();
        OrderList = OrderList.next;
      }
      else{
        shipForm.close();
        newOrderFile = new BinaryOutputFile();
        newOrderFile.writeObject(OrderList);
        break;
      }
    }
    if(OrderList == null){
      newOrderFile = new BinaryOutputFile();
      newOrderFile.writeObject(OrderList);
      shippingReport.close();
    }
    
  }
  
  //This method builds the BasicForm of the Shipping form
  
  private void buildShipForm(){
    
    shipForm.addTextField("ordernum", "Order Number", 5);
    shipForm.addTextArea("cart", "Cart", 20, 40);
    
    shipForm.setEditable("ordernum",false);
    
  }
  
  /**This method takes the most recent order in the OrderList and displays the order numbers
    * and cart contents on the form for the clerk to see. */
  
  private void updateCart(){
    
    shipForm.writeInt("ordernum", OrderList.item.getCart().getOrderNum());
    
    Node<CartItem> buyList = OrderList.item.getCart().getList();
    
    while( buyList != null ){
      int itemQuant = buyList.item.getQuant();
      String itemNum = buyList.item.getItemNum();
      String itemDesc = buyList.item.getDesc();
      shipForm.writeString("cart", "  " + itemQuant + " " + itemNum
                              + " " + itemDesc);
      shipForm.newLine("cart");
      buyList = buyList.next;
    }
    
    
  }
  
  /**This method puts the cart's items and the customer's information on the Shipping notice report 
    * and is used when an order is confirmed for shipment. */
  
  private void shipCurrentCart(){
    
    CustomerCart currentCart = OrderList.item.getCart();
    
    shippingReport.setTitle("Shipping Notice", "Order #:" + currentCart.getOrderNum(),
                            "Customer #: " + OrderList.item.getID(), "Name: " + OrderList.item.getUserName(),
                            "Address: " + OrderList.item.getAddress());
    
    shippingReport.addField("quant", "Quantity", 8);
    shippingReport.addField("itemnum", "Item #", 6);
    shippingReport.addField("desc", "Description", 45);
    
    Node<CartItem> buyList = currentCart.getList();
    
    while( buyList != null ){
      int itemQuant = buyList.item.getQuant();
      String itemNum = buyList.item.getItemNum();
      String itemDesc = buyList.item.getDesc();
      shippingReport.writeString("quant", Integer.toString(itemQuant));
      shippingReport.writeString("itemnum", itemNum);
      shippingReport.writeString("desc", itemDesc);
      buyList = buyList.next;
    }
    
    shippingReport.writeString("desc", "Your order shipped on: 05-Apr-2018 ");
    
  }
  
  public static void main(String[] args) { new ShippingForm(); }; 
  
}
