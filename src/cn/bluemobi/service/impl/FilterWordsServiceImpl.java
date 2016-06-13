package cn.bluemobi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.dao.BaseDao;
import cn.bluemobi.entity.FilterWords;
import cn.bluemobi.entity.Page;
import cn.bluemobi.service.FilterWordsService;
/**
 * 过滤字
 * @author xiazf
 *
 */
@Service
public class FilterWordsServiceImpl implements FilterWordsService {
	@Autowired
	private BaseDao dao;
	/**
	 * 查询过滤字列表
	 * @param page
	 * @param filterWords
	 * @return
	 */
	public Map<String, Object> getFilterWordsListByPage(Page page,FilterWords filterWords){
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = dao.findForInt(" SELECT COUNT(*) FROM `filter_words`  ");
		page.setTotalResult(count);
		//未写完
		List<FilterWords> filterWordsList = dao
				.findForList(
						"  SELECT id,filter_words AS filterWords,(SELECT COUNT(*) FROM `comment` AS c WHERE c.`comment` LIKE CONCAT('%',f.filter_words,'%')) AS number FROM `filter_words` AS f " ,
						FilterWords.class);
		map.put("page", page);
		map.put("filterWordsList", filterWordsList);
		return map;
	}
	/**
	 * 添加过滤字
	 * @param filter
	 */
	public void addFilterWords(String filter){
		dao.executeByParams(" INSERT  INTO  `filter_words` SET `filter_words` = ? ", filter);
	}
	/**
	 * 查询过滤字是否已经添加
	 * @param filter
	 * @return
	 */
	public FilterWords getFilterWordsByFilter(String filter){
		return dao.findForObject(" SELECT id,filter_words AS filterWords FROM `filter_words` WHERE filter_words = ?", FilterWords.class, filter);
	}
	/**
	 * 删除关键字
	 * @param id
	 */
	public void deleteFilterWords(String id){
		dao.executeByParams(" DELETE FROM `filter_words` WHERE id = ?  ", id );
	}
	/**
	 * 查找过滤字
	 * @return
	 */
	public List<FilterWords> getFilterList(){
		return dao.findForList(" SELECT filter_words AS  filterWords FROM `filter_words` ", FilterWords.class);
	}
}
