package com.vladsoft.carpark;



public class CarParkSqlException extends Exception {	
	private static final long serialVersionUID = 1L;
	private int errorCode;
	private String message;	
	
	public CarParkSqlException(int errorCode) {
		this.errorCode = errorCode;
		this.message = "Internal SQL Error: ora" + errorCode+ " ";
	}

	@Override
	public String getMessage() {
		switch(errorCode) {
		case (-20000):
			this.message += "Atempted to get non existent token.";
			break;
		case (-20010):
			this.message += "Atempted to pay already payed customer.";
			break;
		case (-20020):
			this.message += "Atempted to checkout unpayed customer.";
			break;
		}
		return this.message;
	}
	//repackage error for web app logic.
	public int repackageError(int errorCode) {
		switch(errorCode) {
			case (-20000):	return 0;
			case (-20010):	return 0;
			case (-20020):	return 0;
		}
		return errorCode;
				
			
			
	}
}
