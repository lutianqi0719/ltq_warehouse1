package com.lutianqi.exception;

@SuppressWarnings("serial")
public class BalanceNotEnoughException extends ATMException
{

	public BalanceNotEnoughException(String message)
	{
		super(message);
		// TODO Auto-generated constructor stub
	}

	public String getMessage() {
		return super.getMessage();
	}
}
