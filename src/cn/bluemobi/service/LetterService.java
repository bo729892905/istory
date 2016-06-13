package cn.bluemobi.service;

import java.util.List;

import cn.bluemobi.entity.Letter;
import cn.bluemobi.entity.system.Page;

/**
 * 私信
 * @author xiazf
 *
 */
public interface LetterService {
	/**
	 * 获取私信联系人列表
	 * @param memberId
	 * @param page 
	 * @return 
	 */
	public List<Letter> getLetterContactList(String memberId, Page page);
	/**
	 * 获取私信记录
	 * @param sendId
	 * @param memberId
	 * @param page
	 * @return
	 */
	public List<Letter> getLetterRecordsList(String sendId, String memberId,Page page);
	/**
	 * 修改私信已读
	 */
	public void upDateRead(String sendId, String memberId);
	/**
	 * 发送私信
	 * @param sendId
	 * @param receiveId
	 * @param content 
	 * @param type 
	 */
	public void sendLetter(String sendId, String receiveId, String type, String content);
	/**
	 * 获取私信联系人
	 * @param memberId
	 * @param letterTime
	 * @return
	 */
	public List<Letter> getLetterContactListByTime(String memberId, String letterTime);

}
