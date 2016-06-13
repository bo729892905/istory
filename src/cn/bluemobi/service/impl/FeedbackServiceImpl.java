package cn.bluemobi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.dao.BaseDao;
import cn.bluemobi.entity.Feedback;
import cn.bluemobi.entity.Page;
import cn.bluemobi.service.FeedbackService;
import cn.bluemobi.util.helper.ValidateHelper;

/**
 * 意见反馈
 * @author xiazf
 *
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {
	@Autowired
	private BaseDao dao;
	/**
	 * 新增意见反馈
	 * @param memberId
	 * @param text
	 */
	@Override
	public void create(String memberId, String text) {
		dao.executeByParams("INSERT INTO feedback set text=?,status=0,create_time=now(),member_id=?", text,memberId);
	}
	/**
	 * 获取意见反馈列表
	 * @param page
	 * @param feedback 
	 * @return
	 */
	public Map<String, Object> getFeedbackList(Page page, Feedback feedback){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		Object[] array = null;
		Object[] array1 = null;
		if (!ValidateHelper.isNullOrEmpty(feedback.getText())) {
			sb.append(" and f.text like ?");
			list.add('%' + feedback.getText() + '%');
		}
		//用户id
		if (!ValidateHelper.isNullOrEmpty(feedback.getMemberId())) {
			sb.append(" and f.member_id = ?  ");
			list.add(feedback.getMemberId());
		}
		//用户状态
		if (!ValidateHelper.isNullOrEmpty(feedback.getStatus())) {
			sb.append(" and f.status = ?  ");
			list.add(feedback.getStatus());
		}
		// 注册日期
		if (!ValidateHelper.isNullOrEmpty(feedback.getStartTime())) {
			sb.append(" and DATE_FORMAT(f.create_time, '%Y-%m-%d')>=  DATE_FORMAT( ?, '%Y-%m-%d')");
			list.add(feedback.getStartTime());
		}
		if (!ValidateHelper.isNullOrEmpty(feedback.getEndTime())) {
			sb.append(" and DATE_FORMAT(f.create_time, '%Y-%m-%d')<=  DATE_FORMAT( ?, '%Y-%m-%d')");
			list.add(feedback.getEndTime());
		}
		String sql = sb.toString();
		array1 = list.toArray();
		sb.append(" ORDER BY f.create_time DESC  LIMIT ?, ? ");
		Integer count = dao.findForInt(" SElECT COUNT(*) FROM `feedback` AS f WHERE 1 = 1 " + sql,array1);
		page.setTotalResult(count);
		list.add(page.getCurrentResult());
		list.add(page.getShowCount());
		array = list.toArray();
		List<Feedback> feedbackList = dao
				.findForList(
						" SELECT f.id,f.text,f.reply,f.`status`,DATE_FORMAT(f.create_time, '%Y-%m-%d %H:%i:%s')  AS createTime,m.`name` FROM `feedback` AS f LEFT JOIN `member` AS m ON m.id = f.member_id WHERE 1=1  " + sb.toString(),
						Feedback.class, array);
		map.put("page", page);
		map.put("feedbackList", feedbackList);
		return map;
	}
	
	/**
	 * 回复意见反馈
	 * @param id
	 * @param reply
	 */
	public void replyFeedback(String id, String reply){
		dao.executeByParams(" UPDATE `feedback` SET reply = ? ,status= '1' WHERE id = ? ", reply,id);
	}
	
	/**
	 * 获取回复
	 * @param id
	 * @return
	 */
	public Feedback getFeedbackById(String id){
		return dao.findForObject(" SELECT f.id,f.text,f.reply,f.`status`,DATE_FORMAT(f.create_time, '%Y-%m-%d %H:%i:%s')  AS createTime,f.member_id AS memberId  FROM `feedback` AS f WHERE id = ?  ",Feedback.class,Integer.valueOf(id));
	}
}
