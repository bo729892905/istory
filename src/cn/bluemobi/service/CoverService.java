package cn.bluemobi.service;

import java.util.Map;

import cn.bluemobi.entity.Cover;
import cn.bluemobi.entity.Page;

/**
 * 封面
 * @author xiazf
 *
 */
public interface CoverService {
	/**
	 * 封面列表
	 * @param page
	 * @return
	 */
	public Map<String, Object> getAdvertisementList(Page page);
	/**
	 * 新增封面
	 * @param cover
	 * @param id 
	 */
	public void save(String cover, Long id);
	/**
	 * 获取cover总数
	 * @return
	 */
	public Integer getTotalCover();
	/**
	 * 根据id获取封面
	 * @param id
	 * @return
	 */
	public Cover getCoverById(String id);
	/**
	 * 编辑封面
	 * @param cover
	 * @param id
	 * @param id2
	 */
	public void updateCover(String cover, String id, Long id2);
	/**
	 * 删除封面
	 * @param string
	 */
	public void deleteCover(String id);

}
