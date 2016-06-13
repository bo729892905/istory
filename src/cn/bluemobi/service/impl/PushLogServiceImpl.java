package cn.bluemobi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.dao.BaseDao;
import cn.bluemobi.entity.PushLog;
import cn.bluemobi.entity.system.Page;
import cn.bluemobi.service.PushLogService;
/**
 * 系统推送记录
 * @author xiazf
 *
 */
@Service
public class PushLogServiceImpl implements PushLogService {
	@Autowired
	private BaseDao dao;
	/**
	 *新增消息记录 
	 * @param id
	 * @param string
	 */
	public void add(String id, String type,String memberId){
		dao.executeByParams(" INSERT INTO `push_log` SET push_id = ?,type =?,create_time=Now(),member_id=? ", id,type,memberId);
	}
	
	/**
	 * 分页查询推送记录
	 * @param memberId
	 * @param page
	 * @return
	 */
	public List<PushLog> getPushLogListByPage(String memberId, Page page){
		if (page.getPageCount() == -1) {
			dao.findPageSum("SELECT COUNT(*) FROM `push_log` AS p WHERE p.member_id = ? ", page,memberId);
			if (page.getTotalCount() == 0) {
				return null;
			}
		}
		return dao.findByPage(" SELECT p.id,p.push_id AS pushId,DATE_FORMAT(p.create_time,'%Y-%m-%d %H:%i:%s') AS createTime, p.member_id AS memberId,p.type,CASE p.type WHEN 1 THEN f.text WHEN 2 THEN s.title  END  AS title,"
				+ " CASE p.type WHEN 1 THEN f.reply WHEN 2 THEN s.content  END  AS content "
				+ " FROM `push_log` AS p  "
				+ " LEFT JOIN `message_push` AS m ON m.id = p.push_id  "
				+ " LEFT JOIN `feedback` AS f ON f.id = p.push_id  "
				+ " LEFT JOIN `message` AS s ON s.id = m.message_id "
				+ " WHERE p.member_id = ? AND ((p.type =2 AND m.`status` = 1) OR p.type =1) ORDER BY P.create_time DESC ",page,PushLog.class,memberId);
	}
	
	/**
	 * 删除系统推送消息
	 * @param logId
	 */
	public void deletePushLog(String logId){
		dao.executeByParams(" DELETE FROM `push_log` WHERE id = ? ",logId);
	}
	/**
	 * 系统消息数量
	 * @param memberId
	 * @param messageTime
	 * @return
	 */
	public Integer getPushLogListByTime(String memberId, String messageTime){
		return dao.findForInt(" SELECT COUNT(*) FROM `push_log` AS p WHERE p.member_id = ? AND DATE_FORMAT(p.create_time,'%Y-%m-%d %H:%i:%s') > DATE_FORMAT(?,'%Y-%m-%d %H:%i:%s') ", memberId,messageTime);
	}
}
