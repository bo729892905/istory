package cn.bluemobi.controller.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bluemobi.controller.BaseController;
import cn.bluemobi.entity.Comment;
import cn.bluemobi.entity.Page;
import cn.bluemobi.service.CommentService;
/**
 * 评论管理
 * @author xiazf
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("admin/comment/")
public class CommentController extends BaseController {
	@Autowired
	private CommentService commentService;
	
	/**
	 * 进入评论列表
	 * @return
	 */
	@RequestMapping("list")
	public String list(Page page,Comment comment) {
		Map<String,Object> map = commentService.getCommentList(page,comment);
		request.setAttribute("commentList", map.get("commentList"));
		request.setAttribute("page", map.get("page"));
		return "admin/comment/list.jsp";
	}
	
	
}
