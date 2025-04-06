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
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.scene.layout.*;

public class QueueInterface extends Application implements Observers {
	// GUI element :
	private BorderPane screen; // top : title, Center : VBox layout, Bottom : slider
	private VBox layout; // up : current queue | down : servers list (Tilepane)
	private VBox myQueueDisplay;
	private TilePane myServersDisplay; // scrollable
	private Label title;
	private Slider setSimulationSlider;
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
		/* Init the composition of the scene : */	
		title = new Label("Coffe shop - Queue simulation");
		title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
		
		myQueueDisplay = new VBox();
		
		myServersDisplay = new TilePane();
		myServersDisplay.setHgap(7);
		myServersDisplay.setVgap(7);
		myServersDisplay.setPrefColumns(3);
		
		setSimulationSlider = new Slider(0.5, 2.0, 1.0);
		setSimulationSlider.setShowTickLabels(true);
		setSimulationSlider.setShowTickMarks(true);
		setSimulationSlider.setMajorTickUnit(0.5);
		setSimulationSlider.setBlockIncrement(0.1);
		
		layout = new VBox(2);
		layout.setPadding(new Insets(10));
		layout.getChildren().addAll(myQueueDisplay,myServersDisplay );
		
		screen = new BorderPane();
		screen.setTop(title);
		screen.setCenter(layout);
		screen.setBottom(setSimulationSlider);
		
		
		
		/* Show the scene : */
		Scene scene = new Scene(screen, 500, 700);
		primaryStage.setTitle("Coffee Shop");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> {        	
        	//TODO : send report here
        	//e.consume();
        });
        primaryStage.show();
	}
	
	public static void main(String[] args) {
        launch(args);
    }
}
