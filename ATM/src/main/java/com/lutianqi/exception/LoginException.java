package com.lutianqi.exception;

@SuppressWarnings("serial")
public class LoginException extends ATMException
{

	public LoginException(String message)
	{
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	public String getMessage() {
		return super.getMessage();
	}

}
