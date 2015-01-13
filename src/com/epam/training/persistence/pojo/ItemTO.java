/**
 * 
 */
package com.epam.training.persistence.pojo;

import java.io.Serializable;
import java.util.Calendar;

import com.epam.training.persistence.TransferObject;

/**
 * @author Oleg_Burshinov
 *
 */
public class ItemTO implements Serializable, TransferObject  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2370391546618485481L;

	
	
	/**
	 * 
	 */
	public ItemTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param sellerId
	 * @param title
	 * @param description
	 * @param startPrice
	 * @param timeLeft
	 * @param startBiddingDate
	 * @param buyItNow
	 * @param bidIncrement
	 * @param itemId
	 * @param category
	 */
	public ItemTO(Integer sellerId, String title, String description,
			double startPrice, Integer timeLeft, Calendar startBiddingDate,
			boolean buyItNow, Integer bidIncrement, Integer itemId,
			Integer category) {
		super();
		this.sellerId = sellerId;
		this.title = title;
		this.description = description;
		this.startPrice = startPrice;
		this.timeLeft = timeLeft;
		this.startBiddingDate = startBiddingDate;
		this.buyItNow = buyItNow;
		this.bidIncrement = bidIncrement;
		this.itemId = itemId;
		this.category = category;
	}
	/**
	 * Unique id seller
	 */
	private Integer sellerId;
	/**
	 * Title
	 */
	private String title;
	/**
	 * About item
	 */
	private String description;
	/**
	 * Start price
	 */
	private double startPrice;
	/**
	 * How many time to by this item
	 */
	private Integer timeLeft;
	/**
	 *  Start date
	 */
	private Calendar    startBiddingDate;
	/**
	 *  If ....
	 */
	private boolean buyItNow;
	/**
	 * Step to bidding
	 */
	private Integer bidIncrement;
	/**
	 * Unique id item
	 */
	private Integer itemId;
	
	private Integer category;
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bidIncrement == null) ? 0 : bidIncrement.hashCode());
		result = prime * result + (buyItNow ? 1231 : 1237);
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
		result = prime * result
				+ ((sellerId == null) ? 0 : sellerId.hashCode());
		result = prime
				* result
				+ ((startBiddingDate == null) ? 0 : startBiddingDate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(startPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((timeLeft == null) ? 0 : timeLeft.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		if (!(obj instanceof ItemTO)) {
			return false;
		}
		ItemTO other = (ItemTO) obj;
		if (bidIncrement == null) {
			if (other.bidIncrement != null) {
				return false;
			}
		} else if (!bidIncrement.equals(other.bidIncrement)) {
			return false;
		}
		if (buyItNow != other.buyItNow) {
			return false;
		}
		if (category == null) {
			if (other.category != null) {
				return false;
			}
		} else if (!category.equals(other.category)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (itemId == null) {
			if (other.itemId != null) {
				return false;
			}
		} else if (!itemId.equals(other.itemId)) {
			return false;
		}
		if (sellerId == null) {
			if (other.sellerId != null) {
				return false;
			}
		} else if (!sellerId.equals(other.sellerId)) {
			return false;
		}
		if (startBiddingDate == null) {
			if (other.startBiddingDate != null) {
				return false;
			}
		} else if (!startBiddingDate.equals(other.startBiddingDate)) {
			return false;
		}
		if (Double.doubleToLongBits(startPrice) != Double
				.doubleToLongBits(other.startPrice)) {
			return false;
		}
		if (timeLeft == null) {
			if (other.timeLeft != null) {
				return false;
			}
		} else if (!timeLeft.equals(other.timeLeft)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		return true;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ItemTO ["
				+ (sellerId != null ? "sellerId=" + sellerId + ", " : "")
				+ (title != null ? "title=" + title + ", " : "")
				+ (description != null ? "description=" + description + ", "
						: "")
				+ "startPrice="
				+ startPrice
				+ ", "
				+ (timeLeft != null ? "timeLeft=" + timeLeft + ", " : "")
				+ (startBiddingDate != null ? "startBiddingDate="
						+ startBiddingDate + ", " : "")
				+ "buyItNow="
				+ buyItNow
				+ ", "
				+ (bidIncrement != null ? "bidIncrement=" + bidIncrement + ", "
						: "")
				+ (itemId != null ? "itemId=" + itemId + ", " : "")
				+ (category != null ? "category=" + category : "") + "]";
	}
	
	/**
	 * @return the category
	 */
	public Integer getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(Integer category) {
		this.category = category;
	}
	/**
	 * @return the sellerId
	 */
	public Integer getSellerId() {
		return sellerId;
	}
	/**
	 * @param sellerId the sellerId to set
	 */
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * @return the startPrice
	 */
	public double getStartPrice() {
		return startPrice;
	}
	/**
	 * @param startPrice the startPrice to set
	 */
	public void setStartPrice(double startPrice) {
		this.startPrice = startPrice;
	}
	/**
	 * @return the timeLeft
	 */
	public Integer getTimeLeft() {
		return timeLeft;
	}
	/**
	 * @param timeLeft the timeLeft to set
	 */
	public void setTimeLeft(Integer timeLeft) {
		this.timeLeft = timeLeft;
	}
	/**
	 * @return the startBiddingDate
	 */
	public Calendar getStartBiddingDate() {
		return startBiddingDate;
	}
	/**
	 * @param startBiddingDate the startBiddingDate to set
	 */
	public void setStartBiddingDate(Calendar startBiddingDate) {
		this.startBiddingDate = startBiddingDate;
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
	 * @return the bidIncrement
	 */
	public Integer getBidIncrement() {
		return bidIncrement;
	}
	/**
	 * @param bidIncrement the bidIncrement to set
	 */
	public void setBidIncrement(Integer bidIncrement) {
		this.bidIncrement = bidIncrement;
	}
	/**
	 * @return the itemId
	 */
	public Integer getItemId() {
		return itemId;
	}
	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	
}
