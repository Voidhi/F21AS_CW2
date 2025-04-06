package views;

import models.MyData;
import models.Server;
import models.Customer;


import java.awt.event.ActionListener;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
	private void updateServers(List<Server> servers) {
	    myServersDisplay.getChildren().clear();
	    for (Server s : servers) {
	        addServerPanel(s);
	    }
	}
	
	
	private void addServerPanel(Server s) {
		VBox serverBox = new VBox();
		serverBox.setSpacing(5);
		serverBox.setPadding(new Insets(10));
		serverBox.setStyle("-fx-border-color: #888; -fx-border-width: 0.5; -fx-border-radius: 2;");
		serverBox.setAlignment(Pos.CENTER);
		
		Label nameLabel = new Label("Server : " + s.getName());
		nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
		
		Label taskLabel = new Label("Status : " + s.getIsServingWho()==null ? "Available" : ("Is serving : "+s.getIsServingWho().getName()));
		
		VBox order = new VBox();
		
		
		serverBox.getChildren().addAll(nameLabel, taskLabel, order);
		myServersDisplay.getChildren().add(serverBox);
	}
	
	/**
	 * Init the scene
	 */
	@Override
	public void start(Stage primaryStage){
		/* Init the composition of the scene : */	
		title = new Label("Coffee shop - Queue simulation");
		title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
		HBox titleBox = new HBox(title);
		titleBox.setAlignment(Pos.CENTER);
		titleBox.setPadding(new Insets(10));
		
		myQueueDisplay = new VBox();
		
		myServersDisplay = new TilePane();
		myServersDisplay.setHgap(7);
		myServersDisplay.setVgap(7);
		myServersDisplay.setPrefColumns(3);
		
		setSimulationSlider = new Slider(0.5, 3.0, 1.0);
		setSimulationSlider.setPrefWidth(450);
		setSimulationSlider.setShowTickLabels(true);
		setSimulationSlider.setShowTickMarks(true);
		setSimulationSlider.setMajorTickUnit(0.5);
		setSimulationSlider.setMinorTickCount(0);
		setSimulationSlider.setBlockIncrement(0.5);
		setSimulationSlider.setSnapToTicks(true);
		HBox sliderBox = new HBox(setSimulationSlider);
		sliderBox.setAlignment(Pos.CENTER);
		sliderBox.setPadding(new Insets(5,5,5,5));
		
		
		layout = new VBox(2);
		layout.setPadding(new Insets(10));
		layout.getChildren().addAll(myQueueDisplay,myServersDisplay );
		
		screen = new BorderPane();
		screen.setTop(titleBox);
		screen.setCenter(layout);
		screen.setBottom(sliderBox);
		
		
		
		/* Show the scene : */
		Scene scene = new Scene(screen, 500, 500);
		primaryStage.setTitle("Coffee Shop");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> {        	
        	//TODO : send report here
        	//e.consume();
        });
        primaryStage.show();
        
        
        /* - - - - Tests : - - - - */
        // TODO : remove
        Server s = new Server("A GreatServer");
        Customer c = new Customer("Jeremy");
        s.assignCustomer(c);
        addServerPanel(s);
        Server s1 = new Server("Macron");
        Customer c1 = new Customer("Chevrex");
        s1.assignCustomer(c1);
        addServerPanel(s1);
	}
	
	
	
	
	
	public static void main(String[] args) {
        launch(args);        
    }
}
