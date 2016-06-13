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
import cn.bluemobi.entity.Advertisement;
import cn.bluemobi.entity.MicroFilm;
import cn.bluemobi.entity.Page;
import cn.bluemobi.entity.ScriptFactory;
import cn.bluemobi.entity.StoryIsland;
import cn.bluemobi.service.AdvertisementService;
import cn.bluemobi.service.MicroFilmService;
import cn.bluemobi.service.ScriptFactoryService;
import cn.bluemobi.service.StoryIslandService;
import cn.bluemobi.service.SystemLogService;
import cn.bluemobi.util.text.TextHelper;

/**
 * 广告管理
 * @author xiazf
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("admin/advertisement/")
public class AdvertisementController extends BaseController {

	@Autowired
	private AdvertisementService advertisementService;
	@Autowired
	private SystemLogService systemLogService;
	@Autowired
	private StoryIslandService storyIslandService;
	@Autowired
	private MicroFilmService microFilmService;
	@Autowired
	private ScriptFactoryService scriptFactoryService;
	
	/**
	 * 进入广告列表
	 * @return
	 */
	@RequestMapping("list")
	public String list(Page page,Advertisement advertisement) {
		Map<String,Object> map = advertisementService.getAdvertisementList(page,advertisement);
		request.setAttribute("advertisementList", map.get("advertisementList"));
		request.setAttribute("page", map.get("page"));
		if("1".equals(advertisement.getType())){
			return "admin/advertisement/islandList.jsp";
		}else if("2".equals(advertisement.getType())){
			return "admin/advertisement/microFilmList.jsp";
		}else if("3".equals(advertisement.getType())){
			return "admin/advertisement/scriptFactoryList.jsp";
		}else{
			return ERROR_VIEW;
		}
		
	}
	
	/**
	 * 进入故事岛列表
	 * @return
	 */
	@RequestMapping("islandListAll")
	public String list(Page page,StoryIsland storyIsland) {
		storyIsland.setStatus("1");
		Map<String,Object> map = storyIslandService.getStoryIslandListByPage(page,storyIsland);
		request.setAttribute("storyIslandList", map.get("storyIslandList"));
		request.setAttribute("page", map.get("page"));
		return "admin/advertisement/islandListAll.jsp";
	}
	
	
	
	/**
	 * 广告排序
	 */
	@RequestMapping("updateNumber")
	public void updateNumber(String id,String number,String ordertype) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			advertisementService.updateNumber(id,number,ordertype);
			map.put(STATUS, SUCCESS);
			Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			systemLogService.addSystemLog(a, "修改广告排序");
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	
	/**
	 * 广告状态修改
	 */
	@RequestMapping("updateStatus")
	public void updateStatus(String ids,String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			if(!TextHelper.isNullOrEmpty(ids)){
				String idArr[] = ids.split(",");
				advertisementService.updateStatus(idArr,status);
				map.put(STATUS, SUCCESS);
				Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
				systemLogService.addSystemLog(a, "修改广告状态");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	
	/**
	 * 进入轮播图修改页面
	 * @return
	 */
	@RequestMapping("goEditAdvertisement")
	public String goEditAdvertisement(String id) {
		Advertisement ad = advertisementService.getAdvertisementById(id);
		request.setAttribute("advertisement", ad);
		return "admin/advertisement/edit.jsp";
	}
	
	/**
	 * 轮播图修改保存
	 */
	@RequestMapping("save")
	public void save(Advertisement advertisement) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			if("1".equals(advertisement.getLinkType()) && !TextHelper.isNullOrEmpty(advertisement.getLinkId())){
				String msg = "";
				if("1".equals(advertisement.getType())){
					StoryIsland story = storyIslandService.getStoryIslandById(advertisement.getLinkId().toString());
					if(TextHelper.isNullOrEmpty(story)){
						msg="id_error";
					}else if("0".equals(story.getStatus())){
						msg="status_error";
					}
				}else if("2".equals(advertisement.getType())){
					MicroFilm film = microFilmService.getMicroFilmById(advertisement.getLinkId().toString());
					if(TextHelper.isNullOrEmpty(film)){
						msg="id_error";
					}else if("0".equals(film.getStatus())){
						msg="status_error";
					}
				}else if("3".equals(advertisement.getType())){
					ScriptFactory script = scriptFactoryService.getScriptFactoryById(advertisement.getLinkId().toString());
					if(TextHelper.isNullOrEmpty(script)){
						msg="id_error";
					}else if("0".equals(script.getStatus())){
						msg="status_error";
					}
				}
				if(!TextHelper.isNullOrEmpty(msg)){
					map.put("msg", msg);
					return;
				}
			}
			advertisementService.updateAdvertisement(advertisement);
			map.put(STATUS, SUCCESS);
			Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			systemLogService.addSystemLog(a, "修改广告资料");
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	
}
