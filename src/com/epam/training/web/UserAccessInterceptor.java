package com.epam.training.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.epam.training.persistence.pojo.UserRole;

/**
 * @author Oleg_Burshinov Interceptor for separate authentication authorization
 * 
 * 
 */
public class UserAccessInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = Logger
			.getLogger(UserAccessInterceptor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String path = request.getRequestURI().substring(
				request.getContextPath().length());
		logger.info("Intercepting: " + request.getRequestURI() + " "
				+ " User in session "
				+ request.getSession().getAttribute("userrole"));
		if (path.equals("/showitems.htm") || path.equals("/advancedsearch.htm")
				|| path.equals("/showmyitems.htm")) {
			Integer page = Integer.valueOf(1);
			String pageNumber = (String) request.getParameter("pageNumber");
			if (pageNumber != null) {
				try {
					page = Integer.parseInt(pageNumber);
				} catch (NumberFormatException e) {
					System.out.println(e.getMessage());
				}
			}
			request.setAttribute("pageNumber", page);
		}
		if (path.equals("/edititem.htm") || path.equals("/showmyitems.htm")) {
			UserRole ur = (UserRole) request.getSession(false).getAttribute(
					"userrole");
			if (ur == null) {
				redirect(request, response, "/login.htm");
				return false;
			} else {
				if (!ur.isAuthorized()) {
					redirect(request, response, "/login.htm");
				}
				return ur.isAuthorized();
			}

		}

		return true;

	}

	private void redirect(HttpServletRequest request,
			HttpServletResponse response, String path) throws ServletException {
		try {
			response.sendRedirect(request.getContextPath() + path);
		} catch (java.io.IOException e) {
			throw new ServletException(e);
		}
	}

}
