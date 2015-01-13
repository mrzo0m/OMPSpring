package com.epam.training.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.epam.training.persistence.DAOFactory;
import com.epam.training.persistence.Vendors;
import com.epam.training.persistence.pojo.ItemTO;
import com.epam.training.persistence.pojo.UserRole;

/**
 * @author Oleg_Burshinov Controller for edit item action
 */
public class EditItemController extends SimpleFormController {
	private DAOFactory daoFactory;

	/**
	 * Data base initialization
	 * 
	 * @throws Exception
	 */
	public void initDAO() throws Exception {
		daoFactory = DAOFactory.getDAOFactory(Vendors.ORACLE);

	}

	@Override
	protected void onBindOnNewForm(HttpServletRequest request, Object command,
			BindException errors) throws Exception {
		ItemTO item = (ItemTO) command;
		if (item.getItemId() != null) {
			ItemTO item2 = daoFactory.getItemDAO().findItemById(
					item.getItemId());
			item.setTitle(item2.getTitle());
			item.setDescription(item2.getDescription());
			item.setStartPrice(item2.getStartPrice());
			item.setTimeLeft(item2.getTimeLeft());
			item.setBuyItNow(item2.isBuyItNow());
			item.setBidIncrement(item2.getBidIncrement());

		}
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
		ItemTO to = (ItemTO) command;
		if (to.getItemId() != null) {
			daoFactory.getItemDAO().updateItem(to.getItemId(), to);
		} else {
			UserRole role = getUserRole();
			to.setSellerId(role.getUser().getUserId());
			daoFactory.getItemDAO().insertItem(to);
		}

		return new ModelAndView("forward:" + "/showitems.htm?command=showitems");
	}

	/**
	 * @return the userRole
	 */
	public UserRole getUserRole() {
		return null;
	}

}
