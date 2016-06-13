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
			<form action="admin/storyIslandClassify/list.htm" method="post" name="userForm" id="userForm">
			<table border='0' >
				<tr>
				
					<td>
						<span class="input-icon">
							<input autocomplete="off" style="width: 75%"   type="text" name="classify" id="classify" maxlength="10"   placeholder="分类名" />
						</span>
					</td>
					
					<td style="vertical-align:top; padding-left: 10px"><a class="btn btn-small btn-primary" onclick="addClassify()">添加</a></td>
				</tr>
			</table>
			<!-- 检索  -->
			<!-- 检索  -->
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th class="center" style="width:25%">序号</th>
						<th class="center" style="width:55%">分类名</th>
						<th class="center" style="width:5%">排序</th>
						<th class="center" style="width:3%">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty storyIslandClassifyList}">
						<c:forEach items="${storyIslandClassifyList}" var="story" varStatus="vs">
							<tr>
								<td class="center">${vs.index+1 }</td>
								<td class="center">${story.classify }</td>
								<td class="center" >
									<div class='hidden-phone visible-desktop btn-group'>
											<a <c:if test="${vs.index == 0}"> class='btn btn-mini disabled' title="升序" </c:if> <c:if test="${vs.index!=0}"> class='btn btn-mini  btn-info' title="升序"  onclick="upOrDown('${story.id }','${story.number }','1')" </c:if>><i class='icon-arrow-up'></i></a>
											<a <c:if test="${vs.index == (page.totalResult-1)}"> class='btn btn-mini disabled' title="降序" </c:if> <c:if test="${vs.index != (page.totalResult-1)}"> class='btn btn-mini  btn-info' title="降序"  onclick="upOrDown('${story.id }','${story.number }','0')" </c:if> ><i class='icon-arrow-down'></i></a>
									</div>
								</td>
								<td  class="center"  >
									<div class='hidden-phone visible-desktop btn-group'>
											<a class='btn btn-mini btn-info' title="编辑" onclick="editUser('${story.id }');"><i class='icon-edit'></i></a>
											<a class='btn btn-mini btn-danger' title="删除" onclick="deleteClassify('${story.id }');"><i class='icon-trash'></i></a>
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
		function search(){
			top.jzts();
			$("#userForm").submit();
		}
		function toList(id){
			//新增
			top.mainFrame.tabAddHandler("storyIslandList","故事章节列表","admin/storyIsland/chapterList.htm?id="+id);
		}
		//查看
		function editUser(id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="故事分类";
			 diag.URL = '<%=basePath%>admin/storyIslandClassify/edit.htm?id='+id;
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
		
		//修改
		function upOrDown(id,number,type){
			top.jzts();
			$.ajax({
				type: "POST",
				url: '<%=basePath%>admin/storyIslandClassify/updateNumber.htm?tm='+new Date().getTime(),
		    	data: {id:id,number:number,ordertype:type},
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
		//删除
		function deleteClassify(id){
			top.jzts();
			$.ajax({
				type: "POST",
				url: '<%=basePath%>admin/storyIslandClassify/deleteClassify.htm?tm='+new Date().getTime(),
		    	data: {id:id},
				dataType:'json',
				//beforeSend: validateData,
				cache: false,
				success: function(data){
					top.hangge();
					if(data.status==1){
						nextPage(${page.currentPage});
					}else{
						if(data.msg==""){
							bootbox.alert("操作失败，请稍后重试!","确定");
						}else{
							bootbox.alert("该分类下已存在故事，无法删除","确定");
						}
					}
				}
			});
		}
		
		
		//检索
		function addClassify(){
			var classify = $("#classify").val();
			if(classify=='' || classify == undefined){
				$("#classify").tips({
					side:3,
		            msg:'请输入 分类名',
		            bg:'#AE81FF',
		            time:3
		        });
				return;
			}
			bootbox.confirm("您确定要添加此分类吗？", function(result) {
				if(result) {
						top.jzts();
						$.ajax({
							type: "POST",
							url: '<%=basePath%>admin/storyIslandClassify/addClassify.htm?tm='+new Date().getTime(),
					    	data: {classify:classify},
							dataType:'json',
							//beforeSend: validateData,
							cache: false,
							success: function(data){
								top.hangge();
								if(data.status==1){
									nextPage(${page.currentPage});
								}else{
									if("exist_tag"==data.msg){
										bootbox.alert("已存在此分类!","确定");
									}else{
										bootbox.alert("操作失败，请稍后重试!","确定");
									}
								}
							}
						});
					}
			});
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
									url: '<%=basePath%>admin/storyIsland/deleteStoryIsland.htm?tm='+new Date().getTime(),
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

