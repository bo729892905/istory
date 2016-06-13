package cn.bluemobi.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bluemobi.constant.BlueMobiConstant;
import cn.bluemobi.controller.BaseController;
import cn.bluemobi.entity.Admin;
import cn.bluemobi.entity.Page;
import cn.bluemobi.entity.StoryIsland;
import cn.bluemobi.entity.StoryIslandChapter;
import cn.bluemobi.entity.StoryIslandClassify;
import cn.bluemobi.service.StoryIslandClassifyService;
import cn.bluemobi.service.StoryIslandService;
import cn.bluemobi.service.SystemLogService;
import cn.bluemobi.util.text.TextHelper;
/**
 * 故事岛分类
 * @author xiazf
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("admin/storyIsland/")
public class StoryIslandController extends BaseController {
	@Autowired
	private StoryIslandService storyIslandService;
	@Autowired
	private StoryIslandClassifyService storyIslandClassifyService;
	@Autowired
	private SystemLogService systemLogService;
	
	/**
	 * 进入故事岛列表
	 * @return
	 */
	@RequestMapping("list")
	public String list(Page page,StoryIsland storyIsland) {
		Map<String,Object> map = storyIslandService.getStoryIslandListByPage(page,storyIsland);
		//故事岛分类
		List<StoryIslandClassify> sicList = storyIslandClassifyService.getClassifyList();
		request.setAttribute("sicList", sicList);
		
		request.setAttribute("storyIslandList", map.get("storyIslandList"));
		request.setAttribute("page", map.get("page"));
		return "admin/storyIsland/list.jsp";
	}
	
	/**
	 * 进入故事岛章节列表
	 * @return
	 */
	@RequestMapping("chapterList")
	public String chapterList(Page page,String id,String pageNO) {
		Map<String,Object> map = storyIslandService.getChapterListByPage(page,id);
		request.setAttribute("id", id);
		request.setAttribute("chapterList", map.get("chapterList"));
		request.setAttribute("page", map.get("page"));
		request.setAttribute("pageNO", pageNO);
		return "admin/storyIsland/chapterList.jsp";
	}
	
	/**
	 * 进入故事详情
	 * @return
	 */
	@RequestMapping("goStoryIslandInfo")
	public String goStoryIslandInfo(String id) {
		StoryIsland si = storyIslandService.getStoryIslandById(id);
		request.setAttribute("storyIsland", si);
		return "admin/storyIsland/edit.jsp";
	}
	/**
	 * 故事资料修改
	 */
	@RequestMapping("updateChoicenessAndHot")
	public void updateChoicenessAndHot(String id,String choiceness,String hot) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			storyIslandService.updateChoicenessAndHot(id,choiceness,hot);
			map.put(STATUS, SUCCESS);
			Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			systemLogService.addSystemLog(a, "修改故事资料");
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	
	
	
	/**
	 * 进入章节详情
	 */
	@RequestMapping("chapterInfo")
	public String goChapterInfo(String id) {
		StoryIslandChapter sc = storyIslandService.getChapterById(id);
		request.setAttribute("storyIslandChapter", sc);
		return "admin/storyIsland/chapterInfo.jsp";
	}
	
	/**
	 * 故事状态修改
	 */
	@RequestMapping("updateStatus")
	public void updateStatus(String ids,String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			if(!TextHelper.isNullOrEmpty(ids)){
				String idArr[] = ids.split(",");
				storyIslandService.updateStatus(idArr,status);
				map.put(STATUS, SUCCESS);
				Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
				systemLogService.addSystemLog(a, "修改故事状态");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	/**
	 * 删除故事
	 */
	@RequestMapping("deleteStoryIsland")
	public void deleteStoryIsland(String ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			if(!TextHelper.isNullOrEmpty(ids)){
				String[] idArr = ids.split(",");
				for (int i = 0; i < idArr.length; i++) {
					storyIslandService.deleteStoryIsland(idArr[i]);
				}
			}
			Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			systemLogService.addSystemLog(a, "删除故事");
			map.put(STATUS, SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	
}
