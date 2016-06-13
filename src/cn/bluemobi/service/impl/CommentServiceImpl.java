package cn.bluemobi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.dao.BaseDao;
import cn.bluemobi.entity.Comment;
import cn.bluemobi.entity.system.Page;
import cn.bluemobi.service.CommentService;
import cn.bluemobi.util.helper.ValidateHelper;
import cn.bluemobi.util.text.TextHelper;
/**
 * 评论
 * @author xiazf
 *
 */
@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private BaseDao dao;
	/**
	 * 获取评论
	 * @param beCommentId
	 * @param type
	 * @param page
	 * @return
	 */
	public List<Comment> getCommentList(String beCommentId,String secondId,String type, String memberId,Page page,String order){
		String sql = "";
		String orderBy = "";
		if(!TextHelper.isNullOrEmpty(secondId)){
			sql += " AND c.second_id = "+secondId;
		}
		//0是前5个热门的  1是按时间排序显示
		if("1".equals(order)){
			orderBy = " ORDER BY createTime DESC ";
		}else{
			orderBy = " ORDER BY orderNo ASC, createTime DESC ";
		}
		
		if (page.getPageCount() == -1) {
			dao.findPageSum("SELECT COUNT(*) FROM `comment` AS c LEFT JOIN  member AS m ON m.id = c.member_id WHERE c.be_comment_id = ? AND c.type = ? "+sql,page,beCommentId,type);
			if (page.getTotalCount() == 0) {
				return null;
			}
		}
		return dao.findByPage(" SELECT t.id, t.releaseId, t.createTime, t.praiseNum, t.beCommentId, t.type, t.memberId, t.`comment`,t.`isAnonymous`,t.`anonymousName`,t.`anonymousIcon`,t.`beReplyId`, t.`name`, t.icon,t.beReplyName,t.beReplyMemberId, t.beReplyIsAnonymous,t.isPraise,t.commentNum,"
				+ " IF ((@rowNum := @rowNum + 1) < 6 ,@rowNum, 6 ) AS orderNo "
				+ " FROM ( SELECT c.id, c.release_id AS releaseId, DATE_FORMAT( c.create_time, '%Y-%m-%d %H:%i:%s' ) AS createTime, c.be_comment_id AS beCommentId,"
				+ " c.type, c.member_id AS memberId, c.`comment`,c.`isAnonymous`,c.`anonymous_name` AS anonymousName ,c.`anonymous_icon` AS anonymousIcon,c.`be_reply_id` AS beReplyId, m.`name`, m.icon, "
				+ " CASE co.`isAnonymous` WHEN 1 THEN co.`anonymous_name` WHEN 0 THEN m1.`name` END AS beReplyName, "
				+ " co.member_id AS beReplyMemberId, co.`isAnonymous` AS beReplyIsAnonymous,"
				+ " ( SELECT COUNT(*) FROM praise AS p WHERE p.type = '5' AND p.be_praise_id = c.id ) AS praiseNum, "
				+ " ( SELECT COUNT(*) FROM `comment` AS n WHERE n.be_comment_id = c.id AND c.type = 5 ) AS commentNum, "
				+ " ( SELECT COUNT(*) FROM praise AS p WHERE p.type = '5' AND p.be_praise_id = c.id AND p.praise_id = ? ) AS isPraise "
				+ "  FROM `comment` AS c "
				+ " LEFT JOIN member AS m ON m.id = c.member_id "
				+ " LEFT JOIN `comment` AS co ON co.id = c.be_reply_id  "
				+ " LEFT JOIN member AS m1 ON m1.id = co.member_id  "
				+ " WHERE c.be_comment_id = ? AND c.type = ? "+sql+" ORDER BY praiseNum DESC, c.create_time DESC ) t, (SELECT(@rowNum := 0)) b "+orderBy ,page,Comment.class,memberId,beCommentId,type);
	}

	/**
	 * 新增评论
	 * @param comment
	 */
	public Long addComment(Comment comment){
		//如果没传匿名属性，默认的设置为非匿名
		if(TextHelper.isNullOrEmpty(comment.getIsAnonymous())){
			comment.setIsAnonymous("0");
		}
		if(TextHelper.isNullOrEmpty(comment.getBeReplyId())){
			comment.setBeReplyId(null);
		}
		return dao.saveGetKey("INSERT INTO `comment` SET release_id=:releaseId,create_time=NOW(),be_comment_id=:beCommentId,type=:type,member_id=:memberId,comment=:comment,second_id=:secondId,isAnonymous=:isAnonymous,anonymous_icon=:anonymousIcon,anonymous_name=:anonymousName,`be_reply_id`=:beReplyId ", comment);
	}
	
	/**
	 * 后台评论分页列表
	 * @param page
	 * @param comment
	 * @return
	 */
	public Map<String, Object> getCommentList(cn.bluemobi.entity.Page page,Comment comment){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		Object[] array = null;
		Object[] array1 = null;
		if (!ValidateHelper.isNullOrEmpty(comment.getTitle())) {
			sb.append(" AND (si.title LIKE ? AND c.type=1) OR (mf.title LIKE ? AND c.type=2) OR (sf.title LIKE ? AND c.type=3) OR (ss.title LIKE ? AND c.type=4) ");
			list.add('%' + comment.getTitle() + '%');
			list.add('%' + comment.getTitle() + '%');
			list.add('%' + comment.getTitle() + '%');
			list.add('%' + comment.getTitle() + '%');
		}
		if (!ValidateHelper.isNullOrEmpty(comment.getComment())) {
			sb.append(" and c.comment like ?  ");
			list.add('%' + comment.getComment() + '%');
		}
		if (!ValidateHelper.isNullOrEmpty(comment.getMemberId())) {
			sb.append(" and c.member_id = ?  ");
			list.add(comment.getMemberId());
		}
		// 发布日期
		if (!ValidateHelper.isNullOrEmpty(comment.getStartTime())) {
			sb.append(" and DATE_FORMAT(c.create_time, '%Y-%m-%d')>=  DATE_FORMAT( ?, '%Y-%m-%d')");
			list.add(comment.getStartTime());
		}
		if (!ValidateHelper.isNullOrEmpty(comment.getEndTime())) {
			sb.append(" and DATE_FORMAT(c.create_time, '%Y-%m-%d')<=  DATE_FORMAT( ?, '%Y-%m-%d')");
			list.add(comment.getEndTime());
		}
		
		String sql = sb.toString();
		array1 = list.toArray();
		
		sb.append(" ORDER BY c.create_time DESC  LIMIT ?, ? ");
		
		Integer count = dao.findForInt(" SELECT COUNT(*) FROM `comment` AS c  "
				+ " LEFT JOIN `member` AS m ON c.member_id = m.id "
				+ " LEFT JOIN `admin` AS a ON c.release_id = a.id "
				+ " LEFT JOIN `member` AS e ON c.release_id = e.id "
				+ " LEFT JOIN `story_island` AS si ON si.id = c.be_comment_id "
				+ " LEFT JOIN `micro_film` AS mf ON mf.id = c.be_comment_id "
				+ " LEFT JOIN `script_factory` AS sf ON sf.id = c.be_comment_id "
				+ " LEFT JOIN `star_story` AS ss ON ss.id = c.be_comment_id "
				+ " WHERE 1=1 " + sql,array1);
		page.setTotalResult(count);
		list.add(page.getCurrentResult());
		list.add(page.getShowCount());
		array = list.toArray();
		List<Comment> commentList = dao
				.findForList(
						" SELECT c.id,c.release_id AS releaseId,DATE_FORMAT(c.create_time, '%Y-%m-%d %H:%i:%s') AS createTime,c.type,c.member_id AS memberId,c.comment, m.`name`, "
						+ " CASE c.type WHEN 1 THEN si.title  "
						+ " WHEN 2 THEN mf.title "
						+ " WHEN 3 THEN sf.title "
						+ " WHEN 4 THEN ss.title "
						+ " END AS title, "
						+ " CASE c.type WHEN 1 THEN e.`name`  "
						+ " WHEN 2 THEN a.`name` "
						+ " WHEN 3 THEN IF(sf.author_type=0,e.`name`,a.`name`) "
						+ " WHEN 4 THEN a.`name` "
						+ " END AS `author` "
						+ " FROM `comment` AS c  "
						+ " LEFT JOIN `member` AS m ON c.member_id = m.id "
						+ " LEFT JOIN `admin` AS a ON c.release_id = a.id "
						+ " LEFT JOIN `member` AS e ON c.release_id = e.id "
						+ " LEFT JOIN `story_island` AS si ON si.id = c.be_comment_id "
						+ " LEFT JOIN `micro_film` AS mf ON mf.id = c.be_comment_id "
						+ " LEFT JOIN `script_factory` AS sf ON sf.id = c.be_comment_id "
						+ " LEFT JOIN `star_story` AS ss ON ss.id = c.be_comment_id "
						+ " WHERE 1=1  " + sb.toString(),
						Comment.class, array);
		map.put("page", page);
		map.put("commentList", commentList);
		return map;
	}
	
	
	/**
	 * 查找我的评论
	 * @param memberId
	 */
	public List<Comment> getMyComment(String memberId,Page page){
		
		if (page.getPageCount() == -1) {
			dao.findPageSum("SELECT COUNT(*) FROM `comment` AS c"
					+ " INNER JOIN member AS m ON m.id = c.member_id "
					+ " WHERE"
					+ " c.type = 1 AND (c.be_comment_id IN (SELECT id FROM `story_island` AS s WHERE s.release_id = ?) OR c.member_id = ?)"
					+ " OR"
					+ " c.type = 2 AND (c.be_comment_id IN (SELECT id FROM `micro_film` AS m WHERE m.release_id = ? AND m.author_type=0) OR c.member_id = ?) "
					+ " OR "
					+ " c.type = 3 AND (c.be_comment_id IN (SELECT id FROM `script_factory` AS f WHERE f.release_id = ? AND f.author_type=0 ) OR c.member_id = ?) "
					+ " OR "
					+ " c.type = 4 AND (c.be_comment_id IN (SELECT id FROM `star_story` AS t WHERE t.release_id = ?) OR c.member_id = ?) "
					+ " OR "
					+ " c.type = 5 AND (c.be_comment_id IN (SELECT id FROM `comment` AS n WHERE n.member_id = ? )  OR c.member_id = ?)",page,memberId,memberId,memberId,memberId,memberId,memberId,memberId,memberId,memberId,memberId);
			if (page.getTotalCount() == 0) {
				return null;
			}
		}
		return dao.findByPage(" SELECT c.id, c.release_id AS releaseId, DATE_FORMAT( c.create_time, '%Y-%m-%d %H:%i:%s' ) AS createTime, c.be_comment_id AS beCommentId,"
				+ "  c.type, c.member_id AS memberId, c.`comment`,c.`isAnonymous`,c.`anonymous_name` AS anonymousName ,c.`anonymous_icon` AS anonymousIcon, m.`name`, m.`icon`, "
				+ " CASE co.`isAnonymous` WHEN 1 THEN co.`anonymous_name` WHEN 0 THEN m1.`name` END AS beReplyName, "
				+ " co.member_id AS beReplyMemberId, co.`isAnonymous` AS beReplyIsAnonymous,"
				+ " ( SELECT COUNT(*) FROM praise AS p WHERE p.type = '5' AND p.be_praise_id = c.id ) AS praiseNum, "
				+ " ( SELECT COUNT(*) FROM `comment` AS n WHERE n.be_comment_id = c.id AND c.type = 5 ) AS commentNum, "
				+ " ( SELECT COUNT(*) FROM praise AS p WHERE p.type = '5' AND p.be_praise_id = c.id AND p.praise_id = ? ) AS isPraise"
				+ "  FROM `comment` AS c "
				+ " INNER JOIN member AS m ON m.id = c.member_id "
				+ " LEFT JOIN `comment` AS co ON co.id = c.be_reply_id  "
				+ " LEFT JOIN member AS m1 ON m1.id = co.member_id  "
				+ " WHERE "
				+ " c.type = 1 AND (c.be_comment_id IN (SELECT id FROM `story_island` AS a WHERE a.release_id = ?) OR c.member_id = ? OR ( SELECT id FROM `comment` AS a1 WHERE a1.id = c.be_reply_id AND a1.member_id=? )) "
				+ " OR"
				+ " c.type = 2 AND (c.be_comment_id IN (SELECT id FROM `micro_film` AS b WHERE b.release_id = ? AND b.author_type=0) OR c.member_id = ? OR ( SELECT id FROM `comment` AS a2 WHERE a2.id = c.be_reply_id AND a2.member_id=? ))  "
				+ " OR "
				+ " c.type = 3 AND (c.be_comment_id IN (SELECT id FROM `script_factory` AS d WHERE d.release_id = ? AND d.author_type=0 ) OR c.member_id = ? OR ( SELECT id FROM `comment` AS a3 WHERE a3.id = c.be_reply_id AND a3.member_id=? ))  "
				+ " OR "
				+ " c.type = 4 AND (c.be_comment_id IN (SELECT id FROM `star_story` AS e WHERE e.release_id = ?) OR c.member_id = ? OR ( SELECT id FROM `comment` AS a4 WHERE a4.id = c.be_reply_id AND a4.member_id=? ))  "
				+ " OR "
				+ " c.type = 5 AND (c.be_comment_id IN (SELECT id FROM `comment` AS f WHERE f.member_id = ? ) OR c.member_id = ? OR ( SELECT id FROM `comment` AS a5 WHERE a5.id = c.be_reply_id AND a5.member_id=? ))  "
				+ " ORDER BY  c.create_time DESC ",page,Comment.class,memberId,memberId,memberId,memberId,memberId,memberId,memberId,memberId,memberId,memberId,memberId,memberId,memberId,memberId,memberId,memberId);
	}
	
	
	/**
	 * 查找单个评论
	 * @param id
	 * @return
	 */
	public Comment getCommentById(Long id){
		
		return dao.findForObject(" SELECT t.id, t.releaseId, t.createTime, t.praiseNum, t.beCommentId, t.type, t.memberId, t.`comment`,t.`isAnonymous`,t.`anonymousName`,t.`anonymousIcon`,t.`beReplyId`, t.`name`, t.icon,t.beReplyName,t.beReplyMemberId, t.beReplyIsAnonymous,t.commentNum,"
				+ " IF ((@rowNum := @rowNum + 1) < 6 ,@rowNum, 6 ) AS orderNo "
				+ " FROM ( SELECT c.id, c.release_id AS releaseId, DATE_FORMAT( c.create_time, '%Y-%m-%d %H:%i:%s' ) AS createTime, c.be_comment_id AS beCommentId,"
				+ " c.type, c.member_id AS memberId, c.`comment`,c.`isAnonymous`,c.`anonymous_name` AS anonymousName ,c.`anonymous_icon` AS anonymousIcon,c.`be_reply_id` AS beReplyId, m.`name`, m.icon, "
				+ " CASE co.`isAnonymous` WHEN 1 THEN co.`anonymous_name` WHEN 0 THEN m1.`name` END AS beReplyName, "
				+ " co.member_id AS beReplyMemberId, co.`isAnonymous` AS beReplyIsAnonymous,"
				+ " ( SELECT COUNT(*) FROM praise AS p WHERE p.type = '5' AND p.be_praise_id = c.id ) AS praiseNum, "
				+ " ( SELECT COUNT(*) FROM `comment` AS n WHERE n.be_comment_id = c.id AND c.type = 5 ) AS commentNum "
				+ "  FROM `comment` AS c "
				+ " LEFT JOIN member AS m ON m.id = c.member_id "
				+ " LEFT JOIN `comment` AS co ON co.id = c.be_reply_id  "
				+ " LEFT JOIN member AS m1 ON m1.id = co.member_id  "
				+ " WHERE c.id = ? ORDER BY praiseNum DESC, c.create_time DESC ) t, (SELECT(@rowNum := 0)) b  ",Comment.class, id);
	}
	
	/**
	 * 查找我的评论数量
	 * @param memberId
	 * @param commentTime
	 * @return
	 */
	public Integer getMyCommentByTime(String memberId, String commentTime){
		return dao.findForInt(" SELECT COUNT(*) FROM `comment` AS c"
					+ " INNER JOIN member AS m ON m.id = c.member_id "
					+ " WHERE"
					+ " DATE_FORMAT(c.create_time,'%Y-%m-%d %H:%i:%s') > DATE_FORMAT('"+commentTime+"','%Y-%m-%d %H:%i:%s') " 
					+ " AND (c.type = 1 AND (c.be_comment_id IN (SELECT id FROM `story_island` AS s WHERE s.release_id = ?) OR c.member_id = ?)"
					+ " OR "
					+ " c.type = 2 AND (c.be_comment_id IN (SELECT id FROM `micro_film` AS m WHERE m.release_id = ? AND m.author_type=0) OR c.member_id = ?) "
					+ " OR "
					+ " c.type = 3 AND (c.be_comment_id IN (SELECT id FROM `script_factory` AS f WHERE f.release_id = ? AND f.author_type=0 ) OR c.member_id = ?) "
					+ " OR "
					+ " c.type = 4 AND (c.be_comment_id IN (SELECT id FROM `star_story` AS t WHERE t.release_id = ?) OR c.member_id = ?) "
					+ " OR "
					+ " c.type = 5 AND (c.be_comment_id IN (SELECT id FROM `comment` AS n WHERE n.member_id = ? )  OR c.member_id = ?) )", memberId,memberId,memberId,memberId,memberId,memberId,memberId,memberId,memberId,memberId);
	}
}
