package cn.bluemobi.service;

import cn.bluemobi.entity.AboutUs;
/**
 * 关于我们
 * @author xiazf
 *
 */
public interface AboutUsService {
	/**
	 * 获取关于我们
	 * 
	 **/
	public AboutUs getAboutUs();
	/**
	 * 修改关于我们
	 * @param content
	 */
	public void edit(String aboutUs);

}
