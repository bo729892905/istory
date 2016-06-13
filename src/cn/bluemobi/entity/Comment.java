package cn.bluemobi.entity; 
public class Comment {
    private Long id;
    //作者id
    private Long releaseId;
    //评论时间
    private String createTime;
    //被评论id
    private Long beCommentId;
    //被评论二级id
    private Long secondId;
    
    //被评论类型 1是故事 2电影 3剧本 4星故事
    private String type;
    
    private String title;
    //评论人id
    private Long memberId;
    //评论人名字
    private String name;
    //评论内容
    private String comment;
    //发布人名字
    private String author;
    //头像
    private String icon;
    //点赞数量
    private Integer praiseNum;
    //故事岛评论
    public final static Integer STORY_COMMENT=1;
    //微电影评论
    public final static Integer FILM_COMMENT=2;
    //剧本评论
    public final static Integer SCRIPT_COMMENT=3;
    //星故事
    public final static Integer STAR_COMMENT=4;
    //评论
    public final static Integer COMMENT_COMMENT=5;
    
    //是否点赞 1是 0不是
    private String isPraise;
    //发布起止日期
    private String startTime;
    private String endTime;
    
    //评论数量
    private Integer commentNum;
    
    //是否匿名
    private String isAnonymous;
    
    //匿名头像
    private String anonymousIcon;
    //匿名名字
    private String anonymousName;
    
    //被回复人的评论id
    private Long beReplyId;
    //被回复的评论是否匿名
    private String beReplyIsAnonymous;
    //被回复人的名字
    private String beReplyName;
   //被回复的用户id
    private Long beReplyMemberId;
    
    //封面
    private String cover;
    
    //关联故事id
    private Long relateStoryId;
    //关联电影id
    private Long relateFilmId;
    //关联剧本id
    private Long relateScriptId;
    
	public String getBeReplyIsAnonymous() {
		return beReplyIsAnonymous;
	}
	public void setBeReplyIsAnonymous(String beReplyIsAnonymous) {
		this.beReplyIsAnonymous = beReplyIsAnonymous;
	}
	public Long getBeReplyMemberId() {
		return beReplyMemberId;
	}
	public void setBeReplyMemberId(Long beReplyMemberId) {
		this.beReplyMemberId = beReplyMemberId;
	}
	public String getBeReplyName() {
		return beReplyName;
	}
	public void setBeReplyName(String beReplyName) {
		this.beReplyName = beReplyName;
	}
	public Long getBeReplyId() {
		return beReplyId;
	}
	public void setBeReplyId(Long beReplyId) {
		this.beReplyId = beReplyId;
	}
	public String getAnonymousIcon() {
		return anonymousIcon;
	}
	public void setAnonymousIcon(String anonymousIcon) {
		this.anonymousIcon = anonymousIcon;
	}
	public String getAnonymousName() {
		return anonymousName;
	}
	public void setAnonymousName(String anonymousName) {
		this.anonymousName = anonymousName;
	}
	public String getIsAnonymous() {
		return isAnonymous;
	}
	public void setIsAnonymous(String isAnonymous) {
		this.isAnonymous = isAnonymous;
	}
	public Integer getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}
	public Long getSecondId() {
		return secondId;
	}
	public void setSecondId(Long secondId) {
		this.secondId = secondId;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
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
	public String getIsPraise() {
		return isPraise;
	}
	public void setIsPraise(String isPraise) {
		this.isPraise = isPraise;
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
    public void setBeCommentId(Long beCommentId){
        this.beCommentId=beCommentId;
    }
    public Long getBeCommentId(){
        return this.beCommentId;
    }
    public void setType(String type){
        this.type=type;
    }
    public String getType(){
        return this.type;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setMemberId(Long memberId){
        this.memberId=memberId;
    }
    public Long getMemberId(){
        return this.memberId;
    }
    public void setComment(String comment){
        this.comment=comment;
    }
    public String getComment(){
        return this.comment;
    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getPraiseNum() {
		return praiseNum;
	}
	public void setPraiseNum(Integer praiseNum) {
		this.praiseNum = praiseNum;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public Long getRelateStoryId() {
		return relateStoryId;
	}
	public void setRelateStoryId(Long relateStoryId) {
		this.relateStoryId = relateStoryId;
	}
	public Long getRelateFilmId() {
		return relateFilmId;
	}
	public void setRelateFilmId(Long relateFilmId) {
		this.relateFilmId = relateFilmId;
	}
	public Long getRelateScriptId() {
		return relateScriptId;
	}
	public void setRelateScriptId(Long relateScriptId) {
		this.relateScriptId = relateScriptId;
	}
	
	
}
