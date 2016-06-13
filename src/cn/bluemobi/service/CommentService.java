package cn.bluemobi.service;

import java.util.List;
import java.util.Map;

import cn.bluemobi.entity.Comment;
import cn.bluemobi.entity.system.Page;

/**
 * 评论
 * @author xiazf
 *
 */
public interface CommentService {
	/**
	 * 获取评论
	 * @param beCommentId
	 * @param type
	 * @param memberId 
	 * @param page
	 * @param order 
	 * @return
	 */
	public List<Comment> getCommentList(String beCommentId, String secondId,String type, String memberId, Page page, String order);
	/**
	 * 新增评论
	 * @param comment
	 */
	public Long addComment(Comment comment);
	/**
	 * 后台评论分页列表
	 * @param page
	 * @param comment
	 * @return
	 */
	public Map<String, Object> getCommentList(cn.bluemobi.entity.Page page,Comment comment);
	/**
	 * 查找我的评论
	 * @param memberId
	 * @param page 
	 * @return 
	 */
	public List<Comment> getMyComment(String memberId, Page page);
	/**
	 * 查找单个评论
	 * @param id
	 * @return
	 */
	public Comment getCommentById(Long id);
	/**
	 * 查找我的评论数量
	 * @param memberId
	 * @param commentTime
	 * @return
	 */
	public Integer getMyCommentByTime(String memberId, String commentTime);
}
