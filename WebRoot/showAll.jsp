<%@ page language="java" import="java.util.*"  contentType="text/html; charset=utf-8" %>
<%@ page import="entity.Items"%>
<%@ page import="imagesDao.ItemsDao"%>
<%@ page import="service.UploadServlet"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>图片展示页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
	   div{
	      float:left;
	      margin: 10px;
	   }
	   div dd{
	      margin:0px;
	      font-size:10pt;
	   }
	   div dd.dd_name
	   {
	      color:blue;
	   }
	   div dd.dd_city
	   {
	      color:#000;
	   }
	   .footer{
	   text-align:center;
	   width:100%;
	   }
	</style>
  </head>
  
  <body>
    <h1>所有图片</h1>
    <hr>
  
    <center>
    <table width="750" height="60" cellpadding="0" cellspacing="0" border="0">
      <tr>
        <td>
          
          <!-- 图片逆序循环开始 -->
           <% 
           	   Items item = new Items();
               ItemsDao itemsDao = new ItemsDao(); 
               ArrayList<Items> list = itemsDao.getAllItems();
               if(list!=null&&list.size()>0)
               {	
	               for(int i=0;i<list.size();i++)
	               {
	                   item = list.get(i);
           %>   
          <div>
             <dl>
               <dt>
                 <a href="details.jsp?id=<%=item.getId()%>"><img src="<%=item.getPicture()%>" width="200" height="200" border="1"/></a>
               	 <h3><%=item.getName()%></h3>
               </dt>
               <dd class="dd_name"></dd> 
             </dl>
          </div>
          <!-- 循环结束 -->
        
          <%
                   }
              } 
          %>
        </td>
      </tr>
      <tr><td><a href="index.jsp">继续上传</a></td></tr>
    </table>
    </center>
  </body>
</html>
