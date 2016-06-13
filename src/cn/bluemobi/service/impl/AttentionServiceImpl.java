package cn.bluemobi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.dao.BaseDao;
import cn.bluemobi.entity.Attention;
import cn.bluemobi.entity.system.Page;
import cn.bluemobi.service.AttentionService;
import cn.bluemobi.util.text.TextHelper;
/**
 * 关注
 * @author xiazf
 *
 */
@Service
public class AttentionServiceImpl implements AttentionService {
	@Autowired
	private BaseDao dao;
	/**
	 * 获取关注数量
	 * @param memberId
	 * @return
	 */
	public Integer getAttentionNum(String memberId){
		return dao.findForInt(" SELECT COUNT(*) FROM `attention`  AS a INNER JOIN member AS m ON m.id= a.be_attention_id  WHERE a.attention_id = ?", memberId);
	}
	/**
	 * 获取粉丝数量
	 * @param memberId
	 * @return
	 */
	public Integer getBeAttentionNum(String memberId,String fansTime){
		String sql = "";
		
		if(!TextHelper.isNullOrEmpty(fansTime)){
			sql = " AND DATE_FORMAT(a.create_time,'%Y-%m-%d %H:%i:%s') > DATE_FORMAT('"+fansTime+"','%Y-%m-%d %H:%i:%s') "  ;
		}
		return dao.findForInt("SELECT COUNT(*) FROM `attention` AS a INNER JOIN member AS m ON m.id= a.attention_id  WHERE a.be_attention_id = ? " + sql , memberId);
	}
	/**
	 * 关注 和粉丝列表
	 * @param memberId
	 * @param type
	 * @return
	 */
	public List<Attention> getAttention(String memberId, String type, Page page){
		String sql = "";
		String sqlCount="";
		if("1".equals(type)){//1关注 0粉丝
			sql = " SELECT a.attention_id AS attentionId,a.be_attention_id AS beAttentionId,m.icon,m.name,m.introduction FROM `attention` AS a INNER JOIN member AS m ON m.id= a.be_attention_id WHERE a.attention_id = ? ORDER BY a.create_time DESC ";
			sqlCount = " SELECT COUNT(*) FROM `attention` AS a INNER JOIN member AS m ON m.id= a.be_attention_id WHERE a.attention_id = ? ";
		}else{
			sql = " SELECT a.attention_id AS attentionId,a.be_attention_id AS beAttentionId,m.icon,m.name,m.introduction,IF((SELECT COUNT(*) FROM `attention` AS c WHERE c.attention_id = a.be_attention_id AND c.be_attention_id = a.attention_id)>0,1,0) AS attentionEachOther FROM `attention` AS a INNER JOIN member AS m ON m.id= a.attention_id WHERE a.be_attention_id = ? ORDER BY a.create_time DESC ";
			sqlCount = " SELECT COUNT(*) FROM `attention` AS a INNER JOIN member AS m ON m.id= a.attention_id WHERE a.be_attention_id = ?;";
		}
		if (page.getPageCount() == -1) {
			dao.findPageSum(sqlCount, page, memberId);
			if (page.getTotalCount() == 0) {
				return null;
			}
		}
		return dao.findByPage(sql,page, Attention.class, memberId);
	}

	/**
	 * 查询关注记录
	 * @param memberId
	 * @param beAttentionId
	 * @return
	 */
	public Attention getAttentionByMemberId(String myId,String memberId){
		return dao.findForObject(" SELECT a.attention_id AS attentionId,a.be_attention_id AS beAttentionId FROM `attention` AS a  WHERE attention_id = ? AND be_attention_id = ?  ", Attention.class, myId,memberId);
	}
	/**
	 * 添加关注
	 * @param memberId
	 * @param beAttentionId
	 */
	public void addAttention(String memberId, String beAttentionId){
		dao.executeByParams(" INSERT INTO `attention` SET attention_id = ? , be_attention_id = ? ,create_time = NOW() ", memberId,beAttentionId);
	}
	/**
	 * 取消关注
	 * @param memberId
	 * @param beAttentionId
	 */
	public void cancelAttention(String memberId, String beAttentionId){
		dao.executeByParams(" DELETE FROM `attention` WHERE  attention_id = ? AND be_attention_id = ? ", memberId,beAttentionId);
	}
	/**
	 * 获取关注的用户
	 * @param id
	 * @param string
	 * @return
	 */
	public List<String> getAttentionMember(String id){
		return dao.findForStringList(" SELECT a.attention_id FROM attention AS a WHERE a.be_attention_id = ? AND EXISTS( SELECT b.be_attention_id  FROM attention AS b  WHERE b.be_attention_id = a.attention_id AND b.attention_id = ? )", id,id);
	}
	/**
	 * 获取我的关注数量
	 * @param memberId
	 * @param attentionTime
	 * @return
	 */
	public Integer getAttentionStoryByTime(String memberId, String attentionTime){
		return dao.findForInt(" SELECT COUNT(*) FROM `story_island_chapter` WHERE "
					+ " story_island_id IN (  "
					+ " SELECT id FROM `story_island` WHERE release_id IN  "
					+ " (SELECT be_attention_id FROM `attention` WHERE attention_id = ? AND be_attention_id IN ( "
					+ " SELECT attention_id FROM `attention` WHERE be_attention_id = ? ) ) "
					+ " ) AND `status` = 1 "
					+ " AND DATE_FORMAT(create_time,'%Y-%m-%d %H:%i:%s') > DATE_FORMAT(?,'%Y-%m-%d %H:%i:%s') ", memberId,memberId,attentionTime);
	}
}
