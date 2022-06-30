package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
/*
 * Author: Karthik Umashankar
 * CPSC 24500-002 Final Project
 */

// Controller class that controls all the actions on the 'Ships' tab of the applications
public class ShipsController implements Initializable{
	// Class level data variables to keep track of ships
	public static HashSet<Ship> shipSet; //Stores ships
	public static int shipCount; // Counts the number of ships to compare with HashSet later
	public static Ship selectedShip;
	
	public ObservableList<Ship> shipList; //Final list of ships displayed
	private final String dataPath = "data/ships.csv";
	public Stage subStage;
	public Stage stage;
	public Scene scene;
	public Parent root;
	
	// Inject all the FXML components from the Ships tab
	@FXML
	public TableView<Ship> tblShips;
	@FXML
	public TableColumn<Ship, String> tcmShipID;
	@FXML
	public TableColumn<Ship, String> tcmShipName;
	@FXML
	public TableColumn<Ship, Boolean> tcmInservice;
	@FXML
	public TableColumn<Ship, Integer> tcmPassengerLimit;
	@FXML 
	public Label lblShipMessage;
	
	
	// Initialize the Ships tab with some data
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Bind columns with data values
		tcmShipID.setCellValueFactory(new PropertyValueFactory<>("shipID"));
		tcmShipName.setCellValueFactory(new PropertyValueFactory<>("shipName"));
		tcmInservice.setCellValueFactory(new PropertyValueFactory<>("inService"));
		tcmPassengerLimit.setCellValueFactory(new PropertyValueFactory<>("passengerLimit"));
		lblShipMessage.setVisible(false);
		
		
		// Hash Set to ensure no duplicate entries 
		shipSet=new HashSet<>();
		
		// Populate HashSet with ship objects from CSV file.
		try {
			String line = "";
			String name, id;
			boolean inService;
			int passengerLimit;;
			BufferedReader br = new BufferedReader(new FileReader(dataPath));
			while((line = br.readLine())!=null) {
				String[] values = line.split(",");
				id = values[0];
				name = values[1];
				inService = Boolean.parseBoolean(values[2]);
				passengerLimit = Integer.parseInt(values[3]);
				shipSet.add(new Ship(id,name,inService,passengerLimit));
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Set ship count to HashSet size and add all the ships from HashSet to the list
		shipList = FXCollections.observableArrayList(shipSet);
		shipCount=shipSet.size();
		
		// Display the list on the table
		tblShips.setItems(shipList);
	}
	
	// Handle Add Ship Button click
	public void addButtonClicked (ActionEvent e) throws IOException {
		Ship newShip;
		selectedShip = null;
		
		// Launch the Add/Edit page for ships
		FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/AddEditShips.fxml"));
		root = loader.load();	
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		subStage = new Stage();
		Image icon = new Image("Icon.png");
		subStage.getIcons().add(icon);
		scene = new Scene(root);
		subStage.setTitle("Add a new Ship");
		scene.getStylesheets().add(getClass().getResource("resources/application.css").toExternalForm());
		subStage.setScene(scene);
		subStage.setResizable(false);
		AddEditShipController aesController = loader.getController();
		subStage.initOwner(stage);
		subStage.initModality(Modality.WINDOW_MODAL); // Makes sure the user can't change focused window
		subStage.showAndWait();
		
		newShip = aesController.getShip();
		if(newShip!=null) {
			addShip(newShip);
		}
		
	}
	
	// Handle Edit Ship Button click
	public void editButtonClicked(ActionEvent e) throws IOException {
		if(tblShips.getSelectionModel().isEmpty()) {
			lblShipMessage.setText("Please select a record to edit.");
			lblShipMessage.setVisible(true);
			PauseTransition pause = new PauseTransition(Duration.seconds(3));
			pause.setOnFinished(event -> lblShipMessage.setText(null));
			pause.play();
		}
		else {
			Ship newShip;
			selectedShip = tblShips.getSelectionModel().getSelectedItem();
			shipSet.remove(selectedShip);
			shipCount=shipSet.size();
			shipList.remove(selectedShip);
			
			// Launch the Add/Edit page for ships
			FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/AddEditShips.fxml"));
			root = loader.load();	
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			subStage = new Stage();
			Image icon = new Image("Icon.png");
			subStage.getIcons().add(icon);
			scene = new Scene(root);
			subStage.setTitle("Edit this Ship");
			scene.getStylesheets().add(getClass().getResource("resources/application.css").toExternalForm());
			subStage.setScene(scene);
			subStage.setResizable(false);
			AddEditShipController aesController = loader.getController();
			subStage.initOwner(stage);
			subStage.initModality(Modality.WINDOW_MODAL); // Makes sure the user can't change focused window
			subStage.showAndWait();
			
			newShip = aesController.getShip();
			if(newShip!=null) {
				addShip(newShip);
			}
			
		}
	}

	// Add ships to the HashSet 
	public void addShip(Ship ship) {
		shipSet.add(ship);
		shipList.add(ship);
		tblShips.setItems(shipList);
	}
	
	// Handle Print Ship Names Button click
	public void printShipNameClicked(ActionEvent e) {
		System.out.println("Ship Names: ");
		for(Ship s: shipList) {
			System.out.println(s.getShipName());
		}
		lblShipMessage.setText("Ship names are printed in the console.");
		lblShipMessage.setVisible(true);
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(event -> lblShipMessage.setText(null));
		pause.play();
		System.out.println();
	}
	
	// Handle Print Ships in Service Button click
	public void printShipsInServiceClicked(ActionEvent e) {
		System.out.println("Ships in service: ");
		
		for(Ship s: shipList) {
			if(s.isInService()) {
				System.out.println(s);
			}
		}
		lblShipMessage.setText("Ships in service are printed in the console.");
		lblShipMessage.setVisible(true);
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(event -> lblShipMessage.setText(null));
		pause.play();
		System.out.println();
	}
	
	// Handle Print All Ships Button click
	public void printAllShipsClicked(ActionEvent e) {
		System.out.println("All ships details: ");

		for(Ship s: shipList) {
			System.out.println(s);
		}
		lblShipMessage.setText("All the ships details are printed in the console.");
		lblShipMessage.setVisible(true);
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(event -> lblShipMessage.setText(null));
		pause.play();
		System.out.println();
	}
}
