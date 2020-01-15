package com.lutianqi.Util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ATMServer {

	public static void main(String[] args) {
		ServerSocket ss = null ;
		Socket socket = null ;
		try {
			ss = new ServerSocket(9000) ;
			System.out.println("服务已启动。。。");
			while(true){
				socket = ss.accept() ;
				System.out.println(socket.getInetAddress()+" 已接入服务器！");
				new ATMServerThread(socket).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
				try {
					if(socket != null){
						socket.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}

