package com.lutianqi.model;

import java.io.Serializable;

import com.lutianqi.exception.BalanceNotEnoughException;

@SuppressWarnings("serial")
public class SavingAccount extends Account implements Serializable{

	public SavingAccount( String password, String name, String personId,String email,int type) {
		super(   password,  name,  personId, email,type);
	}
	
	/**
	 * 取款方法
	 * @param money
	 * @return
	 */
	public  Account withdraw(double money){
		if(this.getBalance()-money>=0){
			this.setBalance(this.getBalance()-money);
		}else{
			BalanceNotEnoughException balancenotenoughexception = new BalanceNotEnoughException("余额不足，无法取款") ;
			try {
				throw(balancenotenoughexception) ;
			} catch (BalanceNotEnoughException e) {
				e.printStackTrace();
			}
		}
		return this ;
	}
}
