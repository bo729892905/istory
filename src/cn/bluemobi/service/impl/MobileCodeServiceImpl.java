package cn.bluemobi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.dao.BaseDao;
import cn.bluemobi.entity.MobileCode;
import cn.bluemobi.service.MobileCodeService;

@Service
public class MobileCodeServiceImpl implements MobileCodeService{
	@Autowired
	private BaseDao dao;
	/**
	 * 生成验证码记录
	 * @param code
	 * @param mobile
	 */
	public void createCode(int code, String mobile,String type){
		dao.executeByParams("insert into mobile_code set code=?,mobile=?,create_date=now(),type=?", code,mobile,type);
	}
	/**
	 * 获取最后一个验证码
	 * @param mobile
	 */
	public MobileCode getLastCode(String mobile){
		return dao.findForObject("SELECT * FROM `mobile_code` WHERE mobile = ?  ORDER BY create_date desc LIMIT 1", MobileCode.class, mobile);
	}
	/**
	 * 将验证码状态改成已使用
	 * @param id
	 */
	public void updateUsed(Long id){
		dao.executeByParams(" UPDATE `mobile_code` SET isUsed = 1 WHERE id = ? ", id);
	}

}
