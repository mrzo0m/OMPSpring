package com.epam.training.web;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.epam.training.persistence.pojo.AdvancedSearch;

/**
 * @author Oleg_Burshinov Advanced Search action
 */
public class AdvancedSearchController extends SimpleFormController {
	@SuppressWarnings("unused")
	private static final Logger logger = Logger
			.getLogger(LoginController.class);
	private CustomDateEditor customDateEditor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.multiaction.MultiActionController
	 * #initBinder(javax.servlet.http.HttpServletRequest,
	 * org.springframework.web.bind.ServletRequestDataBinder)
	 */
	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, getCustomDateEditor());
		binder.registerCustomEditor(String.class,
				new StringTrimmerEditor(false));
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
		AdvancedSearch query = (AdvancedSearch) command;
		if (query.getItemUID() != null) {
			Cookie itemUID = new Cookie("itemUID", query.getItemUID()
					.toString());
			response.addCookie(itemUID);
		}
		if (query.getTitleOfItem() != null) {
			Cookie titleOfItem = new Cookie("titleOfItem",
					query.getTitleOfItem());
			response.addCookie(titleOfItem);
		}
		if (query.getDescription() != null) {
			Cookie description = new Cookie("description",
					query.getDescription());
			response.addCookie(description);
		}
		if (query.getMinPrice() > 0) {
			Cookie minPrice = new Cookie("minPrice", Double.valueOf(
					query.getMinPrice()).toString());
			response.addCookie(minPrice);
		}
		if (query.getMaxPrice() > 0) {
			Cookie maxPrice = new Cookie("maxPrice", Double.valueOf(
					query.getMaxPrice()).toString());
			response.addCookie(maxPrice);
		}
		if (query.isBuyItNow()) {
			Cookie buyItNow = new Cookie("buyItNow", "true");
			response.addCookie(buyItNow);
		}
		if (query.getStartDate() != null) {
			Cookie startDate = new Cookie("startDate", Long.toString(query
					.getStartDate().getTime()));
			response.addCookie(startDate);
		}
		if (query.getExpireDate() != null) {
			Cookie expireDate = new Cookie("expireDate", Long.toString(query
					.getExpireDate().getTime()));
			response.addCookie(expireDate);
		}
		if (query.getBiddersCount() != null) {
			Cookie biddersCount = new Cookie("biddersCount", query
					.getBiddersCount().toString());
			response.addCookie(biddersCount);
		}

		return new ModelAndView("forward:" + "/showitems.htm?command=showitems")
				.addObject("searchCommand", query);
		// return super.onSubmit(request, response, command, errors);
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
		if (errors.getErrorCount() > 0) {
			return new ModelAndView("AdvancedSearch", errors.getModel());
		}
		AdvancedSearch query = (AdvancedSearch) getCommand(request);
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("itemUID")) {
				query.setItemUID(Integer.parseInt(cookie.getValue()));
			}
			if (cookie.getName().equals("titleOfItem")) {
				query.setTitleOfItem(cookie.getValue());
			}
			if (cookie.getName().equals("description")) {
				query.setDescription(cookie.getValue());
			}
			if (cookie.getName().equals("minPrice")) {
				query.setMinPrice(Double.parseDouble(cookie.getValue()));
			}
			if (cookie.getName().equals("maxPrice")) {
				query.setMaxPrice(Double.parseDouble(cookie.getValue()));
			}
			if (cookie.getName().equals("buyItNow")) {
				query.setBuyItNow(true);
			}
			if (cookie.getName().equals("startDate")) {
				query.setStartDate(new Date(Long.parseLong(cookie.getValue())));
			}
			if (cookie.getName().equals("expireDate")) {
				query.setExpireDate(new Date(Long.parseLong(cookie.getValue())));
			}
			if (cookie.getName().equals("biddersCount")) {
				query.setBiddersCount(Integer.parseInt(cookie.getValue()));
			}
			System.out.println(cookie.getValue());
		}

		return new ModelAndView("AdvancedSearch").addObject("AdvancedSearch",
				query);
	}

	/**
	 * @return the customDateEditor
	 */
	public CustomDateEditor getCustomDateEditor() {
		return customDateEditor;
	}

	/**
	 * @param customDateEditor
	 *            the customDateEditor to set
	 */
	public void setCustomDateEditor(CustomDateEditor customDateEditor) {
		this.customDateEditor = customDateEditor;
	}

}
