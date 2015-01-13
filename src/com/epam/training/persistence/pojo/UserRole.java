package com.epam.training.persistence.pojo;

/**
 * @author Oleg_Burshinov Session scope object for user information and
 *         authorization status
 */
public class UserRole {
	private UserTO user;
	private boolean authorized = false;

	/**
	 * @return the user
	 */
	public UserTO getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(UserTO user) {
		this.user = user;
	}

	/**
	 * @return the authorized
	 */
	public boolean isAuthorized() {
		return authorized;
	}

	/**
	 * @param authorized
	 *            the authorized to set
	 */
	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}
}
