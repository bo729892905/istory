<%
	String pathl = request.getContextPath();
	String basePathl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+pathl+"/";
%>
		<!-- 本页面涉及的js函数，都在head.jsp页面中     -->
		<div id="sidebar" class="menu-min">

				<div id="sidebar-shortcuts">

				</div>
				<!-- #sidebar-shortcuts -->


				<ul class="nav nav-list">

				<li class="active" id="fhindex">
				  <a ><i class="icon-dashboard"></i><span>导航栏</span></a>
				</li>
				<c:if test="${adminRoleList.system == '1'}">
				<li class="" id="adminSys">
				    <a href="#" class="dropdown-toggle" style="height: 28px" ><i class="icon-desktop"></i><span>系统管理</span><b class="arrow icon-angle-down"></b></a>
					  <ul class="submenu">
					  		<li><a style="cursor:pointer;" target="mainFrame" id="adminList" onclick="siMenu('adminList','adminSys','管理员列表','admin/list.htm')"><i class="icon-double-angle-right"></i>管理员列表</a></li>
					  		<li><a style="cursor:pointer;" target="mainFrame" id="adminRoleList" onclick="siMenu('adminRoleList','adminSys','预设角色列表','admin/adminRole/list.htm')"><i class="icon-double-angle-right"></i>预设角色权限</a></li>
					  		<li><a style="cursor:pointer;" target="mainFrame" id="systemLogList"  onclick="siMenu('systemLogList','adminSys','系统日志列表','admin/systemLog/list.htm')"><i class="icon-double-angle-right"></i>系统日志</a></li>
					  		<li><a style="cursor:pointer;" target="mainFrame" id="registerProtocol"  onclick="siMenu('registerProtocol','adminSys','注册协议','admin/registerProtocol/protocol.htm')"><i class="icon-double-angle-right"></i>注册协议</a></li>
					  </ul>
			    </li>
			    </c:if>
			    <c:if test="${adminRoleList.member == '1'}">
			    <li id="memberM">
				    <a href="#" class="dropdown-toggle" style="height: 28px"><i class="icon-desktop"></i><span>用户管理</span><b class="arrow icon-angle-down"></b></a>
					  <ul class="submenu">
					  		<li><a style="cursor:pointer;" target="mainFrame" id='memberList' onclick="siMenu('memberList','memberM','用户列表','admin/member/list.htm')"><i class="icon-double-angle-right"></i>用户列表</a></li>
					  </ul>
			    </li>
			    </c:if>
			    <c:if test="${adminRoleList.storyIsland == '1'}">
			    <li id="storyIslandM">
				    <a href="#" class="dropdown-toggle"  style="height: 28px"><i class="icon-desktop"></i><span>故事岛管理</span><b class="arrow icon-angle-down"></b></a>
					  <ul class="submenu">
					  		<li><a style="cursor:pointer;" target="mainFrame" id="storyIslandList"  onclick="siMenu('storyIslandList','storyIslandM','故事岛列表','admin/storyIsland/list.htm')"><i class="icon-double-angle-right"></i>故事岛列表</a></li>
					  		<li><a style="cursor:pointer;" target="mainFrame" id="storyIslandClassifyList"  onclick="siMenu('storyIslandClassifyList','storyIslandM','故事岛分类列表','admin/storyIslandClassify/list.htm')"><i class="icon-double-angle-right"></i>故事岛分类列表</a></li>
					  </ul>
			    </li>
			    </c:if>
			    <c:if test="${adminRoleList.microFilm == '1'}">
			    <li id="microFilmM">
				    <a href="#" class="dropdown-toggle" style="height: 28px"><i class="icon-desktop"></i><span>微电影管理</span><b class="arrow icon-angle-down"></b></a>
					  <ul class="submenu">
					  		<li><a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('microFilmList','microFilmM','微电影列表','admin/microFilm/list.htm')"><i class="icon-double-angle-right"></i>影院列表</a></li>
					  		<li><a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('uploadInstructions','microFilmM','上传说明','admin/uploadInstructions/info.htm')"><i class="icon-double-angle-right"></i>上传说明</a></li>
					  </ul>
			    </li>
			    </c:if>
			    <c:if test="${adminRoleList.scriptFactory == '1'}">
			    <li id="scriptFactoryM">
				    <a href="#" class="dropdown-toggle" style="height: 28px"><i class="icon-desktop"></i><span>剧本工厂管理</span><b class="arrow icon-angle-down"></b></a>
					  <ul class="submenu">
					  		<li><a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('scriptFactoryList','scriptFactoryM','剧本工厂列表','admin/scriptFactory/list.htm')"><i class="icon-double-angle-right"></i>剧本工厂列表</a></li>
					  </ul>
			    </li>
			    </c:if>
			    <c:if test="${adminRoleList.starStory == '1'}">
			    <li id="starStoryM">
				    <a href="#" class="dropdown-toggle" style="height: 28px" ><i class="icon-desktop"></i><span>星故事管理</span><b class="arrow icon-angle-down"></b></a>
					  <ul class="submenu">
					  		<li><a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('starStoryList','starStoryM','星故事列表','admin/starStory/list.htm')"><i class="icon-double-angle-right"></i>星故事列表</a></li>
					  </ul>
			    </li>
			    </c:if>
			    <c:if test="${adminRoleList.comment == '1'}">
			    <li id="commentM">
				    <a href="#" class="dropdown-toggle" style="height: 28px"><i class="icon-desktop"></i><span>评论管理</span><b class="arrow icon-angle-down"></b></a>
					  <ul class="submenu">
					  		<li><a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('commentList','commentM','评论列表','admin/comment/list.htm')"><i class="icon-double-angle-right"></i>评论列表</a></li>
					  		<li><a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('filterWordsList','commentM','过滤字列表','admin/filterWords/list.htm')"><i class="icon-double-angle-right"></i>过滤字管理</a></li>
					  </ul>
			    </li>
			    </c:if>
			    <c:if test="${adminRoleList.advertisement == '1'}">
			    <li id="advertisementM">
				    <a href="#" class="dropdown-toggle" style="height: 28px"><i class="icon-desktop"></i><span>广告管理</span><b class="arrow icon-angle-down"></b></a>
					  <ul class="submenu">
					  		<li><a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('advertisementIslandList','advertisementM','故事岛轮播列表','admin/advertisement/list.htm?type=1')"><i class="icon-double-angle-right"></i>故事岛轮播</a></li>
					  		<li><a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('advertisementFilmList','advertisementM','微电影轮播列表','admin/advertisement/list.htm?type=2')"><i class="icon-double-angle-right"></i>微电影轮播</a></li>
					  		<li><a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('advertisementStarList','advertisementM','剧本工厂轮播列表','admin/advertisement/list.htm?type=3')"><i class="icon-double-angle-right"></i>剧本工厂轮播</a></li>
					  </ul>
			    </li>
			    </c:if>
			    <c:if test="${adminRoleList.cover == '1'}">
			    <li id="coverM">
				    <a href="#" class="dropdown-toggle" style="height: 28px"><i class="icon-desktop"></i><span>封面管理</span><b class="arrow icon-angle-down"></b></a>
					  <ul class="submenu">
					  		<li><a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('coverList','coverM','封面列表','admin/cover/list.htm?')"><i class="icon-double-angle-right"></i>封面列表</a></li>
					  </ul>
			    </li>
			    </c:if>
			    <c:if test="${adminRoleList.message == '1'}">
			    <li id="messageM">
				    <a href="#" class="dropdown-toggle" style="height: 28px"><i class="icon-desktop"></i><span>推送管理</span><b class="arrow icon-angle-down"></b></a>
					  <ul class="submenu">
					  		<li><a style="cursor:pointer;" target="mainFrame" onclick="siMenu('messageList','messageM','消息列表','admin/message/list.htm?')"><i class="icon-double-angle-right"></i>消息列表</a></li>
					  		<li><a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('messagePushList','messageM','推送日志列表','admin/messagePush/list.htm?')"><i class="icon-double-angle-right"></i>推送日志</a></li>
					  </ul>
			    </li>
			    </c:if>
			    <c:if test="${adminRoleList.feedback == '1'}">
			    <li id="feedbackM">
				    <a href="#" class="dropdown-toggle" style="height: 28px"><i class="icon-desktop"></i><span>意见反馈管理</span><b class="arrow icon-angle-down"></b></a>
					  <ul class="submenu">
					  		<li><a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('feedbackList','feedbackM','意见反馈列表','admin/feedback/list.htm')"><i class="icon-double-angle-right"></i>意见反馈列表</a></li>
					  </ul>
			    </li>
			    </c:if>
			    <c:if test="${adminRoleList.report == '1'}">
			    <li id="reportM">
				    <a href="#" class="dropdown-toggle" style="height: 28px"><i class="icon-desktop"></i><span>举报管理</span><b class="arrow icon-angle-down"></b></a>
					  <ul class="submenu">
					  		<li><a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('reportList','reportM','举报列表','admin/report/list.htm')"><i class="icon-double-angle-right"></i>举报列表</a></li>
					  		<li><a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('tagList','reportM','标签列表','admin/report/tagList.htm')"><i class="icon-double-angle-right"></i>标签列表</a></li>
					  </ul>
			    </li>
			    </c:if>
			    <c:if test="${adminRoleList.aboutUs == '1'}">
			     <li id="aboutUsM">
				    <a href="#" class="dropdown-toggle" style="height: 28px" ><i class="icon-desktop"></i><span>关于我们</span><b class="arrow icon-angle-down"></b></a>
					  <ul class="submenu">
					  		<li><a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('aboutUs','aboutUsM','关于我们','admin/aboutUs/aboutUs.htm')"><i class="icon-double-angle-right"></i>关于我们</a></li>
					  </ul>
			    </li>
				</c:if>
				</ul><!--/.nav-list-->

				<div id="sidebar-collapse"><i class="icon-double-angle-left"></i></div>

			</div><!--/#sidebar-->

