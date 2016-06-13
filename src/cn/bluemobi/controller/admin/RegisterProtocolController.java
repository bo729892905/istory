package cn.bluemobi.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bluemobi.constant.BlueMobiConstant;
import cn.bluemobi.controller.BaseController;
import cn.bluemobi.entity.Admin;
import cn.bluemobi.entity.RegisterProtocol;
import cn.bluemobi.service.RegisterProtocolService;
import cn.bluemobi.service.SystemLogService;
import cn.bluemobi.util.text.TextHelper;

/**
 * 注册协议
 * @author xiazf
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("admin/registerProtocol")
public class RegisterProtocolController extends BaseController {
	@Autowired
	private RegisterProtocolService registerProtocolService;
	@Autowired
	private SystemLogService systemLogService;
	 
	@RequestMapping("/protocol")
	public String left() {
		RegisterProtocol rp =  registerProtocolService.getRegisterProtocol();
		request.setAttribute("registerProtocol", rp);
		return "admin/registerProtocol/protocol.jsp";
	}
	
	@RequestMapping("/edit")
	public void edit(String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			content = TextHelper.rhtml(content);
			registerProtocolService.edit(content);
		    map.put(STATUS, SUCCESS);
		    Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			systemLogService.addSystemLog(a, "修改注册协议");
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
}
