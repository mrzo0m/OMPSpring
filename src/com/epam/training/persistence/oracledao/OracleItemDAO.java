/**
 * 
 */
package com.epam.training.persistence.oracledao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.sql.rowset.JdbcRowSet;

import com.epam.training.persistence.ItemDAO;
import com.epam.training.persistence.pojo.CategoryTO;
import com.epam.training.persistence.pojo.FullInfoTO;
import com.epam.training.persistence.pojo.HistoryTO;
import com.epam.training.persistence.pojo.ItemTO;
import com.sun.rowset.JdbcRowSetImpl;

/**
 * @author Oleg_Burshinov
 * 
 */
public class OracleItemDAO implements ItemDAO {

	private OracleDAOFactory factory;
	private static final int CATEGORY_ROOT = 0;
	private static final String OREDER_DIRECTION = " ORDER BY %s %s NULLS LAST";
	private static final String PRICE_FILTER = " %s %s ? ";
	private static final String DATE_VALUE = "(to_char(%s, 'YYYY-MM-DD HH24:MI') = to_char(?, 'YYYY-MM-DD HH24:MI'))";

	/**
	 * @param factory
	 *            Oracle DAO Factory
	 */
	public OracleItemDAO(OracleDAOFactory factory) {
		this.factory = factory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.epam.training.persistence.ItemDAO#allItems()
	 */
	public Set<ItemTO> allItems(int pageNumber, int itemsCountOnPage) {
		Set<ItemTO> allItems = new HashSet<ItemTO>();
		Connection conn = factory.createConnection();
		String sql = "SELECT * FROM ITEMS";

		sql = Paging.getPage(pageNumber, itemsCountOnPage, sql);
		JdbcRowSet jdbcRs = null;
		try {
			jdbcRs = new JdbcRowSetImpl(conn);
			jdbcRs.setCommand(sql);
			jdbcRs.execute();
			while (jdbcRs.next()) {
				allItems.add(RsToItemTO.returnTOFromRs(jdbcRs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (jdbcRs != null) {
				try {
					jdbcRs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return allItems;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.epam.training.persistence.ItemDAO#deleteItem(com.epam.training.
	 * persistence.ItemTO)
	 */
	public boolean deleteItem(Integer id) {
		Connection conn = factory.createConnection();
		String sql = "DELETE FROM ITEMS WHERE ITEM_ID = ?";
		PreparedStatement pstmt = null;
		int affected = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.epam.training.persistence.ItemDAO#insertItem(com.epam.training.
	 * persistence.ItemTO)
	 */
	public int insertItem(ItemTO to) {
		Connection conn = factory.createConnection();
		int itemId = -1; // if errors return -1
//		String sql = "Insert into ITEMS (SELLER_ID,TITLE,DESCRIPTION,START_PRICE,TIME_LEFT,START_BIDDING_DATE,BUY_IT_NOW,BID_INCREMENT,CATEGORY) values (?,?,?,?,?,?,?,?,?)";
		String sql = "Insert into ITEMS (SELLER_ID,TITLE,DESCRIPTION,START_PRICE,TIME_LEFT,START_BIDDING_DATE,BUY_IT_NOW,BID_INCREMENT) values (?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql, new String[] { "ITEM_ID" });
			pstmt.setInt(1, to.getSellerId());
			pstmt.setString(2, to.getTitle());
			pstmt.setString(3, to.getDescription());
			pstmt.setDouble(4, to.getStartPrice());
			if(to.getTimeLeft() != null){
				pstmt.setInt(5, to.getTimeLeft());
				
			} else {
				pstmt.setNull(5, java.sql.Types.NULL);
			}
			pstmt.setNull(6, java.sql.Types.NULL);
			if (to.isBuyItNow()) {
				pstmt.setInt(7, 1);
			} else {
				pstmt.setInt(7, 0);
			}
			if(to.getBidIncrement() != null){
				pstmt.setInt(8, to.getBidIncrement());
				
			} else {
				pstmt.setNull(8, java.sql.Types.NULL);
			}
//			pstmt.setInt(9, to.getCategory());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				itemId = rs.getInt(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
		return itemId;
	}

	public List<FullInfoTO> getFullInfoItems(int pageNumber,
			int itemsCountOnPage, String[] columnForOrderSpaceSortDirection,
			String filter, String filterColumn, Integer category) {
		LinkedList<FullInfoTO> findedItems = new LinkedList<FullInfoTO>();
		ItemOrders ord = null;
		String column = null;
		String order = null;
		boolean isFilter = false;
		boolean isOrder = false;
		StringBuilder sb = new StringBuilder();
		if (category == null || category == 0) {
			sb.append("SELECT * FROM fullinfoitems ");
		} else {
			sb.append("SELECT * FROM("
					+ "SELECT * FROM fullinfoitems finfo WHERE finfo.category_id IN("
					+ "SELECT  cat_id FROM OFFERCATEGORY CONNECT BY PRIOR cat_id = parentid"
					+ " START WITH parentid = " + category
					+ ") UNION (SELECT * FROM fullinfoitems finfo"
					+ " WHERE finfo.category_id = " + category + "))");
		}
		String tmpsql = new String(sb.toString());
		boolean badFilter = false;
		if ((filter != null) || (filterColumn != null)) {
			sb.append(" WHERE ");
			isFilter = true;
			try {
				ord = ItemOrders.valueOf(filterColumn.toUpperCase());
				column = ord.toString();
				sb.append(" " + column + " ");
			} catch (IllegalArgumentException e) {
				column = null;
				badFilter = true;
				// needed delete where
			}
			sb.append(" LIKE ?");
		}
		if (badFilter) {
			sb = new StringBuilder(tmpsql);
		}
		boolean badOrder = false;
		if (columnForOrderSpaceSortDirection != null) {
			if (columnForOrderSpaceSortDirection.length != 0) {
				// orderParams = new ArrayList<String>();
				isOrder = true;
				for (int i = 0; i < columnForOrderSpaceSortDirection.length; i++) {
					StringTokenizer st = new StringTokenizer(
							columnForOrderSpaceSortDirection[i]);
					while (st.hasMoreTokens()) {
						try {
							ord = ItemOrders.valueOf(st.nextToken()
									.toUpperCase());
							column = ord.toString();
							// sb.append(" ? ");
							// orderParams.add(column);
						} catch (IllegalArgumentException e) {
							column = null;
							badOrder = true;
						}
						if (st.hasMoreTokens()) {
							if (st.nextToken().toUpperCase().equals("DESC")) {
								order = "DESC";

							} else {
								order = "ASC";
							}
						}
						if (column != null) {
							if ((i + 1) < columnForOrderSpaceSortDirection.length) {
								sb.append(",");
							}
						}
					}

				}
				if (badOrder) {
					sb = new StringBuilder(tmpsql);
				}
			}

		}
		Connection conn = factory.createConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		if (isOrder) {
			sb.append(String.format(OREDER_DIRECTION, column, order));
		}
		String sql = sb.toString();
		sql = Paging.getPage(pageNumber, itemsCountOnPage, sql);

		try {
			pstmt = conn.prepareStatement(sql);
			if (isFilter) {
				pstmt.setString(1, "%" + filter + "%");
			}
			if (isOrder) {

				// int count = 0;
				// if (isFilter) {
				// count = 2;
				// for (String ords : orderParams) {
				// pstmt.setString(count, ords);
				// count++;
				// }
				// } else {
				// count = 1;
				// for (String ords : orderParams) {
				// pstmt.setString(count, ords);
				// count++;
				// }
				// }

			}
			rs = pstmt.executeQuery();
			while (rs.next()) {

				findedItems.add(RsToFullInfoTO.returnTOFromRs(rs));
			}
			rs.close();
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
		return findedItems;
	}

	public Set<ItemTO> findItemsByDescription(String description) {
		Set<ItemTO> findedItems = new HashSet<ItemTO>();
		Connection conn = factory.createConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM ITEMS WHERE DESCRIPTION LIKE ?";
		try {
			sql = Paging.getPage(0, 20, sql); // Paging NOT USED YET
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + description + "%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				findedItems.add(RsToItemTO.returnTOFromRs(rs));
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
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return findedItems;
	}

	public ItemTO findItemById(Integer id) {
		ItemTO to = null;
		Connection conn = factory.createConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM ITEMS WHERE ITEM_ID = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				to = RsToItemTO.returnTOFromRs(rs);
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
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return to;
	}

	public Set<ItemTO> findItemsByTitle(String title) {
		Set<ItemTO> findedItems = new HashSet<ItemTO>();
		Connection conn = factory.createConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM ITEMS WHERE TITLE LIKE ?";
		try {
			sql = Paging.getPage(0, 20, sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + title + "%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				findedItems.add(RsToItemTO.returnTOFromRs(rs));
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
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return findedItems;
	}

	public List<FullInfoTO> getFullInfoMyItems(int pageNumber,
			int itemsCountOnPage, Integer userId) {
		List<FullInfoTO> findedItems = new LinkedList<FullInfoTO>();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM fullinfoitems "
				+ "WHERE \"UID\" IN (SELECT distinct b.item_id FROM items itm "
				+ "LEFT JOIN BIDS b ON itm.item_id = b.item_id "
				+ "WHERE b.bidder_id  = ?) "
				+ "OR SELLER = (SELECT u.login FROM USERS u"
				+ " WHERE u.user_id = ?)");

		Connection conn = factory.createConnection();
		PreparedStatement pstmt = null;
		String sql = sb.toString();
		sql = Paging.getPage(pageNumber, itemsCountOnPage, sql);
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId.toString());
			pstmt.setString(2, userId.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				findedItems.add(RsToFullInfoTO.returnTOFromRs(rs));
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return findedItems;
	}

	public Map<Integer, Integer> getSoldItems() {
		Map<Integer, Integer> sold = new HashMap<Integer, Integer>();
		String sql = "SELECT * FROM SOLDITEMS";
		Connection conn = factory.createConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Integer key = (rs.getInt(1));
				Integer value = (rs.getInt(2));
				sold.put(key, value);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
					stmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return sold;
	}

	public boolean isInformationMessageSend(Integer itemId) {
		boolean result = false;

		Connection conn = factory.createConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM donemsg WHERE ITEM_ID = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, itemId.toString());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = true;
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
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;

	}

	public boolean markAsSendedMessage(Integer itemId) {
		boolean result = false;
		Connection conn = factory.createConnection();
		String sql = "Insert into DONEMSG (ITEM_ID) values (?)";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql, new String[] { "MSG_ID" });
			pstmt.setInt(1, itemId);
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				result = true;
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
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public List<HistoryTO> getHistory(Integer itemId) {
		List<HistoryTO> history = new LinkedList<HistoryTO>();
		Connection conn = factory.createConnection();
		String sql = "SELECT * FROM ITEMHISTORY WHERE item_id = ? ORDER BY bid";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, itemId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				history.add(RsToHistoryTO.returnTOFromRs(rs));
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
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return history;
	}

	public List<CategoryTO> getCategory() {
		List<CategoryTO> category = new LinkedList<CategoryTO>();
		Connection conn = factory.createConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT  cat_id, title, LEVEL FROM OFFERCATEGORY CONNECT BY PRIOR cat_id = parentid START WITH parentid = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, CATEGORY_ROOT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				category.add(RsToCategoryTO.returnTOFromRs(rs));
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
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return category;
	}

	public boolean updateItem(Integer editItemId, ItemTO to) {
		boolean result = false;
		Connection conn = factory.createConnection();
//		String sql = "UPDATE ITEMS SET TITLE = ?, DESCRIPTION = ?, START_PRICE = ?, TIME_LEFT = ?, BUY_IT_NOW = ?, BID_INCREMENT = ?, CATEGORY = ? WHERE item_id = ?";
		String sql = "UPDATE ITEMS SET TITLE = ?, DESCRIPTION = ?, START_PRICE = ?, TIME_LEFT = ?, BUY_IT_NOW = ?, BID_INCREMENT = ? WHERE item_id = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, to.getTitle());
			pstmt.setString(2, to.getDescription());
			pstmt.setDouble(3, to.getStartPrice());
			pstmt.setInt(4, to.getTimeLeft());
			if (to.isBuyItNow()) {
				pstmt.setInt(5, 1);
			} else {
				pstmt.setInt(5, 0);
			}
			pstmt.setInt(6, to.getBidIncrement());
//			pstmt.setInt(7, to.getCategory());
			pstmt.setInt(7, editItemId);//8
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = true;
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
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public List<FullInfoTO> getAdvancedInfoItems(int pageNumber,
			int itemsCountOnPage, String[] columnForOrderSpaceSortDirection,
			Map<String, Object> filterMap, Integer category) {
		LinkedList<FullInfoTO> findedItems = new LinkedList<FullInfoTO>();
		ItemOrders ord = null;
		String column = null;
		String order = null;
		boolean isFilter = false;
		boolean isOrder = false;
		StringBuilder sb = new StringBuilder();
		if (category == null || category == 0) {
			sb.append("SELECT * FROM fullinfoitems ");
		} else {
			sb.append("SELECT * FROM("
					+ "SELECT * FROM fullinfoitems finfo WHERE finfo.category_id IN("
					+ "SELECT  cat_id FROM OFFERCATEGORY CONNECT BY PRIOR cat_id = parentid"
					+ " START WITH parentid = " + category
					+ ") UNION (SELECT * FROM fullinfoitems finfo"
					+ " WHERE finfo.category_id = " + category + "))");
		}
		String tmpsql = new String(sb.toString());
		boolean badFilter = false;
		if (!filterMap.isEmpty()) {
			sb.append(" WHERE ");
			int mapSize = 0;

			for (String columnName : filterMap.keySet()) {
				try {
					ord = ItemOrders.valueOf(columnName.toUpperCase());
					column = ord.toString();
					if (column.equals("START_DATE")
							|| column.equals("STOP_DATE")) {
						sb.append(String.format(DATE_VALUE, columnName));
					} else {
						if (column.equals("MIN_PRICE")) {
							sb.append(String.format(PRICE_FILTER, ItemOrders.START_PRICE.toString(), ">"));
						} else if (column.equals("MAX_PRICE")) {
							sb.append(String.format(PRICE_FILTER, ItemOrders.START_PRICE.toString(), "<"));
						} else {
							sb.append(" " + column + " ");
							sb.append(" LIKE ?");
						}
					}

					if (mapSize < filterMap.size() - 1) {
						mapSize++;
						sb.append(" AND ");
					}

				} catch (IllegalArgumentException e) {
					column = null;
					badFilter = true;
					// needed delete where
				}

			}

			isFilter = true;

		}

		if (badFilter) {
			sb = new StringBuilder(tmpsql);
		}
		boolean badOrder = false;
		if (columnForOrderSpaceSortDirection != null) {
			if (columnForOrderSpaceSortDirection.length != 0) {
				// orderParams = new ArrayList<String>();
				isOrder = true;
				for (int i = 0; i < columnForOrderSpaceSortDirection.length; i++) {
					StringTokenizer st = new StringTokenizer(
							columnForOrderSpaceSortDirection[i]);
					while (st.hasMoreTokens()) {
						try {
							ord = ItemOrders.valueOf(st.nextToken()
									.toUpperCase());
							column = ord.toString();
							// sb.append(" ? ");
							// orderParams.add(column);
						} catch (IllegalArgumentException e) {
							column = null;
							badOrder = true;
						}
						if (st.hasMoreTokens()) {
							if (st.nextToken().toUpperCase().equals("DESC")) {
								order = "DESC";

							} else {
								order = "ASC";
							}
						}
						if (column != null) {
							if ((i + 1) < columnForOrderSpaceSortDirection.length) {
								sb.append(",");
							}
						}
					}

				}
				if (badOrder) {
					sb = new StringBuilder(tmpsql);
				}
			}

		}
		Connection conn = factory.createConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		if (isOrder) {
			sb.append(String.format(OREDER_DIRECTION, column, order));
		}
		String sql = sb.toString();
		sql = Paging.getPage(pageNumber, itemsCountOnPage, sql);

		try {
			pstmt = conn.prepareStatement(sql);
			if (isFilter) {
				int filterCount = 1;
				for (String columnName : filterMap.keySet()) {

					
					if (columnName.equals("START_DATE")
							|| columnName.equals("STOP_DATE")) {
						java.sql.Timestamp t = new Timestamp(
								((java.util.Date) filterMap.get(columnName))
										.getTime());
						pstmt.setObject(filterCount, t);
					} else if(columnName.equals("MIN_PRICE") || columnName.equals("MAX_PRICE")){
						pstmt.setObject(filterCount,
								filterMap.get(columnName));
					} else {
						pstmt.setObject(filterCount,
								"%" + filterMap.get(columnName) + "%");
					}
					filterCount++;
				}
			}
			if (isOrder) {

				// int count = 0;
				// if (isFilter) {
				// count = 2;
				// for (String ords : orderParams) {
				// pstmt.setString(count, ords);
				// count++;
				// }
				// } else {
				// count = 1;
				// for (String ords : orderParams) {
				// pstmt.setString(count, ords);
				// count++;
				// }
				// }

			}
			rs = pstmt.executeQuery();
			while (rs.next()) {

				findedItems.add(RsToFullInfoTO.returnTOFromRs(rs));
			}
			rs.close();
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
		return findedItems;

	}
}
