package cn.bluemobi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.dao.BaseDao;
import cn.bluemobi.entity.RegisterProtocol;
import cn.bluemobi.service.RegisterProtocolService;

/**
 * 注册协议接口实现
 * @author xiazf
 *
 */
@Service
public class RegisterProtocolServiceImpl implements RegisterProtocolService {
	@Autowired
	private BaseDao dao;
	/**
	 * 查询注册协议
	 * @return
	 */
	public RegisterProtocol getRegisterProtocol(){
		return dao.findForObject(" SELECT r.id,r.content,DATE_FORMAT(r.create_time,'%Y-%m-%d %H:%i:%s') AS createTime FROM `register_protocol` AS r LIMIT 1 ", RegisterProtocol.class);
	}
	/**
	 * 修改注册协议
	 * @param content
	 */
	public void edit(String content){
		dao.executeByParams(" UPDATE `register_protocol` SET content=? ", content);
	}
}
