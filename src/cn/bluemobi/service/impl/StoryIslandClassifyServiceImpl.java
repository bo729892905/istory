package cn.bluemobi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.dao.BaseDao;
import cn.bluemobi.entity.Page;
import cn.bluemobi.entity.StoryIslandClassify;
import cn.bluemobi.service.StoryIslandClassifyService;
import cn.bluemobi.util.text.TextHelper;

/**
 * 故事岛分类
 * @author xiazf
 *
 */
@Service
public class StoryIslandClassifyServiceImpl implements StoryIslandClassifyService {
	@Autowired
	private BaseDao dao;
	
	/**
	 * 获取故事岛分类列表
	 * @return
	 */
	public List<StoryIslandClassify> getClassifyList(){
		return dao.findForList(" SELECT * FROM `story_island_classify`  ", StoryIslandClassify.class);
	}
	/**
	 * 查找现有故事岛分类种类列表
	 * @return
	 */
	public List<StoryIslandClassify> getStoryIslandClassifyList(String memberId){
		if(TextHelper.isNullOrEmpty(memberId)){
			return dao.findForList(" SELECT  c.id, c.classify FROM `story_island_classify` AS c  ORDER BY number ASC ", StoryIslandClassify.class );
		}else{
			return dao.findForList(" SELECT * FROM ("
					+ " (SELECT  c.id, c.classify,IFNULL((SELECT hit FROM `story_hit_sort` AS shs WHERE shs.classify_id=c.id AND shs.member_id = ? ) ,0) AS hit FROM `story_island_classify` AS c  ORDER BY number ASC LIMIT 1 )) AS a"
					+ " UNION "
					+ " SELECT * FROM ("
					+ " (SELECT  c.id, c.classify,IFNULL((SELECT hit FROM `story_hit_sort` AS shs WHERE shs.classify_id=c.id AND shs.member_id = ?) ,0) AS hit  FROM `story_island_classify` AS c  "
					+ " ORDER BY hit DESC, number ASC )) AS b ", StoryIslandClassify.class,memberId,memberId);
		}
	}
	/**
	 * 故事岛分类列表
	 * @param page
	 * @param storyIslandClassify
	 * @return
	 */
	public Map<String, Object> getClassifyListByPage(Page page,StoryIslandClassify storyIslandClassify){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();

		Integer count = dao.findForInt(" SElECT COUNT(*) FROM `story_island_classify`  ");
		page.setTotalResult(count);
		sb.append("  LIMIT "+page.getCurrentResult()+", "+page.getShowCount()+" ");
		List<StoryIslandClassify> storyIslandClassifyList = dao
				.findForList(
						" SELECT * FROM  `story_island_classify` ORDER BY `number` ASC " + sb.toString(),
						StoryIslandClassify.class);
		map.put("page", page);
		map.put("storyIslandClassifyList", storyIslandClassifyList);
		return map;
	}
	/**
	 * 根据名查找分类
	 * @param classify
	 * @return
	 */
	public StoryIslandClassify getClassifyBy(String classify){
		return dao.findForObject(" SELECT * FROM `story_island_classify` WHERE classify = ? ", StoryIslandClassify.class, classify);
	}
	/**
	 * 新增分类
	 * @param classify
	 */
	public void addClassify(String classify){
		StoryIslandClassify s =  dao.findForObject(" SELECT * FROM  `story_island_classify` ORDER BY number DESC limit 1 ",StoryIslandClassify.class);
		Integer i = 0;
		if(!TextHelper.isNullOrEmpty(s)){
			i = s.getNumber(); 
		}
		i++;
		dao.executeByParams(" INSERT INTO `story_island_classify` SET classify = ?,number = ?  ", classify,i);
	}
	/**
	 * 分类排序
	 * @param id
	 * @param number
	 */
	public void updateNumber(String id, String number,String ordertype){
		StoryIslandClassify a =null;
		//0降序1升序
		if("0".equals(ordertype)){
			 a = getUpOrDownNumber("0",number);
		}
		else if("1".equals(ordertype)){
			a = getUpOrDownNumber("1",number);
		}
		if(!TextHelper.isNullOrEmpty(a)){
			dao.executeByParams(" UPDATE `story_island_classify` SET number = ? WHERE id = ? ", a.getNumber(),id);
			dao.executeByParams(" UPDATE `story_island_classify` SET number = ? WHERE id = ? ", number,a.getId());
		}
		
	}
	/**
	 * 获取上一个或者下一个分类
	 * @param type   
	 */
	public StoryIslandClassify getUpOrDownNumber(String ordertype,String number){
		String sql = "";
		//升
		if(ordertype.equals("1")){
			sql = " AND number < ? ORDER BY number desc LIMIT 1 ";
		}
		//降
		else if(ordertype.equals("0")){
			sql = " AND number > ? ORDER BY number asc LIMIT 1 ";
		}
		return dao.findForObject("SELECT *  FROM `story_island_classify` AS a WHERE 1 = 1  "+sql.toString(), StoryIslandClassify.class,number);
	}
	/**
	 * 根据分类Id查找分类
	 * @param id
	 * @return
	 */
	public StoryIslandClassify getClassifyById(String id){
		return dao.findForObject(" SELECT * FROM `story_island_classify` WHERE id = ? ", StoryIslandClassify.class, id);
	}
	/**
	 * 修改故事分类名
	 * @param id
	 * @param classify
	 */
	public void save(String id, String classify){
		dao.executeByParams(" UPDATE `story_island_classify` SET classify = ? WHERE id = ?  ", classify,id);
	}
	/**
	 * 删除故事分类
	 * @param id
	 * @return
	 */
	public String deleteClassify(String id){
		Integer i = dao.findForInt(" SELECT COUNT(*) FROM  `story_island` WHERE classify_id = ?  ", id);
		if(i>0){
			return "exists_story";
		}else{
			dao.executeByParams(" DELETE FROM `story_island_classify` WHERE id = ? ", id);
		}
		return "";
	}
}
