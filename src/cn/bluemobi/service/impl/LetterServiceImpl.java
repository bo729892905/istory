package cn.bluemobi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.dao.BaseDao;
import cn.bluemobi.entity.Letter;
import cn.bluemobi.entity.system.Page;
import cn.bluemobi.service.LetterService;
/**
 * 私信
 * @author xiazf
 *
 */
@Service
public class LetterServiceImpl implements LetterService{
	@Autowired
	private BaseDao dao;
	
	/**
	 * 获取私信联系人列表
	 * @param memberId
	 */
	public List<Letter> getLetterContactList(String memberId,Page page){
		
		if (page.getPageCount() == -1) {
			dao.findPageSum(" SELECT COUNT(DISTINCT(l.send_id))  FROM `letter` AS l  WHERE l.receive_id =?  ", page,memberId);
			if (page.getTotalCount() == 0) {
				return null;
			}
		}
		return dao.findByPage(" SELECT l.send_id AS sendId,MAX(l.create_time) AS createTime,m.`name` AS sendName,(SELECT COUNT(*) FROM `letter` AS le WHERE  le.receive_id = l.receive_id AND le.send_id = l.send_id AND is_read = 0 ) AS num  FROM `letter` AS l LEFT JOIN member AS m ON l.send_id = m.id "
							+ " WHERE l.receive_id = ?  GROUP BY l.send_id ORDER BY MAX(l.create_time) DESC ", page,Letter.class, memberId);
	}
	
	/**
	 * 获取私信记录
	 * @param sendId
	 * @param memberId
	 * @param page
	 * @return
	 */
	public List<Letter> getLetterRecordsList(String sendId, String memberId,Page page){
		
		if (page.getPageCount() == -1) {
			dao.findPageSum(" SELECT COUNT(*) FROM `letter` AS l WHERE (send_id = ? AND receive_id= ? ) OR (send_id = ? AND receive_id= ? )  ORDER BY l.create_time DESC  ", page,sendId,memberId,memberId,sendId);
			if (page.getTotalCount() == 0) {
				return null;
			}
		}
		return dao.findByPage(" SELECT l.id, l.send_id AS sendId,l.receive_id AS receiveId,l.type,l.content,DATE_FORMAT(l.create_time,'%Y-%m-%d %H:%i:%s') AS createTime,m.`name`AS sendName,m.icon AS sendIcon FROM `letter` AS l LEFT JOIN member AS m ON l.send_id = m.id WHERE (send_id = ? AND receive_id= ? ) OR (send_id = ? AND receive_id= ? )  ORDER BY l.create_time DESC   ", page,Letter.class, sendId,memberId,memberId,sendId);
		
	}
	
	/**
	 * 修改私信已读
	 */
	public void upDateRead(String sendId, String memberId){
		//将用户之间所有私信设置为已读
		dao.executeByParams(" UPDATE `letter` SET is_read='1' WHERE send_id = ? AND receive_id = ? ", sendId,memberId);
	}
	
	/**
	 * 发送私信
	 * @param sendId
	 * @param receiveId
	 */
	public void sendLetter(String sendId, String receiveId,String type, String content){
		dao.executeByParams(" INSERT INTO letter SET send_id = ? ,receive_id = ?,type = ?,content = ? ,create_time = NOW() ", sendId,receiveId,type,content);
	}
	/**
	 * 获取私信联系人聊天数量
	 * @param memberId
	 * @param letterTime
	 * @return
	 */
	public List<Letter> getLetterContactListByTime(String memberId, String letterTime){
		String sql ="";
//		if(!TextHelper.isNullOrEmpty(letterTime)){
//			sql = " (SELECT COUNT(*) FROM `letter` AS le WHERE  le.receive_id = l.receive_id AND le.send_id = l.send_id AND DATE_FORMAT(le.create_time,'%Y-%m-%d %H:%i:%s') > DATE_FORMAT('"+letterTime+"','%Y-%m-%d %H:%i:%s') ) AS num ";
//		}
		sql = " (SELECT COUNT(*) FROM `letter` AS le WHERE  le.receive_id = l.receive_id AND le.send_id = l.send_id AND is_read = 0 ) AS num ";
		return dao.findForList(" SELECT DISTINCT(l.send_id) AS sendId, "
				+ sql
				+ " FROM `letter` AS l "
				+ " LEFT JOIN member AS m ON l.send_id = m.id  "
				+ " WHERE l.receive_id =? ", Letter.class, memberId);
	}
}
