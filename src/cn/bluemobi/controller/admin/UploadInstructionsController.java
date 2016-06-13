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
import cn.bluemobi.entity.UploadInstructions;
import cn.bluemobi.service.SystemLogService;
import cn.bluemobi.service.UploadInstructionsService;
import cn.bluemobi.util.text.TextHelper;

/**
 * 上传说明
 * @author xiazf
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("admin/uploadInstructions")
public class UploadInstructionsController extends BaseController {
	
	@Autowired
	private UploadInstructionsService uploadInstructionsService;
	@Autowired
	private SystemLogService systemLogService;
	
	/**
	 * 进入上传说明
	 * @return
	 */
	@RequestMapping("/info")
	public String left() {
		UploadInstructions ui =  uploadInstructionsService.getUploadInstructions();
		request.setAttribute("uploadInstructions", ui);
		return "admin/microFilm/info.jsp";
	}
	
	/**
	 * 编辑上传说明
	 * @param content
	 */
	@RequestMapping("/edit")
	public void edit(String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			content = TextHelper.rhtml(content);
			uploadInstructionsService.edit(content);
		    map.put(STATUS, SUCCESS);
		    Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			systemLogService.addSystemLog(a, "修改上传说明");
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	
}
