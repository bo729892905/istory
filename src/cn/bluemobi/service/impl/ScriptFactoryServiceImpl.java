package cn.bluemobi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.dao.BaseDao;
import cn.bluemobi.entity.ScriptChapter;
import cn.bluemobi.entity.ScriptFactory;
import cn.bluemobi.entity.ScriptRole;
import cn.bluemobi.entity.system.Page;
import cn.bluemobi.service.ScriptFactoryService;
import cn.bluemobi.util.helper.ValidateHelper;
import cn.bluemobi.util.text.TextHelper;
/**
 * 剧本工厂
 * @author xiazf
 *
 */
@Service
public class ScriptFactoryServiceImpl implements ScriptFactoryService {
	@Autowired
	private BaseDao dao;
	/**
	 * 剧本工厂
	 * @param memberId
	 * @return
	 */
	public List<ScriptFactory> getScriptFactoryList(String memberId){
		return dao.findForList("SELECT s.id,s.title FROM `script_factory` AS s WHERE s.`release_id` = ? AND s.`status` = 1 AND s.`author_type` = 0 AND EXISTS(SELECT * FROM `script_chapter` AS c  WHERE s.id = c.script_id and c.`status` = 1 ) ", ScriptFactory.class, memberId);
	}
	
	/**
	 * 分页查询剧本工厂
	 * @param memberId
	 * @param page
	 * @return
	 */
	public List<ScriptFactory> getScriptFactoryListPage(String type,String memberId,Page page){
		String sql = "";
		String sql1 = "";
		String sql2 = "";
		if(!TextHelper.isNullOrEmpty(memberId) && TextHelper.isNullOrEmpty(type)){
			sql += " AND s.release_id  =  "+memberId+" AND s.`author_type` = 0 ";
			sql1 = " ,( SELECT COUNT(*) FROM praise AS p WHERE p.type = '3' AND p.be_praise_id = s.id AND p.praise_id = "+memberId+" ) AS isPraise ";
		}else if(!TextHelper.isNullOrEmpty(type)){
			if(!TextHelper.isNullOrEmpty(memberId)){
				sql1 = " ,( SELECT COUNT(*) FROM praise AS p WHERE p.type = '3' AND p.be_praise_id = s.id AND p.praise_id = "+memberId+" ) AS isPraise ";
			}
			sql = " and s.status='1' AND EXISTS(SELECT * FROM `script_chapter` AS c  WHERE s.id = c.script_id AND c.`status`=1 )  ";
			sql2 = " AND c.`status` = 1 ";
		}
	    sql += " ORDER BY hot DESC ";
		if (page.getPageCount() == -1) {
			dao.findPageSum("SELECT COUNT(*) FROM script_factory AS s where 1=1    "+sql, page);
			if (page.getTotalCount() == 0) {
				return null;
			}
		}
		return dao.findByPage(" SELECT s.id,s.title,s.release_id AS releaseId,"
				+ " IF( mf.`status`=1,r.relate_film_id,NULL) AS relateFilmId,IF( si.`status`=1,r.relate_story_id,NULL) AS relateStoryId,"
				+ " s.content,s.cover,s.role,s.author_type AS authorType, s.hot,DATE_FORMAT(s.release_time,'%Y-%m-%d %H:%i:%s') AS releaseTime,(SELECT COUNT(*) FROM `script_chapter` AS c WHERE c.script_id = s.id "+sql2+" ) AS chapter ,CASE WHEN s.author_type =0 THEN m.`name` WHEN s.author_type =1 THEN a.`name` END AS author,(SELECT COUNT(*) FROM `comment` AS b WHERE b.type =3 AND b.be_comment_id = s.id ) AS commentNum "
				+ sql1
				+ " FROM `script_factory` AS s "
				+ " LEFT JOIN `member` AS m ON m.id = s.release_id "
				+ " LEFT JOIN `admin` AS a ON a.id = s.release_id "
				+ " LEFT JOIN `relate`AS r ON r.relate_script_id = s.id "
				+ " LEFT JOIN `story_island` AS si ON si.id = r.relate_story_id "
				+ " LEFT JOIN `micro_film` AS mf ON mf.id = r.relate_film_id "
				+ " WHERE 1=1  "+sql,page,ScriptFactory.class);
	}
	/**
	 * 获取章节list
	 * @param scriptId
	 * @return
	 */
	public List<ScriptChapter> getAllChapter(String scriptId,String memberId){
		String sql = "";
		if(!TextHelper.isNullOrEmpty(memberId)){
			sql = " AND s.`status` = 1 "; 
		}
		return dao.findForList("SELECT s.id,s.script_id AS scriptId,s.text,DATE_FORMAT(s.create_time,'%Y-%m-%d %H:%i:%s')  AS createTime,s.`status` FROM `script_chapter` AS s WHERE s.script_id= ? "+sql+" ORDER BY create_time ASC ", ScriptChapter.class, scriptId);
	}
	/**
	 * 剧本详情
	 * @param scriptId
	 * @return
	 */
	public ScriptFactory getScriptFactory(String scriptId,String memberId){
		String sql = " ";
		if(!TextHelper.isNullOrEmpty(memberId)){
			sql +=",( SELECT COUNT(*) FROM praise AS p WHERE p.type = '3' AND p.be_praise_id = s.id AND p.praise_id = "+memberId+" ) AS isPraise  ";
		}
		return dao.findForObject(" SELECT s.id,s.title,s.release_id AS releaseId,s.content,s.`status`,s.cover,s.role,s.hot,DATE_FORMAT(s.release_time,'%Y-%m-%d %H:%i:%s') AS releaseTime,s.author_type AS authorType, CASE WHEN s.author_type =0 THEN m.`name` WHEN s.author_type =1 THEN a.`name` END AS author,m.icon,r.relate_film_id AS relateFilmId,r.relate_story_id AS relateStoryId "
				+ " ,(SELECT COUNT(*) FROM `comment` AS b WHERE b.type = 3 AND b.be_comment_id = s.id ) AS commentNum "
				+ sql
				+ " FROM `script_factory` AS s"
				+ " LEFT JOIN `member` AS m ON m.id = s.release_id "
				+ " LEFT JOIN `admin` AS a ON a.id = s.release_id "
				+ " LEFT JOIN `relate`AS r ON r.relate_script_id = s.id "
				+ " WHERE  s.id=  ? ", ScriptFactory.class, scriptId);
	}
	/**
	 * 增加角色
	 * @param sr
	 */
	public Long addScriptRole(ScriptRole sr){
		return dao.saveGetKey(" INSERT INTO script_role SET `name`=:name,sex=:sex,age=:age,role=:role,property=:property,description=:description,script_id=:scriptId,member_id=:memberId ", sr);
	}
	/**
	 * 增加剧本
	 * @param sf
	 * @return
	 */
	public Long addScript(ScriptFactory sf){
		return dao.saveGetKey("INSERT INTO script_factory SET title=:title,content=:content,cover=:cover,`release_id`=:releaseId,`release_time` = NOW(),`status`=:status", sf);
	}
	/**
	 * 将角色与剧本关联
	 * @param scriptId
	 */
	public void updateRoleScriptId(String scriptId,String id){
		 dao.executeByParams("UPDATE script_role SET script_id = ? WHERE id = ? ", scriptId,id);
	}
	/**
	 * 编辑剧本
	 * @param sf
	 */
	public void updateScript(ScriptFactory sf){
		dao.executeByObject(" UPDATE `script_factory` SET `title`=:title,`release_id`=:releaseId,`content`=:content,`cover`=:cover,`status`=:status WHERE id=:id ", sf);
	}
	
	/**
	 * 发布场景
	 * @param status
	 */
	public void updateScriptStatus(String scriptId, String status){
		dao.executeByParams(" UPDATE `script_factory` SET `status` = ?,`release_time` = NOW() WHERE id = ? ",status, scriptId);
	}
	
	/**
	 * 新增故事章节
	 * @param scriptId
	 * @param text
	 */
	public Long addScriptChapter(String scriptId, String text,String status){
		ScriptChapter sc = new ScriptChapter();
		sc.setScriptId(Long.valueOf(scriptId));
		sc.setText(text);
		sc.setStatus(status);
		
		return dao.saveGetKey(" INSERT INTO script_chapter SET `script_id`=:scriptId,`text`=:text,`create_time`=NOW(),status=:status ",sc);
	}
	/**
	 * 获取角色列表
	 * @param scriptId
	 */
	public List<ScriptRole> getScriptRoleList(String scriptId){
		return dao.findForList("SELECT  s.id,s.`name`,s.sex,s.age,s.role,s.property,s.description FROM script_role AS s WHERE script_id = ? ", ScriptRole.class, scriptId); 
	}
	/**
	 * 删除章节
	 * @param chapterId
	 */
	public void deleteScriptChapter(String chapterId){
		dao.executeByParams(" DELETE FROM `script_chapter` WHERE id= ? ", chapterId);
	}
	/**
	 * 增加剧本热度
	 * @param bePraiseId
	 */
	public void addScriptHot(Long bePraiseId){
		dao.executeByParams(" UPDATE `script_factory` SET hot := hot+1 WHERE id = ?",bePraiseId);
	}
	/**
	 * 减少剧本热度
	 * @param bePraiseId
	 */
	public void deleteScriptHot(Long bePraiseId){
		dao.executeByParams(" UPDATE `script_factory` SET hot := IF(hot-1<0,0,hot-1) WHERE id = ? ", bePraiseId);
	}
	/**
	 * 获取后台剧本分页列表
	 * @param page
	 * @param storyIsland
	 * @return
	 */
	public Map<String, Object> getScriptFactoryListByPage(cn.bluemobi.entity.Page page, ScriptFactory scriptFactory){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		Object[] array = null;
		Object[] array1 = null;
		if (!ValidateHelper.isNullOrEmpty(scriptFactory.getTitle())) {
			sb.append(" and title like ?");
			list.add('%' + scriptFactory.getTitle() + '%');
		}
		if (!ValidateHelper.isNullOrEmpty(scriptFactory.getAuthor())) {
			sb.append(" and( (m.name like ? and author_type = 0) OR (a.name like ? and author_type = 1 ) ) ");
			list.add('%' + scriptFactory.getAuthor() + '%');
			list.add('%' + scriptFactory.getAuthor() + '%');
			
		}
		// 发布日期
		if (!ValidateHelper.isNullOrEmpty(scriptFactory.getReleaseTime())) {
			sb.append(" and DATE_FORMAT(s.release_time, '%Y-%m-%d') =  DATE_FORMAT( ?, '%Y-%m-%d')");
			list.add(scriptFactory.getReleaseTime());
		}
		String sql = sb.toString();
		array1 = list.toArray();
		//按热度排序，再次是发布时间
		sb.append(" ORDER BY hot DESC,s.release_time DESC  LIMIT ?, ? ");
		
		Integer count = dao.findForInt(" SELECT COUNT(*) FROM `script_factory` s LEFT JOIN member m  ON m.id= s.release_id AND s.author_type=0 LEFT JOIN `admin` a ON a.id = s.release_id AND s.author_type = 1  WHERE 1 = 1" + sql,array1);
		page.setTotalResult(count);
		list.add(page.getCurrentResult());
		list.add(page.getShowCount());
		array = list.toArray();
		List<ScriptFactory> scriptFactoryList = dao
				.findForList(
						" SELECT s.id,s.title,s.release_id AS releaseId,s.content,s.cover,s.role,s.hot,DATE_FORMAT(s.release_time, '%Y-%m-%d %H:%i:%s') AS releaseTime,s.`status`,s.author_type AS authorType, IF(author_type='0',m.`name`,a.`name`) AS `author`"
						+ ",(SELECT COUNT(*) FROM `script_chapter` AS c WHERE c.script_id = s.id) AS chapter"
						+ "  FROM `script_factory` s "
						+ " LEFT JOIN member m  ON m.id= s.release_id AND s.author_type=0 "
						+ " LEFT JOIN `admin` a ON a.id = s.release_id AND s.author_type = 1"
						+ "  WHERE 1 = 1  " + sb.toString(),
						ScriptFactory.class, array);
		map.put("page", page);
		map.put("scriptFactoryList", scriptFactoryList);
		return map;
	}
	
	/**
	 * 修改剧本状态
	 * @param idArr
	 * @param status
	 */
	public void updateStatus(String[] idArr, String status){
		String[] sql = new String[100];
		for (int i = 0; i < idArr.length; i++) {
			sql[i] = " UPDATE `script_factory` SET `status` = "+status+" WHERE id = "+idArr[i];
		}
		dao.executeBatch(sql);
	}
	
	/**
	 * 获取脚本详情
	 * @param scriptChapterId
	 * @return
	 */
	public ScriptChapter getScriptChapterById(String scriptChapterId){
		return dao.findForObject(" SELECT s.id,s.script_id AS scriptId,s.text,DATE_FORMAT(s.create_time,'%Y-%m-%d %H:%i:%s')  AS createTime,s.`status` FROM `script_chapter` AS s WHERE id = ?  ", ScriptChapter.class, scriptChapterId);
	}
	/**
	 * 删除剧本
	 * @param string
	 */
	public void deleteScriptFactory(String id){
		dao.executeByParams("DELETE FROM `script_factory` WHERE id=? ", id);
		//删除章节
		dao.executeByParams(" DELETE FROM `script_chapter` WHERE script_id = ? ", id);
		//关联表里 故事的id清空
		dao.executeByParams("UPDATE relate SET relate_script_id=null WHERE relate_script_id=?  ", id);
		//删除评论
		dao.executeByParams(" DELETE FROM `comment` WHERE be_comment_id = ? AND type = 3 ", id);
	}
	
	/**
	 * 剧本工厂修改
	 * @param sf
	 */
	public void save(ScriptFactory sf){
		if(!TextHelper.isNullOrEmpty(sf.getId())){
			//编辑
			dao.executeByObject(" UPDATE `script_factory` SET `title`=:title,content=:content,cover=:cover,hot=:hot WHERE id=:id ", sf);
		}else{
			dao.executeByObject(" INSERT INTO `script_factory` SET `title`=:title,release_id=:releaseId,content=:content,cover=:cover,hot=:hot,release_time=NOW(),status=0,author_type=:authorType ", sf);
		}
	}
	/**
	 * 剧本场次列表
	 * @param page
	 * @param id
	 * @return
	 */
	public Map<String, Object> getChapterListByPage(cn.bluemobi.entity.Page page, String id){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> list = new ArrayList<Object>();
		Object[] array = null;
		Integer count = dao.findForInt(" SElECT COUNT(*) FROM `script_chapter` s  WHERE script_id = ? ",id);
		page.setTotalResult(count);
		list.add(id);
		list.add(page.getCurrentResult());
		list.add(page.getShowCount());
		array = list.toArray();
		List<ScriptChapter> chapterList = dao
				.findForList(
						" SELECT s.id,s.script_id AS scriptId,s.text,DATE_FORMAT(s.create_time,'%Y-%m-%d %H:%i:%s') AS createTime,s.`status` FROM `script_chapter` AS s WHERE script_id = ? ORDER BY s.create_time desc LIMIT ?, ?   ",
						ScriptChapter.class,array);
		map.put("page", page);
		if(chapterList !=null &&chapterList.size()>0){
			for (int i = 0; i < chapterList.size(); i++) {
				String text = TextHelper.replaceHTML(chapterList.get(i).getText());
				chapterList.get(i).setText(text.length()>30?text.substring(0, 30)+"...":text);
			}
		}
		map.put("chapterList", chapterList);
		return map;
	}
	/**
	 * 获取场次详情
	 * @param id
	 * @return
	 */
	public ScriptChapter getChapterById(String id){
		return dao.findForObject(" SELECT s.id,s.script_id AS scriptId,s.text,DATE_FORMAT(s.create_time,'%Y-%m-%d %H:%i:%s') AS createTime,s.`status` FROM `script_chapter` AS s WHERE id = ? ", ScriptChapter.class, id);
	}
	/**
	 * 修改场次详情
	 * @param id
	 * @param text
	 */
	public void updateScriptChapter(String id, String text,String scriptId){
		if(!TextHelper.isNullOrEmpty(id)){
			dao.executeByParams(" UPDATE `script_chapter` SET text=?,create_time=NOW() WHERE id = ?   ", text,id);
		}else{
			dao.executeByParams(" INSERT INTO `script_chapter` SET text=?,create_time=NOW(),script_id=?  ", text,scriptId);
		}
	}
	/**
	 * 修改章节状态
	 * @param chapterId
	 * @param status
	 */
	public void updateChapterStatus(String chapterId, String status){
		dao.executeByParams(" UPDATE `script_chapter` SET `status` = ? WHERE id = ? ", status,chapterId);
	}
	/**
	 * 搜索查找剧本
	 * @param keywords
	 * @param page
	 * @return
	 */
	public List<ScriptFactory> getScriptFactoryBySearch(String keywords,Page page){
		String sql = "";
		List<String> list = new ArrayList<>();
		if(!TextHelper.isNullOrEmpty(keywords)){
			sql += " AND s.title like ? ";
			list.add("%"+keywords+"%");
		}
		if (page.getPageCount() == -1) {
			dao.findPageSum(" SELECT COUNT(*) FROM script_factory AS s where s.`status` = 1  AND EXISTS(SELECT * FROM `script_chapter` AS c  WHERE s.id = c.script_id )  "+sql, page,list.toArray());
			if (page.getTotalCount() == 0) {
				return null;
			}
		}
		sql += " ORDER BY hot DESC ";
		return dao.findByPage("  SELECT s.id,s.title, "
				+ " IF( mf.`status`=1,r.relate_film_id,NULL) AS relateFilmId,IF( si.`status`=1,r.relate_story_id,NULL) AS relateStoryId,"
				+ " s.cover,s.author_type AS authorType, s.hot,DATE_FORMAT(s.release_time,'%Y-%m-%d %H:%i:%s') AS releaseTime,(SELECT COUNT(*) FROM `script_chapter` AS c WHERE c.script_id = s.id AND c.`status` = 1 ) AS chapter ,(SELECT COUNT(*) FROM `comment` AS b WHERE b.type =3 AND b.be_comment_id = s.id ) AS commentNum "
				+ " FROM `script_factory` AS s "
				+ " LEFT JOIN `relate`AS r ON r.relate_script_id = s.id "
				+ " LEFT JOIN `story_island` AS si ON si.id = r.relate_story_id "
				+ " LEFT JOIN `micro_film` AS mf ON mf.id = r.relate_film_id "
				+ " WHERE s.`status`= 1  AND EXISTS(SELECT * FROM `script_chapter` AS c  WHERE s.id = c.script_id )   "+sql, page,ScriptFactory.class, list.toArray());
	
	}
	
	public ScriptFactory getScriptFactoryById(String id){
		return dao.findForObject("SELECT s.id,s.title,s.release_id AS releaseId,s.content,s.`status`,s.cover,s.role,s.hot,DATE_FORMAT(s.release_time,'%Y-%m-%d %H:%i:%s') AS releaseTime,s.author_type AS authorType "
				+ " FROM `script_factory` AS s"
				+ " WHERE  s.id=  ?", ScriptFactory.class, id);
	}
}
