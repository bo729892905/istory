package cn.bluemobi.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bluemobi.constant.BlueMobiConstant;
import cn.bluemobi.controller.BaseController;
import cn.bluemobi.entity.AboutUs;
import cn.bluemobi.entity.Admin;
import cn.bluemobi.service.AboutUsService;
import cn.bluemobi.service.SystemLogService;
import cn.bluemobi.util.text.TextHelper;

/**
 * 关于我们
 * @author xiazf
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("admin/aboutUs")
public class AboutUSController extends BaseController {
	
	@Autowired
	private AboutUsService aboutUsService;
	@Autowired
	private SystemLogService systemLogService; 
	
	
	@RequestMapping("/aboutUs")
	public String left() {
		AboutUs au =  aboutUsService.getAboutUs();
		request.setAttribute("aboutUs", au);
		return "admin/aboutUs/aboutUs.jsp";
	}
	
	@RequestMapping("/edit")
	public void edit(String aboutUs) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			aboutUs = TextHelper.rhtml(aboutUs);
			aboutUsService.edit(aboutUs);
		    map.put(STATUS, SUCCESS);
		    
			Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			systemLogService.addSystemLog(a, "修改关于我们");
		    
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
}
