package cn.bluemobi.service;

import java.util.List;
import java.util.Map;

import cn.bluemobi.entity.Page;
import cn.bluemobi.entity.Report;
import cn.bluemobi.entity.Tag;


/**
 * 举报
 * @author xiazf
 *
 */
public interface ReportService {

	/**
	 * 查询标签列表
	 * @return
	 */
	
	public List<Tag> getReportTagList();
	/**
	 * 新增举报
	 * @param releaseId
	 * @param beReportId
	 * @param type
	 * @param tagId
	 * @param memberId 
	 */
	public void addReport(String releaseId, String beReportId, String type,String tagId, String memberId);
	/**
	 * 获取举报记录
	 * @param beReportId
	 * @param type
	 * @param memberId
	 * @return
	 */
	public Report getReport(String beReportId, String type, String memberId);
	/**
	 * 获取标签列表
	 * @param page
	 * @return
	 */
	public Map<String, Object> getReportTagList(Page page);
	/**
	 * 查找tag
	 * @param tag
	 * @return
	 */
	public Tag getTagByTag(String tag);
	/**
	 * 添加tag
	 * @param tag
	 */
	public void addTag(String tag);
	/**
	 * 删除标签
	 * @param id
	 */
	public void deleteTag(String id);
	/**
	 * 查找举报列表
	 * @param page
	 * @return
	 */
	public Map<String, Object> getReportList(Page page,Report report);
	/**
	 * 删除举报记录
	 * @param type
	 * @param beReportId
	 */
	public void deleteReport(String type, String beReportId);

}
