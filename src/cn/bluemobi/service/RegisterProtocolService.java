package cn.bluemobi.service;

import cn.bluemobi.entity.RegisterProtocol;

/**
 * 注册协议
 * @author xiazf
 *
 */
public interface RegisterProtocolService {
	/**
	 * 查询注册协议
	 * @return
	 */
	public RegisterProtocol getRegisterProtocol();
	/**
	 * 修改注册协议
	 * @param content
	 */
	public void edit(String content);

}
