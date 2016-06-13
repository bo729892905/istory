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
		
		
<div class="container-fluid" id="main-container">


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">

	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="admin/member/list.htm" method="post" name="userForm" id="userForm">
			<table border='0' >
				<tr>
				
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text" name="name" value="${member.name }" placeholder="姓名" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>
					</td>
					<td style="padding-left: 10px">
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text" name="mobile" value="${member.mobile }" placeholder="手机" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>
					</td>
					<td style="padding-left: 10px"><input class="span10" name="startTime" id="startTime" value="${member.startTime}" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'endTime\')}'})" readonly="readonly" style="width:88px;" placeholder="注册日期起"/></td>
					<td style="padding-left: 10px"><input class="span10" name="endTime" id="endTime" value="${member.endTime}" type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'startTime\')}'})" readonly="readonly" style="width:88px;" placeholder="注册日期止"/></td>
					<td style="vertical-align:top; padding-left: 10px"> 
					 	<select class="chzn-select" name="sex" id="sex" data-placeholder="性别" style="vertical-align:top;width: 79px;">
							<option value="">性别</option>
							<option value="1" <c:if test="${member.sex == '1' }">selected</c:if> >男</option>
							<option value="0" <c:if test="${member.sex == '0' }">selected</c:if> >女</option>
						</select>
					</td>
					<td style="vertical-align:top; padding-left: 10px"><button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
				</tr>
			</table>
			<!-- 检索  -->
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th class="center" style="width:5%">
							<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th class="center" style="width:10%">ID</th>
						<th class="center" style="width:15%">姓名</th>
						<th class="center" style="width:15%">账号</th>
						<th class="center" style="width:15%">来源</th>
						<th class="center" style="width:15%">上次登录</th>
						<th class="center" class="center">状态</th>
						<th class="center" class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty memberList}">
						<c:forEach items="${memberList}" var="member" varStatus="vs">
							<tr>
								<td class="center" style=" padding-bottom: 5px">
									<label><input type='checkbox' name='ids' value="${member.id }" /><span class="lbl"></span></label>
								</td>
								<td class="center">${member.id }</td>
								<td class="center">${member.name }</td>
								<td class="center">${member.mobile }</td>
								<td class="center"><c:if test="${member.source == '1' }">注册</c:if>
									<c:if test="${member.source == '2' }">QQ</c:if>
									<c:if test="${member.source == '3' }">微博</c:if>
									<c:if test="${member.source == '4' }">微信</c:if></td>
								<td class="center">${member.lastLoginTime }</td>
								<td style="width: 5%;" class="center">
									<c:if test="${member.status == '0' }"><span class="label label-important arrowed-in">禁用</span></c:if>
									<c:if test="${member.status == '1' }"><span class="label label-success arrowed">开启</span></c:if>
								</td>
								<td  style="width: 4%; padding-left: 20px"  >
									<div class='hidden-phone visible-desktop btn-group'>
											<a class='btn btn-mini btn-info' title="查看" onclick="editUser('${member.id }');"><i class='icon-edit'></i></a>
											
									</div>
								</td>
							</tr>
						
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="100" class="center" >没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
				
				</tbody>
			</table>
			
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;">
					<a class="btn btn-small btn-success" onclick="makeAll('1');">开启</a>
				</td>
					<td style="vertical-align:top;">
					<a class="btn btn-small btn-danger" onclick="makeAll('0');">禁用</a>
				</td>
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
		</div>
		</form>
	</div>
 
 
 
 
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
		
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<!--时间  -->
		<script type="text/javascript" src="<%=basePath%>resource/js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
		
		$(top.hangge());
		
		//检索
		function search(){
			top.jzts();
			$("#userForm").submit();
		}
		//查看
		function editUser(id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="用户资料";
			 diag.URL = '<%=basePath%>admin/member/goMemberInfo.htm?id='+id;
			 diag.Width = 500;
			 diag.Height = 510;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('div_content').style.display == 'none'){
					 nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show();
			 top.hangge();
		}
		//批量操作
		function makeAll(status){

			var str = '';
			for(var i=0;i < document.getElementsByName('ids').length;i++)
			{
				  if(document.getElementsByName('ids')[i].checked){
				  	if(str=='') str += document.getElementsByName('ids')[i].value;
				  	else str += ',' + document.getElementsByName('ids')[i].value;
				  	
				  }
			}
			if(str==''){
				bootbox.dialog("您没有选择任何内容!", 
					[
					  {
						"label" : "关闭",
						"class" : "btn-small btn-success",
						"callback": function() {
							//Example.show("great success");
							}
						}
					 ]
				);
				
				$("#zcheckbox").tips({
					side:3,
		            msg:'点这里全选',
		            bg:'#AE81FF',
		            time:3
		        });
				
				return;
			}else{
			
				var msg = '';
				if(status=='0'){
					msg = "确定要禁用选中的用户吗?";
				}else{
					msg = "确定要启用选中的用户吗?";
				}
				bootbox.confirm(msg, function(result) {
					if(result) {
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>admin/member/updateStatus.htm?tm='+new Date().getTime(),
						    	data: {ids:str,status:status},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									if(data.status==1){
										nextPage(${page.currentPage});
									}else{
										bootbox.alert("操作失败，请稍后重试!","确定");
									}
								}
							});
						}
				});
			}
		
		}
		
		
		
		
		</script>
		
		<script type="text/javascript">
		
		$(function() {
			
			//下拉框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
			//复选框
			$('table th input:checkbox').on('click' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox')
				.each(function(){
					this.checked = that.checked;
					$(this).closest('tr').toggleClass('selected');
				});
					
			});
			
		});
		
		</script>
		
	</body>
</html>

