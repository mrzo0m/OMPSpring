/**
 * 
 */
package com.epam.training.persistence.pojo;

import java.io.Serializable;

/**
 * @author Oleg_Burshinov
 *
 */
public class CategoryTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8455401110895533855L;
	private String categoryTitle;
	private int level;
	private int categoryId;
	/**
	 * @return the categoryTitle
	 */
	public String getCategoryTitle() {
		return categoryTitle;
	}
	/**
	 * @param categoryTitle the categoryTitle to set
	 */
	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}
	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	/**
	 * @return the categoryId
	 */
	public int getCategoryId() {
		return categoryId;
	}
	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + categoryId;
		result = prime * result
				+ ((categoryTitle == null) ? 0 : categoryTitle.hashCode());
		result = prime * result + level;
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
		if (!(obj instanceof CategoryTO)) {
			return false;
		}
		CategoryTO other = (CategoryTO) obj;
		if (categoryId != other.categoryId) {
			return false;
		}
		if (categoryTitle == null) {
			if (other.categoryTitle != null) {
				return false;
			}
		} else if (!categoryTitle.equals(other.categoryTitle)) {
			return false;
		}
		if (level != other.level) {
			return false;
		}
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CategoryTO ["
				+ (categoryTitle != null ? "categoryTitle=" + categoryTitle
						+ ", " : "") + "level=" + level + ", categoryId="
				+ categoryId + "]";
	}
	/**
	 * @param categoryTitle
	 * @param level
	 * @param categoryId
	 */
	public CategoryTO(String categoryTitle, int level, int categoryId) {
		super();
		this.categoryTitle = categoryTitle;
		this.level = level;
		this.categoryId = categoryId;
	}
	
}
