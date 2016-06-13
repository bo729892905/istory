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
<!-- basic styles -->
<link href="static/css/bootstrap.min.css" rel="stylesheet" />
<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
<link rel="stylesheet" href="static/css/font-awesome.min.css" />
<!--[if IE 7]>
  <link rel="stylesheet" href="static/css/font-awesome-ie7.min.css" />
<![endif]-->
<!-- page specific plugin styles -->

<link rel="stylesheet" href="static/css/jquery-ui-1.10.2.custom.min.css" />
<link rel="stylesheet" href="static/css/jquery.gritter.css" />

<!-- ace styles -->
<link rel="stylesheet" href="static/css/ace.min.css" />
<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
<link rel="stylesheet" href="static/css/ace-skins.min.css" />
<!--[if lt IE 9]>
  <link rel="stylesheet" href="static/css/ace-ie.min.css" />
<![endif]-->
<style type="text/css">
#btnmenu tr td{
	margin: 5px 5px;
	padding: 5px 5px;
}
</style>

</head>
<body>
<div class="container-fluid" id="main-container">
	<div class="row-fluid">
		<div class="table-header">
			编辑消息
		</div>
	
		<div id="div_content" class="row-fluid" style="padding-top: 10px;">
			<form class="form-horizontal" id="validation-form" method="post">
					<div class="control-group">
						<label class="control-label" for="title">标题:</label>
						<div class="controls" >
							<div class="span12">
								<input type="hidden" name="id" id="id" value="${message.id }" />
								<input type="text"  name="title" id="title" value="${message.title }" placeholder="请输入标题" />
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="content">内容:</label>
						<div class="controls">
							<div class="span12">
								<textarea  style="width: 80%" rows="8" name="content" id="content" placeholder="请输入消息内容">${message.content }</textarea>
							</div>
						</div>
					</div>
					
				</form>
		</div>
		
		<div class="form-actions" style="text-align: center;">
			<button class="btn btn-info" type="submit" id="submit"><i class="icon-ok"></i> 保存</button>
			&nbsp; &nbsp; &nbsp;
			<button class="btn" type="reset" id="reset"><i class="icon-undo"></i> 重置</button>
		</div>
	
	</div>
</div>
<!-- #main-content -->
</body>
<!-- basic scripts -->
<script src="static/1.9.1/jquery.min.js"></script>
<script type="text/javascript">
window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
</script>
<script src="static/js/bootstrap.min.js"></script>
<!-- page specific plugin scripts -->


<script type="text/javascript" src="static/js/jquery-ui-1.10.2.custom.min.js"></script>
<script type="text/javascript" src="static/js/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" src="static/js/jquery.easy-pie-chart.min.js"></script>
<script type="text/javascript" src="static/js/jquery.gritter.min.js"></script>
<script type="text/javascript" src="static/js/bootbox.min.js"></script>
<script type="text/javascript" src="static/js/spin.min.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script><!-- 弹框插件 -->

<!-- ace scripts -->
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<!-- inline scripts related to this page -->
<script type="text/javascript" src="static/js/target/frame-target.js"></script><!-- 子页面需要添加的js -->
<script type="text/javascript" src="static/js/target/frame-index.js"></script><!-- 子页面需要添加的js -->
<script type="text/javascript">

$(document).ready(function(){
	$("#submit").click(function(){
		this.disabled="disabled";
		if(vailVals()!=0){
			this.disabled='';
			return;
		}
		 bootbox.confirm("你将修改消息！","取消","确定",function(e){
			if(e){
				var data = {
						"id":$("#id").val(),
					"title":$("#title").val(),
					"content":$("#content").val(),
				};
				//action="user/addUser"
				$.post("admin/message/edit.htm", data, function(request) {
					var json = eval('(' + request + ')'); 
					if(1 == json.status){
						/* bootbox.alert("修改成功，点击确定开始跳转到列表！","确定",function(){ */
						$("#div_content").hide();
						top.Dialog.close();
						targetMainFrame("admin/message/list.htm");
						/* }); */
					}else{
						bootbox.alert("新增失败，请稍后重试!","确定");
					}
				});
			}
		}); 
		this.disabled='';
	});
	
	$("#reset").click(function(){
		document.getElementById("validation-form").reset(); 
	});
	
});

//验证
function vailVals(){
	
	if(!checkField($("#title").val(), 1, 60)){
		valTips('title', '请输入正确的标题');
		return 1;
	}
	
	
	if(!checkField($("#content").val(), 1,2000)){
		valTips('content', '请输入消息内容');
		return 1;
	}
	return 0;
}
</script>


</html>

