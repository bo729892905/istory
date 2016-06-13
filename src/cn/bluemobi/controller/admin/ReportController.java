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
import cn.bluemobi.entity.Page;
import cn.bluemobi.entity.Report;
import cn.bluemobi.entity.Tag;
import cn.bluemobi.service.ReportService;
import cn.bluemobi.service.SystemLogService;
import cn.bluemobi.util.text.TextHelper;
/**
 * 举报管理
 * @author xiazf
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("admin/report/")
public class ReportController extends BaseController {
	@Autowired
	private ReportService reportService;
	@Autowired
	private SystemLogService systemLogService;
	
	/**
	 * 进入举报列表
	 */
	@RequestMapping("list")
	public String list(Page page,Report report) {
		Map<String,Object> map = reportService.getReportList(page,report);
		request.setAttribute("reportList", map.get("reportList"));
		request.setAttribute("page", map.get("page"));
		return "admin/report/list.jsp";
	}
	
	/**
	 * 进入标签列表
	 */
	@RequestMapping("tagList")
	public String tagList(Page page) {
		Map<String,Object> map = reportService.getReportTagList(page);
		request.setAttribute("tagList", map.get("tagList"));
		request.setAttribute("page", map.get("page"));
		return "admin/report/tagList.jsp";
	}
	/**
	 * 添加标签
	 */
	@RequestMapping("addTag")
	public void addTag(String tag) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			Tag t = reportService.getTagByTag(tag);
			if(TextHelper.isNullOrEmpty(t)){
				reportService.addTag(tag);
				map.put(STATUS, SUCCESS);
				

				Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
				systemLogService.addSystemLog(a, "新增标签");
				
			}else{
				map.put("msg", "exist_tag");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	/**
	 * 删除标签
	 */
	@RequestMapping("deleteTag")
	public void deleteTag(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			if(!TextHelper.isNullOrEmpty(id)){
				reportService.deleteTag(id);
				map.put(STATUS, SUCCESS);

				Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
				systemLogService.addSystemLog(a, "删除标签");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	/**
	 * 删除举报
	 */
	@RequestMapping("deleteReport")
	public void deleteReport(String type,String beReportId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			reportService.deleteReport(type,beReportId);
			map.put(STATUS, SUCCESS);
			
			Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			systemLogService.addSystemLog(a, "删除举报记录");
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	
}
