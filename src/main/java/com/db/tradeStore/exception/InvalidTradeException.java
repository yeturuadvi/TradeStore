package com.db.tradeStore.exception;

/**
 * @author Advitha
 *
 */
public class InvalidTradeException extends RuntimeException {

	private String tradeId;
	private String errorMessage;
	
	public InvalidTradeException(String tradeId, String errorMessage){
		super(errorMessage);
		this.tradeId = tradeId;
		this.errorMessage = errorMessage;
	}

	public String getTradeId() {
		return tradeId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}	

}
