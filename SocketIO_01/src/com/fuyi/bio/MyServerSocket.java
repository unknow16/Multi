package com.fuyi.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServerSocket {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(12345);
			System.out.println("服务端正在监听");
			
			MyHandlerThreadPool myHandlerThreadPool = new MyHandlerThreadPool(50, 100);
			
			while(true) {
				Socket socket = serverSocket.accept();
				myHandlerThreadPool.execute(new MyServerHandler(socket));
			}
			//new Thread(new MyServerHandler(socket)).start();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			serverSocket = null;
		}
	}
}
