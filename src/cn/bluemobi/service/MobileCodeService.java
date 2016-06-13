package cn.bluemobi.service;

import cn.bluemobi.entity.MobileCode;

public interface MobileCodeService {
	/**
	 * 生成验证码记录
	 * @param code
	 * @param mobile
	 * @param type 
	 */
	public void createCode(int code, String mobile, String type);
	/**
	 * 获取最后一个验证码
	 * @param mobile
	 */
	public MobileCode getLastCode(String mobile);
	/**
	 * 将验证码状态改成已使用
	 * @param id
	 */
	public void updateUsed(Long id);

}
