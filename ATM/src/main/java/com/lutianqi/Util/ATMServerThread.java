package com.lutianqi.Util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

import com.lutianqi.model.Account;
import com.lutianqi.model.Bank;
import com.lutianqi.model.TransportMethod;

public class ATMServerThread extends Thread {

	private Socket socket = null ;
	
	
	public ATMServerThread(Socket socket){
		this.socket = socket ;
	}

	@Override
	public void run() {
		OutputStream out = null ;
		InputStream in = null ;
		ObjectOutputStream oos = null ;
		ObjectInputStream ois = null ;
		try {
			out = socket.getOutputStream() ;
			in = socket.getInputStream() ;
			oos = new ObjectOutputStream(out) ;
			ois = new ObjectInputStream(in) ;
			TransportMethod send_message = null ;  //输入的内容
			TransportMethod receive_message = null  ;//收到的内容
			String action = "" ;
			while(!Thread.interrupted()){
				//服务器接收客户端传递的内容
				receive_message = (TransportMethod)ois.readObject() ;
				action = receive_message.getAction() ;
				if("exit".equals(action))
					break ;
				send_message = this.deal(receive_message, action) ;
				//服务器回复客户端
				oos.writeObject(send_message.getMyAccount());
				oos.reset();
				oos.flush(); 
				send_message = null ;
			}
		} catch (SocketException e) {
			Thread.currentThread().interrupt();
			System.err.println("客户端已经退出"+ socket.getInetAddress());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally{
				try {
					if(oos != null)
						oos.close();
					if(out != null)
						out.close();
					if(ois != null)
						ois.close();
					if(in != null)
						in.close();
					if(socket != null)
						socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public TransportMethod deal(TransportMethod to , String action){
		Bank bank = Bank.getInstance() ;
		Account myAccount = to.getMyAccount() ;
		
		//开户操作
		if("register".equals(action)){
			String password = myAccount.getPassword() ;
			String repassword = to.getRepassword() ;
			String name = myAccount.getName() ;
			String personId = myAccount.getPersonId() ;
			String email = myAccount.getEmail() ;
			int type = myAccount.getType() ;
			Account a = null ;
			synchronized(bank){
				a = bank.register(password, repassword, name, personId, email, type) ;
			}
			to.setMyAccount(a);
		}
		
		//登录操作
		if("login".equals(action)){
			Account a = null ;
			synchronized(bank){
				a = bank.login(myAccount.getId(), myAccount.getPassword()) ;
			}
			to.setMyAccount(a);
		}
		
		//存款
		if("deposit".equals(action)){
			Account a = null ;
			synchronized(bank){
				a = bank.deposit(myAccount.getId(), to.getMoney()) ;
			}
			to.setMyAccount(a);
		}
		
		//取款
		if("withdraw".equals(action)){
			Account a = null ;
			synchronized(bank){
				a = bank.withdraw(myAccount.getId(), myAccount.getPassword() , to.getMoney()) ;
			}
			to.setMyAccount(a);
		}
		
		//透支
		if("updateCeiling".equals(action)){
			Account a = null ;
			synchronized(bank){
				a = bank.updateCeiling(myAccount.getId(),myAccount.getPassword() , to.getMoney()) ;
			}
			to.setMyAccount(a); 
		}
		
		//贷款
		if("requestLoan".equals(action)){
			Account a = null ;
			synchronized(bank){
				a = bank.requestLoan(myAccount.getId(), to.getMoney()) ;
				to.setMyAccount(a); 
			}
		}
		
		//还贷
		if("payLoan".equals(action)){
			Account a = null ;
			synchronized(bank){
				a = bank.payLoan(myAccount.getId(), to.getMoney()) ;
				to.setMyAccount(a); 
			}
		}
		
		//转账
		if("transfer".equals(action)){
			Account toAccount = to.getToAccount() ;
			boolean bl = false ;
			synchronized(bank){
				bl = bank.transfer(myAccount.getId(), myAccount.getPassword(), toAccount.getId(), to.getMoney()) ;
			}
			if(bl){
				to.setMyAccount(bank.getAccountById(myAccount.getId()));
			}else{
				to.setMyAccount(null);
			}
		}
		
		//根据ID查询账户
		if("searchById".equals(action)){
			Account a = null ;
			synchronized (bank) {
				a = bank.getAccountById(myAccount.getId()) ;
			}
			to.setMyAccount(a);
		}
		
		//更多功能...
		return to ;
	}
}

