/**
 * 
 */
package com.epam.training.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.epam.training.persistence.DAOFactory;
import com.epam.training.persistence.Vendors;
import com.epam.training.persistence.pojo.FullInfoTO;
import com.epam.training.persistence.pojo.ItemTO;
import com.epam.training.persistence.pojo.UserRole;

/**
 * @author Oleg_Burshinov Controller for showing user items action
 */
public class ShowMyItemsController extends SimpleFormController {

	private DAOFactory daoFactory;
	private int ITEMS_ON_MY_ITEMS_PAGE = 1;

	/**
	 * Data base initialization
	 * 
	 * @throws Exception
	 */
	public void initDAO() throws Exception {
		daoFactory = DAOFactory.getDAOFactory(Vendors.ORACLE);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.SimpleFormController#showForm(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * org.springframework.validation.BindException, java.util.Map)
	 */
	@Override
	protected ModelAndView showForm(HttpServletRequest request,
			HttpServletResponse response, BindException errors, @SuppressWarnings("rawtypes") Map controlModel)
			throws Exception {
		List<FullInfoTO> allitems = null;

		Integer pageNumber = (Integer) request.getAttribute("pageNumber");
		UserRole userRole = getUserRole();
		allitems = daoFactory.getItemDAO().getFullInfoMyItems(pageNumber,
				getITEMS_ON_MY_ITEMS_PAGE(), userRole.getUser().getUserId());
		return new ModelAndView("ShowMyItems").addObject("allitems", allitems);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		String action = request.getParameter("action");
		ItemTO to = (ItemTO) command;
		if (action != null) {
			if (action.equals("delete")) {
				daoFactory.getItemDAO().deleteItem(to.getItemId());
				return new ModelAndView("redirect:" + "/showmyitems.htm");
			}
		}

		return new ModelAndView("ShowMyItems");
	}

	/**
	 * @return the userRole
	 */
	public UserRole getUserRole() {
		return null;
	}

	/**
	 * @return the iTEMS_ON_MY_ITEMS_PAGE
	 */
	public int getITEMS_ON_MY_ITEMS_PAGE() {
		return ITEMS_ON_MY_ITEMS_PAGE;
	}

	/**
	 * @param iTEMS_ON_MY_ITEMS_PAGE
	 *            the iTEMS_ON_MY_ITEMS_PAGE to set
	 */
	public void setITEMS_ON_MY_ITEMS_PAGE(int iTEMS_ON_MY_ITEMS_PAGE) {
		ITEMS_ON_MY_ITEMS_PAGE = iTEMS_ON_MY_ITEMS_PAGE;
	}

}
