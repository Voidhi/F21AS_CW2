package models;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.jupiter.api.Order;

public class SharedQueue {

    private final Queue<Order> queue = new LinkedList<>();
    private final int capacity; // optional max queue size

    public SharedQueue(int capacity) {
        this.capacity = capacity;
    }

    // Producer adds order
    public synchronized void enqueue(Order order) throws InterruptedException {
        while (queue.size() >= capacity) {
            wait(); // wait until space is available
        }
        queue.add(order);
        notifyAll(); // notify consumers
    }

    // Consumer takes order
    public synchronized Order dequeue() throws InterruptedException {
        while (queue.isEmpty()) {
            wait(); // wait until an order is available
        }
        Order order = queue.poll();
        notifyAll(); // notify producer
        return order;
    }

    // For logging, UI updates, or closing conditions
    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    public synchronized int size() {
        return queue.size();
    }
}
