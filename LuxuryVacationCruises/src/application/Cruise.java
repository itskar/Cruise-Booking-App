package application;

import java.time.LocalDate;
import java.util.Objects;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/*
 * Author: Karthik Umashankar
 * CPSC 24500-002 Final Project
 */

/* Class the models a Cruise object and stores it's name, the ship that is assigned to it, the start date
 * of the cruise, the duration (in days), and the number of passengers booked for it currently. 
*/
public class Cruise{
	// Variables of type Simple...Property optimized for a TableView
	private SimpleStringProperty cruiseName;
	private SimpleStringProperty shipName;
	private SimpleObjectProperty<LocalDate> startDate;
	private SimpleIntegerProperty duration;
	private SimpleIntegerProperty numberOfPassengers;
	
	
	// Empty Constructor
	public Cruise() {
		super();
	}
	
	// Constructor with arguments
	public Cruise(String shipName, String cruiseName,LocalDate startDate, int duration, int numOfPassengers) {
		super();
		this.cruiseName = new SimpleStringProperty(cruiseName);
		this.shipName = new SimpleStringProperty(shipName);
		this.startDate = new SimpleObjectProperty<LocalDate>(startDate);
		this.duration = new SimpleIntegerProperty(duration);
		this.numberOfPassengers = new SimpleIntegerProperty(numOfPassengers);
	}
	
	// Getters and Setters
	public String getCruiseName() {
		return cruiseName.get();
	}
	public void setCruiseName(String CruiseName) {
		this.cruiseName.set(CruiseName);
	}
	public String getShipName() {
		return shipName.get();
	}
	public void setShipName(String ShipName) {
		this.shipName.set(ShipName);
	}
	public LocalDate getStartDate() {
		return startDate.get();
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate.set(startDate);
	}
	public int getDuration() {
		return duration.get();
	}
	public void setDuration(int duration) {
		this.duration.set(duration);
	}
	public int getNumberOfPassengers() {
		return numberOfPassengers.get();
	}
	public void setNumberOfPassengers(int numOfPassengers) {
		this.numberOfPassengers.set(numOfPassengers);
	}
	
	// Overrides the default hashCode function to compare 2 Cruise objects using their names.
	@Override
	public int hashCode() {
		return Objects.hash(cruiseName.get());
	}
	
	// Overrides the default equals function to perform the comparison.
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cruise other = (Cruise) obj;
		return Objects.equals(cruiseName.get(), other.cruiseName.get());
		
	}
	
	// To String
	@Override
	public String toString() {
		return "Cruise Name: " + cruiseName.get() + ", Ship Name: " + shipName.get() + ", Start Date: " + startDate.get() + ", Duration: "
				+ duration.get() + " days, " + " Number of Passengers: " + numberOfPassengers.get();
	}
}
