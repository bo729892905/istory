package cn.bluemobi.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.bluemobi.controller.AppController;
import cn.bluemobi.entity.system.AppData;
import cn.bluemobi.service.FeedbackService;
import cn.bluemobi.util.text.TextHelper;

/**
 * 意见反馈
 * @author xiazf
 *
 */
@Controller
@Scope("prototype")
public class AppFeedbackController extends AppController{
	@Autowired
	private FeedbackService feedbackService;
	/**
	 * 意见反馈
	 */
	@RequestMapping(value = "app/feedback", method = RequestMethod.POST)
	public void feedback(String memberId, String text) {
		AppData data = new AppData();
		data.setStatus(FAIL);
		try {
			if (TextHelper.isNullOrEmpty(memberId) || TextHelper.isNullOrEmpty(text)) {
				data.setStatus(FAIL);
				data.setMsg(PARAM_ERROR);
			} else {
				feedbackService.create(memberId,text);
				data.setStatus(SUCCESS);
			}
		} catch (Exception e) {
			data.setStatus(FAIL);
			data.setMsg(SYSTEM_ERROR);
			e.printStackTrace();
		} finally {
			outJSON(data);
		}
	}
	
}
