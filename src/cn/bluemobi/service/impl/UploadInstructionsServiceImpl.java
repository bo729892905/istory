package cn.bluemobi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.dao.BaseDao;
import cn.bluemobi.entity.UploadInstructions;
import cn.bluemobi.service.UploadInstructionsService;
/**
 *  上传说明
 * @author xiazf
 *
 */
@Service
public class UploadInstructionsServiceImpl implements UploadInstructionsService {
	@Autowired
	private BaseDao dao;
	/**
	 * 查询上传说明
	 * @return
	 */
	public UploadInstructions getUploadInstructions(){
		return dao.findForObject(" SELECT r.id,r.content,DATE_FORMAT(r.create_time,'%Y-%m-%d %H:%i:%s') AS createTime FROM `upload_instructions` AS r LIMIT 1", UploadInstructions.class);
	}
	/**
	 * 编辑上传说明
	 * @param content
	 */
	public void edit(String content){
		dao.executeByParams(" UPDATE `upload_instructions` SET content= ? ", content);
	}
}
