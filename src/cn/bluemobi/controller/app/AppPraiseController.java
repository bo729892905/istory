package cn.bluemobi.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.bluemobi.controller.AppController;
import cn.bluemobi.entity.Praise;
import cn.bluemobi.entity.system.AppData;
import cn.bluemobi.service.PraiseService;
import cn.bluemobi.service.ScriptFactoryService;
import cn.bluemobi.service.StarStoryService;
import cn.bluemobi.service.StoryIslandService;
import cn.bluemobi.util.text.TextHelper;

/**
 * 点赞接口
 * 
 * @author xiazf
 * 
 */

@Controller
@Scope("prototype")
public class AppPraiseController extends AppController {
	@Autowired
	private PraiseService praiseService;
	@Autowired
	private StoryIslandService storyIslandService;
	@Autowired
	private ScriptFactoryService scriptFactoryService;
	@Autowired
	private StarStoryService starStoryService;
	/**
	 * 点赞接口         
	 * 
	 * 被点赞类型 1是故事岛2是微电影3是剧本 4是星故事 5是评论
	 */
	@RequestMapping(value = "app/doPraise", method = RequestMethod.POST)
	public void doPraise(Praise praise,String operation) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if(TextHelper.isNullOrEmpty(praise.getBePraiseId())||TextHelper.isNullOrEmpty(praise.getType())||TextHelper.isNullOrEmpty(praise.getPraiseId())){
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			}else{
				if("1".equals(operation)){//点赞
					Integer count= praiseService.getPraise(praise);
					if(count==0){
						praiseService.addPraise(praise);
						if("1".equals(praise.getType())){
							storyIslandService.addStoryHot(praise.getBePraiseId());
						}else if("3".equals(praise.getType())){
							scriptFactoryService.addScriptHot(praise.getBePraiseId());
						}else if("4".equals(praise.getType())){//
							starStoryService.addStarHot(praise.getBePraiseId());
						}
						data.setStatus(SUCCESS);
					}else{
						data.setStatus(FAIL);
						data.setMsg("already_praise");
					}
				}else{//取消点赞
					Integer count= praiseService.getPraise(praise);
					if(count>0){
						praiseService.deletePraise(praise);
						if("1".equals(praise.getType())){
							storyIslandService.deleteStoryHot(praise.getBePraiseId());
						}else if("3".equals(praise.getType())){
							scriptFactoryService.deleteScriptHot(praise.getBePraiseId());
						}else if("4".equals(praise.getType())){//
							starStoryService.deleteStarHot(praise.getBePraiseId());
						}
						data.setStatus(SUCCESS);
					}else{
						data.setStatus(FAIL);
						data.setMsg("already_cancle_praise");
					}
				}
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
