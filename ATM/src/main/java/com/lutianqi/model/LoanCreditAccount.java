package com.lutianqi.model;

import java.io.Serializable;

import com.lutianqi.dao.Loanable;
import com.lutianqi.exception.BalanceNotEnoughException;
import com.lutianqi.exception.LoanException;

@SuppressWarnings("serial")
public class LoanCreditAccount extends CreditAccount implements Loanable,Serializable
{
	
	//贷款金额
	 
	private long loanAmount ;
	
	public LoanCreditAccount(String password, String name, String personId,
			String email, int type) {
		super(password, name, personId, email, type);
	}

	
	public long getLoanAmount() {
		return loanAmount;
	}


	private void setLoanAmount(long loanAmount) {
		this.loanAmount = loanAmount;
	}


	//贷款

	public Account requestLoan(double money) {
//		LoanCreditAccount account = null ;
		if(money>=0){
			this.setLoanAmount((long) (this.getLoanAmount()+money));
		}else{
			LoanException loanexception = new LoanException("贷款额不能为负数") ;
			try {
				throw(loanexception) ;
			} catch (LoanException e) {
				e.printStackTrace();
			}
		}
		return this ;
	}

	//还贷

	public Account payLoan(double money) {
		if(this.getBalance()-money<0){
			if(money-this.getBalance()<=this.getCeiling()){
				this.setBalance(this.getBalance()-money);
				this.setLoanAmount((long) (this.getLoanAmount()-money));
			}else{
				BalanceNotEnoughException bnee = new BalanceNotEnoughException("账户余额超过透支额，无法还贷") ;
				try {
					throw(bnee) ;
				} catch (BalanceNotEnoughException e) {
					e.printStackTrace();
				}
			}
		}else{
			this.setBalance(this.getBalance()-money);
			this.setLoanAmount((long) (this.getLoanAmount()-money));
		}		
		return this;
	}
}
