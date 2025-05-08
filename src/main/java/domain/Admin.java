package domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Admin {
	@XmlID
	@Id 
	private String email;
	private String name; 
	private String phone;
	private String password;
	
	public Admin() {
		super();
	}
	
	public Admin(String email, String name, String phone, String password) {
		super();
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.password = password;
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
	@Override
	public String toString() {
		return "Admin [email=" + email + ", name=" + name + ", phone=" + phone + ", password=" + password + "]";
	}
	
	
	
}
