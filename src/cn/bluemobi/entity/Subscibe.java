package cn.bluemobi.entity; 

import java.util.List;

/**
 * 订阅
 * @author xiazf
 *
 */
public class Subscibe {
    private Long id;
    private Long storyIslandId;
    private Long memberId;
    private String createTime;
    private String readTime;


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
    public void setMemberId(Long memberId){
        this.memberId=memberId;
    }
    public Long getMemberId(){
        return this.memberId;
    }
    public void setCreateTime(String createTime){
        this.createTime=createTime;
    }
    public String getCreateTime(){
        return this.createTime;
    }
    public void setReadTime(String readTime){
        this.readTime=readTime;
    }
    public String getReadTime(){
        return this.readTime;
    }
    
    //发布人id
    private String releaseId;
    //发布人名字
    private String name;
    //发布人头像
    private String icon;
    //发布人介绍
    private String introduction;
    //发布人关注数
    private Integer attentionNum;
    //故事列表
    private List<StoryIsland> storyIslandList;


	public String getReleaseId() {
		return releaseId;
	}
	public void setReleaseId(String releaseId) {
		this.releaseId = releaseId;
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
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public Integer getAttentionNum() {
		return attentionNum;
	}
	public void setAttentionNum(Integer attentionNum) {
		this.attentionNum = attentionNum;
	}
	public List<StoryIsland> getStoryIslandList() {
		return storyIslandList;
	}
	public void setStoryIslandList(List<StoryIsland> storyIslandList) {
		this.storyIslandList = storyIslandList;
	}
	
}
