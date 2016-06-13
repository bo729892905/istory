package cn.bluemobi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.dao.BaseDao;
import cn.bluemobi.entity.Praise;
import cn.bluemobi.service.PraiseService;
/**
 * 点赞
 * @author xiazf
 *
 */
@Service
public class PraiseServiceImpl implements PraiseService {
	@Autowired
	private BaseDao dao;
	
	/**
	 * 新增点赞记录
	 * @param praise
	 */
	public void addPraise(Praise praise){
		dao.executeByObject(" INSERT INTO praise SET be_praise_id=:bePraiseId,praise_id=:praiseId,`create_time`=NOW(),type=:type ", praise);
		
	}
	/**
	 * 查询点赞
	 */
	public Integer getPraise(Praise praise){
		return dao.findForInt(" SELECT COUNT(*) FROM praise WHERE be_praise_id = ? AND praise_id = ? AND type =? ", praise.getBePraiseId(),praise.getPraiseId(),praise.getType());
	}
	
	/**
	 * 取消点赞
	 * @param praise
	 */
	public void deletePraise(Praise praise){
		dao.executeByParams(" DELETE FROM praise WHERE be_praise_id=? AND praise_id=? AND type =?  ",  praise.getBePraiseId(),praise.getPraiseId(),praise.getType());
	}

}
