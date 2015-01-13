package com.epam.training.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.epam.training.persistence.DAOFactory;
import com.epam.training.persistence.Vendors;
import com.epam.training.persistence.pojo.AdvancedSearch;
import com.epam.training.persistence.pojo.BidsTO;
import com.epam.training.persistence.pojo.FullInfoTO;
import com.epam.training.persistence.pojo.SimpleSearch;
import com.epam.training.persistence.pojo.UserRole;

/**
 * @author Oleg_Burshinov Controller for main application action
 *         <ul>
 *         <li>Show all items
 *         <li>logout
 *         <li>bid
 *         <li>buy
 *         <li>search
 *         <li>clear search
 *         <li>clear advanced search
 *         </ul>
 */
public class ShowItemsController extends MultiActionController {
	private static final Logger logger = Logger
			.getLogger(ShowItemsController.class);
	private int ITEMS_ON_PAGE = 2;
	private static final int UID = 1;
	private static final int Title = 2;
	private static final int Description = 3;
	private DAOFactory daoFactory;
	private BindingResult errors;

	/**
	 * @return errors in bean
	 */
	public BindingResult getErrors() {
		return errors;
	}

	/**
	 * Data base initialization
	 * 
	 * @throws Exception
	 */
	public void initDAO() throws Exception {
		daoFactory = DAOFactory.getDAOFactory(Vendors.ORACLE);

	}

	/**
	 * Checking for errors in command object
	 * 
	 * @param command
	 */
	public void validate(Object command) {
		Validator[] validators = getValidators();
		if (validators != null) {
			for (int index = 0; index < validators.length; index++) {
				Validator validator = validators[index];
				if (validator instanceof AdvancedSearchValidator) {

					if (((AdvancedSearchValidator) validator).supports(command
							.getClass())) {
						ValidationUtils.invokeValidator(validators[index],
								command, errors);
					}
				} else if (validator.supports(command.getClass())) {
					ValidationUtils.invokeValidator(validators[index], command,
							errors);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.multiaction.MultiActionController
	 * #bind(javax.servlet.http.HttpServletRequest, java.lang.Object)
	 */
	@Override
	protected void bind(HttpServletRequest request, Object command)
			throws Exception {
		ServletRequestDataBinder binder = createBinder(request, command);
		binder.bind(request);
		errors = binder.getBindingResult();
	}

	/**
	 * Clear advanced search
	 * 
	 * @param request
	 * @param response
	 * @return main page view
	 */
	public ModelAndView removesearch(HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession(false).removeAttribute("searchCommand");
		return new ModelAndView("forward:" + "/showitems.htm?command=showitems");
	}

	/**
	 * Clear simple search
	 * 
	 * @param request
	 * @param response
	 * @return main page view
	 */
	public ModelAndView clearsearch(HttpServletRequest request,
			HttpServletResponse response) {

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (!cookies[i].getName().equals("JSESSIONID")) {
					cookies[i].setMaxAge(0);
					cookies[i].setValue("");
					response.addCookie(cookies[i]);
				}
			}
		}
		request.getSession(false).removeAttribute("searchCommand");
		return new ModelAndView("redirect:" + "/advancedsearch.htm");

	}

	private String[] getSort(HttpServletRequest request) {
		String[] sort = null;
		if (request.getParameter("sort") != null) {
			String sortColumn = (String) request.getParameter("sort");
			request.setAttribute("sort", sortColumn);
			String order = (String) request.getParameter("order");
			request.setAttribute("order", order);
			if (order.equals("")) {
				request.setAttribute("order", "asc");
				order = "asc";
			}
			sortColumn += " " + order;
			String[] s = { sortColumn };
			sort = s;
		}
		return sort;
	}

	/**
	 * Show items
	 * 
	 * @param request
	 * @param response
	 * @return view with items
	 */
	public ModelAndView showitems(HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("ShowItems");
		List<FullInfoTO> allitems = null;

		Integer pageNumber = (Integer) request.getAttribute("pageNumber");
		String[] sort = getSort(request);
		try {
			Object searchCommand = request.getAttribute("searchCommand");
			if (searchCommand == null) {
				searchCommand = request.getSession(false).getAttribute(
						"searchCommand");
			}
			if (searchCommand instanceof AdvancedSearch) {
				AdvancedSearch query = (AdvancedSearch) searchCommand;

				Map<String, Object> map = new HashMap<String, Object>();

				if (query.getItemUID() != null) {
					map.put("UID", query.getItemUID().toString());
				}
				if (query.getTitleOfItem() != null
						&& !query.getTitleOfItem().equals("")) {
					map.put("TITLE", query.getTitleOfItem());
				}
				if (query.getDescription() != null
						&& !query.getDescription().equals("")) {
					map.put("DESCRIPTION", query.getDescription());
				}
				if (query.getMinPrice() > 0) {
					map.put("MIN_PRICE", query.getMinPrice());
				}
				if (query.getMaxPrice() > 0) {
					map.put("MAX_PRICE", query.getMaxPrice());
				}
				if (query.isBuyItNow()) {
					map.put("BUYITNOW", 1);
				}
				if (query.getStartDate() != null) {
					map.put("START_DATE", query.getStartDate());
				}
				if (query.getExpireDate() != null) {
					map.put("STOP_DATE", query.getExpireDate());
				}
				if (query.getBiddersCount() != null) {
					map.put("BIDDER_COUNT", query.getBiddersCount().toString());
				}

				Map<String, Object> synmap = Collections.synchronizedMap(map);

				allitems = daoFactory.getItemDAO().getAdvancedInfoItems(
						pageNumber, getITEMS_ON_PAGE(), sort, synmap, null);
				request.getSession().setAttribute("searchCommand",
						searchCommand);
			} else if (searchCommand instanceof SimpleSearch) {
				SimpleSearch query = (SimpleSearch) searchCommand;

				if (query.getShipping() == UID) {

					allitems = daoFactory.getItemDAO().getFullInfoItems(
							pageNumber, getITEMS_ON_PAGE(), sort,
							query.getKeywordInput(), "UID", null);

				} else if (query.getShipping() == Title) {
					allitems = daoFactory.getItemDAO().getFullInfoItems(
							pageNumber, getITEMS_ON_PAGE(), sort,
							query.getKeywordInput(), "TITLE", null);

				} else if (query.getShipping() == Description) {
					allitems = daoFactory.getItemDAO().getFullInfoItems(
							pageNumber, getITEMS_ON_PAGE(), sort,
							query.getKeywordInput(), "DESCRIPTION", null);

				}

				request.getSession().setAttribute("searchCommand",
						searchCommand);
			} else {
				allitems = daoFactory.getItemDAO().getFullInfoItems(pageNumber,
						getITEMS_ON_PAGE(), sort, null, null, null);
			}

		} catch (ClassCastException e) {
			e.printStackTrace();
		}

		return mav.addObject("allitems", allitems);
	}

	/**
	 * Unauthorize user
	 * 
	 * @param request
	 * @param response
	 * @return login page view
	 */
	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response) {

		UserRole userRole = getUserRole();
		userRole.setAuthorized(false);
		return new ModelAndView("redirect:" + "/login.htm");
	}

	/**
	 * Bid action
	 * 
	 * @param request
	 * @param response
	 * @param command
	 * @return main page
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView bid(HttpServletRequest request,
			HttpServletResponse response, BidsTO command)
			throws ServletRequestBindingException {
		validate(command);
		if (errors.hasErrors()) {
			logger.info("Bid bean has Errors");
			return showitems(request, response).addObject("bidError",
					"Wrong bid").addObject("bidItemId", command.getItemId());

		} else {
			UserRole role = getUserRole();
			command.setBidderId(role.getUser().getUserId());
			daoFactory.getBidsDAO().addBid(command);
		}
		return new ModelAndView("forward:" + "/showitems.htm?command=showitems");
	}

	/**
	 * Buy action
	 * 
	 * @param request
	 * @param response
	 * @param command
	 * @return main page view
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView buy(HttpServletRequest request,
			HttpServletResponse response, BidsTO command)
			throws ServletRequestBindingException {
		UserRole role = getUserRole();
		command.setBidderId(role.getUser().getUserId());
		daoFactory.getBidsDAO().addBid(command);
		return new ModelAndView("forward:" + "/showitems.htm?command=showitems");
	}

	/**
	 * Simple search action
	 * 
	 * @param request
	 * @param response
	 * @return view with searched items
	 * @throws ServletRequestBindingException
	 */
	public ModelAndView search(HttpServletRequest request,
			HttpServletResponse response) throws ServletRequestBindingException {

		try {
			SimpleSearch searchQuery = (SimpleSearch) newCommandObject(SimpleSearch.class);
			bind(request, searchQuery);
			validate(searchQuery);
			if (errors.hasErrors()) {
				logger.info("Search bean has Errors");
				return new ModelAndView("ShowItems", errors.getModel());
			} else {
				logger.info("Search bean valid. Serch started.");
				return new ModelAndView("forward:"
						+ "/showitems.htm?command=showitems").addObject(
						"searchCommand", searchQuery);

			}
		} catch (Exception e) {
			logger.info(e);
		}

		return new ModelAndView("ShowItems");

	}

	/**
	 * @return the userRole
	 */
	public UserRole getUserRole() {
		return null;
	}

	/**
	 * @return the iTEMS_ON_PAGE
	 */
	public int getITEMS_ON_PAGE() {
		return ITEMS_ON_PAGE;
	}

	/**
	 * @param iTEMS_ON_PAGE
	 *            the iTEMS_ON_PAGE to set
	 */
	public void setITEMS_ON_PAGE(int iTEMS_ON_PAGE) {
		ITEMS_ON_PAGE = iTEMS_ON_PAGE;
	}

}
