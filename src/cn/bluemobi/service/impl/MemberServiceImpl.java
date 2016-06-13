package cn.bluemobi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bluemobi.dao.BaseDao;
import cn.bluemobi.entity.Member;
import cn.bluemobi.entity.Page;
import cn.bluemobi.service.MemberService;
import cn.bluemobi.util.helper.ValidateHelper;
import cn.bluemobi.util.text.TextHelper;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private BaseDao dao;
	
	/**
	 * 验证登录，获取用户信息
	 * @param username
	 * @return
	 */
	public Member getMemberByMobile(String mobile){
		return dao.findForObject("SELECT m.id,m.`name`,m.mobile,m.`password`,DATE_FORMAT(m.last_login_time,'%Y-%m-%d %H:%i:%s')  AS lastLoginTime,m.`status`,m.sex,m.icon,m.introduction,DATE_FORMAT(m.create_time,'%Y-%m-%d %H:%i:%s') AS createTime,m.`source` FROM `member` AS m WHERE mobile = ? ", Member.class, mobile);

	}
	
	/**
	 * 修改密码
	 * @param password
	 * @param mobile
	 */
	public void changePassword(String password, String mobile){
		dao.executeByParams("UPDATE member SET password = ? WHERE mobile=? ", password,mobile);
	}
	
	
	/**
	 * 记录登录时间
	 * @param memberId
	 */
	public void saveLastLoginTime(String memberId){
		dao.executeByParams("UPDATE member SET last_login_time = now() WHERE id=? ", memberId);
	}

	/**
	 * 修改用户资料
	 * @param memberId
	 * @param icon
	 * @param name
	 * @param sex
	 */
	public void changeInfo(String memberId, String icon, String name, String sex,String introduction) {
		String sql = "  UPDATE member SET ";
		List<Object> list = new ArrayList<Object>();
		Object[] array = null;
		if(!TextHelper.isNullOrEmpty(icon)){
			sql += " icon = ?,";
			list.add(icon);
		}
		if(!TextHelper.isNullOrEmpty(name)){
			sql += " name = ?,";
			list.add(name);
		}
		if(!TextHelper.isNullOrEmpty(sex)){
			sql += " sex = ?,";
			list.add(sex);
		}
		if(!TextHelper.isNullOrEmpty(introduction)){
			sql += " introduction = ?,";
			list.add(introduction);
		}
		sql = sql.substring(0, sql.length()-1);
		sql += " WHERE id = ? ";
		list.add(memberId);
		array = list.toArray();
		dao.executeByParams(sql,array);
	}
	
	/**
	 * 根据用户id获取用户信息
	 * @param memberId
	 * @return
	 */
	public Member getMemberById(String memberId){
		return dao.findForObject("SELECT m.id,m.`name`,m.mobile,m.`password`,DATE_FORMAT(m.last_login_time,'%Y-%m-%d %H:%i:%s')  AS lastLoginTime,m.`status`,m.sex,m.icon,m.introduction,DATE_FORMAT(m.create_time,'%Y-%m-%d %H:%i:%s') AS createTime ,m.`source` FROM `member` AS m WHERE id = ? ", Member.class, memberId);
	}
	
	/**
	 * 根据qq查找用户
	 * @param qq
	 * @return
	 */
	public Member getMemberByQQ(String qq){
		return dao.findForObject("SELECT m.id,m.`name`,m.mobile,m.`password`,DATE_FORMAT(m.last_login_time,'%Y-%m-%d %H:%i:%s')  AS lastLoginTime,m.`status`,m.sex,m.icon,m.introduction,DATE_FORMAT(m.create_time,'%Y-%m-%d %H:%i:%s') AS createTime,m.`source` FROM `member` AS m WHERE qq = ? ", Member.class, qq);
	}
	
	/**
	 * 根据微信查找用户
	 * @param weixin
	 * @return
	 */
	public Member getMemberByWechart(String wechart){
		return dao.findForObject("SELECT m.id,m.`name`,m.mobile,m.`password`,DATE_FORMAT(m.last_login_time,'%Y-%m-%d %H:%i:%s')  AS lastLoginTime,m.`status`,m.sex,m.icon,m.introduction,DATE_FORMAT(m.create_time,'%Y-%m-%d %H:%i:%s') AS createTime,m.`source` FROM `member` AS m WHERE wechart = ?", Member.class, wechart);
	}
	/**
	 * 根据微博查找用户
	 * @param weixin
	 * @return
	 */
	public Member getMemberByWeibo(String weibo){
		return dao.findForObject("SELECT m.id,m.`name`,m.mobile,m.`password`,DATE_FORMAT(m.last_login_time,'%Y-%m-%d %H:%i:%s')  AS lastLoginTime,m.`status`,m.sex,m.icon,m.introduction,DATE_FORMAT(m.create_time,'%Y-%m-%d %H:%i:%s') AS createTime,m.`source` FROM `member` AS m WHERE weibo = ? ", Member.class, weibo);
	}
	
	/**
	 * 创建第三方登录用户
	 * @param source
	 * @param code
	 */
	public Long createMemberForThird(String source, String code){
		Member member = new Member();
		member.setSource(source);
		if(("2").equals(source)){
			member.setQq(code);
		}
		else if(("4").equals(source)){
			member.setWechart(code);
		}
		else if(("3").equals(source)){
			member.setWeibo(code);
		}
		
		return dao.saveGetKey("INSERT INTO member set `qq`=:qq,`wechart`=:wechart,`weibo`=:weibo,`create_time`=now(),`status`=1,source=:source ",member );
	}
	
	/**
	 * 创建普通用户
	 * @param me
	 * @return
	 */
	public Long create(Member me){
		return dao.saveGetKey("INSERT INTO member set `mobile`=:mobile,`password`=:password,`create_time`=now(),`status`=1,source=:source ",me );
	}
	
	/**
	 * 统计用户列表总数
	 * @return
	 */
	public Integer getMemberListCount(){
		return dao.findForInt(" SElECT COUNT(*) FROM `member` ");
	}
	/**
	 * 获取用户分页列表
	 * @param page
	 * @param name
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Map<String,Object> getMemberList(Page page,Member member){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		Object[] array = null;
		Object[] array1 = null;
		if (!ValidateHelper.isNullOrEmpty(member.getName())) {
			sb.append(" and name like ?");
			list.add('%' + member.getName() + '%');
		}
		if (!ValidateHelper.isNullOrEmpty(member.getMobile())) {
			sb.append(" and mobile like ?  ");
			list.add('%' + member.getMobile() + '%');
		}
		if (!ValidateHelper.isNullOrEmpty(member.getSex())) {
			sb.append(" and sex = ?  ");
			list.add(member.getSex());
		}
		// 注册日期
		if (!ValidateHelper.isNullOrEmpty(member.getStartTime())) {
			sb.append(" and DATE_FORMAT(create_time, '%Y-%m-%d')>=  DATE_FORMAT( ?, '%Y-%m-%d')");
			list.add(member.getStartTime());
		}
		if (!ValidateHelper.isNullOrEmpty(member.getEndTime())) {
			sb.append(" and DATE_FORMAT(create_time, '%Y-%m-%d')<=  DATE_FORMAT( ?, '%Y-%m-%d')");
			list.add(member.getEndTime());
		}
		String sql = sb.toString();
		array1 = list.toArray();
		sb.append(" ORDER BY create_time DESC  LIMIT ?, ? ");
		Integer count = dao.findForInt(" SElECT COUNT(*) FROM `member` WHERE 1 = 1 " + sql,array1);
		page.setTotalResult(count);
		list.add(page.getCurrentResult());
		list.add(page.getShowCount());
		array = list.toArray();
		List<Member> memberList = dao
				.findForList(
						" SELECT m.id,m.`name`,m.mobile,DATE_FORMAT(m.last_login_time, '%Y-%m-%d %H:%i:%s')  AS lastLoginTime,m.`status`,m.sex,m.icon,m.introduction,DATE_FORMAT(m.create_time, '%Y-%m-%d %H:%i:%s') AS createTime,m.source FROM `member` m WHERE 1=1 " + sb.toString(),
						Member.class, array);
		map.put("page", page);
		map.put("memberList", memberList);
		return map;
	}
	
	/**
	 * 更新用户状态
	 * @param idArr
	 */
	public void updateStatus(String[] idArr,String status){
		String[] sql = new String[100];
		for (int i = 0; i < idArr.length; i++) {
			sql[i] = " UPDATE `member` SET `status` = "+status+" WHERE id = "+idArr[i];
		}
		dao.executeBatch(sql);
	}
	/**
	 * 获取用户信息Forapp
	 * @param memberId
	 */
	public Member getMemberByIdForApp(String memberId){
		return dao.findForObject("SELECT m.id,m.`name`,m.sex,m.icon,m.introduction FROM `member` AS m WHERE id = ? ", Member.class, memberId);
	}
	/**
	 *获取最新用户 
	 * @return
	 */
	public List<Member> getLatestMemberList(){
		return dao.findForList(" SELECT m.id,m.`name`,m.mobile,m.source,DATE_FORMAT(m.create_time, '%Y-%m-%d %H:%i:%s') AS createTime  FROM `member` AS m ORDER BY create_time DESC LIMIT 3 ", Member.class);
	}
	
	/**
	 * 获取所有用户
	 * @return
	 */
	public List<Member> getAllMemberList(){
		return dao.findForList(" SELECT m.id,m.`name`,m.mobile,m.source,DATE_FORMAT(m.create_time, '%Y-%m-%d %H:%i:%s') AS createTime  FROM `member` AS m ", Member.class);
	}
	/**
	 * 根据关键字查找所有用户
	 * @return
	 */
	public List<Member> getAllMemberListBySearch(String memberId, String keywords, cn.bluemobi.entity.system.Page page){
		String sql = "";
		String sql1 = "";
		List<Object> list = new ArrayList<Object>();
		List<Object> list1 = new ArrayList<Object>();
		if(!TextHelper.isNullOrEmpty(memberId)){
			sql1 += ",IF((SELECT COUNT(*) FROM attention AS a WHERE a.attention_id = ? AND a.be_attention_id = m.id)>0,1,0) AS isAttention ";
			list.add(memberId);
		}else{
			sql1 += ",0 AS isAttention ";
		}
		
		if(!TextHelper.isNullOrEmpty(keywords)){
			sql += " AND m.`name` like ? ";
			list.add("%"+keywords+"%");
			list1.add("%"+keywords+"%");
		}
	    sql += " ORDER BY create_time ASC ";
		if (page.getPageCount() == -1) {
			dao.findPageSum("SELECT COUNT(*) FROM `member` AS m where m.`status` = 1  "+sql, page,list1.toArray());
			if (page.getTotalCount() == 0) {
				return null;
			}
		}
		return dao.findByPage(" SELECT m.id,m.`name`,m.introduction,m.icon "+sql1+" FROM `member` AS m "
				+ " WHERE m.`status` = 1 "+sql,page, Member.class,list.toArray());
	}
}
