package com.epam.training.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.epam.training.persistence.DAOFactory;
import com.epam.training.persistence.Vendors;
import com.epam.training.persistence.pojo.UserTO;

/**
 * @author Oleg_Burshinov Controller for registration action
 */
public class RegistrationController extends SimpleFormController {

	private static final Logger logger = Logger
			.getLogger(RegistrationController.class);
	private DAOFactory daoFactory;

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
	 * org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		UserTO to = (UserTO) command;
		daoFactory.getUserDAO().InsertUser(to);
		logger.info("User: " + to.getLogin() + " successfully registered");
		return new ModelAndView("redirect:" + "/login.htm");
	}

}
