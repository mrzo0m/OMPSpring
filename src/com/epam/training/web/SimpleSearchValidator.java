package com.epam.training.web;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.epam.training.persistence.pojo.SimpleSearch;

/**
 * @author Oleg_Burshinov
 * Validator for simple search command
 */
public class SimpleSearchValidator implements Validator {

	public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
		return SimpleSearch.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "keywordInput",
				"field.required");
		SimpleSearch query = (SimpleSearch) target;
		if(query != null){
			int shipping =  query.getShipping();
			if(shipping == query.UID){
				Integer uid = null;
				try{
					uid = Integer.parseInt(query.getKeywordInput());
					if(uid < 0 || uid > Integer.MAX_VALUE){
						errors.rejectValue("keywordInput", "itemuid.outofbound","Error out of bound");
					}
				} catch (NumberFormatException e){
					errors.rejectValue("keywordInput", "itemuid.outofbound","Error out of bound");
				}
				
			}
		}
	}

}
