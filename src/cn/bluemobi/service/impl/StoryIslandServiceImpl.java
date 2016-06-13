package cn.bluemobi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.dao.BaseDao;
import cn.bluemobi.entity.Cover;
import cn.bluemobi.entity.StoryHitSort;
import cn.bluemobi.entity.StoryIsland;
import cn.bluemobi.entity.StoryIslandChapter;
import cn.bluemobi.entity.system.Page;
import cn.bluemobi.service.StoryIslandService;
import cn.bluemobi.util.helper.ValidateHelper;
import cn.bluemobi.util.text.TextHelper;
/**
 * 故事岛
 * @author xiazf
 *
 */
@Service
public class StoryIslandServiceImpl implements StoryIslandService {
	@Autowired
	private BaseDao dao;
	/**
	 * 获取故事岛列表
	 * @param memberId
	 * @param type 
	 * @param choiceness 
	 * @return
	 */
	public List<StoryIsland> getStoryIslandList(String memberId, String choiceness, String classifyId,String type, Page page){
		String sql = "";
		if(!TextHelper.isNullOrEmpty(memberId)){
			sql += " and s.release_id  =  "+memberId;
		}
		if(!TextHelper.isNullOrEmpty(choiceness)){
			sql += " and s.choiceness  =  "+choiceness;
		}
		if(!TextHelper.isNullOrEmpty(classifyId)){
			sql += " and s.classify_id  =  "+classifyId;
		}
	
		if(TextHelper.isNullOrEmpty(memberId)){
			sql += " AND s.status = '1' AND EXISTS(SELECT * FROM story_island_chapter AS c WHERE s.id = c.story_island_id AND c.`status` = 1  ) ";
		}
		if(!TextHelper.isNullOrEmpty(memberId) && "2".equals(type)){
			sql += " AND s.status = '1' AND EXISTS(SELECT * FROM story_island_chapter AS c WHERE s.id = c.story_island_id AND c.`status` = 1 ) AND s.is_anonymous = 0 ";
		}
		if("1".equals(type)){
			sql += " ORDER BY hot DESC ";
		}else{
			sql += " ORDER BY s.release_time DESC ";
		}
		if (page.getPageCount() == -1) {
			dao.findPageSum("SELECT COUNT(*) FROM story_island AS s where  1=1  "+sql, page);
			if (page.getTotalCount() == 0) {
				return null;
			}
		}
		return dao.findByPage("SELECT s.id,s.title,s.release_id AS releaseId,s.classify_id AS classifyId,s.cover,m.`name` AS `author`,DATE_FORMAT(s.release_time,'%Y-%m-%d %H:%i:%s') AS releaseTime,DATE_FORMAT(s.update_time,'%Y-%m-%d %H:%i:%s') AS updateTime,s.hot "
				+",IF( mf.`status`=1,r.relate_film_id,NULL) AS relateFilmId,IF(sf.`status`=1,r.relate_script_id,NULL) AS relateScriptId,s.chapter_type AS chapterType,s.is_anonymous AS isAnonymous,s.anonymous_name AS anonymousName,s.anonymous_icon AS anonymousIcon "
				+ " FROM `story_island` AS s "
				+ " LEFT JOIN member AS m ON m.id=s.release_id "
				+ " LEFT JOIN `relate`AS r ON r.relate_story_id = s.id "
				+ " LEFT JOIN `micro_film` AS mf ON mf.id = r.relate_film_id "
				+ " LEFT JOIN `script_factory` AS sf ON sf.id = r.relate_script_id "
				+ " WHERE 1 = 1 "+sql,page,StoryIsland.class);
	}
	/**
	 * 创建故事
	 * @param title
	 * @param cover
	 * @param memberId
	 * @return
	 */
	public Long create(StoryIsland si){
		return dao.saveGetKey(" INSERT INTO `story_island` SET title=:title,cover=:cover,release_id=:releaseId,status=:status,release_time=NOW(),update_time=NOW(),classify_id=:classifyId,chapter_type=:chapterType,is_anonymous=:isAnonymous,anonymous_icon=:anonymousIcon,anonymous_name=:anonymousName ",si);
	}
	/**
	 * 新增章节
	 * @param sic
	 */
	public Long createChapter(StoryIslandChapter sic,StoryIsland si){
			Long id = 0L;
			//如果没有传故事id，则为新增操作。
			if(TextHelper.isNullOrEmpty(si.getId())){
				id = create(si);
			}else{
				//传了id表示在已有故事上新增章节
				id=Long.valueOf(si.getId());
				
				//如果是发布则更新时间
				if("1".equals(si.getStatus())){
					StoryIsland st = getStoryIslandById(id.toString());
					if(!TextHelper.isNullOrEmpty(st) && "2".equals(st.getStatus())){
						return -1L;
					}else{
						updateUpdateTime(si.getId().toString(),"");
					}
				}
			}
					
			/*		
					sic.setId(Long.valueOf(chapterId));
			}
			//保存原始数据
			sic.setOldText(textStory);
			sic.setColor(color);
			sic.setStrong(strong);
			sic.setEm(em);
			sic.setU(u);
			sic.setTextAlign(textAlign);
			sic.setFont(font);
			sic.setImgList(imgList);
					*/
					
			
			sic.setStoryIslandId(id);
			//如果id为空,执行新增操作
			if(TextHelper.isNullOrEmpty(sic.getId())){
				//首先查找最大的排序
				Integer i = dao.findForInt("SELECT max(sort) from `story_island_chapter` WHERE   story_island_id =?  ", sic.getStoryIslandId());
				Integer sort = i+1;
				sic.setSort(sort);
				dao.executeByObject(" INSERT INTO `story_island_chapter` SET story_island_id=:storyIslandId,chapter_type=:chapterType,text_story=:textStory,img_story_url=:imgStoryUrl,voice_story_url=:voiceStoryUrl,video_story_url=:videoStoryUrl,img=:img,create_time=NOW(),"
						          + " `status`=:status,sort=:sort,paper=:paper,old_text=:oldText,color=:color,strong=:strong,em=:em,u=:u,text_align=:textAlign,font=:font,img_list=:imgList", sic);
			}else{//如果id不为空，则为编辑
				dao.executeByObject(" UPDATE `story_island_chapter` SET story_island_id=:storyIslandId,chapter_type=:chapterType,text_story=:textStory,create_time=NOW(),"
								  + " `status`=:status,paper=:paper,old_text=:oldText,color=:color,strong=:strong,em=:em,u=:u,text_align=:textAlign,font=:font,img_list=:imgList WHERE id=:id ", sic);
			}
			
			return id;
	}
	/**
	 * 更新时间
	 */
	public void updateUpdateTime(String storyIslandId,String status){
		String sql = "";
		if("1".equals(status)){
			sql = " ,status = 1 ";
		}
		dao.executeByParams(" UPDATE `story_island` SET update_time=now() "+sql+" WHERE id = ? ", storyIslandId);
		
			
	}
	/**
	 * 获取用户故事list
	 * @param memberId
	 * @return
	 */
	public List<StoryIsland> getStoryList(String memberId){
		return dao.findForList("SELECT s.id,s.title FROM `story_island` AS s WHERE s.release_id = ? AND s.`status` = 1 AND s.is_anonymous = 0 AND EXISTS(SELECT * FROM story_island_chapter AS c WHERE s.id = c.story_island_id AND c.`status` = 1  ) ", StoryIsland.class, memberId);
	}
	/**
	 * 删除章节
	 * @param chapterId
	 */
	public void deleteStoryIslandChapter(String chapterId,String id){
		Integer i =  dao.findForInt("SELECT COUNT(*) FROM `story_island_chapter` WHERE story_island_id=? ", id);
		dao.executeByParams("DELETE FROM `story_island_chapter` WHERE id=? ", chapterId);
		if(i==1){
			dao.executeByParams("DELETE FROM `story_island` WHERE id=? ", id);
			//关联表里 故事的id清空
			dao.executeByParams("UPDATE relate SET relate_story_id=null WHERE relate_story_id=?  ", id);
		}
	}
	/**
	 * 查询章节信息
	 * @param chapterId
	 * @return
	 */
	public StoryIslandChapter getChapterById(String chapterId){
		return dao.findForObject("SELECT s.id,s.story_island_id AS storyIslandId,s.chapter_type AS chapterType,s.text_story AS textStory,s.img_story_url AS imgStoryUrl,s.voice_story_url AS voiceStoryUrl,s.video_story_url AS videoStoryUrl,s.img,s.`status`,s.sort FROM `story_island_chapter` AS s WHERE id = ? ", StoryIslandChapter.class,chapterId);
	}
	/**
	 * 查询故事的章节list
	 */
	public List<StoryIslandChapter> getALLChapter(String id,String type){
		String sql = "";
		if(!TextHelper.isNullOrEmpty(type)){
			sql  += " AND `status` =  1 ";
		}
		return dao.findForList("SELECT s.id,s.story_island_id AS storyIslandId,s.chapter_type AS chapterType,s.text_story AS textStory,s.img_story_url AS imgStoryUrl,s.voice_story_url AS voiceStoryUrl,s.video_story_url AS videoStoryUrl,s.img,s.`status`,s.sort,s.old_text AS oldText FROM `story_island_chapter` AS s WHERE story_island_id = ? "+sql+" ORDER BY s.sort asc ", StoryIslandChapter.class,id);
	}
	
	/**
	 * 排序
	 * @param id
	 * @param i
	 */
	public void updateSort(String id, int i){
		dao.executeByParams("UPDATE `story_island_chapter` SET sort = ? WHERE id=? ", i,id);
	}
	/**
	 * 获取故事
	 * @param storyId
	 * @return
	 */
	public StoryIsland getStoryIsland(String storyId,String memberId,String type){
		
		String sql ="";
		String whsql ="";
		//用户id
		if(!TextHelper.isNullOrEmpty(memberId)){
			sql += " (SELECT COUNT(*) FROM `subscibe` AS b WHERE b.story_island_id = s.id   AND b.member_id = "+memberId+" ) AS isSubscibe, ";
			
			sql += " (SELECT COUNT(*) FROM `praise` AS p WHERE p.be_praise_id = s.id AND p.type = 1 AND p.praise_id =  "+memberId+" ) AS isPraise, ";
		}
		if(!TextHelper.isNullOrEmpty(type)){
			whsql = " AND s.status = 1 ";
		}
		return dao.findForObject(" SELECT "
				+ " s.id,s.title,s.release_id AS releaseId, s.cover, s.`classify_id` AS classifyId, "
				+ " s.is_anonymous AS isAnonymous,s.anonymous_name AS anonymousName,s.anonymous_icon AS anonymousIcon, "
				+ " m.`name` AS author, m.icon,r.relate_film_id AS relateFilmId,r.relate_script_id AS relateScriptId,"
				+ " DATE_FORMAT(s.release_time,'%Y-%m-%d %H:%i:%s') AS releaseTime, "
				+ " DATE_FORMAT(s.update_time,'%Y-%m-%d %H:%i:%s') AS updateTime, "
				+sql
				+ " (SELECT COUNT(*) FROM `comment` AS c WHERE c.be_comment_id = s.id AND c.type = 1  ) AS commentNum, "
				+ " s.hot FROM `story_island` AS s "
				+ " LEFT JOIN member AS m ON m.id = s.release_id "
				+ " LEFT JOIN `relate`AS r ON r.relate_story_id = s.id "
				+ " WHERE  s.id = ? " +whsql, StoryIsland.class, storyId);
	}
	
	/**
	 * 故事岛封面
	 * @return
	 */
	public List<Cover> coverList(){
		return dao.findForList(" SELECT c.id,c.cover FROM `cover` AS c ORDER BY c.create_time DESC  ", Cover.class);
	}
	/**
	 * 增加点赞数量
	 * @param bePraiseId
	 */
	public void addStoryHot(Long bePraiseId){
		dao.executeByParams(" UPDATE `story_island` SET hot := hot+1 WHERE id = ? ", bePraiseId);
	}
	/**
	 * 减少点赞数量
	 * @param bePraiseId
	 */
	public void deleteStoryHot(Long bePraiseId){
		dao.executeByParams(" UPDATE `story_island` SET hot := IF(hot-1<0,0,hot-1) WHERE id = ? ", bePraiseId);
	}
	
	/**
	 * 后台分页查找故事
	 * @param page
	 * @param storyIsland
	 * @return
	 */
	public Map<String, Object> getStoryIslandListByPage(cn.bluemobi.entity.Page page, StoryIsland storyIsland){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		Object[] array = null;
		Object[] array1 = null;
		if (!ValidateHelper.isNullOrEmpty(storyIsland.getTitle())) {
			sb.append(" and s.title like ?");
			list.add('%' + storyIsland.getTitle() + '%');
		}
		if (!ValidateHelper.isNullOrEmpty(storyIsland.getAuthor())) {
			sb.append(" and m.name like ?  ");
			list.add('%' + storyIsland.getAuthor() + '%');
		}
		if (!ValidateHelper.isNullOrEmpty(storyIsland.getChoiceness())) {
			if("1".equals(storyIsland.getChoiceness())){
				sb.append(" and s.choiceness = ?  ");
				list.add( storyIsland.getChoiceness());
			}
		}
		if (!ValidateHelper.isNullOrEmpty(storyIsland.getClassifyId())) {
				sb.append(" and s.classify_id = ?  ");
				list.add( storyIsland.getClassifyId());
		}
		if (!ValidateHelper.isNullOrEmpty(storyIsland.getStatus())) {
			sb.append(" and s.`status` = ?  ");
			list.add( storyIsland.getStatus());
		}

		// 注册日期
		if (!ValidateHelper.isNullOrEmpty(storyIsland.getReleaseTime())) {
			sb.append(" and DATE_FORMAT(s.release_time, '%Y-%m-%d') =  DATE_FORMAT( ?, '%Y-%m-%d')");
			list.add(storyIsland.getReleaseTime());
		}
		String sql = sb.toString();
		array1 = list.toArray();
		if("0".equals(storyIsland.getType())){
			sb.append(" ORDER BY s.release_time DESC  LIMIT ?, ? ");
		}else{
			sb.append(" ORDER BY hot DESC,s.release_time DESC  LIMIT ?, ? ");
		}
		Integer count = dao.findForInt(" SElECT COUNT(*) FROM `story_island` s LEFT JOIN `member` AS m ON m.id= s.release_id WHERE 1 = 1 " + sql,array1);
		page.setTotalResult(count);
		list.add(page.getCurrentResult());
		list.add(page.getShowCount());
		array = list.toArray();
		List<StoryIsland> storyIslandList = dao
				.findForList(
						" SELECT s.id,s.title,s.release_id AS releaseId,s.author,DATE_FORMAT(s.release_time, '%Y-%m-%d %H:%i:%s') AS releaseTime, DATE_FORMAT(s.update_time, '%Y-%m-%d %H:%i:%s') AS updateTime,"
						+ " s.choiceness,s.hot,s.`status`,s.cover, m.`name` AS author,s.classify_id AS classifyId, c.classify "
						+ " FROM `story_island` s  "
						+ " LEFT JOIN `member` AS m ON m.id= s.release_id "
						+ " LEFT JOIN `story_island_classify` AS c ON  c.id = s.classify_id "
						+ " WHERE 1 = 1 " + sb.toString(),
						StoryIsland.class, array);
		map.put("page", page);
		map.put("storyIslandList", storyIslandList);
		return map;
	}
	
	/**
	 * 更改状态
	 * @param idArr
	 * @param status
	 */
	public void updateStatus(String[] idArr, String status){
		String[] sql = new String[100];
		for (int i = 0; i < idArr.length; i++) {
			sql[i] = " UPDATE `story_island` SET `status` = "+status+" WHERE id = "+idArr[i];
		}
		dao.executeBatch(sql);
	}
	
	/**
	 * 故事章节列表
	 * @param page
	 * @param id
	 * @return
	 */
	public Map<String, Object> getChapterListByPage(cn.bluemobi.entity.Page page, String id){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> list = new ArrayList<Object>();
		Object[] array = null;
		Integer count = dao.findForInt(" SElECT COUNT(*) FROM `story_island_chapter` s WHERE story_island_id = ?  ",id);
		page.setTotalResult(count);
		list.add(id);
		list.add(page.getCurrentResult());
		list.add(page.getShowCount());
		array = list.toArray();
		List<StoryIslandChapter> chapterList = dao
				.findForList(
						" SELECT s.id,s.story_island_id AS storyIslandId,s.chapter_type AS chapterType,s.text_story AS textStory,s.img_story_url AS imgStoryUrl,s.voice_story_url AS voiceStoryUrl,s.video_story_url AS videoStoryUrl,s.`status`,s.sort,DATE_FORMAT(s.create_time,'%Y-%m-%d %H:%i:%s') AS createTime FROM `story_island_chapter` AS s WHERE story_island_id = ? ORDER BY s.sort asc LIMIT ?, ?   ",
						StoryIslandChapter.class,array);
		map.put("page", page);
		map.put("chapterList", chapterList);
		return map;
	}
	
	/**
	 * 根据id查找故事岛
	 * @param id
	 */
	public StoryIsland getStoryIslandById(String id){
		return dao.findForObject(" SELECT s.id,s.title,s.release_id AS releaseId,m.`name` AS `author`,DATE_FORMAT(s.release_time,'%Y-%m-%d %H:%i:%s') AS releaseTime,DATE_FORMAT(s.update_time,'%Y-%m-%d %H:%i:%s') AS updateTime,s.hot,s.cover,s.choiceness,s.`status` FROM `story_island` AS s LEFT JOIN member AS m ON m.id=s.release_id WHERE  s.id =?  ",StoryIsland.class,id);
	}
	/**
	 * 更改精选状态和热度
	 * @param id
	 * @param choiceness
	 */
	public void updateChoicenessAndHot(String id, String choiceness, String hot){
		dao.executeByParams(" UPDATE `story_island` SET choiceness = ?,hot = ? WHERE id = ? ", choiceness,hot,id);
	}
	
	/**
	 * 删除故事
	 * @param id
	 */
	public void deleteStoryIsland(String id){
		dao.executeByParams(" DELETE FROM `story_island` WHERE id=? ", id);
		//删除章节
		dao.executeByParams(" DELETE FROM `story_island_chapter` WHERE story_island_id=? ", id);
		//关联表里 故事的id清空
		dao.executeByParams(" UPDATE relate SET relate_story_id=null WHERE relate_story_id=?  ", id);
		//删除评论
		dao.executeByParams(" DELETE FROM `comment` WHERE be_comment_id = ? AND type = 1 ", id);
	}
	/**
	 * 故事岛章节编辑
	 * @param sic
	 */
	public void updateStoryIslandChapter(StoryIslandChapter sic){
		dao.executeByParams(" UPDATE `story_island_chapter` SET `status` = ? ,create_time = NOW() "
				+ " WHERE id = ? ",sic.getStatus(),sic.getId());
	}
	
	/**
	 * 我的关注 -故事列表
	 * @param memberId
	 * @param page
	 * @return
	 */
	public List<StoryIslandChapter> getAttentionStoryList(String memberId,Page page){

		
		if (page.getPageCount() == -1) {
			dao.findPageSum(" SELECT COUNT(*) FROM `story_island_chapter` WHERE "
					+ " story_island_id IN (  "
					+ " SELECT id FROM `story_island` WHERE release_id IN  "
					+ " (SELECT be_attention_id FROM `attention` WHERE attention_id = ? AND be_attention_id IN ( "
					+ " SELECT attention_id FROM `attention` WHERE be_attention_id = ? ) ) "
					+ " )  AND `status` = 1  ", page,memberId,memberId);
			if (page.getTotalCount() == 0) {
				return null;
			}
		}
		
		return dao.findByPage(" SELECT c.id,c.story_island_id AS storyIslandId,c.chapter_type AS chapterType,c.text_story AS textStory,c.img_story_url AS imgStoryUrl,c.video_story_url AS videoStoryUrl,c.img,"
				+ " r.relate_film_id AS relateFilmId,r.relate_script_id AS relateScriptId,s.is_anonymous AS isAnonymous,s.anonymous_name AS anonymousName,s.anonymous_icon AS anonymousIcon,"
				+ " c.voice_story_url AS voiceStoryUrl,DATE_FORMAT(c.`create_time`,'%Y-%m-%d %H:%i:%s') AS createTime, m.`name` AS author,m.icon,s.title,s.release_id AS releaseId,"
				+ " (SELECT COUNT(*) FROM `praise` AS p WHERE p.be_praise_id = c.story_island_id ) AS praiseNum,"
				+ " (SELECT COUNT(*) FROM `praise` AS p WHERE p.be_praise_id = c.story_island_id AND p.type = 1 AND p.praise_id =  ? ) AS isPraise  "
				+ " FROM `story_island_chapter` AS c "
				+ " LEFT JOIN `story_island` AS s ON s.id = c.story_island_id "
				+ " LEFT JOIN `member` AS m ON s.release_id =m.id "
				+ " LEFT JOIN `relate`AS r ON r.relate_story_id = c.story_island_id "
				+ " WHERE "
				+ " story_island_id IN ( "
				+ " SELECT id FROM `story_island` WHERE release_id IN "
				+ " (SELECT be_attention_id FROM `attention` WHERE attention_id = ? AND be_attention_id IN ( "
				+ "  SELECT attention_id FROM `attention` WHERE be_attention_id = ? )) "
				+ " ) AND c.`status` = 1 ORDER BY c.create_time DESC ",page,StoryIslandChapter.class,memberId,memberId,memberId);
	}
	
	/**
	 * 修改封面和标题
	 * @param si
	 */
	public void updateUpdateTime(StoryIsland si){
		dao.executeByObject(" UPDATE  `story_island` SET `title`=:title,`cover`=:cover,`classify_id`=:classifyId WHERE `id`=:id", si);
	}
	/**
	 * 查找故事列表
	 * @param memberId
	 * @param keywords
	 * @param page
	 * @return
	 */
	public List<StoryIsland> getStoryIslandListBySearch(String keywords, Page page){
		String sql = "";
		List<String> list = new ArrayList<>();
		if(!TextHelper.isNullOrEmpty(keywords)){
			sql += " AND s.title like ? ";
			list.add("%"+keywords+"%");
		}
		
		if (page.getPageCount() == -1) {
			dao.findPageSum(" SELECT COUNT(*) FROM `story_island` AS s WHERE s.`status`= 1 "+sql, page,list.toArray());
			if (page.getTotalCount() == 0) {
				return null;
			}
		}
		sql += " ORDER BY s.hot desc";
		return dao.findByPage(" SELECT s.id,s.title,s.cover,s.hot,(SELECT COUNT(*) FROM `comment` AS c WHERE c.be_comment_id = s.id AND c.type = 1  ) AS commentNum,DATE_FORMAT(s.release_time,'%Y-%m-%d %H:%i:%s') AS releaseTime "
				+" ,IF( mf.`status`=1,r.relate_film_id,NULL) AS relateFilmId,IF(sf.`status`=1,r.relate_script_id,NULL) AS relateScriptId "
				+ " FROM `story_island` AS s "
				+ " LEFT JOIN `relate`AS r ON r.relate_story_id = s.id "
				+ " LEFT JOIN `micro_film` AS mf ON mf.id = r.relate_film_id "
				+ " LEFT JOIN `script_factory` AS sf ON sf.id = r.relate_script_id "
				+ " WHERE s.`status` = 1 "+sql, page,StoryIsland.class, list.toArray());
	}
	
	/**
	 * 添加点击数
	 * @param memberId
	 * @param classifyId
	 */
	public void addClassifyHit(String memberId, String classifyId){
		StoryHitSort shs = dao.findForObject(" SELECT * FROM `story_hit_sort` WHERE member_id = ? AND classify_id = ?  ", StoryHitSort.class, memberId,classifyId);
		if(TextHelper.isNullOrEmpty(shs)){
			dao.executeByParams(" INSERT INTO `story_hit_sort` SET member_id=?,classify_id = ?,hit=0 ", memberId,classifyId );
		}else{
			dao.executeByParams(" UPDATE `story_hit_sort` SET hit := hit+1 WHERE member_id = ? AND classify_id = ?   ", memberId,classifyId);
		}
	}
	/**
	 * 获取故事章节信息
	 * @param chapterId
	 * @return
	 */
	public StoryIslandChapter getTextStory(String chapterId){
		return dao.findForObject(" SELECT id,story_island_id AS storyIslandId,chapter_type AS chapterType,text_story AS textStory,paper AS paper,`status` AS status,"
							   + " old_text AS oldText,color AS color,strong AS strong,em AS em,u AS u,text_align AS textAlign,font AS font,img_list AS imgList FROM `story_island_chapter` WHERE id = ?  " ,StoryIslandChapter.class, chapterId);
	}
	
}
