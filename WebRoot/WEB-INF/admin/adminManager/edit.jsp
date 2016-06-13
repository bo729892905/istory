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
			编辑管理员
		</div>
	
		<div id="div_content" class="row-fluid" style="padding-top: 10px;">
			<form class="form-horizontal" id="validation-form" method="post">
					<div class="control-group">
						<label class="control-label" for="username">账号:</label>
						<div class="controls" >
							<div class="span12">
								<input type="hidden" name="id" id="id" value="${adminInfo.id }" />
								<input type="text" readonly="readonly" name="username" id="username" value="${adminInfo.username }" placeholder="请输入账号" />
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="password">密码:</label>
						<div class="controls">
							<div class="span12">
								<input type="password" value="" name="password" id="password" placeholder="" />
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="password">确认密码:</label>
						<div class="controls">
							<div class="span12">
								<input type="password" value="" name="password1" id="password1" placeholder="" />
							</div>
						</div>
					</div>

					<div class="control-group">
						<label class="control-label" for="name">姓名:</label>
						<div class="controls">
							<span class="span12"> <input  type="text"
								value="${adminInfo.name }" id="name" name="name" placeholder="请输入姓名" />
							</span>
						</div>
					</div>
					
					<div class="control-group">
						<label class="control-label" for="status">是否开通:</label>
						<div class="controls">
							<span class="span12"> <select  id="status"
								name="status">
									<option value="1"
										<c:if test="${adminInfo.status==1 }"> selected=selected</c:if>>启用</option>
									<option value="0"
										<c:if test="${adminInfo.status==0 }">selected=selected</c:if>>禁用</option>
							</select>
							</span>
						</div>
					</div>
					
					<div class="control-group">
						<label class="control-label" for="Role">角色:</label>
						<div class="controls">
							<span class="span12"> <select  id="roleId"
								name="roleId">
									<c:forEach items="${roleNameList}" var="role" varStatus="vs">
										<c:if test="${role.roleName ne '超级管理员' }"> 
										  <option value='${role.id }' <c:if test="${role.id eq adminInfo.roleId }"> selected=selected</c:if>>${role.roleName }</option>
										</c:if>
									</c:forEach>
							</select>
							</span>
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
		 bootbox.confirm("你将修改管理员信息！","取消","确定",function(e){
			if(e){
				var data = {
					"id":$("#id").val(),
					"username":$("#username").val(),
					"password":$("#password").val(),
					"name":$("#name").val(),
					"roleId":$("#roleId").val(),
					"status":$("#status").val()
				};
				//action="user/addUser"
				$.post("admin/save.htm", data, function(request) {
					var json = eval('(' + request + ')'); 
					if(1 == json.status){
						/* bootbox.alert("修改成功，点击确定开始跳转到列表！","确定",function(){ */
							$("#div_content").hide();
							top.Dialog.close();
							targetMainFrame("admin/list.htm");
						/* }); */
					}else{
						bootbox.alert("修改失败，请稍后重试!","确定");
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
	
	if(!checkField($("#username").val(), 5, 20,new RegExp("^[a-zA-Z0-9]{5,20}$"))){
		valTips('username', '请输入5到20位字母或数字组合');
		return 1;
	}
	if($("#password").val() != ''){
		if($("#password").val() != $("#password1").val()){
			valTips('password1', '两次密码不一致');
			return 1;
		}else if(!checkField($("#password").val(), 5, 20)){
			valTips('password', '请输入5到20位字符');
			return 1;
		}
	}
	
	if($("#name").val() == ''){
		valTips('name', '请输入管理员姓名');
		return 1;
	}
	return 0;
}
</script>


</html>

