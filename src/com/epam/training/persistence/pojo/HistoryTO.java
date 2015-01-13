/**
 * 
 */
package com.epam.training.persistence.pojo;

import java.io.Serializable;

/**
 * @author mr.zoom
 *
 */
public class HistoryTO implements Serializable {
	private String login;
	private String name;
	private double bid;
	private Integer userId;
	/**
	 * 
	 */
	private static final long serialVersionUID = 6382740267489164937L;

	/**
	 * @param login
	 * @param name
	 * @param bid
	 * @param userId
	 */
	public HistoryTO(String login, String name, double bid, Integer userId) {
		this.login = login;
		this.name = name;
		this.bid = bid;
		this.userId = userId;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the bid
	 */
	public double getBid() {
		return bid;
	}

	/**
	 * @param bid the bid to set
	 */
	public void setBid(double bid) {
		this.bid = bid;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(bid);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof HistoryTO)) {
			return false;
		}
		HistoryTO other = (HistoryTO) obj;
		if (Double.doubleToLongBits(bid) != Double.doubleToLongBits(other.bid)) {
			return false;
		}
		if (login == null) {
			if (other.login != null) {
				return false;
			}
		} else if (!login.equals(other.login)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (userId == null) {
			if (other.userId != null) {
				return false;
			}
		} else if (!userId.equals(other.userId)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HistoryTO [" + (login != null ? "login=" + login + ", " : "")
				+ (name != null ? "name=" + name + ", " : "") + "bid=" + bid
				+ ", " + (userId != null ? "userId=" + userId : "") + "]";
	}
	
	
	
	
	

}
