package service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

//�Զ�����ʵ��ServletContextListener�ӿڣ�Ϊ����socket���ŷ���������
public class ServerSocketListener implements ServletContextListener {
	//socket server �߳� 
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
		//�½��߳��� 
		socketThread=new Server(null); 
		//�����߳� 
		socketThread.start(); 
		} 
	}

}
