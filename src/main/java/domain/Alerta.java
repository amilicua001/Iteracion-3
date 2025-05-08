package domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.XmlAccessType;


@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Alerta implements Serializable {
	@XmlID
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer alertNumber;
	private String from;
	private String to;
	private int nPlaces;
	private Date date;
	private float priceMax;
	private Traveler traveler;
	
	public Alerta() {
		super();
	}
	
	public Alerta(String from, String to, int nPlaces, Date date, float priceMax,
			Traveler traveler) {
		super();
		this.from = from;
		this.to = to;
		this.nPlaces = nPlaces;
		this.date = date;
		this.priceMax = priceMax;
		this.traveler = traveler;
	}
	/**
	 * @return the alertNumber
	 */
	public Integer getAlertNumber() {
		return alertNumber;
	}
	/**
	 * @param alertNumber the alertNumber to set
	 */
	public void setAlertNumber(Integer alertNumber) {
		this.alertNumber = alertNumber;
	}
	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}
	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}
	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}
	/**
	 * @return the nPlaces
	 */
	public int getnPlaces() {
		return nPlaces;
	}
	/**
	 * @param nPlaces the nPlaces to set
	 */
	public void setnPlaces(int nPlaces) {
		this.nPlaces = nPlaces;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the priceMax
	 */
	public float getPriceMax() {
		return priceMax;
	}
	/**
	 * @param priceMax the priceMax to set
	 */
	public void setPriceMax(float priceMax) {
		this.priceMax = priceMax;
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
	@Override
	public String toString() {
		return "Alerta [alertNumber=" + alertNumber + ", from=" + from + ", to=" + to + ", nPlaces=" + nPlaces
				+ ", date=" + date + ", priceMax=" + priceMax + ", traveler=" + traveler + "]";
	} 
	
}
