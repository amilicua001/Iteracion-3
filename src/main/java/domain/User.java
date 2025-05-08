package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlSeeAlso ({Traveler.class, Driver.class})
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	@XmlID
	@Id 
	private String email;
	private String name; 
	private String phone;
	private String password;
	private float money; 
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Ride> rides=new Vector<Ride>();
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private ArrayList<MoneyMovement> moneyMovementList=new ArrayList<MoneyMovement>();
	
	public User() {
		super();
	}
	

	public User(String email, String name, String phone, String password) {
		super();
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.password = password;
		this.money=0;
	}


	/**
	 * @return the rides
	 */
	public List<Ride> getRides() {
		return rides;
	}


	/**
	 * @param rides the rides to set
	 */
	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}


	/**
	 * @return the moneyMovmentList
	 */
	public ArrayList<MoneyMovement> getMoneyMovement() {
		return moneyMovementList;
	}


	/**
	 * @param moneyMovementList the moneyMovementList to set
	 */
	public void setMoneyMovementList(ArrayList<MoneyMovement> moneyMovementList) {
		this.moneyMovementList = moneyMovementList;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public float getMoney() {
		return money;
	}


	public void setMoney(float f) {
		this.money = f;
	}


	public User(String email, String name) {
		this.email = email;
		this.name = name;
	}
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	public String toString(){
		return name;
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
		if (email != other.getEmail())
			return false;
		return true;
	}

}

