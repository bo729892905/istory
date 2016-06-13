package cn.bluemobi.entity; 
public class RegisterProtocol {
    private Long id;
    private String content;
    private String createTime;


    public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }
    public void setContent(String content){
        this.content=content;
    }
    public String getContent(){
        return this.content;
    }
    public void setCreateTime(String createTime){
        this.createTime=createTime;
    }
    public String getCreateTime(){
        return this.createTime;
    }
}
