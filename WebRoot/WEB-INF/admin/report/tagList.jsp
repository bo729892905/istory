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
			<form action="admin/report/tagList.htm" method="post" name="userForm" id="userForm">
			<table border='0' >
				<tr>
				
					<td>
						<span class="input-icon">
							<input autocomplete="off" style="width: 75%"   type="text" name="tag" id="tag" maxlength="10"   placeholder="标签" />
						</span>
					</td>
					
					<td style="vertical-align:top; padding-left: 10px">
						<a class="btn btn-small btn-primary" onclick="addTag()">添加</a></td>
				</tr>
			</table>
			<!-- 检索  -->
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th class="center" style="width:1%">序号</th>
						<th class="center" style="width:15%">标签</th>
						<th class="center" style="width:1%" >操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty tagList}">
						<c:forEach items="${tagList}" var="tag" varStatus="vs">
							<tr>
								<td class="center">${vs.index+1 }</td>
								<td class="center">${tag.tag }</td>
								<td  style=" padding-left: 60px"  >
									<div class='hidden-phone visible-desktop btn-group'>
											<a class='btn btn-mini btn-danger' title="删除" onclick="delTag('${tag.id }');"><i class='icon-trash'></i></a>
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
		function addTag(){
			var tag = $("#tag").val();
			if(tag=='' || tag == undefined){
				$("#tag").tips({
					side:3,
		            msg:'请输入标签',
		            bg:'#AE81FF',
		            time:3
		        });
				return;
			}
			bootbox.confirm("您确定要添加此标签吗？", function(result) {
				if(result) {
						top.jzts();
						$.ajax({
							type: "POST",
							url: '<%=basePath%>admin/report/addTag.htm?tm='+new Date().getTime(),
					    	data: {tag:tag},
							dataType:'json',
							//beforeSend: validateData,
							cache: false,
							success: function(data){
								top.hangge();
								if(data.status==1){
									nextPage(${page.currentPage});
								}else{
									if("exist_tag"==data.msg){
										bootbox.alert("已存在此标签!","确定");
									}else{
										bootbox.alert("操作失败，请稍后重试!","确定");
									}
								}
							}
						});
					}
			});
		}
		
		
		//删除
		function delTag(id){
			bootbox.confirm("确定要删除此标签吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>admin/report/deleteTag.htm?id="+id+"&tm="+new Date().getTime();
					$.post(url,function(data){
						nextPage(${page.currentPage});
					});
					top.hangge();
				}
			});
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

