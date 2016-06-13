/**
 * 
 * 1.主要是用于页面间的iframe跳转
 * 2.主要是用于ifarame中权限菜单的单击事件的控制
 * 
 */
/**
 * 获取当前跳转的方法
 */
var targetMainFrame = parent.window.targetMainFrame;

/**
 * 用于处理当前页面权限的操作
 */
$(document).ready(function(){
	parent.window.clickFuncInit($("a[name^='oper_']"),searchClick,addClick,editClick,trashClick);
});

/**
 * 调取显示验证form 弹框
 */
var valTips =function(id,errStr){
	$("#"+id).tips({
		side : 2,
		msg : errStr,
		bg : '#AE81FF',
		time : 3
	});
	$("#"+id).focus();
}


/**
 * 
 * 验证字段
 * 
 * <br>
 * 
 * 1.当参数为4个的时候：<br>
 * stOrLen表示最少长度，ed 表示最大长度，regx正则
 * 2.当参数为3个的时候：<br>
 * 	如果ed:是数字
 * stOrLen表示最小长度，ed 表示最大长度
 * 	如果ed:是正则
 * stOrLen表示长度，ed 是正则
 * 3.当参数为2个的时候：<br>
 * 	stOrLen 是数字表示长度
 * 	stOrLen 是正则表示正则
 */

var checkField = function(fv, stOrLen, ed, regx) {
	
	if (typeof (stOrLen) == 'number' && typeof (ed) == 'number'){
		if (stOrLen >= ed)
			console.info("最小长度不能大于结束长度");
	}
	
	if (fv != null && fv != '' && fv.length != 0 && fv != 'undefined')
		if (arguments.length == 4) {
			if (fv.length >= stOrLen && fv.length <= ed)
				if (regx)
					if (regx.test(fv))
						return true;
		} else if (arguments.length == 3) {
			if (typeof (ed) == 'number') {
				if (fv.length >= stOrLen && fv.length <= ed)
					return true;
			} else {
				if (fv.length == stOrLen)
					if (ed)
						if (ed.test(fv))
							return true;
			}
		} else if (arguments.length == 2) {
			if (stOrLen && typeof (stOrLen) == 'number') {
				if (fv.length == stOrLen)
					return true;
			} else if (stOrLen) {
				if (stOrLen.test(fv))
					return true;
			}
		} else if (arguments.length == 1) {
			return true;
		}
	return false;
};

