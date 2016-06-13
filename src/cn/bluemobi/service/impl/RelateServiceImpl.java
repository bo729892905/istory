package cn.bluemobi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.dao.BaseDao;
import cn.bluemobi.entity.Relate;
import cn.bluemobi.service.RelateService;
import cn.bluemobi.util.text.TextHelper;
/**
 * 关联表
 * @author xiazf
 *
 */
@Service
public class RelateServiceImpl implements RelateService {
	@Autowired
	private BaseDao dao;
	/**
	 * 增加关联
	 * @param storyIslandId
	 * @param microFilmId
	 * @param scriptFactoryId
	 * @param type
	 */
	public void addRelate(String storyIslandId, String microFilmId,String scriptFactoryId, String type){
		
		dao.executeByParams("UPDATE relate SET relate_script_id=null WHERE relate_script_id=? ",scriptFactoryId);
		dao.executeByParams("UPDATE relate SET relate_film_id=null   WHERE relate_film_id=? ",microFilmId);
		dao.executeByParams("UPDATE relate SET relate_story_id=null  WHERE relate_story_id=? ",storyIslandId);
		
		if(TextHelper.isNullOrEmpty(storyIslandId)){
			storyIslandId=null;
		}
		if(TextHelper.isNullOrEmpty(microFilmId)){
			microFilmId=null;
		}
		if(TextHelper.isNullOrEmpty(scriptFactoryId)){
			scriptFactoryId=null;
		}
		
		dao.executeByParams("INSERT INTO relate SET relate_story_id=?, relate_script_id=?,relate_film_id=?",storyIslandId,scriptFactoryId,microFilmId);
		
	}
	
	/**
	 * 获取脚本关联
	 * @param scriptId
	 * @return
	 */
	public Relate getRelateByScript(String scriptId){
		return dao.findForObject("SELECT r.id,r.relate_story_id AS relateStoryId,r.relate_script_id AS relateScriptId,r.relate_film_id AS relateFilmId FROM `relate`AS r WHERE r.relate_script_id=? ", Relate.class, scriptId);
	}
	/**
	 * 获取关联 故事id
	 * @param storyId
	 * @return
	 */
	public Relate getRelatebyStory(String storyId){
		return dao.findForObject("SELECT r.id,r.relate_story_id AS relateStoryId,r.relate_script_id AS relateScriptId,r.relate_film_id AS relateFilmId FROM `relate`AS r WHERE r.relate_story_id=? ", Relate.class, storyId);
	}
	/**
	 * 获取关联 电影id
	 * @param filmId
	 * @return
	 */
	public Relate getRelatebyFilm(String filmId){
		return dao.findForObject("SELECT r.id,r.relate_story_id AS relateStoryId,r.relate_script_id AS relateScriptId,r.relate_film_id AS relateFilmId FROM `relate`AS r WHERE r.relate_film_id=? ", Relate.class, filmId);
	}

}
