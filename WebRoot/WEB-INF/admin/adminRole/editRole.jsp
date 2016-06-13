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
/* #btnmenu tr td{
	margin: 5px 5px;
	padding: 5px 5px;
} */
</style>

</head>
<body>
<div class="container-fluid" id="main-container">
	<div class="row-fluid">
		<div class="table-header">
			权限列表
		</div>
	
		<div id="div_content" class="row-fluid" style="padding-top: 10px;">
			<form class="form-horizontal" id="validation-form" method="post">
				<input id="id" value="${adminRole.id }" type="hidden">
				<table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="center">权限名</th>
						<th class="center">授权</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width:50%; padding-left: 70px">系统管理</td>
						<td style="width:50%; padding-left: 70px"><label><input type="checkbox" class="ace-switch ace-switch-3" id="system" <c:if test="${adminRole.system == 1 }">checked="checked" </c:if> onclick="kf_qx1(this.id)">
						<span class="lbl"></span>
						</label>
	   					</td>
					</tr>
					<tr>
						<td style="width:50%; padding-left: 70px">用户管理</td>
						<td style="width:50%; padding-left: 70px"><label><input type="checkbox" class="ace-switch ace-switch-3" id="member" <c:if test="${adminRole.member == 1 }">checked="checked" </c:if>  onclick="kf_qx1(this.id)">
						<span class="lbl"></span>
						</label>
	   					</td>
					</tr>
					<tr>
						<td style="width:50%; padding-left: 70px">故事岛管理</td>
						<td style="width:50%; padding-left: 70px"><label><input type="checkbox" class="ace-switch ace-switch-3" id="story_island" <c:if test="${adminRole.storyIsland == 1 }">checked="checked" </c:if>  onclick="kf_qx1(this.id)">
						<span class="lbl"></span>
						</label>
	   					</td>
					</tr>
					<tr>
						<td style="width:50%; padding-left: 70px">微电影管理</td>
						<td style="width:50%; padding-left: 70px"><label><input type="checkbox" class="ace-switch ace-switch-3" id="micro_film" <c:if test="${adminRole.microFilm == 1 }">checked="checked" </c:if>  onclick="kf_qx1(this.id)">
						<span class="lbl"></span>
						</label>
	   					</td>
					</tr>
					<tr>
						<td style="width:50%; padding-left: 70px">剧本工厂管理</td>
						<td style="width:50%; padding-left: 70px"><label><input type="checkbox" class="ace-switch ace-switch-3" id="script_factory" <c:if test="${adminRole.scriptFactory == 1 }">checked="checked" </c:if>  onclick="kf_qx1(this.id)">
						<span class="lbl"></span>
						</label>
	   					</td>
					</tr>
					<tr>
						<td style="width:50%; padding-left: 70px">星故事管理</td>
						<td style="width:50%; padding-left: 70px"><label><input type="checkbox" class="ace-switch ace-switch-3" id="star_story" <c:if test="${adminRole.starStory == 1 }">checked="checked" </c:if>  onclick="kf_qx1(this.id)">
						<span class="lbl"></span>
						</label>
	   					</td>
					</tr>
					<tr>
						<td style="width:50%; padding-left: 70px">评论管理</td>
						<td style="width:50%; padding-left: 70px"><label><input type="checkbox" class="ace-switch ace-switch-3" id="comment" <c:if test="${adminRole.comment == 1 }">checked="checked" </c:if>  onclick="kf_qx1(this.id)">
						<span class="lbl"></span>
						</label>
	   					</td>
					</tr>
					<tr>
						<td style="width:50%; padding-left: 70px">广告管理</td>
						<td style="width:50%; padding-left: 70px"><label><input type="checkbox" class="ace-switch ace-switch-3" id="advertisement" <c:if test="${adminRole.advertisement == 1 }">checked="checked" </c:if>  onclick="kf_qx1(this.id)">
						<span class="lbl"></span>
						</label>
	   					</td>
					</tr>
					<tr>
						<td style="width:50%; padding-left: 70px">封面管理</td>
						<td style="width:50%; padding-left: 70px"><label><input type="checkbox" class="ace-switch ace-switch-3" id="cover" <c:if test="${adminRole.cover == 1 }">checked="checked" </c:if>  onclick="kf_qx1(this.id)">
						<span class="lbl"></span>
						</label>
	   					</td>
					</tr>
					<tr>
						<td style="width:50%; padding-left: 70px">推送管理</td>
						<td style="width:50%; padding-left: 70px"><label><input type="checkbox" class="ace-switch ace-switch-3" id="message" <c:if test="${adminRole.message == 1 }">checked="checked" </c:if>  onclick="kf_qx1(this.id)">
						<span class="lbl"></span>
						</label>
	   					</td>
					</tr>
					<tr>
						<td style="width:50%; padding-left: 70px">意见反馈管理</td>
						<td style="width:50%; padding-left: 70px"><label><input type="checkbox" class="ace-switch ace-switch-3" id="feedback" <c:if test="${adminRole.feedback == 1 }">checked="checked" </c:if>  onclick="kf_qx1(this.id)">
						<span class="lbl"></span>
						</label>
	   					</td>
					</tr>
					<tr>
						<td style="width:50%; padding-left: 70px">举报管理</td>
						<td style="width:50%; padding-left: 70px"><label><input type="checkbox" class="ace-switch ace-switch-3" id="report" <c:if test="${adminRole.report == 1 }">checked="checked" </c:if>  onclick="kf_qx1(this.id)">
						<span class="lbl"></span>
						</label>
	   					</td>
					</tr>
					<tr>
						<td style="width:50%; padding-left: 70px">关于我们</td>
						<td style="width:50%; padding-left: 70px"><label><input type="checkbox" class="ace-switch ace-switch-3" id="about_us" <c:if test="${adminRole.aboutUs == 1 }"> checked="checked" </c:if>  onclick="kf_qx1(this.id)">
						<span class="lbl"></span>
						</label>
	   					</td>
					</tr>
				</tbody>
				</table>
			</form>
		</div>
		<!-- 
		<div class="form-actions" style="text-align: center;">
			<button class="btn btn-info" type="submit" id="submit"><i class="icon-ok"></i> 保存</button>
			    &nbsp; &nbsp; &nbsp;
			<button class="btn" type="reset" id="reset"><i class="icon-undo"></i> 重置</button>
		</div> -->
	
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

var hcid1 = '';
var qxhc1 = '';
function kf_qx1(roleId){
	if(roleId != hcid1){
		hcid1 = roleId;
		qxhc1 = '';
	}
	var value = 1;
	var wqx = $("#"+roleId).attr("checked");
	if(qxhc1 == ''){
		if(wqx == 'checked'){
			qxhc1 = 'checked';
		}else{
			qxhc1 = 'unchecked';
		}
	}
	if(qxhc1 == 'checked'){
		value = 0;
		qxhc1 = 'unchecked';
	}else{
		value = 1;
		qxhc1 = 'checked';
	}
		var id= $("#id").val();
		var url = "<%=basePath%>admin/adminRole/editRole.htm?id="+id+"&value="+value+"&roleName="+roleId;
		$.get(url,function(data){
			if(data=="success"){
				//document.location.reload();
			}
		}); 
}


</script>


</html>

