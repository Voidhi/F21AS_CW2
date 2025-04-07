package views;

import models.MyData;
import models.Server;
import models.ServerThread;
import models.SharedQueue;
import models.Customer;


import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.scene.layout.*;

public class QueueInterface extends Application implements Observers {
	// GUI element :
	private BorderPane screen; // top : title, Center : VBox layout, Bottom : slider
	private VBox layout; // up : current queue | down : servers list (Tilepane)
	private VBox myQueueDisplay;
	private HBox myQueue_Categories;
	private VBox myQueue_CustomersNames;
	private VBox myQueue_CustomersItems;
	private TilePane myServersDisplay;
	private Label title;
	private Slider setSimulationSlider;
	// Event : 
	private ActionListener actionListener;
	// Data :
	// Threads :
	private List<Server> activeServers = new ArrayList<>();
	private List<Thread> serverThreads = new ArrayList<>();
	
	
	private Runnable onSwitch;
	public QueueInterface(Runnable onSwitch) {
	    this.onSwitch = onSwitch;
	}
	
	
	// To send info to the controller :
	public void addSetListener(ActionListener al) {
		// TODO : link buttons to event :
		// eg : mySlider.addActionListener(al);
	}
	/**
	 * To receive info from the models directly and update the view:
	 * eg. the queue, servers' status...
	 */
	public void Update() {
		Platform.runLater(() -> {
			try {
				updateServers(activeServers);
		        //updateServers(SharedQueue.getInstance().getQueue());		
			}catch(Exception e) {}       	
		});
	}
	private void updateQueue(Queue<Customer> queue) {
		myQueueDisplay.getChildren().clear();
		myQueue_CustomersNames.getChildren().clear();
		myQueue_CustomersItems.getChildren().clear();
		
		Label numberInQueue = new Label("There is currently : " + queue.size() + " customers wainting in the queue");
		numberInQueue.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
		
		Label nameHeader = new Label("Name");
		nameHeader.setStyle("-fx-font-weight: bold;");
		Label orderHeader = new Label("Order");
		orderHeader.setStyle("-fx-font-weight: bold;");
		myQueue_CustomersNames.getChildren().add(nameHeader);
		myQueue_CustomersItems.getChildren().add(orderHeader);
		
	    for (Customer c : queue) {
	    	Label name = new Label(c.getName());
	        name.setStyle("-fx-font-size: 12px;");
	        myQueue_CustomersNames.getChildren().add(name);
	        
	        // TODO : display " _nbitems_ items" here
	        Label items = new Label("has ___ items");
	        items.setStyle("-fx-font-size: 12px;");
	        myQueue_CustomersItems.getChildren().add(items);
	    }
	    myQueueDisplay.getChildren().addAll(numberInQueue, myQueue_Categories);
	}
	
	
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
		serverBox.setStyle("-fx-border-color: #888; -fx-border-width: 0.5; -fx-border-radius: 5;");
		serverBox.setAlignment(Pos.CENTER);
		
		Label nameLabel = new Label("Server : " + s.getName());
		nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
		
		String statusText = (s.getIsServingWho() == null)
			    ? "Available"
			    : "Is serving: " + s.getIsServingWho().getName();

			Label taskLabel = new Label("Status: " + statusText);


		VBox order = new VBox();
		// TODO : complete here to show order...
		
		serverBox.getChildren().addAll(nameLabel, taskLabel, order);
		myServersDisplay.getChildren().add(serverBox);
	}
	
	private void startAutoUpdate() {
	    Thread uiUpdater = new Thread(() -> {
	        while (true) {
	            try {
	                Thread.sleep(1000); // update every second
	                Platform.runLater(() -> {
	                    try {
	                        updateQueue(SharedQueue.getInstance().getQueue());
	                        updateServers(activeServers);
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                });
	            } catch (InterruptedException e) {
	                break;
	            }
	        }
	    });
	    uiUpdater.setDaemon(true);
	    uiUpdater.start();
	}

	
	
	public void startSimulation(int numServers) {
	    for (int i = 1; i <= numServers; i++) {
	        Server s = new Server("Server " + i);
	        activeServers.add(s);

	        ServerThread serverThread = new ServerThread(s, i); // assuming you already created this class
	        Thread t = new Thread(serverThread);
	        t.start();
	        serverThreads.add(t);

	        addServerPanel(s);
	    }
	    startAutoUpdate(); // starts refreshing GUI every second
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
		myQueueDisplay.setStyle("-fx-border-color: #888; -fx-border-width: 0.5; -fx-border-radius: 5;");
		myQueueDisplay.setAlignment(Pos.CENTER);
		myQueueDisplay.setPadding(new Insets(20,15,20,15));
		myQueue_Categories = new HBox();
		myQueue_Categories.setPadding(new Insets(10));
		myQueue_Categories.setAlignment(Pos.CENTER);
		myQueue_CustomersNames = new VBox();
		myQueue_CustomersNames.setPadding(new Insets(0,15,0,15));
		myQueue_CustomersItems = new VBox();
		myQueue_CustomersItems.setPadding(new Insets(0,15,0,15));
		myQueue_Categories.getChildren().addAll(myQueue_CustomersNames, myQueue_CustomersItems);
		
		myServersDisplay = new TilePane();
		myServersDisplay.setHgap(7);
		myServersDisplay.setVgap(7);
		myServersDisplay.setPrefColumns(3);
		
		Button switchButton = new Button("Switch to Command View");
        switchButton.setOnAction(e -> onSwitch.run());
        
		setSimulationSlider = new Slider(0.5, 3.0, 1.0);
		setSimulationSlider.setMaxWidth(Double.MAX_VALUE);
		setSimulationSlider.setShowTickLabels(true);
		setSimulationSlider.setShowTickMarks(true);
		setSimulationSlider.setMajorTickUnit(0.5);
		setSimulationSlider.setMinorTickCount(0);
		setSimulationSlider.setBlockIncrement(0.5);
		setSimulationSlider.setSnapToTicks(true);
		
		VBox sliderBox = new VBox(setSimulationSlider, switchButton);
		sliderBox.setAlignment(Pos.CENTER);
		sliderBox.setPadding(new Insets(20,15,20,15));
		HBox.setHgrow(setSimulationSlider, Priority.ALWAYS); // so the width can be responsive
		
		
		
		layout = new VBox(2);
		layout.setPadding(new Insets(10));
		layout.getChildren().addAll(myQueueDisplay,myServersDisplay );
		
		screen = new BorderPane();
		screen.setTop(titleBox);
		screen.setCenter(layout);
		screen.setBottom(sliderBox);
		
		
		
		/* Show the scene : */
		Scene scene = new Scene(screen, 430, 500);
		primaryStage.setTitle("Coffee Shop");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> {        	
        	//TODO : send report here
        	//e.consume();
        });
        primaryStage.show();
        
        // TODO : remove :
        startSimulation(3);
	}
	
	
	
	public static void main(String[] args) {
        launch(args);        
    }
}
