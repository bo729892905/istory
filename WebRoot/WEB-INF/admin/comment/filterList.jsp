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
			<form action="admin/filterWords/list.htm" method="post" name="userForm" id="userForm">
			<table border='0' >
				<tr>
					<td>
						<span class="span12">添加过滤字
							<input autocomplete="off" id="nav-search-input" type="text"  value="" placeholder="" />
						</span>
					</td>
					<td style="vertical-align:top; padding-left: 10px"><a class="btn btn-small btn-primary" onclick="add();">添加</a></td>
				</tr>
				
				
			</table>
			<!-- 检索  -->
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th class="center" style="width:5%">序号</th>
						<th class="center" style="width:20%">过滤字</th>
						<th class="center" style="width:15%">数量</th>
						<th class="center" >操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty filterWordsList }">
						<c:forEach items="${filterWordsList}" var="filter" varStatus="vs">
							<tr>
								<td class="center">${vs.index+1}</td>
								<td class="center">${filter.filterWords }</td>
								<td class="center">${filter.number }</td>
								<td  style="width: 3%; padding-left: 60px" >
									<div class='hidden-phone visible-desktop btn-group'>
											<a class='btn btn-mini btn-danger' title="删除" onclick="deleteFilter('${filter.id }');"><i class='icon-trash'></i></a>
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
				<td style="vertical-align:top; "><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
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
		//删除
		function deleteFilter(id){
			bootbox.confirm("确定删除该过滤字吗？", function(result) {	
				if(result){
					$.ajax({
						type: "POST",
						url: '<%=basePath%>admin/filterWords/deleteFilterWords.htm?tm='+new Date().getTime(),
				    	data: {id:id},
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
		
		function add(){
			var filter = $("#nav-search-input").val();
			if(filter=='' || filter == null){
				$("#nav-search-input").tips({
					side:3,
		            msg:'请填写过滤字',
		            bg:'#AE81FF',
		            time:3
		        });
				return;
			}
			$.ajax({
				type: "POST",
				url: '<%=basePath%>admin/filterWords/add.htm?tm='+new Date().getTime(),
		    	data: {filter:filter,},
				dataType:'json',
				//beforeSend: validateData,
				cache: false,
				success: function(data){
					if(data.status==1){
						nextPage(${page.currentPage});
					}else{
						if(data.msg=="repeat"){
							bootbox.alert("请勿添加重复的过滤字!","确定");
						}else{
							bootbox.alert("操作失败，请稍后重试!","确定");
						}
					}
				}
			});
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
					msg = "确定要禁用选中的剧本吗?";
				}else{
					msg = "确定要启用选中的剧本吗?";
				}
				bootbox.confirm(msg, function(result) {
					if(result) {
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>admin/starStory/updateStatus.htm?tm='+new Date().getTime(),
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
		
		
		//批量删除
		function deleteAll(){
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
				bootbox.confirm("确定删除选中的故事吗？", function(result) {
					if(result) {
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>admin/starStory/deleteStarStory.htm?tm='+new Date().getTime(),
						    	data: {ids:str},
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

