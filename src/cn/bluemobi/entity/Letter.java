package cn.bluemobi.entity;
/**
 * 私信
 * @author xiazf
 *
 */
public class Letter {
	//id
	private Long id;
	//发送人id
	private Long sendId;
	//接收人id
	private Long receiveId;
	//1是文字 2是图
	private String type;
	//内容
	private String content;
	//创建时间
	private String createTime;
	//发送人姓名
	private String sendName;
	//发送人头像
	private String sendIcon;
	//数量
	private Integer num;
	
	//是否读
	private String isRead;
	
	
	public String getIsRead() {
		return isRead;
	}
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSendId() {
		return sendId;
	}
	public void setSendId(Long sendId) {
		this.sendId = sendId;
	}
	public Long getReceiveId() {
		return receiveId;
	}
	public void setReceiveId(Long receiveId) {
		this.receiveId = receiveId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	public String getSendIcon() {
		return sendIcon;
	}
	public void setSendIcon(String sendIcon) {
		this.sendIcon = sendIcon;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	
}
