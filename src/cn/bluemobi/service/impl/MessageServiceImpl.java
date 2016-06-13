package cn.bluemobi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.dao.BaseDao;
import cn.bluemobi.entity.Message;
import cn.bluemobi.entity.MessagePush;
import cn.bluemobi.entity.Page;
import cn.bluemobi.service.MessagePushService;
import cn.bluemobi.service.MessageService;
import cn.bluemobi.service.PushLogService;
import cn.bluemobi.util.text.TextHelper;

/**
 * 消息
 * @author xiazf
 *
 */
@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	private BaseDao dao;
	@Autowired
	private PushLogService pushLogService;
	@Autowired
	private MessagePushService messagePushService;
	/**
	 * 获取消息列表
	 * @param page
	 * @return
	 */
	public Map<String, Object> getMessageServiceList(Page page){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		Object[] array = null;
		//查询总数
		Integer count = dao.findForInt(" SELECT COUNT(*) FROM `message` ");
		sb.append(" ORDER BY create_time DESC  LIMIT ?, ? ");
		page.setTotalResult(count);
		list.add(page.getCurrentResult());
		list.add(page.getShowCount());
		array = list.toArray();
		//分页查询列表
		List<Message> messageList = dao
				.findForList(
						" SELECT a.id,a.title,a.content,DATE_FORMAT(a.create_time,'%Y-%m-%d %H:%i:%s') AS createTime FROM `message` AS a WHERE 1=1 "
						+ sb.toString() ,
						Message.class,array);
		map.put("page", page);
		map.put("messageList", messageList);
		return map;
	}
	
	/**
	 * 新增消息
	 * @param title
	 * @param content
	 */
	public void save(String title, String content){
		dao.executeByParams(" INSERT INTO `message` SET title = ?,content = ?,create_time = NOW()  ", title,content);
	}
	/**
	 * 获取message
	 * @param id
	 */
	public Message getMessageById(String id){
		return dao.findForObject(" SELECT a.id,a.title,a.content,DATE_FORMAT(a.create_time,'%Y-%m-%d %H:%i:%s') AS createTime FROM `message` AS a WHERE a.id = ?  ", Message.class, id);
	}
	/**
	 * 修改消息
	 * @param title
	 * @param content
	 */
	public void updateMessage(String title, String content,String id){
		dao.executeByParams(" UPDATE  `message` SET title = ?,content = ? WHERE id = ?  ", title,content,id);
	}
	/**
	 * 删除消息
	 * @param id
	 */
	public void deleteMessage(String id){
		dao.executeByParams(" DELETE FROM `message` WHERE id = ? ", id);
		List<MessagePush> list = dao.findForList(" SELECT m.id,m.message_id AS messageId,DATE_FORMAT(m.push_time,'%Y-%m-%d %H:%i:%s') AS pushTime,m.`status` FROM `message_push` AS m WHERE message_id = ? ",MessagePush.class,id);
		dao.executeByParams(" DELETE FROM `message_push` WHERE message_id = ? ",id);	
		if(!TextHelper.isNullOrEmpty(list)){
			for (int i = 0; i < list.size(); i++) {
				dao.executeByParams(" DELETE FROM `push_log` WHERE push_id = ? ", list.get(i).getId());
			}
		}
	}
}
