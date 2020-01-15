package com.lutianqi.UI;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.lutianqi.Util.ATMClient;
import com.lutianqi.model.Account;
import com.lutianqi.model.SavingAccount;
import com.lutianqi.model.TransportMethod;

public class LoginPanel extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private JTextField IdtextField;
	
	private JPasswordField passwordField;
	
	private ATMClient client ;
	
	/**
	 * Create the frame.
	 */
	public LoginPanel() {
		
		client = new ATMClient();
		
		setTitle("ATM Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 382, 367);
		setLocationRelativeTo(getOwner());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel Idlabel = new JLabel("\u7528\u6237\u8D26\u53F7\uFF1A");
		Idlabel.setFont(new Font("宋体", Font.BOLD, 18));
		Idlabel.setBounds(45, 59, 102, 49);
		contentPane.add(Idlabel);
		
		JLabel passwordlabel = new JLabel("\u7528\u6237\u5BC6\u7801\uFF1A");
		passwordlabel.setFont(new Font("宋体", Font.BOLD, 18));
		passwordlabel.setBounds(45, 118, 112, 65);
		contentPane.add(passwordlabel);
		
		IdtextField = new JTextField();
		IdtextField.addActionListener(this);
		IdtextField.setBounds(167, 72, 144, 31);
		contentPane.add(IdtextField);
		IdtextField.setColumns(10);
		
		JButton submitbutton = new JButton("\u786E\u8BA4");
		submitbutton.addActionListener(this);
		submitbutton.setFont(new Font("宋体", Font.BOLD, 15));
		submitbutton.setBounds(64, 208, 102, 49);
		contentPane.add(submitbutton);
		
		JButton backbutton = new JButton("\u53D6\u6D88");
		backbutton.addActionListener(this);
		backbutton.setFont(new Font("宋体", Font.BOLD, 15));
		backbutton.setBounds(182, 208, 102, 49);
		contentPane.add(backbutton);
		
//		JButton I18button = new JButton("中英切换");
//		I18button.addActionListener(this);
//		I18button.setFont(new Font("宋体", Font.BOLD, 10));
//		I18button.setBounds(250, 15, 80, 50);
//		contentPane.add(I18button);
		
		passwordField = new JPasswordField();
		passwordField.addActionListener(this);
		passwordField.setBounds(167, 132, 144, 31);
		contentPane.add(passwordField);
		
	}

	public void actionPerformed(ActionEvent e) {
		
		String action = e.getActionCommand();
		
		if ("确认".equals(action)) {
			String id = IdtextField.getText();
			String password = "";
			for (char c : passwordField.getPassword())
				password += c;
			Account a = new SavingAccount("", "", "", "", 0);
			a.setId(Long.parseLong(id));
			a.setPassword(password);
			TransportMethod to = new TransportMethod("login", a, null);
			client.sendMsg(to);
			to = client.receiveMsg();
			a = to.getMyAccount();
			if (a == null) {
				JOptionPane.showMessageDialog(null, "账号或密码错误");
			}
			if (a != null) {
				BusinessPanel bp = new BusinessPanel(a, client);
				bp.setVisible(true);
				this.setVisible(false);
			}
		}
		
		if ("取消".equals(action)) {
			TransportMethod to = new TransportMethod("exit", null, null);
			client.sendMsg(to);
			client.close();
			setVisible(false);
			MainPanel mp = new MainPanel();
			mp.setVisible(true);
		}
//		
//		if("中英切换".equals(action))
//		{
//			Properties p=new Properties();
//			FileReader fr;
//			try
//			{
//				fr = new FileReader("./english.properties");
//				p.load(fr);
//			}
//			catch (FileNotFoundException e1)
//			{
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			catch (IOException e1)
//			{
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			
//		}
	}

}
