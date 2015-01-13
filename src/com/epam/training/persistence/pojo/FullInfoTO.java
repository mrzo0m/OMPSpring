/**
 * 
 */
package com.epam.training.persistence.pojo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.epam.training.persistence.TransferObject;

/**
 * @author Oleg_Burshinov
 *
 */
public class FullInfoTO implements TransferObject {
	/**
	 * UID
	 */
	private Integer uid;
	/**
	 * Unique id
	 */
	private String title;
	/**
	 * item title 
	 */
	private String description;
	/**
	 * about item
	 */
	private String seller;
	/**
	 * Seller name
	 */
	private double startPrice;
	/**
	 * start price
	 */
	private Integer  bidIncrement;
	/**
	 * best offer to bid
	 */
	private double bestOffer;
	/**
	 * Bidder name with best offer
	 */
	private String bidder;
	/**
	 *  If ....
	 */
	private Boolean buyItNow;
	/**
	 * When bidding end: Start date + time left
	 */
	private Calendar stopDate;
	
	private String category;
	
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the uid
	 */
	public Integer getUid() {
		return uid;
	}

	/**
	 * @return the buyItNow
	 */
	public boolean isBuyItNow() {
		return buyItNow;
	}
	/**
	 * @return the buyItNow
	 */
	public Boolean getBuyItNow() {
		return buyItNow;
	}

	/**
	 * @param buyItNow the buyItNow to set
	 */
	public void setBuyItNow(Boolean buyItNow) {
		this.buyItNow = buyItNow;
	}


	/**
	 * @param buyItNow the buyItNow to set
	 */
	public void setBuyItNow(boolean buyItNow) {
		this.buyItNow = buyItNow;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(Integer uid) {
		this.uid = uid;
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
	 * @return the seller
	 */
	public String getSeller() {
		return seller;
	}
	/**
	 * @param seller the seller to set
	 */
	public void setSeller(String seller) {
		this.seller = seller;
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
	 * @return the bestOffer
	 */
	public double getBestOffer() {
		return bestOffer;
	}
	/**
	 * @param bestOffer the bestOffer to set
	 */
	public void setBestOffer(double bestOffer) {
		this.bestOffer = bestOffer;
	}
	/**
	 * @return the bidder
	 */
	public String getBidder() {
		return bidder;
	}
	/**
	 * @param bidder the bidder to set
	 */
	public void setBidder(String bidder) {
		this.bidder = bidder;
	}
	/**
	 * @return the stopDate
	 */
	public Date getStopDate() {
//		String stopDateOut = "";
//		if(stopDate != null){
//
//			String pattern = "dd-MM-yyyy hh:mm";
//		    SimpleDateFormat format = new SimpleDateFormat(pattern);
//		     
//		     System.out.println("RETURN DATE FROM TO:" + format.format(stopDate.getTime()));
//		     stopDateOut = format.format(stopDate.getTime());
//		}
//		return stopDateOut;
	     return (stopDate == null) ? null : stopDate.getTime() ;
	}
	/**
	 * @param stopDate the stopDate to set
	 */
	public void setStopDate(Calendar stopDate) {
		this.stopDate = stopDate;
	}
	/**
	 * @param uid
	 * @param title
	 * @param description
	 * @param seller
	 * @param startPrice
	 * @param bidIncrement
	 * @param bestOffer
	 * @param bidder
	 * @param stopDate
	 * @param buyItNow 
	 */
	public FullInfoTO(Integer uid,String title, String description, String seller,
			double startPrice, Integer bidIncrement, double bestOffer,
			String bidder, Calendar stopDate, boolean buyItNow) {
		super();
		this.uid = uid;
		this.title = title;
		this.description = description;
		this.seller = seller;
		this.startPrice = startPrice;
		this.bidIncrement = bidIncrement;
		this.bestOffer = bestOffer;
		this.bidder = bidder;
		this.stopDate = stopDate;
		this.buyItNow = buyItNow;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(bestOffer);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((bidIncrement == null) ? 0 : bidIncrement.hashCode());
		result = prime * result + ((bidder == null) ? 0 : bidder.hashCode());
		result = prime * result
				+ ((buyItNow == null) ? 0 : buyItNow.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((seller == null) ? 0 : seller.hashCode());
		temp = Double.doubleToLongBits(startPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((stopDate == null) ? 0 : stopDate.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
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
		if (!(obj instanceof FullInfoTO)) {
			return false;
		}
		FullInfoTO other = (FullInfoTO) obj;
		if (Double.doubleToLongBits(bestOffer) != Double
				.doubleToLongBits(other.bestOffer)) {
			return false;
		}
		if (bidIncrement == null) {
			if (other.bidIncrement != null) {
				return false;
			}
		} else if (!bidIncrement.equals(other.bidIncrement)) {
			return false;
		}
		if (bidder == null) {
			if (other.bidder != null) {
				return false;
			}
		} else if (!bidder.equals(other.bidder)) {
			return false;
		}
		if (buyItNow == null) {
			if (other.buyItNow != null) {
				return false;
			}
		} else if (!buyItNow.equals(other.buyItNow)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (seller == null) {
			if (other.seller != null) {
				return false;
			}
		} else if (!seller.equals(other.seller)) {
			return false;
		}
		if (Double.doubleToLongBits(startPrice) != Double
				.doubleToLongBits(other.startPrice)) {
			return false;
		}
		if (stopDate == null) {
			if (other.stopDate != null) {
				return false;
			}
		} else if (!stopDate.equals(other.stopDate)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		if (uid == null) {
			if (other.uid != null) {
				return false;
			}
		} else if (!uid.equals(other.uid)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		String pattern = "dd-MM-yyyy hh:mm";
	    SimpleDateFormat format = new SimpleDateFormat(pattern);
		return "FullInfoTO ["
				+ (uid != null ? "uid=" + uid + ", " : "")
				+ (title != null ? "title=" + title + ", " : "")
				+ (description != null ? "description=" + description + ", "
						: "")
				+ (seller != null ? "seller=" + seller + ", " : "")
				+ "startPrice="
				+ startPrice
				+ ", "
				+ (bidIncrement != null ? "bidIncrement=" + bidIncrement + ", "
						: "") + "bestOffer=" + bestOffer + ", "
				+ (bidder != null ? "bidder=" + bidder + ", " : "")
				+ (buyItNow != null ? "buyItNow=" + buyItNow + ", " : "")
				+ (stopDate != null ? "stopDate" + format.format(stopDate.getTime()) : "") + "]";
	}
	

}
