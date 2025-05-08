package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Driver extends User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private ArrayList<Car> cars= new ArrayList<Car>();
	private float puntuazioa=-1;
	private int reviewCop=0;
	public Driver() {
		super();
	}


	
	public Driver(String email, String name, String phone, String password) {
		super(email,name,phone,password);
	}



	/**
	 * This method creates a bet with a question, minimum bet ammount and percentual profit
	 * 
	 * @param question to be added to the event
	 * @param betMinimum of that question
	 * @return Bet
	 */
	public Ride addRide(String from, String to, Date date, int nPlaces, float price, Car car)  {
        Ride ride=new Ride(from,to,date,nPlaces,price, this, car);
        getRides().add(ride);
        return ride;
	}

	/**
	 * @return the puntuazioa
	 */
	public float getPuntuazioa() {
		return puntuazioa;
	}


	/**
	 * @param puntuazioa the puntuazioa to set
	 */
	public void setPuntuazioa(float puntuazioa) {
		this.puntuazioa = puntuazioa;
	}


	/**
	 * @return the reviewcop
	 */
	public int getReviewCop() {
		return reviewCop;
	}


	/**
	 * @param reviewCop the reviewCop to set
	 */
	public void setReviewCop(int reviewCop) {
		this.reviewCop = reviewCop;
	}


	/**
	 * This method checks if the ride already exists for that driver
	 * 
	 * @param from the origin location 
	 * @param to the destination location 
	 * @param date the date of the ride 
	 * @return true if the ride exists and false in other case
	 */
	public boolean doesRideExists(String from, String to, Date date)  {	
		for (Ride r:getRides())
			if ( (java.util.Objects.equals(r.getFrom(),from)) && (java.util.Objects.equals(r.getTo(),to)) && (java.util.Objects.equals(r.getDate(),date)) )
			 return true;
		
		return false;
	}
		
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Driver other = (Driver) obj;
		if (this.getEmail() != other.getEmail())
			return false;
		return true;
	}

	public Ride removeRide(String from, String to, Date date) {
		boolean found=false;
		int index=0;
		Ride r=null;
		while (!found && index<=getRides().size()) {
			r=getRides().get(++index);
			if ( (java.util.Objects.equals(r.getFrom(),from)) && (java.util.Objects.equals(r.getTo(),to)) && (java.util.Objects.equals(r.getDate(),date)) )
			found=true;
		}
			
		if (found) {
			getRides().remove(index);
			return r;
		} else return null;
	}


	public ArrayList<Car> getCars() {
		return cars;
	}


	public void setCars(ArrayList<Car> cars) {
		this.cars = cars;
	}
	
}
