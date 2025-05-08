package domain;

import java.util.ResourceBundle;

public class UserComplain {
	private User user;
	private int complainNumber;

	
	public UserComplain() {
		super();
	}
	
	public UserComplain(User user, int complainNumber) {
		super();
		this.user = user;
		this.complainNumber = complainNumber;
	}
	@Override
	public String toString() {
		return user +", " + ResourceBundle.getBundle("Etiquetas").getString("UserComplain.complainNumber") + " = " + complainNumber;
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the complainNumber
	 */
	public int getComplainNumber() {
		return complainNumber;
	}
	/**
	 * @param complainNumber the complainNumber to set
	 */
	public void setComplainNumber(int complainNumber) {
		this.complainNumber = complainNumber;
	}

	
}
