package cn.bluemobi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.dao.BaseDao;
import cn.bluemobi.entity.Member;
import cn.bluemobi.entity.MessagePush;
import cn.bluemobi.entity.Page;
import cn.bluemobi.service.MemberService;
import cn.bluemobi.service.MessagePushService;
import cn.bluemobi.service.PushLogService;
import cn.bluemobi.util.helper.ValidateHelper;
import cn.bluemobi.util.text.TextHelper;
/**
 * 推送消息
 * @author xiazf
 *
 */
@Service
public class MessagePushServiceImpl implements MessagePushService {
	@Autowired
	private BaseDao dao;
	@Autowired
	private MemberService memberService;
	@Autowired
	private PushLogService pushLogService;
	
	public Long add(String id, String date, String status){
		MessagePush mp = new MessagePush();
		mp.setMessageId(Long.valueOf(id));
		mp.setPushTime(date);
		mp.setStatus(status);
		Long mId = dao.saveGetKey(" INSERT INTO `message_push` SET `message_id` =:messageId, `push_time` =:pushTime ,`status`=:status  ", mp);
		if("1".equals(status)){
			//获取所有用户
			List<Member> list = memberService.getAllMemberList();
			if(!TextHelper.isNullOrEmpty(list)){
				for (int i = 0; i < list.size(); i++) {
					pushLogService.add(mId.toString(), "2", list.get(i).getId().toString());
				}
			}
		}
		return mId;
	}
	
	/**
	 * 更新推送状态
	 * @param mid
	 * @param string
	 */
	public void updateMessagePush(Long mid, String status){
		dao.executeByParams(" UPDATE `message_push` SET status=?  WHERE id = ?", status,mid);
		//获取所有用户
		List<Member> list = memberService.getAllMemberList();
		if(!TextHelper.isNullOrEmpty(list)){
			for (int i = 0; i < list.size(); i++) {
				pushLogService.add(mid.toString(), "2", list.get(i).getId().toString());
			}
		}
	}
	/**
	 * 查询消息推送列表
	 * @param page
	 * @return
	 */
	public Map<String, Object> getMessagePushList(MessagePush messagePush,Page page){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		Object[] array = null;
		Object[] array1 = null;
		
		// 推送日期
		if (!ValidateHelper.isNullOrEmpty(messagePush.getStartTime())) {
			sb.append(" and DATE_FORMAT(push_time, '%Y-%m-%d %H:%i:%s')>=  DATE_FORMAT( ?, '%Y-%m-%d %H:%i:%s')");
			list.add(messagePush.getStartTime());
		}
		if (!ValidateHelper.isNullOrEmpty(messagePush.getEndTime())) {
			sb.append(" and DATE_FORMAT(push_time, '%Y-%m-%d %H:%i:%s')<=  DATE_FORMAT( ?, '%Y-%m-%d %H:%i:%s')");
			list.add(messagePush.getEndTime());
		}
		
		String sql = sb.toString();
		array1 = list.toArray();
		
		Integer count = dao.findForInt(" SELECT COUNT(*) FROM `message_push` WHERE 1=1 "+ sql.toString(),array1);
		sb.append(" ORDER BY push_time DESC  LIMIT ?, ? ");
		page.setTotalResult(count);
		//未写完
		list.add(page.getCurrentResult());
		list.add(page.getShowCount());
		array = list.toArray();
		List<MessagePush> messagePushList = dao
				.findForList(
						" SELECT m.id,m.message_id AS messageId,DATE_FORMAT(m.push_time,'%Y-%m-%d %H:%i:%s') AS pushTime,m.`status`,s.title FROM `message_push` AS m "
						+ " LEFT JOIN `message` AS s ON s.id = m.message_id WHERE 1=1 " +sb.toString() ,
						MessagePush.class,array);
		map.put("page", page);
		map.put("messagePushList", messagePushList);
		return map;
	}
}
