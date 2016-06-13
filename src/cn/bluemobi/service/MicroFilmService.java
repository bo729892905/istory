package cn.bluemobi.service;

import java.util.List;
import java.util.Map;

import cn.bluemobi.entity.MicroFilm;
import cn.bluemobi.entity.Page;

/**
 * 微电影
 * @author xiazf
 *
 */
public interface MicroFilmService {
	/**
	 * 用户微电影
	 * @param memberId
	 * @return
	 */
	public List<MicroFilm> getMicroFilmList(String memberId);
	/**
	 * 获取电影分页显示列表
	 * @param page
	 * @param microFilm
	 * @return
	 */
	public Map<String, Object> getMicroFilmListByPage(Page page,MicroFilm microFilm);
	/**
	 * 更改微电影状态
	 * @param idArr
	 * @param status
	 */
	public void updateStatus(String[] idArr, String status);
	/**
	 * 删除电影
	 * @param id
	 */
	public void deleteMicroFilm(String id);
	/**
	 * 根据id获取电影详情
	 * @param id
	 */
	public MicroFilm getMicroFilmById(String id);
	/**
	 * 保存微电影编辑
	 * @param mf
	 */
	public void save(MicroFilm mf);
	/**
	 * 手机端微电影列表
	 * @param type 
	 * @param memberId 
	 * @param type 
	 * @param myId 
	 * @param page
	 * @return
	 */
	public List<MicroFilm> getMicroFilmListByPage(String filmType, String memberId, String type, String myId, cn.bluemobi.entity.system.Page page);
	/**
	 * 增加播放次数
	 * @param filmId
	 */
	public void addFilmPlayCount(String filmId);
	/**
	 * 查找电影详情
	 * @param filmId
	 * @param memberId 
	 * @param memberId 
	 * @return
	 */
	public MicroFilm getMicroFilmForApp(String filmId, String memberId);
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
			String memberId, String cover);
	/**
	 * 搜索查找电影列表
	 * @param keywords
	 * @param page
	 * @return
	 */
	public List<MicroFilm> getMicroFilmListBySearch(String keywords,cn.bluemobi.entity.system.Page page);
	

}
