﻿<%
	String pathh = request.getContextPath();
	String basePathh = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+pathh+"/";
%>
<div class="navbar navbar-inverse">
		  <div class="navbar-inner">
		   <div class="container-fluid">
			  <a class="brand" href="javascript:void(0);"><small><i class="icon-leaf"></i> YOUGUSHI后台管理系统</small> </a>
			  <ul class="nav ace-nav pull-right">
					<li class="light-blue user-profile">
						<a class="user-menu dropdown-toggle" href="javascript:alert('预留功能,待开发');" data-toggle="dropdown">
							<img alt="FH" src="static/avatars/user.jpg" class="nav-user-photo" />
							<span id="user_info" style="top:15px">
					 			<c:if test="${adminUser!=null}">
					        		${adminUser.name}
					        	</c:if>
							</span>
							<i class="icon-caret-down"></i>
						</a>
						<ul id="user_menu" class="pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-closer">
							<li><a onclick="editUserH(${adminUser.id});" style="cursor:pointer;"><i class="icon-user"></i> 修改资料</a></li>
							<li class="divider"></li>
							<li><a href="admin/loginout.htm"><i class="icon-off"></i> 退出</a></li>
						</ul>
					</li>
			  </ul><!--/.ace-nav-->
		   </div><!--/.container-fluid-->
		  </div><!--/.navbar-inner-->
		</div><!--/.navbar-->
	
	
		<script type="text/javascript">
			
			//菜单状态切换
			var fmid = "fhindex";
			var mid = "fhindex";
			function siMenu(id,fid,MENU_NAME,MENU_URL){
				if(id != mid){
					$("#"+mid).removeClass();
					mid = id;
				}
				if(fid != fmid){
					$("#"+fmid).removeClass();
					fmid = fid;
				}
				$("#"+fid).attr("class","active open");
				$("#"+id).attr("class","active");
				top.mainFrame.tabAddHandler(id,MENU_NAME,MENU_URL);
				if(MENU_URL != "druid/index.html"){
					jzts();
				}
			}
			
			function toMenu(id,MENU_NAME,MENU_URL){
				top.mainFrame.tabAddHandler2(id,MENU_NAME,MENU_URL);
					jzts();
				
			}
			
		
			$(function(){
			
				//换肤
				$("#skin-colorpicker").ace_colorpicker().on("change",function(){
					var b=$(this).find("option:selected").data("class");
					hf(b);
					var url = '<%=basePathh%>/head/setSKIN.do?SKIN='+b+'&tm='+new Date().getTime();
					$.get(url,function(data){});
				
				})
			})

			var USER_ID;
		
		<%-- 	function getUname(){
				$.ajax({
					type: "POST",
					url: '<%=basePathh%>/head/getUname.do?tm='+new Date().getTime(),
			    	data: encodeURI(""),
					dataType:'json',
					//beforeSend: validateData,
					cache: false,
					success: function(data){
						//alert(data.list.length);
						 $.each(data.list, function(i, list){
							 
							 //登陆者资料
							 $("#user_info").html('<small>Welcome</small> '+list.NAME+'');
							 
							 
							 USER_ID = list.USER_ID;//用户ID
							 hf(list.SKIN)//皮肤
							 
							 if(list.USERNAME != 'admin'){
								 $("#adminmenu").hide();	//隐藏菜单设置
								 $("#adminzidian").hide();	//隐藏数据字典
								 $("#systemset").hide();	//隐藏系统设置
								 $("#productCode").hide();	//隐藏代码生成
							 }
							 
						 });
					}
				});
			}
			getUname(); --%>
			
			function hf(b){
				
				var a=$(document.body);
				a.attr("class",a.hasClass("navbar-fixed")?"navbar-fixed":"");
				if(b!="default"){
					a.addClass(b)
				}if(b=="skin-1"){
					$(".ace-nav > li.grey").addClass("dark")
				}else{
					$(".ace-nav > li.grey").removeClass("dark")
				}
				if(b=="skin-2"){
						$(".ace-nav > li").addClass("no-border margin-1");
						$(".ace-nav > li:not(:last-child)").addClass("white-pink")
						.find('> a > [class*="icon-"]').addClass("pink").end()
						.eq(0).find(".badge").addClass("badge-warning");
				}else{
						$(".ace-nav > li").removeClass("no-border").removeClass("margin-1");
						$(".ace-nav > li:not(:last-child)").removeClass("white-pink")
						.find('> a > [class*="icon-"]').removeClass("pink").end()
						.eq(0).find(".badge").removeClass("badge-warning");
				}
				if(b=="skin-3"){
					$(".ace-nav > li.grey").addClass("red").find(".badge").addClass("badge-yellow");
				}else{
					$(".ace-nav > li.grey").removeClass("red").find(".badge").removeClass("badge-yellow");
				}
			}
			
			//修改个人资料
			function editUserH(id){
				 jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="个人资料";
				 diag.URL = '<%=basePathh%>/admin/goEditU.htm?id='+id;
				 diag.Width = 500;
				 diag.Height = 510;
				 diag.CancelEvent = function(){ //关闭事件
					diag.close();
				 };
				 diag.show();
				 top.hangge();
			}
			
			//系统设置
			function editSys(){
				 jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="系统设置";
				 diag.URL = '<%=basePathh%>/head/goSystem.do';
				 diag.Width = 600;
				 diag.Height = 596;
				 diag.CancelEvent = function(){ //关闭事件
					diag.close();
				 };
				 diag.show();
			}
		
			//代码生成
			function productCode(){
				 jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="代码生成器";
				 diag.URL = '<%=basePathh%>/head/goProductCode.do';
				 diag.Width = 800;
				 diag.Height = 450;
				 diag.CancelEvent = function(){ //关闭事件
					hangge();
					diag.close();
				 };
				 diag.show();
			}
		
			//数据字典
			function zidian(){
				 jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="数据字典";
				 diag.URL = '<%=basePathh%>/dictionaries.do?PARENT_ID=0';
				 diag.Width = 799;
				 diag.Height = 460;
				 diag.CancelEvent = function(){ //关闭事件
					diag.close();
				 };
				 diag.show();
				 
			}
		
			//菜单
			function menu(){
				 jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="菜单编辑";
				 diag.URL = '<%=basePathh%>/menu.do';
				 diag.Width = 720;
				 diag.Height = 390;
				 diag.CancelEvent = function(){ //关闭事件
					diag.close();
				 };
				 diag.show();
				 
			}
			
			//切换菜单
			function changeMenu(){
				window.location.href='<%=basePathh%>main/yes';
			}
			
			//清除加载进度
			function hangge(){
				$("#jzts").hide();
			}
			
			//显示加载进度
			function jzts(){
				$("#jzts").show();
			}
		</script>
