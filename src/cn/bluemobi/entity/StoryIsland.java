package cn.bluemobi.entity; 
public class StoryIsland {
	
    private Long id;
    //标题
    private String title;
    //发布人id
    private Long releaseId;
    //头像
    private String icon;
    
    //作者
    private String author;
    //发布时间
    private String releaseTime;
    //更新时间
    private String updateTime;
    //热度
    private Long hot;
    //精选
    private String choiceness;
    //
    private String storyType;
    private String textStory;
    private String imgStoryUrl;
    private String voiceStoryUrl;
    private String videoStoryUrl;
    private String duration;
    //1是开启0是禁用
    private String status;
    //封面
    private String cover;
    //排序选项 1是热度 0是时间
    private String type;
    //是否阅读
    private String isRead;
    //是否订阅
    private String isSubscibe;
    //是否点赞
    private String isPraise;
    //评论数量
    private Integer commentNum;
    //分类classifyId
    private Integer classifyId;
  	//分类classify
    private String classify;
    //关联电影id
    private String relateFilmId;
    //关联剧本id
    private String relateScriptId;
    
    //故事类型
    private String chapterType;
    
    
    //是否匿名
    private String isAnonymous;
    
    //匿名头像
    private String anonymousIcon;
    //匿名名字
    private String anonymousName;
    
    
    public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getChapterType() {
		return chapterType;
	}
	public void setChapterType(String chapterType) {
		this.chapterType = chapterType;
	}
	public String getRelateFilmId() {
		return relateFilmId;
	}
	public void setRelateFilmId(String relateFilmId) {
		this.relateFilmId = relateFilmId;
	}
	
	public String getRelateScriptId() {
		return relateScriptId;
	}
	public void setRelateScriptId(String relateScriptId) {
		this.relateScriptId = relateScriptId;
	}
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public Integer getClassifyId() {
		return classifyId;
	}
	public void setClassifyId(Integer classifyId) {
		this.classifyId = classifyId;
	}
	public String getIsSubscibe() {
		return isSubscibe;
	}
	public void setIsSubscibe(String isSubscibe) {
		this.isSubscibe = isSubscibe;
	}
	public String getIsPraise() {
		return isPraise;
	}
	public void setIsPraise(String isPraise) {
		this.isPraise = isPraise;
	}
	public Integer getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}
	public String getIsRead() {
		return isRead;
	}
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public void setAuthor(String author){
        this.author=author;
    }
    public String getAuthor(){
        return this.author;
    }
    public void setReleaseTime(String releaseTime){
        this.releaseTime=releaseTime;
    }
    public String getReleaseTime(){
        return this.releaseTime;
    }
    public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public void setHot(Long hot){
        this.hot=hot;
    }
    public Long getHot(){
        return this.hot;
    }
    public void setChoiceness(String choiceness){
        this.choiceness=choiceness;
    }
    public String getChoiceness(){
        return this.choiceness;
    }
    public void setStoryType(String storyType){
        this.storyType=storyType;
    }
    public String getStoryType(){
        return this.storyType;
    }
    public void setTextStory(String textStory){
        this.textStory=textStory;
    }
    public String getTextStory(){
        return this.textStory;
    }
    public void setImgStoryUrl(String imgStoryUrl){
        this.imgStoryUrl=imgStoryUrl;
    }
    public String getImgStoryUrl(){
        return this.imgStoryUrl;
    }
    public void setVoiceStoryUrl(String voiceStoryUrl){
        this.voiceStoryUrl=voiceStoryUrl;
    }
    public String getVoiceStoryUrl(){
        return this.voiceStoryUrl;
    }
    public void setVideoStoryUrl(String videoStoryUrl){
        this.videoStoryUrl=videoStoryUrl;
    }
    public String getVideoStoryUrl(){
        return this.videoStoryUrl;
    }
    public void setDuration(String duration){
        this.duration=duration;
    }
    public String getDuration(){
        return this.duration;
    }
    public void setStatus(String status){
        this.status=status;
    }
    public String getStatus(){
        return this.status;
    }
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getIsAnonymous() {
		return isAnonymous;
	}
	public void setIsAnonymous(String isAnonymous) {
		this.isAnonymous = isAnonymous;
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
	
}
