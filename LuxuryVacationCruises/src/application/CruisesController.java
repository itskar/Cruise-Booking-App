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

// Controller class that controls all the actions on the 'Cruises' tab of the applications
public class CruisesController implements Initializable{
	// Class level data variables to keep track of Cruises
	public static HashSet<Cruise> cruiseSet; //Stores Cruises
	public static int cruiseCount; // Counts the number of Cruises to compare with HashSet later
	public static Cruise selectedCruise;
	
	public ObservableList<Cruise> cruiseList; //Final list of Cruises displayed
	private final String dataPath = "data/Cruises.csv";
	public Stage subStage;
	public Stage stage;
	public Scene scene;
	public Parent root;
	
	// Inject all the FXML components from the Cruises tab
	@FXML
	public TableView<Cruise> tblCruises;
	@FXML
	public TableColumn<Cruise, String> tcmShipName;
	@FXML
	public TableColumn<Cruise, String> tcmCruiseName;
	@FXML
	public TableColumn<Cruise, LocalDate> tcmStartDate;
	@FXML
	public TableColumn<Cruise, Integer> tcmDuration;
	@FXML
	public TableColumn<Cruise, Integer> tcmNumOfPassengers;
	@FXML 
	public Label lblCruiseMessage;
	
	
	// Initialize the Cruises tab with some data
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Bind columns with data values
		tcmShipName.setCellValueFactory(new PropertyValueFactory<>("shipName"));
		tcmCruiseName.setCellValueFactory(new PropertyValueFactory<>("cruiseName"));
		tcmStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
		tcmDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
		tcmNumOfPassengers.setCellValueFactory(new PropertyValueFactory<>("numberOfPassengers"));
		lblCruiseMessage.setVisible(false);
		
		
		// Hash Set to ensure no duplicate entries 
		cruiseSet=new HashSet<>();
		
		// Populate HashSet with cruise objects from CSV file.
		try {
			String line = "";
			String cruiseName, shipName;
			LocalDate startDate;
			int duration;
			int numberOfPassengers = 0;
			BufferedReader br = new BufferedReader(new FileReader(dataPath));
			while((line = br.readLine())!=null) {
				String[] values = line.split(",");
				shipName = values[0];
				cruiseName = values[1];
				startDate = LocalDate.parse(values[2]);
				duration = Integer.parseInt(values[3]);
				numberOfPassengers = Integer.parseInt(values[4]);
				cruiseSet.add(new Cruise(shipName,cruiseName,startDate,duration,numberOfPassengers));
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Set cruise count to HashSet size and add all the Cruises from HashSet to the list
		cruiseList = FXCollections.observableArrayList(cruiseSet);
		cruiseCount=cruiseSet.size();
		
		// Display the list on the table
		tblCruises.setItems(cruiseList);
	}
	
	// Handle Add cruise Button click
	public void addCruiseButtonClicked (ActionEvent e) throws IOException {
		Cruise newCruise;
		selectedCruise = null;
		
		// Launch the Add/Edit page for Cruises
		FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/AddEditCruises.fxml"));
		root = loader.load();	
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		subStage = new Stage();
		Image icon = new Image("Icon.png");
		subStage.getIcons().add(icon);
		scene = new Scene(root);
		subStage.setTitle("Add a new cruise");
		scene.getStylesheets().add(getClass().getResource("resources/application.css").toExternalForm());
		subStage.setScene(scene);
		subStage.setResizable(false);
		AddEditCruiseController aecController = loader.getController();
		subStage.initOwner(stage);
		subStage.initModality(Modality.WINDOW_MODAL); // Makes sure the user can't change focused window
		subStage.showAndWait();
		
		newCruise = aecController.getCruise();
		if(newCruise!=null) {
			addCruise(newCruise);
		}
		
	}
	
	// Handle Edit cruise Button click
	public void editCruiseButtonClicked(ActionEvent e) throws IOException {
		if(tblCruises.getSelectionModel().isEmpty()) {
			lblCruiseMessage.setText("Please select a record to edit.");
			lblCruiseMessage.setVisible(true);
			PauseTransition pause = new PauseTransition(Duration.seconds(3));
			pause.setOnFinished(event -> lblCruiseMessage.setText(null));
			pause.play();
		}
		else {
			Cruise newCruise;
			selectedCruise = tblCruises.getSelectionModel().getSelectedItem();
			cruiseSet.remove(selectedCruise);
			cruiseCount=cruiseSet.size();
			cruiseList.remove(selectedCruise);
			
			// Launch the Add/Edit page for Cruises
			FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/AddEditCruises.fxml"));
			root = loader.load();	
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			subStage = new Stage();
			Image icon = new Image("Icon.png");
			subStage.getIcons().add(icon);
			scene = new Scene(root);
			subStage.setTitle("Edit this cruise");
			scene.getStylesheets().add(getClass().getResource("resources/application.css").toExternalForm());
			subStage.setScene(scene);
			subStage.setResizable(false);
			AddEditCruiseController aesController = loader.getController();
			subStage.initOwner(stage);
			subStage.initModality(Modality.WINDOW_MODAL); // Makes sure the user can't change focused window
			subStage.showAndWait();
			
			newCruise = aesController.getCruise();
			if(newCruise!=null) {
				addCruise(newCruise);
			}
			
		}
	}

	// Add Cruises to the HashSet 
	public void addCruise(Cruise cruise) {
		cruiseSet.add(cruise);
		cruiseList.add(cruise);
		tblCruises.setItems(cruiseList);
	}
	
	// Handle Print cruise Names Button click
	public void printcruiseListClicked(ActionEvent e) {
		System.out.println("Cruises: ");
		for(Cruise s: cruiseList) {
			System.out.println(s.getCruiseName());
		}
		lblCruiseMessage.setText("Cruises are printed in the console.");
		lblCruiseMessage.setVisible(true);
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(event -> lblCruiseMessage.setText(null));
		pause.play();
		System.out.println();
	}
	
	// Handle Print All Cruises Button click
	public void printCruisesDetailsClicked(ActionEvent e) {
		System.out.println("All Cruises details: ");

		for(Cruise s: cruiseList) {
			System.out.println(s);
		}
		lblCruiseMessage.setText("All the Cruises details are printed in the console.");
		lblCruiseMessage.setVisible(true);
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(event -> lblCruiseMessage.setText(null));
		pause.play();
		System.out.println();
	}
}
