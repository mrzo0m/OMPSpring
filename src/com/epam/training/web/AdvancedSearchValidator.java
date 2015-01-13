package com.epam.training.web;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.epam.training.persistence.pojo.AdvancedSearch;

/**
 * @author Oleg_Burshinov Validator for Advanced Search object
 */
public class AdvancedSearchValidator implements Validator {

	public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
		return AdvancedSearch.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		AdvancedSearch query = (AdvancedSearch) target;
		if (query != null) {
			if (query.getBiddersCount() != null) {
				if (query.getBiddersCount() < 0) {
					errors.rejectValue("biddersCount", "bidders.min.count",
							"Error bidders count low");
				}
			}
			if (query.getItemUID() != null) {
				if (query.getItemUID() < 0
						|| query.getItemUID() > Integer.MAX_VALUE) {
					errors.rejectValue("itemUID", "itemuid.outofbound",
							"Error out of bound");
				}
			}

			if (query.getMaxPrice() > 0 && query.getMinPrice() > 0) {
				if (query.getMinPrice() > query.getMaxPrice()) {
					errors.rejectValue("maxPrice", "maxPrice.large",
							"Error out of bound");
				}
			}

		}

	}

}
