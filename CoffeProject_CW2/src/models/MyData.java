package models;

import java.util.LinkedList;

public class MyData implements Observable{
	// Singleton :
	private static MyData myInstance;

	private static final String csvPath = "";
	private LinkedList<Customer> myQueue;
	
	
	
	/**
	 * Private constructor - Thread-safe singleton
	 * @param path
	 */
	private MyData(String path) {
		myQueue = new LinkedList<>();
		// TODO : init with csv files
	}
	/**
	 * Public singleton accessor - Thread-safe singleton
	 * @return unique instance
	 */
	public static MyData getInstance() {
		if (myInstance == null) 
			synchronized(MyData.class) {
				if (myInstance == null)
					myInstance = new MyData(csvPath);
			}
		return myInstance;
	}
	
	
	public Customer assignNextCustomer() {return this.myQueue.poll();}	
	public void addNewCustomertoQueue(Customer c) {
		if(this.myQueue.contains(c)) {
			System.out.println("Customer already in the queue");
		}
		this.myQueue.add(c);
	}
	
	/**
	 * Observator/MVC pattern
	 */
	public void notifyObservers() {
		
	}
}
