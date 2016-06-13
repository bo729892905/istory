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
		
<div id="page-content" class="clearfix" >
						
  <div class="row-fluid" style="padding-top: 5% ">
	  <form class="form-horizontal" id="validation-form" method="post">
	  		<c:if test="${storyIslandChapter.chapterType==1}">
 		  		<textarea id="content" name="content" style="width: 50%;height:300px; " maxlength="10000">${storyIslandChapter.textStory}</textarea>
 		    </c:if>
 		    <c:if test="${storyIslandChapter.chapterType==2}">
 		  		<a href="${member.icon}" target="_blank"><img src="${storyIslandChapter.imgStoryUrl }" width="50%"></a>
 		    </c:if>
 		    <c:if test="${storyIslandChapter.chapterType==3}">
 		  		<audio controls="controls">  
  					 <source src="${storyIslandChapter.voiceStoryUrl}" >
 		  				<p>您的浏览器版本过低，暂不支持此浏览器</p>
				</audio> 
 		    </c:if>
 		    <input type="hidden" id="videoStoryUrl" value="${storyIslandChapter.videoStoryUrl }">
 		    <c:if test="${storyIslandChapter.chapterType==4}">
 			    <div class="video-player" align="center" id="a1">
 			    	<%-- <embed src="" autostart=false loop=false height=670 width=502 > --%>
			     </div>
 		    </c:if>
 	 </form>
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
 <!--  <div style="text-align: left; padding-left: 15%">
			<button class="btn btn-info" type="submit" id="submit"><i class="icon-ok"></i> 保存</button>
			&nbsp; &nbsp; &nbsp;
			<button class="btn" type="reset" id="reset"><i class="icon-undo"></i> 重置</button>
		</div> -->
	
</div><!--/#page-content-->
		
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="editor/main.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		
		<script type="text/javascript" src="resource/js/ckplayer/ckplayer.js" charset="utf-8"></script>
		<script type="text/javascript">
		
		$(top.hangge());
		
		</script>
		
		<script type="text/javascript">
		
		
		var contentEditor;
		$(function() {
			
			KindEditor.ready(function(K) {
				contentEditor = K.create('#content',{
		        	items:[
		        	        'source', '|','formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
		        	        'italic', 'underline', 'strikethrough', 'removeformat', 'justifyleft', 'justifycenter', 'justifyright',
		        	        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
		        	        'superscript', '|', 'preview', 'fullscreen'
		        	      ],
		        	 afterBlur: function () { this.sync(); },
		        	 urlType : 'domain',
		        	 uploadJson : $("base")[0].href+'editor/jsp/upload_json.jsp',
		        });
			});
			
			
			$("#reset").click(function(){
				contentEditor.html("");
			});
			
			$("#submit").click(function(){
				var data = {
						"content":contentEditor.html(),
					};
				$.post("admin/registerProtocol/edit.htm", data, function(request) {
					var json = eval('(' + request + ')'); 
					if(1 == json.status){
						bootbox.alert("保存成功!","确定");
					}else{
						bootbox.alert("保存失败，请稍后重试!","确定");
					}
				});
			});
			
	        var flashvars={
	    	        p:0,
	    	        e:0
	    	    };
	        var videoStoryUrl = $("#videoStoryUrl").val();
	   	    var video=[videoStoryUrl+'->video/mp4'];
	   	    var support=['all'];
	   	    CKobject.embedHTML5('a1','ckplayer_a1',600,400,video,flashvars,support);
		
		});
		

	/*        function playPause() {
	 
	       var myVideo = document.getElementsByTagName('video')[0];
	 
	       if (myVideo.paused){
	 
	           myVideo.play();
				}
				       else{
				 
				           myVideo.pause();
				}
	       }
	        */
	       
		</script>
		
	</body>
</html>

