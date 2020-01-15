package com.lutianqi.exception;

@SuppressWarnings("serial")
public class LoanException extends ATMException
{

	public LoanException(String message)
	{
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	public String getMessage() {
		return super.getMessage();
	}

}
