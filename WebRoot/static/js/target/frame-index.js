/**
 * 
 * 1.用于主页控制iframe的高度和宽度
 * 2.用于主页跳转iframe页面
 * 
 */

/**用于控制iframe页面*/
var iframeids = ["mainFrame"];
var iframehide = "yes";
function dyniframesize() {  
    var dyniframe = new Array();
    for (i = 0; i < iframeids.length; i++) {  
        if (document.getElementById) {  
            dyniframe[dyniframe.length] = document.getElementById(iframeids[i]);  
            if (dyniframe[i] && !window.opera) {  
                dyniframe[i].style.display = "block";
                if (dyniframe[i].contentDocument && dyniframe[i].contentDocument.body.offsetHeight)  
                    dyniframe[i].height = dyniframe[i].contentDocument.body.offsetHeight;  
                else if (dyniframe[i].Document && dyniframe[i].Document.body.scrollHeight)  
                    dyniframe[i].height = dyniframe[i].Document.body.scrollHeight;  
            }  
        }  
        if ((document.all || document.getElementById) && iframehide == "no") {  
            var tempobj = document.all ? document.all[iframeids[i]] : document.getElementById(iframeids[i]);
            tempobj.style.display = "block";
        }  
    }  
}  

if (window.addEventListener){
	window.addEventListener("load", dyniframesize, false);
}else if (window.attachEvent){
    window.attachEvent("onload", dyniframesize);
}else{  
    window.onload = dyniframesize();
}

function setparentheight(){
	alert(1);
   parent.document.all(self.name).height=document.body.scrollHeight+50;
}

/**用户控制跳转，和子页面的按钮事件*/
var targetMainFrame = function(path){
	$("#mainFrame").attr("src",path);
};

/**
 * 对于其他点击事件需要
 */
var clickFunc = function(cbtn,clickFunc){
	$(cbtn).click(clickFunc);
};

/**
 * 如果有权限的情况下，必须调用这个方法来执行function
 */
var clickFuncInit = function(clickBtns,searchClick,addClick,editClick,trashClick){
	$(clickBtns).click(function(){
		console.info(this.id);
		if(this.id.indexOf("_add")>0){
			if(addClick)
				addClick();
		}else if(this.id.indexOf("_edit")>0){
			if(editClick)
				editClick();
		}else if(this.id.indexOf("_search")){
			if(searchClick)
				searchClick();
		}else if(this.id.indexOf("_trash")){
			if(trashClick)
				trashClick();
		}
	});
};
