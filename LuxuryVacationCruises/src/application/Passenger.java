package application;
import java.time.LocalDate;
import java.util.Objects;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
/*
 * Author: Karthik Umashankar
 * CPSC 24500-002 Final Project
 */

/* Class the models a passenger object and stores their first name, last name, date of birth, and the cruise they have booked.
*/
public class Passenger {
	// Variables of type Simple...Property optimized for a TableView
	private SimpleStringProperty firstName;
	private SimpleStringProperty lastName;
	private SimpleObjectProperty<LocalDate> dateOfBirth;
	private SimpleStringProperty cruiseName;
	
	// Empty Constructor
	public Passenger() {
		super();
	}
	
	// Constructor with arguments
	public Passenger(String firstName, String lastName, LocalDate dateOfBirth, String cruiseName) {
		super();
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.dateOfBirth = new SimpleObjectProperty<LocalDate>(dateOfBirth);
		this.cruiseName = new SimpleStringProperty(cruiseName);
	}
	
	// Getters and Setters
	public String getFirstName() {
		return firstName.get();
	}
	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}
	public String getLastName() {
		return lastName.get();
	}
	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth.get();
	}
	public void setdateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth.set(dateOfBirth);;
	}
	public String getCruiseName() {
		return cruiseName.get();
	}
	public void setCruiseName(String cruiseName) {
		this.cruiseName.set(cruiseName);
	}
	
	// Overrides the default hashCode function to compare 2 ship objects using their names.
		@Override
		public int hashCode() {
			return Objects.hash(firstName.get(),lastName.get(), cruiseName.get());
		}
		
		// Overrides the default equals function to compare if there's two bookings for the same people.
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Passenger other = (Passenger) obj;
			return Objects.equals(firstName.get(), other.getFirstName()) && lastName.get()== other.getLastName() 
					&& cruiseName.get()==other.getCruiseName();
			
		}
		
	
	// To String
	@Override
	public String toString() {
		return "First Name: " + firstName.get() + ", Last Name: " + lastName.get() + ", Date of birth: " + dateOfBirth.get() + ", Cruise Booked: "
				+ cruiseName.get();
	}
}
