package cn.bluemobi.controller.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.bluemobi.controller.AppController;
import cn.bluemobi.entity.StarStory;
import cn.bluemobi.entity.system.AppData;
import cn.bluemobi.service.StarStoryService;
import cn.bluemobi.util.text.TextHelper;

/**
 * 星故事
 * 
 * @author xiazf
 * 
 */

@Controller
@Scope("prototype")
public class AppStarStoryController extends AppController {
	@Autowired
	private StarStoryService starStoryService;
	
	/**
	 * 星故事列表         
	 * 
	 */
	@RequestMapping(value = "app/starStoryList", method = RequestMethod.POST)
	public void starStoryList(String memberId) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			//按照发布时间来添加，同时要查询出是否点赞
			List<StarStory> list =  starStoryService.getStarStoryList(memberId);
			data.putInData("list",list);
			data.setStatus(SUCCESS);
		} catch (Exception e) {
			data.setStatus(FAIL);
			data.setMsg(SYSTEM_ERROR);
			e.printStackTrace();
		}finally{
			outJSON(data);
		}
	}
	/**
	 * 星故事浏览接口
	 */
	@RequestMapping(value = "app/starStoryViewCount", method = RequestMethod.POST)
	public void starStoryViewCount(String id){
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if(TextHelper.isNullOrEmpty(id)){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				//星故事
				starStoryService.addViewCount(id);
				data.setStatus(SUCCESS);
			}
		} catch (Exception e) {
			data.setStatus(FAIL);
			data.setMsg(SYSTEM_ERROR);
			e.printStackTrace();
		}finally{
			outJSON(data);
		}
	}
	
}
