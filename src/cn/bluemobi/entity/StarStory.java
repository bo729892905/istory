package cn.bluemobi.entity; 
public class StarStory {
    private Long id;
    //标题
    private String title;
    //发布人id
    private Long releaseId;
    //发布人
    private String author;
    //
    private Long number;
    //浏览次数
    private Long viewCount;
    //发布时间
    private String releaseTime;
    //热度
    private Long hot;
    //状态
    private String status;
    //海报url
    private String posterUrl;
    //类型 1是图文 2是视频
    private String type;
    //封面
    private String cover;
    //内容
    private String content;
    //视频地址
    private String vedioUrl;
    //是否点赞
    private String isPraise;
    //评论数量
    private Integer commentNum;
    
   
	public Integer getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
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
    public void setNumber(Long number){
        this.number=number;
    }
    public Long getNumber(){
        return this.number;
    }
    public void setViewCount(Long viewCount){
        this.viewCount=viewCount;
    }
    public Long getViewCount(){
        return this.viewCount;
    }
    public void setReleaseTime(String releaseTime){
        this.releaseTime=releaseTime;
    }
    public String getReleaseTime(){
        return this.releaseTime;
    }
    public void setHot(Long hot){
        this.hot=hot;
    }
    public Long getHot(){
        return this.hot;
    }
    public void setStatus(String status){
        this.status=status;
    }
    public String getStatus(){
        return this.status;
    }
    public void setPosterUrl(String posterUrl){
        this.posterUrl=posterUrl;
    }
    public String getPosterUrl(){
        return this.posterUrl;
    }
    public void setType(String type){
        this.type=type;
    }
    public String getType(){
        return this.type;
    }
   /* public void setCover(String cover){
        this.cover=cover;
    }
    public String getCover(){
        return this.cover;
    }*/
    public void setContent(String content){
        this.content=content;
    }
    public String getContent(){
        return this.content;
    }
    public void setVedioUrl(String vedioUrl){
        this.vedioUrl=vedioUrl;
    }
    public String getVedioUrl(){
        return this.vedioUrl;
    }
	public String getIsPraise() {
		return isPraise;
	}
	public void setIsPraise(String isPraise) {
		this.isPraise = isPraise;
	}
    
}
