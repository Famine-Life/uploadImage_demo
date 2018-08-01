package service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

//自定义类实现ServletContextListener接口，为了让socket随着服务器启动
public class ServerSocketListener implements ServletContextListener {
	//socket server 线程 
	private Server socketThread; 
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		if(null!=socketThread && !socketThread.isInterrupted()) 
		{ 
		socketThread.closeSocketServer(); 
		socketThread.interrupt(); 
		} 
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		if(null==socketThread) 
		{ 
		//新建线程类 
		socketThread=new Server(null); 
		//启动线程 
		socketThread.start(); 
		} 
	}

}
