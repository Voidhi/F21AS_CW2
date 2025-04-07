package models;

public class ServerThread implements Runnable {

    private Server server; // existing Server object
    private boolean running = true;
    private int id;
    private double speed = 1; // Modify the speed at which servers are able to process commands

    public ServerThread(Server server, int id) {
        this.server = server;
        this.id = id;
    }

    public void setSpeed(double speed){
        this.speed = speed;
    }

    @Override
    public void run() {
        Log logger = Log.getInstance();

        logger.logAndPrint("Server " + id + " started.");
        System.out.println("Server thread loop started.");

        while (running) {
            try {
                System.out.println("Waiting for customer...");
                Customer customer = SharedQueue.getInstance().dequeue();  // Waits if empty
                server.assignNewCustomer(customer);  // sets isServingWho
                logger.logAndPrint("Server " + id + " is now serving " + customer.getName());

                // Simulate order processing time (2–4 seconds)
                int delay = (int)((2000 + Math.random() * 2000)/speed);
                Thread.sleep(delay);

                logger.logAndPrint("Server " + id + " finished serving " + customer.getName());
                server.makeAvailable();  // clear current customer

                if(SharedQueue.getInstance().isEmpty()){ //TODO : add second condition verifying that we do want to end the program (if more customers arrives later, we do not want to immediately cut the program)
                    running = false; // Stop the program when there are no more customers
                }

                // Optional: notify GUI observers here

            } catch (InterruptedException e) {
                logger.logAndPrint("Server " + id + " interrupted.");
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                logger.logAndPrint("Error in Server " + id + ": " + e.getMessage());
            }
        }

        logger.logAndPrint("Server " + id + " stopped.");
    }

    public void stop() {
        running = false;
    }
}
