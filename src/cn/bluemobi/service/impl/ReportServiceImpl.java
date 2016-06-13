package cn.bluemobi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.dao.BaseDao;
import cn.bluemobi.entity.Page;
import cn.bluemobi.entity.Report;
import cn.bluemobi.entity.Tag;
import cn.bluemobi.service.ReportService;
import cn.bluemobi.util.helper.ValidateHelper;
/**
 * 举报
 * @author xiazf
 *
 */
@Service
public class ReportServiceImpl implements ReportService {
	@Autowired
	private BaseDao dao;
	/**
	 * 查询标签列表
	 * @return
	 */
	
	public List<Tag> getReportTagList(){
		return dao.findForList("SELECT id,tag,DATE_FORMAT(create_time,'%Y-%m-%d %H:%i:%s') AS createTime FROM tag ORDER BY create_time DESC LIMIT 3 ", Tag.class);
	}
	/**
	 * 新增举报
	 * @param releaseId
	 * @param beReportId
	 * @param type
	 * @param tagId
	 * @param memberId 
	 */
	public void addReport(String releaseId, String beReportId, String type,String tagId, String memberId){
		dao.executeByParams(" INSERT INTO `report` SET release_id=?,be_report_id=?,type=?,tag_id=?,member_id=?,create_time=NOW()", releaseId,beReportId,type,tagId,memberId);
	}
	
	/**
	 * 获取举报记录
	 * @param beReportId
	 * @param type
	 * @param memberId
	 * @return
	 */
	public Report getReport(String beReportId, String type, String memberId){
		return dao.findForObject(" SELECT r.id,r.release_id AS releaseId,DATE_FORMAT(r.create_time,'%Y-%m-%d %H:%i:%s') AS createTime,r.type,r.be_report_id AS beReportId,r.tag_id AS tagId,r.member_id AS memberId FROM `report` AS r WHERE r.be_report_id = ? AND r.type = ? AND r.member_id = ?   ", Report.class, beReportId,type,memberId);
	}
	/**
	 * 获取标签列表
	 * @param page
	 * @return
	 */
	public Map<String, Object> getReportTagList(Page page){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		Object[] array = null;
		
		sb.append(" ORDER BY create_time desc  LIMIT ?, ? ");
		
		Integer count = dao.findForInt(" SElECT COUNT(*) FROM `tag`  ");
		page.setTotalResult(count);
		list.add(page.getCurrentResult());
		list.add(page.getShowCount());
		array = list.toArray();
		List<Tag> tagList = dao
				.findForList(
						" SELECT * FROM `tag` " + sb.toString(),
						Tag.class, array);
		map.put("page", page);
		map.put("tagList", tagList);
		return map;
	}
	/**
	 * 查找tag
	 * @param tag
	 * @return
	 */
	public Tag getTagByTag(String tag){
		return dao.findForObject("  SELECT * FROM `tag` WHERE tag = ? ", Tag.class, tag);
	}
	/**
	 * 添加tag
	 * @param tag
	 */
	public void addTag(String tag){
		dao.executeByParams(" INSERT INTO `tag` SET tag = ?,create_time=NOW() ", tag);
	}
	/**
	 * 删除标签
	 * @param id
	 */
	public void deleteTag(String id){
		dao.executeByParams(" DELETE FROM `tag` WHERE id=? ", id);
	}
	/**
	 * 查找举报列表
	 * @param page
	 * @return
	 */
	public Map<String, Object> getReportList(Page page,Report report){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		Object[] array = null;
		Object[] array1 = null;
		//标题
		if (!ValidateHelper.isNullOrEmpty(report.getTitle())) {
			sb.append(" AND (i.title LIKE ? AND r.type=1) OR (s.title LIKE ? AND r.type=2) OR (f.title LIKE ? AND r.type=3) ");
			list.add('%' + report.getTitle() + '%');
			list.add('%' + report.getTitle() + '%');
			list.add('%' + report.getTitle() + '%');
		}
		
		// 举报日期
		if (!ValidateHelper.isNullOrEmpty(report.getStartTime())) {
			sb.append(" and DATE_FORMAT(r.create_time, '%Y-%m-%d')>=  DATE_FORMAT( ?, '%Y-%m-%d')");
			list.add(report.getStartTime());
		}
		if (!ValidateHelper.isNullOrEmpty(report.getEndTime())) {
			sb.append(" and DATE_FORMAT(r.create_time, '%Y-%m-%d')<=  DATE_FORMAT( ?, '%Y-%m-%d')");
			list.add(report.getEndTime());
		}
		
		array1 = list.toArray();
		
		
		Integer count = dao.findForInt("SELECT COUNT(*) FROM ( "
				       	+ "(SELECT r.* "
						+ " FROM `report` AS r "
						+ " LEFT JOIN `member` AS m ON r.release_id = m.id"
						+ " LEFT JOIN `admin` AS a ON r.release_id = a.id"
						+ " LEFT JOIN `script_factory` AS s ON s.id = r.be_report_id"
						+ " LEFT JOIN `micro_film` AS f ON f.id = r.be_report_id"
						+ " LEFT JOIN `story_island` AS i ON i.id = r.be_report_id"
						+ " LEFT JOIN `tag` AS t ON t.id = r.tag_id"
						+ " WHERE 1=1 "
						+ sb.toString()
						+ " GROUP BY type,be_report_id )  AS abc  )"
						,array1);
		page.setTotalResult(count);
		list.add(page.getCurrentResult());
		list.add(page.getShowCount());
		array = list.toArray();
		List<Report> reportList = dao
				.findForList(
						  " SELECT r.type,r.be_report_id AS beReportId,"
						+ " CASE r.type WHEN 1 THEN m.`name` WHEN 2 THEN IF(s.author_type=0,m.`name`,a.`name`) WHEN 3 THEN IF(f.author_type=0,m.`name`,a.`name`) END AS `name`,"
						+ " CASE r.type WHEN 1 THEN i.title WHEN 2 THEN s.title WHEN 3 THEN f.title END AS `title`,"
						+ " GROUP_CONCAT(DISTINCT t.tag) AS tag,"
						+ " COUNT(*) AS reportTimes "
						+ " FROM `report` AS r "
						+ " LEFT JOIN `member` AS m ON r.release_id = m.id"
						+ " LEFT JOIN `admin` AS a ON r.release_id = a.id"
						+ " LEFT JOIN `script_factory` AS s ON s.id = r.be_report_id"
						+ " LEFT JOIN `micro_film` AS f ON f.id = r.be_report_id"
						+ " LEFT JOIN `story_island` AS i ON i.id = r.be_report_id"
						+ " LEFT JOIN `tag` AS t ON t.id = r.tag_id"
						+ " WHERE 1=1 "
						+ sb.toString()
						+ " GROUP BY type,be_report_id ORDER BY COUNT(*)  DESC  LIMIT ?, ? " ,
						Report.class, array);
		map.put("page", page);
		map.put("reportList", reportList);
		return map;
	}
	
	/**
	 * 删除举报记录
	 * @param type
	 * @param beReportId
	 */
	public void deleteReport(String type, String beReportId){
		dao.executeByParams(" DELETE FROM `report` WHERE type = ? AND be_report_id = ? ", type,beReportId);
	}
}
