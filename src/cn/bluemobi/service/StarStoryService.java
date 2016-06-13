package cn.bluemobi.service;

import java.util.List;
import java.util.Map;

import cn.bluemobi.entity.Page;
import cn.bluemobi.entity.StarStory;


/**
 * 星故事
 * @author xiazf
 *
 */
public interface StarStoryService {
	/**
	 * 是否点赞过
	 * @param memberId
	 * @return
	 */
	public List<StarStory> getStarStoryList(String memberId);
	/**
	 * 增加浏览量
	 */
	public void addViewCount(String id);
	/**
	 * 增加热度
	 * @param bePraiseId
	 */
	public void addStarHot(Long bePraiseId);
	/**
	 * 减少热度
	 * @param bePraiseId
	 */
	public void deleteStarHot(Long bePraiseId);
	/**
	 * 星故事后台分页列表
	 * @param page
	 * @param starStory
	 * @return
	 */
	public Map<String, Object> getStarStoryListByPage(Page page,StarStory starStory);
	/**
	 * 修改星故事状态
	 * @param idArr
	 * @param status
	 */
	public void updateStatus(String[] idArr, String status);
	/**
	 * 删除星故事
	 * @param string
	 */
	public void deleteStarStory(String string);
	/**
	 * 根据id查找星故事
	 * @param id
	 * @return
	 */
	public StarStory getStarStoryById(String id);
	/**
	 * 编辑新故事
	 * @param ss
	 */
	public void save(StarStory ss);
	/**
	 * 获取最大期数
	 * @return
	 */
	public Integer getMaxNumber();
	/**
	 * 新增星故事
	 * @param ss
	 */
	public void add(StarStory ss);

}
