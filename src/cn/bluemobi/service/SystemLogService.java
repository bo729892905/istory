package cn.bluemobi.service;

import java.util.Map;

import cn.bluemobi.entity.Admin;
import cn.bluemobi.entity.Page;
import cn.bluemobi.entity.SystemLog;

/**
 * 系统日志
 * @author xiazf
 *
 */
public interface SystemLogService {
	/**
	 * 系统日志列表
	 * @param page
	 * @param systemLog
	 * @return
	 */
	public Map<String, Object> getSystemLogList(Page page, SystemLog systemLog);
	/**
	 * 创建系统日志
	 * @param a 
	 * @param operation
	 */
	public void addSystemLog(Admin admin, String operation);
	

}
