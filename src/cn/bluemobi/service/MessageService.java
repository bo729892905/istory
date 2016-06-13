package cn.bluemobi.service;

import java.util.Map;

import cn.bluemobi.entity.Message;
import cn.bluemobi.entity.Page;

/**
 * 消息
 * @author xiazf
 *
 */
public interface MessageService {
	/**
	 * 获取消息列表
	 * @param page
	 * @return
	 */
	public Map<String, Object> getMessageServiceList(Page page);
	/**
	 * 新增消息
	 * @param title
	 * @param content
	 */
	public void save(String title, String content);
	/**
	 * 获取message
	 * @param id
	 */
	public Message getMessageById(String id);
	/**
	 * 修改消息
	 * @param title
	 * @param content
	 * @param content2 
	 */
	public void updateMessage(String title, String content, String id);
	/**
	 * 删除消息
	 * @param id
	 */
	public void deleteMessage(String id);

}
