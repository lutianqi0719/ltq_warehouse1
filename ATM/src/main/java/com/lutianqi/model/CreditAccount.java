package com.lutianqi.model;

import com.lutianqi.exception.BalanceNotEnoughException;

@SuppressWarnings("serial")
public class CreditAccount extends Account
{

	@Override
	public Account withdraw(double money)
	{
		// TODO Auto-generated method stub
		if(super.getBalance()-money>=0){
			super.setBalance(super.getBalance()-money);
		}else if(Math.abs(super.getBalance()-money)<=ceiling){
			super.setBalance(super.getBalance()-money);
		}else{
			BalanceNotEnoughException bnee = new BalanceNotEnoughException("账户余额超过透支额,不需要透支") ;
			try {
				throw(bnee) ;
			} catch (BalanceNotEnoughException e) {
				e.printStackTrace();
			}
		}
		return this;
	}
	
	private double ceiling ;

	public CreditAccount(String password, String name, String personId,String email,int type) {
		super(password,  name,  personId, email,type);

	}

	public double getCeiling() {
		return ceiling;
	}

	public void setCeiling(double ceiling) {
		this.ceiling = ceiling;
	}

}
