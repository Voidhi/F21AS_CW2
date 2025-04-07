package models;

import exceptions.InvalidIDException;
import items.*;
import items.Factories.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class DocumentManager {

    /**
     * Reads the Items.csv and create all the available items
     * @return items - the LinkedList of items
     */

    private static final String pathItems = "src/resources/Items.csv";
    private static final String pathCommands = "src/resources/Commands.csv";
    private static HashMap<Customer,Integer> customerOrders;
    public static HashMap<String,Items> ReadItemsCsv() { //This method reads the Items.csv file and returns the according list of items.
        HashMap<String,Items> items = new HashMap<>(); // The list of all available items
        String[] lineInfo = null; //gets all the data from a line, allowing to create the according item
        try{
            FileReader fr = new FileReader(pathItems);
            BufferedReader br = new BufferedReader(fr);
            br.readLine(); //Skip first line
            while (br.ready()) {
                lineInfo = br.readLine().split(","); //read the data in the line
                String tmpStartWIth = lineInfo[0].substring(0, 3);
                switch(tmpStartWIth) {
                    case "WDR":
                        ItemsFactory warmDrinkFactory = new WarmDrinkFactory();
                        Items warmDrink = warmDrinkFactory.createItem(lineInfo[0], lineInfo[1], Float.parseFloat(lineInfo[2]), lineInfo[3]);
                        items.put(lineInfo[0],warmDrink);
                        break;
                    case "CDR":
                        ItemsFactory coldDrinkFactory = new ColdDrinkFactory();
                        Items coldDrink = coldDrinkFactory.createItem(lineInfo[0], lineInfo[1], Float.parseFloat(lineInfo[2]), lineInfo[3]);
                        items.put(lineInfo[0],coldDrink);
                        break;
                    case "PST":
                        ItemsFactory pastryFactory = new PastryFactory();
                        Items pastry = pastryFactory.createItem(lineInfo[0], lineInfo[1], Float.parseFloat(lineInfo[2]), lineInfo[3]);
                        items.put(lineInfo[0],pastry);
                        break;
                    case "SNK":
                        ItemsFactory snackFactory = new SnackFactory();
                        Items snack = snackFactory.createItem(lineInfo[0], lineInfo[1], Float.parseFloat(lineInfo[2]), lineInfo[3]);
                        items.put(lineInfo[0],snack);
                        break;
                    case "OTH":
                        ItemsFactory othersFactory = new OthersFactory();
                        Items others = othersFactory.createItem(lineInfo[0], lineInfo[1], Float.parseFloat(lineInfo[2]), lineInfo[3]);
                        items.put(lineInfo[0],others);
                        break;
                    default :
                        throw new InvalidIDException("Invalid ID : does not recognize product type");
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
        return items;
    }

    /**
     * Adds a specific order to Commands.csv
     * @param myCommand The client's order
     */
    public static void writeOneCommandCSV(Order myCommand){ //this method adds a specific command to the Commands.csv
        try{
            FileWriter fw = new FileWriter(pathCommands, true); //true valor is used to avoid overwriting file
            BufferedWriter bw = new BufferedWriter(fw);
            for(int i = 0; i < myCommand.getOrderList().size() ;i++) { //writes all ordered items in a command
                bw.newLine();
                bw.write(myCommand.getCustomerID() + "," + myCommand.toString() + "," + myCommand.getOrderList().get(i).get_ID());
            }
            bw.close();
        }
        catch (Exception e){
            System.out.println("Error " + e);
        }
    }

    public static void writeAllCommandsCSV(LinkedList<Order> myCommands){ //this method adds all registered commands to the Commands.csv
        for(Order orders : myCommands) { //write every items in all commands
            writeOneCommandCSV(orders);
        }
    }


    // Clear the command csv at the beginning
    public static void clearCSV(){
        try{
            FileWriter fw = new FileWriter(pathCommands, false); //false valor is used to overwrite file
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("CustomerID,timestamp,itemID"); //write the first line
            bw.close();
        }
        catch (Exception e){
            System.out.println("Error " + e);
        }
    }

    //This method read the Command.csv and update the number of time each item was ordered
    public static void getNumberOrderedItems(){
        String[] lineInfo = null;
        HashMap<String, Integer> itemCounts = new HashMap<>();
        float totalRevenue = 0;
        HashMap<String, ArrayList<Items>> customerOrders = new HashMap<>();
        try {
            FileReader fr = new FileReader(pathCommands);
            BufferedReader br = new BufferedReader(fr);
            br.readLine(); //Skip first line
            HashMap<String,Items> itemList = ReadItemsCsv();
            while(br.ready()){

                lineInfo = br.readLine().split(",");
                String customerID = lineInfo[0];
                String itemID = lineInfo[2];
                if(itemList.containsKey(itemID)) {
                    Items item = itemList.get(itemID);
                    itemCounts.put(itemID, itemCounts.getOrDefault(itemID, 0) + 1);
                    customerOrders.putIfAbsent(customerID, new ArrayList<>());
                    customerOrders.get(customerID).add(item);
                }
            }
            br.close();

            for (String customerID : customerOrders.keySet()) {
                Order order = new Order(customerID, customerOrders.get(customerID), new Date());
                totalRevenue += order.calculatePrice();
            }

            // Save report to file
            new File("summary_report.txt").delete();
            FileWriter fw = new FileWriter("summary_report.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("===== Coffee Shop Summary Report =====\n");
            bw.write("Item Name       | Ordered Count | Price per Unit\n");
            bw.write("-------------------------------------------------\n");
            for (String itemID : itemCounts.keySet()) {
                Items item = itemList.get(itemID);
                bw.write(item.get_Name() + " | " + itemCounts.get(itemID) + " | £" + item.get_pricePerUnit() + "\n");
            }

            bw.write("-------------------------------------------------\n");
            bw.write("Total Revenue: £" + String.format("%.2f", totalRevenue) + "\n");
            bw.close();

            System.out.println("Report generated: summary_report.txt");
        }
        catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    //This methods reads the Commands.csv and creates the correct number of customers and add their command
    public static HashMap<String,Customer> CreateCustomers(){
        HashMap<String,Customer> customers = new HashMap<>();
        customerOrders = new HashMap<>();
        String[] lineInfo = null; //gets all the data from a line, allowing to create the according customer
        try{
            FileReader fr = new FileReader(pathCommands);
            BufferedReader br = new BufferedReader(fr);
            br.readLine(); //Skip first line
            while (br.ready()){
                lineInfo = br.readLine().split(","); //read the data in the line
                if(!customers.containsKey(lineInfo[0])){ // Create customer only if he's not already in the map
                    customers.put(lineInfo[0],new Customer(lineInfo[0]));
                    customerOrders.put(customers.get(lineInfo[0]),1); //Initialize to one the number of ordered items
                }
                else{
                    // Get the current order count for the customer
                    Integer currentOrders = customerOrders.get(customers.get(lineInfo[0]));

                    // Increment the order count
                    customerOrders.put(customers.get(lineInfo[0]), currentOrders + 1);
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
        return customers;
    }

    public static HashMap<Customer, Integer> getCustomerOrders() {
        return customerOrders;
    }
}