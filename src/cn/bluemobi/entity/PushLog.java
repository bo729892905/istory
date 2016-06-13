package cn.bluemobi.entity;
/**
 * 系统消息
 * @author xiazf
 *
 */
public class PushLog {
	//id
	private Long id;
	//被推送对象id
	private Long pushId;
	//被推送用户id
	private Long memberId;
	//创建时间
	private String createTime;
	//推送类型 1是意见反馈回复 2是系统推送的消息
	private String type;
	
	//标题
	private String title;
	//内容
	private String  content;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPushId() {
		return pushId;
	}
	public void setPushId(Long pushId) {
		this.pushId = pushId;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
