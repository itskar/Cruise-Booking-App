package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/*
* Author: Karthik Umashankar
* CPSC 24500-002 Final Project
*/
//Controller class that controls all the actions on the AddEditShips page

public class AddEditShipController implements Initializable{
	// Declare class variable to store a ship
	private Ship ship;
	private Ship tempShip;
	
	// Inject all the FXML components from the Add/Edit Page
	@FXML private TextField txtShipID;
	@FXML private TextField txtShipName;
	@FXML private CheckBox chkInService;
	@FXML private TextField txtPassengerLimit;
	@FXML private Button btnAddEdit;
	@FXML private Button btnBack;
	@FXML private Label lblShipEditMessage;
	@FXML private Label lblShipEditTitle;
	
	/*Constructor that accepts a Ship object and sets it to the class' ship to populate forms
	  for editing purposes*/
	public AddEditShipController(){
		this.ship = ShipsController.selectedShip;
		tempShip = ship;
	}
	
	//Initializes the form depending on Add/Edit mode.
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(this.ship==null) {
			lblShipEditTitle.setText("Add a new Ship");
			btnAddEdit.setText("Add");
		}
		else {
			lblShipEditTitle.setText("Edit Ship");
			btnAddEdit.setText("Save");
			txtShipID.setText(ship.getShipID());
			txtShipName.setText(ship.getShipName());
			chkInService.setSelected(ship.isInService());
			txtPassengerLimit.setText(String.valueOf(ship.getPassengerLimit()));
			
		}
		
	}
	
	// Handle Add/Edit Button Click
	public void addButtonClicked (ActionEvent e) throws IOException {
		if(checkAllFields()) {
			String id = txtShipID.getText();
			String name  = txtShipName.getText();
			boolean inService = chkInService.isSelected();
			int passengerLimit=Integer.parseInt(txtPassengerLimit.getText());
			ship = new Ship(id,name,inService,passengerLimit);
			ShipsController.shipSet.add(ship);
			ShipsController.shipCount++;
			if(ShipsController.shipCount!=ShipsController.shipSet.size()) {
				ShipsController.shipCount--;
				lblShipEditMessage.setText("A ship named " + name+ " already exists. Please try again.");
				lblShipEditMessage.setVisible(true);
				ship = tempShip;
			}
			else {
				btnAddEdit.getScene().getWindow().hide();
			}
					
		 }
		else {
			lblShipEditMessage.setText("One or more field is empty/incorrect. Please try again.");
			lblShipEditMessage.setVisible(true);
		}
		
	}
	
	//Handle Back button click
	public void backButtonClicked (ActionEvent e) {
		btnAddEdit.getScene().getWindow().hide();
	}
	
	// Validate all form fields to ensure no empty records and type safe entries.
	public boolean checkAllFields() {
		boolean validated = false;
		if(txtShipID.getText() != null && txtShipName.getText()!= null && txtShipID.getText() != "" && txtShipName.getText() != "" ) {
				validated = true;
		}
		else {
			validated = false;
			return validated;
		}
		if(txtPassengerLimit.getText() != null && txtPassengerLimit.getText() != "" ) {
			try{
				Integer.parseInt(txtPassengerLimit.getText());
				validated = true;
			}catch (Exception e) {
				validated = false;
			}	
		}
		else {
			validated = false;
		}
		
		return validated;
	}
	
	
	//Returns the ship instance of the class
	Ship getShip() {
		return ship;
	}
	
}

