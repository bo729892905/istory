package cn.bluemobi.entity; 
public class ScriptFactory {
    private Long id;
    private String title;
    private Long releaseId;
    private String content;
    private String cover;
    //场次
    private Long chapter;
    
    private Long role;
    
    private Long hot;
    
    private Long relateStoryId;
    private Long relateFilmId;
    private String releaseTime;
    private String status;
    //评论数量
    private String commentNum;
    //发布人
    private String author;
    //发布人类型 0是普通用户 1是管理员
    private String authorType;
    /**
     * 头像
     * @return
     */
    private String icon;
    
    private String isPraise;
    
    
    public String getIsPraise() {
		return isPraise;
	}
	public void setIsPraise(String isPraise) {
		this.isPraise = isPraise;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(String commentNum) {
		this.commentNum = commentNum;
	}
	public String getAuthorType() {
		return authorType;
	}
	public void setAuthorType(String authorType) {
		this.authorType = authorType;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setReleaseId(Long releaseId){
        this.releaseId=releaseId;
    }
    public Long getReleaseId(){
        return this.releaseId;
    }
    public void setContent(String content){
        this.content=content;
    }
    public String getContent(){
        return this.content;
    }
    public void setCover(String cover){
        this.cover=cover;
    }
    public String getCover(){
        return this.cover;
    }
    public void setChapter(Long chapter){
        this.chapter=chapter;
    }
    public Long getChapter(){
        return this.chapter;
    }
    public void setRole(Long role){
        this.role=role;
    }
    public Long getRole(){
        return this.role;
    }
    public void setHot(Long hot){
        this.hot=hot;
    }
    public Long getHot(){
        return this.hot;
    }
    public void setRelateStoryId(Long relateStoryId){
        this.relateStoryId=relateStoryId;
    }
    public Long getRelateStoryId(){
        return this.relateStoryId;
    }
    public void setRelateFilmId(Long relateFilmId){
        this.relateFilmId=relateFilmId;
    }
    public Long getRelateFilmId(){
        return this.relateFilmId;
    }
    public void setReleaseTime(String releaseTime){
        this.releaseTime=releaseTime;
    }
    public String getReleaseTime(){
        return this.releaseTime;
    }
    public void setStatus(String status){
        this.status=status;
    }
    public String getStatus(){
        return this.status;
    }
}
