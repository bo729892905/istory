package cn.bluemobi.service;

import java.util.List;

import cn.bluemobi.entity.Member;
import cn.bluemobi.entity.StoryIsland;
import cn.bluemobi.entity.Subscibe;
import cn.bluemobi.entity.system.Page;

/**
 * 订阅
 * @author xiazf
 *
 */
public interface SubscibeService {
	/**
	 * 获取订阅数量
	 * @param memberId
	 * @return
	 */
	public Integer getSubscibeNum(String memberId);
	/**
	 *获取更新数量 
	 * @param updateTime
	 * @param memberId 
	 * @return
	 */
	public Integer getUpdateNum( String memberId);
	/**
	 * 获取订阅的用户故事列表
	 * @param memberId
	 * @param page 
	 * @return
	 */
	public List<Subscibe> getSubscibeAuthorList(String memberId, Page page);
	/**
	 * 获取单个作者的故事列表
	 * @param id 
	 * @param memberId 
	 * @return
	 */
	public List<StoryIsland> getStoryIslandList(String id, String memberId);
	/**
	 * 更新故事阅读时间
	 * @param storyId
	 * @param memberId
	 */
	public void updateReadTime(String storyId, String memberId);
	/**
	 * 获取订阅记录
	 * @param storyId
	 * @param memberId
	 * @return
	 */
	public Subscibe getSubscibe(String storyId, String memberId);
	/**
	 * 新增订阅
	 * @param storyId
	 * @param memberId
	 */
	public void addSubscibe(String storyId, String memberId);
	/**
	 * 取消订阅
	 * @param storyId
	 * @param memberId
	 */
	public void cancelSubscibe(String storyId, String memberId);
	/**
	 * 获取改故事的订阅用户id
	 * @param id
	 * @return
	 */
	public List<String> getSubscibeMemberList(Long id);
	
}
