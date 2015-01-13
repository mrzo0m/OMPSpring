package com.epam.training.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.epam.training.persistence.DAOFactory;
import com.epam.training.persistence.Vendors;
import com.epam.training.persistence.pojo.UserRole;
import com.epam.training.persistence.pojo.UserTO;

/**
 * @author Oleg_Burshinov
 * 
 */
public class LoginController extends SimpleFormController {
	private static final Logger logger = Logger
			.getLogger(LoginController.class);
	private DAOFactory daoFactory;

	/**
	 * Data base initialization
	 * 
	 * @throws Exception
	 */
	public void initDAO() throws Exception {
		daoFactory = DAOFactory.getDAOFactory(Vendors.ORACLE);

	}

	/**
	 * @return the userRole
	 */
	public UserRole getUserRole() {
		return null;

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
		UserTO user = (UserTO) command;
		String login = user.getLogin();
		String password = user.getPassword();
		logger.debug("User input to login: " + login + " " + password);
		UserTO to = new UserTO(login, password);
		boolean authorized = daoFactory.getUserDAO().authorizeUser(to);
		logger.info("User authorized: " + authorized);
		ModelAndView modelAndView = null;
		if (authorized) {
			modelAndView = new ModelAndView("redirect:" + getSuccessView());
			int userId = daoFactory.getUserDAO().getUserIdByLogin(
					user.getLogin());
			if (userId > 0) {
				user = daoFactory.getUserDAO().getUserInfoById(userId);
				getUserRole().setAuthorized(authorized);
				getUserRole().setUser(user);
			} else {
				errors.reject("authorization_authentication_error",
						"authorization error");
				modelAndView = showForm(request, response, errors);
			}

		} else {
			errors.reject("authorization_authentication_error",
					"authorization error");
			modelAndView = showForm(request, response, errors);
		}
		return modelAndView;
	}
}
