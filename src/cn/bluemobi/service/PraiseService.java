package cn.bluemobi.service;

import cn.bluemobi.entity.Praise;

/**
 * 点赞
 * @author xiazf
 *
 */

public interface PraiseService {
	/**
	 * 新增点赞记录
	 * @param praise
	 */
	public void addPraise(Praise praise);

	/**
	 * 取消点赞
	 * @param praise
	 */
	public void deletePraise(Praise praise);
	/**
	 * 查询点赞
	 */
	public Integer getPraise(Praise praise);
}
