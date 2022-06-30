package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/*
* Author: Karthik Umashankar
* CPSC 24500-002 Final Project
*/
//Controller class that controls all the actions on the AddEditPassengers page

public class AddEditPassengerController implements Initializable{
	// Declare class variable to store a Passenger
	private Passenger passenger;
	private Passenger tempPassenger;
	private ArrayList<String> cruisesAvailable = new ArrayList<>();
	
	// Inject all the FXML components from the Add/Edit Page
	@FXML private ComboBox<String> cbxCruises;
	@FXML private TextField txtFirstName;
	@FXML private TextField txtLastName;
	@FXML private DatePicker dtpDateOfBirth;
	@FXML private Button btnAddEdit;
	@FXML private Button btnBack;
	@FXML private Label lblPassengerEditMessage;
	@FXML private Label lblPassengerEditTitle;
	
	/*Constructor that accepts a Passenger object and sets it to the class' Passenger to populate forms
	  for editing purposes*/
	public AddEditPassengerController(){
		this.passenger = PassengersController.selectedPassenger;
		tempPassenger = passenger;
	}
	
	//Initializes the form depending on Add/Edit mode.
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Add all cruises that are available and not fully booked to the combo box
		for(Cruise c: CruisesController.cruiseSet) {
			for(Ship s: ShipsController.shipSet) {
				if(c.getShipName().equals(s.getShipName()) && c.getNumberOfPassengers() < s.getPassengerLimit()) {
					System.out.println(c + " " + s);
					cruisesAvailable.add(c.getCruiseName());
				}
			}
		}
		
		cbxCruises.getItems().addAll(cruisesAvailable);
		
		if(this.passenger==null) {
			lblPassengerEditTitle.setText("Add a new Passenger");
			btnAddEdit.setText("Add");
		}
		else {
			lblPassengerEditTitle.setText("Edit Passenger");
			btnAddEdit.setText("Save");
			cbxCruises.getSelectionModel().select(passenger.getCruiseName());
			txtFirstName.setText(passenger.getFirstName());
			txtLastName.setText(passenger.getLastName());
			dtpDateOfBirth.setValue(passenger.getDateOfBirth());
		}
		
	}
	
	
	// Handle Add/Edit Button Click
	public void addButtonClicked (ActionEvent e) throws IOException {
		if(checkAllFields()) {
			String firstName  = txtFirstName.getText();
			String lastName = txtLastName.getText();
			LocalDate dob = dtpDateOfBirth.getValue();
			String cruiseName = cbxCruises.getSelectionModel().getSelectedItem();
			
			passenger = new Passenger(firstName,lastName,dob,cruiseName);
			PassengersController.passengerSet.add(passenger);
			PassengersController.passengerCount++;
			if(PassengersController.passengerCount != PassengersController.passengerSet.size()) {
				PassengersController.passengerCount --;
				lblPassengerEditMessage.setText("A Passenger named " + firstName + " " + lastName + ""
						+ " already has that booking. Please try again.");
				lblPassengerEditMessage.setVisible(true);
				passenger = tempPassenger;
			}
			else {
				btnAddEdit.getScene().getWindow().hide();
			}
					
		 }
		else {
			lblPassengerEditMessage.setText("One or more fields is empty/incorrect. Please try again.");
			lblPassengerEditMessage.setVisible(true);
		}
		
	}
	
	//Handle Back button click
	public void backButtonClicked (ActionEvent e) {
		btnAddEdit.getScene().getWindow().hide();
	}
	
	// Validate all form fields to ensure no empty records and type safe entries.
	public boolean checkAllFields() {
		boolean validated = false;
		if(cbxCruises.getSelectionModel().getSelectedItem() != null && txtFirstName.getText()!= null && dtpDateOfBirth.getValue()!=null
				&& txtFirstName.getText()!= "" && txtLastName.getText()!=null && txtLastName.getText() != "") {
			validated = true;
		}
		else {
			validated = false;
			return validated;
		}
		
		return validated;
	}
	
	
	//Returns the ship instance of the class
	Passenger getPassenger() {
		return passenger;
	}
	
}

