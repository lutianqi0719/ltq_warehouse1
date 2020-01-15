package com.lutianqi.UI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.lutianqi.model.Account;
import com.lutianqi.model.CreditAccount;
import com.lutianqi.model.LoanCreditAccount;
import com.lutianqi.model.LoanSavingAccount;
import com.lutianqi.model.SavingAccount;
import com.lutianqi.model.TransportMethod;
import com.lutianqi.Util.ATMClient;



public class BusinessPanel extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField acttextField;
	private JComboBox<String> actBox;

	private Account account;
	private JLabel balanceL;
	private JLabel cellingL ;
	private JLabel loanL ;
	private ATMClient client ;

	/**
	 * Create the frame.
	 */
	public BusinessPanel(Account account , ATMClient client) {
		
		this.client = client ;
		this.account = account;
		int type = account.getType();
		
		setTitle("ATM Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 382, 367);
		setLocationRelativeTo(getOwner());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel accountIdlabel = new JLabel("\u8D26   \u6237\uFF1A");
		accountIdlabel.setFont(new Font("微软雅黑", Font.BOLD, 15));
		accountIdlabel.setBounds(69, 30, 67, 33);
		contentPane.add(accountIdlabel);

		JLabel balancelabel = new JLabel("\u4F59   \u989D\uFF1A");
		balancelabel.setFont(new Font("微软雅黑", Font.BOLD, 15));
		balancelabel.setBounds(69, 98, 67, 33);
		contentPane.add(balancelabel);

		JLabel namelabel = new JLabel("\u59D3   \u540D\uFF1A");
		namelabel.setFont(new Font("微软雅黑", Font.BOLD, 15));
		namelabel.setBounds(69, 73, 67, 15);
		contentPane.add(namelabel);

		JLabel cellinglabel = new JLabel("\u4FE1\u7528\u989D\u5EA6\uFF1A");
		cellinglabel.setFont(new Font("微软雅黑", Font.BOLD, 15));
		cellinglabel.setBounds(61, 141, 75, 15);
		contentPane.add(cellinglabel);

		JLabel loanlabel = new JLabel("\u8D37\u6B3E\u989D\uFF1A");
		loanlabel.setFont(new Font("微软雅黑", Font.BOLD, 15));
		loanlabel.setBounds(69, 183, 67, 15);
		contentPane.add(loanlabel);

		String id = String.valueOf(account.getId());
		JLabel idL = new JLabel(id);
		idL.setFont(new Font("微软雅黑", Font.BOLD, 15));
		idL.setBounds(165, 40, 191, 15);
		contentPane.add(idL);

		String name = account.getName();
		JLabel nameL = new JLabel(name);
		nameL.setFont(new Font("微软雅黑", Font.BOLD, 15));
		nameL.setBounds(163, 73, 160, 15);
		contentPane.add(nameL);

		balanceL = new JLabel(String.valueOf(account.getBalance()));
		balanceL.setFont(new Font("微软雅黑", Font.BOLD, 15));
		balanceL.setBounds(165, 108, 158, 15);
		contentPane.add(balanceL);

		//JLabel cellingL
		if (type == 1) {
			CreditAccount ca = (CreditAccount) account;
			cellingL = new JLabel(String.valueOf(ca.getCeiling()));
		} else if (type == 3) {
			LoanCreditAccount lca = (LoanCreditAccount) account;
			cellingL = new JLabel(String.valueOf(lca.getCeiling()));
		} else {
			cellingL = new JLabel("0.0");
		}
		cellingL.setFont(new Font("微软雅黑", Font.BOLD, 15));
		cellingL.setBounds(165, 142, 158, 15);
		contentPane.add(cellingL);

		//	JLabel
		if (type == 2) {
			LoanSavingAccount lsa = (LoanSavingAccount) account;
			loanL = new JLabel(String.valueOf(lsa.getLoanAmount()));
		} else if (type == 3) {
			LoanCreditAccount lca = (LoanCreditAccount) account;
			loanL = new JLabel(String.valueOf(lca.getLoanAmount()));
		} else {
			loanL = new JLabel("0.0");
		}
		loanL.setFont(new Font("微软雅黑", Font.BOLD, 15));
		loanL.setBounds(165, 182, 158, 15);
		contentPane.add(loanL);

		actBox = new JComboBox<String>();
		actBox.addItem("存款");
		actBox.addItem("取款");
		if (type == 1 || type == 3)
			actBox.addItem("透支");
		if (type == 2 || type == 3) {
			actBox.addItem("贷款");
			actBox.addItem("还贷");
		}
		actBox.addItem("转账");
		actBox.setBounds(69, 229, 95, 21);
		actBox.addActionListener(this);
		contentPane.add(actBox);

		acttextField = new JTextField();
		acttextField.setBounds(165, 229, 109, 21);
		contentPane.add(acttextField);
		acttextField.addActionListener(this);
		acttextField.setColumns(10);

		JButton submitbutton = new JButton("\u63D0\u4EA4");
		submitbutton.addActionListener(this);
		submitbutton.setBounds(69, 260, 93, 38);
		contentPane.add(submitbutton);

		JButton backbutton = new JButton("\u8FD4\u56DE");
		backbutton.setBounds(181, 260, 93, 38);
		backbutton.addActionListener(this);
		contentPane.add(backbutton);
		
	}

	public void actionPerformed(ActionEvent e) {
		
		String action = e.getActionCommand();
		
		if ("提交".equals(action)) {
			//获取操作 例如：存款 转账 等
			String act = (String) actBox.getSelectedItem();
			
			//处理输入金钱
			String moneyStr = acttextField.getText();
			double money = 0;
			try {
				money = Double.valueOf(moneyStr);
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "请输入金额");
				act = "" ;
			}
			if(money < 0 ){
				JOptionPane.showMessageDialog(null, "输入的金额不能低于0");
				act = "" ;
			}
			//定义序列化对象进行信息传递
			TransportMethod to = null ;
			//判断操作
			if ("存款".equals(act)) {
				//判断账户类型 并 建立 序列化对象 To
				if (account.getType() == 0) {
					SavingAccount a = (SavingAccount) account;
					to = new TransportMethod("deposit", a, null);
				} else if (account.getType() == 1) {
					CreditAccount a = (CreditAccount) account;
					to = new TransportMethod("deposit", a, null);
				} else if (account.getType() == 2) {
					LoanSavingAccount a = (LoanSavingAccount) account;
					to = new TransportMethod("deposit", a, null);
				} else {
					LoanCreditAccount a = (LoanCreditAccount) account;
					to = new TransportMethod("deposit", a, null);
				}
				//判断金额数据 并返回处理结果
				if (money > 0) {
					to.setMoney(money);
					client.sendMsg(to) ;
					TransportMethod to1 = client.receiveMsg() ;
					Account a = to1.getMyAccount() ;
					balanceL.setText(String.valueOf(a.getBalance()));
					account = a ;
					JOptionPane.showMessageDialog(null, "存款成功");
				}else{
					JOptionPane.showMessageDialog(null, "操作失败");
				}
			}
			
			if ("取款".equals(act)) {
				//判断账户类型 并 建立 序列化对象 To
				if (account.getType() == 0) {
					SavingAccount a = (SavingAccount) account;
					to = new TransportMethod("withdraw", a, null);
				} else if (account.getType() == 1) {
					CreditAccount a = (CreditAccount) account;
					to = new TransportMethod("withdraw", a, null);
				} else if (account.getType() == 2) {
					LoanSavingAccount a = (LoanSavingAccount) account;
					to = new TransportMethod("withdraw", a, null);
				} else {
					LoanCreditAccount a = (LoanCreditAccount) account;
					to = new TransportMethod("withdraw", a, null);
				}
				//设置取款金额
				to.setMoney(money);
				//发送信息到服务器
				client.sendMsg(to) ;
				//接收服务器信息
				to = client.receiveMsg() ;
				//处理操作结果
				Account a = to.getMyAccount() ;
				if (a == null) {
					JOptionPane.showMessageDialog(null, "操作失败");
				}
				if (a != null) {
					balanceL.setText(String.valueOf(a.getBalance()));
					account = a ;
					JOptionPane.showMessageDialog(null, "取款成功");
				}
			}
			
			if ("透支".equals(act)) {
				// 判断账户类型 并 建立 序列化对象 To
				if (account.getType() == 0) {
					SavingAccount a = (SavingAccount) account;
					to = new TransportMethod("updateCeiling", a, null);
				} else if (account.getType() == 1) {
					CreditAccount a = (CreditAccount) account;
					to = new TransportMethod("updateCeiling", a, null);
				} else if (account.getType() == 2) {
					LoanSavingAccount a = (LoanSavingAccount) account;
					to = new TransportMethod("updateCeiling", a, null);
				} else {
					LoanCreditAccount a = (LoanCreditAccount) account;
					to = new TransportMethod("updateCeiling", a, null);
				}
				//设置透支金额
				to.setMoney(money);
				//发送序列化对象到服务器
				client.sendMsg(to);
				//接收服务器信息
				to = client.receiveMsg();
				//处理操作结果
				Account a = to.getMyAccount();
				if (a == null) {
					JOptionPane.showMessageDialog(null, "操作失败");
				}
				if (a != null) {
					if (1 == a.getType()) {
						CreditAccount ca = (CreditAccount) a;
						cellingL.setText(String.valueOf(ca.getCeiling()));
					}
					if (3 == a.getType()) {
						LoanCreditAccount lca = (LoanCreditAccount) a;
						cellingL.setText(String.valueOf(lca.getCeiling()));
					}
					account = a;
					JOptionPane.showMessageDialog(null, "设置透支额成功");
				}
			}
			
			if ("贷款".equals(act)) {
				//判断账户类型 并 建立 序列化对象 To
				if (account.getType() == 2) {
					LoanSavingAccount a = (LoanSavingAccount) account;
					to = new TransportMethod("requestLoan", a, null);
				} 
				if (account.getType() == 3) {
					LoanCreditAccount a = (LoanCreditAccount) account;
					to = new TransportMethod("requestLoan", a, null);
				}
				//设置贷款金额
				to.setMoney(money);
				//发送序列化对象到服务器
				client.sendMsg(to) ;
				//接收服务器信息
				to = client.receiveMsg() ;
				//处理操作结果
				Account a = to.getMyAccount() ;
				if (a == null) {
					JOptionPane.showMessageDialog(null, "操作失败");
				}
				if (a != null) {
					 if(a.getType() == 2){
					 LoanSavingAccount lsa = (LoanSavingAccount) a ; 
					 loanL.setText(String.valueOf(lsa.getLoanAmount()));
					 }
					 if(a.getType() == 3){
					 LoanCreditAccount lca = (LoanCreditAccount) a ;
					 loanL.setText(String.valueOf(lca.getLoanAmount()));
					 }
					 account = a ;
					JOptionPane.showMessageDialog(null, "贷款成功");
				}
			}
			
			if ("还贷".equals(act)) {
				//判断账户类型 并 建立 序列化对象 To
				if (account.getType() == 2) {
					LoanSavingAccount a = (LoanSavingAccount) account;
					to = new TransportMethod("payLoan", a, null);
				} 
				if (account.getType() == 3){
					LoanCreditAccount a = (LoanCreditAccount) account;
					to = new TransportMethod("payLoan", a, null);
				}
				//设置还贷金额
				to.setMoney(money);
				//发送序列化对象到服务器
				client.sendMsg(to) ;
				//接收服务器信息
				to = client.receiveMsg() ;
				//处理操作结果
				Account a = to.getMyAccount() ;
				if (a == null) {
					JOptionPane.showMessageDialog(null, "操作失败");
				}
				if (a != null) {
					if(a.getType() == 2){
						 LoanSavingAccount lsa = (LoanSavingAccount) a ;
						 loanL.setText(String.valueOf(lsa.getLoanAmount()));
						 balanceL.setText(String.valueOf(lsa.getBalance()));
						 }
						 if(a.getType() == 3){
						 LoanCreditAccount lca = (LoanCreditAccount) a ;
						 loanL.setText(String.valueOf(lca.getLoanAmount()));
						 balanceL.setText(String.valueOf(lca.getBalance()));
						 }
						 account = a ;
					JOptionPane.showMessageDialog(null, "还贷成功");
				}
			}
			
			if ("转账".equals(act)) {
				String idStr = JOptionPane.showInputDialog("请输入要转账的账户ID");
				if (idStr == null) {
					JOptionPane.showMessageDialog(null, "Id不能为空");
				} else {
					//获取转账账户的用户名
					SavingAccount searchAccount = new SavingAccount("", "", "", "", 0) ;
					searchAccount.setId(Long.valueOf(idStr));
					//数据打包 请求服务器
					to = new TransportMethod("searchById", searchAccount, null) ;
					client.sendMsg(to) ;
					//接收数据信息
					to = client.receiveMsg() ;
					String toName = to.getMyAccount().getName().charAt(0)+"" ;
					for(int i=0 ; i<to.getMyAccount().getName().length()-1 ; i++)
						toName += "*" ;
					int i = JOptionPane.showConfirmDialog(null, " 给  "+toName+"  转入" + money);
					//建立收款人信息
					SavingAccount toAccount = new SavingAccount("", "", "", "", 0) ;
					toAccount.setId(Long.valueOf(idStr));
					//判断账户类型 并 建立 序列化对象 To
					if (account.getType() == 0) {
						SavingAccount a = (SavingAccount) account;
						to = new TransportMethod("transfer", a, toAccount);
					} else if (account.getType() == 1) {
						CreditAccount a = (CreditAccount) account;
						to = new TransportMethod("transfer", a, toAccount);
					} else if (account.getType() == 2) {
						LoanSavingAccount a = (LoanSavingAccount) account;
						to = new TransportMethod("transfer", a, toAccount);
					} else {
						LoanCreditAccount a = (LoanCreditAccount) account;
						to = new TransportMethod("transfer", a, toAccount);
					}
					//设置转账金额
					to.setMoney(money);
					//发送信息到服务器
					client.sendMsg(to);
					//处理操作结果
					if (i == 0) {
						to = client.receiveMsg();
						Account a = to.getMyAccount();
						if (a == null) {
							JOptionPane.showMessageDialog(null, "转账失败！");
						} else {
							balanceL.setText(String.valueOf(a.getBalance()));
							account = a ;
							JOptionPane.showMessageDialog(null, "转账成功！");
						}
					}
				}
			}
			
		}
		
		if ("返回".equals(action)) {
			account = null;
			TransportMethod to = new TransportMethod("exit", null, null) ;
			client.sendMsg(to) ;
			client.close();
			LoginPanel lp = new LoginPanel();
			this.setVisible(false);
			lp.setVisible(true);
		}
	}
	
	
}
