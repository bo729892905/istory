package cn.bluemobi.entity; 
public class MicroFilm {
    private Long id;
    private String title;
    private Long releaseId;
    private String theme;
    private String filmType;
    private String releaseTime;
    private String director;
    private String scriptwriter;
    private String actor;
    private String content;
    private String cover;
    private String vedioUrl;
    private Long playCount;
    private String status;
    private Long relateStoryId;
    private Long relateScriptId;
    //发布人
    private String author;
    //评论数量
    private String commentNum;
    //点赞数量
    private String  praiseNum;
    //是否点赞
    private String isPraise;
    //作者类型
    private String authorType;
    //作者头像
    private String icon;
    //是否关注
    private Integer isAttention;
    
    
	public Integer getIsAttention() {
		return isAttention;
	}
	public void setIsAttention(Integer isAttention) {
		this.isAttention = isAttention;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getAuthorType() {
		return authorType;
	}
	public void setAuthorType(String authorType) {
		this.authorType = authorType;
	}
	public String getIsPraise() {
		return isPraise;
	}
	public void setIsPraise(String isPraise) {
		this.isPraise = isPraise;
	}
	public String getPraiseNum() {
		return praiseNum;
	}
	public void setPraiseNum(String praiseNum) {
		this.praiseNum = praiseNum;
	}
	public String getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(String commentNum) {
		this.commentNum = commentNum;
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
    public void setTheme(String theme){
        this.theme=theme;
    }
    public String getTheme(){
        return this.theme;
    }
    public void setFilmType(String filmType){
        this.filmType=filmType;
    }
    public String getFilmType(){
        return this.filmType;
    }
    public void setReleaseTime(String releaseTime){
        this.releaseTime=releaseTime;
    }
    public String getReleaseTime(){
        return this.releaseTime;
    }
    public void setDirector(String director){
        this.director=director;
    }
    public String getDirector(){
        return this.director;
    }
    public void setScriptwriter(String scriptwriter){
        this.scriptwriter=scriptwriter;
    }
    public String getScriptwriter(){
        return this.scriptwriter;
    }
    public void setActor(String actor){
        this.actor=actor;
    }
    public String getActor(){
        return this.actor;
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
    public void setVedioUrl(String vedioUrl){
        this.vedioUrl=vedioUrl;
    }
    public String getVedioUrl(){
        return this.vedioUrl;
    }
    public void setPlayCount(Long playCount){
        this.playCount=playCount;
    }
    public Long getPlayCount(){
        return this.playCount;
    }
    public void setStatus(String status){
        this.status=status;
    }
    public String getStatus(){
        return this.status;
    }
    public void setRelateStoryId(Long relateStoryId){
        this.relateStoryId=relateStoryId;
    }
    public Long getRelateStoryId(){
        return this.relateStoryId;
    }
    public void setRelateScriptId(Long relateScriptId){
        this.relateScriptId=relateScriptId;
    }
    public Long getRelateScriptId(){
        return this.relateScriptId;
    }
}
