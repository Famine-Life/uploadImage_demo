package service;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
/*
 * 服务端
 */
public class Server extends Thread {
	private ServerSocket serverSocket = null; 
	public Server(ServerSocket serverScoket){ 
		try { 
			if(null == serverSocket){ 
				this.serverSocket = new ServerSocket(8888); 
				System.out.println("socket start----------------"); 
			} 
		} catch (Exception e) { 
			System.out.println("Server创建socket服务出错"); 
			e.printStackTrace(); 
		} 
	} 

	public void run(){ 
	while(!this.isInterrupted()){ 
		try { 
			Socket socket = serverSocket.accept(); 
			//创建一个新的线程
			ServerThread st=new ServerThread(socket);
			//启动线程
			st.start();
			socket.setSoTimeout(30000); 
		}catch (Exception e) { 
			e.printStackTrace(); 
		} 
	} 
	} 

	public void closeSocketServer()
	{ 
		try 
		{ 
			if(null!=serverSocket && !serverSocket.isClosed()) 
			{ 
				serverSocket.close(); 
			} 
		} 
		catch (IOException e) 
		{ 
			// TODO Auto-generated catch block 
			e.printStackTrace(); 
		} 
	} 
}

