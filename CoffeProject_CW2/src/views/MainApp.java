package views;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application{
	private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        showQueueInterface();
    }
    
    private void showQueueInterface() {
        QueueInterface queueInterface = new QueueInterface(() -> showCommandInterface());
        try {
            queueInterface.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showCommandInterface() {
        CommandInterface commandInterface = new CommandInterface(() -> showQueueInterface());
        try {
            commandInterface.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
