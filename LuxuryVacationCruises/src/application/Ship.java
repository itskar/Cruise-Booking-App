package application;

import java.util.Objects;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/*
 * Author: Karthik Umashankar
 * CPSC 24500-002 Final Project
 */

/* Class the models a ship object and stores it's Name, ID, whether it's in service and 
its passenger limit
*/
public class Ship {
	// Variables of type Simple...Property optimized for a TableView
	private SimpleStringProperty shipName;
	private SimpleStringProperty shipID;
	private SimpleBooleanProperty inService;
	private SimpleIntegerProperty passengerLimit;
	
	// Empty Constructor
	public Ship() {
		super();
	}
	
	// Constructor with arguments
	public Ship(String shipID, String shipName,boolean inService, int passengerLimit) {
		super();
		this.shipName = new SimpleStringProperty(shipName);
		this.shipID = new SimpleStringProperty(shipID);
		this.inService = new SimpleBooleanProperty(inService);
		this.passengerLimit = new SimpleIntegerProperty(passengerLimit);
	}
	
	// Getters and Setters
	public String getShipName() {
		return shipName.get();
	}
	public void setShipName(String shipName) {
		this.shipName.set(shipName);
	}
	public String getShipID() {
		return shipID.get();
	}
	public void setShipID(String shipID) {
		this.shipID.set(shipID);
	}
	public boolean isInService() {
		return inService.get();
	}
	public void setInService(boolean inService) {
		this.inService.set(inService);;
	}
	public int getPassengerLimit() {
		return passengerLimit.get();
	}
	public void setPassengerLimit(int passengerLimit) {
		this.passengerLimit.set(passengerLimit);
	}
	
	// Overrides the default hashCode function to compare 2 ship objects using their names.
	@Override
	public int hashCode() {
		return Objects.hash(shipName.get());
	}
	
	// Overrides the default equals function to perform the comparison.
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ship other = (Ship) obj;
		return Objects.equals(shipName.get(), other.shipName.get());
		
	}
	
	// To String
	@Override
	public String toString() {
		return "Ship Name: " + shipName.get() + ", Ship ID: " + shipID.get() + ", In Service: " + inService.get() + ", Passenger Limit:"
				+ passengerLimit.get();
	}
}
