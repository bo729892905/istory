package cn.bluemobi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.dao.BaseDao;
import cn.bluemobi.entity.Admin;
import cn.bluemobi.entity.Page;
import cn.bluemobi.entity.SystemLog;
import cn.bluemobi.service.AdminService;
import cn.bluemobi.service.SystemLogService;
import cn.bluemobi.util.helper.ValidateHelper;
/**
 * 系统日志
 * @author xiazf
 *
 */
@Service
public class SystemLogServiceImpl implements SystemLogService {
	@Autowired
	private BaseDao dao;
	@Autowired
	private AdminService adminService;
	/**
	 * 系统日志列表
	 * @param page
	 * @param systemLog
	 * @return
	 */
	public Map<String, Object> getSystemLogList(Page page, SystemLog systemLog){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		Object[] array = null;
		Object[] array1 = null;
		//操作者账号
		if (!ValidateHelper.isNullOrEmpty(systemLog.getUsername())) {
			sb.append(" and username like ?");
			list.add('%' + systemLog.getUsername() + '%');
		}
		// 操作日期
		if (!ValidateHelper.isNullOrEmpty(systemLog.getStartTime())) {
			sb.append(" and DATE_FORMAT(create_time, '%Y-%m-%d')>=  DATE_FORMAT( ?, '%Y-%m-%d')");
			list.add(systemLog.getStartTime());
		}
		if (!ValidateHelper.isNullOrEmpty(systemLog.getEndTime())) {
			sb.append(" and DATE_FORMAT(create_time, '%Y-%m-%d')<=  DATE_FORMAT( ?, '%Y-%m-%d')");
			list.add(systemLog.getEndTime());
		}
		String sql = sb.toString();
		array1 = list.toArray();
		sb.append(" ORDER BY create_time DESC  LIMIT ?, ? ");
		Integer count = dao.findForInt(" SElECT COUNT(*) FROM `system_log` WHERE 1 = 1 " + sql,array1);
		page.setTotalResult(count);
		list.add(page.getCurrentResult());
		list.add(page.getShowCount());
		array = list.toArray();
		List<SystemLog> systemLogList = dao
				.findForList(
						" SELECT s.id,s.username,s.role_name AS roleName,s.operation, DATE_FORMAT(s.login_time, '%Y-%m-%d %H:%i:%s') AS loginTime, DATE_FORMAT(s.create_time, '%Y-%m-%d %H:%i:%s') AS createTime FROM `system_log` AS s WHERE 1=1 " + sb.toString(),
						SystemLog.class, array);
		map.put("page", page);
		map.put("systemLogList", systemLogList);
		return map;
	}
	
	/**
	 * 创建系统日志
	 * @param operation
	 */
	public void addSystemLog(Admin admin,String operation){
		Admin a =adminService.getAdminByUsername(admin.getUsername());
		dao.executeByParams(" INSERT INTO `system_log` SET `username`= ?,role_name = ?,operation = ?,login_time = ?,create_time = NOW() ",a.getUsername(),a.getRoleName(),operation,a.getLoginTime());
	}
}
