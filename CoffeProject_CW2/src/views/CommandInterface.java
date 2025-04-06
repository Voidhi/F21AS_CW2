package views;

import exceptions.InvalidIDException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import items.Items;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import models.*;

public class CommandInterface extends Application {
    private HashMap<String,Items> myItems;
    private TextField customerIdField;
    private ComboBox<String> itemComboBox;
    private Spinner<Integer> quantitySpinner;
    private ListView<String> orderListView;
    private Label totalLabel;
    private ArrayList<Items> selectedItems = new ArrayList<>();

    @SuppressWarnings("unused")
	@Override
    public void start(Stage primaryStage) {
        DocumentManager.clearCSV();
        myItems = DocumentManager.ReadItemsCsv();
        Label customerLabel = new Label("Enter Customer ID (Cxxxx):");
        customerIdField = new TextField("C0000");
        itemComboBox = new ComboBox<>();
        for (Map.Entry<String, Items> entry : myItems.entrySet()) {
            Items item = entry.getValue();
            itemComboBox.getItems().add(item.get_Name() + " - £" + item.get_pricePerUnit());
        }
        itemComboBox.setPromptText("Select an item");
        quantitySpinner = new Spinner<>(1, 10, 1);
        Button addButton = new Button("Add Item");
        addButton.setOnAction(e -> addItem());
        Button resetButton = new Button("Reset Order");
        resetButton.setOnAction(event -> resetOrder());
        orderListView = new ListView<>();
        totalLabel = new Label("Total: £0.00");
        Button confirmButton = new Button("Confirm Order");
        confirmButton.setOnAction(e -> confirmOrder());
        VBox layout = new VBox(10, customerLabel, customerIdField, itemComboBox, quantitySpinner, addButton, orderListView, resetButton, totalLabel, confirmButton);
        layout.setPadding(new Insets(10));
        Scene scene = new Scene(layout, 400, 600);
        primaryStage.setTitle("Coffee Shop");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void validateCustomerID(String id) throws InvalidIDException {
        if (!id.matches("C[0-9]{4}")) {
            throw new InvalidIDException(id, "Customer");
        }
    }

    private void addItem() {
        String selected = itemComboBox.getValue();
        int quantity = quantitySpinner.getValue();

        if (selected == null || customerIdField.getText().isEmpty()) {
            showAlert("Please enter Customer ID and select an item.");
            return;
        }
        
        try {
            validateCustomerID(customerIdField.getText());
        } catch (InvalidIDException e) {
            showAlert(e.getMessage());
            return;
        }
        
        String itemName = selected.split(" - ")[0];
        boolean found = false;

        for (int i = 0; i < orderListView.getItems().size(); i++) {
            String existing = orderListView.getItems().get(i);
            if (existing.startsWith(itemName)) {
                int existingQuantity = Integer.parseInt(existing.split("x")[1].trim());
                orderListView.getItems().set(i, itemName + " x" + (existingQuantity + quantity));
                found = true;
                break;
            }
        }

        if (!found) {
            orderListView.getItems().add(itemName + " x" + quantity);
        }

        for (Map.Entry<String, Items> entry : myItems.entrySet()) {
            Items item = entry.getValue();
            if (item.get_Name().equals(itemName)) {
                for (int i = 0; i < quantity; i++) {
                    selectedItems.add(item);
                }
                break;
            }
        }

        calculateTotal();
    }
    
    private void resetOrder() {
        selectedItems.clear();
        orderListView.getItems().clear();
        totalLabel.setText("£0.00");
    }

    private void calculateTotal() {
    	try {
    		Order order = new Order(customerIdField.getText(), selectedItems, new Date());
            float total = order.calculatePrice();
            totalLabel.setText("Total: £" + String.format("%.2f", total));
    	}catch(InvalidIDException e) {
    		showAlert(e.getMessage());
            return;
    	}        
    }

    private void confirmOrder() {
        if (customerIdField.getText().isEmpty() || selectedItems.isEmpty()) {
            showAlert("Please enter Customer ID and add items to the order!");
            return;
        }
        try {
            Order order = new Order(customerIdField.getText(), selectedItems, new Date());
            DocumentManager.writeOneCommandCSV(order);
            generateBill(order);
        } catch (InvalidIDException e) {
            showAlert(e.getMessage());
            return;
        }    
        selectedItems.clear();
        orderListView.getItems().clear();
        totalLabel.setText("Total: £0.00");
        customerIdField.clear();
    }

    private void generateBill(Order order) {
        String bill = "Coffee Shop Bill\n";
        bill += "Customer ID: " + order.getCustomerID() + "\n";
        bill += "Order Time: " + order.toString() + "\n";
        bill += "--------------------------------\n";
        for (Items item : order.getOrderList()) {
            bill += item.get_Name() + " - £" + item.get_pricePerUnit() + "\n";
        }
        bill += "--------------------------------\n";
        bill += "Total: £" + String.format("%.2f", order.calculatePrice());

        Alert billAlert = new Alert(Alert.AlertType.INFORMATION);
        billAlert.setTitle("Bill");
        billAlert.setHeaderText("Order Bill");
        billAlert.setContentText(bill);
        billAlert.showAndWait();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}