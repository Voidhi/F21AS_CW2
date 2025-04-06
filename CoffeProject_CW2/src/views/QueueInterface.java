package views;

import models.MyData;
import models.Server;
import models.Customer;


import java.awt.event.ActionListener;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.layout.*;

public class QueueInterface extends Application implements Observers {
	// GUI element :
	private BorderPane Screen; // top : title, Center : VBox layout, Bottom : slider
	private VBox layout; // up : current queue | down : servers list (Tilepane)
	private TilePane myServers; // scrollable
	private Label titleLabel;
	// Event : 
	private ActionListener actionListener;
	// Data :
	
	
	// To send info to the controller :
	public void addSetListener(ActionListener al) {
		// TODO : link buttons to event :
		// eg : mySlider.addActionListener(al);
	}
	// To receive info from the model MyData directly :
	public void Update() {
		// to update the info on the view
		// eg. the queue, servers' stats...
//		Platform.runLater(() -> {
//          updateQueue(MyData.getInstance().getQueue());
//          updateServers(MyData.getInstance().getQueue());			
//		});
	}
	private void updateQueue(List<Customer> queue) {}
	private void updateServers(List<Server> servers) {}
	

	/**
	 * Init the scene
	 */
	@Override
	public void start(Stage primaryStage){
		// Init the composition of the scene :
		Screen = new BorderPane();
		layout = new VBox(2);
		layout.setPadding(new Insets(10));;
		
		// Show the scene :
		Scene scene = new Scene(layout, 400, 600);
		primaryStage.setTitle("Coffee Shop");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> {
        	e.consume();
        	//TODO : send report here
        });
        primaryStage.show();
	}
	
	public static void main(String[] args) {
        launch(args);
    }
}
