package cn.bluemobi.entity;

/**
 * 关注
 * @author xiazf
 *
 */
public class Attention {
	//主键id
	private Long id;
	 //关注人id	
	private Long attentionId;
	 //被关注人id
	private Long beAttentionId;
	 //创建时间
	private Long createTime;
	//用户头像
	private String icon;
	//用户姓名
	private String name;
	//用户简介
	private String introduction;
	//是否相互关注
	private String attentionEachOther;
	
	
	public String getAttentionEachOther() {
		return attentionEachOther;
	}
	public void setAttentionEachOther(String attentionEachOther) {
		this.attentionEachOther = attentionEachOther;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAttentionId() {
		return attentionId;
	}
	public void setAttentionId(Long attentionId) {
		this.attentionId = attentionId;
	}
	public Long getBeAttentionId() {
		return beAttentionId;
	}
	public void setBeAttentionId(Long beAttentionId) {
		this.beAttentionId = beAttentionId;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	 
	 
}
