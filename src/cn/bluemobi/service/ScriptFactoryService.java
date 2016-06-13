package cn.bluemobi.service;

import java.util.List;
import java.util.Map;

import cn.bluemobi.entity.ScriptChapter;
import cn.bluemobi.entity.ScriptFactory;
import cn.bluemobi.entity.ScriptRole;
import cn.bluemobi.entity.system.Page;

/**
 * 剧本工厂
 * @author xiazf
 *
 */
public interface ScriptFactoryService {
	/**
	 * 剧本工厂
	 * @param memberId
	 * @return
	 */
	public List<ScriptFactory> getScriptFactoryList(String memberId);
	/**
	 * 分页查询剧本工厂
	 * @param memberId
	 * @param page
	 * @return
	 */
	public List<ScriptFactory> getScriptFactoryListPage(String type,String memberId,Page page);
	/**
	 * 获取章节list
	 * @param scriptId
	 * @param memberId 
	 * @return
	 */
	public List<ScriptChapter> getAllChapter(String scriptId, String memberId);
	/**
	 * 剧本详情
	 * @param scriptId
	 * @param memberId 
	 * @return
	 */
	public ScriptFactory getScriptFactory(String scriptId, String memberId);
	/**
	 * 增加角色
	 * @param sr
	 * @return 
	 */
	public Long addScriptRole(ScriptRole sr);
	/**
	 * 增加剧本
	 * @param sf
	 * @return
	 */
	public Long addScript(ScriptFactory sf);
	/**
	 * 将角色与剧本关联
	 * @param id 
	 * @param scriptId
	 */
	public void updateRoleScriptId(String scriptId,String id);
	/**
	 * 编辑剧本
	 * @param sf
	 */
	public void updateScript(ScriptFactory sf);
	/**
	 * 发布场景
	 * @param status
	 * @param scriptId 
	 */
	public void updateScriptStatus(String scriptId, String status);
	/**
	 * 新增故事章节
	 * @param scriptId
	 * @param text
	 * @param status 
	 */
	public Long addScriptChapter(String scriptId, String text, String status);
	/**
	 * 获取角色列表
	 * @param scriptId
	 * @return 
	 */
	public List<ScriptRole> getScriptRoleList(String scriptId);
	/**
	 * 删除章节
	 * @param chapterId
	 */
	public void deleteScriptChapter(String chapterId);
	/**
	 * 增加剧本热度
	 * @param bePraiseId
	 */
	public void addScriptHot(Long bePraiseId);
	/**
	 * 减少剧本热度
	 * @param bePraiseId
	 */
	public void deleteScriptHot(Long bePraiseId);
	/**
	 * 获取后台剧本分页列表
	 * @param page
	 * @param scriptFactory
	 * @return
	 */
	public Map<String, Object> getScriptFactoryListByPage(cn.bluemobi.entity.Page page, ScriptFactory scriptFactory);
	/**
	 * 修改剧本状态
	 * @param idArr
	 * @param status
	 */
	public void updateStatus(String[] idArr, String status);
	/**
	 * 获取脚本详情
	 * @param scriptChapterId
	 * @return
	 */
	public ScriptChapter getScriptChapterById(String scriptChapterId);
	/**
	 * 删除剧本
	 * @param string
	 */
	public void deleteScriptFactory(String string);
	/**
	 * 剧本工厂修改
	 * @param sf
	 */
	public void save(ScriptFactory sf);
	/**
	 * 剧本场次列表
	 * @param page
	 * @param id
	 * @return
	 */
	public Map<String, Object> getChapterListByPage(
			cn.bluemobi.entity.Page page, String id);
	/**
	 * 获取场次详情
	 * @param id
	 * @return
	 */
	public ScriptChapter getChapterById(String id);
	/**
	 * 修改场次详情
	 * @param id
	 * @param text
	 * @param scriptId 
	 */
	public void updateScriptChapter(String id, String text, String scriptId);
	/**
	 * 修改章节状态
	 * @param chapterId
	 * @param status
	 */
	public void updateChapterStatus(String chapterId, String status);
	/**
	 * 搜索查找剧本
	 * @param keywords
	 * @param page
	 * @return
	 */
	public List<ScriptFactory> getScriptFactoryBySearch(String keywords,
			Page page);
	
	public ScriptFactory getScriptFactoryById(String id);

}
