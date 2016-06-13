package cn.bluemobi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.dao.BaseDao;
import cn.bluemobi.entity.MicroFilm;
import cn.bluemobi.entity.Page;
import cn.bluemobi.service.MicroFilmService;
import cn.bluemobi.util.helper.ValidateHelper;
import cn.bluemobi.util.text.TextHelper;

/**
 * 微电影
 * @author xiazf
 *
 */
@Service
public class MicroFilmServiceImpl implements MicroFilmService {
	@Autowired
	private BaseDao dao;
	/**
	 * 用户微电影
	 * @param memberId
	 * @return
	 */
	public List<MicroFilm> getMicroFilmList(String memberId){
		return dao.findForList("SELECT id,title FROM `micro_film` WHERE release_id = ? AND `status` = 1 AND `author_type` = 0 ", MicroFilm.class, memberId);

	}
	/**
	 * 获取电影分页显示列表
	 * @param page
	 * @param microFilm
	 * @return
	 */
	public Map<String, Object> getMicroFilmListByPage(Page page,MicroFilm microFilm){
 		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		Object[] array = null;
		Object[] array1 = null;
		if (!ValidateHelper.isNullOrEmpty(microFilm.getTitle())) {
			sb.append(" AND title like ?");
			list.add('%' + microFilm.getTitle() + '%');
		}
		if (!ValidateHelper.isNullOrEmpty(microFilm.getAuthor())) {
			sb.append(" AND (f.author_type=1 AND a.name like ?) OR (f.author_type=0 AND m.name like ?) ");
			list.add('%' + microFilm.getAuthor() + '%');
			list.add('%' + microFilm.getAuthor() + '%');
		}
		//1是影院 0是原创
		if (!ValidateHelper.isNullOrEmpty(microFilm.getFilmType())) {
			sb.append(" AND film_type = ?  ");
			list.add( microFilm.getFilmType());
		}
		// 发布日期
		if (!ValidateHelper.isNullOrEmpty(microFilm.getReleaseTime())) {
			sb.append(" AND DATE_FORMAT(release_time, '%Y-%m-%d') =  DATE_FORMAT( ?, '%Y-%m-%d')");
			list.add(microFilm.getReleaseTime());
		}
		String sql = sb.toString();
		array1 = list.toArray();
		sb.append(" ORDER BY play_count DESC  LIMIT ?, ? ");
		
		Integer count = dao.findForInt(" SElECT COUNT(*) FROM `micro_film` f"
				+ " LEFT JOIN `member` AS m ON m.id = f.release_id "
				+ " LEFT JOIN `admin` AS a ON a.id = f.release_id "
				+ " WHERE 1 = 1 " + sql,array1);
		page.setTotalResult(count);
		list.add(page.getCurrentResult());
		list.add(page.getShowCount());
		array = list.toArray();
		List<MicroFilm> microFilmList = dao
				.findForList(
						" SELECT f.id,f.title,f.release_id AS releaseId,f.theme,f.film_type AS filmType,DATE_FORMAT(f.release_time, '%Y-%m-%d %H:%i:%s') AS releaseTime, f.director,f.scriptwriter,f.actor,f.content,f.cover,f.vedio_url AS vedioUrl,f.play_count AS playCount,f.`status`"
						+ " ,CASE WHEN f.author_type =0 THEN m.`name` WHEN f.author_type =1 THEN a.`name` END AS author "
						+ " FROM `micro_film` f "
						+ " LEFT JOIN `member` AS m ON m.id = f.release_id "
						+ " LEFT JOIN `admin` AS a ON a.id = f.release_id "
						+ " WHERE 1 = 1 " + sb.toString(),
						MicroFilm.class, array);
		map.put("page", page);
		map.put("microFilmList", microFilmList);
		return map;
	}
	
	/**
	 * 更改微电影状态
	 * @param idArr
	 * @param status
	 */
	public void updateStatus(String[] idArr, String status){
		String[] sql = new String[100];
		for (int i = 0; i < idArr.length; i++) {
			sql[i] = " UPDATE `micro_film` SET `status` = "+status+" WHERE id = "+idArr[i];
		}
		dao.executeBatch(sql);
	}
	/**
	 * 删除电影
	 * @param id
	 */
	public void deleteMicroFilm(String id){
		dao.executeByParams(" DELETE FROM `micro_film` WHERE id=? ", id);
		//关联表里 电影的id清空
		dao.executeByParams(" UPDATE `relate` SET relate_film_id=null WHERE relate_film_id = ?  ", id);
		//删除评论
		dao.executeByParams(" DELETE FROM `comment` WHERE be_comment_id = ? AND type = 2 ", id);
	}
	/**
	 * 根据id获取电影详情
	 * @param id
	 */
	public MicroFilm getMicroFilmById(String id){
		return dao.findForObject(" SELECT f.id,f.title,f.release_id AS releaseId,f.theme,f.film_type AS filmType,"
				+ " DATE_FORMAT(f.release_time, '%Y-%m-%d %H:%i:%s') AS releaseTime, f.director,f.scriptwriter,f.actor,f.content,f.cover,f.vedio_url AS vedioUrl,"
				+ " f.play_count AS playCount,f.`status`,r.relate_story_id AS relateStoryId,r.relate_script_id AS relateScriptId "
				+ " ,CASE WHEN f.author_type =0 THEN m.`name` WHEN f.author_type =1 THEN a.`name` END AS author "
				+ " FROM `micro_film` f "
				+ " LEFT JOIN `member` AS m ON m.id = f.release_id "
				+ " LEFT JOIN `admin` AS a ON a.id = f.release_id "
				+ " LEFT JOIN `relate`AS r ON r.relate_film_id = f.id "
				+ " WHERE  f.id = ?  ",MicroFilm.class, id);
	}
	/**
	 * 保存微电影编辑
	 * @param mf
	 */
	public void save(MicroFilm mf){
		if(!TextHelper.isNullOrEmpty(mf.getId())){
			//编辑
			dao.executeByObject(" UPDATE `micro_film` SET `title`=:title,theme=:theme,film_type=:filmType,director=:director,scriptwriter=:scriptwriter,actor=:actor,content=:content,cover=:cover,vedio_url=:vedioUrl,play_count=:playCount WHERE id=:id ", mf);
		}else{
			mf.setFilmType("1");
			dao.executeByObject(" INSERT INTO `micro_film` SET `title`=:title,release_id=:releaseId,theme=:theme,film_type=:filmType,director=:director,scriptwriter=:scriptwriter,actor=:actor,content=:content,cover=:cover,vedio_url=:vedioUrl,play_count=:playCount,release_time=NOW(),`author_type`=1 ", mf);
		}
	}
	
	/**
	 * 手机端微电影列表
	 * @param memberId
	 * @param page
	 * @return
	 */
	public List<MicroFilm> getMicroFilmListByPage(String filmType,String memberId, String type,String myId,cn.bluemobi.entity.system.Page page){
		String sql = "";
		String sql1 = "";
		if(!TextHelper.isNullOrEmpty(filmType)){
			sql += " AND m.film_type=  "+filmType;
		}
		
		if(!TextHelper.isNullOrEmpty(memberId) && TextHelper.isNullOrEmpty(type)){
			sql += " and m.release_id  =  "+memberId+" AND m.author_type = 0 ";
			if(TextHelper.isNullOrEmpty(myId)){
				sql1 = " ,( SELECT COUNT(*) FROM praise AS p WHERE p.type = '2' AND p.be_praise_id = m.id AND p.praise_id = "+memberId+" ) AS isPraise ";
			}else{
				sql1 = " ,( SELECT COUNT(*) FROM praise AS p WHERE p.type = '2' AND p.be_praise_id = m.id AND p.praise_id = "+myId+" ) AS isPraise ";
			}
		}else if(!TextHelper.isNullOrEmpty(memberId) && !TextHelper.isNullOrEmpty(type)){
			sql1 = " ,( SELECT COUNT(*) FROM praise AS p WHERE p.type = '2' AND p.be_praise_id = m.id AND p.praise_id = "+memberId+" ) AS isPraise ";
		}
		sql += " ORDER BY m.play_count DESC  ";
		
		if (page.getPageCount() == -1) {
			dao.findPageSum("SELECT COUNT(*) FROM `micro_film` AS m where m.status = '1'  "+sql, page);
			if (page.getTotalCount() == 0) {
				return null;
			}
		}
		return dao.findByPage(" SELECT m.id,m.title,m.release_id AS releaseId,m.theme,m.film_type AS filmType,"
				+ " DATE_FORMAT(m.release_time,'%Y-%m-%d %H:%i:%s') AS releaseTime,"
				+ " m.director,m.scriptwriter,m.actor,m.content,m.cover,m.vedio_url AS vedioUrl,"
				+ " m.play_count AS playCount,m.`status`,"
				+ " IF( si.`status`=1,r.relate_story_id,NULL) AS relateStoryId,IF(sf.`status`=1,r.relate_script_id,NULL) AS relateScriptId,"
				+ " m.`author_type`,"
				+ " (SELECT COUNT(*) FROM `comment` AS b WHERE b.type = 2 AND b.be_comment_id = m.id ) AS commentNum,"
				+ " (SELECT COUNT(*) FROM `praise` AS p WHERE p.type = 2 AND p.be_praise_id=m.id ) AS praiseNum "
				+sql1
				+ " FROM `micro_film` AS m "
				+ " LEFT JOIN `relate`AS r ON r.relate_film_id = m.id "
				+ " LEFT JOIN `story_island` AS si ON si.id = r.relate_story_id "
				+ " LEFT JOIN `script_factory` AS sf ON sf.id = r.relate_script_id "
				+ " WHERE m.status='1' "+sql,page,MicroFilm.class);
	}
	
	/**
	 * 增加播放次数
	 * @param filmId
	 */
	public void addFilmPlayCount(String filmId){
		dao.executeByParams(" UPDATE `micro_film` SET play_count := play_count+1 WHERE id = ? ", filmId);
	}
	
	/**
	 * 查找电影详情
	 * @param filmId
	 * @return
	 */
	public MicroFilm getMicroFilmForApp(String filmId,String memberId){
		String sql ="";
		if(!TextHelper.isNullOrEmpty(memberId)){
			sql = " ,( SELECT COUNT(*) FROM praise AS p WHERE p.type = '2' AND p.be_praise_id = m.id AND p.praise_id = "+memberId+" ) AS isPraise ";
			sql += " ,IF((SELECT COUNT(*) FROM `attention` AS t WHERE t.`attention_id` = "+memberId+" AND t.be_attention_id = m.release_id)>=1,1,0) AS isAttention ";
		}
		return dao.findForObject(" SELECT m.id,m.title,m.release_id AS releaseId,m.theme,m.film_type AS filmType,DATE_FORMAT(m.release_time,'%Y-%m-%d %H:%i:%s') AS releaseTime,m.director,m.scriptwriter,m.actor,m.content,m.cover,m.vedio_url,m.play_count AS playCount,m.author_type AS authorType"
				+ ", CASE WHEN m.author_type =0 THEN a.`name` END AS author  "
				+ ", CASE WHEN m.author_type =0 THEN a.`icon` END AS icon  "
				+ sql
				+ " ,(SELECT COUNT(*) FROM `comment` AS c WHERE c.be_comment_id = m.id AND c.type = 2  ) AS commentNum "
				+ " FROM `micro_film` AS m "
				+ " LEFT JOIN `member` AS a ON a.id = m.release_id "
				+ " WHERE m.id = ? ", MicroFilm.class, filmId);
	}
	
	/**
	 * 用户新增电影
	 * @param title
	 * @param theme
	 * @param director
	 * @param scriptwriter
	 * @param actor
	 * @param content
	 * @param vedioUrl
	 * @param memberId
	 * @param cover
	 */
	public void addMicroFilm(String title, String theme, String director,
			String scriptwriter, String actor, String content, String vedioUrl,
			String memberId, String cover){
		dao.executeByParams(" INSERT INTO `micro_film` SET `title`= ? ,`theme` = ?,`director` = ?,"
				           + "`scriptwriter` = ?,`actor` = ?,`content` = ?,`vedio_url` = ?,`cover` = ?,`release_id`=?,`release_time`=NOW(),`film_type`=0,`author_type`= 0" 
				,title,theme,director,scriptwriter,actor,content,vedioUrl,cover,memberId);
	}
	
	/**
	 * 搜索查找电影列表
	 * @param keywords
	 * @param page
	 * @return
	 */
	public List<MicroFilm> getMicroFilmListBySearch(String keywords,cn.bluemobi.entity.system.Page page){
		String sql = "";
		List<String> list = new ArrayList<>();
		if(!TextHelper.isNullOrEmpty(keywords)){
			sql += " AND m.title like ? ";
			list.add("%"+keywords+"%");
		}
		if (page.getPageCount() == -1) {
			dao.findPageSum(" SELECT COUNT(*) FROM `micro_film` AS m WHERE m.`status`= 1 "+sql, page,list.toArray());
			if (page.getTotalCount() == 0) {
				return null;
			}
		}
		sql += " ORDER BY m.play_count  DESC ";
		return dao.findByPage(" SELECT m.id,m.title,m.cover,m.play_count AS playCount,m.author_type AS authorType,DATE_FORMAT(m.release_time,'%Y-%m-%d %H:%i:%s') AS releaseTime,"
				+ " IF( si.`status`=1,r.relate_story_id,NULL) AS relateStoryId,IF(sf.`status`=1,r.relate_script_id,NULL) AS relateScriptId,"
				+ " (SELECT COUNT(*) FROM `comment` AS c WHERE c.be_comment_id = m.id AND c.type = 2  ) AS commentNum,(SELECT COUNT(*) FROM `praise` AS p WHERE p.type = 2 AND p.be_praise_id=m.id ) AS praiseNum FROM `micro_film` AS m"
				+ " LEFT JOIN `relate`AS r ON r.relate_film_id = m.id "
				+ " LEFT JOIN `story_island` AS si ON si.id = r.relate_story_id "
				+ " LEFT JOIN `script_factory` AS sf ON sf.id = r.relate_script_id "
				+ " WHERE m.`status`= 1   "+sql, page,MicroFilm.class, list.toArray());
	
	}
}
