package models;

import java.util.ArrayList;

public class CustomerThread implements Runnable{

    private ArrayList<Customer> customer; // existing Customer object
    private boolean running = true;
    private int delay;
    private int id;
    public CustomerThread(ArrayList<Customer> customer, int delay) {
        this.customer = customer;
        this.delay = delay;
    }

    @Override
    public void run() {
        Log logger = Log.getInstance();

        System.out.println("Customer thread loop started.");
        id = 0;
        while (running && id < customer.size()){
            try {
            	logger.logAndPrint("Customer " + id + " is now in the queue.");
                SharedQueue.getInstance().enqueue(customer.get(id)); //Put the customer into the queue
                id++;
               
                Thread.sleep(delay);
                if(id == customer.size() - 1) {
                	running = false; //Stop the thread once every customer has been added
                        //TODO : add a method to add new customers that put running into true again for server and customer thread
                }
            }
            catch (InterruptedException e) {
            	logger.logAndPrint("Customer " + id + " interrupted.");
            	Thread.currentThread().interrupt();
            	break;
            } catch (Exception e) {
            	logger.logAndPrint("Error in Customer " + id + ": " + e.getMessage());
            }
        }
    }

    public void stop() {
        running = false;
    }
}
