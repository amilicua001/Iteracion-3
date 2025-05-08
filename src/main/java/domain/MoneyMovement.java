package domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class MoneyMovement implements Serializable{
	private float amount; 
	private String explain;
	@Id
	@XmlID
	private String dataString;
	private Date data;
	private User user;

	public MoneyMovement() {
		super();
	}
	
	public MoneyMovement(float amount, String explain, User user) {
		super();
		this.amount = amount;
		this.explain = explain;
		this.data = new Date(); 
		this.dataString = data.toString();
		this.user= user;
	}
	
	/**
	 * @return the data
	 */
	public Date getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Date data) {
		this.data = data;
	}

	/**
	 * @return the amount
	 */
	public float getAmount() {
		return amount;
	}
	/**
	 * @param ammount the amount to set
	 */
	public void setAmount(float amount) {
		this.amount = amount;
	}
	/**
	 * @return the explain
	 */
	public String getExplain() {
		return explain;
	}
	/**
	 * @param explain the explain to set
	 */
	public void setExplain(String explain) {
		this.explain = explain;
	}

	@Override
	public String toString() {
		return ResourceBundle.getBundle("Etiquetas").getString("MoneyMovement.Amount") + ": " + amount + "�, " + ResourceBundle.getBundle("Etiquetas").getString("MoneyMovement.Explain") + ": " + explain + ", " + ResourceBundle.getBundle("Etiquetas").getString("MoneyMovement.Data") + ": " + data;
	}

	
	
	
}
