package cn.bluemobi.controller.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.bluemobi.controller.AppController;
import cn.bluemobi.entity.StoryIsland;
import cn.bluemobi.entity.Subscibe;
import cn.bluemobi.entity.system.AppData;
import cn.bluemobi.entity.system.Page;
import cn.bluemobi.service.AttentionService;
import cn.bluemobi.service.MemberService;
import cn.bluemobi.service.StoryIslandService;
import cn.bluemobi.service.SubscibeService;
import cn.bluemobi.util.text.TextHelper;

/**
 * App订阅接口
 * 
 * @author xiazf
 * 
 */

@Controller
@Scope("prototype")
public class AppSubscibeController extends AppController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private AttentionService  attentionService;
	@Autowired
	private SubscibeService subscibeService;
	@Autowired
	private StoryIslandService storyIslandService;
	/**
	 * 订阅列表
	 */
	@RequestMapping(value = "app/getSubscibeList", method = RequestMethod.POST)
	public void getSubscibeList(String memberId) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if (TextHelper.isNullOrEmpty(memberId)) {
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			} else {
				Page page = new Page();
				page.setPageNo(Integer.parseInt(getParameter("pageNo") == null ? "1": getParameter("pageNo")));
				page.setPageSize(Integer.parseInt(getParameter("pageSize") == null ? "10": getParameter("pageSize")));
				page.setPageCount(-1);
				//查询用户订阅的故事作者列表
				List<Subscibe> sub =  subscibeService.getSubscibeAuthorList(memberId,page);
				//查询作者的故事列表
				if(sub !=null && sub.size() >0 ){
					for (int i = 0; i < sub.size(); i++) {
						List<StoryIsland> list = subscibeService.getStoryIslandList(sub.get(i).getReleaseId().toString(),memberId);
						sub.get(i).setStoryIslandList(list==null? new ArrayList<StoryIsland>():list);
						/*if(list !=null){
							for (int j = 0; j < list.size(); j++) {
								subscibeService.updateReadTime(list.get(j).getId().toString(),memberId);
							}
						}*/
					}
				}
			
				data.putInData("list",sub);
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
	
	/**
	 * 新增或者取消订阅
	 */
	@RequestMapping(value = "app/addOrCancelSubscibe", method = RequestMethod.POST)
	public void addOrCancelSubscibe(String storyId, String memberId,String type) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if (TextHelper.isNullOrEmpty(storyId)||TextHelper.isNullOrEmpty(memberId)) {
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			} else {
				//新增或者取消订阅
				if("1".equals(type)){//订阅
					Subscibe s = subscibeService.getSubscibe(storyId,memberId);
					if(TextHelper.isNullOrEmpty(s)){
						subscibeService.addSubscibe(storyId,memberId);
						data.setStatus(SUCCESS);
					}else{
						data.setStatus(FAIL);
						data.setMsg("already_subscibe");
					}
				}else{//取消订阅
					subscibeService.cancelSubscibe(storyId,memberId);
					data.setStatus(SUCCESS);

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
