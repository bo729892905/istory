package cn.bluemobi.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bluemobi.constant.BlueMobiConstant;
import cn.bluemobi.controller.BaseController;
import cn.bluemobi.entity.Admin;
import cn.bluemobi.entity.AdminRole;
import cn.bluemobi.entity.Page;
import cn.bluemobi.service.AdmiRoleService;
import cn.bluemobi.service.AdminService;
import cn.bluemobi.service.SystemLogService;
import cn.bluemobi.util.text.TextHelper;
/**
 * 系统角色
 * @author xiazf
 *
 */
@Controller
@Scope("prototype")
public class AdminRoleController extends BaseController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private AdmiRoleService admiRoleService;
	@Autowired
	private SystemLogService systemLogService;
	/**
	 * 进入角色列表
	 * @return
	 */
	@RequestMapping("admin/adminRole/list")
	public String adminRoleList(Page page) {
		Integer count =  admiRoleService.getAdminRoleListCount();
		page.setTotalResult(count);
		List<AdminRole> list = admiRoleService.getAdminRoleList(page);
		request.setAttribute("adminRoleList", list);
		request.setAttribute("page", page);
		return "admin/adminRole/list.jsp";
	}
	
	/**
	 * 进入权限编辑
	 * @return
	 */
	@RequestMapping("admin/adminRole/goEditRole")
	public String goEditRole(String id) {
		AdminRole  ar = admiRoleService.getAdminRoleById(id);
		request.setAttribute("adminRole", ar);
		return "admin/adminRole/editRole.jsp";
	}
	/**
	 * 保存管理员修改
	 */
	@RequestMapping("admin/adminRole/editRole")
	public void editRole(String id,String value,String roleName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			admiRoleService.updateAdminRole(id,value,roleName);
			map.put(STATUS, SUCCESS);
			Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			systemLogService.addSystemLog(a, "修改角色权限");
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	/**
	 * 进入角色资料编辑
	 * @return
	 */
	@RequestMapping("admin/adminRole/goEdit")
	public String goEdit(String id) {
		AdminRole  ar = admiRoleService.getAdminRoleById(id);
		request.setAttribute("adminRole", ar);
		return "admin/adminRole/edit.jsp";
	}
	/**
	 * 保存角色资料编辑
	 * @return
	 */
	@RequestMapping("admin/adminRole/edit")
	public void edit(String id,String description) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			admiRoleService.editAdminRole(id,description);
			map.put(STATUS, SUCCESS);
			Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			systemLogService.addSystemLog(a, "修改角色资料");
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	
	
	
	/**
	 * 进入新增角色
	 */
	@RequestMapping("admin/adminRole/goAddRole")
	public String goAddRole() {
		return "admin/adminRole/add.jsp";
	}
	/**
	 * 新增角色
	 */
	@RequestMapping("admin/adminRole/add")
	public void addAdminRole(AdminRole adminRole) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			AdminRole ar = admiRoleService.getRoleByname(adminRole.getRoleName());
			if(TextHelper.isNullOrEmpty(ar)){
				admiRoleService.addAdminRole(adminRole);
				map.put(STATUS, SUCCESS);
				Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
				systemLogService.addSystemLog(a, "新增角色");
			}else{
				map.put("msg", "name_exist");
				map.put(STATUS, ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	/**
	 * 删除角色
	 */
	@RequestMapping("admin/adminRole/deleteRole")
	public void deleteRole(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			admiRoleService.deleteRole(id);
			map.put(STATUS, SUCCESS);
			Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			systemLogService.addSystemLog(a, "删除角色");
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	
}
