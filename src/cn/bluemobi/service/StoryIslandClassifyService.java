package cn.bluemobi.service;

import java.util.List;
import java.util.Map;

import cn.bluemobi.entity.Page;
import cn.bluemobi.entity.StoryIslandClassify;

/**
 * 故事岛分类
 * @author xiazf
 *
 */
public  interface StoryIslandClassifyService {
	/**
	 * 获取故事岛分类列表
	 * @return
	 */
	public List<StoryIslandClassify> getClassifyList();
	/**
	 * 查找现有故事岛分类种类列表
	 * @param memberId 
	 * @return
	 */
	public List<StoryIslandClassify> getStoryIslandClassifyList(String memberId);
	/**
	 * 故事岛分类列表
	 * @param page
	 * @param storyIslandClassify
	 * @return
	 */
	public Map<String, Object> getClassifyListByPage(Page page,StoryIslandClassify storyIslandClassify);
	/**
	 * 根据名查找分类
	 * @param classify
	 * @return
	 */
	public StoryIslandClassify getClassifyBy(String classify);
	/**
	 * 新增分类
	 * @param classify
	 */
	public void addClassify(String classify);
	/**
	 * 分类排序
	 * @param id
	 * @param number
	 * @param ordertype 
	 */
	public void updateNumber(String id, String number, String ordertype);
	/**
	 * 根据分类Id查找分类
	 * @param id
	 * @return
	 */
	public StoryIslandClassify getClassifyById(String id);
	/**
	 * 修改故事分类名
	 * @param id
	 * @param classify
	 */
	public void save(String id, String classify);
	/**
	 * 删除故事分类
	 * @param id
	 * @return
	 */
	public String deleteClassify(String id);

}
