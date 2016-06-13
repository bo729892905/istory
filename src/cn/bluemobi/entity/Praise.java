package cn.bluemobi.entity; 
public class Praise {
    private Long id;
    private Long bePraiseId;
    private Long praiseId;
    private String createTime;
    //1是故事岛2是微电影3是剧本 4是星故事 5是评论
    private String type;

    public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }
    public void setBePraiseId(Long bePraiseId){
        this.bePraiseId=bePraiseId;
    }
    public Long getBePraiseId(){
        return this.bePraiseId;
    }
    public void setPraiseId(Long praiseId){
        this.praiseId=praiseId;
    }
    public Long getPraiseId(){
        return this.praiseId;
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
    
}
