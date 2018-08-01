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
 * 多线程处理socket接收的数据
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
			//读取客户端发送的信息 
			String strXML = ""; 
			byte[] temp = new byte[1024]; 
			int length = 0; 
			while((length = in.read(temp)) != -1)
			{ 
				strXML += new String(temp,0,length); 
			} 
			if("end".equals(strXML)){ 
				System.out.println("准备关闭socket"); 
				break; 
			} 
			if("".equals(strXML)) 
			continue; 
			
			String fileName=strXML;
			fileName=fileName.replace("upload\\", "");
			
			System.out.println("上传的图片名称："+fileName);
			System.out.println("客户端发来:"+strXML);
			
			//数据库操作
			//0,实例化数据库工具类
			uploadImages_test oSqlCon=new uploadImages_test();
			//1.加载驱动
			oSqlCon.forName();  
			  String sql = "insert into tb_file values('"+fileName+"','"+strXML+"')";  //sql语句
			  //2.建立连接
			  Connection con = oSqlCon.getConnection();
			  //3.执行sql语句,返回一个resultset结果集
			  Statement stmt = oSqlCon.getStatement(con);
			  //4.使用结果集（ResultSet）对象的访问方法获取数据：
			  ResultSet rs = oSqlCon.getResultSet(stmt, sql);
			  //oSqlCon.printTable(rs);
			  oSqlCon.close(con, stmt, null);
			  
			// MethodHandler mh = new MethodHandler(ReadXML.readXML(strXML.toString())); 
			// String resultXML = mh.getResultXML(); 
			// System.out.println("返回："+resultXML.toString()); 
		
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
