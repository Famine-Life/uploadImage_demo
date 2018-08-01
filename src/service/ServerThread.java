package service;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import uploadImages_jdbc.uploadImages_test;

/*
 * ���̴߳���socket���յ�����
 */
public class ServerThread extends Thread {
	private Socket socket; 
	UploadServlet UploadServlet=null;
	public ServerThread(Socket socket) { 
		this.socket=socket; 
	} 
	@SuppressWarnings("unused") 
	public void run() 
	{ 
		try{ 
	
		InputStream in= socket.getInputStream(); 
	
		PrintWriter out=new PrintWriter(socket.getOutputStream()); 
	
		//BufferedReader wt = new BufferedReader(new InputStreamReader(System.in)); 
	
		while(true){ 
			//��ȡ�ͻ��˷��͵���Ϣ 
			String strXML = ""; 
			byte[] temp = new byte[1024]; 
			int length = 0; 
			while((length = in.read(temp)) != -1)
			{ 
				strXML += new String(temp,0,length); 
			} 
			if("end".equals(strXML)){ 
				System.out.println("׼���ر�socket"); 
				break; 
			} 
			if("".equals(strXML)) 
			continue; 
			
			String fileName=strXML;
			fileName=fileName.replace("upload\\", "");
			
			System.out.println("�ϴ���ͼƬ���ƣ�"+fileName);
			System.out.println("�ͻ��˷���:"+strXML);
			
			//���ݿ����
			//0,ʵ�������ݿ⹤����
			uploadImages_test oSqlCon=new uploadImages_test();
			//1.��������
			oSqlCon.forName();  
			  String sql = "insert into tb_file values('"+fileName+"','"+strXML+"')";  //sql���
			  //2.��������
			  Connection con = oSqlCon.getConnection();
			  //3.ִ��sql���,����һ��resultset�����
			  Statement stmt = oSqlCon.getStatement(con);
			  //4.ʹ�ý������ResultSet������ķ��ʷ�����ȡ���ݣ�
			  ResultSet rs = oSqlCon.getResultSet(stmt, sql);
			  //oSqlCon.printTable(rs);
			  oSqlCon.close(con, stmt, null);
			  
			// MethodHandler mh = new MethodHandler(ReadXML.readXML(strXML.toString())); 
			// String resultXML = mh.getResultXML(); 
			// System.out.println("���أ�"+resultXML.toString()); 
		
			// if(!"".equals(resultXML)){ 
			// out.print(resultXML); 
			out.flush(); 
			out.close(); 
			// } 
			
			} 
		socket.close(); 
		System.out.println("socket stop....."); 

		}
		catch(IOException ex)
		{ 
			System.out.println("error:"+ex);
		}finally
		{ 

		} 
	}
 
}
