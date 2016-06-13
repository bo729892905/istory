package cn.bluemobi.controller.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bluemobi.controller.BaseController;
import cn.bluemobi.entity.Page;
import cn.bluemobi.entity.SystemLog;
import cn.bluemobi.service.SystemLogService;
/**
 * 系统日志管理
 * @author xiazf
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("admin/systemLog/")
public class SystemLogController extends BaseController {
	@Autowired
	private SystemLogService systemLogService;
	/**
	 * 进入系统日志列表
	 * @return
	 */
	@RequestMapping("list")
	public String logList(Page page,SystemLog systemLog) {
		Map<String,Object> map = systemLogService.getSystemLogList(page,systemLog);
		request.setAttribute("systemLogList", map.get("systemLogList"));
		request.setAttribute("page", map.get("page"));
		return "admin/systemLog/list.jsp";
	}
	
}
