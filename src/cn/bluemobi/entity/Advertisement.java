package cn.bluemobi.entity; 
public class Advertisement {
    private Long id;
    //广告url
    private String advertisementUrl;
    private String linkUrl;
    private Long hit;
    //1开开启0禁用
    private String status;
    //1是故事岛 2是微电影 3是剧本
    private String type;
    //排序1-5
    private Long number;
    //广告标题
    private String title;
    //链接方式
    private String linkType;
    //链接id
    private Long linkId;
    
    //关联故事
    private Long relateStoryId;
    //关联电影
    private Long relateFilmId;
    //关联剧本
    private Long relateScriptId;
    
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
	public String getLinkType() {
		return linkType;
	}
	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}
	public Long getLinkId() {
		return linkId;
	}
	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}
	public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }
    public String getAdvertisementUrl() {
		return advertisementUrl;
	}
	public void setAdvertisementUrl(String advertisementUrl) {
		this.advertisementUrl = advertisementUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setLinkUrl(String linkUrl){
        this.linkUrl=linkUrl;
    }
    public String getLinkUrl(){
        return this.linkUrl;
    }
    public void setHit(Long hit){
        this.hit=hit;
    }
    public Long getHit(){
        return this.hit;
    }
    public void setStatus(String status){
        this.status=status;
    }
    public String getStatus(){
        return this.status;
    }
    public void setType(String type){
        this.type=type;
    }
    public String getType(){
        return this.type;
    }
    public void setNumber(Long number){
        this.number=number;
    }
    public Long getNumber(){
        return this.number;
    }
}
