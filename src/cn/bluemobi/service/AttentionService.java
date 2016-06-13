package cn.bluemobi.service;

import java.util.List;

import cn.bluemobi.entity.Attention;
import cn.bluemobi.entity.system.Page;

/**
 * 关注
 * @author xiazf
 *
 */
public interface AttentionService {
	/**
	 * 获取关注数量
	 * @param memberId
	 * @return
	 */
	public Integer getAttentionNum(String memberId);
	/**
	 * 获取粉丝数量
	 * @param memberId
	 * @param fansTime 
	 * @return
	 */
	public Integer getBeAttentionNum(String memberId, String fansTime);
	/**
	 * 关注 和粉丝列表
	 * @param memberId
	 * @param type
	 * @param page 
	 * @return
	 */
	public List<Attention> getAttention(String memberId, String type, Page page);
	/**
	 * 查询关注记录
	 * @param memberId
	 * @param beAttentionId
	 * @return
	 */
	public Attention getAttentionByMemberId(String memberId,String beAttentionId);
	/**
	 * 添加关注
	 * @param memberId
	 * @param beAttentionId
	 */
	public void addAttention(String memberId, String beAttentionId);
	/**
	 * 取消关注
	 * @param memberId
	 * @param beAttentionId
	 */
	public void cancelAttention(String memberId, String beAttentionId);
	/**
	 * 获取关注的用户
	 * @param id
	 * @return
	 */
	public List<String> getAttentionMember(String id);
	/**
	 * 获取我的关注数量
	 * @param memberId
	 * @param attentionTime
	 * @return
	 */
	public Integer getAttentionStoryByTime(String memberId, String attentionTime);

}
