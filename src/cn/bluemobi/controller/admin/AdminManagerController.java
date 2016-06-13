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
import cn.bluemobi.util.encryption.MD5Tools;
import cn.bluemobi.util.text.TextHelper;
/**
 * 系统管理员
 * @author xiazf
 *
 */
@Controller
@Scope("prototype")
public class AdminManagerController extends BaseController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private AdmiRoleService admiRoleService;
	@Autowired
	private SystemLogService systemLogService;
	/**
	 * 进入系统管理员列表
	 * @return
	 */
	@RequestMapping("admin/list")
	public String adminList(Page page,Admin admin) {
		Map<String,Object> map = adminService.getAdminList(page,admin);
		request.setAttribute("adminList", map.get("adminList"));
		request.setAttribute("page", map.get("page"));
		return "admin/adminManager/list.jsp";
	}
	
	/**
	 * 进入系统管理员编辑
	 * @return
	 */
	@RequestMapping("admin/goEditAdmin")
	public String goEditAdmin(String id) {
		Admin admin = adminService.getAdminById(id);
		List<AdminRole> list = admiRoleService.getRoleNameList();
		request.setAttribute("adminInfo", admin);
		request.setAttribute("roleNameList", list);
		return "admin/adminManager/edit.jsp";
	}
	/**
	 * 保存管理员修改
	 */
	@RequestMapping("admin/save")
	public void save(Admin admin,String type,String password1) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			if(!TextHelper.isNullOrEmpty(type) && !TextHelper.isNullOrEmpty(password1)){
				Admin a = adminService.getAdminById(admin.getId().toString());
				if(!a.getPassword().equals(MD5Tools.encode(password1))){
					map.put(STATUS, ERROR);
					map.put("msg", "password_error");
				}else{
					adminService.updateAdmin(admin,type);
					map.put(STATUS, SUCCESS);
				}
			}else{
				
				adminService.updateAdmin(admin,type);
				map.put(STATUS, SUCCESS);
				Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
				systemLogService.addSystemLog(a, "修改管理员资料");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	
	/**
	 * 进入新增管理员
	 */
	@RequestMapping("admin/goAddAdmin")
	public String goAddAdmin() {
		List<AdminRole> list = admiRoleService.getRoleNameList();
		request.setAttribute("roleNameList", list);
		return "admin/adminManager/add.jsp";
	}
	/**
	 * 新增管理员
	 */
	@RequestMapping("admin/add")
	public void add(Admin admin) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			Admin ad = adminService.getAdminByUsername(admin.getUsername());
			if(TextHelper.isNullOrEmpty(ad)){
				adminService.addAdmin(admin);
				map.put(STATUS, SUCCESS);
				Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
				systemLogService.addSystemLog(a, "新增管理员");
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
	 * 进入管理员个人资料修改
	 */
	@RequestMapping("admin/goEditU")
	public String goEditU(String id) {
		Admin admin = adminService.getAdminById(id);
		request.setAttribute("adminU", admin);
		return "admin/edit.jsp";
	}
	
}
