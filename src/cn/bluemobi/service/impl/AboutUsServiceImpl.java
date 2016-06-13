package cn.bluemobi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.dao.BaseDao;
import cn.bluemobi.entity.AboutUs;
import cn.bluemobi.service.AboutUsService;

/**
 * 关于我们
 * @author xiazf
 *
 */
@Service
public class AboutUsServiceImpl implements AboutUsService {
	@Autowired
	private BaseDao dao;
	/**
	 * 获取关于我们
	 * 
	 **/
	public AboutUs getAboutUs(){
		return dao.findForObject(" SELECT id,about_us AS aboutUs FROM `about_us` LIMIT 1 ", AboutUs.class);
	}
	/**
	 * 修改关于我们
	 * @param content
	 */
	public void edit(String aboutUs){
		dao.executeByParams(" UPDATE `about_us` SET about_us=?  ", aboutUs);
	}
}
