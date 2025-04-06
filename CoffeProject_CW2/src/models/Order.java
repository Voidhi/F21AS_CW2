package models;

import exceptions.InvalidIDException;
import items.*;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    private String customerID;
    private ArrayList<Items> orderList = new ArrayList<>();
    private Date timestamp;

    public Order(String customerID, ArrayList<Items> orderList, Date timestamp) throws InvalidIDException{
    	if( customerID.length() != 5)
			throw new InvalidIDException("Invalid ID : Customer ID length must be of 5");
    	if (!customerID.matches("C[0-9]{4}")) {
            throw new InvalidIDException(customerID, "Customer");
        }	    			
    			
        this.customerID = customerID;
        this.orderList = orderList;
        this.timestamp = timestamp;
    }
    public Order(String customerID, ArrayList<Items> orderList) throws InvalidIDException{
    	this(customerID, orderList, new Date());
    }
    public Order(String customerID, Date timestamp) throws InvalidIDException{
    	this(customerID, new ArrayList<Items>(), timestamp) ;
    }
    public Order(String customerID) throws InvalidIDException{
    	this(customerID, new ArrayList<Items>(), new Date());
    }

    public float calculatePrice() {
        float totalPrice = 0;
        ArrayList<Items> discountedItems = new ArrayList<>();
        
        // Counters to keep track of every item ordered!
        int snackCount = 0;
        int pastryCount = 0;
        int warmDrinkCount = 0;
        int toppingCount = 0;
        float warmDrinkPrice = 0;
        float toppingPrice = 0;
        
        // This loop counts every item and price
        for (Items item : orderList) {
            if (!discountedItems.contains(item)) {
                totalPrice += item.get_pricePerUnit();
            }
            
            if (item instanceof Snack) snackCount++;
            if (item instanceof Pastry) pastryCount++;
            if (item instanceof WarmDrink) {
                warmDrinkCount++;
                warmDrinkPrice = item.get_pricePerUnit();
            }
            if (item instanceof Others) {
                toppingCount++;
                toppingPrice += item.get_pricePerUnit();
            }
        }
        
        // If a snack and a pastry are ordered, give a 20% discount
        int discountPairs = Math.min(snackCount, pastryCount);
        if (discountPairs > 0) {
            totalPrice -= discountPairs * 0.2f * (2.50f + 2.00f); // Assuming average snack & pastry price
        }

        // If a WarmDrink and two toppings are ordered, give a 25% discount
        if (warmDrinkCount > 0 && toppingCount >= 2) {
            totalPrice -= 0.25f * (warmDrinkPrice + toppingPrice);
        }

        return totalPrice;
        
        // Old code (I tried to re-optimize it, go here if the new one doesn't work)
        /** If a snack and a pastry are ordered, give a 20% discount
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i) instanceof Snack) {
                for (int j = 0; j < orderList.size(); j++) {
                    if (orderList.get(j) instanceof Pastry && !discountedItems.contains(orderList.get(i)) && !discountedItems.contains(orderList.get(j))) {
                        totalPrice += (orderList.get(i).get_pricePerUnit() + orderList.get(j).get_pricePerUnit()) * 0.8f;
                        discountedItems.add(orderList.get(i));
                        discountedItems.add(orderList.get(j));
                        break;  // Exit inner loop after discount is applied
                    }
                }
            }
        }

        // If a WarmDrink and two toppings are ordered, give a 25% discount
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i) instanceof WarmDrink && !discountedItems.contains(orderList.get(i))) {
                int toppingsCount = 0;
                float drinkPrice = orderList.get(i).get_pricePerUnit();
                for (int j = 0; j < orderList.size(); j++) {
                    if (orderList.get(j) instanceof Others && !discountedItems.contains(orderList.get(j))) {
                        toppingsCount++;
                        drinkPrice += orderList.get(j).get_pricePerUnit();
                        discountedItems.add(orderList.get(j));
                        if (toppingsCount == 2) {
                            totalPrice += drinkPrice * 0.75f;
                            discountedItems.add(orderList.get(i));
                            break;
                        }
                    }
                }
            }
        }

        // Add remaining items that are not discounted
        for (Items item : orderList) {
            if (!discountedItems.contains(item)) {
                totalPrice += item.get_pricePerUnit();
            }
        }

        return totalPrice; **/
    }

    public String getCustomerID(){
        return this.customerID;
    }

    public ArrayList<Items> getOrderList(){
        return this.orderList;
    }

    @SuppressWarnings("deprecation")
	@Override
    public String toString(){
        return timestamp.getHours() + ":" + timestamp.getMinutes() + ":" + timestamp.getSeconds();
    }


}
