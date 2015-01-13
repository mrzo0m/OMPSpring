package com.epam.training.web;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.epam.training.persistence.pojo.BidsTO;

/**
 * @author Oleg_Burshinov
 * Bid validator
 */
public class BidValidator implements Validator {
	private final double EPSILON = 0.001;

	public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
		return BidsTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		BidsTO bid = (BidsTO) target;
		if(bid.getBid() < EPSILON){
			errors.rejectValue("bid", "bid.low",
					"The bid too low.");
		}else if (bid.getBestOffer() > EPSILON && bid.getBidInc() != null){
			double tmp = bid.getBestOffer() + bid.getBidInc();
			if(bid.getBid() < tmp){
				errors.rejectValue("bid", "bid.low",
						"The bid too low.");
			}
			
		} else if (bid.getBidInc() != null){
			double tmp =  bid.getStartPrice() + bid.getBidInc(); 
			if(bid.getBid() < tmp){
				errors.rejectValue("bid", "bid.low",
						"The bid too low.");
			}
		}

	}

}
