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
	<!--查看图片插件 -->
	<link rel="stylesheet" media="screen" type="text/css" href="plugins/zoomimage/css/zoomimage.css" />
    <link rel="stylesheet" media="screen" type="text/css" href="plugins/zoomimage/css/custom.css" />
   
	<!--查看图片插件 -->
	 
	</head>
<body>
		
		
<div class="container-fluid" id="main-container">


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">

	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="admin/cover/list.htm" method="post" name="userForm" id="userForm">
			
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th class="center" style="width:5%"><label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label></th>
						<th class="center" style="width:5%">ID</th>
						<th class="center" style="width:15%">封面图片</th>
						<th class="center" style="width:15%">发布时间</th>
						<th class="center" style="width:15%">发布人</th>
						<th class="center" style="width:4%">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty coverList }">
						<c:forEach items="${coverList}" var="cover" varStatus="vs">
							<tr>
								<td class="center" style="vertical-align:middle;"><label><input type='checkbox' name='ids' value="${cover.id }" /><span class="lbl"></span></label></td>
								<td class="center" style="vertical-align:middle;">${cover.id }</td>
								<td class="center" style="vertical-align:middle;">
								<a href="${cover.cover }" title="" class="bwGal"><img src="${cover.cover }" alt="" width="100"></a>
								</td>
								<td class="center" style="vertical-align:middle;">${cover.createTime }</td>
								<td class="center" style="vertical-align:middle;">${cover.name }</td>
								<td  class="center" style="vertical-align:middle;padding-left:40px;" >
									<div class='hidden-phone visible-desktop btn-group'>
										<a class='btn btn-mini btn-info' title="查看" onclick="editUser('${cover.id }');"><i class='icon-edit'></i></a>
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
					<a class="btn btn-small btn-primary" onclick="goAddCover();">添加</a>
				</td>
				<td style="vertical-align:top;">
					<a class="btn btn-small btn-danger" onclick="deleteAll();">删除</a>
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
		
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		
		<script type="text/javascript" src="plugins/zoomimage/js/jquery.js"></script>
	    <script type="text/javascript" src="plugins/zoomimage/js/eye.js"></script>
	    <script type="text/javascript" src="plugins/zoomimage/js/utils.js"></script>
	    <script type="text/javascript" src="plugins/zoomimage/js/zoomimage.js"></script>
	    <script type="text/javascript" src="plugins/zoomimage/js/layout.js"></script>
		<script type="text/javascript">
		
		$(top.hangge());
		
		function goAddCover(){
			$.ajax({
				type: "POST",
				url: '<%=basePath%>admin/cover/getNumber.htm?tm='+new Date().getTime(),
				dataType:'json',
				//beforeSend: validateData,
				cache: false,
				success: function(data){
					if(data.status==1){
						if(data.number<30){
							//新增
							top.mainFrame.tabAddHandler("coverList","新增封面","admin/cover/goAddCover.htm?");
						}else{
							bootbox.dialog("封面数量已达到最大上限30个，不能再添加了！", 
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
						}
					}else{
						bootbox.alert("操作失败，请稍后重试!","确定");
					}
				}
			});
		}
		
		//查看
		function editUser(id){
			//编辑
			top.mainFrame.tabAddHandler("coverList","编辑封面","admin/cover/goEditCover.htm?id="+id);
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
				bootbox.confirm("确定删除选中的封面吗？", function(result) {
					if(result) {
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>admin/cover/deleteCover.htm?tm='+new Date().getTime(),
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
		<style type="text/css">
		li {list-style-type:none;}
		</style>
		<ul class="navigationTabs">
            <li><a></a></li>
            <li></li>
        </ul>
	</body>
</html>

