package cn.bluemobi.service;

import java.util.List;
import java.util.Map;

import cn.bluemobi.entity.FilterWords;
import cn.bluemobi.entity.Page;

/**
 * 过滤字
 * @author xiazf
 *
 */
public interface FilterWordsService {
	/**
	 * 查询过滤字列表
	 * @param page
	 * @param filterWords
	 * @return
	 */
	public Map<String, Object> getFilterWordsListByPage(Page page,FilterWords filterWords);
	/**
	 * 添加过滤字
	 * @param filter
	 */
	public void addFilterWords(String filter);
	/**
	 * 查询过滤字是否已经添加
	 * @param filter
	 * @return
	 */
	public FilterWords getFilterWordsByFilter(String filter);
	/**
	 * 删除关键字
	 * @param id
	 */
	public void deleteFilterWords(String id);
	/**
	 * 查找过滤字
	 * @return
	 */
	public List<FilterWords> getFilterList();

}
