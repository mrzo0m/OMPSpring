/**
 * 
 */
package com.epam.training.persistence.oracledao;

/**
 * @author Oleg_Burshinov
 *
 */
public enum ItemOrders {
	    /**
	     * COLUMN 
	     */
	    UID("\"UID\""),
	    /**
	     * COLUMN 
	     */
	    TITLE("TITLE"),
	    /**
	     * COLUMN 
	     */
	    DESCRIPTION("DESCRIPTION"),
	    /**
	     * COLUMN 
	     */
	    SELLER("SELLER"),
	    /**
	     * COLUMN 
	     */
	    START_PRICE("START_PRICE"),
	    /**
	     * COLUMN 
	     */
	    BID_INCREMENT("BID_INCREMENT"),
	    /**
	     * COLUMN 
	     */
	    BEST_OFFER("BEST_OFFER"),
	    /**
	     * COLUMN 
	     */
	    BIDDER("BIDDER"),
	    /**
	     * COLUMN 
	     */
	    STOP_DATE("STOP_DATE"),
	    /**
	     * COLUMN 
	     */
	    BUYITNOW("BUYITNOW"),
	    /**
	     * COLUMN 
	     */
	    BIDDER_COUNT("BIDDER_COUNT"),
	    /**
	     * COLUMN 
	     */
	    MIN_PRICE("MIN_PRICE"),
	    /**
	     * COLUMN 
	     */
	    MAX_PRICE("MAX_PRICE"),
	    /**
	     * COLUMN 
	     */
	    START_DATE("START_DATE")
	    ;
	    /**
	     * @param text
	     */
	    private ItemOrders(final String text) {
	        this.text = text;
	    }

	    private final String text;

	    /* (non-Javadoc)
	     * @see java.lang.Enum#toString()
	     */
	    @Override
	    public String toString() {
	        // TODO Auto-generated method stub
	        return text;
	    }
}
