package cn.bluemobi.entity; 
public class Admin {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String status;
    private Long roleId;
    //记录登录时间
    private String loginTime;
    //权限名
    private String roleName;
    
    
    public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
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
    public void setPassword(String password){
        this.password=password;
    }
    public String getPassword(){
        return this.password;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }
    public void setStatus(String status){
        this.status=status;
    }
    public String getStatus(){
        return this.status;
    }
    public void setRoleId(Long roleId){
        this.roleId=roleId;
    }
    public Long getRoleId(){
        return this.roleId;
    }
}
