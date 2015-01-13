package com.epam.training.persistence.oracledao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import com.epam.training.persistence.pojo.FullInfoTO;

class RsToFullInfoTO {
	public static FullInfoTO returnTOFromRs(ResultSet rs) throws SQLException{
		FullInfoTO to = null;
		Integer uid = Integer.valueOf(rs.getInt("UID"));
		String title = rs.getString("TITLE");
		String description = rs.getString("DESCRIPTION");
		String seller =  rs.getString("SELLER");
		double startPrice = rs.getDouble("START_PRICE");
		Integer bidIncrement = Integer.valueOf(rs.getInt("BID_INCREMENT"));
		double bestOffer =  rs.getDouble("BEST_OFFER");
		String bidder = rs.getString("BIDDER");
		Calendar stopDate = null;
		if(rs.getTimestamp("STOP_DATE") != null && !rs.wasNull()){
			Timestamp timestamp = rs.getTimestamp("STOP_DATE");
			stopDate = Calendar.getInstance();
			stopDate.setTimeInMillis(timestamp.getTime());
		}
		boolean buyItNow = false;
		int tmp = rs.getInt("BUYITNOW");
		if (tmp == 1){
			buyItNow = true;
		}
		String category = rs.getString("CATEGORY");
		to = new FullInfoTO(uid, title, description, seller, startPrice, bidIncrement, bestOffer, bidder, stopDate, buyItNow);
		to.setCategory(category);
		return to;
	}
}
