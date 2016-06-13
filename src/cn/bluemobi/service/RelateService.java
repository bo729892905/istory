package cn.bluemobi.service;

import cn.bluemobi.entity.Relate;

/**
 * 关联
 * @author xiazf
 *
 */
public interface RelateService {
	/**
	 * 增加关联
	 * @param storyIslandId
	 * @param microFilmId
	 * @param scriptFactoryId
	 * @param type
	 */
	public void addRelate(String storyIslandId, String microFilmId,String scriptFactoryId, String type);
	/**
	 * 获取关联 脚本id
	 * @param scriptId
	 * @return
	 */
	public Relate getRelateByScript(String scriptId);
	/**
	 * 获取关联 故事id
	 * @param storyId
	 * @return
	 */
	public Relate getRelatebyStory(String storyId);
	/**
	 * 获取关联 电影id
	 * @param filmId
	 * @return
	 */
	public Relate getRelatebyFilm(String filmId);
}
