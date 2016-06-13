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
<!-- basic styles -->
<link href="static/css/bootstrap.min.css" rel="stylesheet" />
<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
<link rel="stylesheet" href="static/css/font-awesome.min.css" />
<!--[if IE 7]>
  <link rel="stylesheet" href="static/css/font-awesome-ie7.min.css" />
<![endif]-->
<!-- page specific plugin styles -->

<link rel="stylesheet" href="static/css/jquery-ui-1.10.2.custom.min.css" />
<link rel="stylesheet" href="static/css/jquery.gritter.css" />

<!-- ace styles -->
<link rel="stylesheet" href="static/css/ace.min.css" />
<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
<link rel="stylesheet" href="static/css/ace-skins.min.css" />
<!-- ace styles -->
<link rel="stylesheet" href="static/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />
<!--[if lt IE 9]>
  <link rel="stylesheet" href="static/css/ace-ie.min.css" />
<![endif]-->
<style type="text/css">
#btnmenu tr td{
	margin: 5px 5px;
	padding: 5px 5px;
}
</style>

</head>
<body>
<div class="container-fluid" id="main-container">
	<div class="row-fluid">
		<div class="table-header">
			编辑轮播图
		</div>
	
		<div id="div_content1" class="row-fluid" style="padding-top: 10px;">
			<form class="form-horizontal" id="validation-form" method="post" enctype="multipart/form-data">
					<div class="control-group">
						<label class="control-label" for="title">标题:</label>
						<div class="controls" >
							<div class="span12">
								<input type="hidden" name="type" id="type" value="${advertisement.type}" >
								<input type="hidden" name="id" id="id" value="${advertisement.id }" >
								<input type="text"  name="title" id="title" value="${advertisement.title }" >
							</div>
						</div>
					</div>
					
					<div class="control-group">
						<label class="control-label" for="director">链接方式:</label>
						<div class="controls"  >
							<select class="chzn-select"  name="linkType" id="linkType" onchange="turn()" style="vertical-align:top;width: 100px;">
								<option value="0"  <c:if test="${advertisement.linkType == '0' }">selected</c:if> >跳转链接</option>
								<option value="1" <c:if test="${advertisement.linkType == '1' }">selected</c:if> >跳转<c:if test="${advertisement.type == 1}">故事</c:if><c:if test="${advertisement.type == 2}">电影</c:if><c:if test="${advertisement.type == 3}">剧本</c:if></option>
							</select>
						</div>
					</div>
					<div class="control-group" id="toURL">
						<label class="control-label"  for="author">链接:</label>
						<div class="controls">
							<span class="span12"><input  type="text"  name="linkUrl" id="linkUrl" value="${advertisement.linkUrl }" ></span>
						</div>
					</div>
					<div class="control-group" id="toID" >
						
						<label class="control-label"  for="author"><c:if test="${advertisement.type == 1}">故事</c:if><c:if test="${advertisement.type == 2}">电影</c:if><c:if test="${advertisement.type == 3}">剧本</c:if>ID:</label>
						<div class="controls">
							<span class="span12"><input  type="text"  name="linkId" id="linkId" value="${advertisement.linkId}"></span>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="content">图片:</label>
						<div class="controls">
							<span class="span12" style="width: 30%"> 
								
								<img style=border:0;margin-top:2px; <c:if test="${advertisement != null && advertisement.advertisementUrl != '' &&  advertisement.advertisementUrl != null }"> src="${  advertisement.advertisementUrl}" </c:if>  <c:if test="${ advertisement == null ||  advertisement.advertisementUrl == '' ||  advertisement.advertisementUrl == null }"> src="<%=basePath %>/imgs/zanwutupian.jpg" </c:if>   id="file1img" width="300" height="300" >
								<input value="${ advertisement.advertisementUrl }" name="advertisementUrl" type="hidden" id="file1input" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<br>
								<input type="file" name="file"  onchange="ajaxFileUpload(this)" id="file1">
							</span>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="content">点击量:</label>
						<div class="controls">
							<span class="span12"><input  type="text"  name="hit" id="hit" value="${advertisement.hit }" > <button class="btn  btn-danger btn-mini" type="button" id="clear">清除</button>
							</span>
						</div>
					</div>
					
				</form>
		</div>
		
		<div  style="padding-left: 20%">
			<button class="btn btn-info" type="submit" id="submit"><i class="icon-ok"></i> 保存</button>
			&nbsp; &nbsp; &nbsp;
			<button class="btn" type="reset" id="reset"><i class="icon-undo"></i> 返回</button>
		</div>
		
	</div>
</div>
<!-- #main-content -->
</body>
<!-- basic scripts -->
<script src="static/1.9.1/jquery.min.js"></script>
<script src="static/js/bootstrap.min.js"></script>
<!-- page specific plugin scripts -->


<script type="text/javascript" src="static/js/jquery-ui-1.10.2.custom.min.js"></script>
<script type="text/javascript" src="static/js/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" src="static/js/jquery.easy-pie-chart.min.js"></script>
<script type="text/javascript" src="static/js/jquery.gritter.min.js"></script>
<script type="text/javascript" src="static/js/bootbox.min.js"></script>
<script type="text/javascript" src="static/js/spin.min.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script><!-- 弹框插件 -->

<!-- ace scripts -->
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<!-- inline scripts related to this page -->
<script type="text/javascript" src="static/js/target/frame-target.js"></script><!-- 子页面需要添加的js -->
<script type="text/javascript" src="static/js/target/frame-index.js"></script><!-- 子页面需要添加的js -->
<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
<script type="text/javascript" src="resource/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="resource/js/publicSystem.js"></script>
<script type="text/javascript">
var value = 1;
$(document).ready(function(){
	
	//下拉框
	$(".chzn-select").chosen(); 
	$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
	$("#clear").click(function(){
		$("#hit").val(0);
	});
	
	$("#submit").click(function(){
		this.disabled="disabled";
		if(vailVals()!=0){
			this.disabled='';
			return;
		}
		 bootbox.confirm("你将修改轮播图信息！","取消","确定",function(e){
			if(e){
				$.post("admin/advertisement/save.htm", $("#validation-form").serialize(), function(request) {
					var json = eval('(' + request + ')'); 
					if(1 == json.status){
						var type = $("#type").val(); 
						var title = "";
						var tabId ="";
						if(type=='1'){
							 title = "故事岛轮播列表";
							 tabId =  "advertisementIslandList";
						}else if(type=='2'){
							 title = "微电影轮播列表";
							 tabId =  "advertisementFilmList";
						}else if(type=='3'){
							 title = "剧本工厂轮播列表";
							 tabId =  "advertisementStarList";
						}
						top.mainFrame.tabAddHandler(tabId,title,"admin/advertisement/list.htm?type="+type);
					}else{
						if(json.msg == 'id_error'){
							bootbox.alert("指定Id的对象不存在!","确定");
						}else if(json.msg == 'status_error'){
							bootbox.alert("指定Id的对象被禁用!","确定");
						}else{
							bootbox.alert("修改失败，请稍后重试!","确定");
						}
					}
				}); 
			}
		}); 
		this.disabled='';
	});
	
	$("#reset").click(function(){
		var type = $("#type").val(); 
		var title = "";
		var tabId ="";
		if(type=='1'){
			 title = "故事岛轮播列表";
			 tabId =  "advertisementIslandList";
		}else if(type=='2'){
			 title = "微电影轮播列表";
			 tabId =  "advertisementFilmList";
		}else if(type=='3'){
			 title = "剧本工厂轮播列表";
			 tabId =  "advertisementStarList";
		}
		top.mainFrame.tabAddHandler(tabId,title,"admin/advertisement/list.htm?type="+type);
	});
	
	$('#file1').ace_file_input({
		no_file:'请选择图片 ...',
		btn_choose:'选择',
		btn_change:'更改',
		droppable:false,
		onchange:null,
		thumbnail:false, //| true | large
		whitelist:'gif|png|jpg|jpeg',
		//blacklist:'gif|png|jpg|jpeg'
		//onchange:''
		//
	});
	
	$('#file2').ace_file_input({
		no_file:'请选择图片 ...',
		btn_choose:'选择',
		btn_change:'更改',
		droppable:false,
		onchange:null,
		thumbnail:false, //| true | large
		whitelist:'gif|png|jpg|jpeg',
		//blacklist:'gif|png|jpg|jpeg'
		//onchange:''
		//
	});
	$("#picture2").hide();
	$("#vedio").hide();	
	turn();
});

function turn(){
	var a = $("#linkType").val();
	if(a==0){
		$("#toURL").show();
		$("#toID").hide();
	}else {
		$("#toURL").hide();
		$("#toID").show();
	}
}
var hcid1 = '';
var qxhc1 = '';

function kf_qx1(choiceness){
	if(choiceness != hcid1){
		hcid1 = choiceness;
		qxhc1 = '';
	}
	var wqx = $("#"+choiceness).attr("checked");
	if(qxhc1 == ''){
		if(wqx == 'checked'){
			qxhc1 = 'checked';
		}else{
			qxhc1 = 'unchecked';
		}
	}
	if(qxhc1 == 'checked'){
		value = 0;
		qxhc1 = 'unchecked';
	}else{
		value = 1;
		qxhc1 = 'checked';
	}
		
}



//上传文件
var isIE = /msie/i.test(navigator.userAgent) && !window.opera; 		
function ajaxFileUpload(obj) {
	//获取欲上传的文件路径  
	var file = $(obj).val();
	var rs = checkPictureType(file);
	if (rs < 0) {
		$(obj).val("");
		$.messager.alert("提示", "您选择的上传文件不是有效的图片文件！");
		return;
	}
	
	var fileSize = 0;          
    if (isIE && !obj.files) {      
      var filePath = obj.value;      
      var fileSystem = new ActiveXObject("Scripting.FileSystemObject");         
      var file1 = fileSystem.GetFile (filePath);      
      fileSize = file1.Size;     
    } else {     
     fileSize = obj.files[0].size;      
     }    
     var size = fileSize / 1024;     
     if(size>2000){   
    	 $.messager.alert("提示","图片不能大于2M");   
     }else{   
			var fileId=$(obj).attr("id");
			$.ajaxFileUpload({
				url : $("base")[0].href+'admin/uploadFile.htm', //需要链接到服务器地址
				secureuri : false,
				fileElementId : fileId, //文件选择框的id属性
				dataType : 'json', //服务器返回的格式，可以是json
				success : function(data) { //相当于java中try语句块的用法
				data = data.replace(/<[^>].*?>/g, "");//对返回的数据进行转换
				data = eval("(" + data + ")");//对返回的数据进行转换
	
				$("#"+fileId+"input").val(data.src);
				$("#"+ fileId+"img").attr({
					src:data.src
				});
			},error: function(data){  
	        }  
		});
     }

}
//过滤类型
function fileType(obj){
	var fileType=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
    if(fileType != '.gif' && fileType != '.png' && fileType != '.jpg' && fileType != '.jpeg'){
    	$("#tp").tips({
			side:3,
            msg:'请上传图片格式的文件',
            bg:'#AE81FF',
            time:3
        });
    	$("#tp").val('');
    	document.getElementById("tp").files[0] = '请选择图片';
    }
}
//删除
function delP(){
	$("#tpz").val("");
	$("#tpi").attr("src","");
	$("#tpa").attr("href","javascript:void(0);");
}

//验证
function vailVals(){
	if(!checkField($("#title").val(), 1, 32)){
		valTips('title', '请输入正确的标题');
		return 1;
	}
	if($("#linkType").val()==0){
		if(!checkField($("#linkUrl").val(), 1, 180)){
			valTips('linkUrl', '请输入正确的链接地址');
			return 1;
		}
	}else if($("#linkType").val()==1){
		if(!checkField($("#linkId").val(),1, 10,RegExp("^[0-9]*$"))){
			valTips('linkId', '请输入正确的ID');
			return 1;
		}
	}
	if(!checkField($("#file1input").val(), 1, 180)){
		valTips('file1', '请上传海报');
		return 1;
	}
	
	if(!checkField($("#hit").val(), 1, 10,RegExp("^[0-9]*$"))){
		valTips('hit', '请输入正确的点击量');
		return 1;
}
	return 0;
}

<%-- //查看
function editUser(){
	 top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 var type = $("#type").val();
	 if(type == 1){
		 diag.Title ="故事列表";
		 diag.URL = '<%=basePath%>admin/advertisement/islandListAll.htm?';
	 }else if(type == 2){
		 diag.Title ="电影列表";
		 diag.URL = '<%=basePath%>admin/advertisement/islandListAll.htm?';
	 }else{
		 diag.Title ="剧本列表";
		 diag.URL = '<%=basePath%>admin/advertisement/islandListAll.htm?';
	 }
	 diag.Width = 800;
	 diag.Height = 510;
	 diag.CancelEvent = function(){ //关闭事件
		 if(diag.innerFrame.contentWindow.document.getElementById('page-content').style.display == 'none'){
			 nextPage(${page.currentPage});
		}
		 diag.close();
	 };
	 diag.show();
	 top.hangge();
};
 --%>
</script>


</html>

