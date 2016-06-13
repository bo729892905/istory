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
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
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
			<form action="admin/advertisement/list.htm?type=1" method="post" name="userForm" id="userForm">
			
			<!-- 检索  -->
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th class="center" style="width:5%"><label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label></th>
						<th class="center" style="width:5%">ID</th>
						<th class="center" style="width:15%">标题</th>
						<th class="center" style="width:15%">广告图片</th>
						<th class="center" style="width:10%">链接方式</th>
						<th class="center" style="width:15%">页面链接</th>
						<th class="center" style="width:5%">链接Id</th>
						<th class="center" style="width:10%">点击量</th>
						<th class="center" style="width:5%">状态</th>
						<th class="center" style="width:5%">排序</th>
						<th class="center" style="width:5%">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty advertisementList}">
						<c:forEach items="${advertisementList}" var="var" varStatus="vs">
							<tr>
								<td class="center" style="vertical-align:middle;"><label><input type='checkbox' name='ids' value="${var.id}" /><span class="lbl"></span></label></td>
								<td class="center" style="vertical-align:middle;">${var.id}</td>
								<td class="center" style="vertical-align:middle;">${var.title}</td>
								<td class="center" style="vertical-align:middle;">
								<a href="${var.advertisementUrl }" title="${var.title}" class="bwGal"><img src="${var.advertisementUrl }" alt="${var.title}" width="100"></a>
								</td>
								<td class="center" style="vertical-align:middle;">
									<c:if test="${var.linkType == '0' }">链接网页</c:if>
									<c:if test="${var.linkType == '1' }">链接Id</c:if>
								</td>
								<td class="center linkUrl" style="vertical-align:middle;">
										${var.linkUrl}
								</td>
								<td class="center" style="vertical-align:middle;">${var.linkId}</td>
								<td class="center" style="vertical-align:middle;">${var.hit}</td>
								<td class="center" style="vertical-align:middle;">
									<c:if test="${var.status == '0' }"><span class="label label-important arrowed-in">禁用</span></c:if>
									<c:if test="${var.status == '1' }"><span class="label label-success arrowed">开启</span></c:if>
								</td>
								<td class="center" style="vertical-align:middle;">
									<div class='hidden-phone visible-desktop btn-group'>
											<a <c:if test="${vs.index == 0}"> class='btn btn-mini disabled' title="升序" </c:if> <c:if test="${vs.index!=0}"> class='btn btn-mini  btn-info' title="升序"  onclick="upOrDown('${var.id }','${var.number }','1')" </c:if>><i class='icon-arrow-up'></i></a>
											<a <c:if test="${vs.index == 4}"> class='btn btn-mini disabled' title="降序" </c:if> <c:if test="${vs.index!=4}"> class='btn btn-mini  btn-info' title="降序"  onclick="upOrDown('${var.id }','${var.number }','0')" </c:if> ><i class='icon-arrow-down'></i></a>
									</div>
								</td>
								<td class="center" style="vertical-align:middle;padding-left:16px;">
									<div class='hidden-phone visible-desktop btn-group'>
											<a class='btn btn-mini btn-info' title="编辑"  onclick="editUser('${var.id }')"><i class='icon-edit'></i></a>
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
								<a class="btn btn-small btn-success" onclick="makeAll('1')">开启</a>
							</td>
								<td style="vertical-align:top;">
								<a class="btn btn-small btn-danger" onclick="makeAll('0')">禁用</a>
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
	<!-- 	
		返回顶部 
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a> -->
			<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<!-- 引入 -->
		
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		
		<script type="text/javascript" src="plugins/zoomimage/js/jquery.js"></script>
	    <script type="text/javascript" src="plugins/zoomimage/js/eye.js"></script>
	    <script type="text/javascript" src="plugins/zoomimage/js/utils.js"></script>
	    <script type="text/javascript" src="plugins/zoomimage/js/zoomimage.js"></script>
	    <script type="text/javascript" src="plugins/zoomimage/js/layout.js"></script>
		
		<script type="text/javascript">
		
		$(top.hangge());
		
		//页面加载之后,设置.样式.
		jQuery(function(){
		     //使用id选择器;例如:tab对象->tr->td对象.
		    $(".linkUrl").each(function(i){
		         //获取td当前对象的文本,如果长度大于25;
		         if($(this).text().length>25){
		                //给td设置title属性,并且设置td的完整值.给title属性.
		$(this).attr("title",$(this).text());
		                //获取td的值,进行截取。赋值给text变量保存.
		var text=$(this).text().substring(0,25)+"...";
		                //重新为td赋值;
		                $(this).text(text);
		         }
		      });
		});
		
		
		
		
		
		
		//检索
		function search(){
			top.jzts();
			$("#userForm").submit();
		}
		//查看
		function editUser(id){
			//编辑页面
			top.mainFrame.tabAddHandler("advertisementIslandList","故事岛轮播图资料","admin/advertisement/goEditAdvertisement.htm?id="+id);
		}
		
		//修改
		function upOrDown(id,number,type){
			top.jzts();
			$.ajax({
				type: "POST",
				url: '<%=basePath%>admin/advertisement/updateNumber.htm?tm='+new Date().getTime(),
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
		
		//开启
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
					msg = "确定要禁用选中的轮播吗?";
				}else{
					msg = "确定要启用选中的轮播吗?";
				}
				bootbox.confirm(msg, function(result) {
					if(result) {
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>admin/advertisement/updateStatus.htm?tm='+new Date().getTime(),
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

