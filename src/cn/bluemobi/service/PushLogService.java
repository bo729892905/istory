package cn.bluemobi.service;

import java.util.List;

import cn.bluemobi.entity.PushLog;
import cn.bluemobi.entity.system.Page;

/**
 * 系统推送记录
 * @author xiazf
 *
 */
public interface PushLogService {

	/**
	 *新增消息记录 
	 * @param id
	 * @param string
	 */
	public void add(String id, String type,String memberId);
	/**
	 * 分页查询推送记录
	 * @param memberId
	 * @param page
	 * @return
	 */
	public List<PushLog> getPushLogListByPage(String memberId, Page page);
	/**
	 * 删除系统推送消息
	 * @param logId
	 */
	public void deletePushLog(String logId);
	/**
	 * 系统消息数量
	 * @param memberId
	 * @param messageTime
	 * @return
	 */
	public Integer getPushLogListByTime(String memberId, String messageTime);

}
