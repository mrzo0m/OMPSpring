package com.epam.training.web;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.epam.training.persistence.pojo.UserTO;

/**
 * @author Oleg_Burshinov Validator for login action
 */
public class LoginValidator implements Validator {
	private static final int MINIMUM_PASSWORD_LENGTH = 6;

	public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
		return UserTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login",
				"field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"field.required");
		UserTO user = (UserTO) target;
		if (user.getPassword() != null
				&& user.getPassword().trim().length() < MINIMUM_PASSWORD_LENGTH) {
			errors.rejectValue("password", "field.min.length",
					"The password must be at least [" + MINIMUM_PASSWORD_LENGTH
							+ "] characters in length.");
		}
	}

}
