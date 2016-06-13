<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">

<!-- jsp文件头和头部 -->
<%@ include file="top.jsp"%>

</head>
<body>

	<div class="container-fluid" id="main-container">
		

			<div id="page-content" class="clearfix">

				<div class="page-header position-relative">
					<h1>
						welcome,<c:if test="${adminUser!=null}">
						        		${adminUser.name}
						        	</c:if>
						        	<small><i class="icon-double-angle-right"></i> </small>
					</h1>
				</div>
				<!--/page-header-->

				<div class="row-fluid">
					<div class="space-6"></div>
					<div class="row-fluid">
						<h6 class="left page-header" style="font-weight:bolder;">最新文章</h6>
						
							<table id="table_story" class="table table-striped  "  style=" width: 87%">
								<tbody>
									<!-- 开始循环 -->	
									<c:choose>
										<c:when test="${not empty storyList}">
											<c:forEach items="${storyList}" var="story" varStatus="vs">
												<tr>
													<td class="center">ID：${story.id }</td>
													<td class="center">${story.title }</td>
													<td class="center"><c:if test="${story.type == '1' }">故事岛</c:if>
														<c:if test="${story.type == '2'  }">微电影</c:if>
														<c:if test="${story.type == '3' }">剧本</c:if>
													<td class="center">${story.author }</td>
													<td style="text-align: right">发布时间：${story.createTime }</td>
													<td style="text-align: right"><c:if test="${adminRoleList.member == '1'}"><a style="cursor: pointer" onclick="toStory('${story.type}')" >查看</a></c:if></td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr class="main_info">
												<td colspan="100" class="left" >没有相关数据</td>
											</tr>
										</c:otherwise>
									</c:choose>
							</tbody>
						</table>

					</div>
				</div>
				<div class="row-fluid">
					<div class="space-6"></div>
					<div class="row-fluid">
						<h6 class="left page-header" style="font-weight:bolder;">最新用户</h6>
						
							<table id="table_report" class="table table-striped  "  style=" width: 80%">
								<tbody>
									<!-- 开始循环 -->	
									<c:choose>
										<c:when test="${not empty memberList}">
											<c:forEach items="${memberList}" var="member" varStatus="vs">
												<tr>
													<td class="center">ID：${member.id }</td>
													<td class="center">${member.mobile }</td>
													<td class="center"><c:if test="${member.source == '1' }">注册</c:if>
														<c:if test="${member.source == '2' }">QQ</c:if>
														<c:if test="${member.source == '3' }">微博</c:if>
														<c:if test="${member.source == '4' }">微信</c:if></td>
													<td class="center">${member.name }</td>
													<td style="text-align: right">注册时间：${member.createTime }</td>
												</tr>
											</c:forEach>
												<tr>
													<td class="center"></td>
													<td class="center"></td>
													<td class="center"></td>
													<td class="center"></td>
													<td style="text-align: right"><c:if test="${adminRoleList.member == '1'}"><a style="cursor: pointer" onclick="toMember()" >查看</a></c:if></td>
												</tr>
										</c:when>
										<c:otherwise>
											<tr class="main_info">
												<td colspan="100" class="left" >没有相关数据</td>
											</tr>
										</c:otherwise>
									</c:choose>
							</tbody>
						</table>
						<h6 class="left" style="font-weight:bolder;"></h6>
						
						<!--  <div class="left">
							  <div class="container"> 
							  
								<div class="row">
									  <div class=" col-xs-4"> .col-md-4</div>
									  <div class=" col-xs-4">.col-md-4</div>
									  <div class=" col-xs-4">.col-md-4</div>
								</div>
								
							</div>
						</div>
					</div> -->
				</div>
				
				<!--/row-->
		</div>
		<!-- #main-content -->
	</div>
	<!--/.fluid-container#main-container-->
	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse"> <i
		class="icon-double-angle-up icon-only"></i>
	</a>
	<!-- basic scripts -->
	<script src="static/1.9.1/jquery.min.js"></script>
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>

	<script src="static/js/bootstrap.min.js"></script>
	<!-- page specific plugin scripts -->

	<!--[if lt IE 9]>
		<script type="text/javascript" src="static/js/excanvas.min.js"></script>
		<![endif]-->
	<script type="text/javascript" src="static/js/jquery-ui-1.10.2.custom.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.ui.touch-punch.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.easy-pie-chart.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.sparkline.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.flot.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.flot.pie.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.flot.resize.min.js"></script>
	<!-- ace scripts -->
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<!-- inline scripts related to this page -->


	<script type="text/javascript">

		$(top.hangge());
		function toMember(){
				//编辑
				top.mainFrame.tabAddHandler("memberList","用户列表","admin/member/list.htm?");
		}
		
		function toStory(type){
			if(type==1){
				top.mainFrame.tabAddHandler("storyIslandList","故事岛列表","admin/storyIsland/list.htm?");
			}else if(type==2){
				top.mainFrame.tabAddHandler("microFilmList","微电影列表","admin/microFilm/list.htm");
			}else if(type==3){
				top.mainFrame.tabAddHandler("scriptFactoryList","剧本工厂列表","admin/scriptFactory/list.htm");
			}
	}
		
	</script>
</body>
</html>
