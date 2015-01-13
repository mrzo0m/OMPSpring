/**
 * 
 */
package com.epam.training.persistence.oracledao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import com.epam.training.persistence.pojo.ItemTO;

/**
 * @author Oleg_Burshinov
 *
 */
class RsToItemTO {
	
	public static ItemTO returnTOFromRs(ResultSet rs) throws SQLException{
		ItemTO to = null;
		Integer itemId = Integer.valueOf(rs.getInt("ITEM_ID"));
		Integer sellerId = Integer.valueOf(rs.getInt("SELLER_ID"));
		String title = rs.getString("TITLE");
		String description = rs.getString("DESCRIPTION");
		double startPrice = rs.getDouble("START_PRICE");
		Integer timeLeft = Integer.valueOf(rs.getInt("TIME_LEFT"));
		Calendar startBiddingDate = Calendar.getInstance();
		startBiddingDate.setTime(rs.getTimestamp("START_BIDDING_DATE"));
		boolean buyItNow = false;
		int tmp = rs.getInt("BUY_IT_NOW");
		if (tmp == 1){
			buyItNow = true;
		}
		Integer bidIncrement = Integer.valueOf(rs.getInt("BID_INCREMENT"));
		Integer category = Integer.valueOf(rs.getInt("CATEGORY"));
		to = new ItemTO(sellerId, title, description, startPrice, timeLeft, startBiddingDate, buyItNow, bidIncrement, itemId, category);
		return to;
	}
}
