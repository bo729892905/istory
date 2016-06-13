package cn.bluemobi.entity; 
public class Report {
    private Long id;
    private Long releaseId;
    private String createTime;
    private String type;
    private Long beReportId;
    private Long tagId;
    private Long memberId;
    //举报日期起止
    private String startTime;
    private String endTime;
    //标题
    private String title;
    //标签
    private String tag;
    //作者名字
    private String name;
    //举报次数
    private Integer reportTimes;
    
    
    public Integer getReportTimes() {
		return reportTimes;
	}
	public void setReportTimes(Integer reportTimes) {
		this.reportTimes = reportTimes;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }
    public void setReleaseId(Long releaseId){
        this.releaseId=releaseId;
    }
    public Long getReleaseId(){
        return this.releaseId;
    }
    public void setCreateTime(String createTime){
        this.createTime=createTime;
    }
    public String getCreateTime(){
        return this.createTime;
    }
    public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setBeReportId(Long beReportId){
        this.beReportId=beReportId;
    }
    public Long getBeReportId(){
        return this.beReportId;
    }
    public void setTagId(Long tagId){
        this.tagId=tagId;
    }
    public Long getTagId(){
        return this.tagId;
    }
}
