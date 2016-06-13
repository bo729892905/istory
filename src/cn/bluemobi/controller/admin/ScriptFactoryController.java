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
import cn.bluemobi.entity.ScriptChapter;
import cn.bluemobi.entity.ScriptFactory;
import cn.bluemobi.service.ScriptFactoryService;
import cn.bluemobi.service.SystemLogService;
import cn.bluemobi.util.text.TextHelper;

/**
 * 剧本工厂
 * @author xiazf
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("admin/scriptFactory/")
public class ScriptFactoryController extends BaseController {
	@Autowired
	private ScriptFactoryService scriptFactoryService;
	@Autowired
	private SystemLogService systemLogService;
	
	/**
	 * 进入剧本工厂列表
	 * @return
	 */
	@RequestMapping("list")
	public String list(Page page,ScriptFactory scriptFactory) {
		Map<String,Object> map = scriptFactoryService.getScriptFactoryListByPage(page,scriptFactory);
		request.setAttribute("scriptFactoryList", map.get("scriptFactoryList"));
		request.setAttribute("page", map.get("page"));
		return "admin/scriptFactory/list.jsp";
	}
	/**
	 * 剧本状态修改
	 */
	@RequestMapping("updateStatus")
	public void updateStatus(String ids,String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			if(!TextHelper.isNullOrEmpty(ids)){
				String idArr[] = ids.split(",");
				scriptFactoryService.updateStatus(idArr,status);
				map.put(STATUS, SUCCESS);
				Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
				systemLogService.addSystemLog(a, "修改剧本状态");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	
	/**
	 * 删除剧本
	 */
	@RequestMapping("deleteScriptFactory")
	public void deleteScriptFactory(String ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			if(!TextHelper.isNullOrEmpty(ids)){
				String[] idArr = ids.split(",");
				for (int i = 0; i < idArr.length; i++) {
					scriptFactoryService.deleteScriptFactory(idArr[i]);
				}
			}
			Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			systemLogService.addSystemLog(a, "删除剧本");
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
	@RequestMapping("goEditScript")
	public String goEditScript(String id) {
		ScriptFactory sf = scriptFactoryService.getScriptFactory(id,"");
		request.setAttribute("scriptFactory", sf);
		return "admin/scriptFactory/edit.jsp";
	}
	
	/**
	 * 编辑剧本工厂
	 */
	@RequestMapping("save")
	public void save(ScriptFactory sf) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			Admin admin = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			sf.setReleaseId(admin.getId());
			//1是管理员 0是用户
			sf.setAuthorType("1");
			scriptFactoryService.save(sf);
			map.put(STATUS, SUCCESS);
			systemLogService.addSystemLog(admin, "修改剧本工厂");
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
	@RequestMapping("goAddScript")
	public String goAddScript() {
		return "admin/scriptFactory/add.jsp";
	}
	
	/**
	 * 进入剧本场次列表
	 * @return
	 */
	@RequestMapping("chapterList")
	public String chapterList(Page page,String id) {
		Map<String,Object> map = scriptFactoryService.getChapterListByPage(page,id);
		request.setAttribute("id", id);
		request.setAttribute("chapterList", map.get("chapterList"));
		request.setAttribute("page", map.get("page"));
		return "admin/scriptFactory/chapterList.jsp";
	}
	
	/**
	 * 进入场次详情
	 */
	@RequestMapping("chapterInfo")
	public String goChapterInfo(String id,String scriptId) {
		if(!TextHelper.isNullOrEmpty(id)){
			ScriptChapter sc = scriptFactoryService.getChapterById(id);
			request.setAttribute("scriptChapter", sc);
		}
		request.setAttribute("scriptId", scriptId);
		return "admin/scriptFactory/chapterInfo.jsp";
	}
	/**
	 * 编辑场次详情
	 */
	@RequestMapping("chapterEdit")
	public void goChapterEdit(String id,String text,String scriptId) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			Admin admin = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			text = TextHelper.rhtml(text);
			scriptFactoryService.updateScriptChapter(id,text,scriptId);
			map.put(STATUS, SUCCESS);
			systemLogService.addSystemLog(admin, "修改剧本场次详情");
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
}
