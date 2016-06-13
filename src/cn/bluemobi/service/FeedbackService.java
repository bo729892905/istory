package cn.bluemobi.service;

import java.util.Map;

import cn.bluemobi.entity.Feedback;
import cn.bluemobi.entity.Page;

/**
 * 意见反馈
 * @author xiazf
 *
 */
public interface FeedbackService {
	/**
	 * 新增意见反馈
	 * @param memberId
	 * @param text
	 */
	public void create(String memberId, String text);
	/**
	 * 获取意见反馈列表
	 * @param page
	 * @param feedback 
	 * @return
	 */
	public Map<String, Object> getFeedbackList(Page page, Feedback feedback);
	/**
	 * 回复意见反馈
	 * @param id
	 * @param reply
	 */
	public void replyFeedback(String id, String reply);
	/**
	 * 获取回复
	 * @param id
	 * @return
	 */
	public Feedback getFeedbackById(String id);

}
