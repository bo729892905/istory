package cn.bluemobi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.dao.BaseDao;
import cn.bluemobi.entity.IndexStory;
import cn.bluemobi.service.IndexStoryService;
/**
 * 首页故事接口
 * @author xiazf
 *
 */
@Service
public class IndexStoryServiceImpl implements IndexStoryService {
	@Autowired
	private BaseDao dao;
	/**
	 * 获取故事
	 * @return
	 */
	public List<IndexStory> getNewStoryList(){
		return dao.findForList(" SELECT id,title,createTime,author,type FROM ("
				+ " SELECT s.id,s.title,DATE_FORMAT(s.release_time, '%Y-%m-%d %H:%i:%s') AS createTime,m.`name` AS author ,1 AS type FROM `story_island` AS s LEFT JOIN `member` AS m ON m.id = s.release_id"
				+ " union "
				+ " SELECT f.id,f.title,DATE_FORMAT(f.release_time, '%Y-%m-%d %H:%i:%s') AS createTime,CASE WHEN f.author_type =0 THEN m.`name` WHEN f.author_type =1 THEN a.`name` END AS author,2 AS type FROM `micro_film` AS f LEFT JOIN `member` AS m ON m.id = f.release_id LEFT JOIN `admin` AS a ON a.id = f.release_id"
				+ " union "
				+ " SELECT c.id,c.title,DATE_FORMAT(c.release_time, '%Y-%m-%d %H:%i:%s') AS createTime,CASE WHEN c.author_type =0 THEN m.`name` WHEN c.author_type =1 THEN a.`name` END AS author,3 AS type  FROM `script_factory` AS c LEFT JOIN `member` AS m ON m.id = c.release_id LEFT JOIN `admin` AS a ON a.id = c.release_id"
				+ " ) AS a4 ORDER BY createTime DESC LIMIT 3  ", IndexStory.class);
	}
}
