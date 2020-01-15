package com.lutianqi.Util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.lutianqi.model.Account;
import com.lutianqi.model.TransportMethod;

public class ATMClient {

	private Socket socket = null;

	private InputStream in = null;

	private OutputStream out = null;
	
	private ObjectOutputStream oos = null ;
	
	private ObjectInputStream ois = null ;
	

	public ATMClient() {
		try {
			socket = new Socket("127.0.0.1", 9000); // 127.0.0.1
			in = socket.getInputStream();
			out = socket.getOutputStream();
			oos = new ObjectOutputStream(out) ;
			ois = new ObjectInputStream(in) ;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 向服务器发送信息
	 * @param send_message
	 * @return
	 */
	public boolean sendMsg(TransportMethod to) {
		boolean bl = false;
		try {
 			oos.writeObject(to);
 			oos.reset();
 			oos.flush();
			bl = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bl;
	}

	/**
	 * 读取服务器发来的内容
	 * @return
	 */
	public TransportMethod receiveMsg() {
		TransportMethod to = null ;
		try {
			try {
				Account a = null ;
				Object obj = ois.readObject() ;
				if(obj == null)
					a = null ;
				else
					a = (Account)obj ;
				to = new TransportMethod("", a, null) ;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return to ;
	}

	/**
	 * 关闭当前账户的连接
	 */
	public void close() {
		try {
			if (out != null)
				out.close();
			if (in != null)
				in.close();
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
