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
import cn.bluemobi.entity.StoryIslandClassify;
import cn.bluemobi.service.StoryIslandClassifyService;
import cn.bluemobi.service.SystemLogService;
import cn.bluemobi.util.text.TextHelper;
/**
 * 故事岛分类
 * @author xiazf
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("admin/storyIslandClassify/")
public class StoryIslandClasssifyController extends BaseController {
	@Autowired
	private StoryIslandClassifyService storyIslandClassifyService;
	@Autowired
	private SystemLogService systemLogService;
	/**
	 * 进入故事分类列表
	 * @return
	 */
	@RequestMapping("list")
	public String list(Page page,StoryIslandClassify storyIslandClassify) {
		Map<String,Object> map = storyIslandClassifyService.getClassifyListByPage(page,storyIslandClassify);
		
		request.setAttribute("storyIslandClassifyList", map.get("storyIslandClassifyList"));
		request.setAttribute("page", map.get("page"));
		return "admin/storyIslandClassify/list.jsp";
	}
	
	
	
	/**
	 * 添加分类
	 */
	@RequestMapping("addClassify")
	public void addClassify(String classify) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			StoryIslandClassify sic = storyIslandClassifyService.getClassifyBy(classify);
			if(TextHelper.isNullOrEmpty(sic)){
				storyIslandClassifyService.addClassify(classify);
				map.put(STATUS, SUCCESS);
				Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
				systemLogService.addSystemLog(a, "新增故事分类");
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
	 * 分类排序
	 */
	@RequestMapping("updateNumber")
	public void updateNumber(String id,String number,String ordertype) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			storyIslandClassifyService.updateNumber(id,number,ordertype);
			map.put(STATUS, SUCCESS);
			Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			systemLogService.addSystemLog(a, "修改故事分类排序");
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	/**
	 * 进入故事分类名修改
	 * @param id
	 */
	@RequestMapping("edit")
	public String edit(String id) {
		StoryIslandClassify sic = storyIslandClassifyService.getClassifyById(id);
		request.setAttribute("storyIslandClassify", sic);
		return "admin/storyIslandClassify/edit.jsp";
	}
	/**
	 * 更改分类名
	 * @param id
	 * @param classify
	 */
	@RequestMapping("save")
	public void save(String id,String classify) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			storyIslandClassifyService.save(id,classify);
			map.put(STATUS, SUCCESS);
			Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			systemLogService.addSystemLog(a, "修改故事分类名");
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	
	/**
	 * 分类排序
	 */
	@RequestMapping("deleteClassify")
	public void deleteClassify(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			String msg = storyIslandClassifyService.deleteClassify(id);
			if(TextHelper.isNullOrEmpty(msg)){
				map.put(STATUS, SUCCESS);
				Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
				systemLogService.addSystemLog(a, "删除故事分类排序");
			}else{
				map.put("msg", msg);
				map.put(STATUS, ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
}
