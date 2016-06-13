package cn.bluemobi.entity; 
public class SystemLog {
    private Long id;
    //用户账号
    private String username;
    //权限名
    private String roleName;
    //操作
    private String operation;
    //登录时间
    private String loginTime;
    //操作时间
    private String createTime;
    //起
    private String startTime;
    //止
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public String getUsername(){
        return this.username;
    }
    public void setRoleName(String roleName){
        this.roleName=roleName;
    }
    public String getRoleName(){
        return this.roleName;
    }
    public void setOperation(String operation){
        this.operation=operation;
    }
    public String getOperation(){
        return this.operation;
    }
    public void setLoginTime(String loginTime){
        this.loginTime=loginTime;
    }
    public String getLoginTime(){
        return this.loginTime;
    }
}
