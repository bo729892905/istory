package cn.bluemobi.service;

import java.util.Map;

import cn.bluemobi.entity.MessagePush;
import cn.bluemobi.entity.Page;

/**
 * 发送日志
 * @author xiazf
 *
 */
public interface MessagePushService {
	/**
	 * 创建发送日志
	 * @param id
	 * @param date
	 * @param status
	 */
	public Long add(String id, String date, String status);
	/**
	 * 更新推送状态
	 * @param mid
	 * @param string
	 */
	public void updateMessagePush(Long mid, String status);
	/**
	 * 查询消息推送列表
	 * @param messagePush 
	 * @param page
	 * @return
	 */
	public Map<String, Object> getMessagePushList(MessagePush messagePush, Page page);

}
