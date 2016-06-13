package cn.bluemobi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.dao.BaseDao;
import cn.bluemobi.entity.Cover;
import cn.bluemobi.entity.Page;
import cn.bluemobi.service.CoverService;

/**
 * 封面
 * @author xiazf
 *
 */
@Service
public class CoverServiceImpl implements CoverService {
	@Autowired
	private BaseDao dao;
	/**
	 * 封面列表
	 * @param page
	 * @return
	 */
	public Map<String, Object> getAdvertisementList(Page page){
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = getTotalCover();
		page.setTotalResult(count);
		//未写完
		List<Cover> coverList = dao
				.findForList(
						"  SELECT a.id,a.cover,DATE_FORMAT(a.create_time,'%Y-%m-%d %H:%i:%s') AS createTime,a.release_id AS releaseId,m.`name` FROM `cover` AS a LEFT JOIN `admin` AS m ON m.id = a.release_id  ORDER BY create_time DESC LIMIT ?, ? " ,
						Cover.class,page.getCurrentResult(),page.getShowCount());
		map.put("page", page);
		map.put("coverList", coverList);
		return map;
	}
	/**
	 * 新增封面
	 * @param cover
	 * id   发布人id
	 */
	public void save(String cover,Long id){
		dao.executeByParams(" INSERT INTO `cover` SET cover = ?,release_id = ?,create_time = NOW()  ", cover,id);
	}
	/**
	 * 获取cover总数
	 * @return
	 */
	public Integer getTotalCover(){
		return dao.findForInt(" SELECT COUNT(*) FROM cover " );
	}
	/**
	 * 根据id获取封面
	 * @param id
	 * @return
	 */
	public Cover getCoverById(String id){
		return dao.findForObject(" SELECT a.id,a.cover,DATE_FORMAT(a.create_time,'%Y-%m-%d %H:%i:%s') AS createTime,a.release_id AS releaseId,m.`name` FROM `cover` AS a LEFT JOIN `admin` AS m ON m.id = a.release_id WHERE a.id = ?  ", Cover.class, id);
	}
	/**
	 * 编辑封面
	 * @param cover
	 * @param id
	 * @param id2
	 */
	public void updateCover(String cover, String id, Long id2){
		dao.executeByParams(" UPDATE `cover` SET cover = ?,release_id = ?,create_time = NOW() WHERE id = ?  ", cover,id2,id);
	}
	/**
	 * 删除封面
	 * @param string
	 */
	public void deleteCover(String id){
		dao.executeByParams(" DELETE FROM `cover` WHERE id = ? ", id);
	}
}
