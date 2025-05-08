package domain;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Booking implements Serializable{
	@Id
	@XmlID
	private String id;
	private Driver driver;
	private Traveler traveler;
	private Ride ride;
	private float blockedMoney;
	private int nPlaces;
	private String state;
	private String note;
	
	public Booking() {
		super();
	}
	
	public Booking(Driver driver, Traveler traveler, Ride ride, float blockedMoney, int nPlaces,String note) {
		super();
		this.id = ride.getRideNumber() + traveler.getEmail() + note;
		this.driver = driver;
		this.traveler = traveler;
		this.ride = ride;
		this.blockedMoney = blockedMoney;
		this.nPlaces = nPlaces;
		this.note=note;
		this.state="Pending";
	}
	
	
	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	/**
	 * @return the nPlaces
	 */
	public int getNPlaces() {
		return nPlaces;
	}


	/**
	 * @param nPlaces the nPlaces to set
	 */
	public void setNPlaces(int nPlaces) {
		this.nPlaces = nPlaces;
	}


	/**
	 * @return the driver
	 */
	public Driver getDriver() {
		return driver;
	}
	/**
	 * @param driver the driver to set
	 */
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	/**
	 * @return the traveler
	 */
	public Traveler getTraveler() {
		return traveler;
	}
	/**
	 * @param traveler the traveler to set
	 */
	public void setTraveler(Traveler traveler) {
		this.traveler = traveler;
	}
	/**
	 * @return the ride
	 */
	public Ride getRide() {
		return ride;
	}
	/**
	 * @param ride the ride to set
	 */
	public void setRide(Ride ride) {
		this.ride = ride;
	}
	/**
	 * @return the blockedMoney
	 */
	public float getBlockedMoney() {
		return blockedMoney;
	}
	/**
	 * @param blocekdMoney the blockedMoney to set
	 */
	public void setBlockedMoney(float blocekdMoney) {
		this.blockedMoney = blocekdMoney;
	}
	


	@Override
	public String toString() {
		return blockedMoney + "€ " +traveler.getName() + " - "+ ride.toString() +" - "+ nPlaces + " - " + note +" - "+ state;
	}

	
}
