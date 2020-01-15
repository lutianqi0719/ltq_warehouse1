package com.lutianqi.dao;

import com.lutianqi.model.Account;

public interface Loanable
{
	//贷款
	public Account requestLoan(double money) ;
	
	//还贷
	public Account payLoan(double money) ;
}
