package com.lutianqi.test;

import java.util.Scanner;

import com.lutianqi.model.Account;
import com.lutianqi.model.Bank;

public class BankTest {

	private static Scanner sc = new Scanner(System.in) ;
	
	private Bank bank = Bank.getInstance() ;
	
	public static void main(String[] args) {
		Account a = null ;
		BankTest bt = new BankTest() ;
		boolean bl = true ;
		while(bl){
			System.out.println("0.开户  1.登陆  2.存款 3.取款 4.设置透支 5.转账 6.统计所有账户总额 7.贷款 8.还贷 9:排名 10:注销");
			int act = sc.nextInt() ;
			switch(act){
			case 0 : bt.kaihu(); break ;
			case 1 : a = bt.Login() ; break ;
			case 2 : bt.cunkuan(); break ;
			case 3 : a = bt.qukuan(a); break ;
			case 4 : a = bt.celling(a) ; break ;
			case 5 : a = bt.zhuanzhang(a) ; break ;
			case 6 : bt.getSum(); break ;
			case 7 : a = bt.daikuan(a) ; break ;
			case 8 : a = bt.huandai(a) ; break ;
			case 9 : bt.paiming(); break ;
			case 10 : a = null ; break ;
			default :bl = false ; break ;
			}
		}
	}
	
	public void paiming(){
		bank.printSortAllAccount();
	}
	
	public Account huandai(Account a){
		if (a == null) {
			System.out.println("您还没登陆");
			 return null ;
		} else {
			System.out.println("请输入还贷金额：");
			String money = sc.next() ;
			Account ai = bank.payLoan(a.getId(), Double.valueOf(money)) ;
			if(ai != null){
				System.out.println("sucess ! "+ai);
				return ai ;
			}else{
				System.out.println("fail!");
				return a ;
			}
		}
	}
	
	public Account daikuan(Account a){
		if (a == null) {
			System.out.println("您还没登陆");
			 return null ;
		} else {
			System.out.println("请输入贷款金额：");
			String money = sc.next() ;
			Account ai = bank.requestLoan(a.getId(), Double.valueOf(money)) ;
			if(ai != null){
				System.out.println("sucess ! "+ai);
				return ai ;
			}else{
				System.out.println("fail!");
				return a ;
			}
		}
		
	}
	
	public void getSum(){
		System.out.println("sum="+bank.getBalanceSum());
		bank.print();
	}
	
	public Account zhuanzhang(Account a){
		if (a == null) {
			System.out.println("您还没登陆");
			 return null ;
		} else {
			System.out.println("请输入转入账号：");
			String to = sc.next() ;
			System.out.println("请输入转账金额：");
			String money = sc.next() ;
			boolean bl = bank.transfer(a.getId(), a.getPassword(), Long.valueOf(to), Double.valueOf(money)) ;
			if(bl){
//				a.setBalance(a.getBalance()-Double.valueOf(money));
				System.out.println("成功！"+a);
				return a ;
			}else{
				System.out.println("操作失败");
				return a ;
			}
		}
	}
	
	public Account celling(Account a){
		if (a == null) {
			System.out.println("您还没登陆");
			 return null ;
		} else {
			System.out.println("请设置celling:");
			String money = sc.next() ;
			Account ai = bank.updateCeiling(a.getId(), a.getPassword(), Double.valueOf(money)) ;
			if(ai != null){
				System.out.println("设置成功！--"+ai);
				return ai ;
			}else{
				System.out.println("设置失败");
				return a ; 
			}
		}
	}
	
	public Account qukuan(Account a) {
		if (a == null) {
			System.out.println("您还没登陆");
			 return null ;
		} else {
			System.out.println("请输入取款金额：");
			String money = sc.next();
			Account ai = bank.withdraw(a.getId(), a.getPassword(), Double.valueOf(money));
			if(ai != null){
				System.out.println("账户当前状况：" + ai);
				return ai ;
			}else{
				return a ;
			}
		}
	}
	
	public void cunkuan(){

		System.out.println("请输入ID：");
		String idStr = sc.next() ;
		System.out.println("请存入金钱：");
		String money = sc.next() ;
			Account a =bank.deposit(Long.valueOf(idStr),Double.valueOf(money)) ;
			if(a == null)
				System.out.println("存款失败");
			else
				System.out.println("存款成功");
	}
	
	public Account Login(){
		System.out.println("请输入ID：");
		String idStr = sc.next() ;
		System.out.println("请输入密码：");
		String password = sc.next() ;
		return bank.login(Long.valueOf(idStr), password) ;
	}
	
	public void kaihu(){
		System.out.println("请输入您要开户的账户类型:0 – 储蓄账户  1 – 信用账户 2 – 可贷款储蓄账户 3– 可贷款信用账户");
		int type = sc.nextInt() ;
		System.out.println("请依次输入：password, repassword, name, personID, email");
		String password = sc.next() ;
		String repassword = sc.next() ;
		String name = sc.next() ;
		String personID = sc.next() ;
		String email = sc.next() ;
		Account a =bank.register(password, repassword, name, personID, email, type) ;
		if(a != null)
			System.out.println("已成功开户："+a);
		else
			System.out.println("开户失败");
	}

}

