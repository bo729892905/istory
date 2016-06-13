package cn.bluemobi.service;

import java.util.List;

import cn.bluemobi.entity.IndexStory;

/**
 * 首页文章故事
 * @author xiazf
 *
 */
public interface IndexStoryService {
	/**
	 * 获取故事
	 * @return
	 */
	public List<IndexStory> getNewStoryList();

}
