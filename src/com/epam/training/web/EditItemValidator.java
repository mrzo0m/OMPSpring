/**
 * 
 */
package com.epam.training.web;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.epam.training.persistence.pojo.ItemTO;

/**
 * @author Oleg_Burshinov
 * 
 */
public class EditItemValidator implements Validator {
	private static final int TITLE_LENGTH = 30;
	private static final double EPSILON = 0.001;
	private static final int DESCRIPTION_LENGTH = 80;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
		return ItemTO.class.isAssignableFrom(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title",
				"field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startPrice",
				"field.required");
		ItemTO item = (ItemTO) target;
		if (item.getTitle().length() > TITLE_LENGTH) {
			errors.rejectValue("title", "title.big", "The title is too long.");
		}
		if (item.getStartPrice() < EPSILON) {
			errors.rejectValue("startPrice", "startprice.low",
					"The startPrice is too low.");
		}
		if (item.getDescription().length() > DESCRIPTION_LENGTH) {
			errors.rejectValue("description", "description.big",
					"The description is too long.");
		}
		if (!item.isBuyItNow() && item.getBidIncrement() == null) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bidIncrement",
					"field.required");
		}
		if (item.getBidIncrement() != null) {
			if (!item.isBuyItNow() && item.getBidIncrement() <= 0) {
				errors.rejectValue("bidIncrement", "bidIncrement.low",
						"The bid Increment is too low.");
			}
		}

		if (!item.isBuyItNow() && item.getTimeLeft() == null) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "timeLeft",
					"field.required");
		}
		if (item.getTimeLeft() != null) {
			if (!item.isBuyItNow() && item.getTimeLeft() <= 0) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "timeLeft",
						"field.required");
			}
		}

		// more time left check needed.

	}

}
