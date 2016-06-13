package cn.bluemobi.service;

import java.util.List;
import java.util.Map;

import cn.bluemobi.entity.Advertisement;
import cn.bluemobi.entity.Page;

/**
 * 广告管理
 * @author xiazf
 *
 */
public interface AdvertisementService {
	/**
	 * 查询广告列表
	 * @param page
	 * @param advertisement
	 * @return
	 */
	public Map<String, Object> getAdvertisementList(Page page,Advertisement advertisement);
	/**
	 *升序或者降序
	 */
	public void updateNumber(String id, String number, String ordertype);
	/**
	 * 状态修改
	 * @param idArr
	 * @param status
	 */
	public void updateStatus(String[] idArr, String status);
	/**
	 * 查找轮播
	 * @param id
	 * @return
	 */
	public Advertisement getAdvertisementById(String id);
	/**
	 * 修改轮播图信息
	 * @param advertisement
	 */
	public void updateAdvertisement(Advertisement advertisement);
	/**
	 * 获取轮播广告图
	 * @param type
	 * @return
	 */
	public List<Advertisement> getAdvertisementListByType(String type);
	/**
	 * 
	 * @param id
	 */
	public void addViewCount(String id);

}
