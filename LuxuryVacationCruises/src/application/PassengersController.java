package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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

// Controller class that controls all the actions on the 'Passengers' tab of the applications
public class PassengersController implements Initializable{
	// Class level data variables to keep track of Passengers
	public static HashSet<Passenger> passengerSet; //Stores Passengers
	public static int passengerCount; // Counts the number of Passengers to keep track of max passengers.
	public static Passenger selectedPassenger;
	
	public ObservableList<Passenger> passengerList; //Final list of Passengers displayed
	private final String dataPath = "data/passengers.csv";
	public Stage subStage;
	public Stage stage;
	public Scene scene;
	public Parent root;
	
	// Inject all the FXML components from the Passengers tab
	@FXML
	public TableView<Passenger> tblPassengers;
	@FXML
	public TableColumn<Passenger, String> tcmFirstName;
	@FXML
	public TableColumn<Passenger, String> tcmLastName;
	@FXML
	public TableColumn<Passenger, Boolean> tcmDateOfBirth;
	@FXML
	public TableColumn<Passenger, Integer> tcmCruiseName;
	@FXML 
	public Label lblPassengerMessage;
	
	
	// Initialize the Passengers tab with some data
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Bind columns with data values
		tcmFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		tcmLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		tcmDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
		tcmCruiseName.setCellValueFactory(new PropertyValueFactory<>("cruiseName"));
		lblPassengerMessage.setVisible(false);
		
		
		// Hash Set to store passengers
		passengerSet=new HashSet<>();
		
		// Populate HashSet with Passenger objects from CSV file.
		try {
			String line = "";
			String firstName, lastName, cruiseName;
			LocalDate dateOfBirth;
			BufferedReader br = new BufferedReader(new FileReader(dataPath));
			while((line = br.readLine())!=null) {
				String[] values = line.split(",");
				firstName = values[0];
				lastName = values[1];
				dateOfBirth = LocalDate.parse(values[2]);
				cruiseName = values[3];
				passengerSet.add(new Passenger(firstName,lastName,dateOfBirth,cruiseName));
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Set Passenger count to HashSet size and add all the Passengers from HashSet to the list
		passengerList = FXCollections.observableArrayList(passengerSet);
		passengerCount=passengerSet.size();
		
		// Display the list on the table
		tblPassengers.setItems(passengerList);
	}
	
	// Handle Add Passenger Button click
	public void addPassengerButtonClicked (ActionEvent e) throws IOException {
		Passenger newPassenger;
		selectedPassenger = null;
		
		// Launch the Add/Edit page for Passengers
		FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/AddEditPassengers.fxml"));
		root = loader.load();	
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		subStage = new Stage();
		Image icon = new Image("Icon.png");
		subStage.getIcons().add(icon);
		scene = new Scene(root);
		subStage.setTitle("Add a new Passenger");
		scene.getStylesheets().add(getClass().getResource("resources/application.css").toExternalForm());
		subStage.setScene(scene);
		subStage.setResizable(false);
		AddEditPassengerController aesController = loader.getController();
		subStage.initOwner(stage);
		subStage.initModality(Modality.WINDOW_MODAL); // Makes sure the user can't change focused window
		subStage.showAndWait();
		
		newPassenger = aesController.getPassenger();
		if(newPassenger!=null) {
			addPassenger(newPassenger);
		}
	}
	
	// Handle Edit Passenger Button click
	public void editPassengerButtonClicked(ActionEvent e) throws IOException {
		if(tblPassengers.getSelectionModel().isEmpty()) {
			lblPassengerMessage.setText("Please select a record to edit.");
			lblPassengerMessage.setVisible(true);
			PauseTransition pause = new PauseTransition(Duration.seconds(3));
			pause.setOnFinished(event -> lblPassengerMessage.setText(null));
			pause.play();
		}
		else {
			Passenger newPassenger;
			selectedPassenger = tblPassengers.getSelectionModel().getSelectedItem();
			passengerSet.remove(selectedPassenger);
			passengerCount=passengerSet.size();
			passengerList.remove(selectedPassenger);
			
			// Launch the Add/Edit page for Passengers
			FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/AddEditPassengers.fxml"));
			root = loader.load();	
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			subStage = new Stage();
			Image icon = new Image("Icon.png");
			subStage.getIcons().add(icon);
			scene = new Scene(root);
			subStage.setTitle("Edit this Passenger");
			scene.getStylesheets().add(getClass().getResource("resources/application.css").toExternalForm());
			subStage.setScene(scene);
			subStage.setResizable(false);
			AddEditPassengerController aepController = loader.getController();
			subStage.initOwner(stage);
			subStage.initModality(Modality.WINDOW_MODAL); // Makes sure the user can't change focused window
			subStage.showAndWait();
			
			newPassenger = aepController.getPassenger();
			if(newPassenger!=null) {
				addPassenger(newPassenger);
			}
			
		}
	}

	// Add Passengers to the HashSet and return boolean if the addition was successful
	public void addPassenger(Passenger Passenger) {
		passengerSet.add(Passenger);
		passengerList.add(Passenger);
		tblPassengers.setItems(passengerList);
	}
	
	
	// Handle Print All Passengers Button click
	public void printPassengerClicked(ActionEvent e) {
		System.out.println("All Passengers' details: ");

		for(Passenger s: passengerList) {
			System.out.println(s);
		}
		lblPassengerMessage.setText("All the Passengers details are printed in the console.");
		lblPassengerMessage.setVisible(true);
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(event -> lblPassengerMessage.setText(null));
		pause.play();
		System.out.println();
	}
}
