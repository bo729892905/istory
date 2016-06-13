package cn.bluemobi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.dao.BaseDao;
import cn.bluemobi.entity.StoryIsland;
import cn.bluemobi.entity.Subscibe;
import cn.bluemobi.entity.system.Page;
import cn.bluemobi.service.SubscibeService;

/**
 * 订阅
 * @author xiazf
 *
 */
@Service
public class SubscibeServiceImpl implements SubscibeService {
	@Autowired
	private BaseDao dao;
	/**
	 * 获取订阅数量
	 * @param memberId
	 * @return
	 */
	public Integer getSubscibeNum(String memberId){
		//故事状态一定要是启用的
		return dao.findForInt(" SELECT COUNT(*) FROM `subscibe` AS a INNER JOIN story_island b ON b.id = a.story_island_id  WHERE a.member_id = ? AND b.`status`=1 ", memberId);
	}
	
	/**
	 *获取更新数量 
	 * @param updateTime
	 * @return
	 */
	public Integer getUpdateNum(String memberId){
		//故事状态一定要是启用的
		return dao.findForInt(" SELECT COUNT(*) FROM `subscibe` AS a INNER JOIN story_island b ON b.id = a.story_island_id WHERE a.member_id = ? AND DATE_FORMAT(a.read_time, '%Y-%m-%d %H:%i:%s') < DATE_FORMAT(b.update_time, '%Y-%m-%d %H:%i:%s') ANd b.`status`=1  ",memberId);
	}
	
	/**
	 * 获取订阅的用户故事列表
	 * @param memberId
	 * @return
	 */
	public List<Subscibe> getSubscibeAuthorList(String memberId,Page page){
		if (page.getPageCount() == -1) {
			dao.findPageSum(" SELECT  COUNT(*) FROM ( "
					+ " SELECT a.* FROM `subscibe` AS a "
					+ " LEFT JOIN `story_island` AS b ON b.id = a.story_island_id "
					+ " LEFT JOIN `member` AS m ON m.id = b.release_id "
					+ " WHERE a.member_id =? AND b.`status` =1  GROUP BY b.release_id) AS c ", page,memberId);
			if (page.getTotalCount() == 0) {
				return null;
			}
		}
		return dao.findByPage(" SELECT b.release_id AS releaseId,m.`name`,m.icon,m.introduction,"
				+ " (SELECT COUNT(*) FROM `attention` AS t  WHERE t.be_attention_id = b.release_id ) AS attentionNum,MAX(b.update_time) AS updateTime FROM `subscibe` AS a "
				+ " LEFT JOIN `story_island` AS b ON b.id = a.story_island_id "
				+ " LEFT JOIN `member` AS m ON m.id = b.release_id"
				+ " WHERE a.member_id =? AND b.`status` =1  GROUP BY b.release_id ORDER BY updateTime  DESC ",page,Subscibe.class,memberId);
	}
	/**
	 * 获取单个作者的故事列表
	 * @return
	 */
	public List<StoryIsland> getStoryIslandList(String id,String memberId){
		return dao.findForList(" SELECT a.story_island_id AS id ,b.title,b.cover, "
				+ " IF(DATE_FORMAT(a.read_time, '%Y-%m-%d %H:%i:%s') < DATE_FORMAT(b.update_time, '%Y-%m-%d %H:%i:%s'),1,0) AS isRead "
				+ " FROM `subscibe` AS a "
				+ " INNER JOIN `story_island` AS b ON b.id = a.story_island_id  "
				+ " WHERE b.release_id = ? AND b.`status` =1  AND a.member_id = ?  ORDER BY b.update_time DESC ", StoryIsland.class, id,memberId);
	}
	
	/**
	 * 更新故事阅读时间
	 * @param storyId
	 * @param memberId
	 */
	public void updateReadTime(String storyId, String memberId){
		dao.executeByParams(" UPDATE `subscibe` SET read_time=NOW() WHERE story_island_id = ? AND member_id = ? ", storyId,memberId);
	}
	/**
	 * 获取订阅记录
	 * @param storyId
	 * @param memberId
	 * @return
	 */
	public Subscibe getSubscibe(String storyId, String memberId){
		return dao.findForObject(" SELECT * FROM `subscibe` WHERE story_island_id =? and member_id = ? ", Subscibe.class, storyId,memberId);
	}
	/**
	 * 新增订阅
	 * @param storyId
	 * @param memberId
	 */
	public void addSubscibe(String storyId, String memberId){
		dao.executeByParams(" INSERT INTO `subscibe` SET story_island_id =?,member_id = ?,create_time=NOW(),read_time=NOW() ", storyId,memberId);
	}
	/**
	 * 取消订阅
	 * @param storyId
	 * @param memberId
	 */
	public void cancelSubscibe(String storyId, String memberId){
		dao.executeByParams(" DELETE FROM  `subscibe` WHERE story_island_id =? and member_id = ? ", storyId,memberId);
	}
	/**
	 * 获取改故事的订阅用户
	 * @param id
	 * @return
	 */
	public List<String> getSubscibeMemberList(Long id){
		return dao.findForStringList(" SELECT `member_id` AS memberId FROM `subscibe` WHERE story_island_id = ? ",  id);
	}
}
