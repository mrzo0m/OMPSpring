/**
 * 
 */
package com.epam.training.persistence.oracledao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.epam.training.persistence.BidsDAO;
import com.epam.training.persistence.pojo.BidsTO;

/**
 * @author Oleg_Burshinov
 * 
 */
public class OracleBidsDAO implements BidsDAO {

	private OracleDAOFactory factory;
	private final static double EPSILON = 0.00001;

	/**
	 * @param factory
	 *            Oracle DAO Factory
	 */
	public OracleBidsDAO(OracleDAOFactory factory) {
		this.factory = factory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.epam.training.persistence.BidsDAO#addBid(com.epam.training.persistence
	 * .BidsTO)
	 */
	public boolean addBid(BidsTO to) {
		Connection conn = factory.createConnection();
		PreparedStatement pstmt = null;
		int affected = 0;
		double bidInc = 0;
		double startPrice = 0;
		double bestOffer = 0;
		int ownerId = 0;
		String sql = "SELECT bid_increment, seller_id, start_price FROM ITEMS WHERE item_id = ?";
		try {
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, to.getItemId());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				bidInc = rs.getDouble("bid_increment");
				ownerId = rs.getInt("seller_id");
				startPrice = rs.getDouble("start_price");
			}
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally{
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}					
			}
		}
		sql = "SELECT * FROM BLACKLIST WHERE user_id = ? AND black_user_id = ?";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ownerId);
			pstmt.setInt(2, to.getBidderId());
			affected = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}					
			}
		}
		if (affected > 0){
			try {
				conn.setAutoCommit(true);
				conn.close();
				return false;
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
		}
	
		sql = "SELECT MAX(BID)AS BESTOFFER FROM BIDS WHERE item_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, to.getItemId());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				bestOffer = rs.getDouble("BESTOFFER");
			}
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally{
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}					
			}
		}
		double goodbid =  startPrice + bidInc;
		if(bestOffer > 0){
			goodbid =  bestOffer + bidInc;
		}
		if(bidInc == 0 && bestOffer > 0){
			try {
				conn.setAutoCommit(true);
				conn.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			return false; // CAN BUY ONLY ONCE
		}
		if((Math.abs(goodbid - to.getBid()) < EPSILON) || (to.getBid() > goodbid)){ // bid valid
		 sql = "INSERT INTO BIDS (BIDDER_ID, ITEM_ID, BID) VALUES (?,?,?)";
			
			try {
				pstmt = conn.prepareStatement(sql, new String[] { "ITEM_ID" });
				pstmt.setInt(1, to.getBidderId());
				pstmt.setInt(2, to.getItemId());
				pstmt.setDouble(3, to.getBid());
				affected = pstmt.executeUpdate();
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					to.setBidId(rs.getInt(1));
				}
				rs.close();
				conn.commit();
			} catch (SQLException e) {
				affected = 0;
				if(e.getErrorCode() == 20001){
					System.out.println("Error: Bids to low");				
				}
			} finally {
				try {
					conn.setAutoCommit(true);
					if(pstmt != null){
						pstmt.close();					
					}
				} catch (SQLException e) {
					e.printStackTrace();
					
				}
			}
		} else {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		boolean retval = false;
		if(affected > 0){
			retval = true;
		}
		return retval;
	}
}
