package cn.bluemobi.service;

import java.util.List;

import cn.bluemobi.entity.AdminRole;
import cn.bluemobi.entity.Page;

/**
 * 角色
 * @author xiazf
 *
 */
public interface AdmiRoleService {
	/**
	 * 获取较色列表
	 * @return
	 */
	public List<AdminRole> getRoleNameList();
	/**
	 * 统计角色数量
	 * @return
	 */
	public Integer getAdminRoleListCount();
	/**
	 * 获取角色分页列表
	 * @param page
	 * @return
	 */
	public List<AdminRole> getAdminRoleList(Page page);
	/**
	 * 获取用户权限
	 * @param id
	 * @return
	 */
	public AdminRole getAdminRoleById(String id);
	/**
	 * 修改权限
	 * @param id
	 * @param value
	 * @param roleName
	 */
	public void updateAdminRole(String id, String value, String roleName);
	/**
	 * 修改角色描述
	 * @param id
	 * @param description 
	 */
	public void editAdminRole(String id, String description);
	/**
	 * 查询角色
	 * @param roleName
	 * @return
	 */
	public AdminRole getRoleByname(String roleName);
	/**
	 * 新增角色
	 * @param adminRole
	 */
	public void addAdminRole(AdminRole adminRole);
	/**
	 * 删除角色
	 * @param id
	 */
	public void deleteRole(String id);

}
