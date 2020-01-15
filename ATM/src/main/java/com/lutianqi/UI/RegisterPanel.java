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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.lutianqi.model.Account;
import com.lutianqi.model.CreditAccount;
import com.lutianqi.model.LoanCreditAccount;
import com.lutianqi.model.LoanSavingAccount;
import com.lutianqi.model.SavingAccount;
import com.lutianqi.model.TransportMethod;
import com.lutianqi.Util.ATMClient;

public class RegisterPanel extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private JTextField nametextField;
	
	private JTextField peisonIdtextField;
	
	private JTextField emailtextField;
	
	private JPasswordField passwordField;
	
	private JPasswordField repasswordField;
	
	private JComboBox<String> accountTypecomboBox ;
	
	private ATMClient client ;

	/**
	 * Create the frame.
	 */
	public RegisterPanel() {
		
		client = new ATMClient();
		
		setTitle("ATM Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 382, 367);
		setLocationRelativeTo(getOwner());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel accountTypelabel = new JLabel("\u8D26\u6237\u7C7B\u578B\uFF1A");
		accountTypelabel.setFont(new Font("宋体", Font.BOLD, 15));
		accountTypelabel.setBounds(51, 22, 86, 30);
		contentPane.add(accountTypelabel);
		
		JLabel namelabel = new JLabel("\u7528\u6237\u540D\uFF1A");
		namelabel.setFont(new Font("宋体", Font.BOLD, 15));
		namelabel.setBounds(51, 62, 73, 39);
		contentPane.add(namelabel);
		
		JLabel passwordlabel = new JLabel("\u5BC6\u7801\uFF1A");
		passwordlabel.setFont(new Font("宋体", Font.BOLD, 15));
		passwordlabel.setBounds(51, 111, 77, 29);
		contentPane.add(passwordlabel);
		
		JLabel repasswordlabel = new JLabel("\u786E\u8BA4\u5BC6\u7801\uFF1A");
		repasswordlabel.setFont(new Font("宋体", Font.BOLD, 15));
		repasswordlabel.setBounds(51, 150, 86, 37);
		contentPane.add(repasswordlabel);
		
		JLabel personIdlabel = new JLabel("\u8EAB\u4EFD\u8BC1\u53F7\uFF1A");
		personIdlabel.setFont(new Font("宋体", Font.BOLD, 15));
		personIdlabel.setBounds(51, 197, 86, 30);
		contentPane.add(personIdlabel);
		
		JLabel emaillabel = new JLabel("\u7535\u5B50\u90AE\u7BB1\uFF1A");
		emaillabel.setFont(new Font("宋体", Font.BOLD, 15));
		emaillabel.setBounds(51, 237, 86, 30);
		contentPane.add(emaillabel);
		
		accountTypecomboBox = new JComboBox<String>();
		accountTypecomboBox.setFont(new Font("宋体", Font.BOLD, 13));
		accountTypecomboBox.addItem("\u50A8\u84C4\u8D26\u6237");
		accountTypecomboBox.addItem("\u4FE1\u7528\u8D26\u6237");
		accountTypecomboBox.addItem("\u53EF\u8D37\u6B3E\u50A8\u84C4\u8D26\u6237");
		accountTypecomboBox.addItem("\u53EF\u8D37\u6B3E\u4FE1\u7528\u8D26\u6237");
		accountTypecomboBox.setToolTipText("");
		accountTypecomboBox.setBounds(162, 22, 135, 30);
		contentPane.add(accountTypecomboBox);
		accountTypecomboBox.addActionListener(this);
		
		
		nametextField = new JTextField();
		nametextField.addActionListener(this);
		nametextField.setBounds(162, 71, 135, 21);
		contentPane.add(nametextField);
		nametextField.setColumns(10);
		
		peisonIdtextField = new JTextField();
		peisonIdtextField.addActionListener(this);
		peisonIdtextField.setColumns(10);
		peisonIdtextField.setBounds(162, 202, 135, 21);
		contentPane.add(peisonIdtextField);
		
		emailtextField = new JTextField();
		emailtextField.addActionListener(this);
		emailtextField.setColumns(10);
		emailtextField.setBounds(162, 242, 135, 21);
		contentPane.add(emailtextField);
		
		passwordField = new JPasswordField();
		passwordField.addActionListener(this);
		passwordField.setBounds(162, 115, 135, 21);
		contentPane.add(passwordField);
		
		repasswordField = new JPasswordField();
		repasswordField.addActionListener(this);
		repasswordField.setBounds(162, 158, 135, 21);
		contentPane.add(repasswordField);
		
		JButton submitbutton = new JButton("\u63D0\u4EA4");
		submitbutton.addActionListener(this);
		submitbutton.setBounds(84, 277, 93, 30);
		contentPane.add(submitbutton);
		
		JButton backbutton = new JButton("\u8FD4\u56DE");
		backbutton.setBounds(187, 277, 93, 30);
		backbutton.addActionListener(this);
		contentPane.add(backbutton);
	}


	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		String action = e.getActionCommand();
		
		if("提交".equals(action)){
			//获取注册信息
			int type = accountTypecomboBox.getSelectedIndex() ;
			String name = nametextField.getText() ;
			String password = "" ;
			for(char c : passwordField.getPassword())
				password+=c ;
			String repassword = "" ;
			for(char c : repasswordField.getPassword())
				repassword+=c ;
			String personId = peisonIdtextField.getText() ;
			String email = emailtextField.getText() ;
			
			//创建对象并发生给服务器
			if (type == 0) {
				SavingAccount a = new SavingAccount(password, name, personId,
						email, type);
				TransportMethod to = new TransportMethod("register", a, null);
				to.setRepassword(repassword);
				client.sendMsg(to);
			} else if (type == 1) {
				CreditAccount a = new CreditAccount(password, name, personId,
						email, type);
				TransportMethod to = new TransportMethod("register", a, null);
				to.setRepassword(repassword);
				client.sendMsg(to);
			} else if (type == 2) {
				LoanSavingAccount a = new LoanSavingAccount(password, name,
						personId, email, type);
				TransportMethod to = new TransportMethod("register", a, null);
				to.setRepassword(repassword);
				client.sendMsg(to);
			} else {
				LoanCreditAccount a = new LoanCreditAccount(password, name,
						personId, email, type);
				TransportMethod to = new TransportMethod("register", a, null);
				to.setRepassword(repassword);
				client.sendMsg(to);
			}
			//接收服务器信息
			TransportMethod rto = client.receiveMsg() ;
			Account a = rto.getMyAccount() ;
			//处理结果
			if(a == null){
				JOptionPane.showMessageDialog(null, "开户失败");
			}else{
				int i = JOptionPane.showConfirmDialog(null, "开户成功，是否继续创建") ;
				if(i==1){
					setVisible(false) ;
					BusinessPanel bp = new BusinessPanel(a , client) ;
					bp.setVisible(true);
				}
			}
		} 
		
		if("返回".equals(action)){
			this.setVisible(false);
			MainPanel mp = new MainPanel() ;
			mp.setVisible(true);
			TransportMethod exit = new TransportMethod("exit" , null , null) ;
			client.sendMsg(exit) ;
			client.close();
		}
	}
	

}

