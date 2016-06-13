package cn.bluemobi.entity; 
public class ScriptChapter {
	
    private Long id;
    private Long scriptId;
    private String text;
    private String createTime;
    private String title;
    

    public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }
    public void setScriptId(Long scriptId){
        this.scriptId=scriptId;
    }
    public Long getScriptId(){
        return this.scriptId;
    }
    public void setText(String text){
        this.text=text;
    }
    public String getText(){
        return this.text;
    }
    public void setCreateTime(String createTime){
        this.createTime=createTime;
    }
    public String getCreateTime(){
        return this.createTime;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public String getTitle(){
        return this.title;
    }
    
    /**
     * 状态  1是发布 0是保存
     */ 
    private String status;


	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
