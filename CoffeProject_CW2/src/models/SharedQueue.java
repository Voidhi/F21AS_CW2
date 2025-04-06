package models;

import java.util.LinkedList;
import java.util.Queue;


public class SharedQueue {
	// Singleton :
	private static SharedQueue myInstance;
		
    private final Queue<Customer> queue = new LinkedList<>();
    private final int capacity = 10; // optional max queue size
    
    /** Private constructor - Thread-safe singleton
	 * @param path
	 */
	private SharedQueue() {}
	/**
	 * Public singleton accessor - Thread-safe singleton
	 * @return unique instance
	 */
	public static SharedQueue getInstance() {
		if (myInstance == null) 
			synchronized(MyData.class) {
				if (myInstance == null)
					myInstance = new SharedQueue();
			}
		return myInstance;
	}
	public synchronized Queue<Customer> getQueue() throws InterruptedException{
		return this.queue;
	}
	

    /**
     * Add a new customer to the queue
     * @param order
     * @throws InterruptedException
     */
    public synchronized void enqueue(Customer c) throws InterruptedException {
    	if(this.queue.contains(c)) {
			System.out.println("Customer already in the queue");
			return;
		}
    	
        while (queue.size() >= capacity) 
            wait(); // wait until space is available      
        queue.add(c);
        notifyAll(); // notify consumers
    }

    
    public synchronized Customer dequeue() throws InterruptedException {
        while (queue.isEmpty()) {
            wait(); // wait until an order is available
        }
        Customer c = queue.poll();
        notifyAll(); // notify producer
        return c;
    }

    // For logging, UI updates, or closing conditions
    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    public synchronized int size() {
        return queue.size();
    }
}
