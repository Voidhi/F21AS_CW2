package models;

public class ServerThread implements Runnable {

    private Server server; // existing Server object
    private boolean running = true;
    private int id;

    public ServerThread(Server server, int id) {
        this.server = server;
        this.id = id;
    }

    @Override
    public void run() {
        Log logger = Log.getInstance();

        logger.logEvent("Server " + id + " started.");

        while (running) {
            try {
                Customer customer = SharedQueue.getInstance().dequeue();  // Waits if empty
                server.assignNewCustomer();  // sets isServingWho
                logger.logEvent("Server " + id + " is now serving " + customer.getName());

                // Simulate order processing time (2–4 seconds)
                int delay = (int)(2000 + Math.random() * 2000);
                Thread.sleep(delay);

                logger.logEvent("Server " + id + " finished serving " + customer.getName());
                server.makeAvailable();  // clear current customer

                // Optional: notify GUI observers here

            } catch (InterruptedException e) {
                logger.logEvent("Server " + id + " interrupted.");
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                logger.logEvent("Error in Server " + id + ": " + e.getMessage());
            }
        }

        logger.logEvent("Server " + id + " stopped.");
    }

    public void stop() {
        running = false;
    }
}
