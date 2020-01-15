package com.lutianqi.model;

import java.io.Serializable;

public abstract class Account implements Serializable
{
	
	private static final long serialVersionUID = 1L;
		/**
		 * 账户号码
		 */
		private long id ;
		/**
		 * 账户密码
		 */
		private String password ;
		/**
		 * 真实姓名
		 */
		private String name ;
		/**
		 * 身份证号码
		 */
		private String personId ;
		/**
		 * 客户的电子邮件
		 */
		private String email ;
		/**
		 * 账户余额
		 */
		private double balance ;
		
		/**
		 * 账户类型
		 * 0 – 储蓄账户  1 – 信用账户 2 – 可贷款储蓄账户 3– 可贷款信用账户
		 */
		private int type ;
		/**
		 * 无参构造方法
		 */
		public Account() {
		}
		/**
		 * 有参构造方法
		 * @param id
		 * @param password
		 * @param name
		 * @param personId
		 * @param email
		 * @param balance
		 */
		public Account( String password, String name, String personId,
				String email, int type) {
			this.id = Id.createid();
			this.password = password;
			this.name = name;
			this.personId = personId;
			this.email = email;
			this.type = type ;
			this.balance = 0;
		}
		
		//set get 方法
		public long getId() {
			return id ;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPersonId() {
			return personId;
		}
		public void setPersonId(String personId) {
			this.personId = personId;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public double getBalance() {
			return balance;
		}
		public void setBalance(double balance) {
			this.balance = balance;
		}
		
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		/**
		 * 存款方法
		 * @param money
		 * @return
		 */
		public final  Account deposit(double money){
			this.setBalance(this.getBalance()+money);
			return this ;
		}
		
		/**
		 * 取款方法
		 * @param money
		 * @return
		 */
		public abstract  Account withdraw(double money) ;
		
		@Override
		public String toString() {
			return "Account [id=" + id + ", password=" + password + ", name="
					+ name + ", personId=" + personId + ", email=" + email
					+ ", balance=" + balance + ", type=" + type + "]";
		}
}
