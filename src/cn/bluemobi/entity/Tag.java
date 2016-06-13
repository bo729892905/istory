package cn.bluemobi.entity; 
public class Tag {
    private Long id;
    private String tag;
    private String createTime;


    public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }
    public void setTag(String tag){
        this.tag=tag;
    }
    public String getTag(){
        return this.tag;
    }
    public void setCreateTime(String createTime){
        this.createTime=createTime;
    }
    public String getCreateTime(){
        return this.createTime;
    }
}
