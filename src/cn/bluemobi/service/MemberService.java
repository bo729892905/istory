package cn.bluemobi.service;

import java.util.List;
import java.util.Map;

import cn.bluemobi.entity.Member;
import cn.bluemobi.entity.Page;

public interface MemberService {
	/**
	 * 验证登录
	 * @param mobile
	 * @return
	 */
	public Member getMemberByMobile(String mobile);
	/**
	 * 修改密码
	 * @param password
	 * @param mobile
	 */
	public void changePassword(String password, String mobile);
	/**
	 * 记录登录时间
	 * @param memberId
	 */
	public void saveLastLoginTime(String memberId);
	/**
	 * 修改用户资料
	 * @param memberId
	 * @param icon
	 * @param name
	 * @param sex
	 */
	public void changeInfo(String memberId, String icon, String name, String sex,String introduction);
	/**
	 * 根据用户id获取用户信息
	 * @param memberId
	 * @return
	 */
	public Member getMemberById(String memberId);
	
	/**
	 * 根据qq查找用户
	 * @param qq
	 * @return
	 */
	public Member getMemberByQQ(String qq);
	/**
	 * 根据微信查找用户
	 * @param weixin
	 * @return
	 */
	public Member getMemberByWechart(String wechart);
	/**
	 * 根据微信查找用户
	 * @param weixin
	 * @return
	 */
	public Member getMemberByWeibo(String weibo);
	/**
	 * 创建第三方登录用户
	 * @param source
	 * @param code
	 */
	public Long createMemberForThird(String source, String code);
	/**
	 * 创建普通用户
	 * @param me
	 * @return
	 */
	public Long create(Member me);
	/**
	 * 统计用户列表总数
	 * @return
	 */
	public Integer getMemberListCount();
	/**
	 * 获取用户分页列表
	 * @param page
	 * @param name
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @param sex 
	 * @return
	 */
	public Map<String,Object> getMemberList(Page page, Member member);
	/**
	 * 更新用户状态
	 * @param idArr
	 */
	public void updateStatus(String[] idArr,String status);
	/**
	 * 获取用户信息Forapp
	 * @param memberId
	 * @return 
	 */
	public Member getMemberByIdForApp(String memberId);
	/**
	 *获取最新用户 
	 * @return
	 */
	public List<Member> getLatestMemberList();
	/**
	 * 获取所有用户
	 * @return
	 */
	public List<Member> getAllMemberList();
	/**
	 * 根据关键字查找所有用户
	 * @param page 
	 * @param keywords 
	 * @param memberId 
	 * @return
	 */
	public List<Member> getAllMemberListBySearch(String memberId, String keywords, cn.bluemobi.entity.system.Page page);

}
