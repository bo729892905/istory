package cn.bluemobi.service;

import java.util.Map;

import cn.bluemobi.entity.Admin;
import cn.bluemobi.entity.Page;

/**
 * 后台管理员
 * @author xiazf
 *
 */
public interface AdminService {
	/**
	 * 根据用户名获取用户
	 * @param username
	 * @return
	 */
	public Admin getAdminByUsername(String username);
	/**
	 * 获取管理员列表
	 * @param admin 
	 * @return
	 */
	public Map<String, Object> getAdminList(Page page, Admin admin);
	/**
	 * 获取管理员列表count
	 * @return
	 */
	public Integer getAdminListCount();
	/**
	 * 根据id获取管理员信息
	 * @param id
	 * @return
	 */
	public Admin getAdminById(String id);
	/**
	 * 更新管理员资料
	 * @param admin
	 * @param type 
	 */
	public void updateAdmin(Admin admin, String type);
	/**
	 * 新增管理员
	 * @param admin
	 */
	public void addAdmin(Admin admin);
	/**
	 * 更新登录时间
	 * @param id
	 */
	public void updateLoginTime(String id);
}
