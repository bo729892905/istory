package cn.bluemobi.service;

import java.util.Map;

import cn.bluemobi.entity.system.Page;

/**
 * 发现接口
 * @author xiazf
 *
 */
public interface SearchService {

	/**
	 * 发现接口
	 * @param memberId
	 * @param keywords
	 * @param type
	 * @param page 
	 * @return
	 */
	public Map<String, Object> getAllBySearch(String memberId, String keywords,String type, Page page);

}
