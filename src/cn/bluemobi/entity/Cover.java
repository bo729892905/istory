package cn.bluemobi.entity; 
public class Cover {
    private Long id;
    private String cover;
    private String createTime;
    private Long releaseId;
    private String name;

    public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }
    public void setCover(String cover){
        this.cover=cover;
    }
    public String getCover(){
        return this.cover;
    }
    public void setCreateTime(String createTime){
        this.createTime=createTime;
    }
    public String getCreateTime(){
        return this.createTime;
    }
    public void setReleaseId(Long releaseId){
        this.releaseId=releaseId;
    }
    public Long getReleaseId(){
        return this.releaseId;
    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
