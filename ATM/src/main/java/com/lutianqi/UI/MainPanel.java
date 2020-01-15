package com.lutianqi.UI;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



public class MainPanel extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPanel frame = new MainPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainPanel() {
		setTitle("ATM Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 382, 367);
		setLocationRelativeTo(getOwner());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblicbcAtm = new JLabel("  ATM  ");
		lblicbcAtm.setFont(new Font("宋体", Font.BOLD, 26));
		lblicbcAtm.setBounds(140, 43, 262, 44);
		contentPane.add(lblicbcAtm);
		
		JButton register_button = new JButton("\u6CE8\u518C");
		register_button.addActionListener(this) ;
		register_button.setBounds(77, 153, 83, 32);
		contentPane.add(register_button);
		
		JButton login_button = new JButton("\u767B\u5F55");
		login_button.addActionListener(this);
		login_button.setBounds(218, 153, 83, 32);
		contentPane.add(login_button);
	}

	public void actionPerformed(ActionEvent e) {
		
		String action = e.getActionCommand() ;
		
		if("注册".equals(action)){
			RegisterPanel rp = new RegisterPanel() ;
			this.setVisible(false);
			rp.setVisible(true) ;
		}
		
		if("登录".equals(action)){
			LoginPanel lp = new LoginPanel() ;
			this.setVisible(false);
			lp.setVisible(true) ;
		}
	}
}

