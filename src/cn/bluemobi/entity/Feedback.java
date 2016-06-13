package cn.bluemobi.entity; 
public class Feedback {
    private Long id;
    private String createTime;
    private String text;
    private String reply;
    private String status;
    private Long memberId;
    private String startTime;
    private String endTime;
    private String name;
    
    
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }
    public void setCreateTime(String createTime){
        this.createTime=createTime;
    }
    public String getCreateTime(){
        return this.createTime;
    }
    public void setText(String text){
        this.text=text;
    }
    public String getText(){
        return this.text;
    }
    public void setReply(String reply){
        this.reply=reply;
    }
    public String getReply(){
        return this.reply;
    }
    public void setStatus(String status){
        this.status=status;
    }
    public String getStatus(){
        return this.status;
    }
    public void setMemberId(Long memberId){
        this.memberId=memberId;
    }
    public Long getMemberId(){
        return this.memberId;
    }
}
