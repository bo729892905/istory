<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
	<%@ include file="../top.jsp"%>   
	</head>
<body>
		
<div id="page-content" class="clearfix" >
						
  <div class="row-fluid" style="padding-top: 5% ">
	  <form class="form-horizontal" id="validation-form" method="post">
 		  <textarea id="aboutUs" name="aboutUs" style="width: 50%;height:300px; " maxlength="10000">${aboutUs.aboutUs}</textarea>
 	 </form>
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
  <div style="text-align: left; padding-left: 15%">
			<button class="btn btn-info" type="submit" id="submit"><i class="icon-ok"></i> 保存</button>
			&nbsp; &nbsp; &nbsp;
			<button class="btn" type="reset" id="reset"><i class="icon-undo"></i> 重置</button>
		</div>
	
</div><!--/#page-content-->
		
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="editor/main.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		
		$(top.hangge());
		
		</script>
		
		<script type="text/javascript">
		
		
		var contentEditor;
		$(function() {
			
			KindEditor.ready(function(K) {
				contentEditor = K.create('#aboutUs',{
		        	items:[
		        	        'source', '|','formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
		        	        'italic', 'underline', 'strikethrough', 'removeformat', 'justifyleft', 'justifycenter', 'justifyright',
		        	        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
		        	        'superscript', '|', 'preview', 'fullscreen'
		        	      ],
		        	 afterBlur: function () { this.sync(); },
		        	 urlType : 'domain',
		        	 uploadJson : $("base")[0].href+'editor/jsp/upload_json.jsp',
		        });
			});
			
			
			$("#reset").click(function(){
				contentEditor.html("");
			});
			
			$("#submit").click(function(){
				bootbox.confirm("确定修改“关于我们”吗？", function(result) {
					if(result){
						var data = {
								"aboutUs":contentEditor.html(),
							};
						$.post("admin/aboutUs/edit.htm", data, function(request) {
							var json = eval('(' + request + ')'); 
							if(1 == json.status){
								bootbox.alert("修改成功!","确定");
							}else{
								bootbox.alert("修改失败，请稍后重试!","确定");
							}
						});
					}
				});
			});
			
		});
		
		</script>
		
	</body>
</html>

