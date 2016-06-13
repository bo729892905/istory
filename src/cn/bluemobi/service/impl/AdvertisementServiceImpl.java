package cn.bluemobi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.dao.BaseDao;
import cn.bluemobi.entity.Advertisement;
import cn.bluemobi.entity.Page;
import cn.bluemobi.entity.Relate;
import cn.bluemobi.service.AdvertisementService;
import cn.bluemobi.util.text.TextHelper;
/**
 * 广告管理
 * @author xiazf
 *
 */
@Service
public class AdvertisementServiceImpl implements AdvertisementService {
	@Autowired
	private BaseDao dao;
	/**
	 * 查询广告列表
	 * @param page
	 * @param advertisement
	 * @return
	 */
	public Map<String, Object> getAdvertisementList(Page page,Advertisement advertisement){
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = dao.findForInt(" SELECT COUNT(*) FROM `advertisement` WHERE type = ?   ",advertisement.getType());
		page.setTotalResult(count);
		//未写完
		List<Advertisement> advertisementList = dao
				.findForList(
						"  SELECT a.id,a.advertisement_url AS advertisementUrl,a.link_url AS linkUrl,a.`link_type` AS linkType,a.`link_id` AS linkId,a.hit,a.`status`,a.`status`,a.type,a.number,a.title FROM `advertisement` AS a WHERE type = ?  ORDER BY number ASC " ,
						Advertisement.class,advertisement.getType());
		map.put("page", page);
		map.put("advertisementList", advertisementList);
		return map;
	}
	
	/**
	 *升序或者降序
	 */
	public void updateNumber(String id, String number, String ordertype){
		Advertisement a =null;
		//0故事轮播降序1故事轮播升序
		if("0".equals(ordertype)){
			 a = getUpOrDownNumber("1","0",number);
		}
		else if("1".equals(ordertype)){
			a = getUpOrDownNumber("1","1",number);
		}
		//2微电影轮播降序3微电影轮播升序
		else if("2".equals(ordertype)){
			a = getUpOrDownNumber("2","0",number);
		}
		else if("3".equals(ordertype)){
			a = getUpOrDownNumber("2","1",number);
		}
		//4是剧本轮播降序 5是剧本轮播升序
		else if("4".equals(ordertype)){
			a = getUpOrDownNumber("3","0",number);
		}
		else if("5".equals(ordertype)){
			a = getUpOrDownNumber("3","1",number);
		}
		if(!TextHelper.isNullOrEmpty(a)){
			dao.executeByParams(" UPDATE `advertisement` SET number = ? WHERE id = ? ", a.getNumber(),id);
			dao.executeByParams(" UPDATE `advertisement` SET number = ? WHERE id = ? ", number,a.getId());
		}
		
	}
	/**
	 * 
	 * @param type   1故事岛  2 微电影 3 剧本
	 */
	public Advertisement getUpOrDownNumber(String type,String ordertype,String number){
		String sql = "";
		//升
		if(ordertype.equals("1")){
			sql = " AND number < ? ORDER BY number desc LIMIT 1 ";
		}
		//降
		else if(ordertype.equals("0")){
			sql = " AND number > ? ORDER BY number asc LIMIT 1 ";
		}
		return dao.findForObject("SELECT a.id,a.advertisement_url AS advertisementUrl,a.link_url AS linkUrl,a.hit,a.`status`,a.`status`,a.type,a.number,a.title FROM `advertisement` AS a WHERE type = ?  "+sql.toString(), Advertisement.class, type,number);
	}
	
	/**
	 * 状态修改
	 * @param idArr
	 * @param status
	 */
	public void updateStatus(String[] idArr, String status){
		String[] sql = new String[100];
		for (int i = 0; i < idArr.length; i++) {
			sql[i] = " UPDATE `advertisement` SET `status` = "+status+" WHERE id = "+idArr[i];
		}
		dao.executeBatch(sql);
	}
	
	/**
	 * 查找轮播
	 * @param id
	 * @return
	 */
	public Advertisement getAdvertisementById(String id){
		return dao.findForObject(" SELECT a.id,a.advertisement_url AS advertisementUrl,a.link_url AS linkUrl,a.link_type AS linkType,a.link_id AS linkId,a.hit,a.`status`,a.`status`,a.type,a.number,a.title FROM `advertisement` AS a WHERE id = ?  ", Advertisement.class,id);
	}
	
	/**
	 * 修改轮播图信息
	 * @param advertisement
	 */
	public void updateAdvertisement(Advertisement advertisement){
		dao.executeByObject(" UPDATE  `advertisement` SET `advertisement_url` =:advertisementUrl,`link_url` =:linkUrl,`link_type`=:linkType,`link_id`=:linkId,`hit` =:hit,`title`=:title WHERE `id`=:id  ", advertisement);
	}
	/**
	 * 获取轮播广告图
	 * @param type 1是故事岛 2是微电影 3是剧本
	 * @return
	 */
	public List<Advertisement> getAdvertisementListByType(String type){
		
		List<Advertisement> list=dao.findForList(" SELECT * FROM `advertisement` WHERE `type` = ? AND  `status` = 1  ORDER BY number ASC", Advertisement.class, type);
		//查找关联对象
		if(list != null&&list.size()>0){
			for (Advertisement advertisement : list) {
				if("1".equals(advertisement.getLinkType())){
					Relate relate = new Relate();
					if("1".equals(type)){
						 relate = dao.findForObject(" SELECT r.id,r.relate_story_id AS relateStoryId,r.relate_script_id AS relateScriptId,r.relate_film_id AS relateFilmId FROM `relate`AS r WHERE r.relate_story_id = ?  ", Relate.class, advertisement.getLinkId());
					}else if("2".equals(type)){
						 relate = dao.findForObject(" SELECT r.id,r.relate_story_id AS relateStoryId,r.relate_script_id AS relateScriptId,r.relate_film_id AS relateFilmId FROM `relate`AS r WHERE r.relate_film_id = ?  ", Relate.class, advertisement.getLinkId());
					}else if("3".equals(type)){
						 relate = dao.findForObject(" SELECT r.id,r.relate_story_id AS relateStoryId,r.relate_script_id AS relateScriptId,r.relate_film_id AS relateFilmId FROM `relate`AS r WHERE r.relate_script_id = ?  ", Relate.class, advertisement.getLinkId());
					}
					if(relate != null){
						advertisement.setRelateStoryId(relate.getRelateStoryId());
						advertisement.setRelateFilmId(relate.getRelateFilmId());
						advertisement.setRelateScriptId(relate.getRelateScriptId());
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 
	 * @param id
	 */
	public void addViewCount(String id){
		dao.executeByParams(" UPDATE `advertisement` SET hit := hit+1 WHERE id = ? ", id);
	}

}