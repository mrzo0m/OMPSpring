/**
 * 
 */
package com.epam.training.persistence.pojo;

import java.io.Serializable;

import com.epam.training.persistence.TransferObject;

/**
 * @author Oleg_Burshinov
 * 
 */
public class BidsTO implements Serializable, TransferObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5562449852329905972L;
	/**
	 * 
	 * @param bidderId
	 * @param itemId
	 * @param bid
	 */
	public BidsTO(Integer bidderId, Integer itemId, double bid) {
		super();
		this.bidderId = bidderId;
		this.itemId = itemId;
		this.bid = bid;
	}
	
	/**
	 * 
	 */
	public BidsTO() {
		super();
	}

	/**
	 *  unique id of bids
	 */
	private Integer bidId;
	/**
	 * id form users table
	 */
	private Integer bidderId;
	/**
	 * id form items table
	 */
	private Integer itemId;
	/**
	 * bid to item
	 */
	private double bid;
	/**
	 * bid best offer
	 */
	private double bestOffer;
	/**
	 * bid inc
	 */
	private Integer bidInc;
	
	private double startPrice;
	
	/**
	 * @return the bidId
	 */
	public Integer getBidId() {
		return bidId;
	}


	/**
	 * @param bidId the bidId to set
	 */
	public void setBidId(Integer bidId) {
		this.bidId = bidId;
	}


	/**
	 * @return the bidderId
	 */
	public Integer getBidderId() {
		return bidderId;
	}


	/**
	 * @param bidderId the bidderId to set
	 */
	public void setBidderId(Integer bidderId) {
		this.bidderId = bidderId;
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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(bid);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((bidderId == null) ? 0 : bidderId.hashCode());
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BidsTO))
			return false;
		BidsTO other = (BidsTO) obj;
		if (Double.doubleToLongBits(bid) != Double.doubleToLongBits(other.bid))
			return false;
		if (bidderId == null) {
			if (other.bidderId != null)
				return false;
		} else if (!bidderId.equals(other.bidderId))
			return false;
		if (itemId == null) {
			if (other.itemId != null)
				return false;
		} else if (!itemId.equals(other.itemId))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "BidsTO [bid=" + bid + ", bidderId=" + bidderId + ", itemId="
				+ itemId + "]";
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
	 * @return the bidInc
	 */
	public Integer getBidInc() {
		return bidInc;
	}

	/**
	 * @param bidInc the bidInc to set
	 */
	public void setBidInc(Integer bidInc) {
		this.bidInc = bidInc;
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


	
}
