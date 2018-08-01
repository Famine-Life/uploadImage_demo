<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ page import="entity.Items" %>
<%@ page import="imagesDao.ItemsDao" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>单图片显示</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style>
	.mainbody{
	    width:1000px;
	    margin:0 auto;
	    text-align: center;
	}
	.showDiv{
	    width: 1000px;
	}
	#showImg{
	    display: block;
	    margin:0 auto;
	    border:1px dashed #666;
	}
	.footer{
	   position:fixed;
	   bottom:0px;
	   text-align:center;
	   width:100%;
	   }
	.toAll{
		margin-top:50px;
		width:100%;
		height:50px;
	}
	.toAll a{
		color:red;
		font-size:26px;
	}
</style>
  </head>
  <% 
  String id=request.getParameter("id");
  ItemsDao dao=new ItemsDao();
  Items item=dao.getItemsById(Integer.parseInt(id));
  
   %>
  <body>
      <div class="mainbody">
              <h1>图片展示</h1>
              <hr>
              <center><a href="index.jsp">返回上传</a>
              		 <a href="showAll.jsp">返回图片列表</a>
              		<a href="showImages.jsp"></a>
              </center>
              <hr>
              <p>
               <div class="showDiv">
                   <img  id="showImg" src="<%=item.getPicture()%>" border="1"/>
               </div>
               
               <hr>
              </p>
      </div>
</body>
</html>
