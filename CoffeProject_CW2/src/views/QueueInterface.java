package views;

import java.awt.event.ActionListener;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;

public class QueueInterface extends Application implements Observers {
	// GUI element :
	private BorderPane Screen; // top : title, Center : VBox layout, Bottom : slider
	private VBox layout; // up : current queue | down : servers list (Tilepane)
	private TilePane myServers; // scrollable
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
	}
	
	
	

	/**
	 * Init the scene
	 */
	@Override
	public void start(Stage primaryStage){
		// Init the compo of the scene :
		Screen = new BorderPane();
		layout = new VBox();
		
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
