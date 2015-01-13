package com.epam.training.persistence.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Oleg_Burshinov
 * Transfer object for advanced search 
 */
public class AdvancedSearch  implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer itemUID;
	private String titleOfItem;
	private String description;
	private double minPrice;
	private double maxPrice;
	private boolean buyItNow; 
	private Date startDate;
	private Date expireDate;
	private Integer biddersCount;
	
	
	/**
	 * @return the itemUID
	 */
	public Integer getItemUID() {
		return itemUID;
	}


	/**
	 * @param itemUID the itemUID to set
	 */
	public void setItemUID(Integer itemUID) {
		this.itemUID = itemUID;
	}


	/**
	 * @return the titleOfItem
	 */
	public String getTitleOfItem() {
		return titleOfItem;
	}


	/**
	 * @param titleOfItem the titleOfItem to set
	 */
	public void setTitleOfItem(String titleOfItem) {
		this.titleOfItem = titleOfItem;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the minPrice
	 */
	public double getMinPrice() {
		return minPrice;
	}


	/**
	 * @param minPrice the minPrice to set
	 */
	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}


	/**
	 * @return the maxPrice
	 */
	public double getMaxPrice() {
		return maxPrice;
	}


	/**
	 * @param maxPrice the maxPrice to set
	 */
	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}


	/**
	 * @return the buyItNow
	 */
	public boolean isBuyItNow() {
		return buyItNow;
	}


	/**
	 * @param buyItNow the buyItNow to set
	 */
	public void setBuyItNow(boolean buyItNow) {
		this.buyItNow = buyItNow;
	}




	/**
	 * @return the biddersCount
	 */
	public Integer getBiddersCount() {
		return biddersCount;
	}


	/**
	 * @param biddersCount the biddersCount to set
	 */
	public void setBiddersCount(Integer biddersCount) {
		this.biddersCount = biddersCount;
	}


	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}


	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	/**
	 * @return the expireDate
	 */
	public Date getExpireDate() {
		return expireDate;
	}


	/**
	 * @param expireDate the expireDate to set
	 */
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AdvancedSearch ["
				+ (itemUID != null ? "itemUID=" + itemUID + ", " : "")
				+ (titleOfItem != null ? "titleOfItem=" + titleOfItem + ", "
						: "")
				+ (description != null ? "description=" + description + ", "
						: "") + "minPrice=" + minPrice + ", maxPrice="
				+ maxPrice + ", buyItNow=" + buyItNow + ", "
				+ (startDate != null ? "startDate=" + startDate + ", " : "")
				+ (expireDate != null ? "expireDate=" + expireDate + ", " : "")
				+ (biddersCount != null ? "biddersCount=" + biddersCount : "")
				+ "]";
	}



	
	
	
	
}
