package cn.bluemobi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.dao.BaseDao;
import cn.bluemobi.entity.AdminRole;
import cn.bluemobi.entity.Page;
import cn.bluemobi.service.AdmiRoleService;
/**
 * 角色
 * @author xiazf
 *
 */
@Service
public class AdmiRoleServiceImpl implements AdmiRoleService {
	@Autowired
	private BaseDao dao;
	/**
	 * 获取较色列表
	 * @return
	 */
	public List<AdminRole> getRoleNameList(){
		return dao.findForList("SELECT a.id,a.role_name AS roleName FROM `admin_role` AS a", AdminRole.class);
	}
	/**
	 * 统计角色数量
	 * @return
	 */
	public Integer getAdminRoleListCount(){
		return dao.findForInt(" SELECT COUNT(*) FROM `admin_role`");
	}
	/**
	 * 获取角色分页列表
	 * @param page
	 * @return
	 */
	public List<AdminRole> getAdminRoleList(Page page){
		 return dao.findForList(" SELECT a.id,a.role_name AS roleName,a.description FROM `admin_role` AS a LIMIT ?,?", AdminRole.class,page.getCurrentResult(),page.getShowCount());
	}
	/**
	 * 获取用户权限
	 * @param id
	 * @return
	 */
	public AdminRole getAdminRoleById(String id){
		return dao.findForObject(" SELECT a.id ,a.role_name AS roleName,a.system,a.member,a.story_island AS storyIsland,a.micro_film AS microFilm,a.script_factory AS scriptFactory,a.star_story AS starStory,a.`comment`,a.advertisement,a.cover,a.message,a.feedback,a.report,a.about_us AS aboutUs,a.description FROM `admin_role` AS a WHERE id = ? ", AdminRole.class,id);
	}
	/**
	 * 修改权限
	 * @param id
	 * @param value
	 * @param roleName
	 */
	public void updateAdminRole(String id, String value, String roleName){
		dao.executeByParams(" UPDATE `admin_role` SET " + roleName + " = ? WHERE id = ? ",value,id);
	}
	/**
	 * 修改角色描述
	 * @param id
	 */
	public void editAdminRole(String id,String description){
		dao.executeByParams(" UPDATE `admin_role` SET  `description` = ? WHERE id = ? ",description,id);
	}
	/**
	 * 查询角色
	 * @param roleName
	 * @return
	 */
	public AdminRole getRoleByname(String roleName){
		return dao.findForObject(" SELECT a.id ,a.role_name AS roleName,a.system,a.member,a.story_island AS storyIsland,a.micro_film AS microFilm,a.script_factory AS scriptFactory,a.star_story AS starStory,a.`comment`,a.advertisement,a.cover,a.feedback,a.report,a.about_us AS aboutUs,a.description FROM `admin_role` AS a WHERE role_name = ? ", AdminRole.class,roleName);
	}
	/**
	 * 新增角色
	 * @param adminRole
	 */
	public void addAdminRole(AdminRole adminRole){
		dao.executeByObject(" INSERT INTO `admin_role` SET `role_name`=:roleName, `description`=:description", adminRole);
	}
	/**
	 * 删除角色
	 * @param id
	 */
	public void deleteRole(String id){
		dao.executeByParams(" DELETE FROM `admin_role` WHERE id = ? ", id);
	}
}
