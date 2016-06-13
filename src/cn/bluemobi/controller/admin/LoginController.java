package cn.bluemobi.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bluemobi.constant.BlueMobiConstant;
import cn.bluemobi.controller.BaseController;
import cn.bluemobi.entity.Admin;
import cn.bluemobi.service.AdminService;
import cn.bluemobi.service.SystemLogService;
import cn.bluemobi.util.encryption.MD5Tools;
/**
 * 后台登录页面
 * @author xiazf
 *
 */
@Controller
@Scope("prototype")
public class LoginController extends BaseController{
	@Autowired
	private AdminService adminService;
	@Autowired
	private SystemLogService systemLogService;
	
	/**
	 * 进入登录页面
	 * @return
	 */
	@RequestMapping("admin/login")
	public String login(){
		return "/admin/login.jsp";
	}
	
	/**
	 * 登录提交
	 * @return
	 */
	@RequestMapping("admin/login/submit")
	public void submit(Admin admin,String code){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			if(code==null||!code.toLowerCase().equals(session.getAttribute("sessionSecCode").toString().toLowerCase())){
				map.put("info", "codeError");
			}else{
				admin.setPassword(MD5Tools.encode(admin.getPassword()));
				Admin ad=adminService.getAdminByUsername(admin.getUsername());
				if(ad==null){
					//账号错误
					map.put("info", "usernameError");
				}else{
					if(ad.getStatus().equals("0")){
						map.put("info", "statusError");
					}else if(!ad.getPassword().equals(admin.getPassword())){
						map.put("info", "passwordError");
					}else{
						//把登录用户信息存入session
						session.setAttribute(BlueMobiConstant.ADMIN_USER, ad);
						adminService.updateLoginTime(ad.getId().toString());
						systemLogService.addSystemLog(ad, "登录后台");
						map.put(STATUS, SUCCESS);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			outJSON(map);
		}
	}
	
	/**
	 * 登出
	 * @return
	 */
	@RequestMapping("admin/loginout")
	public String loginout(){
		Admin admin  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
		systemLogService.addSystemLog(admin, "退出后台");
		session.invalidate();
		return "/admin/login.jsp";
	}
	
}
