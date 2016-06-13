<%@page import="cn.bluemobi.constant.BlueMobiConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% String basePath=BlueMobiConstant.domain; %>
<base href="<%=basePath%>" />


<meta http-equiv="Content-Type" contenttype="text/xml; charset=UTF-8" />
<script src="resource/jquery-1.7.2.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).keydown(function(e){
    var target = e.target ;
    var tag = e.target.tagName.toUpperCase();
    if(e.keyCode == 8){
     if((tag == 'INPUT' && !$(target).attr("readonly"))||(tag == 'TEXTAREA' && !$(target).attr("readonly"))){
      if((target.type.toUpperCase() == "RADIO") || (target.type.toUpperCase() == "CHECKBOX")){
       return false ;
      }else{
       return true ; 
      }
     }else{
      return false ;
     }
    }
});
</script>