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
			<form action="admin/advertisement/islandListAll.htm" method="post" name="userForm" id="userForm">
			<table border='0' >
				<tr>
				
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text" name="title" value="${storyIsland.title }" placeholder="文章标题" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>
					</td>
					<td style="vertical-align:top; padding-left: 10px"><button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
					<td style="vertical-align:top; padding-left: 10px"><a class="btn btn-small btn-primary" onclick="choose();"  title="确定">确定</a></td>
				</tr>
			</table>
			<!-- 检索  -->
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th class="center" style="width:5%">
							<label><input type="checkbox" id="zcheckbox"/></label>
						</th>
						<th class="center" style="width:10%">ID</th>
						<th class="center" style="width:15%">标题</th>
						<th class="center" style="width:15%">发布时间</th>
						<th class="center" class="center">状态</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty storyIslandList}">
						<c:forEach items="${storyIslandList}" var="story" varStatus="vs">
							<tr>
								<td class="center" style=" padding-bottom: 5px">
									<label><input type='checkbox' name='ids' value="${story.id }" /><span class="lbl"></span></label>
								</td>
								<td class="center">${story.id }</td>
								<td class="center">${story.title }</td>
								<td class="center">${story.releaseTime }</td>
								<td style="width: 5%;" class="center">
									<c:if test="${story.status == '0' }"><span class="label label-important arrowed-in">禁用</span></c:if>
									<c:if test="${story.status == '1' }"><span class="label label-success arrowed">开启</span></c:if>
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
			top.mainFrame.tabAddHandler("storyIslandList","故事章节列表","admin/storyIsland/chapterList.htm?id="+id+"&pageNO="+${page.currentPage});
		}
		
		//批量操作
		function choose(){
			
			var str = '';
			var count = 0;
			for(var i=0;i < document.getElementsByName('ids').length;i++)
			{
				  if(document.getElementsByName('ids')[i].checked){
					  str = document.getElementsByName('ids')[i].value;
			 		  count = count+1;
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
				return;
			}else{
				//如果多选，则判断
				if(count>1){
					bootbox.dialog("只能选择一条数据!", 
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
			}else{
				bootbox.confirm("确定跳转选中的故事吗？", function(result) {	
					if(result) {
						//编辑页面
						top.mainFrame.tabAddHandler("advertisementIslandList","故事岛轮播图资料","admin/advertisement/goEditAdvertisement.htm?id="+id);
						}
					});
				}
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

