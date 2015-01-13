/**
 * 
 */
package com.epam.training.persistence.oracledao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import com.epam.training.persistence.UserDAO;
import com.epam.training.persistence.pojo.BidsTO;
import com.epam.training.persistence.pojo.BlackListTO;
import com.epam.training.persistence.pojo.ItemTO;
import com.epam.training.persistence.pojo.UserTO;

/**
 * @author Oleg_Burshinov
 * 
 */
public class OracleUserDAO implements UserDAO {

	private OracleDAOFactory factory;

	/**
	 * @param factory
	 *            Oracle DAO Factory
	 */
	public OracleUserDAO(OracleDAOFactory factory) {
		this.factory = factory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.epam.training.persistence.UserDAO#InsertUser(com.epam.training.
	 * persistence.UserTO)
	 */
	public int InsertUser(UserTO to) {
		Connection conn = factory.createConnection();
		CallableStatement cstmt = null;

		try {
			cstmt = conn.prepareCall("{? = call get_hash_val(?)}");
			cstmt.registerOutParameter(1, Types.VARCHAR);
			cstmt.setString(2, to.getPassword());
			cstmt.executeQuery();
			to.setPassword(cstmt.getString(1));

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				if(cstmt != null){
					cstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		int userId = -1; // if errors return -1
		String sql = "Insert into USERS (FULL_NAME,BILLING_ADDRESS,LOGIN,PASSWORD,EMAIL) values (?,?,?,?,?)";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql, new String[] { "USER_ID" });
			pstmt.setString(1, to.getFullName());
			pstmt.setString(2, to.getBillingAddress());
			pstmt.setString(3, to.getLogin());
			pstmt.setString(4, to.getPassword());
			pstmt.setString(5, to.getEmail());
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				userId = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return userId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.epam.training.persistence.UserDAO#UserItems(com.epam.training.persistence
	 * .UserTO)
	 */
	public ItemTO[] UserItems(UserTO to) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.epam.training.persistence.UserDAO#addBid(com.epam.training.persistence
	 * .UserTO)
	 */
	public BidsTO addBid(UserTO to) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.epam.training.persistence.UserDAO#addItem(com.epam.training.persistence
	 * .UserTO)
	 */
	public ItemTO addItem(UserTO to) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.epam.training.persistence.UserDAO#authorizeUser(com.epam.training
	 * .persistence.UserTO)
	 */
	public boolean authorizeUser(UserTO to) {
		Connection conn = factory.createConnection();
		CallableStatement cstmt = null;
		boolean authorized = false;
		try {
			cstmt = conn.prepareCall("{? = call get_hash_val(?)}");
			cstmt.registerOutParameter(1, Types.VARCHAR);
			cstmt.setString(2, to.getPassword());
			cstmt.executeQuery();
			to.setPassword(cstmt.getString(1));

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				if(cstmt != null){
					cstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM USERS WHERE LOGIN LIKE ? AND upper(PASSWORD) LIKE ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + to.getLogin() + "%");
			pstmt.setString(2, "%" + to.getPassword() + "%");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				authorized = true;
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return authorized;
	}

	public Integer getUserIdByLogin(String login) {
		Integer userId = -1;

		Connection conn = factory.createConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT USER_ID FROM USERS WHERE LOGIN = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, login);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				userId = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return userId;
	}

	public UserTO getUserInfoById(Integer userId) {
		UserTO to = null;
		Connection conn = factory.createConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM USERS WHERE user_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId.toString());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				to = RsToUserTo.returnTOFromRs(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return to;
	}

	public boolean addUserToBlackList(BlackListTO to) {
		Connection conn = factory.createConnection();

		boolean result = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int affected = 0;
		String sql = "SELECT * FROM BLACKLIST WHERE user_id = ? AND black_user_id = ?";
		try {
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, to.getUserId());
			pstmt.setInt(2, to.getBlackUserId());
			affected = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		if (affected > 0) {
			try {
				conn.setAutoCommit(true);
				conn.close();
				return false;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Statement stmt = null;
		sql = "DELETE FROM (SELECT * FROM BIDS WHERE item_id IN (SELECT item_id FROM items WHERE  seller_id = "
				+ to.getUserId()
				+ ") AND bidder_id = "
				+ to.getBlackUserId()
				+ ")";
//		boolean isDelete = false;
		try {
			stmt = conn.createStatement();
			stmt.execute(sql);
//			isDelete = stmt.execute(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		sql = "Insert into BLACKLIST (USER_ID,BLACK_USER_ID) values (?,?)";
		try {

			pstmt = conn.prepareStatement(sql, new String[] { "BLACKLIST_ID" });
			pstmt.setInt(1, to.getUserId());
			pstmt.setInt(2, to.getBlackUserId());
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.commit();
				conn.setAutoCommit(true);
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	public boolean removeUserFromBlackList(BlackListTO to) {
		Connection conn = factory.createConnection();
		String sql = "DELETE FROM BLACKLIST WHERE USER_ID = ? AND BLACK_USER_ID = ?";
		PreparedStatement pstmt = null;
		int affected = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, to.getUserId());
			pstmt.setInt(2, to.getBlackUserId());
			affected = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		boolean retval = false;
		if (affected > 0) {
			retval = true;
		}
		return retval;

	}

}
