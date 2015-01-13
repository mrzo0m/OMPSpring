package com.epam.training.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.epam.training.persistence.DAOFactory;
import com.epam.training.persistence.Vendors;
import com.epam.training.persistence.pojo.UserTO;

/**
 * @author Oleg_Burshinov Validator for Registration
 */
public class RegistrationValidator implements Validator {

	private Pattern pattern;
	private Matcher matcher;
	private static final int MINIMUM_PASSWORD_LENGTH = 6;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private DAOFactory daoFactory;

	/**
	 * Data base initialization
	 * 
	 * @throws Exception
	 */
	public void initDAO() throws Exception {
		daoFactory = DAOFactory.getDAOFactory(Vendors.ORACLE);

	}

	RegistrationValidator() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
		return UserTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName",
				"field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "billingAddress",
				"field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login",
				"field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword",
				"field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email",
				"field.required");

		UserTO user = (UserTO) target;
		if (!errors.hasFieldErrors("login")) {
			if (user.getLogin() != null) {
				int unique = -1;
				unique = daoFactory.getUserDAO().getUserIdByLogin(
						user.getLogin());
				if (unique > 0) {
					errors.rejectValue("login", "login.not.unique",
							"Not unique login");
				}
			}
		}
		if (!errors.hasFieldErrors("password")) {
			if (user.getPassword() != null
					&& user.getPassword().trim().length() < MINIMUM_PASSWORD_LENGTH) {
				errors.rejectValue("password", "password.min.length",
						"The password must be at least ["
								+ MINIMUM_PASSWORD_LENGTH
								+ "] characters in length.");
			}
		}
		if (!errors.hasFieldErrors("confirmPassword")) {
			if (user.getPassword() != null
					&& !user.getPassword().equals(user.getConfirmPassword())) {
				errors.rejectValue("confirmPassword", "password.not.match",
						"Password does not match the confirm password.");
			}
		}
		if (!errors.hasFieldErrors("email")) {
			matcher = pattern.matcher(user.getEmail());
			if (!matcher.matches()) {
				errors.rejectValue("email", "email.bad.pattern",
						"Wrong email syntax");
			}
		}

	}

}
