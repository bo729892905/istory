<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="../basePath.jsp" %> 
<head>
<meta charset="utf-8">
<title>
<c:if test="${type == 1}">
	${storyIsland.title}
</c:if>
<c:if test="${type == 2}">
	${microFilm.title}
</c:if>
<c:if test="${type == 3}">
	${title}
</c:if>
<c:if test="${type == 4}">
	${starStory.title}
</c:if>
</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<link href="resource/scss/swiper.min.css" rel="stylesheet" type="text/css" />
<link href="resource/scss/app-common.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="resource/fmbox/css/reset.css">
<link rel="stylesheet" href="resource/fmbox/css/style.css" media="screen" type="text/css" />
<style type="text/css">

.text1{
	text-decoration: line-through;
	color: gray;
	padding: 5px;
}

h3 {
	font-family: Arial, sans-serif;
	font-size: 18px;
	color: #fff;
	padding: 10px;
	background-color: gray;
}
.img_icon{
	margin-top:10px;
	float: left;
	width: 100%;
	border-bottom: 2px solid #ccc;
	padding-bottom:5px;
	z-index: 999999;
}
.s_comment{
	margin-top:10px;
	float: left;
	width: 100%;
	border-top: 1px solid #ccc;
	padding-top:5px;
}
.icon{
	border-radius:50%
}
span.swiper-pagination-bullet:not(.swiper-pagination-bullet-active){
	    opacity: 1;
	    background: rgba(0,0,0,0.2);
}
.textSty{padding-top: 85px; padding-bottom: 50px}
.scriptSty{margin-left:5px;margin-right:5px; padding-top: 5px; padding-bottom: 50px}
.wrapper_qr { width:100%; padding:10px; box-sizing:border-box; text-align:center;}
a.btn { display:inline-block; border:1px solid #04B4AE; border-radius:3px; color:#04B4AE; line-height:30px; width:150px; margin:0 10px; text-decoration:none;}
a.btn:active { background:#fff; color:#676767; }

/*遮罩  */
 #poplayer  
    {  
        position:absolute;  
        z-index:8888;
        top:50%; 
        left:50%; 
        margin:-47px 0 0 -46px;
    }  
    #covered  
    {  
        position:relative;
        width:100%;  
        height:300px;  
        text-align:center;
        margin-bottom:20px;
        overflow: hidden;
    } 
     #covered img{ height:300px; } 
     .rel{position:relative;}
     .blankDiv{margin-bottom:120px; }
</style>
</head>
<body>
	<ul style="display: none" class="tab clearfix">
		<li class="story active"><i class="iicon i-story"></i>故事</li>
		<li class="movie" ><i class="iicon i-movie"></i>电影</li>
	</ul>

  <div class="swiper-container swiper-container-h">
		<c:if test="${type == 1 || storyIsland != ''}">
		 <div class="img_icon story-container" >
				<div style="margin-left: 10px">
					<img class="icon" style="float: left;width: 60px;height: 60px" src="${storyIsland.icon}"/> 
					<div class="" style=" margin-left:60px; ">
					     <p style="font-size:16px; padding-top: 5px; color: #444444">${storyIsland.author}</p>
					     <p style="font-size:13px; padding-top: 5px;">${storyIsland.releaseTime}</p>
				 	</div>
				</div>
			</div>
		</c:if>
		<div class="swiper-wrapper story-container">
	 	<c:if test="${type == 1 || storyIsland != ''}">
	 		<c:forEach var="sc" items="${list }" varStatus="status">
				<div class="swiper-slide flash">
			   <!-- 故事内容 -->
			   <div>
			 	<!-- 图文 -->
				<c:if test="${sc.chapterType == 1}"><div style="word-wrap:break-word;min-height:200px; ">${sc.textStory}</div></c:if>
				<!-- 图片 -->
				<c:if test="${sc.chapterType == 2}"><div style="margin-left:5px;margin-right:5px;"><img style="width:100%;" src="${sc.imgStoryUrl}"></div></c:if>
				<!-- 音频 -->
				<c:if test="${sc.chapterType == 3}">
				<%-- <div style="text-align: center;">
		 		  		<audio controls="controls">  
		  					 <source src="${sc.voiceStoryUrl}" >
		 		  				<p>您的浏览器版本过低，暂不支持此浏览器</p>
						</audio> 
				</div> --%>
				<input type="hidden" id="voiceStoryUrl" value="${sc.voiceStoryUrl }" >
				<input type="hidden" id="cover" value="${storyIsland.cover }" >
				<div id="jm${status.index}" class="music-player">
						<div class="zj"></div>
						
					
						<div class="controls">
							<div class="play-controls">
								<!-- <a href="javascript:;" class="icon-previous jp-previous" title="previous"></a> -->
								<a href="javascript:;" class="icon-play jp-play" title="play"></a>
								<a href="javascript:;" class="icon-pause jp-pause" title="pause"></a>
								<!-- <a href="javascript:;" class="icon-next jp-next" title="next"></a> -->
							</div>
							<!-- <div class="volume-level jp-volume-bar">
								<span class="jp-volume-bar-value" style="width: 0%"></span>
								<a href="javascript:;" class="icon-volume-up jp-volume-max" title="max volume" ></a>
								<a href="javascript:;" class="icon-volume-down jp-mute" title="mute"></a>
							</div> -->
						</div>
						<div class="info">
							<div class="center">
								<div class="jp-playlist">
									<ul>
										<li></li>
									</ul>
								</div>
							
							</div>
							<div class="progress jp-seek-bar">
								<span class="jp-play-bar" style="width: 0%"></span>
							</div>
						</div>
						
						<div id="jp${status.index}" class="jp-jplayer"></div>
					
					</div>
				
				
				
			    </c:if>
				<!-- 视频 -->
				<input type="hidden" id="videoStoryUrl" value="${sc.videoStoryUrl }" >
				<input type="hidden" id="img" value="${sc.img }" >
			    <c:if test="${sc.chapterType==4}">
			    <div style="margin-left:5px; margin-bottom: 10px;">
			    <div  class="video-player" style=" margin: 0 auto" id="a${status.index}"></div>
			    </div>
			    </c:if>
				</div>
				
				<!--评论内容  -->
			<div style=" font-weight:bold; height:20px;  padding-left:10px;background:#E6E6E6;">热门评论</div>
			  <c:if test="${commentList != null && not empty  commentList }">
				<c:forEach var="comment" items="${commentList }">
				 <div class="s_comment" >
					<div class="">
					  <img class="icon" style="float: left;margin-left: 10px; max-width: 40px;max-height: 40px" src="${comment.icon}"/>
					  <div class="" style=" margin-left:60px;">
					     <p>${comment.name}</p>
					     <p>${comment.createTime}</p>
					     <p style="padding-top:10px; padding-right:5px;color:black">${comment.comment}</p>
					  </div>
					</div>
				</div>
				</c:forEach>
				 <div class="s_comment commentList1" >
					  <div class="blankDiv"></div>
			     </div>
			</c:if>
			<c:if test="${empty  commentList || commentList == null}">
				<div class="blankDiv" style="z-index:888;  text-align: center;  height:200px; ">
					<br>
					&nbsp;
					<div >
					  暂无评论
					</div>
					<br>
					&nbsp;
					<br>
				</div>
			</c:if>
				
				
			</div>
			
			</c:forEach>
		</c:if>
	</div>
	
	<div style="" class="swiper-pagination swiper-pagination-h"></div>
	 <div class="floatbox">
		<!-- <div style="position:absolute;top:0;right:0;">X</div> -->
	</div> 
</div>
 <div class="content2">
			<div class="">
			<!--微电影内容  -->
			 <c:if test="${type == 2 || microFilm != '' }">
				 <div class="rel microFilmClass">
					 <input type="hidden"  value="${microFilm.vedioUrl }" >
					<div id="covered"><img style="" src="${microFilm.cover}"> </div>
					<div id="poplayer"><img src="resource/images/play.png"></div>
				</div> 
				<div class="microFilmClass">
					  <img  style="float: left;margin-left: 5px; width:100px !important;" src="${microFilm.cover}"/>
					  <div class="" style=" margin-left:110px; margin-right: 5px;">
					     <p>题材：${microFilm.title}</p>
					     <p>导演：${microFilm.director}</p>
					     <p>编剧：${microFilm.scriptwriter}</p>
					     <p>主演：${microFilm.actor}</p>
					     <p class="mcontent">剧情：${microFilm.content}</p>
					  </div>
				</div>
			</c:if> 
			<!--剧本内容  -->
			<c:if test="${type == 3}">
				<div class="scriptSty">
					<div >${scriptChapter.text} </div>
				</div>
			</c:if>
			<!--星故事内容  -->
			<c:if test="${type == 4}">
				<div class="">
					<c:if test="${starStory.type == 1}">
						<div style="margin-left:5px;margin-right:5px; margin-bottom: 10px"> <img width="100%" src="${starStory.cover}"> </div>
						<div style="margin-left:5px;margin-right:5px; margin-bottom: 10px;text-align: center;"  >${starStory.content} </div>
					</c:if>
					<c:if test="${starStory.type == 2}">
						<div class="rel">
							<input type="hidden"  value="${starStory.vedioUrl }" >
							<div id="covered"><img style="" src="${starStory.cover}"> </div>
							<div id="poplayer"><img src="resource/images/play.png"></div>
						</div>
						<div >${starStory.content}</div>
					</c:if>
				</div>
			</c:if>
			
			<!--评论内容  -->
			<div style=" font-weight:bold; height:20px;  padding-left:10px;background:#E6E6E6;">热门评论</div>
			<c:if test="${commentList1 != null && not empty commentList}">
				<c:forEach var="comment" items="${commentList1 }">
				 <div class="s_comment commentList1" >
					<div class="">
					  <img class="icon" style="float: left;margin-left: 10px; max-width: 40px;max-height: 40px" src="${comment.icon}"/>
					  <div class="" style=" margin-left:60px;">
					     <p>${comment.name}</p>
					     <p>${comment.createTime}</p>
					     <p style="padding-top:10px; padding-right:5px;color:black">${comment.comment}</p>
					  </div>
					</div>
				</div>
				</c:forEach>
				 <div class="s_comment commentList1" >
						<div class="blankDiv">1</div>
				</div>
			</c:if>
			<c:if test="${empty  commentList1 }">
				<div class="blankDiv" class="commentList1" style="z-index:888;  text-align: center;   ">
					<br>
					&nbsp;
					<div >
					  暂无评论
					</div>
					<br>
					&nbsp;
				</div>
			</c:if>
		</div>
	 <div class="floatbox">
		
	</div> 
	</div>

<script src="resource/js/swiper.min.js"></script>
<script src="resource/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="resource/js/ckplayer/ckplayer.js" charset="utf-8"></script>
<script type="text/javascript" src='resource/fmbox/js/jquery.jplayer.min.js'></script>
<script type="text/javascript" src='resource/fmbox/js/jplayer.playlist.min.js'></script>

<!-- Initialize Swiper -->
<script>
$(function(){
	    //使用id选择器;例如:tab对象->tr->td对象.
    $(".mcontent").each(function(i){
	         //获取td当前对象的文本,如果长度大于25;
	         if($(this).html().length>150){
	                //给td设置title属性,并且设置td的完整值.给title属性.
				$(this).attr("title",$(this).text());
	                //获取td的值,进行截取。赋值给text变量保存.
				var text="剧情"+$(this).html().substring(0,150)+"...";
	                //重新为td赋值;
            	 $(this).html(text);
	         }
	      });
	
	    
	 $(".rel").find("div").click(function(){
		 var url = $(".rel").find("input").val();
		 window.location.href=url; 
	 });
	 
    var W = document.body.clientWidth - 30;
      	 if (W > 640) {W = 500;}
    var H = W / 1;
   var W1 = document.body.clientWidth - 20;
 	 if (W1 > 640) {W1 =500;}
   var H1 = W1 / 1;
	 $("div.flash").each(function(index, element){
		 var flashvars={
			        p:0,
			        e:0,
			        i:$("#img", $(element)).val()
			    };
	    	var videoStoryUrl = $("#videoStoryUrl", $(element)).val();
	    	if(videoStoryUrl !=""){
			    var video=[''];//videoStoryUrl+->video/mp4'
			    video[0]=videoStoryUrl+"->video/mp4";
			    var support=['all'];
			    CKobject.embedHTML5('a'+index,'ckplayer_a'+index,W,H,video,flashvars,support);
	    	};
	    	
	    	//初始化音频播放器
	    	var voiceStoryUrl = $("#voiceStoryUrl", $(element)).val();
	    	var cover = $("#cover", $(element)).val();
	   		if(voiceStoryUrl !=""){
	   			 var playlist = [{
			   	      title:"",
			   	      artist:"",
			   	      mp3:voiceStoryUrl,
			   		  /*下面是海报*/
			   	      poster: cover
			   	    },];

			   	  var cssSelector = {
			   	    jPlayer: "#jp"+index,
			   	    cssSelectorAncestor: "#jm"+index,
			   	  };
			   	  
			   	  var options = {
			   			jPlayerPlaylist: "Jplayer.swf",
			   	        supplied: "ogv, m4v, oga, mp3",
			   	     	volume :1
			   	  };
			     $(".music-player").width(W1);
			     $(".music-player").height(H1);
			     $(".music-player").css("margin-top","0px");
			     $(".music-player").css("margin-bottom","10px");
			   	  var myPlaylist = new jPlayerPlaylist(cssSelector, playlist, options);
	   			
	   		} 	
	    	
	 })
		
	 
    var swiperH = new Swiper('.swiper-container-h', {
        pagination: '.swiper-pagination-h',
        paginationClickable: true,
        paginationBulletRender: function (index, className) {
            return '<span style="width:15px;height:15px;line-height:15px;font-size:10px;   color:#eee;" class="' + className + '">' + (index + 1) + '</span>';
        },
        spaceBetween: 50
    });    
	    
	//让页脚自适应屏幕宽度
	 window.onresize = function(){
		$(".floatbox").width($('body').width());
		$(".floatbox").height($('body').width()/9);
		$(".swiper-pagination").height($('body').height()/5);
		$(".blankDiv").height($('body').height()/4);
		
	 }
	$(".floatbox").width($('body').width());
	$(".floatbox").height($('body').width()/9);
	$(".swiper-pagination").height($('body').height()/6);
	$(".blankDiv").height($('body').height()/4);
	//点击事件
	$(".floatbox").click(function(){
		window.location.href="<%=basePath%>app/webDownload.htm";
	});
	
	
	//初始化页眉
	  var type = "${type}";
	  var storyIsland = "${storyIsland}";
	  var microFilm = "${microFilm}";
	  if(storyIsland !="" && microFilm !="" ){
		  $(".tab").show();
		  if(type == "1"){
			  $(".content2").hide();
			  $(".story-container").show(); 
			  $(".swiper-container").removeAttr("style");
			 /*  $(".swiper-container").css("cssText","height: 100%!important");  */
			  $(".swiper-pagination").show();
			  
			  
		  }else{
			  $(".story-container").hide();
			  $(".swiper-container").css('height','0');
		      $(".content2").show();
	  		  $(".story").removeClass("active");
	  	 	  $(".movie").addClass("active");
		      $(".swiper-pagination").hide();
		  }
	  }else if(storyIsland !="" && microFilm == ""){
		  $(".tab").hide();
		  $(".content2").hide();
		  $(".swiper-container").css('padding-top',0);
		  $(".story-container").show();  
		  $(".swiper-pagination").show();
		  
	  }else if(storyIsland =="" && (microFilm !="" || type =="3" || type== "4")){
	      $(".swiper-container").css('padding-top',0);
	      $(".swiper-container").css('height','0');
  	 	  $(".content2").removeClass("content2");
		  $(".tab").hide();
		  $(".story-container").hide();
	      $(".content2").show();
	      $(".swiper-pagination").hide();
	      $(".microFilmClass").show();
	      if(type=="3" || type=="4"){
	    	 $(".microFilmClass").hide();
	      }
	  }
	
	  //绑定事件
		$(".tab li").click(function(){
		   	var _idx = $(this).index();
		   	$(".tab li").removeClass("active");
		   	$(this).addClass("active");
		   	if( _idx ) {
		   		$(".story-container").hide();
		   		$(".content2").show();
		   	 	$(".swiper-container").css('height','0');
		   		$(".swiper-pagination").hide();
		   	} else {
		   		$(".content2").hide();
				$(".story-container").show();
				$(".swiper-pagination").show();
				 $(".swiper-container").css('height','100%');
		   	}
		   });
	  
	  
});


</script>
</body>
</html>