package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Car implements Serializable{
	@Id
	@XmlID
	private String registration;
	private Driver driver;
	private String colour;
	private String brand;
	private String model;
	private int nPlaces;
	
	
	public Car() {
		super();
	}
	
	public Car(String registration, Driver driver, String colour, String brand, String model, int nPlaces) {
		super();
		this.registration = registration;
		this.driver = driver;
		this.colour = colour;
		this.brand = brand;
		this.model = model;
		this.nPlaces = nPlaces;
	}
	
	public String getRegistration() {
		return registration;
	}
	public void setRegistration(String registration) {
		this.registration = registration;
	}
	public Driver getDriver() {
		return driver;
	}
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getNPlaces() {
		return nPlaces;
	}
	public void setNPlaces(int nPlaces) {
		this.nPlaces = nPlaces;
	}
	@Override
	public String toString() {
		return  brand +" | "+ model +" | "+ registration;
	}
	
}


