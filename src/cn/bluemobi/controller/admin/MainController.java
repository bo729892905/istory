package cn.bluemobi.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bluemobi.constant.BlueMobiConstant;
import cn.bluemobi.controller.BaseController;
import cn.bluemobi.entity.Admin;
import cn.bluemobi.entity.AdminRole;
import cn.bluemobi.entity.IndexStory;
import cn.bluemobi.entity.Member;
import cn.bluemobi.service.AdmiRoleService;
import cn.bluemobi.service.IndexStoryService;
import cn.bluemobi.service.MemberService;
/**
 * 后台主页面管理
 * @author xiazf
 *
 */
@Controller
@Scope("prototype")
public class MainController extends BaseController {
	@Autowired
	private AdmiRoleService admiRoleService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private IndexStoryService indexStoryService;
	/**
	 * 进入后台主界面
	 * @return
	 */
	@RequestMapping("admin/index")
	public String left() {
		//把登录用户信息存入session
		Admin ad = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
		//获取用户权限list
		AdminRole ar =	admiRoleService.getAdminRoleById(ad.getRoleId().toString());
		request.getSession().setAttribute("adminRoleList", ar);
		return "admin/index.jsp";
	}
	/**
	 * 进入tab标签
	 * @return
	 */
	@RequestMapping(value="admin/tab")
	public String tab(){
		return "admin/tab.jsp";
	}
	
	/**
	 * 进入首页后的默认页面
	 * @return
	 */
	@RequestMapping(value="admin/default")
	public String defaultPage(){
		List<Member> list = memberService.getLatestMemberList();
		List<IndexStory> storyList = indexStoryService.getNewStoryList();
		request.setAttribute("memberList",list);
		request.setAttribute("storyList",storyList);
		return "admin/default.jsp";
	}
	
}
