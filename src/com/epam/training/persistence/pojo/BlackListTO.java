/**
 * 
 */
package com.epam.training.persistence.pojo;

import java.io.Serializable;

/**
 * @author mr.zoom
 * 
 */
public class BlackListTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2054148246431561437L;
	private Integer userId;
	private Integer blackUserId;
	
	/**
	 * @param userId
	 * @param blackUserId
	 */
	public BlackListTO(Integer userId, Integer blackUserId) {
		super();
		this.userId = userId;
		this.blackUserId = blackUserId;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the blackUserId
	 */
	public Integer getBlackUserId() {
		return blackUserId;
	}

	/**
	 * @param blackUserId
	 *            the blackUserId to set
	 */
	public void setBlackUserId(Integer blackUserId) {
		this.blackUserId = blackUserId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((blackUserId == null) ? 0 : blackUserId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		if (!(obj instanceof BlackListTO)) {
			return false;
		}
		BlackListTO other = (BlackListTO) obj;
		if (blackUserId == null) {
			if (other.blackUserId != null) {
				return false;
			}
		} else if (!blackUserId.equals(other.blackUserId)) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BlackListTO ["
				+ (userId != null ? "userId=" + userId + ", " : "")
				+ (blackUserId != null ? "blackUserId=" + blackUserId : "")
				+ "]";
	}

}
