package cn.bluemobi.service;

import cn.bluemobi.entity.UploadInstructions;

/**
 * 上传说明
 * @author xiazf
 *
 */
public interface UploadInstructionsService {
	/**
	 * 查询上传说明
	 * @return
	 */
	public UploadInstructions getUploadInstructions();
	/**
	 * 编辑上传说明
	 * @param content
	 */
	public void edit(String content);
	
}
