package cn.bluemobi.controller.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bluemobi.controller.BaseController;
import cn.bluemobi.entity.MessagePush;
import cn.bluemobi.entity.Page;
import cn.bluemobi.service.MessagePushService;
/**
 * 消息推送日志
 * @author xiazf
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("admin/messagePush/")
public class MessagePushController extends BaseController {
	@Autowired
	private MessagePushService messagePushService;
	/**
	 * 进入消息推送日志页面
	 */
	@RequestMapping("list")
	public String list(MessagePush messagePush,Page page) {
		//获取封面list
		Map<String,Object> map = messagePushService.getMessagePushList(messagePush,page);
		request.setAttribute("messagePushList", map.get("messagePushList"));
		request.setAttribute("page", map.get("page"));
		return "admin/messagePush/list.jsp";
	}
}
