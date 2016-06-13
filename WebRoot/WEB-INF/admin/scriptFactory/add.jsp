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
			编辑剧本工厂
		</div>
	
		<div id="div_content" class="row-fluid" style="padding-top: 10px;">
			<form class="form-horizontal" id="validation-form" method="post">
					<div class="control-group">
						<label class="control-label" for="title">标题:</label>
						<div class="controls" >
							<div class="span12">
								<input type="text"  name="title" id="title" value="${scriptFactory.title }" >
							</div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="author">故事梗概:</label>
						<div class="controls">
							<span class="span12">
								<textarea class="form-control"  style="width: 50%"  rows="10" name="content" id="content" >${scriptFactory.content}</textarea>
							</span>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="cover">封面:</label>
						<div class="controls">
							<span class="span12" style="width: 30%"> 
								<img style=border:0;margin-top:2px; <c:if test="${scriptFactory != null && scriptFactory.cover != '' && scriptFactory.cover != null }"> src="${ scriptFactory.cover}" </c:if>  <c:if test="${scriptFactory == null || scriptFactory.cover == '' || scriptFactory.cover == null }"> src="<%=basePath %>/imgs/zanwutupian.jpg" </c:if>   id="file4img" width="300" height="300" >
								<input value="${scriptFactory.cover }" name="cover" type="hidden" id="cover" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<br>
								<input type="file" name="file"  onchange="ajaxFileUpload(this)" id="file4">
							</span>
						</div>
					</div>
					
					<div class="control-group">
						<label class="control-label" for="hot">热度:</label>
						<div class="controls">
							<span class="span12"> <input type="text"  name="hot" id="hot" value="${scriptFactory.hot }" > 
							</span>
						</div>
					</div>
					
				</form>
		</div>
		
		<div style="padding-left: 20%">
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
<script type="text/javascript">
window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
</script>
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
<!--时间  -->
<script type="text/javascript" src="<%=basePath%>resource/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="resource/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="resource/js/publicSystem.js"></script>
<script type="text/javascript">
var value = 1;
$(document).ready(function(){
	$("#submit").click(function(){
		this.disabled="disabled";
		if(vailVals()!=0){
			this.disabled='';
			return;
		}
		 bootbox.confirm("你将修改剧本信息！","取消","确定",function(e){
			if(e){
				$.post("admin/scriptFactory/save.htm", $("#validation-form").serialize(), function(request) {
					var json = eval('(' + request + ')'); 
					if(1 == json.status){
						top.mainFrame.tabAddHandler("scriptFactoryList","剧本工厂列表","admin/scriptFactory/list.htm");
					}else{
						bootbox.alert("修改失败，请稍后重试!","确定");
					}
				}); 
			}
		}); 
		this.disabled='';
	});
	
	$("#reset").click(function(){
		top.mainFrame.tabAddHandler("scriptFactoryList","剧本工厂列表","admin/scriptFactory/list.htm");
	});
	
	$('#file4').ace_file_input({
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
	
});

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
	
				$("#cover").val(data.src);
				$("#"+ fileId+"a").attr({
					href:data.src
				});
				$("#"+ fileId+"img").attr({
					src:data.src
				});
			},error: function(data){  
	        }  
		});
     }

}


//验证
function vailVals(){
	if(!checkField($("#title").val(), 1, 50)){
		valTips('title', '请输入正确的标题');
		return 1;
	}
	if(!checkField($("#content").val(), 1, 5000)){
		valTips('content', '请输入正确的故事梗概');
		return 1;
	}
	if(!checkField($("#cover").val(), 1, 150)){
		valTips('file4', '请上传封面');
		return 1;
	}
	if(!checkField($("#hot").val(), 1, 10,RegExp("^[0-9]*$"))){
			valTips('hot', '请输入正确的热度');
			return 1;
	}
	return 0;;
}
</script>


</html>

