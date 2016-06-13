package cn.bluemobi.service;

import java.util.List;
import java.util.Map;

import cn.bluemobi.entity.Cover;
import cn.bluemobi.entity.StoryIsland;
import cn.bluemobi.entity.StoryIslandChapter;
import cn.bluemobi.entity.system.Page;

/**
 * 故事岛
 * @author xiazf
 *
 */
public interface StoryIslandService {
	/**
	 * 获取故事岛列表
	 * @param memberId
	 * @param type 
	 * @param choiceness 
	 * @param classify 
	 * @param classify 
	 * @param page 
	 * @return
	 */
	public List<StoryIsland> getStoryIslandList(String memberId, String choiceness,String classifyId, String type,  Page page);
	/**
	 * 创建故事
	 * @param title
	 * @param cover
	 * @param memberId
	 * @param classifyId 
	 * @param chapterType 
	 * @param isRelease 
	 * @return
	 */
	public Long create(StoryIsland si);
	/**
	 * 新增章节
	 * @param sic
	 * @param si 
	 */
	public Long createChapter(StoryIslandChapter sic, StoryIsland si);
	/**
	 * 更新时间
	 * @param storyIslandId 
	 * @param status 
	 */
	public void updateUpdateTime(String storyIslandId, String status);
	/**
	 * 获取用户故事list
	 * @param memberId
	 * @return
	 */
	public List<StoryIsland> getStoryList(String memberId);
	/**
	 * 删除章节
	 * @param chapterId
	 * @param id 
	 */
	public void deleteStoryIslandChapter(String chapterId, String id);
	/**
	 * 查询章节信息
	 * @param chapterId
	 * @return
	 */
	public StoryIslandChapter getChapterById(String chapterId);
	/**
	 * 查询故事的章节list
	 * @param type 
	 */
	public List<StoryIslandChapter> getALLChapter(String id, String type);
	/**
	 * 排序
	 * @param id
	 * @param i
	 */
	public void updateSort(String id, int i);
	/**
	 * 获取故事
	 * @param storyId
	 * @param memberId 
	 * @param type 
	 * @return
	 */
	public StoryIsland getStoryIsland(String storyId, String memberId, String type);
	/**
	 * 故事岛封面
	 * @return
	 */
	public List<Cover> coverList();
	/**
	 * 增加点赞数量
	 * @param bePraiseId
	 */
	public void addStoryHot(Long bePraiseId);
	/**
	 * 减少点赞数量
	 * @param bePraiseId
	 */
	public void deleteStoryHot(Long bePraiseId);
	/**
	 * 后台分页查找故事
	 * @param page
	 * @param storyIsland
	 * @return
	 */
	public Map<String, Object> getStoryIslandListByPage(cn.bluemobi.entity.Page page, StoryIsland storyIsland);
	/**
	 * 更改状态
	 * @param idArr
	 * @param status
	 */
	public void updateStatus(String[] idArr, String status);
	/**
	 * 故事章节列表
	 * @param page
	 * @param id
	 * @return
	 */
	public Map<String, Object> getChapterListByPage(cn.bluemobi.entity.Page page, String id);
	/**
	 * 根据id查找故事岛
	 * @param id
	 * @return 
	 */
	public StoryIsland getStoryIslandById(String id);
	/**
	 * 更改精选状态和热度
	 * @param id
	 * @param choiceness
	 */
	public void updateChoicenessAndHot(String id, String choiceness, String hot);
	/**
	 * 删除故事
	 * @param id
	 */
	public void deleteStoryIsland(String id);
	/**
	 * 故事岛章节编辑
	 * @param sic
	 */
	public void updateStoryIslandChapter(StoryIslandChapter sic);
	/**
	 * 我的关注 -故事列表
	 * @param memberId
	 * @param page
	 * @return
	 */
	public List<StoryIslandChapter> getAttentionStoryList(String memberId,Page page);
	/**
	 * 修改封面和标题
	 * @param si
	 */
	public void updateUpdateTime(StoryIsland si);
	/**
	 * 查找故事列表
	 * @param memberId
	 * @param keywords
	 * @param page
	 * @return
	 */
	public List<StoryIsland> getStoryIslandListBySearch(String keywords, Page page);
	/**
	 * 添加点击数
	 * @param memberId
	 * @param classifyId
	 */
	public void addClassifyHit(String memberId, String classifyId);
	/**
	 * 获取故事章节信息
	 * @param chapterId
	 * @return
	 */
	public StoryIslandChapter getTextStory(String chapterId);
	
}
