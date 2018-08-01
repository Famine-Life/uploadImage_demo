package uploadImages_jdbc;
import java.sql.*;

public class uploadImages_test{
	 /**
	  * @param args
	  */
	
	 // �ɹ����غ󣬻ὫDriver���ʵ��ע�ᵽDriverManager���С�
	 // 2���ṩJDBC���ӵ�URL
	 // ����URL�������������ݿ�ʱ��Э�顢��Э�顢����Դ��ʶ��
	 // ��д��ʽ��Э�飺��Э�飺����Դ��ʶ
	 // Э�飺��JDBC��������jdbc��ʼ
	 // ��Э�飺�������ӵ���������������ݿ����ϵͳ���ơ�
	 // ����Դ��ʶ������ҵ����ݿ���Դ�ĵ�ַ�����Ӷ˿ڡ�
	 // ��װ������ӵķ���
	 private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	 private static final String URL = "jdbc:sqlserver://localhost:1433;DatabaseName=uploadImages";
	 private static final String USER = "sa";
	 private static final String PWD = "12345";
	
	 // 1������JDBC��������
	 // ���������ݿ�֮ǰ������Ҫ������Ҫ���ӵ����ݿ��������JVM��Java���������
	 // ͨ��java.lang.Class��ľ�̬����forName(String className)ʵ�֡�
	 public void forName(){
	  try {
	   Class.forName(DRIVER);
	  } catch (Exception e) {
	   System.out.println("�Ҳ������������� ����������ʧ�ܣ�");
	   e.printStackTrace();
	  }
	 }
	
	 // 3���������ݿ������
	 // Ҫ�������ݿ⣬��Ҫ��java.sql.DriverManager���󲢻��Connection����
	 // �ö���ʹ���һ�����ݿ�����ӡ�
	 // ʹ��DriverManager��getConnectin(String url , String username ,
	 // String password )��������ָ���������ӵ����ݿ��·�������ݿ���û�����
	 // ��������á�
	 public Connection getConnection() {
	  Connection con = null;
	  try {
		  con = DriverManager.getConnection(URL, USER, PWD);
		  System.out.println("���ӳɹ���");
	  } catch (Exception e) {
		  System.out.println("���ݿ�����ʧ�ܣ�");
		  e.printStackTrace();
	
	  }
	  return con;
	 }
	
	 // 4��ִ��SQL��䣬����java.sql.Statementʵ����Statementʵ����Ϊ����3�����ͣ�
	 // * ִ�о�̬SQL��䡣ͨ��ͨ��Statementʵ��ʵ�֡�
	 // * ִ�ж�̬SQL��䡣ͨ��ͨ��PreparedStatementʵ��ʵ�֡�
	 // * ִ�����ݿ�洢���̡�ͨ��ͨ��CallableStatementʵ��ʵ�֡�
	 // �����ʵ�ַ�ʽ��
	 public Statement getStatement(Connection con) {
	  Statement stmt = null;
	  try {
		  stmt = con.createStatement();
	  } catch (SQLException e) {
		  System.out.println("��ȡ��ѯ���ʧ�ܣ�");
		  e.printStackTrace();
	  }
	  return stmt;
	 }
	
	 // 5��Statement�ӿ��ṩ������ִ��SQL���ķ�����executeQuery ��executeUpdate��execute
	 // * ResultSet executeQuery(String sqlString)��ִ�в�ѯ���ݿ��SQL���
	 // ������һ���������ResultSet������
	 // * int executeUpdate(String sqlString)������ִ��INSERT��UPDATE��
	 // DELETE����Լ�SQL DDL��䣬�磺CREATE TABLE��DROP TABLE��
	 // * execute(sqlString):����ִ�з��ض���������������¼����������ϵ���䡣
	 public ResultSet getResultSet(Statement stmt, String sql) {
	  ResultSet rs = null;
	  try {
		stmt.executeUpdate(sql);
	  } catch (SQLException e) {
		  System.out.println("����ʧ�ܣ�");
		  e.printStackTrace();
	  }
	  return rs;
	 }
	
	 // 6��������
	 // ���������
	 // 1��ִ�и��·��ص��Ǳ��β���Ӱ�쵽�ļ�¼����
	 // 2��ִ�в�ѯ���صĽ����һ��ResultSet����
	 // **ResultSet��������SQL����������������У�������ͨ��һ��get�����ṩ�˶���Щ
	 // �������ݵķ��ʡ�
	 // **ʹ�ý������ResultSet������ķ��ʷ�����ȡ���ݣ�
	 public void printTable(ResultSet rs) {
	  try {
		  ResultSetMetaData m = null;// ��ȡ ����Ϣ
	
		  m = rs.getMetaData();
		  int columns = m.getColumnCount();
	
	   // ��ʾ��,���ı�ͷ
	   for (int i = 1; i <= columns; i++) {
		   System.out.print(m.getColumnName(i));
		   System.out.print("\t\t");
	   }
	   System.out.println();
	   // ��ʾ�������
	   String a = null;
		while (rs.next()) {
			//ע�⣬ѭ��columns�Ǵ�1��ʼ ����ֹͣ�������׳���
			 for (int i = 1; i <=columns; i++) {
				 //�ж������Ƿ���String����
				 if (rs.getString(i) instanceof String){
					a=(rs.getString(i)).trim();	//��ǰ��ո�ȥ��
					System.out.print(a);
				}else{
					System.out.print(rs.getString(i));
				}
				
			     System.out.print("\t\t");
		    }
		    System.out.println();
		   }
	  } catch (SQLException e) {
		  System.out.println("�޷���ӡ��ѯ�����");
		  e.printStackTrace();
	  }
	 }
	
	 // 7���ر�JDBC����
	 // ��������Ժ�Ҫ������ʹ�õ�JDBC����ȫ���رգ����ͷ�JDBC��Դ���ر�˳�����
	 // ��˳���෴��
	 // 1���رռ�¼��
	 // 2���ر�����
	 // 3���ر����Ӷ���
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
	/* web��Ŀ����Ҫ
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