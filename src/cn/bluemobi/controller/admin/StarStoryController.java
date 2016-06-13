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
import cn.bluemobi.entity.StarStory;
import cn.bluemobi.service.StarStoryService;
import cn.bluemobi.service.SystemLogService;
import cn.bluemobi.util.text.TextHelper;

/**
 * 星故事
 * @author xiazf
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("admin/starStory/")
public class StarStoryController extends BaseController {

	@Autowired
	private StarStoryService starStoryService;
	@Autowired
	private SystemLogService systemLogService;
	
	/**
	 * 进入星故事列表
	 * @return
	 */
	@RequestMapping("list")
	public String list(Page page,StarStory starStory) {
		Map<String,Object> map = starStoryService.getStarStoryListByPage(page,starStory);
		request.setAttribute("starStoryList", map.get("starStoryList"));
		request.setAttribute("page", map.get("page"));
		return "admin/starStory/list.jsp";
	}
	/**
	 * 星故事状态修改
	 */
	@RequestMapping("updateStatus")
	public void updateStatus(String ids,String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			if(!TextHelper.isNullOrEmpty(ids)){
				String idArr[] = ids.split(",");
				starStoryService.updateStatus(idArr,status);
				map.put(STATUS, SUCCESS);
				Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
				systemLogService.addSystemLog(a, "修改星故事状态");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	/**
	 * 删除星故事
	 */
	@RequestMapping("deleteStarStory")
	public void deleteStarStory(String ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			if(!TextHelper.isNullOrEmpty(ids)){
				String[] idArr = ids.split(",");
				for (int i = 0; i < idArr.length; i++) {
					starStoryService.deleteStarStory(idArr[i]);
				}
			}
			Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			systemLogService.addSystemLog(a, "删除星故事");
			map.put(STATUS, SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		}
	}
	/**
	 * 去编辑页面
	 */
	@RequestMapping("goEditStar")
	public String goEditStar(String id) {
		StarStory ss = starStoryService.getStarStoryById(id);
		request.setAttribute("starStory", ss);
		return "admin/starStory/edit.jsp";
	}
	
	/**
	 * 编辑星故事
	 */
	@RequestMapping("save")
	public void save(StarStory ss) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			Admin admin = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			ss.setReleaseId(admin.getId());
			//设置状态状态
			ss.setStatus("1");
			starStoryService.save(ss);
			map.put(STATUS, SUCCESS);
			Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			systemLogService.addSystemLog(a, "修改星故事资料");
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	/**
	 * 去新增页面
	 */
	@RequestMapping("goAdd")
	public String goAdd() {
		Integer number =  starStoryService.getMaxNumber();
		request.setAttribute("number", ++number);
		return "admin/starStory/add.jsp";
	}
	
	/**
	 * 新增星故事
	 */
	@RequestMapping("add")
	public void add(StarStory ss) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			Admin admin = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			ss.setReleaseId(admin.getId());
			//设置状态未启用状态
			ss.setStatus("1");
			starStoryService.add(ss);
			map.put(STATUS, SUCCESS);
			Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			systemLogService.addSystemLog(a, "新增星故事");
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	
	
}
