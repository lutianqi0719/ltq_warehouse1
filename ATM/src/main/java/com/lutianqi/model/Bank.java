package com.lutianqi.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.lutianqi.exception.ATMException;
import com.lutianqi.exception.LoginException;
import com.lutianqi.exception.RegisterException;

public class Bank
{
	private static Bank instance = null;
	
	  //当前所有的账户对象的信息
	 
	private List<Account> accounts;

	private Bank()
	{
		accounts = new ArrayList<Account>();

		System.out.println("Bank-->" + accounts.size());
		accounts = this.getFromFile();
	}

	
	 //将Bank类作成单例。 获取Bank对象
	 
	public static Bank getInstance()
	{
		if (null == instance)
		{
			synchronized (Bank.class)
			{
				if (instance == null)
				{
					instance = new Bank();
				}
			}
		}
		return instance;
	}

	public void print()
	{
		for (int i = 0; i < accounts.size(); i++)
		{
			System.out.println(accounts.get(i));
		}
	}

	/**
	 * 当前账户数量
	 * 
	 * @return
	 */
	public int getIndex()
	{
		return accounts.size();
	}

	/**
	 * 用户开户(register)
	 * 
	 * @param id
	 * @param password
	 * @param repassword
	 * @param name
	 * @param personID
	 * @param email
	 * @param type
	 * @return
	 */
	public Account register(String password, String repassword, String name, String personID, String email, int type)
	{
		// 项目需求规定账户类型：0 – 储蓄账户 1 – 信用账户 2 – 可贷款储蓄账户 3– 可贷款信用账户
		Account account = null;
		System.out.println(password+"\t"+password+"\t"+name+"\t"+personID+"\t"+email);
		boolean bl = false;
		RegisterException re = new RegisterException("开户异常");
		CheckEmail iv = new CheckEmail();
		if (!iv.checkemail(email))
		{ // 验证email
			try
			{
				type = -1;
				throw (re);
			}
			catch (RegisterException e)
			{
				e.printStackTrace();
			}
		}
		if (!password.equals(repassword))
		{ // 验证密码
			try
			{
				type = -1;
				throw (re);
			}
			catch (RegisterException e)
			{
				e.printStackTrace();
			}
		}
//		if(personID.length()!=18){ //验证身份证
//			try {
//				throw(re) ;
//			} catch (RegisterException e) {
//				e.printStackTrace();
//			}
//		}
		if (type == 0)
		{
			account = new SavingAccount(password, name, personID, email, type);
			bl = true;
		}
		else if (type == 1)
		{
			account = new CreditAccount(password, name, personID, email, type);
			bl = true;
		}
		else if (type == 2)
		{
			account = new LoanSavingAccount(password, name, personID, email, type);
			bl = true;
		}
		else if (type == 3)
		{
			account = new LoanCreditAccount(password, name, personID, email, type);
			bl = true;
		}
		else
		{
			// 账户类型错误
			try
			{
				throw (re);
			}
			catch (RegisterException e)
			{
				e.printStackTrace();
			}
		}
		if (bl)
		{
			accounts.add(account);
			this.saveToDataBase();
		}
		else
		{
			account = null;
		}
		return account;
	}

	/**
	 * 用户登录(login)
	 * 
	 * @param id
	 * @param password
	 * @return
	 */
	public Account login(Long id, String password)
	{
		Account account = null;
		boolean bl = false;
		// 向服务器获取账户信息并验证
		for (int i = 0; i < accounts.size(); i++)
		{
			account = accounts.get(i);
			if (id == account.getId() && password.equals(account.getPassword()))
			{
				bl = true;
				break;
			}
		}
		if (!bl)
		{
			LoginException le = new LoginException("账号或密码错误");
			try
			{
				account = null;
				throw (le);
			}
			catch (LoginException e)
			{
				e.printStackTrace();
			}
		}
		return account;
	}

	/**
	 * 用户存款(deposit)
	 * 
	 * @param id
	 * @param money
	 * @return
	 */
	public Account deposit(Long id, double money)
	{
		Account account = null;
		ATMException ae = new ATMException("存款异常");
		boolean bl = false;
		// 获取当前账户
		for (int i = 0; i < accounts.size(); i++)
		{
			account = accounts.get(i);
			if (id == account.getId())
			{
				accounts.set(i, account.deposit(money));
				this.saveToDataBase();
				bl = true;
				break;
			}
		}
		if (!bl)
		{
			try
			{
				account = null;
				throw (ae);
			}
			catch (ATMException e)
			{
				e.printStackTrace();
			}
		}
		return account;
	}

	/**
	 * 用户取款(withdraw)
	 * 
	 * @param id
	 * @param password
	 * @param money
	 * @return
	 */
	public Account withdraw(Long id, String password, double money)
	{
		Account account = null;
		ATMException ae = new ATMException("取款异常");
		double balance = 0;
		boolean bl = false;
		// 获取当前账户
		for (int i = 0; i < accounts.size(); i++)
		{
			account = accounts.get(i);
			balance = account.getBalance();
			if (id == account.getId() && password.equals(account.getPassword()))
			{
				if (account.getType() == 0)
				{
					SavingAccount sa = (SavingAccount) accounts.get(i);
					accounts.set(i, sa.withdraw(money));
					this.saveToDataBase();
					account = sa;
					bl = true;
				}
				else if (account.getType() == 1)
				{
					CreditAccount ca = (CreditAccount) accounts.get(i);
					accounts.set(i, ca.withdraw(money));
					this.saveToDataBase();
					account = ca;
					bl = true;
				}
				else if (account.getType() == 2)
				{
					LoanSavingAccount lsa = (LoanSavingAccount) accounts.get(i);
					accounts.set(i, lsa.withdraw(money));
					this.saveToDataBase();
					account = lsa;
					bl = true;
				}
				else if (account.getType() == 3)
				{
					LoanCreditAccount lca = (LoanCreditAccount) accounts.get(i);
					accounts.set(i, lca.withdraw(money));
					this.saveToDataBase();
					account = lca;
					bl = true;
				}
				else
				{
					try
					{
						throw (ae);
					}
					catch (ATMException e)
					{
						e.printStackTrace();
					}
				}
				break;
			}
		}
		if (balance == account.getBalance()) account = null;
		if (!bl)
		{
			try
			{
				account = null;
				throw (ae);
			}
			catch (ATMException e)
			{
				e.printStackTrace();
			}
		}
		return account;
	}

	/**
	 * 设置透支额度(updateCeiling)
	 * 
	 * @param id
	 * @param password
	 * @param money
	 * @return
	 */
	public Account updateCeiling(Long id, String password, double money)
	{
		Account account = null;
		ATMException ae = new ATMException("设置透支额度异常");
		boolean bl = false;
		for (int i = 0; i < accounts.size(); i++)
		{
			account = accounts.get(i);
			if (id == account.getId() && password.equals(account.getPassword()))
			{
				if (account.getType() == 1)
				{
					CreditAccount ca = (CreditAccount) accounts.get(i);
					ca.setCeiling(money);
					accounts.set(i, ca);
					this.saveToDataBase();
					account = ca;
					bl = true;
				}
				else if (account.getType() == 3)
				{
					LoanCreditAccount lca = (LoanCreditAccount) accounts.get(i);
					lca.setCeiling(money);
					accounts.set(i, lca);
					this.saveToDataBase();
					account = lca;
					bl = true;
				}
				else
				{
					try
					{
						throw (ae); // 不是信用账户
					}
					catch (ATMException e)
					{
						e.printStackTrace();
					}
				}
				break;
			}
		}
		if (!bl)
		{
			try
			{
				account = null;
				throw (ae);
			}
			catch (ATMException e)
			{
				e.printStackTrace();
			}
		}
		return account;
	}

	
	// 转账功能(transfer)
	
	public boolean transfer(Long from, String passwordFrom, Long to, double money)
	{
		boolean bl = false;
		ATMException ae = new ATMException("转账异常");
		if (this.withdraw(from, passwordFrom, money) != null && this.deposit(to, money) != null)
		{
			this.saveToDataBase();
			bl = true;
		}
		else
		{
			try
			{
				throw (ae);
			}
			catch (ATMException e)
			{
				e.printStackTrace();
			}
		}
		return bl;
	}

	/**
	 * 统计银行所有账户余额总数
	 * 
	 * @return
	 */
	public double getBalanceSum()
	{
		double sum = 0;
		for (int i = 0; i < accounts.size(); i++)
		{
			Account account = accounts.get(i);
			sum += account.getBalance();
		}
		return sum;
	}

	/**
	 * 统计所有信用账户透支额度总数
	 * 
	 * @return
	 */
	public double getCeilingSum()
	{
		double sum = 0;
		for (Account account : accounts)
		{
			if (account.getType() == 1)
			{
				CreditAccount account0 = (CreditAccount) account;
				sum += account0.getCeiling();
			}
		}
		return sum;
	}

	/**
	 * 贷 款(requestLoan)
	 * 
	 * @return
	 */
	public Account requestLoan(Long id, double money)
	{
		Account account = null;
		ATMException ae = new ATMException("贷款异常");
		boolean bl = false;
		for (int i = 0; i < accounts.size(); i++)
		{
			account = accounts.get(i);
			if (id.longValue() == account.getId())
			{
				if (account.getType() == 2)
				{
					LoanSavingAccount lsa = (LoanSavingAccount) accounts.get(i);
					accounts.set(i, lsa.requestLoan(money));
					this.saveToDataBase();
					account = lsa;
					bl = true;
				}
				else if (account.getType() == 3)
				{
					LoanCreditAccount lca = (LoanCreditAccount) accounts.get(i);
					accounts.set(i, lca.requestLoan(money));
					this.saveToDataBase();
					account = lca;
					bl = true;
				}
				else
				{
					try
					{
						throw (ae);
					}
					catch (ATMException e)
					{
						e.printStackTrace();
					}
				}
				break;
			}
		}
		if (!bl) account = null;
		return account;
	}

	
	 // 还贷款(requestLoan)
	
	public Account payLoan(Long id, double money)
	{
		Account account = null;
		ATMException ae = new ATMException("还贷异常");
		boolean bl = false;
		for (int i = 0; i < accounts.size(); i++)
		{
			account = accounts.get(i);
			if (id.longValue() == account.getId())
			{
				if (account.getType() == 2)
				{
					LoanSavingAccount lsa = (LoanSavingAccount) accounts.get(i);
					accounts.set(i, lsa.payLoan(money));
					this.saveToDataBase();
					bl = true;
					account = lsa;
				}
				else if (account.getType() == 3)
				{
					LoanCreditAccount lca = (LoanCreditAccount) accounts.get(i);
					accounts.set(i, lca.payLoan(money));
					this.saveToDataBase();
					account = lca;
					bl = true;
				}
				else
				{
					try
					{
						throw (ae);
					}
					catch (ATMException e)
					{
						e.printStackTrace();
					}
				}
				break;
			}
		}
		if (!bl) account = null;
		return account;
	}

	
	 // 统计所有账户贷款的总额(totoal)
	 
	public double total()
	{
		double sum = 0;
		for (Account account : accounts)
		{
			if (account.getType() == 2)
			{
				sum += ((LoanSavingAccount) account).getLoanAmount();
			}
			if (account.getType() == 3)
			{
				sum += ((LoanCreditAccount) account).getLoanAmount();
			}
		}
		return sum;
	}

	/**
	 * 为Bank类添加一个方法，能够打印所有用户的总资产排名（提高部分） 说明: 1）、一个用户可能会有多个账号,以身份证号为准.
	 * 2）、总资产指多个账户余额的总和,不需要考虑贷款账户的贷
	 */
	public void printSortAllAccount()
	{
		// hm 记录了 身份证号，以及同一证件下的所有账户总金额
		HashMap<String, Number> hm = new HashMap<String, Number>();
		for (Account account : accounts)
		{
			String personId = account.getPersonId();
			double balance = account.getBalance();
			if (hm.containsKey(personId))
			{
				double sum = (Double) hm.get(personId);
				hm.put(personId, sum + balance);
			}
			else
			{
				hm.put(personId, balance);
			}
		}
		// 将身份证放入数组中排序
		String[] personIds = new String[hm.size()];
		Set<String> set = hm.keySet();
		Iterator<String> it = set.iterator();
		int i = 0;
		while (it.hasNext())
		{
			personIds[i++] = it.next();
		}
		for (i = 0; i < personIds.length; i++)
		{
			for (int j = 0; j < personIds.length - 1; j++)
			{
				double a = (Double) hm.get(String.valueOf(personIds[j]));
				double b = (Double) hm.get(personIds[j + 1]);
				if ((a - 0) < (b - 0))
				{
					String t = personIds[j];
					personIds[j] = personIds[j + 1];
					personIds[j + 1] = t;
				}
			}
		}
		System.out.println("personId\t\tmoney");
		for (int t = 0; t < personIds.length; t++) System.out.println(personIds[t] + "\t\t" + hm.get(personIds[t]));
	}

	/**
	 * 将数据保存到文件中
	 */
	public void saveToDataBase(){
		FileOutputStream fos = null ;
		ObjectOutputStream oos  = null ;
		try {
			fos = new FileOutputStream("src/main/java/com/lutianqi/model/accounts.txt") ;
			oos = new ObjectOutputStream(fos) ;
			oos.writeObject(accounts); 
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(oos != null){
					oos.close();
				}
				if(oos != null){
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	// 读取信息到内存
	 
	@SuppressWarnings("unchecked")
	public List<Account> getFromFile()
	{
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try
		{
			fis = new FileInputStream("src/main/java/com/lutianqi/model/accounts.txt");

			System.out.println("fis-->" + fis);
			ois = new ObjectInputStream(fis);
			try
			{
				accounts = (List<Account>) ois.readObject();

				for (Account ac : accounts)
				{
					System.out.println(ac.getName());
				}

				System.out.println(accounts);
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (ois != null) ois.close();
				if (fis != null) fis.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return accounts;
	}

	
	 // 根据ID查询用户名
	
	public String getNameById(Long id)
	{
		String nameStr = "";
		for (Account a : accounts)
		{
			if (id == a.getId())
			{
				nameStr = a.getName();
				break;
			}
		}
		int len = nameStr.length();
		String name = "";
		name += nameStr.charAt(0);
		for (int i = 0; i < len - 1; i++)
		{
			name += "*";
		}
		return name;
	}

	public Account getAccountById(Long id)
	{
		Account ai = null;
		for (Account a : accounts)
		{
			if (id == a.getId())
			{
				ai = a;
				break;
			}
		}
		return ai;
	}
}
