package models;

import javafx.application.Platform;
import java.util.List;

public class SimulationMonitor implements Runnable {

    private List<Server> servers;
    private SharedQueue queue;

    public SimulationMonitor(List<Server> servers) {
        this.servers = servers;
        this.queue = SharedQueue.getInstance();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(1000); // check every 1s

                boolean queueEmpty = queue.isEmpty();
                boolean allServersIdle = servers.stream().allMatch(s -> s.getIsServingWho() == null);

                if (queueEmpty && allServersIdle) {
                    Log.getInstance().logAndPrint("Simulation finished: queue is empty and all servers are idle.");
                    Log.getInstance().writeToFile("simulation_log.txt");

                    Platform.runLater(() -> {
                        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                        alert.setTitle("Simulation Complete");
                        alert.setHeaderText(null);
                        alert.setContentText("Coffee shop is closed.\nLog written to file.");
                        alert.showAndWait();

                        Platform.exit(); // or System.exit(0);
                    });

                    break;
                }
            }
        } catch (InterruptedException e) {
            System.err.println("SimulationMonitor interrupted");
        }
    }
}
