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
import cn.bluemobi.entity.Feedback;
import cn.bluemobi.entity.Page;
import cn.bluemobi.service.FeedbackService;
import cn.bluemobi.service.PushLogService;
import cn.bluemobi.service.SystemLogService;

/** 
 * 意见反馈
 * @author xiazf
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("admin/feedback/")
public class FeedbackController extends BaseController {

	@Autowired
	private FeedbackService feedbackService;
	@Autowired
	private SystemLogService systemLogService;
	@Autowired
	private PushLogService pushLogService;
	/**
	 * 进入意见反馈列表
	 * @return
	 */
	@RequestMapping("list")
	public String list(Page page,Feedback feedback) {
		//获取封面list
		Map<String,Object> map = feedbackService.getFeedbackList(page,feedback);
		request.setAttribute("feedbackList", map.get("feedbackList"));
		request.setAttribute("page", map.get("page"));
		return "admin/feedback/list.jsp";
	}
	

	/**
	 * 进入回复
	 * @return
	 */
	@RequestMapping("goReply")
	public String goReply(String id) {
		request.setAttribute("id", id);
		return "admin/feedback/reply.jsp";
	}

	/**
	 * 新增回复保存
	 * @return
	 */
	@RequestMapping("save")
	public void save(String id,String reply) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			feedbackService.replyFeedback(id,reply);
			map.put(STATUS, SUCCESS);
			Feedback fb = feedbackService.getFeedbackById(id);
			Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			systemLogService.addSystemLog(a, "新增回复");
			//记录消息 type 1
			pushLogService.add(id,"1",fb.getMemberId().toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	

}
