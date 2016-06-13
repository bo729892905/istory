package cn.bluemobi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.dao.BaseDao;
import cn.bluemobi.entity.Page;
import cn.bluemobi.entity.StarStory;
import cn.bluemobi.service.StarStoryService;
import cn.bluemobi.util.helper.ValidateHelper;

/**
 * 星故事
 * @author xiazf
 *
 */
@Service
public class StarStoryServiceImpl implements StarStoryService {
	@Autowired
	private BaseDao dao;
	/**
	 * 星故事列表
	 * @param memberId
	 * @return
	 */
	public List<StarStory> getStarStoryList(String memberId){
		return dao.findForList("SELECT s.id, s.title, s.release_id, s.number, s.view_count, DATE_FORMAT( s.release_time, '%Y-%m-%d %H:%i:%s' ) AS releaseTime,  s.hot, ( SELECT COUNT(*) FROM praise AS p WHERE p.praise_id = ? AND p.be_praise_id = s.id AND p.type = 4 ) AS isPraise ,(SELECT COUNT(*) FROM `comment` AS b WHERE b.type = 4 AND b.be_comment_id = s.id ) AS commentNum,s.`status`, s.poster_url AS posterUrl, s.type, s.content,s.cover, s.vedio_url AS vedioUrl FROM `star_story` AS s WHERE s.`status` = 1 ORDER BY s.number DESC",StarStory.class,memberId );
	}
	/**
	 * 增加浏览量
	 */
	public void addViewCount(String id){
		dao.executeByParams(" UPDATE `star_story` SET view_count := view_count+1 WHERE id = ? ", id);
	}
	/**
	 * 增加热度
	 * @param bePraiseId
	 */
	public void addStarHot(Long bePraiseId){
		dao.executeByParams(" UPDATE `star_story` SET hot := hot+1 WHERE id = ? ", bePraiseId);
	}
	/**
	 * 减少热度
	 * @param bePraiseId
	 */
	public void deleteStarHot(Long bePraiseId){
		dao.executeByParams(" UPDATE `star_story` SET hot := IF(hot-1<0,0,hot-1) WHERE id = ? ", bePraiseId);
	}
	
	/**
	 * 星故事后台分页列表
	 * @param page
	 * @param scriptFactory
	 * @return
	 */
	public Map<String, Object> getStarStoryListByPage(Page page,StarStory starStory){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		Object[] array = null;
		Object[] array1 = null;
		if (!ValidateHelper.isNullOrEmpty(starStory.getTitle())) {
			sb.append(" and title like ?");
			list.add('%' + starStory.getTitle() + '%');
		}
		if (!ValidateHelper.isNullOrEmpty(starStory.getAuthor())) {
			sb.append(" and a.name like ? ");
			list.add('%' + starStory.getAuthor() + '%');
			
		}
		// 发布日期
		if (!ValidateHelper.isNullOrEmpty(starStory.getReleaseTime())) {
			sb.append(" and DATE_FORMAT(s.release_time, '%Y-%m-%d') =  DATE_FORMAT( ?, '%Y-%m-%d')");
			list.add(starStory.getReleaseTime());
		}
		String sql = sb.toString();
		array1 = list.toArray();
		//按期数排序
		sb.append(" ORDER BY number DESC  LIMIT ?, ? ");
		
		Integer count = dao.findForInt(" SELECT COUNT(*) FROM `star_story` s LEFT JOIN `admin` a ON a.id = s.release_id   WHERE 1 = 1" + sql,array1);
		page.setTotalResult(count);
		list.add(page.getCurrentResult());
		list.add(page.getShowCount());
		array = list.toArray();
		List<StarStory> starStoryList = dao
				.findForList(
						" SELECT s.id,s.title,s.release_id AS releaseId,s.number,s.view_count AS viewCount,s.hot,DATE_FORMAT(s.release_time, '%Y-%m-%d %H:%i:%s') AS releaseTime,s.`status`,a.`name` AS `author` FROM `star_story` s  LEFT JOIN `admin` a ON a.id = s.release_id  WHERE 1 = 1  " + sb.toString(),
						StarStory.class, array);
		map.put("page", page);
		map.put("starStoryList", starStoryList);
		return map;
	}
	
	/**
	 * 修改星故事状态
	 * @param idArr
	 * @param status
	 */
	public void updateStatus(String[] idArr, String status){
		String[] sql = new String[100];
		for (int i = 0; i < idArr.length; i++) {
			sql[i] = " UPDATE `star_story` SET `status` = "+status+" WHERE id = "+idArr[i];
		}
		dao.executeBatch(sql);
	}
	/**
	 * 删除星故事
	 * @param string
	 */
	public void deleteStarStory(String id){
		dao.executeByParams(" DELETE FROM `star_story` WHERE id=? ", id);
		//删除评论
		dao.executeByParams(" DELETE FROM `comment` WHERE be_comment_id = ? AND type = 4 ", id);
	}
	/**
	 * 根据id查找星故事
	 * @param id
	 * @return
	 */
	public StarStory getStarStoryById(String id){
		return dao.findForObject(" SELECT s.id,s.title,s.release_id AS releaseId,s.number,s.view_count AS viewCount,DATE_FORMAT(s.release_time, '%Y-%m-%d %H:%i:%s') AS releaseTime,s.hot,s.`status`,s.poster_url AS posterUrl,s.type,s.cover,s.content,s.vedio_url AS vedioUrl FROM `star_story` AS s WHERE id = ? ", StarStory.class, id);
	}
	/**
	 * 编辑新故事
	 * @param ss
	 */
	public void save(StarStory ss){
		dao.executeByObject(" UPDATE `star_story` SET title=:title,release_id=:releaseId,release_time=NOW(),`status`=:status,poster_url=:posterUrl,type=:type,cover=:cover,content=:content,vedio_url=:vedioUrl WHERE id=:id  ", ss);
	}
	/**
	 * 获取最大期数
	 * @return
	 */
	public Integer getMaxNumber(){
		return dao.findForInt(" SELECT MAX(`number`) AS number FROM `star_story` ");
	}
	/**
	 * 新增星故事
	 * @param ss
	 */
	public void add(StarStory ss){
		dao.executeByObject(" INSERT INTO  `star_story` SET `title`=:title,`release_id`=:releaseId,`release_time`=NOW(),`status`=:status,`poster_url`=:posterUrl,`type`=:type,`cover`=:cover,`content`=:content,`vedio_url`=:vedioUrl,`number`=:number ", ss);
	}
	
}
