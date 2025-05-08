package domain;

import java.io.Serializable; 
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Traveler extends User implements Serializable{
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Alerta> alertList=new Vector<Alerta>();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	public Traveler() {
		super();
	}
	

	public Traveler(String email, String name, String phone, String password) {
		super(email,name,phone,password);
	}


	/**
	 * @return the alertList
	 */
	public List<Alerta> getAlertList() {
		return alertList;
	}


	/**
	 * @param alertList the alertList to set
	 */
	public void setAlertList(List<Alerta> alertList) {
		this.alertList = alertList;
	}
	
	public boolean doesAlertExists(String from, String to, Date date)  {	
		for (Alerta alerta:getAlertList())
			if ( (java.util.Objects.equals(alerta.getFrom(),from)) && (java.util.Objects.equals(alerta.getTo(),to)) && (java.util.Objects.equals(alerta.getDate(),date)) )
			 return true;
		
		return false;
	}


}
