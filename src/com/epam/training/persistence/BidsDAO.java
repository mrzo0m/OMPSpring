package com.epam.training.persistence;

import com.epam.training.persistence.pojo.BidsTO;

/**
 * @author Oleg_Burshinov
 *
 */
public interface BidsDAO {
	/**
	 * @param to Transfer object for bids
	 * @return adding status: true if bid set or false
	 */
	public boolean addBid(BidsTO to);
}
