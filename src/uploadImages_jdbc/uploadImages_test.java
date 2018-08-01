package uploadImages_jdbc;
import java.sql.*;

public class uploadImages_test{
	 /**
	  * @param args
	  */
	
	 // 成功加载后，会将Driver类的实例注册到DriverManager类中。
	 // 2、提供JDBC连接的URL
	 // 连接URL定义了连接数据库时的协议、子协议、数据源标识。
	 // 书写形式：协议：子协议：数据源标识
	 // 协议：在JDBC中总是以jdbc开始
	 // 子协议：是桥连接的驱动程序或是数据库管理系统名称。
	 // 数据源标识：标记找到数据库来源的地址与连接端口。
	 // 封装获得连接的方法
	 private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	 private static final String URL = "jdbc:sqlserver://localhost:1433;DatabaseName=uploadImages";
	 private static final String USER = "sa";
	 private static final String PWD = "12345";
	
	 // 1、加载JDBC驱动程序：
	 // 在连接数据库之前，首先要加载想要连接的数据库的驱动到JVM（Java虚拟机），
	 // 通过java.lang.Class类的静态方法forName(String className)实现。
	 public void forName(){
	  try {
	   Class.forName(DRIVER);
	  } catch (Exception e) {
	   System.out.println("找不到驱动程序类 ，加载驱动失败！");
	   e.printStackTrace();
	  }
	 }
	
	 // 3、创建数据库的连接
	 // 要连接数据库，需要向java.sql.DriverManager请求并获得Connection对象，
	 // 该对象就代表一个数据库的连接。
	 // 使用DriverManager的getConnectin(String url , String username ,
	 // String password )方法传入指定的欲连接的数据库的路径、数据库的用户名和
	 // 密码来获得。
	 public Connection getConnection() {
	  Connection con = null;
	  try {
		  con = DriverManager.getConnection(URL, USER, PWD);
		  System.out.println("连接成功！");
	  } catch (Exception e) {
		  System.out.println("数据库连接失败！");
		  e.printStackTrace();
	
	  }
	  return con;
	 }
	
	 // 4、执行SQL语句，须获得java.sql.Statement实例，Statement实例分为以下3种类型：
	 // * 执行静态SQL语句。通常通过Statement实例实现。
	 // * 执行动态SQL语句。通常通过PreparedStatement实例实现。
	 // * 执行数据库存储过程。通常通过CallableStatement实例实现。
	 // 具体的实现方式：
	 public Statement getStatement(Connection con) {
	  Statement stmt = null;
	  try {
		  stmt = con.createStatement();
	  } catch (SQLException e) {
		  System.out.println("获取查询语句失败！");
		  e.printStackTrace();
	  }
	  return stmt;
	 }
	
	 // 5、Statement接口提供了三种执行SQL语句的方法：executeQuery 、executeUpdate和execute
	 // * ResultSet executeQuery(String sqlString)：执行查询数据库的SQL语句
	 // ，返回一个结果集（ResultSet）对象。
	 // * int executeUpdate(String sqlString)：用于执行INSERT、UPDATE或
	 // DELETE语句以及SQL DDL语句，如：CREATE TABLE和DROP TABLE等
	 // * execute(sqlString):用于执行返回多个结果集、多个更新计数或二者组合的语句。
	 public ResultSet getResultSet(Statement stmt, String sql) {
	  ResultSet rs = null;
	  try {
		stmt.executeUpdate(sql);
	  } catch (SQLException e) {
		  System.out.println("插入失败！");
		  e.printStackTrace();
	  }
	  return rs;
	 }
	
	 // 6、处理结果
	 // 两种情况：
	 // 1、执行更新返回的是本次操作影响到的记录数。
	 // 2、执行查询返回的结果是一个ResultSet对象。
	 // **ResultSet包含符合SQL语句中条件的所有行，并且它通过一套get方法提供了对这些
	 // 行中数据的访问。
	 // **使用结果集（ResultSet）对象的访问方法获取数据：
	 public void printTable(ResultSet rs) {
	  try {
		  ResultSetMetaData m = null;// 获取 列信息
	
		  m = rs.getMetaData();
		  int columns = m.getColumnCount();
	
	   // 显示列,表格的表头
	   for (int i = 1; i <= columns; i++) {
		   System.out.print(m.getColumnName(i));
		   System.out.print("\t\t");
	   }
	   System.out.println();
	   // 显示表格内容
	   String a = null;
		while (rs.next()) {
			//注意，循环columns是从1开始 ，，停止条件容易出错！
			 for (int i = 1; i <=columns; i++) {
				 //判断数据是否是String类型
				 if (rs.getString(i) instanceof String){
					a=(rs.getString(i)).trim();	//把前后空格去掉
					System.out.print(a);
				}else{
					System.out.print(rs.getString(i));
				}
				
			     System.out.print("\t\t");
		    }
		    System.out.println();
		   }
	  } catch (SQLException e) {
		  System.out.println("无法打印查询结果！");
		  e.printStackTrace();
	  }
	 }
	
	 // 7、关闭JDBC对象
	 // 操作完成以后要把所有使用的JDBC对象全都关闭，以释放JDBC资源，关闭顺序和声
	 // 明顺序相反：
	 // 1、关闭记录集
	 // 2、关闭声明
	 // 3、关闭连接对象
	 public void close(Connection con, Statement stmt, ResultSet rs) {
//		  try {
		  	//rs.close();
//		  } catch (SQLException e1) {
//			   // TODO Auto-generated catch block
//			   e1.printStackTrace();
//		  }
		  try {
			 if (stmt != null)
			 stmt.close();
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
		  try {
			  if (con != null)
			  con.close();
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	 }
	/* web项目不需要
	 public static void main(String[] args) {
		  String sql = "select * from tb_info";
		  Connection con = getConnection();
		  Statement stmt = getStatement(con);
		  ResultSet rs = getResultSet(stmt, sql);
		  printTable(rs);
		  close(con, stmt, rs);
	 }
	 */
}