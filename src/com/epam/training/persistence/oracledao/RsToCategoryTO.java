/**
 * 
 */
package com.epam.training.persistence.oracledao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.epam.training.persistence.pojo.CategoryTO;

/**
 * @author Oleg_Burshinov
 *
 */
public class RsToCategoryTO {
	/**
	 * @param rs
	 * @return Category info
	 * @throws SQLException
	 */
	public static CategoryTO returnTOFromRs(ResultSet rs) throws SQLException{
		CategoryTO to = null;
		Integer catId = Integer.valueOf(rs.getInt("CAT_ID"));
		String title = rs.getString("TITLE");
		Integer level = Integer.valueOf(rs.getInt("LEVEL"));
		to = new CategoryTO(title, level, catId);
		return to;
		
	}
}
