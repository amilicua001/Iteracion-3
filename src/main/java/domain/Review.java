package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Review implements Serializable{
	@Id
	@XmlID
	private String id;
	private Driver d;
	private Traveler t;
	private int puntuazioa;
	private String text;
	
	
	public Review() {
		super();
	}
	
	public Review(String id, Driver d, Traveler t, int puntuazioa, String text) {
		super();
		this.id = id;
		this.d = d;
		this.t = t;
		this.puntuazioa = puntuazioa;
		this.text = text;
	}
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Driver getD() {
		return d;
	}
	public void setD(Driver d) {
		this.d = d;
	}
	public Traveler getT() {
		return t;
	}
	public void setT(Traveler t) {
		this.t = t;
	}
	public int getPuntuazioa() {
		return puntuazioa;
	}
	public void setPuntuazioa(int puntuazioa) {
		this.puntuazioa = puntuazioa;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}



	@Override
	public String toString() {
		return puntuazioa + " | " + text;
	}
	
	
}