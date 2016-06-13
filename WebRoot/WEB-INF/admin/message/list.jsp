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
			<form action="admin/message/list.htm" method="post" name="userForm" id="userForm">
			
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th class="center" style="width:5%"><label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label></th>
						<th class="center" style="width:5%">序号</th>
						<th class="center" style="width:30%">标题</th>
						<th class="center" style="width:20%">创建时间</th>
						<th class="center" style="width:4%">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty messageList }">
						<c:forEach items="${messageList}" var="message" varStatus="vs">
							<tr>
								<td class="center" style="vertical-align:middle;"><label><input type='checkbox' name='ids' value="${message.id }" /><span class="lbl"></span></label></td>
								<td class="center" style="vertical-align:middle;">${vs.index+1 }</td>
								<td class="center" style="vertical-align:middle;">${message.title }</td>
								<td class="center" style="vertical-align:middle;">${message.createTime }</td>
								<td  class="center" style="vertical-align:middle;padding-left:40px;" >
									<div class='hidden-phone visible-desktop btn-group'>
										<a class='btn btn-mini btn-info' title="修改" onclick="editUser('${message.id }');"><i class='icon-edit'></i></a>
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
					<a class="btn btn-small btn-primary" onclick="goAddMessage()">添加</a>
				</td>
				<td style="vertical-align:top;">
					<input class="span10" name="sendTime" id="sendTime" value="" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true,minDate:CurentTime()})" readonly="readonly" style="width:160px;" placeholder="发送日期"/>
					<a style="margin-bottom: 10px;" class="btn btn-small btn-primary" onclick="sendMessage()">发送</a>
				</td>
				<td style="vertical-align:top;">
					<a class="btn btn-small btn-danger" onclick="deleteAll()">删除</a>
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
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<!--时间  -->
		<script type="text/javascript" src="<%=basePath%>resource/js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
		
		$(top.hangge());
		
		function CurentTime() {
            var now = new Date();
            var year = now.getFullYear();      
            var month = now.getMonth() + 1;    
            var day = now.getDate();          
            var hh = now.getHours();           
            var mm = now.getMinutes();         
            var clock = year + "-";
            if (month < 10)
                clock += "0";
            clock += month + "-";
            if (day < 10)
                clock += "0";
            clock += day + " ";
            if (hh < 10)
                clock += "0";
            clock += hh + ":";
            if (mm < 10) clock += '0';
            clock += mm;
            return (clock);
        }
		
		//新增
		function goAddMessage(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="消息资料";
			 diag.URL = '<%=basePath%>admin/message/goAddMessage.htm';
			 diag.Width = 500;
			 diag.Height = 510;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('div_content').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location.reload()",100);
					 }else{
						 nextPage(${page.currentPage});
					 }
				}
				diag.close();
			 };
			 diag.show();
			 top.hangge();
		}
		
		
		//查看
		function editUser(id){
			top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="消息资料";
			 diag.URL = '<%=basePath%>admin/message/goEditMessage.htm?id='+id;
			 diag.Width = 500;
			 diag.Height = 510;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('div_content').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location.reload()",100);
					 }else{
						 nextPage(${page.currentPage});
					 }
				}
				diag.close();
			 };
			 diag.show();
			 top.hangge();
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
				bootbox.confirm("确定删除选中的消息吗？", function(result) {
					if(result) {
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>admin/message/deleteMessage.htm?tm='+new Date().getTime(),
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
		
		
		//批量删除
		function sendMessage(){
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
					bootbox.dialog("一次请选择一条消息发送!", 
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
					//发送操作
					bootbox.confirm("确定发送选中的消息吗？", function(result) {
						if(result) {
									top.jzts();
									$.ajax({
										type: "POST",
										url: '<%=basePath%>admin/message/sendMessage.htm?tm='+new Date().getTime(),
								    	data: {
								    		id:str,
								    		sendTime:$("#sendTime").val()
								    		},
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

