<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>图片上传</title>
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
  
  <body>
      <div class="mainbody">
              <h1>基于socket,io,jdbc的上传图片功能测试，大家来分享点图片？</h1>
              <h2>用户注册、登录功能正在完善~</h2>
              <hr>
              <p>
                  <form name="demoForm" id="demoForm" method="post" enctype="multipart/form-data" 
                  action="/uploadImage_demo/UploadServlet">
                  图片上传前预览：<input type="file" name="uploadFile" id="input_img" onchange="UploadImg(this)" accept="image/*"/>
                  <input type="button" value="隐藏图片" onclick="document.getElementById('showImg').style.display = 'none';"/>
                  <input type="button" value="显示图片" onclick="document.getElementById('showImg').style.display = 'block';"/>
                  <div class="toAll"><a href="showAll.jsp" />查看所有图片</a></div>
               <div class="showDiv">
                   <img id="showImg"/>
               </div>
               <div id="showException"></div>
               <hr>
                <div>文件大小: 
                     <span id="bytesTotal"></span> 
                </div> 
               <input type="submit" value="上传" />
              	</form>
              </p>
      </div>
            <script type="text/javascript">        

                //判断浏览器是否支持FileReader接口
                if (typeof FileReader == 'undefined') {
                    document.getElementById("showException").InnerHTML = "<h1>当前浏览器不支持FileReader接口</h1>";
                    //使选择控件不可操作
                    document.getElementById("input_img").setAttribute("disabled", "disabled");
                }
        
                //选择图片，马上预览
                var file;
                var reader;
                function UploadImg(obj) {
                    file = obj.files[0];
                    console.log(obj);console.log(file);
                    console.log("file.size = " + file.size);  //file.size 单位为byte
        
                    reader = new FileReader();
        
                    //读取文件过程方法
                    reader.onloadstart = function (e) {
                        console.log("开始读取....");
                        document.getElementById("bytesTotal").textContent = (file.size/1024)/1024+"M"; 
                    }
                    reader.onprogress = function (e) {
                        console.log("正在读取中....");
                    }
                    reader.onabort = function (e) {
                        console.log("中断读取....");
                    }
                    reader.onerror = function (e) {
                        console.log("读取异常....");
                    }
                    reader.onload = function (e) {
                        console.log("成功读取....");
        
                        var img = document.getElementById("showImg");
                        img.src = e.target.result;
                        //或者 img.src = this.result;  //e.target == this
                    }
                    reader.readAsDataURL(file);//显示图片。
                }

                
            </script>
</body>
</html>
