package cn.bluemobi.entity; 
public class MessagePush {
    private Long id;
    private Long messageId;
    private Long pushTo;
    private String pushTime;
    private String status;
    private String title;
    private String startTime;
    private String endTime;
    
    
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }
    public void setMessageId(Long messageId){
        this.messageId=messageId;
    }
    public Long getMessageId(){
        return this.messageId;
    }
    public void setPushTo(Long pushTo){
        this.pushTo=pushTo;
    }
    public Long getPushTo(){
        return this.pushTo;
    }
    public void setPushTime(String pushTime){
        this.pushTime=pushTime;
    }
    public String getPushTime(){
        return this.pushTime;
    }
    public void setStatus(String status){
        this.status=status;
    }
    public String getStatus(){
        return this.status;
    }
}
