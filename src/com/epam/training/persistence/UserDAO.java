/**
 * 
 */
package com.epam.training.persistence;

import com.epam.training.persistence.pojo.BidsTO;
import com.epam.training.persistence.pojo.BlackListTO;
import com.epam.training.persistence.pojo.ItemTO;
import com.epam.training.persistence.pojo.UserTO;

/**
 * @author Oleg_Burshinov
 * 
 */
public interface UserDAO {
	/**
	 * @param to
	 * @return  description  not added yet
	 */
	public int InsertUser(UserTO to);

	/**
	 * @param to
	 * @return  description  not added yet
	 */
	public boolean authorizeUser(UserTO to);

	/**
	 * @param to
	 * @return added item
	 */
	public ItemTO addItem(UserTO to);

	/**
	 * @param to 
	 * @return description  not added yet  
	 */
	public ItemTO[] UserItems(UserTO to);

	/**
	 * @param to
	 * @return  description  not added yet
	 */
	public BidsTO addBid(UserTO to);
	
	/**
	 * @param login
	 * @return user id
	 */
	public Integer getUserIdByLogin(String login);
	
	/**
	 * @param userId
	 * @return information about user
	 */
	public UserTO getUserInfoById(Integer userId);
	
	/**
	 * @param to
	 * @return status
	 */
	public boolean addUserToBlackList(BlackListTO to);
	
	/**
	 * @param to
	 * @return status
	 */
	public boolean removeUserFromBlackList(BlackListTO to);
}
