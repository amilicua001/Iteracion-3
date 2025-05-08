package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Complain implements Serializable{
	@Id
	@XmlID
	private String id;
	private User from;
	private User to;
	private String text;
	
	
	public Complain() {
		super();
	}
	
	public Complain(String id, User from, User to, String text) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.text = text;
	}




	public String getId() {
		return id;
	}




	public void setId(String id) {
		this.id = id;
	}




	public User getFrom() {
		return from;
	}




	public void setFrom(User from) {
		this.from = from;
	}




	public User getTo() {
		return to;
	}




	public void setTo(User to) {
		this.to = to;
	}




	public String getText() {
		return text;
	}




	public void setText(String text) {
		this.text = text;
	}




	@Override
	public String toString() {
		return  from + "=>" + to + " : \n" + text +"\n";
	}
	



	
}