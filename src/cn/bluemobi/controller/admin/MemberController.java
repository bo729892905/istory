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
import cn.bluemobi.entity.Member;
import cn.bluemobi.entity.Page;
import cn.bluemobi.service.MemberService;
import cn.bluemobi.service.SystemLogService;
import cn.bluemobi.util.text.TextHelper;
/**
 * 用户管理
 * @author xiazf
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("admin/member/")
public class MemberController extends BaseController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private SystemLogService systemLogService;
	/**
	 * 进入用户列表
	 * @return
	 */
	@RequestMapping("list")
	public String list(Page page,Member member) {
		Map<String,Object> map = memberService.getMemberList(page,member);
		request.setAttribute("memberList", map.get("memberList"));
		request.setAttribute("page", map.get("page"));
		return "admin/member/list.jsp";
	}
	
	/**
	 * 进入用户详情
	 * @return
	 */
	@RequestMapping("goMemberInfo")
	public String goMemberInfo(String id) {
		Member  m = memberService.getMemberById(id);
		request.setAttribute("member", m);
		return "admin/member/info.jsp";
	}
	/**
	 * 用户状态修改
	 */
	@RequestMapping("updateStatus")
	public void updateStatus(String ids,String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(STATUS, ERROR);
		try {
			if(!TextHelper.isNullOrEmpty(ids)){
				String idArr[] = ids.split(",");
				memberService.updateStatus(idArr,status);
				map.put(STATUS, SUCCESS);
			}
			Admin a  = (Admin)session.getAttribute(BlueMobiConstant.ADMIN_USER);
			systemLogService.addSystemLog(a, "修改用户状态");
		} catch (Exception e) {
			e.printStackTrace();
			map.put(STATUS, ERROR);
		}finally{
			outJSON(map);
		 }
	}
	
}
