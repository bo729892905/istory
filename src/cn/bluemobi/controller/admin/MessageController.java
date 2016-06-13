package cn.bluemobi.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bluemobi.constant.BlueMobiConstant;
import cn.bluemobi.controller.BaseController;
import cn.bluemobi.controller.PushExample;
import cn.bluemobi.entity.Admin;
import cn.bluemobi.entity.Message;
import cn.bluemobi.entity.Page;
import cn.bluemobi.service.MemberService;
import cn.bluemobi.service.MessagePushService;
import cn.bluemobi.service.MessageService;
import cn.bluemobi.service.SystemLogService;
import cn.bluemobi.util.text.TextHelper;

/** 
 * 消息管理
 * @author xiazf
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("admin/message/")
public class MessageController extends BaseController {

	@Autowired
	private MessageService messageService;
	@Autowired
	private SystemLogService systemLogService;
	@Autowired
	private MessagePushService messagePushService;
	@Autowired
	private MemberService memberService;
	/**
	 * 进入消息列表
	 * @return
	 */
	@RequestMapping("list")
	public String list(Page page) {
		//获取封面list
		Map<String,Object> map = messageService.getMessageServiceList(page);
		request.setAttribute("messageList", map.get("messageList"));
		request.setAttribute("page", map.get("page"));
		return "admin/message/list.jsp";
	}
	

	/**
	 * 进入新增消息
	 * @return
	 */
	@RequestMapping("goAddMessage")
	public String goAddMessage() {
		return "admin/message/add.jsp";
	}

	/**
	 * 新增消息保存
	 * @return
	 */
	@RequestMapping("add")
	public void add(String title,String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			messageService.save(title,content);
			map.put(STATUS, SUCCESS);
			
			Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			systemLogService.addSystemLog(a, "新增消息");
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	/**
	 * 进入编辑消息
	 * @return
	 */
	@RequestMapping("goEditMessage")
	public String goEditMessage(String id) {
		Message message = messageService.getMessageById(id);
		request.setAttribute("message", message);
		return "admin/message/edit.jsp";
	}

	/**
	 * 新增消息保存
	 * @return
	 */
	@RequestMapping("edit")
	public void edit(String id,String title,String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			messageService.updateMessage(title,content,id);
			map.put(STATUS, SUCCESS);
			Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			systemLogService.addSystemLog(a, "修改消息");
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	
	/**
	 * 删除消息
	 */
	@RequestMapping("deleteMessage")
	public void deleteMessage(String ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			if(!TextHelper.isNullOrEmpty(ids)){
				String[] idArr = ids.split(",");
				for (int i = 0; i < idArr.length; i++) {
					messageService.deleteMessage(idArr[i]);
				}
			}
			map.put(STATUS, SUCCESS);
			Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			systemLogService.addSystemLog(a, "删除消息");
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	
	/**
	 * 发送消息
	 */
	@RequestMapping("sendMessage")
	public void sendMessage(final String id,final String sendTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			if(!TextHelper.isNullOrEmpty(id)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date = sdf.format(new Date());
				Message me = messageService.getMessageById(id);
				
				if(TextHelper.isNullOrEmpty(sendTime)){
					//发送消息
//					pushMessage()
					
					messagePushService.add(id,date,"1");
					PushExample.testSendPushApp(me.getContent());
				}else{//定时发送
				    Date d1 = sdf.parse(sendTime);
				    Date d2 = sdf.parse(date);
				    long diff = (d1.getTime() - d2.getTime())/1000;
					if(diff<=0L){
						//发送消息
//						pushMessage()
						messagePushService.add(id,date,"1");
						PushExample.testSendPushApp(me.getContent());
					}else{
				        final ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);  
				        final Long mid = messagePushService.add(id,sendTime,"0");
				        schedule.schedule( new Runnable() {
						      public void run() {
						    		//发送消息
//									pushMessage()
									messagePushService.updateMessagePush(mid,"1");
									Message me = messageService.getMessageById(id);
									PushExample.testSendPushApp(me.getContent());
						      }
						    },diff, TimeUnit.SECONDS);  
				        schedule.shutdown();
					}
				}
				map.put(STATUS, SUCCESS);
				
				Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
				systemLogService.addSystemLog(a, "发送消息");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	
	
}
