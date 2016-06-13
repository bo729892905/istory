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
import cn.bluemobi.service.AdminService;
import cn.bluemobi.util.encryption.MD5Tools;
import cn.bluemobi.util.helper.ValidateHelper;
import cn.bluemobi.util.text.TextHelper;
/**
 * 后台管理员
 * @author xiazf
 *
 */
@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private BaseDao dao;
	/**
	 * 根据用户名获取用户
	 * @param username
	 * @return
	 */
	public Admin getAdminByUsername(String username){
		return dao.findForObject(" SELECT a.id,a.username,a.`password`,a.`name`,a.`status`,a.`role_id` AS roleId ,r.role_name AS roleName,DATE_FORMAT(a.login_time, '%Y-%m-%d %H:%i:%s') AS loginTime FROM `admin` AS a LEFT JOIN `admin_role` AS r ON r.id = a.role_id WHERE username = ? ", Admin.class,username);
	}
	/**
	 * 获取管理员列表
	 * @return
	 */
	public Map<String,Object> getAdminList(Page page,Admin admin){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		Object[] array = null;
		Object[] array1 = null;
		if (!ValidateHelper.isNullOrEmpty(admin.getName())) {
			sb.append(" and name like ?");
			list.add('%' + admin.getName() + '%');
		}
		if (!ValidateHelper.isNullOrEmpty(admin.getUsername())) {
			sb.append(" and username like ?  ");
			list.add('%' + admin.getUsername() + '%');
		}
		String sql = sb.toString();
		array1 = list.toArray();
		sb.append(" ORDER BY id ASC  LIMIT ?, ? ");
		Integer count = dao.findForInt(" SElECT COUNT(*) FROM `admin` WHERE 1 = 1 " + sql,array1);
		page.setTotalResult(count);
		list.add(page.getCurrentResult());
		list.add(page.getShowCount());
		array = list.toArray();
		List<Admin> adminList = dao.findForList(" SELECT * FROM `admin` WHERE 1=1 "+sb.toString(), Admin.class,array);
		map.put("page", page);
		map.put("adminList", adminList);
		return map;
		
	}
	/**
	 * 获取管理员列表count
	 * @return
	 */
	public Integer getAdminListCount(){
		return dao.findForInt(" SELECT count(*) FROM `admin` ");
	}
	/**
	 * 根据id获取管理员信息
	 * @param id
	 * @return
	 */
	public Admin getAdminById(String id){
		return dao.findForObject(" SELECT a.id,a.username,a.`password`,a.`name`,a.`status`,a.`role_id` AS roleId FROM `admin` AS a WHERE id =? ", Admin.class,id);
	}
	/**
	 * 更新管理员资料
	 * @param admin
	 */
	public void updateAdmin(Admin admin,String type){
		String sql = "";
		if(TextHelper.isNullOrEmpty(type)){
			sql +=" , `status`=:status,`role_id`=:roleId ";
		}
		if(!TextHelper.isNullOrEmpty(admin.getPassword())){
			admin.setPassword(MD5Tools.encode(admin.getPassword()));
			sql += " ,password=:password ";
		}
		sql += " WHERE id=:id ";
		dao.executeByObject(" UPDATE `admin` SET `name`=:name "+sql, admin);
	}
	/**
	 * 新增管理员
	 * @param admin
	 */
	public void addAdmin(Admin admin){
		admin.setPassword(MD5Tools.encode(admin.getPassword()));
		dao.executeByObject(" INSERT INTO `admin` SET `username`=:username,`name`=:name,`password`=:password,`status`=:status,`role_id`=:roleId ", admin);
	}
	/**
	 * 更新登录时间
	 * @param id
	 */
	public void updateLoginTime(String id){
		dao.executeByParams(" UPDATE `admin` SET `login_time`=NOW() WHERE id = ?  ", id);
	}
}
