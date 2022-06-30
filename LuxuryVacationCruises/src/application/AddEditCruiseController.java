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
//Controller class that controls all the actions on the AddEditCruises page

public class AddEditCruiseController implements Initializable{
	// Declare class variable to store a Cruise
	private Cruise cruise;
	private Cruise tempCruise;
	private ArrayList<String> shipsInService = new ArrayList<>();
	
	// Inject all the FXML components from the Add/Edit Page
	@FXML private ComboBox<String> cbxShipName;
	@FXML private TextField txtCruiseName;
	@FXML private DatePicker dtpStartDate;
	@FXML private TextField txtDuration;
	@FXML private Button btnAddEdit;
	@FXML private Button btnBack;
	@FXML private Label lblCruiseEditMessage;
	@FXML private Label lblCruiseEditTitle;
	
	/*Constructor that accepts a Cruise object and sets it to the class' cruise to populate forms
	  for editing purposes*/
	public AddEditCruiseController(){
		this.cruise = CruisesController.selectedCruise;
		tempCruise = cruise;
	}
	
	//Initializes the form depending on Add/Edit mode.
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Add all ships that are in service to the combo box
		for(Ship s: ShipsController.shipSet) {
			if(s.isInService()) {
				shipsInService.add(s.getShipName());
			}
		}
		cbxShipName.getItems().addAll(shipsInService);
		
		if(this.cruise==null) {
			lblCruiseEditTitle.setText("Add a new Cruise");
			btnAddEdit.setText("Add");
		}
		else {
			lblCruiseEditTitle.setText("Edit Cruise");
			btnAddEdit.setText("Save");
			cbxShipName.getSelectionModel().select(cruise.getShipName());
			txtCruiseName.setText(cruise.getCruiseName());
			dtpStartDate.setValue(cruise.getStartDate());
			txtDuration.setText(String.valueOf(cruise.getDuration()));
			}
		
	}
	
	
	// Handle Add/Edit Button Click
	public void addButtonClicked (ActionEvent e) throws IOException {
		if(checkAllFields()) {
			String id = cbxShipName.getSelectionModel().getSelectedItem();
			String name  = txtCruiseName.getText();
			LocalDate startDt = dtpStartDate.getValue();
			int duration =Integer.parseInt(txtDuration.getText());
			cruise = new Cruise(id,name,startDt,duration, 0);
			CruisesController.cruiseSet.add(cruise);
			CruisesController.cruiseCount++;
			if(CruisesController.cruiseCount!=CruisesController.cruiseSet.size()) {
				CruisesController.cruiseCount--;
				lblCruiseEditMessage.setText("A cruise named " + name + " already exists. Please try again.");
				lblCruiseEditMessage.setVisible(true);
				cruise = tempCruise;
			}
			else {
				btnAddEdit.getScene().getWindow().hide();
			}
					
		 }
		else {
			lblCruiseEditMessage.setText("One or more field is empty/incorrect. Please try again.");
			lblCruiseEditMessage.setVisible(true);
		}
		
	}
	
	//Handle Back button click
	public void backButtonClicked (ActionEvent e) {
		btnAddEdit.getScene().getWindow().hide();
	}
	
	// Validate all form fields to ensure no empty records and type safe entries.
	public boolean checkAllFields() {
		boolean validated = false;
		if(cbxShipName.getSelectionModel().getSelectedItem() != null && txtCruiseName.getText()!= null && dtpStartDate.getValue()!=null
				&& txtCruiseName.getText()!= "") {
			validated = true;
		}
		else {
			validated = false;
			return validated;
		}
		if(txtDuration.getText() != null && txtDuration.getText() != "") {
			try{
				Integer.parseInt(txtDuration.getText());
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
	Cruise getCruise() {
		return cruise;
	}
	
}

