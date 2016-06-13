package cn.bluemobi.entity; 
public class AdminRole {
    private Long id;
    private String roleName;
    private String system;
    private String member;
    private String storyIsland;
    private String microFilm;
    private String scriptFactory;
    private String starStory;
    private String comment;
    private String advertisement;
    private String cover;
    //推送
    private String message;
    private String feedback;
    private String report;
    private String aboutUs;
    private String description;
    
    
    public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }
    public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setSystem(String system){
        this.system=system;
    }
    public String getSystem(){
        return this.system;
    }
    public void setMember(String member){
        this.member=member;
    }
    public String getMember(){
        return this.member;
    }
    public void setStoryIsland(String storyIsland){
        this.storyIsland=storyIsland;
    }
    public String getStoryIsland(){
        return this.storyIsland;
    }
    public void setMicroFilm(String microFilm){
        this.microFilm=microFilm;
    }
    public String getMicroFilm(){
        return this.microFilm;
    }
    public void setScriptFactory(String scriptFactory){
        this.scriptFactory=scriptFactory;
    }
    public String getScriptFactory(){
        return this.scriptFactory;
    }
    public String getStarStory() {
		return starStory;
	}
	public void setStarStory(String starStory) {
		this.starStory = starStory;
	}
    public void setComment(String comment){
        this.comment=comment;
    }
    public String getComment(){
        return this.comment;
    }
    public void setAdvertisement(String advertisement){
        this.advertisement=advertisement;
    }
    public String getAdvertisement(){
        return this.advertisement;
    }
    public void setCover(String cover){
        this.cover=cover;
    }
    public String getCover(){
        return this.cover;
    }
    public void setFeedback(String feedback){
        this.feedback=feedback;
    }
    public String getFeedback(){
        return this.feedback;
    }
    public void setReport(String report){
        this.report=report;
    }
    public String getReport(){
        return this.report;
    }
    public void setAboutUs(String aboutUs){
        this.aboutUs=aboutUs;
    }
    public String getAboutUs(){
        return this.aboutUs;
    }
}
