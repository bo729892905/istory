package cn.bluemobi.entity; 
public class Member {
	//用户id
    private Long id;
    //用户姓名 昵称
    private String name;
    //登录账号，手机号
    private String mobile;
    //登录密码
    private String password;
    //上次登录时间
    private String lastLoginTime;
    //状态
    private String status;
    //性别
    private String sex;
    //用户头像
    private String icon;
    //用户简介
    private String introduction;
    //用户创建时间
    private String createTime;
    //qqcode
    private String qq;
    //wechart
    private String wechart;
    //weibo
    private String weibo;
    //用户创建时间
    private String source;
    //开始日期(查询条件)
    private String startTime;
    //结束日期(查询条件)
    private String endTime;
    
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
	public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }
    public void setMobile(String mobile){
        this.mobile=mobile;
    }
    public String getMobile(){
        return this.mobile;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public String getPassword(){
        return this.password;
    }
    public void setLastLoginTime(String lastLoginTime){
        this.lastLoginTime=lastLoginTime;
    }
    public String getLastLoginTime(){
        return this.lastLoginTime;
    }
    public void setStatus(String status){
        this.status=status;
    }
    public String getStatus(){
        return this.status;
    }
    public void setSex(String sex){
        this.sex=sex;
    }
    public String getSex(){
        return this.sex;
    }
    public void setIcon(String icon){
        this.icon=icon;
    }
    public String getIcon(){
        return this.icon;
    }
    public void setIntroduction(String introduction){
        this.introduction=introduction;
    }
    public String getIntroduction(){
        return this.introduction;
    }
    public void setCreateTime(String createTime){
        this.createTime=createTime;
    }
    public String getCreateTime(){
        return this.createTime;
    }
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getWechart() {
		return wechart;
	}
	public void setWechart(String wechart) {
		this.wechart = wechart;
	}
	public String getWeibo() {
		return weibo;
	}
	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	//关注数量
	private Integer attentionNum;
	//粉丝数量
	private Integer beAttentionNum;
	//订阅数量
	private Integer subscibeNum;
	//是否关注
	private String isAttention;
	
	public Integer getAttentionNum() {
		return attentionNum;
	}
	public void setAttentionNum(Integer attentionNum) {
		this.attentionNum = attentionNum;
	}
	public Integer getBeAttentionNum() {
		return beAttentionNum;
	}
	public void setBeAttentionNum(Integer beAttentionNum) {
		this.beAttentionNum = beAttentionNum;
	}
	public Integer getSubscibeNum() {
		return subscibeNum;
	}
	public void setSubscibeNum(Integer subscibeNum) {
		this.subscibeNum = subscibeNum;
	}
	public String getIsAttention() {
		return isAttention;
	}
	public void setIsAttention(String isAttention) {
		this.isAttention = isAttention;
	}
}
