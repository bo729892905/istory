package cn.bluemobi.entity; 
public class StoryIslandChapter {
    private Long id;
    //故事岛id
    private Long storyIslandId;
    private String chapterType;
    private String textStory;
    private String imgStoryUrl;
    private String voiceStoryUrl;
    private String videoStoryUrl;
    //时长
    private String duration;
    //创建时间
    private String createTime;
    //保存状态，或者发布状态
    private String status;
    //排序
    private Integer sort;
    //标题
    private String title;
    //名字
    private String author;
    //头像
    private String icon;
    //点赞数
    private String praiseNum;
    //发布人
    private String releaseId;
    //信纸
    private String paper;
    //关联电影id
    private String relateFilmId;
    //关联剧本id
    private String relateScriptId;
    //是否点赞
    private Integer isPraise;
    //视频封面
    private String img;
    
    
    //是否匿名
    private String isAnonymous;
    
    //匿名头像
    private String anonymousIcon;
    //匿名名字
    private String anonymousName;
    
    private String oldText;
    private String color;
    private String strong;
    private String em;
    private String u;
    private String textAlign; 
    private String font;
    private String imgList;
  
    
    
	public String getOldText() {
		return oldText;
	}
	public void setOldText(String oldText) {
		this.oldText = oldText;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getStrong() {
		return strong;
	}
	public void setStrong(String strong) {
		this.strong = strong;
	}
	public String getEm() {
		return em;
	}
	public void setEm(String em) {
		this.em = em;
	}
	public String getU() {
		return u;
	}
	public void setU(String u) {
		this.u = u;
	}
	
	public String getTextAlign() {
		return textAlign;
	}
	public void setTextAlign(String textAlign) {
		this.textAlign = textAlign;
	}
	public String getFont() {
		return font;
	}
	public void setFont(String font) {
		this.font = font;
	}
	public String getImgList() {
		return imgList;
	}
	public void setImgList(String imgList) {
		this.imgList = imgList;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Integer getIsPraise() {
		return isPraise;
	}
	public void setIsPraise(Integer isPraise) {
		this.isPraise = isPraise;
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
	public String getPaper() {
		return paper;
	}
	public void setPaper(String paper) {
		this.paper = paper;
	}
	public String getReleaseId() {
		return releaseId;
	}
	public void setReleaseId(String releaseId) {
		this.releaseId = releaseId;
	}
	public String getPraiseNum() {
		return praiseNum;
	}
	public void setPraiseNum(String praiseNum) {
		this.praiseNum = praiseNum;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getTitle() {
		return title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }
    public void setStoryIslandId(Long storyIslandId){
        this.storyIslandId=storyIslandId;
    }
    public Long getStoryIslandId(){
        return this.storyIslandId;
    }
    public void setChapterType(String chapterType){
        this.chapterType=chapterType;
    }
    public String getChapterType(){
        return this.chapterType;
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
    public void setCreateTime(String createTime){
        this.createTime=createTime;
    }
    public String getCreateTime(){
        return this.createTime;
    }
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
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
